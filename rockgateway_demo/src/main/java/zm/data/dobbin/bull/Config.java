package zm.data.dobbin.bull;

import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.core.cache.CacheService;
import org.noear.water.WaterClient;
import org.noear.water.model.ConfigM;
import org.noear.water.solon_plugin.CacheUtils;
import org.noear.water.utils.RedisX;
import org.noear.weed.cache.ICacheServiceEx;
import org.noear.weed.cache.LocalCache;
import org.noear.weed.cache.SecondCache;

import javax.sql.DataSource;

@Configuration
public class Config {
    //静态配置
    //
    public static final int agroupId = 6;

    public static final String water_config_tag = "dobbin";

    public static ConfigM cfg(String key) {
        return WaterClient.Config.get(water_config_tag, key);
    }

    public static final RedisX rd_pepper_18 = cfg("dobbin_redis").getRd(18);

    public static final String group_name = "BULL";

    //动态配置

    /**
     * 数据库
     * @return
     */
    @Bean(value = "dobbin", typed = true)
    public DataSource db1() {
        return cfg("dobbin").getDs(true);
    }

    /**
     * 数据库
     * @return
     */
    @Bean(value = "sponge_sugar", typed = true)
    public DataSource db2() {
        return cfg("sponge_sugar").getDs(true);
    }

    @Bean(value = "cache_bull", typed = true)
    public CacheService getCacheDobbin() {

        ICacheServiceEx cache_remote = cfg("dobbinapi_cache").getCh("DATA_CACHE", 60 * 5);
        ICacheServiceEx cache_local = new LocalCache(60 * 5);
        ICacheServiceEx cache = new SecondCache(cache_local, cache_remote).nameSet("cache_bull");

        return CacheUtils.wrap(cache);
    }
}
