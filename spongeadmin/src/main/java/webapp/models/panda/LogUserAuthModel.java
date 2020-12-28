package webapp.models.panda;

import lombok.Data;
import org.noear.weed.*;
import java.util.*;

/// <summary>
/// 生成:2019/06/17 02:19:04
/// 
/// </summary>
@Data
public class LogUserAuthModel implements IBinder
{
    public long row_id;
    public long open_id;
    public int agroup_id;
    public int type;
    public Date log_fulltime;
    public int log_date;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        row_id = s.get("row_id").value(0L);
        open_id = s.get("open_id").value(0L);
        agroup_id = s.get("agroup_id").value(0);
        type = s.get("type").value(0);
        log_fulltime = s.get("log_fulltime").value(null);
        log_date = s.get("log_date").value(0);
	}
	
	public IBinder clone()
	{
		return new LogUserAuthModel();
	}
}