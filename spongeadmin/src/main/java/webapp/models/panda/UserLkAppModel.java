package webapp.models.panda;

import lombok.Data;
import org.apache.http.util.TextUtils;
import org.noear.weed.*;
import java.util.*;

/// <summary>
/// 生成:2019/06/14 05:30:32
/// 
/// </summary>
@Data
public class UserLkAppModel implements IBinder
{
    public long app_linkid;
    public long open_id;
    public String mobile;
    public int app_id;
    public int ugroup_id;
    public int agroup_id;
    public int is_real;
    public String id_name;
    public int similar_times;
    public int register_date;
    public Date register_fulltime;
    public int real_date;
    public Date real_fulltime;
    public String platform;
    public String agroup_name;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        app_linkid = s.get("app_linkid").value(0L);
        open_id = s.get("open_id").value(0L);
        mobile = s.get("mobile").value(null);
        app_id = s.get("app_id").value(0);
        ugroup_id = s.get("ugroup_id").value(0);
        agroup_id = s.get("agroup_id").value(0);
        is_real = s.get("is_real").value(0);
        id_name = s.get("id_name").value(null);
        similar_times = s.get("similar_times").value(0);
        register_date = s.get("register_date").value(0);
        register_fulltime = s.get("register_fulltime").value(null);
        real_date = s.get("real_date").value(0);
        real_fulltime = s.get("real_fulltime").value(null);
        platform = s.get("platform").value("");
        agroup_name = s.get("agroup_name").value(null);
	}
	
	public IBinder clone()
	{
		return new UserLkAppModel();
	}

	public String getReal() {
	    String real = "";
	    switch (is_real){
            case 0:real = "未实名";break;
            case 1:real = "已实名";break;
            case 2:real = "待补全";break;
            default:break;
        }

        return real;
    }

    public String hideName(String name) {
        if (!TextUtils.isEmpty(name)) {
            int len = name.length();
            switch (len) {
                case 2:name = name.substring(0,1)+"*";break;
                default:name = name.substring(0,1)+"**";break;
            }
        }
        return name;
    }
}