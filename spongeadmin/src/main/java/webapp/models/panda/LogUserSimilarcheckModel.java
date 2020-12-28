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
public class LogUserSimilarcheckModel implements IBinder
{
    public long log_id;
    public long open_id;
    public int agroup_id;
    public int ugroup_id;
    public String face_img;
    public String id_code;
    public String id_name;
    public double similarity;
    public Date create_fulltime;
    public int create_date;
    public Date update_fulltime;
    public int update_date;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        log_id = s.get("log_id").value(0L);
        open_id = s.get("open_id").value(0L);
        agroup_id = s.get("agroup_id").value(0);
        ugroup_id = s.get("ugroup_id").value(0);
        face_img = s.get("face_img").value(null);
        id_code = s.get("id_code").value(null);
        id_name = s.get("id_name").value(null);
        similarity = s.get("similarity").value(0d);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
	}
	
	public IBinder clone()
	{
		return new LogUserSimilarcheckModel();
	}
}