package org.noear.sponge.admin.model.sponge_track;

import org.noear.wood.*;

/// <summary>
/// 生成:2017/12/05 10:24:43
/// 
/// </summary>
public class StatTotalPvUvModel implements IBinder
{
    public long url_id;
    public long total_pv;
    public long total_uv;
    public long d30_pv;
    public long d30_uv;
    public long d7_pv;
    public long d7_uv;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        url_id = s.get("url_id").value(0L);
        total_pv = s.get("total_pv").value(0L);
        total_uv = s.get("total_uv").value(0L);
        d30_pv = s.get("d30_pv").value(0L);
        d30_uv = s.get("d30_uv").value(0L);
        d7_pv = s.get("d7_pv").value(0L);
        d7_uv = s.get("d7_uv").value(0L);
	}
	
	public IBinder clone()
	{
		return new StatTotalPvUvModel();
	}
}