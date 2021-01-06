package org.noear.sponge.admin.dso;

import org.noear.weed.cache.ICacheServiceEx;
import org.noear.sponge.admin.Config;

public class CacheUtil {
   public static boolean isUsingCache = true;

   public static ICacheServiceEx dataCache = Config.cfg("rock_cache").getCh(Config.water_service_name + "_DATA_CACHE", 60 * 5); // new EmptyCache();

   public static void clearByTag(String tag) {
      dataCache.clear(tag);
   }

   public static <T> void updateByTag(String tag, org.noear.weed.ext.Fun1<T, T> setter) {
      dataCache.update(tag, setter);
   }

}
