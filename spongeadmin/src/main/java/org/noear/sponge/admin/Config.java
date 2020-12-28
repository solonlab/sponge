package org.noear.sponge.admin;

import org.noear.solon.Solon;
import org.noear.water.model.ConfigM;
import org.noear.weed.DbContext;
import org.noear.water.*;

public class Config {
    public static String web_title = "SPONGE";
    public static String water_config_tag = "sponge";

    public static String water_service_name = "spongeadmin";

    public static String track_uri() {
        return cfg("track_uri").value;
    }

    public static String raas_uri = cfg("raas_uri").value;
    public static String water_uri = cfg("water_uri").value;

    public static DbContext sponge_track = cfg("sponge_track").getDb(true);
    public static DbContext sponge_angel = cfg("sponge_angel").getDb(true);
    public static DbContext sponge = cfg("sponge").getDb(true);
    public static DbContext water = cfg("water").getDb(true);
    public static DbContext panda = cfg("panda").getDb(true);

    public static DbContext rock = cfg("rock").getDb(true);

    public static String sponge_url = cfg("sponge_url").value;

    /*相关状态控制*/
    public static final Boolean is_debug = "1".equals(cfg("is_debug").value);
    public static boolean is_test_env = "1".equals(cfg("is_test_env").value);


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
        return WaterClient.Config.get(water_config_tag, key);
    }
}