package org.noear.sponge.admin.models.sponge_angel;

import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.util.Date;

/// <summary>
/// 生成:2018/01/08 02:20:50
/// 
/// </summary>
public class UserExDcardModel implements IBinder
{
    public long open_id;
    public String holder_name;
    public String card_code;
    public String card_name;
    public String card_mobile;
    public String bank_name;
    public String bank_branch;
    public String paysys_bank;
    public String id_code;
    public String icon;
    public Date create_fulltime;
    public int create_date;
    public Date update_fulltime;
    public int update_date;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        open_id = s.get("open_id").value(0L);
        holder_name = s.get("holder_name").value(null);
        card_code = s.get("card_code").value(null);
        card_name = s.get("card_name").value(null);
        card_mobile = s.get("card_mobile").value(null);
        bank_name = s.get("bank_name").value(null);
        bank_branch = s.get("bank_branch").value(null);
        paysys_bank = s.get("paysys_bank").value(null);
        id_code = s.get("id_code").value(null);
        icon = s.get("icon").value(null);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
	}
	
	public IBinder clone()
	{
		return new UserExDcardModel();
	}
}