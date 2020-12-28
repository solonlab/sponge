package lib.sponge.rock.models;

import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

/// <summary>
/// 生成:2017/05/25 09:41:35
/// 
/// </summary>
public class UserGroupModel implements IBinder
{
    public int ugroup_id;
    public String name;

	@Override
	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
		ugroup_id = s.get("ugroup_id").value(0);
        name = s.get("name").value(null);
	}

	@Override
	public IBinder clone()
	{
		return new UserGroupModel();
	}
}