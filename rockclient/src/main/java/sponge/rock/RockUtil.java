package sponge.rock;

import org.noear.solon.Solon;
import org.noear.water.WaterClient;
import org.noear.water.model.ConfigM;
import org.noear.water.model.MessageM;
import org.noear.water.utils.RedisX;
import org.noear.weed.cache.CacheTags;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class RockUtil {

    private static RedisX _redis_idx = null;
    private static RedisX _redis_uni = null;
    private static RedisX _redis_time = null;
    private static ReentrantLock run_lock = new ReentrantLock();

    //======================================
    //
    // redis相关的应用操作
    //

    public static long newID(String key, String field, int cacheTime) {
        tryInit();

        long val = _redis_idx.open1((ru) -> {
            return ru.key(key).expire(cacheTime).hashIncr(field, 1l);
        });


        return val;
    }

    //尝试把group_key锁给inMaster
    public static boolean tryLock(String group, String key, int inSeconds, String inMaster) {
        tryInit();

        String key2 = group + ".lk." + key;

        return _redis_uni.open1((ru) -> {
            if (ru.key(key2).exists() == false) {
                ru.key(key2).expire(inSeconds).lock(inMaster);
            }

            return inMaster.equals(ru.key(key2).get());
        });
    }

    public static boolean tryLock(String group, String key, int inSeconds) {
        tryInit();

        String key2 = group + ".lk." + key;

        return _redis_uni.open1((ru) -> {
            if (ru.key(key2).exists() == false) {
                return ru.key(key2).expire(inSeconds).lock("_");
            }else{
                return false;
            }
        });
    }

    //group_key是否已被锁定
    public static boolean isLocked(String group, String key) {
        tryInit();

        String key2 = group + ".lk." + key;

        return _redis_uni.open1((ru) -> {
            return ru.key(key2).exists();
        });
    }

    //解锁group_key
    public static void unLock(String group, String key){
        tryInit();

        String key2 = group+".lk." + key;

        _redis_uni.open0((ru) -> {
            ru.key(key2).delete();
        });
    }

    //一般用于业务唯一约束检查
    //
    public static boolean isUnique(String group, String key) {
        return isUnique(group, key, 3);
    }

    //@key     关键值
    //@seconds 有效时间（秒数）
    //return   是否在有效时间内唯一
    public static boolean isUnique(String group, String key, int inSeconds) {
        tryInit();

        String key2 = group + key;

        long val = _redis_uni.open1((ru) -> {
            if (ru.key(key2).exists()) {
                return 2l;
            } else {
                return ru.key(key2).expire(inSeconds).incr(1l);
            }
        });

        return val == 1;
    }

    private static ConfigM redis_cfg = null;
    public static RedisX redis(int db) {
        if(redis_cfg == null) {
            redis_cfg = WaterClient.Config.get("sponge", "rock_redis");
        }

        return redis(redis_cfg, db, 300);
    }

    public static RedisX redis(int db, int maxTotal) {
        if(redis_cfg == null) {
            redis_cfg = WaterClient.Config.get("sponge", "rock_redis");
        }

        return redis(redis_cfg, db, maxTotal);
    }

    public static RedisX redis(ConfigM cfg, int db, int maxTotal) {
        return new RedisX(cfg.getProp(), db, maxTotal);
    }


    //==================
    //
    private static void tryInit() {
        if (_redis_idx == null) {
            run_lock.lock();

            if(_redis_idx == null) {
                try {
                    _redis_idx = redis(1);
                    _redis_uni = redis(2);
                    _redis_time = redis(5);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            run_lock.unlock();
        }
    }

    //======================================
    //
    // 消息相关的操作
    //

    private static String msg_topic = "water.cache.update";

    protected static void sendMessage(String message) {
        try {
            WaterClient.Message.sendMessage(RockUtil.buildGuid(), msg_topic, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void subscribeTopic(String subscriber_key, String receiver_url, String access_key) throws Exception {
        WaterClient.Message.subscribeTopic(subscriber_key, receiver_url, access_key, "18658857337", 1, Solon.cfg().isDriftMode(), msg_topic);
    }

    public static boolean receiveMessage(MessageM msg) {
        if (msg_topic.equals(msg.topic) == false)
            return false;

        try {
            CacheTags cacheTags = RockClient.rock_cache.tags();
            String[] tag_keys = msg.message.split(";");

            //清掉缓存
            for (String tk : tag_keys) {
                cacheTags.clear(tk);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return true;
    }


    //======================================
    //
    // 生成各种KEY
    //

    public static String buildUkey(int ugroupID, String mobile) {
        return md5(ugroupID + "#" + mobile);
    }


    public static String buildAppkey() {
        char[] chars = new char[16];
        int i = 0;
        while (i < 16) {
            int f = (int) (Math.random() * 3);
            if (f == 0)
                chars[i] = (char) ('A' + Math.random() * 26);
            else if (f == 1)
                chars[i] = (char) ('a' + Math.random() * 26);
            else
                chars[i] = (char) ('0' + Math.random() * 10);
            i++;
        }
        return new String(chars);
    }

    public static String buildAkey() {
        return buildGuid();
    }

    public static String buildGuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String md5(String str) {
        String s = null;
        try {
            byte[] data = str.getBytes("UTF-8");

            s = DigestUtils.md5Hex(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return s;
    }
}
