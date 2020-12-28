package org.noear.sponge.admin.models.sponge_track;

import lombok.Getter;
import org.noear.weed.*;

/// <summary>
/// 生成:2018/07/25 05:32:27
/// 
/// </summary>
@Getter
public class StatTrackDateHourPvUvIpModel implements IBinder
{
    public long row_id;
    public long url_id;
    public long tag_id;
    public int vi;
    public String vd;
    public int log_date;
    public int log_hour;
    public long uv;
    public long pv;
    public long ip;
    public long uk;
    public long ua;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        row_id = s.get("row_id").value(0L);
        url_id = s.get("url_id").value(0L);
        tag_id = s.get("tag_id").value(0L);
        vi = s.get("vi").value(0);
        vd = s.get("vd").value(null);
        log_date = s.get("log_date").value(0);
        log_hour = s.get("log_hour").value(0);
        uv = s.get("uv").value(0L);
        pv = s.get("pv").value(0L);
        ip = s.get("ip").value(0L);
        uk = s.get("uk").value(0L);
        ua = s.get("ua").value(0L);
	}
	
	public IBinder clone()
	{
		return new StatTrackDateHourPvUvIpModel();
	}
}