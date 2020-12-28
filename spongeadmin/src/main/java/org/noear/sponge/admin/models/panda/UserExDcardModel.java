package org.noear.sponge.admin.models.panda;

import lombok.Data;
import org.noear.sponge.admin.utils.StringUtil;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.util.Date;

/// <summary>
/// 生成:2018/08/27 05:22:24
/// 
/// </summary>
@Data
public class UserExDcardModel implements IBinder
{
    public long dcard_id;
    public long open_id;
    public String holder_name;
    public String card_code;
    public String card_name;
    public String card_mobile;
    public long bank_id;
    public String bank_name;
    public String bank_branch;
    public String paysys_bank;
    public String id_code;
    public String card_img;
    public int is_main;
    public String icon;
    public int status;
    public Date create_fulltime;
    public int create_date;
    public Date update_fulltime;
    public int update_date;

    public void bind(GetHandlerEx s)
    {
        //1.source:数据源
        //
        dcard_id = s.get("dcard_id").value(0L);
        open_id = s.get("open_id").value(0L);
        holder_name = s.get("holder_name").value(null);
        card_code = s.get("card_code").value(null);
        card_name = s.get("card_name").value(null);
        card_mobile = s.get("card_mobile").value(null);
        bank_id = s.get("bank_id").value(0L);
        bank_name = s.get("bank_name").value(null);
        bank_branch = s.get("bank_branch").value(null);
        paysys_bank = s.get("paysys_bank").value(null);
        id_code = s.get("id_code").value(null);
        card_img = s.get("card_img").value(null);
        icon = s.get("icon").value(null);
        is_main = s.get("is_main").value(0);
        status = s.get("status").value(0);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
    }

    public IBinder clone()
    {
        return new UserExDcardModel();
    }

    public String isMainCard() {
        String main = "主卡";
        switch (is_main){
            case 1:main="主卡";break;
            case 0:main="次卡";break;
            default:break;
        }
        return main;
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