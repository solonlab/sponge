package org.noear.sponge.admin.widget;

import org.noear.grit.client.GritClient;
import org.noear.grit.model.domain.Resource;
import org.noear.grit.model.domain.ResourceEntity;
import org.noear.solon.core.handle.Context;
import org.noear.sponge.admin.dso.Session;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * Created by yuety on 14-9-10.
 */
public class ToolmenuTag extends TagSupport {
    private String pack;

    public String getPack() {
        return this.pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            String groupCode = getPack();

            Context ctx = Context.current();
            String path = ctx.pathNew();
            StringBuffer buf = new StringBuffer();


            Resource resourceGroup = GritClient.global().resource().getResourceByCode(groupCode);

            if (resourceGroup.resource_id > 0) {
                buf.append("<toolmenu>");
                buf.append("<tabbar>");

                List<ResourceEntity> list = GritClient.global().auth()
                        .getUriListByGroup(Session.current().getSubjectId(), resourceGroup.resource_id);

                for (Resource r : list) {
                    buildItem(ctx, buf, r.display_name, r.link_uri, path);
                }

                buf.append("</tabbar>");
                buf.append("</toolmenu>");

                pageContext.getOut().write(buf.toString());
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


    private void buildItem(Context ctx, StringBuffer sb, String title, String url, String cPath) {
        String url2 = url + "?" + ctx.uri().getQuery();

        if (cPath.indexOf(url) > 0) {
            sb.append("<button onclick=\"location='" + url2 + "'\" class='sel'>");
            sb.append(title);
            sb.append("</button>");
        } else {
            sb.append("<button onclick=\"location='" + url2 + "'\">");
            sb.append(title);
            sb.append("</button>");
        }
    }
}
