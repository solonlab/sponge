package webapp.models.others.resp;

import lombok.Getter;
import lombok.Setter;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;
import webapp.models.sponge_track.ShortUrlExStatModel;

/**
 * @Author:Fei.chu
 * @Description:
 */
@Getter
public class UrlStatResp implements IBinder {
    public long url_id;
    public int tag_id;
    public int admin_group;
    public long uv_total;
    public long pv_total;
    public long ip_total;
    public long uv_today;
    public long pv_today;
    public long ip_today;
    public long uv_yesterday;
    public long pv_yesterday;
    public long ip_yesterday;
    public String url_name;
    public String track_params;
    public int has_track;

    public void bind(GetHandlerEx s) {
        //1.source:数据源
        //
        url_id = s.get("url_id").value(0L);
        tag_id = s.get("tag_id").value(0);
        admin_group = s.get("admin_group").value(0);
        uv_total = s.get("uv_total").value(0L);
        pv_total = s.get("pv_total").value(0L);
        ip_total = s.get("ip_total").value(0L);
        uv_today = s.get("uv_today").value(0L);
        pv_today = s.get("pv_today").value(0L);
        ip_today = s.get("ip_today").value(0L);
        uv_yesterday = s.get("uv_yesterday").value(0L);
        pv_yesterday = s.get("pv_yesterday").value(0L);
        ip_yesterday = s.get("ip_yesterday").value(0L);
        url_name = s.get("url_name").value(null);
        track_params = s.get("track_params").value(null);
        has_track = s.get("has_track").value(0);
    }

    public IBinder clone() {
        return new UrlStatResp();
    }

}
