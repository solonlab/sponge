package org.noear.sponge.admin.dso;

import org.noear.water.utils.IDUtils;

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


    //
    //==================================================
    //
    public static long buildUrlID(){
        return getID("url_id") + 1000000;
    }

    public static long getID(String tag) {
        return IDUtils.newID("SPONGE_ID", tag);
    }
}
