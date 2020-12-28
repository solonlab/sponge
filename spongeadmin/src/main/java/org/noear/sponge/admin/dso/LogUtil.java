package org.noear.sponge.admin.dso;

import org.noear.solon.core.handle.Context;
import org.noear.water.WaterClient;
import org.noear.water.log.Level;

/**
 * Created by xun on 17/5/2.
 */
public class LogUtil {

    public static void LogDebug(String cmd, String args, Context data) {
        StringBuilder sb = new StringBuilder();

        data.paramMap().forEach((k, v) -> {
            sb.append(k).append("=").append(v).append("; ");
        });

        LogDebug(cmd, args, sb.toString());
    }

    public static void LogDebug(String cmd, String args, String data) {
        WaterClient.Log.append("sponge_log_admin", Level.DEBUG, cmd, args, data);
    }

    public static void error(String tag, String label, Throwable ex) {
        WaterClient.Log.append("sponge_log_admin", Level.ERROR, tag, label, ex);
    }

    public static void error(String tag, String tag2, String label, Throwable ex) {

        WaterClient.Log.append("sponge_log_admin", Level.ERROR, tag, tag2, label, ex);
    }
}
