package org.noear.sponge.track;

import org.noear.water.WaterClient;
import org.noear.water.model.ConfigM;
import org.noear.weed.DbContext;
import org.noear.weed.cache.ICacheServiceEx;

public class Config {
    public static String water_config_tag = "sponge";
    public static String water_service_name = "trackapi";

    public static String uv_cookie_key = "_0vjxxAfpPDjAs4ZT";

    public static DbContext sponge_track = cfg("sponge_track").getDb(true);

    public static ICacheServiceEx cache = cfg("rock_cache").getCh2().nameSet("cache");

    //向Water注册当前服务
    public static void tryInit() {
        sponge_track.initMetaData();
    }

    //获取一个数据库配置
    public static ConfigM cfg(String key) {
        return WaterClient.Config.get(water_config_tag, key);
    }
}
