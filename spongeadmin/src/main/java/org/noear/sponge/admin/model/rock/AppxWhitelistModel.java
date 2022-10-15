package org.noear.sponge.admin.model.rock;

import lombok.Getter;
import org.noear.wood.*;

/// <summary>
/// 生成:2018/02/05 04:49:46
/// 
/// </summary>
@Getter
public class AppxWhitelistModel implements IBinder
{
    public int row_id;
    public String tag;
    public int type;
    public String value;
    public String note;
    public long count;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        row_id = s.get("row_id").value(0);
        tag = s.get("tag").value(null);
        type = s.get("type").value(0);
        value = s.get("value").value(null);
        note = s.get("note").value(null);
        count = s.get("count").value(0L);

	}
	
	public IBinder clone()
	{
		return new AppxWhitelistModel();
	}
}