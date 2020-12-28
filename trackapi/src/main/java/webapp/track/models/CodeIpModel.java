package webapp.track.models;

import org.noear.weed.*;

public class CodeIpModel implements IBinder {

    /**   */
    public long ip_id;
    /**   */
    public int city_code;

    public void bind(GetHandlerEx s) {
        ip_id = s.get("ip_id").value(0l);
        city_code = s.get("city_code").value(0);
    }

    public IBinder clone() {
        return new CodeIpModel();
    }
}
