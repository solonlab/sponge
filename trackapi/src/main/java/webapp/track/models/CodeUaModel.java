package webapp.track.models;

import org.noear.weed.*;

public class CodeUaModel implements IBinder {

    /**   */
    public long ua_id;
    /**  平台。0=未知；101=IPhone；102=iPad；111=Android；201=Win；211=Mac；221=linux */
    public int platform;
    /**  客户端。0=未知；1001=支付宝；1002=微信； */
    public int client;

    public void bind(GetHandlerEx s) {
        ua_id = s.get("ua_id").value(0l);
        platform = s.get("platform").value(0);
        client = s.get("client").value(0);
    }

    public IBinder clone() {
        return new CodeUaModel();
    }
}
