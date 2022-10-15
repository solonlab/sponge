package org.noear.sponge.admin.model.sponge_track;

import lombok.Getter;
import org.noear.wood.*;

/// <summary>
/// 生成:2017/12/11 02:59:52
/// 
/// </summary>
@Getter
public class TrackTagExTrackStatModel implements IBinder
{
    public long row_id;
    public int tag_id;
    public int tag_pid;
    public int admin_group;
    public int vi;
    public String vd;
    public long uv_total;
    public long pv_total;
    public long ip_total;
    public long uv_today;
    public long pv_today;
    public long ip_today;
    public long uv_yesterday;
    public long pv_yesterday;
    public long ip_yesterday;
    public String url_name;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        row_id = s.get("row_id").value(0L);
        tag_id = s.get("tag_id").value(0);
        tag_pid = s.get("tag_pid").value(0);
        admin_group = s.get("admin_group").value(0);
        vi = s.get("vi").value(0);
        vd = s.get("vd").value(null);
        uv_total = s.get("uv_total").value(0L);
        pv_total = s.get("pv_total").value(0L);
        ip_total = s.get("ip_total").value(0L);
        uv_today = s.get("uv_today").value(0L);
        pv_today = s.get("pv_today").value(0L);
        ip_today = s.get("ip_today").value(0L);
        uv_yesterday = s.get("uv_yesterday").value(0L);
        pv_yesterday = s.get("pv_yesterday").value(0L);
        ip_yesterday = s.get("ip_yesterday").value(0L);
        url_name = s.get("url_name").value(null);
	}
	
	public IBinder clone()
	{
		return new TrackTagExTrackStatModel();
	}
}