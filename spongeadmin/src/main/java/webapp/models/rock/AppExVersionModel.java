package webapp.models.rock;

import lombok.Getter;
import org.noear.weed.*;
import java.util.*;

/// <summary>
/// 生成:2018/01/26 11:56:11
/// 
/// </summary>
@Getter
public class AppExVersionModel implements IBinder
{
    public int row_id;
    public int agroup_id;
    public int app_id;
    public int ver;
    public String content;
    public int type;
    public int alert_ver;
    public int force_ver;
    public int platform;
    public String url;
    public int is_enable;
    public Date log_fulltime;
    public long counts;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        row_id = s.get("row_id").value(0);
        agroup_id = s.get("agroup_id").value(0);
        app_id = s.get("app_id").value(0);
        ver = s.get("ver").value(0);
        content = s.get("content").value(null);
        type = s.get("type").value(0);
        alert_ver = s.get("alert_ver").value(0);
        force_ver = s.get("force_ver").value(0);
        platform = s.get("platform").value(0);
        url = s.get("url").value(null);
        is_enable = s.get("is_enable").value(0);
        log_fulltime = s.get("log_fulltime").value(null);
        counts = s.get("counts").value(0L);
	}
	
	public IBinder clone()
	{
		return new AppExVersionModel();
	}
}