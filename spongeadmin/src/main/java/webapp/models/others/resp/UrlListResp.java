package webapp.models.others.resp;

import lombok.Getter;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;
import webapp.models.sponge_track.ShortUrlModel;

import java.util.Date;
import java.util.List;

/**
 * @Author:Fei.chu
 * @Description: 跟踪列表返回对象
 */
@Getter
public class UrlListResp{
    public long url_id;
    public int tag_id;
    public String url_key;
    public String url_name;
    public String url_partner_key;
    public String url_val_md5;
    public String url_val;
    public String user_field;
    public String track_params;
    public String trans_params;
    public long url_redirect_count;
    public String note;
    public Date create_fulltime;
    public int admin_group;
    public String build_link;
    public List<CopyHrefResp> hrefList;

    public int has_track;

}
