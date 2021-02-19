package org.noear.sponge.admin.dso;

import org.noear.water.utils.IDUtils;

import java.util.Random;
import java.util.UUID;

public class IDUtil {
    public static String getAppSecretkey() {
        char[] chars = new char[16];
        int i = 0;
        while (i < 16) {
            int f = (int) (Math.random() * 3);
            if (f == 0)
                chars[i] = (char) ('A' + Math.random() * 26);
            else if (f == 1)
                chars[i] = (char) ('a' + Math.random() * 26);
            else
                chars[i] = (char) ('0' + Math.random() * 10);
            i++;
        }
        return new String(chars);
    }

    public static String buildGuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static String getCodeByID(long id) {
        long code = id;
        long key = 999999999;

        code = code + key;
        code = code - (key / 100);
        code = code + (key / 10000);
        code = code - (key / 1000000);
        code = code + 1;

        return Long.toString(code,36);
    }

    public String getIDByCode(String code){
        return "";
    }

    //
    //
    //==================================================
    //
    //
    public static long buildUaID(){
        return getID("ua_id") + 10000;
    }

    public static long buildIpID(){
        return getID("ip_id") + 20000;
    }

    public static long buildUrlID(){
        return getID("url_id") + 1000000;
    }

    public static long buildLogID(String tag) {
        return getID(tag) + 1000000;
    }

    private static long getID1(String tag) {
        long max = 1000000;

        return buildID(tag, max);
    }

    private static long buildID(String tag,long startIndex) {
        return IDUtils.newID("BEAUTY_ID", tag) + startIndex;
    }


    public static long getID(String tag) {
        return IDUtils.newID("SPONGE_ID", tag);
    }


    private static long getByTime(){
        return System.currentTimeMillis() * 10000 + new Random(1000).nextInt();
    }

}
