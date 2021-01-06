package org.noear.sponge.admin.widget;

import org.noear.bcf.BcfClient;
import org.noear.bcf.BcfUtil;
import org.noear.bcf.models.BcfGroupModel;
import org.noear.bcf.models.BcfResourceModel;
import org.apache.http.util.TextUtils;
import org.noear.solon.core.handle.Context;
import org.noear.sponge.admin.Config;
import org.noear.sponge.admin.dso.Session;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * Created by yuety on 14-9-10.
 */
public class HeaderTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        try {

            //当前视图path //此处改过，xyj，20180831
            String cPath = Context.current().path();


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
            sb.append("<div>");
            sb.append("<agroup class='left'>");
            //cls1
            sb.append("<a>").append(Config.web_title).append("</a>");

            //cls2::下拉框形式显示App Group
//            sb.append("<select class=\"header_select\">");
//            for (AppGroupModel appGroup :
//                    DbAppGroupApi.getAllAppGroups()) {
//                sb.append("<option class=\"header_option\">");
//                sb.append(appGroup.getName());
//                sb.append("</option>");
//            }
//            sb.append("</select>");

            //cls3::图片形式
//            sb.append("<img src=\"");
//            sb.append(request.getAttribute("img"));
//            sb.append("/title_51kb.png\"/>");

            sb.append("</agroup>");
            sb.append("<nav class='left'>");

            for(BcfGroupModel g :list) {
                BcfResourceModel res = BcfClient.getUserFirstResourceByPack(Session.current().getPUID(), g.pgid);

                if (TextUtils.isEmpty(res.uri_path) == false) {
                    buildItem(sb, g.cn_name, res, cPath, g.uri_path); //::en_name 改为 uri_path
                }
            }

            sb.append("</nav>");

            sb.append("<p class='right'>");
            String temp = Session.current().getUserName();
            if(temp!=null) {
                sb.append("欢迎 ");
                sb.append(temp);
            }
            sb.append(" （<a href='#' onclick='modifyMm();return false;' >修改密码</a>）");
            sb.append("</p>");

            sb.append("</div>");
            sb.append("</header>");

            pageContext.getOut().write(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.doStartTag();
    }

    private void buildItem(StringBuffer sb,String title,BcfResourceModel res,String cPath,String pack) {

        //此处改过，xyj，201811(uadmin)
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
