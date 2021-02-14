package org.noear.sponge.admin.model.sponge_track;

import lombok.Getter;
import org.noear.weed.*;

/// <summary>
/// 生成:2017/12/13 01:51:26
/// 
/// </summary>
@Getter
public class StatDateHourPvUvIpModel implements IBinder
{
    public long row_id;
    public long url_id;
    public int tag_id;
    public int log_date;
    public int log_hour;
    public long pv;
    public long uv;
    public long ip;
    public long uv2;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        row_id = s.get("row_id").value(0L);
        url_id = s.get("url_id").value(0L);
        tag_id = s.get("tag_id").value(0);
        log_date = s.get("log_date").value(0);
        log_hour = s.get("log_hour").value(0);
        pv = s.get("pv").value(0L);
        uv = s.get("uv").value(0L);
        ip = s.get("ip").value(0L);
        uv2 = s.get("uv2").value(0L);
	}
	
	public IBinder clone()
	{
		return new StatDateHourPvUvIpModel();
	}
}