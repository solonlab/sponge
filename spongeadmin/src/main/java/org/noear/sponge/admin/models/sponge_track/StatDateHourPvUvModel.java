package org.noear.sponge.admin.models.sponge_track;

import org.noear.weed.*;

/// <summary>
/// 生成:2017/12/05 10:24:43
/// 
/// </summary>
public class StatDateHourPvUvModel implements IBinder
{
    public long row_id;
    public long url_id;
    public int log_date;
    public int log_hour;
    public int pv;
    public int uv;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        row_id = s.get("row_id").value(0L);
        url_id = s.get("url_id").value(0L);
        log_date = s.get("log_date").value(0);
        log_hour = s.get("log_hour").value(0);
        pv = s.get("pv").value(0);
        uv = s.get("uv").value(0);
	}
	
	public IBinder clone()
	{
		return new StatDateHourPvUvModel();
	}
}