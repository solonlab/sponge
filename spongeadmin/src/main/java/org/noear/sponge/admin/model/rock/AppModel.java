package org.noear.sponge.admin.model.rock;

import lombok.Getter;
import org.noear.weed.*;
import java.util.*;

/// <summary>
/// 生成:2018/01/22 05:47:35
/// 
/// </summary>
@Getter
public class AppModel implements IBinder
{
    public int app_id;
    public String app_key;
    public String app_secret_key;
    public int ugroup_id;
    public int agroup_id;
    public String name;
    public String note;
    public int is_disabled;

    public Date log_fulltime;

    //管理是否开启设置
    public int ar_is_setting;
    //管理是否审核中
    public int ar_is_examine;
    //管理审核版本号
    public int ar_examine_ver;
    public long counts;
	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        app_id = s.get("app_id").value(0);
        app_key = s.get("app_key").value(null);
        app_secret_key = s.get("app_secret_key").value(null);
        ugroup_id = s.get("ugroup_id").value(0);
        agroup_id = s.get("agroup_id").value(0);
        name = s.get("name").value(null);
        note = s.get("note").value(null);
        log_fulltime = s.get("log_fulltime").value(null);

        ar_is_setting = s.get("ar_is_setting").value(0);
        ar_is_examine = s.get("ar_is_examine").value(0);
        ar_examine_ver = s.get("ar_examine_ver").value(0);


        counts = s.get("counts").value(0L);

	}
	
	public IBinder clone()
	{
		return new AppModel();
	}
}