package org.noear.sponge.admin.models.panda;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.util.Date;

/// <summary>
/// 生成:2019/04/25 05:31:34
/// 
/// </summary>
@Data
public class RiskReportOrderModel implements IBinder
{
    public long order_id;
    public String order_no;
    public String out_order_no;
    public long open_id;
    public String mobile;
    public int pay_type;
    public int type;
    public double pay_amount;
    public int status;
    public String remark;
    public Date create_fulltime;
    public int create_date;
    public Date finish_fulltime;
    public int finish_date;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        order_id = s.get("order_id").value(0L);
        order_no = s.get("order_no").value(null);
        out_order_no = s.get("out_order_no").value(null);
        open_id = s.get("open_id").value(0L);
        mobile = s.get("mobile").value(null);
        pay_type = s.get("pay_type").value(0);
        type = s.get("type").value(0);
        pay_amount = s.get("pay_amount").value(0d);
        status = s.get("status").value(0);
        remark = s.get("remark").value(null);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        finish_fulltime = s.get("finish_fulltime").value(null);
        finish_date = s.get("finish_date").value(0);
	}
	
	public IBinder clone()
	{
		return new RiskReportOrderModel();
	}

	public String getPayType(){
	    String payType = "";
	    switch (pay_type){
            case 1:payType = "支付宝";break;
            case 2:payType = "微信";break;
            default:break;
        }
        return payType;
    }

    public String getTypeStr(){
        String payType = "";
        switch (type){
            case 1:payType = "个人信用报告";break;
            case 2:payType = "运营商数据报告";break;
            default:break;
        }
        return payType;
    }

    public String getStatusStr(){
	    String statusStr = "";
        switch (status){
            case 1:statusStr = "待支付";break;
            case 2:statusStr = "支付成功";break;
            case 3:statusStr = "支付失败";break;
            case 4:statusStr = "支付失败";break;
            default:break;
        }
        return statusStr;
    }
}