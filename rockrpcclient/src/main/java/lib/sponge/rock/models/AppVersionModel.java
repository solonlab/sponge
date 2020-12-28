package lib.sponge.rock.models;

import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.util.Date;

/// <summary>
/// 生成:2017/08/08 12:03:44
/// 
/// </summary>
public class AppVersionModel implements IBinder
{
    public int agroup_id;
    public int app_id;
    public int ver;
    public String content;
    /** 更新方式（0：普通更新， 1：强制更新） */
    public int type;
    /** x版本以下提示更新 */
    public int alert_ver;
    /** x版本以下强制更新 */
    public int force_ver;
    public int platform;
    public String url;
    public int is_enable;
    public Date log_fulltime;

    @Override
	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
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
	}

    @Override
	public IBinder clone()
	{
		return new AppVersionModel();
	}
}