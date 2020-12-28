package org.noear.sponge.admin.widget;

import org.apache.http.util.TextUtils;
import org.noear.solon.core.handle.Context;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by yuety on 14-9-10.
 */
public class PagebarTag extends TagSupport {

    private int pageIndex;
    private int pageCount;
    private int rowCount;
    private int pageSize;

    public int getPageSize(){return pageSize;}
    public void setPageSize(int pageSize){this.pageSize=pageSize;}

    public int getRowCount(){return rowCount;}
    public void setRowCount(int rowCount){this.rowCount=rowCount;}

    @Override
    public int doStartTag() throws JspException {
        try {

            if(pageSize==0)//pageSize要有初始值，不然不会出错
                pageSize=1;

            pageCount = rowCount/pageSize;
            if(rowCount%pageSize>0)
                pageCount++;

            if(pageCount==0)
                pageCount=1;


            String page = Context.current().param("page");
            if(TextUtils.isEmpty(page)==false)
                pageIndex = Integer.parseInt(page);
            else
                pageIndex = 1;

            if(pageIndex<1)
                pageIndex=1;

            StringBuffer sb = new StringBuffer();
            sb.append("<pagebar>");

            sb.append("<left>");
            sb.append(" <table>");
            sb.append("  <tr>");
            sb.append("   <td><button class='form_button' onclick=\"UrlQueryBy('page',1)\" "+(1 == pageIndex ? "disabled='disabled'":"")+" >首页</button></td>");
            sb.append("   <td><button class='form_button' onclick=\"UrlQueryBy('page',"+(pageIndex-1)+")\" "+(1 == pageIndex ? "disabled='disabled'":"")+" >上一页</button></td>");
            sb.append("   <td><input  id='js_pager_no' type='text' value='"+(pageIndex)+"' onblur=\"UrlQueryBy('page',this.value);\" class='form_text' /></td>");
            sb.append("   <td><button class='form_button' onclick=\"UrlQueryBy('page',"+(pageIndex+1)+")\" "+ (pageCount == pageIndex ? "disabled='disabled'":"") +" >下一页</button></td>");
            sb.append("   <td><button class='form_button' onclick=\"UrlQueryBy('page',"+(pageCount)+")\" "+(pageCount == pageIndex ? "disabled='disabled'":"")+" >尾页</button></td>");
            sb.append("  </tr>");
            sb.append(" </table>");
            sb.append("</left>");

            sb.append("<right>");
            sb.append("  <em>共<span>"+(pageCount)+"</span>页</em>");
            sb.append("  <em>（共<span>"+(rowCount)+"</span>条记录）</em>");
            sb.append("</right>");

            sb.append("</pagebar>");

            sb.append("<script type=\"text/javascript\">\n" +
                    "    $(function () {\n" +
                    "        \n" +
                    "        $('#js_pager_no').keyup(function(event){\n" +
                    "            if (event.keyCode == 13)\n" +
                    "                UrlQueryBy('page',this.value);\n" +
                    "        });\n" +
                    "\n" +
                    "        $(document).keyup(function (event) {\n" +
                    "\n" +
                    "            if (event.ctrlKey) {\n" +
                    "                if (event.keyCode == 37)\n" +
                    "                    UrlQueryBy('page',"+(pageIndex -1)+");\n" +
                    "                else if (event.keyCode == 39)\n" +
                    "                    UrlQueryBy('page',"+(pageIndex +1)+");\n" +
                    "            }\n" +
                    "        });\n" +
                    "    });\n" +
                    "</script>");


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
