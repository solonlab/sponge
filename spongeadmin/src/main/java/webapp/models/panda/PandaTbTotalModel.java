package webapp.models.panda;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.util.Date;

/// <summary>
/// 生成:2019/01/07 02:45:48
/// 
/// </summary>
@Data
public class PandaTbTotalModel implements IBinder
{
    public long open_id;
    public String detail_url_account;
    public String detail_url_base;
    public String detail_url_all;
    public int create_date;
    public Date create_fulltime;
    public int update_date;
    public Date update_fulltime;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        open_id = s.get("open_id").value(0L);
        detail_url_account = s.get("detail_url_account").value(null);
        detail_url_base = s.get("detail_url_base").value(null);
        detail_url_all = s.get("detail_url_all").value(null);
        create_date = s.get("create_date").value(0);
        create_fulltime = s.get("create_fulltime").value(null);
        update_date = s.get("update_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
	}
	
	public IBinder clone()
	{
		return new PandaTbTotalModel();
	}
}