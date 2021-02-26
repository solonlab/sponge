package org.noear.sponge.admin.dso;

import org.noear.solon.core.handle.Context;
import org.noear.solon.logging.utils.TagsMDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    static Logger logger = LoggerFactory.getLogger("sponge_log_admin");

    public static void LogDebug(String cmd, String args, Context data) {
        StringBuilder sb = new StringBuilder();

        data.paramMap().forEach((k, v) -> {
            sb.append(k).append("=").append(v).append("; ");
        });

        LogDebug(cmd, args, sb.toString());
    }

    public static void LogDebug(String cmd, String args, String data) {
        TagsMDC.tag0(cmd);

        logger.debug("{}\r\n{}", args, data);
        //WaterClient.Log.append("sponge_log_admin", Level.DEBUG, cmd, args, data);
    }

    public static void error(String tag, String label, Throwable ex) {
        TagsMDC.tag0(tag);
        logger.error("{}\r\n{}", label, ex);
        //WaterClient.Log.append("sponge_log_admin", Level.ERROR, tag, label, ex);
    }

    public static void error(String tag, String tag1, String label, Throwable ex) {
        TagsMDC.tag0(tag).tag1(tag1);

        logger.error("{}\r\n{}", label, ex);
        //WaterClient.Log.append("sponge_log_admin", Level.ERROR, tag, tag1, label, ex);
    }
}
