package org.noear.sponge.admin.model.rock;

import lombok.Data;
import org.noear.wood.*;

/// <summary>
/// 生成:2018/01/23 01:44:26
/// 
/// </summary>
@Data
public class UserGroupModel implements IBinder
{
    public int ugroup_id;
    public String name;
	public int is_disabled;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        ugroup_id = s.get("ugroup_id").value(0);
        name = s.get("name").value(null);
		is_disabled = s.get("is_disabled").value(0);
	}
	
	public IBinder clone()
	{
		return new UserGroupModel();
	}
}