package org.noear.sponge.admin.widget;

import org.noear.grit.client.GritClient;
import org.noear.grit.client.GritUtil;
import org.noear.grit.model.domain.Resource;
import org.noear.grit.model.domain.ResourceEntity;
import org.noear.grit.model.domain.ResourceGroup;
import org.noear.solon.Solon;
import org.noear.solon.Utils;
import org.noear.solon.core.handle.Context;
import org.noear.sponge.admin.dso.Session;
import org.noear.water.utils.TextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

public class HeaderTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        try {

            Context ctx = Context.current();
            long subjectId = Session.current().getSubjectId();
            String path = ctx.pathNew();

            if (subjectId == 0) {
                //如果用户未登录
                ctx.redirect("/login");
                return super.doStartTag();
            }

            List<ResourceGroup> groupList = GritClient.global().auth().getUriGroupList(subjectId);

            if (groupList.size() == 0) {
                ctx.redirect("/login");
                return super.doStartTag();
            }


            StringBuilder buf = new StringBuilder();
            buf.append("<header>");

            buf.append("<label>"); //new
            buf.append(Solon.cfg().appTitle());
            buf.append("</label>\n");//new


            buf.append("<nav>");

            for (ResourceGroup group : groupList) {
                ResourceEntity res = GritClient.global().auth().getUriFristByGroup(subjectId, group.resource_id);

                if (Utils.isEmpty(res.link_uri) == false) {
                    buildGroupItem(buf, group, res, path);
                }
            }

            buf.append("</nav>\n");

            buf.append("<aside>");//new

            String userDisplayName = Session.current().getDisplayName();
            if (Utils.isNotEmpty(userDisplayName)) {
                buf.append("<a>");
                buf.append("<i class='fa fa-user'></i> ");
                buf.append(userDisplayName);
                buf.append("</a>");
            }

            buf.append("<a class='split' href='/'><i class='fa fa-fw fa-circle-o-notch'></i>退出</a>");
            buf.append("</aside>");//new

            buf.append("</header>\n");

            pageContext.getOut().write(buf.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.doStartTag();
    }

    private void buildGroupItem(StringBuilder buf, ResourceGroup resourceGroup, Resource res, String path) {
        String newUrl = GritUtil.buildDockUri(res);

        if (path.indexOf(resourceGroup.link_uri) == 0) {
            buf.append("<a class='sel' href='" + newUrl + "'>");
            buf.append(resourceGroup.display_name);
            buf.append("</a>");
        } else {
            buf.append("<a href='" + newUrl + "'>");
            buf.append(resourceGroup.display_name);
            buf.append("</a>");
        }
    }
}
