package org.noear.sponge.admin.models.panda;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;
import org.apache.http.util.TextUtils;

import java.util.Date;

/// <summary>
/// 生成:2018/08/27 05:22:23
/// 
/// </summary>
@Data
public class PandaOperatorBaseModel implements IBinder
{
    public long open_id;
    public String mobile;
    public String id_card;
    public String name;
    public int state;
    public int phone_num_type;
    public Date create_fulltime;
    public int create_date;
    public Date update_fulltime;
    public int update_date;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        open_id = s.get("open_id").value(0L);
        mobile = s.get("mobile").value(null);
        id_card = s.get("id_card").value(null);
        name = s.get("name").value(null);
        state = s.get("state").value(0);
        phone_num_type = s.get("phone_num_type").value(0);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
	}
	
	public IBinder clone()
	{
		return new PandaOperatorBaseModel();
	}

    public String getState() {
        String stateName = "";
        switch (state){
            case 0:stateName = "正常";
                break;
            case 1:stateName = "冻结";
                break;
            case 2:stateName = "欠费";
                break;
            case 3:stateName = "停机";
                break;
            case 4:stateName = "销户";
                break;
            case 5:stateName = "未激活";
                break;
            case 6:stateName = "未知";
                break;
            default:break;
        }
        if (open_id == 0) {
            stateName = "";
        }
        return stateName;
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
}