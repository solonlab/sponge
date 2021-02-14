package org.noear.trackapi.utils;

import org.noear.solon.core.handle.Context;
import org.noear.water.utils.TextUtils;

public class IpUtil {

    public static String getIP(Context context){
        String ip =  context.header("RemoteIp");

        if (TextUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = context.header("X-Forwarded-For");
        }

        if (TextUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = context.header("Proxy-Client-IP");
        }

        if (TextUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = context.header("WL-Proxy-Client-IP");
        }

        return ip;
    }
}
