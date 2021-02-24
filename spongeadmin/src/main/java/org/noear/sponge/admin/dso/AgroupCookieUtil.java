package org.noear.sponge.admin.dso;

import org.noear.solon.core.handle.Context;

/**
 * @author noear 2021/2/24 created
 */
public class AgroupCookieUtil {
    public static int cookieGet() {
        return Integer.parseInt(Context.current().cookie("spongeadmin_agroup", "0"));
    }

    public static void cookieSet(int agroup_id) {
        Context.current().cookieSet("spongeadmin_agroup", String.valueOf(agroup_id));
    }
}
