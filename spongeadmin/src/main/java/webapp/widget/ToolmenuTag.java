package webapp.widget;


import org.noear.bcf.BcfClient;
import org.noear.bcf.models.BcfGroupModel;
import org.noear.bcf.models.BcfResourceModel;
import org.noear.solon.core.handle.Context;
import webapp.dso.Session;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by yuety on 14-9-10.
 */
public class ToolmenuTag extends TagSupport {
    private String pack;
    public String getPack(){return this.pack;}
    public void setPack(String pack){this.pack=pack;}

    @Override
    public int doStartTag() throws JspException {
        try {
            //当前视图path
            HttpServletRequest request = ((HttpServletRequest)pageContext.getRequest());
            String cPath = Context.current().path();;

            StringBuffer sb = new StringBuffer();

            BcfGroupModel gPack = BcfClient.getGroupByCode(pack);

            if (gPack.pgid > 0) {
                sb.append("<toolmenu>");
                sb.append("<tabbar>");

                forPack(request,gPack.pgid, sb, cPath);

                sb.append("</tabbar>");
                sb.append("</toolmenu>");

                pageContext.getOut().write(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        return super.doEndTag();
    }

    private void forPack(HttpServletRequest request, int packID, StringBuffer sb, String cPath) throws SQLException {
        List<BcfResourceModel> list = BcfClient.getUserResourcesByPack(Session.current().getPUID(), packID);

        for (BcfResourceModel r : list) {
            buildItem(request,sb, r.cn_name, r.uri_path, cPath);
        }
    }

    private void buildItem(HttpServletRequest request,StringBuffer sb,String title,String url,String cPath) {
        String url2 = url + "?"+request.getQueryString();

        if(cPath.indexOf(url)!=-1)
        {
            sb.append("<button onclick=\"location='"+url2+"'\" class='sel'>");
            sb.append(title);
            sb.append("</button>");
        }
        else
        {
            sb.append("<button onclick=\"location='"+url2+"'\">");
            sb.append(title);
            sb.append("</button>");
        }
    }
}
