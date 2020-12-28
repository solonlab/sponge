package org.noear.sponge.admin.models.panda;

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
public class UserExIdentificationModel implements IBinder
{
    public long open_id;
    public String id_code;
    public String id_name;
    public String id_address;
    public String id_date_start;
    public String id_date_end;
    public String id_nation;
    public String id_police;
    public int id_sex;
    public int id_birthday;
    public String card_img_url;
    public String card_back_img_url;
    public Date create_fulltime;
    public int create_date;
    public Date update_fulltime;
    public int update_date;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        open_id = s.get("open_id").value(0L);
        id_code = s.get("id_code").value(null);
        id_name = s.get("id_name").value(null);
        id_address = s.get("id_address").value(null);
        id_date_start = s.get("id_date_start").value(null);
        id_date_end = s.get("id_date_end").value(null);
        id_nation = s.get("id_nation").value(null);
        id_police = s.get("id_police").value(null);
        id_sex = s.get("id_sex").value(0);
        id_birthday = s.get("id_birthday").value(0);
        card_img_url = s.get("card_img_url").value(null);
        card_back_img_url = s.get("card_back_img_url").value(null);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
	}
	
	public IBinder clone()
	{
		return new UserExIdentificationModel();
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

    public String hideIdCode(String id_code) {
        if (!TextUtils.isEmpty(id_code)) {
            id_code = id_code.substring(0,6)+"************";
        }
        return id_code;
    }

    public String hasCardImgUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return "无";
        } else {
            return "有";
        }
    }

    public String hideAddress(String address) {
        if (!TextUtils.isEmpty(address)) {
            int len = address.length();
            address = address.substring(0,len/4*2)+"****";
        }
        return address;
    }
}