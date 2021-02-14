package org.noear.sponge.admin.model.rock;

import lombok.Getter;
import org.noear.weed.*;

/// <summary>
/// 生成:2018/01/22 05:47:35
/// 
/// </summary>
@Getter
public class AppExSettingModel implements IBinder
{
    public int row_id;
    public int agroup_id;
    public int app_id;
    public int is_client;
    public String name;
    public int type;
    public String value;
    public String note;
    public int ver_start;

    public long counts;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        row_id = s.get("row_id").value(0);
        agroup_id = s.get("agroup_id").value(0);
        app_id = s.get("app_id").value(0);
        is_client = s.get("is_client").value(0);
        name = s.get("name").value(null);
        type = s.get("type").value(0);
        value = s.get("value").value(null);
        note = s.get("note").value(null);
        ver_start = s.get("ver_start").value(0);

        counts = s.get("counts").value(0L);
	}
	
	public IBinder clone()
	{
		return new AppExSettingModel();
	}
}