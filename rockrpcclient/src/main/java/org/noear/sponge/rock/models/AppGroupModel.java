package org.noear.sponge.rock.models;

import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

/// <summary>
/// 生成:2017/05/25 09:41:35
/// 
/// </summary>
public class AppGroupModel implements IBinder
{
    public int agroup_id;
    public String name;
	public int ugroup_id;

	@Override
	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        agroup_id = s.get("agroup_id").value(0);
        name = s.get("name").value(null);
		ugroup_id = s.get("ugroup_id").value(0);
	}

	@Override
	public IBinder clone()
	{
		return new AppGroupModel();
	}
}