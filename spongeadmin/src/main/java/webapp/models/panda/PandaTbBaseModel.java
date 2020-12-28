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
public class PandaTbBaseModel implements IBinder
{
    public long open_id;
    public String user_name;
    public String email;
    public int user_level;
    public String nick_name;
    public String name;
    public int gender;
    public String mobile;
    public int vip_count;
    public int create_date;
    public Date create_fulltime;
    public int update_date;
    public Date update_fulltime;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        open_id = s.get("open_id").value(0L);
        user_name = s.get("user_name").value(null);
        email = s.get("email").value(null);
        user_level = s.get("user_level").value(0);
        nick_name = s.get("nick_name").value(null);
        name = s.get("name").value(null);
        gender = s.get("gender").value(0);
        mobile = s.get("mobile").value(null);
        vip_count = s.get("vip_count").value(0);
        create_date = s.get("create_date").value(0);
        create_fulltime = s.get("create_fulltime").value(null);
        update_date = s.get("update_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
	}
	
	public IBinder clone()
	{
		return new PandaTbBaseModel();
	}
}