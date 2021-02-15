package org.noear.sponge.admin.widget;

import org.noear.solon.core.handle.Context;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by yuety on 14-9-10.
 */
public class FooterTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        try {
            //当前视图path
            String cPath = Context.current().path();


            if (cPath.startsWith("/WEB-INF/view/login")) {
                StringBuffer sb = new StringBuffer();
                sb.append("<footer>");
                sb.append("<p>");
                sb.append("</p>");
                sb.append("</footer>");
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
}
