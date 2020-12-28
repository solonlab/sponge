package webapp.widget;

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

            StringBuffer sb = new StringBuffer();
            sb.append("<footer>");

            if(cPath.indexOf("/WEB-INF/view/login")==0) //只在登录页显示
            {
                sb.append("<p>");
                //sb.append("众马科技 浙ICP备16027614号");
                sb.append("</p>");
            }

            sb.append("</footer>");
            pageContext.getOut().write(sb.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        return super.doEndTag();
    }
}
