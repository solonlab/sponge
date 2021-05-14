package org.noear.sponge.admin.dso;

import org.noear.solon.core.handle.Context;
import org.noear.water.utils.TextUtils;

public class GroupUtil {

    //应用组cookie记忆处理
    public static int groupCookie(int out_agroup_id, int agroup_id) {
        if (out_agroup_id == 0) {
            String agroup = Context.current().cookie("agroup");

            if (!TextUtils.isEmpty(agroup) && !"null".equals(agroup)) {
                out_agroup_id = Integer.parseInt(agroup);
                Context.current().cookieSet("agroup", agroup, -1);
            }
        } else {
            Context.current().cookieSet("agroup", agroup_id + "", -1);
        }
        return out_agroup_id;
    }
}
