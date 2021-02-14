package org.noear.sponge.admin.model.rock;

import lombok.Data;
import org.noear.weed.*;

/// <summary>
/// 生成:2018/01/22 05:47:35
/// 
/// </summary>
@Data
public class AppGroupModel implements IBinder
{
    public int agroup_id;
    public String name;
    public String tag;
    public int ugroup_id;
    public int is_disabled;
    public long counts;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        agroup_id = s.get("agroup_id").value(0);
        name = s.get("name").value(null);
        tag = s.get("tag").value(null);
        ugroup_id = s.get("ugroup_id").value(0);
        is_disabled = s.get("is_disabled").value(0);

	}

	public IBinder clone()
	{
		return new AppGroupModel();
	}
}