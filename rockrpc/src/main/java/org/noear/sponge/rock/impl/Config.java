package org.noear.sponge.rock.impl;

import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.water.WaterClient;
import org.noear.water.model.ConfigM;
import org.noear.weed.DbContext;
import org.noear.weed.cache.ICacheServiceEx;

@Configuration
public class Config {

    @Bean("rock_db")
    public DbContext rock_db() {
        return cfg("sponge_rock").getDb(true);
    }

    @Bean("rock_cache")
    public ICacheServiceEx rock_cache() {
        return cfg("sponge_cache").getCh2().nameSet("rock_cache");
    }


    public ConfigM cfg(String name) {
        return WaterClient.Config.get("sponge", name);
    }
}
