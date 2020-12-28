package webapp.models.sponge_track;


import lombok.Getter;
import org.noear.weed.*;

import java.util.*;

/// <summary>
/// 生成:2017/12/05 10:24:43
/// 
/// </summary>
@Getter
public  class TrackTagModel implements IBinder
{
    public int tag_id;
    public int agroup_id;
    public String tag_name;
    public String tag_host;
    public String tag_access_key;
    public String t_user_field;
    public String t_track_params;
    public int t_track_params_num;
    public String t_trans_params;
    public String note;
    public int admin_group;
    public String t_build_link;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        tag_id = s.get("tag_id").value(0);
        agroup_id = s.get("agroup_id").value(0);
        tag_name = s.get("tag_name").value(null);
        tag_host = s.get("tag_host").value(null);
        tag_access_key = s.get("tag_access_key").value(null);
        t_user_field = s.get("t_user_field").value(null);
        t_track_params = s.get("t_track_params").value(null);
        t_track_params_num = s.get("t_track_params_num").value(0);
        t_trans_params = s.get("t_trans_params").value(null);
        note = s.get("note").value(null);
        admin_group = s.get("admin_group").value(0);
        t_build_link = s.get("t_build_link").value(null);
	}
	
	public IBinder clone()
	{
		return new TrackTagModel();
	}

}