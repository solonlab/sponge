package org.noear.sponge.admin.dso;

import org.noear.solon.cloud.CloudLogger;
import org.noear.solon.core.handle.Context;
import org.noear.mlog.utils.*;

public class LogUtil {

    private static CloudLogger logger = CloudLogger.get("sponge_log_admin");

    public static void LogDebug(String cmd, String args, Context data) {
        StringBuilder sb = new StringBuilder();

        data.paramMap().forEach((k, v) -> {
            sb.append(k).append("=").append(v).append("; ");
        });

        LogDebug(cmd, args, sb.toString());
    }

    public static void LogDebug(String cmd, String args, String data) {
        logger.debug(Tags.tag0(cmd), "{}\n\n{}", args, data);
        //WaterClient.Log.append("sponge_log_admin", Level.DEBUG, cmd, args, data);
    }

    public static void error(String tag, String label, Throwable ex) {
        logger.error(Tags.tag0(tag), "{}\n\n{}", label, ex);
        //WaterClient.Log.append("sponge_log_admin", Level.ERROR, tag, label, ex);
    }

    public static void error(String tag, String tag1, String label, Throwable ex) {
        logger.error(Tags.tag0(tag).tag1(tag1), "{}\n\n{}", label, ex);
        //WaterClient.Log.append("sponge_log_admin", Level.ERROR, tag, tag1, label, ex);
    }
}
