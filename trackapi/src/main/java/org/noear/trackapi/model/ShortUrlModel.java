package org.noear.trackapi.model;

import org.noear.water.utils.TextUtils;
import org.noear.wood.GetHandlerEx;
import org.noear.wood.IBinder;

public class ShortUrlModel implements IBinder {
    public long url_id;
    public int tag_id;
    public String url_key;
    public String url_name;
    public String url_val;

    public String user_field;

    public String track_params; //c,b,d
    public String trans_params; //x=c,y=d
    public int admin_group;

    public int is_disable;

    @Override
    public void bind(GetHandlerEx s) {
        //1.source:数据源
        //
        url_id = s.get("url_id").value(0L);
        tag_id = s.get("tag_id").value(0);
        url_key = s.get("url_key").value(null);
        url_name = s.get("url_name").value("");
        url_val = s.get("url_val").value(null);

        user_field = s.get("user_field").value(null);

        //跟踪参数
        track_params = s.get("track_params").value("").replaceAll("\\*","");//对挖掘标签进行过滤
        //透传参数
        trans_params = s.get("trans_params").value("");//
        admin_group = s.get("admin_group").value(0);

        is_disable = s.get("is_disable").value(0);
    }

    @Override
    public IBinder clone() {
        return new ShortUrlModel();
    }

    public boolean isRequirePost(){
        return trans_params != null && trans_params.startsWith("#");
    }

    public boolean isRequireBuildUrl(){
        return TextUtils.isEmpty(trans_params) == false && trans_params.startsWith("#") == false;
    }
}