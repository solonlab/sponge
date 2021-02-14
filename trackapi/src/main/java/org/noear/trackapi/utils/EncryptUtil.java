package org.noear.trackapi.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * Created by Mazexal on 2017/4/25.
 */
public class EncryptUtil {
    public static String md5(String str)
    {
        String s = null;
        try {
            byte[] data = str.getBytes(StandardCharsets.UTF_8);

            s = DigestUtils.md5Hex(data);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return s;
    }

    public static String sha1(String str){
        String s = null;
        try {
            byte[] data = str.getBytes(StandardCharsets.UTF_8);

            s = DigestUtils.sha1Hex(data);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return s;
    }
}