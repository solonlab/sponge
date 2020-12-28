package org.noear.sponge.admin.models.sponge_angel;

import lombok.Getter;
import org.noear.weed.*;
import org.apache.http.util.TextUtils;

import java.util.*;

/// <summary>
/// 生成:2018/01/18 04:48:53
/// 
/// </summary>
@Getter
public class UserExLinkmanModel implements IBinder
{
    public long open_id;
    public String contact_e1_name;
    public String contact_e1_mobile;
    public String contact_e2_name;
    public String contact_e2_mobile;
    public int contact_e1_type;
    public int contact_e2_type;
    public Date create_fulltime;
    public int create_date;
    public Date update_fulltime;
    public int update_date;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        open_id = s.get("open_id").value(0L);
        contact_e1_name = s.get("contact_e1_name").value(null);
        contact_e1_mobile = s.get("contact_e1_mobile").value(null);
        contact_e2_name = s.get("contact_e2_name").value(null);
        contact_e2_mobile = s.get("contact_e2_mobile").value(null);
        contact_e1_type = s.get("contact_e1_type").value(0);
        contact_e2_type = s.get("contact_e2_type").value(0);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
	}
	
	public IBinder clone()
	{
		return new UserExLinkmanModel();
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

    public String hideMobile(String mobile) {
	    if (!TextUtils.isEmpty(mobile)) {
	        mobile = mobile.substring(0,3)+"********";
        }
        return mobile;
    }
}