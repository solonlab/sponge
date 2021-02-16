package org.noear.sponge.admin.dso;

import org.noear.solon.Solon;
import org.noear.weed.cache.ICacheServiceEx;
import org.noear.sponge.admin.Config;

public class CacheUtil {
   public static boolean isUsingCache = true;

   public static ICacheServiceEx dataCache = Config.cfg("rock_cache").getCh(Solon.cfg().appName() + "_DATA_CACHE", 60 * 5); // new EmptyCache();

   public static void clearByTag(String tag) {
      dataCache.clear(tag);
   }

   public static <T> void updateByTag(String tag, org.noear.weed.ext.Fun1<T, T> setter) {
      dataCache.update(tag, setter);
   }

}
