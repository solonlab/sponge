package org.noear.sponge.admin.models.sponge_track;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.math.BigDecimal;

/**
 * @Author:Fei.chu
 * @Date:Created in 15:26 2019/06/06
 * @Description:
 */
@Data
public class StatUaClientDatePvUvIpModel implements IBinder {

    /**  */
    /**  */
    public long url_id;
    /**  */
    public int tag_id;
    /** 客户端。0=未知；1001支付宝，1002微信 */
    public int ua_client;
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
    /** 管理组（同一组内可见） */
    public int admin_group;

    public long _val;

    public void bind(GetHandlerEx s) {
        url_id = s.get("url_id").value(0l);
        tag_id = s.get("tag_id").value(0);
        ua_client = s.get("ua_client").value(0);
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
        admin_group = s.get("admin_group").value(0);

        _val = s.get("_val").longValue(0);
    }

    public IBinder clone() {
        return new StatUaClientDatePvUvIpModel();
    }

}
