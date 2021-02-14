package org.noear.rock.model;

import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

public class UserGroupModel implements IBinder {
	public int ugroup_id;
	public String name;

	@Override
	public void bind(GetHandlerEx s) {
		//1.source:数据源
		//
		ugroup_id = s.get("ugroup_id").value(0);
		name = s.get("name").value(null);
	}

	@Override
	public IBinder clone() {
		return new UserGroupModel();
	}
}