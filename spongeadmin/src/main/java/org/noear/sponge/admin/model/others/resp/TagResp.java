package org.noear.sponge.admin.model.others.resp;

import lombok.Getter;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

/**
 * @Author:Fei.chu
 * @Description:
 */
@Getter
public class TagResp implements IBinder {
    public int tag_id;
    public int agroup_id;
    public String tag_name;
    public String note;
    public String t_user_field;
    public String t_track_params;
    public String t_trans_params;
    public int admin_group;

    public long pv_today;
    public long uv_today;
    public long ip_today;
    public long pv_yesterday;
    public long uv_yesterday;
    public long ip_yesterday;

    public void bind(GetHandlerEx s)
    {
        //1.source:数据源
        //
        tag_id = s.get("tag_id").value(0);
        agroup_id = s.get("agroup_id").value(0);
        tag_name = s.get("tag_name").value(null);
        t_trans_params = s.get("t_trans_params").value(null);
        t_user_field = s.get("t_user_field").value(null);
        t_track_params = s.get("t_track_params").value(null);
        note = s.get("note").value(null);
        admin_group = s.get("admin_group").value(0);
        pv_today = s.get("pv_today").value(0L);
        uv_today = s.get("uv_today").value(0L);
        ip_today = s.get("ip_today").value(0L);
        pv_yesterday = s.get("pv_yesterday").value(0L);
        uv_yesterday = s.get("uv_yesterday").value(0L);
        ip_yesterday = s.get("ip_yesterday").value(0L);
    }

    public IBinder clone()
    {
        return new TagResp();
    }

}
