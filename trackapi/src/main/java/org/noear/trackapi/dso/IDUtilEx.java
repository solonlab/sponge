package org.noear.trackapi.dso;


import org.noear.solon.cloud.CloudClient;
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
    }

    public static long buildIpID(){
        return CloudClient.id().generate();
    }
}
