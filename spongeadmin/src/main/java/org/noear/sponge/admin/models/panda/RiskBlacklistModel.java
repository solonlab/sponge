package org.noear.sponge.admin.models.panda;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.util.*;

/// <summary>
/// 生成:2019/05/28 03:57:03
/// 
/// </summary>
@Data
public class RiskBlacklistModel implements IBinder
{
    public long row_id;
    public String mobile;
    public String id_code;
    public String id_name;
    public int from_agroup_id;
    public int agroup_id;
    public int reason_type;
    public Date log_fulltime;
    public int log_date;
    public String agroup_name;
    public String from_agroup_name;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        row_id = s.get("row_id").value(0L);
        mobile = s.get("mobile").value(null);
        id_code = s.get("id_code").value(null);
        id_name = s.get("id_name").value(null);
        from_agroup_id = s.get("from_agroup_id").value(0);
        agroup_id = s.get("agroup_id").value(0);
        reason_type = s.get("reason_type").value(0);
        log_fulltime = s.get("log_fulltime").value(null);
        log_date = s.get("log_date").value(0);

        agroup_name = s.get("agroup_name").value(null);
        from_agroup_name = s.get("from_agroup_name").value(null);
	}
	
	public IBinder clone()
	{
		return new RiskBlacklistModel();
	}

	public String getReason(){
	    String name = "";
	    switch (reason_type){
            case 1 : name = "调单";break;
            case 2 : name = "小贷";break;
            case 3 : name = "风控";break;
            case 4 : name = "用户要求";break;
            case 0 : name = "全部";break;
            default:break;
        }
        return name;
    }
}