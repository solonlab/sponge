package org.noear.sponge.rock.impl;

import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.water.WaterClient;
import org.noear.water.log.WaterLogger;
import org.noear.water.model.ConfigM;
import org.noear.weed.DbContext;
import org.noear.weed.cache.ICacheServiceEx;

@Configuration
public class Config {

    @Bean("rock_db")
    public DbContext rock_db() {
        return cfg("rock").getDb(true);
    }

    @Bean("rock_cache")
    public ICacheServiceEx rock_cache() {
        return cfg("rock_cache").getCh2().nameSet("rock_cache");
    }

    @Bean("rock_log")
    public WaterLogger log() {
        return new WaterLogger("sponge_log_rock");
    }

    public ConfigM cfg(String name) {
        return WaterClient.Config.get("sponge", name);
    }
}
