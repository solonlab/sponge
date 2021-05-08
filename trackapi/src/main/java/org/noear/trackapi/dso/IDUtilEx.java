package org.noear.trackapi.dso;


import org.noear.solon.cloud.CloudClient;
import org.noear.water.utils.IDUtils;

import java.util.Random;
import java.util.UUID;

public class IDUtilEx {
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
        return CloudClient.id().generate();
        //return getID("ua_id") + 10000;
    }

    public static long buildIpID(){
        return CloudClient.id().generate();
        //return getID("ip_id") + 20000;
    }

    public static long buildUrlID(){
        return CloudClient.id().generate();
        //return getID("url_id") + 1000000;
    }

    public static long buildLogID(String tag) {
        return CloudClient.id().generate();
        //return getID(tag) + 1000000;
    }


//    public static long getID(String tag) {
//        try {
//            return IDUtils.newID("SPONGE_ID", tag, 3600 * 24 * 365);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//
//            LogUtil.error("IDUtil", "", "","getID", ex);
//
//            return getByTime();
//        }
//    }


//    private static long getByTime(){
//        return System.currentTimeMillis() * 10000 + new Random(1000).nextInt();
//    }

}
