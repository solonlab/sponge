package org.noear.trackapi.dso;

import org.noear.solon.logging.utils.TagsMDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xun on 17/5/2.
 */
public class LogUtil {
    static Logger log = LoggerFactory.getLogger("sponge_log_track");

    public static void error(String tag, String tag1, String tag2,String label , Throwable ex) {
        TagsMDC.tag0(tag);
        TagsMDC.tag1(tag1);
        TagsMDC.tag2(tag2);

        log.error("{}\r\n{}", label, ex);
    }

    public static void debug(String tag, String tag1, String tag2, String label , String txt) {
        TagsMDC.tag0(tag);
        TagsMDC.tag1(tag1);
        TagsMDC.tag2(tag2);

        log.debug("{}\r\n{}", label, txt);
    }
}
