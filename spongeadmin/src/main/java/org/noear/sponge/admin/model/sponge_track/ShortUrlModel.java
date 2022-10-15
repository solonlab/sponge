package org.noear.sponge.admin.model.sponge_track;

import lombok.Getter;
import org.noear.wood.*;
import org.noear.sponge.admin.Config;

import java.util.*;

/// <summary>
/// 生成:2017/12/05 10:24:42
/// 
/// </summary>
@Getter
public class ShortUrlModel implements IBinder
{
    public long url_id;
    public int tag_id;
    public String url_partner_key;
    public String url_key;
    public String url_name;
    public String url_val_md5;
    public String url_val;
    public String user_field;
    public String track_params;
    public int track_params_num;
    public String trans_params;
    public long url_redirect_count;
    public String note;
    public Date create_fulltime;
    public int admin_group;
    public String build_link;
    public int is_disable;
    public Date update_time;

    //是否有跟踪项目
    public int  has_track;

	public void bind(GetHandlerEx s) {
        //1.source:数据源
        //
        url_id = s.get("url_id").value(0L);
        tag_id = s.get("tag_id").value(0);
        url_partner_key = s.get("url_partner_key").value(null);
        url_key = s.get("url_key").value(null);
        url_name = s.get("url_name").value(null);
        url_val_md5 = s.get("url_val_md5").value(null);
        url_val = s.get("url_val").value(null);
        user_field = s.get("user_field").value(null);
        track_params = s.get("track_params").value(null);
        track_params_num = s.get("track_params_num").value(0);
        trans_params = s.get("trans_params").value(null);
        url_redirect_count = s.get("url_redirect_count").value(0L);
        note = s.get("note").value(null);
        create_fulltime = s.get("create_fulltime").value(null);
        admin_group = s.get("admin_group").value(0);
        build_link = s.get("build_link").value(null);
        is_disable = s.get("is_enable").value(0);
        update_time = s.get("update_time").value(null);

//        if (track_params != null && track_params.indexOf("*") > 0) {
//            has_track = 1;
//        }
        if (track_params != null) {
            has_track = 1;
        }
    }
	
	public IBinder clone()
	{
		return new ShortUrlModel();
	}

	public String full_url() {
        return Config.track_uri() + url_key;
    }

}