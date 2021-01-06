package org.noear.sponge.admin.models.sponge_track;

import org.noear.weed.*;

/// <summary>
/// 生成:2017/12/05 10:24:43
/// 
/// </summary>
public class TmpTotalPvUvModel implements IBinder
{
    public long obj_id;
    public long pv;
    public long uv;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        obj_id = s.get("obj_id").value(0L);
        pv = s.get("pv").value(0L);
        uv = s.get("uv").value(0L);
	}
	
	public IBinder clone()
	{
		return new TmpTotalPvUvModel();
	}
}