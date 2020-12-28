package org.noear.sponge.admin.models.panda;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;
import org.noear.sponge.admin.utils.StringUtil;

import java.util.Date;

/// <summary>
/// 生成:2018/08/27 05:22:24
/// 
/// </summary>
@Data
public class UserExCcardModel implements IBinder
{
    public long ccard_id;
    public long open_id;
    public String holder_name;
    public String card_code;
    public String card_name;
    public String card_mobile;
    public String bank_name;
    public String bank_branch;
    public String id_code;
    public String expiration_date;
    public int quota;
    public int date_bill;
    public int date_due;
    public int status;
    public Date date_end;
    public int examine_state;
    public String icon;
    public long bank_id;
    public String remark;
    public Date create_fulltime;
    public int create_date;
    public Date update_fulltime;
    public int update_date;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        ccard_id = s.get("ccard_id").value(0L);
        open_id = s.get("open_id").value(0L);
        holder_name = s.get("holder_name").value(null);
        card_code = s.get("card_code").value(null);
        card_name = s.get("card_name").value(null);
        card_mobile = s.get("card_mobile").value(null);
        bank_name = s.get("bank_name").value(null);
        bank_branch = s.get("bank_branch").value(null);
        id_code = s.get("id_code").value(null);
        expiration_date = s.get("expiration_date").value(null);
        quota = s.get("quota").value(0);
        date_bill = s.get("date_bill").value(0);
        date_due = s.get("date_due").value(0);
        status = s.get("status").value(0);
        date_end = s.get("date_end").value(null);
        examine_state = s.get("examine_state").value(0);
        icon = s.get("icon").value(null);
        bank_id = s.get("bank_id").value(0L);
        remark = s.get("remark").value(null);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
	}
	
	public IBinder clone()
	{
		return new UserExCcardModel();
	}

    public String statusName() {
        String statusName = "废弃";
        switch (status){
            case 1:statusName="正常";break;
            case 0:statusName="废弃";break;
            default:break;
        }
        return statusName;
    }

    public String getHideIdName() {
        return StringUtil.hideName(holder_name);
    }

    public String getHideIdCode(){
        return StringUtil.getHideIdCode(id_code);
    }

    public String getHideCardCode(){
        return StringUtil.getHideBankCardCode(card_code);
    }
}