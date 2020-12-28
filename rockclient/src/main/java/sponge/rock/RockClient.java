package sponge.rock;

import org.noear.water.WaterClient;
import org.noear.water.model.ConfigM;
import org.noear.water.utils.RedisX;
import org.noear.weed.DbContext;
import org.noear.weed.cache.ICacheServiceEx;
import sponge.rock.models.*;
import sponge.rock.process.*;

import java.sql.SQLException;
import java.util.*;

public final class RockClient {
    protected static DbContext rock_db;
    protected static ICacheServiceEx rock_cache;

    //初始化...
    public static void tryInit(ICacheServiceEx cache) {
        rock_db = WaterClient.Config.get("sponge", "rock").getDb(true);
        rock_cache = cache;
    }

    public static void tryInit(ICacheServiceEx cache, DbContext db) {
        rock_db = db;
        rock_cache = cache;
    }

    public static DbContext db() {
        return rock_db;
    }

    //=========================

    //添加一个应用模型
    public static AppModel addApp(int agroupID, int ugroupID, String name) throws SQLException {
        String appKey = RockUtil.buildAppkey();
        String akey = RockUtil.buildAkey();

        long app_id = db().table("appx")
                .set("agroup_id", agroupID)
                .set("ugroup_id", ugroupID)
                .set("app_key", appKey)
                .set("akey", akey)
                .set("name", name)
                .insert();

        return db().table("appx").where("app_id=?", app_id).select("*").getItem(new AppModel());
    }

    //更新应用的名称
    public static void udpAppName(int appID, String name) throws SQLException {
        db().table("appx")
                .set("name", name)
                .where("app_id=?", appID)
                .update();

        delCacheByApp(appID);
    }

    //更新应用的审核状态
    public static void udpAppExamine(int appID, int isExamine, int examineVer) throws SQLException {
        db().table("appx")
                .set("ar_is_examine", isExamine)
                .set("ar_examine_ver", examineVer)
                .where("app_id=?", appID)
                .update();

        delCacheByApp(appID);
    }

    //获取一个应用模型
    public static AppModel getApp(int appID) throws SQLException {
        app_get_app sp = new app_get_app(rock_db);

        sp.app_id = appID;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_" + appID);

        return sp.getItem(new AppModel());
    }

    //获取一个应用模型
    public static AppModel getApp(String akey) throws SQLException {
        app_get_app sp = new app_get_app(rock_db);
        sp.akey = akey;
        sp.caching(rock_cache)
                .usingCache(60 * 10);

        return sp.getItem(new AppModel(), (cu, m) -> {
            cu.cacheTag("app_" + m.app_id);
        });
    }

    //可以只输入一个分组
    public static List<AppModel> getAppsByGroup(int agroupID, int ugroupID) throws Exception {
        if (agroupID < 1 && ugroupID < 1) {
            throw new Exception("请输入有效的 agroupID 或 ugroupID");
        }

        app_get_app_list sp = new app_get_app_list(rock_db);
        sp.agroup_id = agroupID;
        sp.ugroup_id = ugroupID;
        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_list_" + agroupID);

        return sp.getList(new AppModel());
    }

    //获取一个应用组模型
    public static AppGroupModel getAppGroup(int agroupID) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroupID");
        }

        app_get_agroup sp = new app_get_agroup(rock_db);

        sp.agroup_id = agroupID;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_group_" + agroupID);


        return sp.getItem(new AppGroupModel());
    }

    //获取一个用户组模型
    public static UserGroupModel getUserGroup(int ugroupID) throws Exception {
        if (ugroupID < 1) {
            throw new Exception("请输入有效的 ugroupID");
        }

        app_get_ugroup sp = new app_get_ugroup(rock_db);

        sp.ugroup_id = ugroupID;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("user_group_" + ugroupID);


        return sp.getItem(new UserGroupModel());
    }

    //=======================
    //获取一个应用设置集
    public static AppSettingCollection getAppSetting(int appID, int verStart, boolean isClientOnly) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 app_id");
        }

        app_ex_get_setting sp = new app_ex_get_setting(rock_db);
        sp.app_id = appID;
        sp.is_client_only = isClientOnly;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_setting_" + appID);

        List<AppSettingModel> tmp = sp.getList(new AppSettingModel());
        List<AppSettingModel> mod = new ArrayList<>();

        //版本控制
        for (AppSettingModel m : tmp) {
            if (verStart < m.ver_start) {
                continue;
            }
            mod.add(m);
        }

        AppSettingCollection set = new AppSettingCollection();
        set.bind(mod);
        return set;

    }

    //获取一个应用设置项
    public static AppSettingModel getAppSettingItem(int appID, String name) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

        app_ex_get_setting_item sp = new app_ex_get_setting_item(rock_db);
        sp.app_id = appID;
        sp.name = name;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_setting_" + appID);

        return sp.getItem(new AppSettingModel());
    }

    public static AppSettingModel getAppSettingItemNoCache(int appID, String name) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

        app_ex_get_setting_item sp = new app_ex_get_setting_item(rock_db);
        sp.app_id = appID;
        sp.name = name;

        return sp.getItem(new AppSettingModel());
    }

    //仅用于管理
    public static boolean delAppSettingItem(int appID, String name) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

        app_ex_del_setting_item sp = new app_ex_del_setting_item(rock_db);
        sp.app_id = appID;
        sp.name = name;

        boolean isOk = sp.execute() > 0;

        rock_cache.tags().clear("app_setting_by_name_" + name);

        delCacheByApp(appID);

        return isOk;
    }


    public static AppSettingCollection getAppSettingEx(int appID, int ver, boolean isClientOnly) throws Exception {
        int agroup_id = getApp(appID).agroup_id;

        return getAppSettingEx(agroup_id, appID, ver, isClientOnly);
    }

    //获取应用设置项 //已包函时间
    public static AppSettingCollection getAppSettingEx(int groupID, int appID, int ver, boolean isClientOnly) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

        AppSettingCollection sets = RockClient.getAppGroupSetting(groupID, ver, isClientOnly);

        AppSettingCollection temp = RockClient.getAppSetting(appID, ver, isClientOnly);

        for (String k : temp.keySet()) {
            AppSettingModel v = temp.get(k);

            if (v.value != null && v.value.length() > 0) {
                sets.put(k, v);
            }
        }

        return sets;
    }

    public static List<AppSettingModel> getAppSettingItemsByName(int agroupID, String name) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroupID");
        }

        app_ex_get_setting_item_by_name sp = new app_ex_get_setting_item_by_name(rock_db);
        sp.agroup_id = agroupID;
        sp.name = name;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_setting_by_name_" + name)
                .cacheTag("app_setting_by_name_" + agroupID);

        return sp.getList(new AppSettingModel());
    }

    //获取一个应用设置项 //如果没有去app_group_setting获取
    public static AppSettingModel getAppSettingItemEx(int appID, String name) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

        AppSettingModel item = getAppSettingItem(appID, name);
        if (item.row_id > 0) {
            return item;
        } else {
            int agroup_id = getApp(appID).agroup_id;
            return getAppGroupSettingItem(agroup_id, name);
        }
    }


    //设置一个应用设置项的值
    public static void setAppSettingItem(int appID, String name, int type, String value, int verStart, boolean isClient) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

        int agroup_id = getApp(appID).agroup_id;

        if (db().table("appx_ex_setting").where("app_id=? AND name=?", appID, name).exists()) {
            db().table("appx_ex_setting")
                    .set("agroup_id", agroup_id)
                    .set("type", type)
                    .set("value", value)
                    .set("ver_start", verStart)
                    .set("is_client", isClient ? 1 : 0)
                    .where("app_id=? AND name=?", appID, name)
                    .update();
        } else {
            db().table("appx_ex_setting")
                    .set("agroup_id", agroup_id)
                    .set("app_id", appID)
                    .set("name", name)
                    .set("type", type)
                    .set("value", value)
                    .set("ver_start", verStart)
                    .set("is_client", isClient ? 1 : 0)
                    .insert();
        }

        rock_cache.tags().clear("app_setting_by_name_" + name);

        delCacheByApp(appID);
    }


    //获取一个应用组设置集
    public static AppSettingCollection getAppGroupSetting(int agroupID, int verStart, boolean isClientOnly) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroup_id");
        }

        app_ex_get_setting sp = new app_ex_get_setting(rock_db);
        sp.agroup_id = agroupID;
        sp.is_client_only = isClientOnly;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_group_setting_" + agroupID);

        List<AppSettingModel> tmp = sp.getList(new AppSettingModel());
        List<AppSettingModel> mod = new ArrayList<>();

        for (AppSettingModel m : tmp) {
            if (verStart < m.ver_start) {
                continue;
            }
            mod.add(m);
        }

        AppSettingCollection set = new AppSettingCollection();
        set.bind(mod);
        return set;
    }

    //获取一个应用组的设置项
    public static AppSettingModel getAppGroupSettingItem(int agroupID, String name) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroup_id");
        }

        app_ex_get_setting_item sp = new app_ex_get_setting_item(rock_db);
        sp.agroup_id = agroupID;
        sp.name = name;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_group_setting_" + agroupID);

        return sp.getItem(new AppSettingModel());
    }

    public static AppSettingModel getAppGroupSettingItemNoCache(int agroupID, String name) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroup_id");
        }

        app_ex_get_setting_item sp = new app_ex_get_setting_item(rock_db);
        sp.agroup_id = agroupID;
        sp.name = name;

        return sp.getItem(new AppSettingModel());
    }


    //仅用于管理
    public static boolean delAppGroupSettingItem(int agroupID, String name) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroupID");
        }

        app_ex_del_setting_item sp = new app_ex_del_setting_item(rock_db);
        sp.agroup_id = agroupID;
        sp.name = name;

        boolean isOk = sp.execute() > 0;

        delCacheByAppGroup(agroupID);

        return isOk;
    }


    //设置一个应用组的设置项的值
    public static void setAppGroupSettingItem(int agroupID, String name, int type, String value, int verStart, boolean isClient) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroup_id");
        }

        if (db().table("appx_ex_setting").where("agroup_id=? AND app_id=0 AND name=?", agroupID, name).exists()) {
            db().table("appx_ex_setting")
                    .set("type", type)
                    .set("value", value)
                    .set("ver_start", verStart)
                    .set("is_client", isClient ? 1 : 0)
                    .where("agroup_id=? AND app_id=0 AND name=?", agroupID, name)
                    .update();
        } else {
            db().table("appx_ex_setting")
                    .set("agroup_id", agroupID)
                    .set("app_id", 0)
                    .set("name", name)
                    .set("type", type)
                    .set("value", value)
                    .set("ver_start", verStart)
                    .set("is_client", isClient ? 1 : 0)
                    .insert();
        }

        delCacheByAppGroup(agroupID);
    }

    //=======================
    /** 检查应用版本更新 */
    public static AppUpdateModel chkAppUpdate(int appID, int platform, int verID) throws SQLException {
        AppVersionModel ver = getAppVersionEx(appID, platform);
        AppUpdateModel udp = new AppUpdateModel(ver);

        /** 第一优先 */
        if (ver.force_ver > 0) {
            if (ver.force_ver > verID) {
                udp.type = UpdateType.force;
                return udp;
            }
        }

        /** 第二优先 */
        if (ver.alert_ver > 0) {
            if (ver.alert_ver > verID) {
                udp.type = UpdateType.alert;
                return udp;
            }
        } else {
            /** 第三优先 */
            if (ver.ver > verID) {
                if (ver.type == 1) {
                    udp.type = UpdateType.force;
                } else {
                    udp.type = UpdateType.alert;
                }

                return udp;
            }
        }

        return udp;
    }

    /** 获取一个应用版本 */
    public static AppVersionModel getAppVersionEx(int appID, int platform) throws SQLException {
        AppVersionModel ver = doGetAppVersion(0, appID, platform);

        if (ver.app_id > 0) {
            return ver;
        }

        int agroup_id = getApp(appID).agroup_id;

        ver = doGetAppVersion(agroup_id, 0, platform);

        return ver;
    }

    public static AppVersionModel getAppGroupVersion(int agroupID, int platform) throws SQLException {
        return doGetAppVersion(agroupID, 0, platform);
    }

    public static AppVersionModel getAppVersion(int appID, int platform) throws SQLException {
        return doGetAppVersion(0, appID, platform);
    }


    private static AppVersionModel doGetAppVersion(int agroupID, int appID, int platform) throws SQLException {
        app_ex_get_verson sp = new app_ex_get_verson(rock_db);
        sp.agroup_id = agroupID;
        sp.app_id = appID;
        sp.platform = platform;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_group_ver_" + agroupID)
                .cacheTag("app_ver_" + appID);

        return sp.getItem(new AppVersionModel());
    }

    //=======================

    //获取接口状态码
    public static AppCodeCollection getAppCodes(int agroupID) throws SQLException {
        app_ex_get_codes sp = new app_ex_get_codes(rock_db);
        sp.agroup_id = agroupID;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_code_" + agroupID);

        List<AppCodeModel> m = sp.getList(new AppCodeModel());

        AppCodeCollection ac = new AppCodeCollection();
        ac.bind(m);
        return ac;
    }

    //获取一个Api Code 的描述 //无异常
    public static String tryAppCode(int agroupID, int code) {
        try {
            return getAppCode(agroupID, code);
        } catch (Exception ex) {
            return "";
        }
    }

    //获取一个Api Code 的描述
    public static String getAppCode(int agroupID, int code) throws SQLException {
        app_ex_get_code sp = new app_ex_get_code(rock_db);
        sp.agroup_id = agroupID;
        sp.code = code;

        AppCodeModel m = sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_code_" + agroupID)
                .getItem(new AppCodeModel());

        if (m.note == null)
            return "";
        else
            return m.note;
    }

    //type=0=ip; type=1=host
    public static boolean isWhitelist(String tag, int type, String val) throws SQLException {
        app_get_whitelist sp = new app_get_whitelist(rock_db);
        sp.type = type;
        sp.tag = tag;
        sp.value = val;

        sp.caching(rock_cache)
                .cacheTag("app_whitelist_" + type + "_" + tag);

        return sp.getValue(null) != null;
    }

    //==================

    public static long newID(String key, String field, int cacheTime) {
        return RockUtil.newID(key,field,cacheTime);
    }


    //一般用于业务唯一约束检查
    //
    public static boolean isUnique(String group, String key) {
        return RockUtil.isUnique(group, key, 3);
    }

    //@key     关键值
    //@seconds 有效时间（秒数）
    //return   是否在有效时间内唯一
    public static boolean isUnique(String group, String key, int inSeconds) {
        return RockUtil.isUnique(group, key, inSeconds);
    }

    public static RedisX redis(int db) {
        return RockUtil.redis(db);
    }

    public static RedisX redis(int db, int maxTotal) {
        return RockUtil.redis(db, maxTotal);
    }

    public static RedisX redis(ConfigM cfg, int db, int maxTotal) {
        return RockUtil.redis(cfg, db, maxTotal);
    }

    //====================================================================================


    public static void delCacheByApp(int appID) {
        if (appID < 1) {
            return;
        }

        rock_cache.tags()
                .clear("app_setting_" + appID)
                .clear("app_" + appID);

        RockUtil.sendMessage("app_" + appID + ";app_setting_" + appID);
    }

    public static void delCacheByAppGroup(int agroupID) {
        if (agroupID < 1) {
            return;
        }

        rock_cache.tags()
                .clear("app_group_" + agroupID)
                .clear("app_group_setting_" + agroupID)
                .clear("app_setting_by_name_" + agroupID)
                .clear("app_list_" + agroupID);


        RockUtil.sendMessage("app_group_" + agroupID + ";app_group_setting_" + agroupID + ";app_setting_by_name_" + agroupID + ";app_list_" + agroupID);
    }

    public static void delCacheForVersion(int agroupID, int appID) {
        if (appID > 0) {
            rock_cache.tags()
                    .clear("app_ver_" + appID);

            RockUtil.sendMessage("app_ver_" + appID);
        } else {
            rock_cache.tags()
                    .clear("app_group_ver_" + agroupID);

            RockUtil.sendMessage("app_group_ver_" + agroupID);
        }
    }

    public static void delCacheForCodes(int agroupID) {
        rock_cache.tags()
                .clear("app_code_" + agroupID);

        RockUtil.sendMessage("app_code_" + agroupID);
    }

    public static void delCacheForWhiteList(String tag, int type) {
        rock_cache.tags()
                .clear("app_whitelist_" + type + "_" + tag);

        RockUtil.sendMessage("app_whitelist_" + type + "_" + tag);
    }

}
