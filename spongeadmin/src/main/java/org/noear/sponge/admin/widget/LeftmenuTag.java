package org.noear.sponge.admin.widget;

import org.noear.grit.client.GritClient;
import org.noear.grit.client.GritUtil;
import org.noear.grit.model.domain.Resource;
import org.noear.grit.model.domain.ResourceEntity;
import org.noear.grit.model.domain.ResourceGroup;
import org.noear.solon.core.handle.Context;
import org.noear.sponge.admin.dso.Session;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * Created by yuety on 14-9-10.
 */
public class LeftmenuTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        try {
            Context ctx = Context.current();
            String path = ctx.pathNew();
            long subjectId = Session.global().getSubjectId();

            StringBuilder buf = new StringBuilder();

            List<ResourceGroup> groupList = GritClient.global().auth().getUriGroupList(subjectId);
            long groupId = 0;
            for (ResourceGroup group : groupList) {
                if (path.startsWith(group.link_uri)) {
                    groupId = group.resource_id;
                    break;
                }
            }

            buf.append("<menu>");
            buf.append("<div onclick=\"$('main').toggleClass('smlmenu');if(window.onMenuHide){window.onMenuHide();}\"><i class='fa fa-bars'></i></div>");
            buf.append("<items>");

            List<ResourceEntity> resList = GritClient.global().auth().getUriListByGroup(subjectId, groupId);
            for (Resource res : resList) {
                buildItem(buf, res, path);
            }

            buf.append("</items>");
            buf.append("</menu>");

            pageContext.getOut().write(buf.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.doStartTag();
    }

    private void buildItem(StringBuilder buf, Resource res, String path) {
        if ("$".equals(res.display_name)) {
            buf.append("<br/><br/>");
            return;
        }

        String newUrl = GritUtil.buildDockUri(res);

        if (path.indexOf(res.link_uri) >= 0) {
            buf.append("<a class='sel' href='" + newUrl + "'>");
            buf.append(res.display_name);
            buf.append("</a>");
        } else {
            buf.append("<a href='" + newUrl + "'>");
            buf.append(res.display_name);
            buf.append("</a>");
        }
    }
}
