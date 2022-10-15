package org.noear.sponge.admin.model.sponge_track;

import lombok.Data;
import org.noear.wood.GetHandlerEx;
import org.noear.wood.IBinder;

import java.math.BigDecimal;

/**
 * @Author:Fei.chu
 * @Date:Created in 16:15 2019/06/05
 * @Description:
 */
@Data
public class StatUaPlatformDatePvUvIpModel  implements IBinder {

    /**  */
    /**  */
    public long url_id;
    /**  */
    public int tag_id;
    /** 平台。0=未知；101=IPhone；102=iPad；111=Android；201=Win；211=Mac */
    public int ua_platform;
    /**  */
    public long uv;
    /**  */
    public long pv;
    /**  */
    public long ip;
    /**  */
    public long uk;
    /** 汇总url的uv，仅用于tag */
    public long uv2;
    /** yyyyMMdd */
    public int log_date;

    public long _val;

    public void bind(GetHandlerEx s) {
        url_id = s.get("url_id").value(0l);
        tag_id = s.get("tag_id").value(0);
        ua_platform = s.get("ua_platform").value(0);
        try{
            uv = s.get("uv").value(BigDecimal.ZERO).longValue();
        } catch (Exception e){
            uv = s.get("uv").value(0).longValue();
        }
        try{
            pv = s.get("pv").value(BigDecimal.ZERO).longValue();
        } catch (Exception e){
            pv = s.get("pv").value(0).longValue();
        }
        try{
            ip = s.get("ip").value(BigDecimal.ZERO).longValue();
        } catch (Exception e){
            ip = s.get("ip").value(0).longValue();
        }
        try{
            uv2 = s.get("uv2").value(BigDecimal.ZERO).longValue();
        } catch (Exception e){
            uv2 = s.get("uv2").value(0).longValue();
        }

        uk = s.get("uk").value(0l);
        log_date = s.get("log_date").value(0);


        //附助用
        _val = s.get("_val").longValue(0);
    }

    public IBinder clone() {
        return new StatUaPlatformDatePvUvIpModel();
    }
}
