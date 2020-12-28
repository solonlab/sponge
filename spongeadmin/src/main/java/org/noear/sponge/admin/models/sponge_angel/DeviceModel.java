package org.noear.sponge.admin.models.sponge_angel;

import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.util.Date;

/// <summary>
/// 生成:2018/01/08 02:20:50
/// 
/// </summary>
public class DeviceModel implements IBinder
{
    public long device_id;
    public String device_udid;
    public String device_name;
    public Date log_fulltime;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        device_id = s.get("device_id").value(0L);
        device_udid = s.get("device_udid").value(null);
        device_name = s.get("device_name").value(null);
        log_fulltime = s.get("log_fulltime").value(null);
	}
	
	public IBinder clone()
	{
		return new DeviceModel();
	}
}