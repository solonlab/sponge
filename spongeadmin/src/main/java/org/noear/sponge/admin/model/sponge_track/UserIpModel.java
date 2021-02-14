package org.noear.sponge.admin.model.sponge_track;

import org.noear.weed.*;

/// <summary>
/// 生成:2017/12/05 10:24:43
/// 
/// </summary>
public class UserIpModel implements IBinder
{
    public long ip_id;
    public String ip_val;
    public int net_segment;
    public int city_code;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        ip_id = s.get("ip_id").value(0L);
        ip_val = s.get("ip_val").value(null);
        net_segment = s.get("net_segment").value(0);
        city_code = s.get("city_code").value(0);
	}
	
	public IBinder clone()
	{
		return new UserIpModel();
	}
}