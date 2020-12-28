package webapp.models.panda;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;
import org.apache.http.util.TextUtils;

import java.util.Date;

/// <summary>
/// 生成:2018/08/27 05:22:24
/// 
/// </summary>
@Data
public class UserExBaseInfoModel implements IBinder
{
    public long open_id;
    public String weixin;
    public String qq;
    public int qualification;
    public int mstatus;
    public String address;
    public int city_code;
    public int house_status;
    public int house_type;
    public Date create_fulltime;
    public int create_date;
    public Date update_fulltime;
    public int update_date;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        open_id = s.get("open_id").value(0L);
        weixin = s.get("weixin").value(null);
        qq = s.get("qq").value(null);
        qualification = s.get("qualification").value(0);
        mstatus = s.get("mstatus").value(0);
        address = s.get("address").value(null);
        city_code = s.get("city_code").value(0);
        house_status = s.get("house_status").value(0);
        house_type = s.get("house_type").value(0);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
	}
	
	public IBinder clone()
	{
		return new UserExBaseInfoModel();
	}

    public String hideAddress(String address) {
        if (!TextUtils.isEmpty(address)) {
            int len = address.length();
            address = address.substring(0,len/4*3)+"****";
        }
        return address;
    }
}