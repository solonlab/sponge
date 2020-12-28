package org.noear.sponge.admin.models.panda;

import lombok.Data;
import org.noear.weed.*;
import java.util.*;

/// <summary>
/// 生成:2019/07/03 11:13:52
/// 
/// </summary>
@Data
public class LogUserOperationModel implements IBinder
{
    public long log_id;
    public long open_id;
    public String mobile;
    public int agroup_id;
    public int ugroup_id;
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
        log_id = s.get("log_id").value(0L);
        open_id = s.get("open_id").value(0L);
        mobile = s.get("mobile").value(null);
        agroup_id = s.get("agroup_id").value(0);
        ugroup_id = s.get("ugroup_id").value(0);
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

	public String getOperateName(){
	    String name = "";
	    switch (type){
            case 0:name="未知";break;
            case 1:name="注册";break;
            case 2:name="登录";break;
            case 3:name="登出";break;
            case 4:name="授信申请";break;
            case 5:name="绑卡";break;
            case 6:name="解绑";break;
            case 7:name="注销";break;
            case 8:name="提现";break;
            case 9:name="支付/消费";break;
            case 10:name="更改手机号码";break;
            case 11:name="套现";break;
            case 12:name="打开app";break;
            default:name="未知";break;
        }
        return name;
    }
}