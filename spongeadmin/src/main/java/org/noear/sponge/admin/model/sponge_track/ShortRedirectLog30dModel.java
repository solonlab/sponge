package org.noear.sponge.admin.model.sponge_track;

import org.noear.weed.*;
import java.util.*;

/// <summary>
/// 生成:2017/12/05 10:24:42
/// 
/// </summary>
public class ShortRedirectLog30dModel implements IBinder
{
    public long log_id;
    public long url_id;
    public int tag_id;
    public long user_id;
    public String user_key;
    public String v1;
    public String v2;
    public String v3;
    public String v4;
    public String v5;
    public String log_ip;
    public long log_ip_id;
    public long log_ua_id;
    public int log_date;
    public int log_hour;
    public Date log_fulltime;
    public int admin_group;

    public String url_name;
    public String tag_name;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        log_id = s.get("log_id").value(0L);
        url_id = s.get("url_id").value(0L);
        tag_id = s.get("tag_id").value(0);
        user_id = s.get("user_id").value(0L);
        user_key = s.get("user_key").value(null);
        v1 = s.get("v1").value(null);
        v2 = s.get("v2").value(null);
        v3 = s.get("v3").value(null);
        v4 = s.get("v4").value(null);
        v5 = s.get("v5").value(null);
        log_ip = s.get("log_ip").value(null);
        log_ip_id = s.get("log_ip_id").value(0L);
        log_ua_id = s.get("log_ua_id").value(0L);
        log_date = s.get("log_date").value(0);
        log_hour = s.get("log_hour").value(0);
        log_fulltime = s.get("log_fulltime").value(null);
        admin_group = s.get("admin_group").value(0);

        url_name = s.get("url_name").value(null);
        tag_name = s.get("tag_name").value(null);
	}
	
	public IBinder clone()
	{
		return new ShortRedirectLog30dModel();
	}
}