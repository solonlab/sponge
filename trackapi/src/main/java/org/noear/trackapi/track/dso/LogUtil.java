package org.noear.trackapi.track.dso;

import org.noear.water.WaterClient;
import org.noear.water.log.Level;
import org.noear.water.utils.ThrowableUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/**
 * Created by xun on 17/5/2.
 */
public class LogUtil {


    public static void error(String tag, Throwable ex) {
        error(tag, "", "", "", ex);
    }

    public static void error(String tag, String tag1, String tag2,String label , Throwable ex) {
        StringBuilder sb = new StringBuilder();
        sb.append(ex.toString()).append("\r\n").append(ThrowableUtils.getString(ex));

        WaterClient.Log.append("sponge_log_track", Level.ERROR, tag, tag1, tag2, label, sb.toString());

        System.out.print(tag + "::\r\n");
        System.out.print(sb.toString());
        System.out.print("\r\n");
    }

    public static void error(String tag, String tag1, String label , String txt) {
        StringBuilder sb = new StringBuilder();
        sb.append(txt);

        WaterClient.Log.append("sponge_log_track", Level.ERROR,  tag, tag1, label, sb.toString());

        System.out.print(tag + "::\r\n");
        System.out.print(sb.toString());
        System.out.print("\r\n");
    }


    public static void debug(String tag, String tag1, String tag2, String label , String txt) {
        StringBuilder sb = new StringBuilder();
        sb.append(txt);

        WaterClient.Log.append("sponge_log_track", Level.DEBUG, tag, tag1, tag2, label, sb.toString());

        System.out.print(tag + "::\r\n");
        System.out.print(sb.toString());
        System.out.print("\r\n");
    }

    public static void output(String tag, String tag1, String tag2, String label , String txt) {
        StringBuilder sb = new StringBuilder();
        sb.append(txt);

        WaterClient.Log.append("sponge_log_track", Level.TRACE, tag, tag1, tag2, label, sb.toString());

        System.out.print(tag + "::\r\n");
        System.out.print(sb.toString());
        System.out.print("\r\n");
    }

    public static String getFullStackTrace(Throwable e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(baos));
        return baos.toString();
    }

}
