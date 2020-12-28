package webapp.widget;

import org.apache.http.util.TextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by yuety on 15/1/9.
 */
public class StateSelectorTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        try {
            StringBuilder sb = new StringBuilder();

            String clinetStateKey = clientID + stateKey;

            sb.append("<script>").append("function " + clientID + "_onStateSelect(val,e) { ");

            if (forPage && TextUtils.isEmpty(onSelect))
                sb.append("    UrlQueryBy('" + stateKey + "',val,'page');");
            else {
                sb.append("    $('#" + clinetStateKey + "').val(val);")
                        .append("    var m = $('#" + clientID + "');")
                        .append("    m.find('.selected').removeClass('selected');")
                        .append("    $(e).addClass('selected');");

                if(TextUtils.isEmpty(onSelect) == false){
                    sb.append(onSelect).append("(val,e);");
                }
            }


            sb.append("} </script>");



            sb.append("<input id='" + clinetStateKey + "' name='" + clinetStateKey + "' value='" + getState() + "' type='hidden' />");
            sb.append("<span class='selector' id='" + clientID + "'>");
            sb.append(buildHtml());
            sb.append("</span>");

            pageContext.getOut().write(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        return super.doEndTag();
    }


    //--------
    private String clientID="";
    private boolean forPage=true;
    private String onSelect = "";

    public String getOnSelect(){
        return this.onSelect;
    }

    public void  setOnSelect(String val){
        this.onSelect = val;
    }

    public String getClientID(){
        return clientID;
    }
    public void setClientID(String val){
        clientID=val;
    }

    public boolean getForPage(){
        return forPage;
    }

    public void setForPage(boolean val){
        forPage=val;
    }


    private String stateKey = "_state";

    private int state = -1;//按自然顺序，0,1,2,3,4
    public int getState() {

        String key = clientID + stateKey;
        int s1 = getInt(key);

        if (s1 > -1)
            return s1;

        if (state > -1)
            return state;

        return 0;
    }

    public void setState(int val) {
        state = val;
    }

    private String items;
    public String getItems(){
        return items;
    }
    public void setItems(String val){
        items = val;
    }

    private int getInt(String key) {
        String p = pageContext.getRequest().getParameter(key);

        if(p==null|| p.length()==0)
            return -1;
        else
            return Integer.parseInt(p);
    }

    protected String buildHtml()
    {
        if (items == null || items.length()==0)
            return "";


        StringBuilder sb = new StringBuilder();

        int idx = 0;
        for (String item : items.split(","))
        {
            if (idx > 0)
                sb.append('|');

            if (idx == getState())
                sb.append("<span class='stateItem selected' onclick='"+clientID+"_onStateSelect("+idx+",this)'>"+item+"</span>");
            else
                sb.append("<span class='stateItem' onclick='"+clientID+"_onStateSelect("+idx+",this)'>"+item+"</span>");

            idx++;
        }
        return sb.toString();
    }
}
