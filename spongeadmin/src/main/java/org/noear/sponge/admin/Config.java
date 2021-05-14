package org.noear.sponge.admin;

import org.noear.solon.Solon;
import org.noear.solon.annotation.Configuration;
import org.noear.water.model.ConfigM;
import org.noear.weed.DbContext;
import org.noear.water.*;

@Configuration
public class Config {
    public static String track_uri() {
        return cfg("track_uri").value;
    }

    public static DbContext sponge_track = cfg("sponge_track").getDb(true);
    public static DbContext sponge_rock = cfg("sponge_rock").getDb(true);

    public static final String push_suffix = "_push";

    //是否使用标答检查器？
    public static boolean is_use_tag_checker() {
        return "1".equals(cfg("water","enable_tag_checker").getString());
    }


    //================================
    //
    //获取一个数据库配置
    public static ConfigM cfg(String key) {
        return cfg(Solon.cfg().appGroup(), key);
    }

    public static ConfigM cfg(String group, String key) {
        return WaterClient.Config.get(group, key);
    }
}