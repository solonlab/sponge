package webapp.models.panda;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.util.Date;

/// <summary>
/// 生成:2018/08/27 05:22:23
/// 
/// </summary>
@Data
public class PandaOperatorTotalModel implements IBinder
{
    public long open_id;
    public String details_url_base;
    public String details_url_credit;
    public String details_url_call_log;
    public String details_url_bill;
    public String details_url_statistic;
    public String details_url_all;
    public int type;
    public Date create_fulltime;
    public int create_date;
    public Date update_fulltime;
    public int update_date;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        open_id = s.get("open_id").value(0L);
        details_url_base = s.get("details_url_base").value(null);
        details_url_credit = s.get("details_url_credit").value(null);
        details_url_call_log = s.get("details_url_call_log").value(null);
        details_url_bill = s.get("details_url_bill").value(null);
        details_url_statistic = s.get("details_url_statistic").value(null);
        details_url_all = s.get("details_url_all").value(null);
        type = s.get("type").value(0);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
	}
	
	public IBinder clone()
	{
		return new PandaOperatorTotalModel();
	}
}