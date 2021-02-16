package org.noear.sponge.admin;

import org.noear.solon.Solon;
import org.noear.water.model.ConfigM;
import org.noear.weed.DbContext;
import org.noear.water.*;

public class Config {
    public static String track_uri() {
        return cfg("track_uri").value;
    }

    public static DbContext sponge_track = cfg("sponge_track").getDb(true);
    public static DbContext sponge_rock = cfg("sponge_rock").getDb(true);

    public static final String push_suffix = "_push";

    public static void tryInit() {
        WaterClient.Config.getProperties("water_session").forEach((k, v) -> {
            if (Solon.cfg().isDebugMode()) {
                String key = k.toString();
                if (key.indexOf(".session.") < 0) {
                    Solon.cfg().put(k, v);
                }
            } else {
                Solon.cfg().put(k, v);
            }
        });
    }

    //================================
    //
    //获取一个数据库配置
    public static ConfigM cfg(String key) {
        return WaterClient.Config.get(Solon.cfg().appGroup(), key);
    }
}