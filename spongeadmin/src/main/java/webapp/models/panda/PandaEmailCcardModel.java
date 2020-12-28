package webapp.models.panda;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.util.Date;

/// <summary>
/// 生成:2019/01/28 11:47:19
/// 
/// </summary>
@Data
public class PandaEmailCcardModel implements IBinder
{
    public long row_id;
    public long open_id;
    public String email_id;
    public String email;
    public String bills_all;
    public String bills_month12;
    public String data_all;
    public String report_data;
    public int is_delete;
    public Date create_fulltime;
    public int create_date;
    public Date update_fulltime;
    public int update_date;

    public String hide_email;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        row_id = s.get("row_id").value(0L);
        open_id = s.get("open_id").value(0L);
        email_id = s.get("email_id").value(null);
        email = s.get("email").value(null);
        bills_all = s.get("bills_all").value(null);
        bills_month12 = s.get("bills_month12").value(null);
        data_all = s.get("data_all").value(null);
        report_data = s.get("report_data").value(null);
        is_delete = s.get("is_delete").value(0);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
	}
	
	public IBinder clone()
	{
		return new PandaEmailCcardModel();
	}
}