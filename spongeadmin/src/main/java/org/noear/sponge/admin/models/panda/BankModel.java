package org.noear.sponge.admin.models.panda;

import lombok.Data;
import org.noear.weed.*;

/// <summary>
/// 生成:2019/03/26 10:18:30
/// 
/// </summary>
@Data
public class BankModel implements IBinder
{
    public long bank_id;
    public String code;
    public String name;
    public int type;
    public String bank_alias;
    public String logo_colourless;
    public String logo_color;
    public String sc_url;
    public String sc_phone_desc;
    public String sc_phone;
    public String act_sms_desc;
    public String act_sms;
    public String act_sms_template;
    public String act_phone_desc;
    public String act_phone;
    public String background_img;
    public int is_cash_support;
    public double cash_up_line;
    public double cash_down_line;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        bank_id = s.get("bank_id").value(0L);
        code = s.get("code").value(null);
        name = s.get("name").value(null);
        type = s.get("type").value(0);
        bank_alias = s.get("bank_alias").value(null);
        logo_colourless = s.get("logo_colourless").value(null);
        logo_color = s.get("logo_color").value(null);
        sc_url = s.get("sc_url").value(null);
        sc_phone_desc = s.get("sc_phone_desc").value(null);
        sc_phone = s.get("sc_phone").value(null);
        act_sms_desc = s.get("act_sms_desc").value(null);
        act_sms = s.get("act_sms").value(null);
        act_sms_template = s.get("act_sms_template").value(null);
        act_phone_desc = s.get("act_phone_desc").value(null);
        act_phone = s.get("act_phone").value(null);
        background_img = s.get("background_img").value(null);
        is_cash_support = s.get("is_cash_support").value(0);
        cash_up_line = s.get("cash_up_line").value(0d);
        cash_down_line = s.get("cash_down_line").value(0d);
	}
	
	public IBinder clone()
	{
		return new BankModel();
	}
}