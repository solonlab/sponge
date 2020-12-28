package webapp.dso;

import org.apache.http.util.TextUtils;
import org.noear.solon.core.handle.Context;

/**
 * @Author:Fei.chu
 * @Date:Created in 13:41 2019/05/09
 * @Description:
 */
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
