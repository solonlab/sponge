package org.noear.sponge.admin.model.sponge_track;

import org.noear.weed.*;

/// <summary>
/// 生成:2017/12/05 10:24:43
/// 
/// </summary>
public class UserAgentModel implements IBinder
{
    public long ua_id;
    public String ua_key;
    public String ua_val;
    public int platform;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        ua_id = s.get("ua_id").value(0L);
        ua_key = s.get("ua_key").value(null);
        ua_val = s.get("ua_val").value(null);
        platform = s.get("platform").value(0);
	}
	
	public IBinder clone()
	{
		return new UserAgentModel();
	}
}