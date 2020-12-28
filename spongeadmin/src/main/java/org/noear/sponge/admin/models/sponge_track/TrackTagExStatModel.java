package org.noear.sponge.admin.models.sponge_track;

import lombok.Getter;
import org.noear.weed.*;

/// <summary>
/// 生成:2017/12/07 10:23:24
/// 
/// </summary>
@Getter
public class TrackTagExStatModel implements IBinder
{
    public int tag_id;
    public int tag_pid;
    public int admin_group;
    public long uv_total;
    public long pv_total;
    public long ip_total;
    public long uv_today;
    public long pv_today;
    public long ip_today;
    public long uv_yesterday;
    public long pv_yesterday;
    public long ip_yesterday;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        tag_id = s.get("tag_id").value(0);
        tag_pid = s.get("tag_pid").value(0);
        admin_group = s.get("admin_group").value(0);
        uv_total = s.get("uv_total").value(0L);
        pv_total = s.get("pv_total").value(0L);
        ip_total = s.get("ip_total").value(0L);
        uv_today = s.get("uv_today").value(0L);
        pv_today = s.get("pv_today").value(0L);
        ip_today = s.get("ip_today").value(0L);
        uv_yesterday = s.get("uv_yesterday").value(0L);
        pv_yesterday = s.get("pv_yesterday").value(0L);
        ip_yesterday = s.get("ip_yesterday").value(0L);
	}
	
	public IBinder clone()
	{
		return new TrackTagExStatModel();
	}
}