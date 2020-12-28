package org.noear.sponge.admin.models.panda;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.util.Date;

/// <summary>
/// 生成:2018/08/27 05:22:23
/// 
/// </summary>
@Data
public class PandaRiskRubberModel implements IBinder
{
    public long row_id;
    public long open_id;
    public int agroup_id;
    public long order_id;
    public String request_id;
    public int score;
    public int advise;
    public String details_url;
    public Date create_fulltime;
    public int create_date;
    public Date update_fulltime;
    public int update_date;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        row_id = s.get("row_id").value(0L);
        open_id = s.get("open_id").value(0L);
        agroup_id = s.get("agroup_id").value(0);
        order_id = s.get("order_id").value(0L);
        request_id = s.get("request_id").value(null);
        score = s.get("score").value(0);
        advise = s.get("advise").value(0);
        details_url = s.get("details_url").value(null);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
	}
	
	public IBinder clone()
	{
		return new PandaRiskRubberModel();
	}
}