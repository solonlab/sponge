package webapp.models.others.resp;

import lombok.Getter;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

/**
 * @Author:Fei.chu
 * @Description:
 */
@Getter
public class TrackTagVIResp implements IBinder {
    public String params;
    public int vi;

    public void bind(GetHandlerEx s)
    {
        //1.source:数据源
        params = s.get("params").value(null);
        vi = s.get("vi").value(0);
    }

    public IBinder clone()
    {
        return new TrackTagVIResp();
    }
}
