package webapp.models.sponge_angel;

import lombok.Getter;
import org.noear.weed.*;
import java.util.*;

/// <summary>
/// 生成:2018/01/19 05:09:52
/// 
/// </summary>
@Getter
public class LogUserOperationModel implements IBinder
{
    public long row_id;
    public long open_id;
    public int agroup_id;
    public int ugroup_id;
    public String mobile;
    public int type;
    public double lat;
    public double lng;
    public String ip;
    public long device_id;
    public String device_udid;
    public int log_date;
    public Date log_fulltime;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        row_id = s.get("row_id").value(0L);
        open_id = s.get("open_id").value(0L);
        agroup_id = s.get("agroup_id").value(0);
        ugroup_id = s.get("ugroup_id").value(0);
        mobile = s.get("mobile").value(null);
        type = s.get("type").value(0);
        lat = s.get("lat").value(0d);
        lng = s.get("lng").value(0d);
        ip = s.get("ip").value(null);
        device_id = s.get("device_id").value(0L);
        device_udid = s.get("device_udid").value(null);
        log_date = s.get("log_date").value(0);
        log_fulltime = s.get("log_fulltime").value(null);
	}
	
	public IBinder clone()
	{
		return new LogUserOperationModel();
	}
}