package org.noear.sponge.admin.widget;

import org.noear.bcf.BcfClient;
import org.noear.bcf.BcfUtil;
import org.noear.bcf.models.BcfGroupModel;
import org.noear.bcf.models.BcfResourceModel;
import org.noear.solon.core.handle.Context;
import org.noear.sponge.admin.Config;
import org.noear.sponge.admin.dso.Session;
import org.noear.water.utils.TextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

public class HeaderTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        try {

            //当前视图path //此处改过，noear，20180831
            Context context = Context.current();
            String cPath = context.path();


            if (Session.current().getPUID() == 0) {   //检查用户是已登录
                Context.current().redirect("/login");
                return super.doStartTag();
            }

            List<BcfGroupModel> list = BcfClient.getUserPacks(Session.current().getPUID());

            if(list.size() == 0){
                Context.current().redirect("/login");
                return super.doStartTag();
            }


            StringBuffer sb = new StringBuffer();
            sb.append("<header>");

            sb.append("<label>"); //new
            sb.append(Config.web_title);
            sb.append("</label>\n");//new


            sb.append("<nav>");

            for(BcfGroupModel g :list) {
                BcfResourceModel res = BcfClient.getUserFirstResourceByPack(Session.current().getPUID(), g.pgid);

                if (TextUtils.isEmpty(res.uri_path) == false) {
                    buildItem(sb, g.cn_name, res, cPath, g.uri_path); //::en_name 改为 uri_path
                }
            }

            sb.append("</nav>\n");

            sb.append("<aside>");//new
            String temp = Session.current().getUserName();
            if(temp!=null) {
                sb.append("<i class='fa fa-user'></i> ");
                sb.append(temp);
            }

            sb.append("<a class='logout' href='/'><i class='fa fa-fw fa-circle-o-notch'></i>退出</a>");
            sb.append("</aside>");//new

            sb.append("</header>\n");

            pageContext.getOut().write(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.doStartTag();
    }

    private void buildItem(StringBuffer sb,String title,BcfResourceModel res,String cPath,String pack) {

        //此处改过，noear，201811(uadmin)
        String newUrl = BcfUtil.buildBcfUnipath(res);

        if(cPath.indexOf(pack)==0)
        {
            sb.append("<a class='sel' href='" + newUrl + "'>");
            sb.append(title);
            sb.append("</a>");
        }
        else
        {
            sb.append("<a href='" + newUrl + "'>");
            sb.append(title);
            sb.append("</a>");
        }

    }
}
