package webapp.track.dso;

import java.net.*;
import java.util.HashMap;
import java.util.Map;

import org.noear.solon.core.handle.Context;
import org.noear.water.utils.TextUtils;
import webapp.utils.IpUtil;

public class HttpRequestX {
    public Context context;
    public HttpRequestX(Context context){
        this.context = context;
    }

    public String getReferer(){
        String temp = getHeader("Referer");
        if (TextUtils.isEmpty(temp)) {
            return "";
        }else{
            return temp;
        }
    }

    public String getHeader(String name){
        return context.header(name);
    }

    public String getUserAgent(){
        String v = getHeader("User-Agent");
        if(v == null) {
            return "";
        }
        else {
            return v;
        }
    }

    public String getUserIP(){
        return IpUtil.getIP(context);
    }

    public String getCookie(String name){
        return context.cookie(name,"");
    }

    public void setCookie(String name, String val) {
        context.cookieSet(name, val, 60 * 60 * 24 * 365);//单位为秒(365天)
    }

    public String getParameter(String name, boolean incReferer){
        if("@host".equals(name)){
            tryParseReferer();

            if (_refererUrl == null) {
                return "";
            }else{
                return _refererUrl.getHost();
            }
        }

        if("@path".equals(name)){
            tryParseReferer();

            if (_refererUrl == null) {
                return "";
            }else{
                return _refererUrl.getPath();
            }
        }

        if("@pathall".equals(name)){
            tryParseReferer();

            if (_refererUrl == null) {
                return "";
            }else{
                return _refererUrl.getHost()+_refererUrl.getPath();
            }
        }

        ////////////////

        String v = context.param(name);
        if(v == null) {
            if(incReferer)
                return getRefParameter(name,"");
            else
                return "";
        }
        else
            return v;
    }

    Map<String,String> _refParameter;
    public String getRefParameter(String name, String def) {
        tryParseReferer();

        if(_refParameter.containsKey(name)){
            return _refParameter.get(name);
        }else{
            return def;
        }
    }

    //获取referer url 上的参数
    private URI _refererUrl;
    private void tryParseReferer(){
        if(_refParameter == null){
            _refParameter = new HashMap<>();
        }else{
            return;
        }

        String temp = getHeader("Referer");
        if (TextUtils.isEmpty(temp)) {
            return;
        }

        try {
            _refererUrl = URI.create(temp);


            String[] mAry = _refererUrl.getQuery().split("&");

            for (String str : mAry){
                String[] ss = str.split("=");

                _refParameter.put(ss[0].toLowerCase(), ss[1]);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
