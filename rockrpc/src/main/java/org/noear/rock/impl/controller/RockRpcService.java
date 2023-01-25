package org.noear.rock.impl.controller;

import org.noear.rock.protocol.RockRpc;
import org.noear.rock.model.*;
import org.noear.rock.RockUtil;
import lombok.NonNull;
import org.noear.solon.Solon;
import org.noear.solon.annotation.*;
import org.noear.rock.impl.dso.process.*;
import org.noear.wood.DbContext;
import org.noear.wood.cache.ICacheServiceEx;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//@Before({StartHandler.class,IpHandler.class})
//@After({EndHandler.class})
//@Mapping("/")
//@Component(remoting = true)
@Component
public final class RockRpcService implements RockRpc {
    @Inject("rock_db")
    public DbContext rock_db;

    @Inject("rock_cache")
    public ICacheServiceEx rock_cache;

    //初始化...
    public RockRpcService() {
        Solon.context().getWrapAsync("rock_cache", (bw)->{
            RockUtil.tryInit(bw.raw(), this);
        });
    }

    private DbContext db() {
        return rock_db;
    }

    //=========================

    //添加一个应用模型
    @Override
    public AppModel addApp(@NonNull Integer agroupID, @NonNull Integer ugroupID, @NonNull String name) throws SQLException {
        String app_key = RockUtil.buildAppKey();
        String app_secret_key = RockUtil.buildAppSecretKey();

        long app_id = db().table("appx")
                .set("agroup_id", agroupID)
                .set("ugroup_id", ugroupID)
                .set("app_key", app_key)
                .set("app_secret_key", app_secret_key)
                .set("name", name)
                .insert();

        return db().table("appx")
                .whereEq("app_id", app_id)
                .selectItem("*", AppModel.class);
    }

    //更新应用的名称
    @Override
    public void udpAppName(@NonNull Integer appID,@NonNull String name) throws SQLException {
        db().table("appx")
                .set("name", name)
                .where("app_id=?", appID)
                .update();

        RockUtil.delCacheByApp(appID);
    }

    //更新应用的审核状态
    @Override
    public void udpAppExamine(@NonNull Integer appID,@NonNull Integer isExamine, @NonNull Integer examineVer) throws SQLException {
        db().table("appx")
                .set("ar_is_examine", isExamine)
                .set("ar_examine_ver", examineVer)
                .where("app_id=?", appID)
                .update();

        RockUtil.delCacheByApp(appID);
    }

    //获取一个应用模型
    @Override
    public AppModel getAppByID(@NonNull Integer appID) throws SQLException {
        app_get_app sp = new app_get_app(rock_db);

        sp.app_id = appID;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_" + appID);

        return sp.getItem(AppModel.class);
    }

    public AppModel getAppByIDNoCache(@NonNull Integer appID) throws SQLException {
        app_get_app sp = new app_get_app(rock_db);

        sp.app_id = appID;

        return sp.getItem(AppModel.class);
    }

    //获取一个应用模型
    @Override
    public AppModel getAppByKey(@NonNull String appKey) throws SQLException {
        app_get_app sp = new app_get_app(rock_db);
        sp.app_key = appKey;
        sp.caching(rock_cache)
                .usingCache(60 * 10);

        return sp.getItem(AppModel.class, (cu, m) -> {
            cu.cacheTag("app_" + m.app_id);
        });
    }

    //可以只输入一个分组
    @Override
    public List<AppModel> getAppsByGroup(@NonNull Integer agroupID,@NonNull  Integer ugroupID) throws Exception {
        if (agroupID < 1 && ugroupID < 1) {
            throw new Exception("请输入有效的 agroupID 或 ugroupID");
        }

        app_get_app_list sp = new app_get_app_list(rock_db);
        sp.agroup_id = agroupID;
        sp.ugroup_id = ugroupID;
        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_list_" + agroupID);

        return sp.getList(AppModel.class);
    }

    //获取一个应用组模型
    @Override
    public AppGroupModel getAppGroup(@NonNull Integer agroupID) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroupID");
        }

        app_get_agroup sp = new app_get_agroup(rock_db);

        sp.agroup_id = agroupID;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_group_" + agroupID);


        return sp.getItem(AppGroupModel.class);
    }

    //获取一个用户组模型
    @Override
    public UserGroupModel getUserGroup(@NonNull Integer ugroupID) throws Exception {
        if (ugroupID < 1) {
            throw new Exception("请输入有效的 ugroupID");
        }

        app_get_ugroup sp = new app_get_ugroup(rock_db);

        sp.ugroup_id = ugroupID;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("user_group_" + ugroupID);


        return sp.getItem(UserGroupModel.class);
    }

    //=======================
    //获取一个应用设置集
    @Override
    public AppSettingCollection getAppSetting(@NonNull Integer appID,@NonNull  Integer verStart,@NonNull Boolean isClientOnly) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 app_id");
        }

        app_ex_get_setting sp = new app_ex_get_setting(rock_db);
        sp.app_id = appID;
        sp.is_client_only = isClientOnly;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_setting_" + appID);

        List<AppSettingModel> tmp = sp.getList(AppSettingModel.class);
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
    @Override
    public AppSettingModel getAppSettingItem(@NonNull Integer appID,@NonNull String name) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

        app_ex_get_setting_item sp = new app_ex_get_setting_item(rock_db);
        sp.app_id = appID;
        sp.name = name;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_setting_" + appID);

        return sp.getItem(AppSettingModel.class);
    }

    @Override
    public AppSettingModel getAppSettingItemNoCache(@NonNull Integer appID,@NonNull String name) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

        app_ex_get_setting_item sp = new app_ex_get_setting_item(rock_db);
        sp.app_id = appID;
        sp.name = name;

        return sp.getItem(AppSettingModel.class);
    }

    //仅用于管理
    @Override
    public Boolean delAppSettingItem(@NonNull Integer appID,@NonNull String name) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

        app_ex_del_setting_item sp = new app_ex_del_setting_item(rock_db);
        sp.app_id = appID;
        sp.name = name;

        Boolean isOk = sp.execute() > 0;

        rock_cache.tags().clear("app_setting_by_name_" + name);

        RockUtil.delCacheByApp(appID);

        return isOk;
    }

    @Override
    public AppSettingCollection getAppSettingEx(@NonNull Integer appID,@NonNull  Integer ver, @NonNull Boolean isClientOnly) throws Exception {
         Integer agroup_id = getAppByID(appID).agroup_id;

        return getAppSettingEx2(agroup_id, appID, ver, isClientOnly);
    }

    //获取应用设置项 //已包函时间
    public AppSettingCollection getAppSettingEx2(@NonNull Integer groupID, @NonNull Integer appID, @NonNull Integer ver,@NonNull Boolean isClientOnly) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

        if(groupID < 1){
            groupID = getAppByID(appID).agroup_id;
        }

        AppSettingCollection sets = getAppGroupSetting(groupID, ver, isClientOnly);

        AppSettingCollection temp = getAppSetting(appID, ver, isClientOnly);

        for (String k : temp.keySet()) {
            AppSettingModel v = temp.get(k);

            if (v.value != null && v.value.length() > 0) {
                sets.put(k, v);
            }
        }

        return sets;
    }

    @Override
    public List<AppSettingModel> getAppSettingItemsByName(@NonNull Integer agroupID,@NonNull String name) throws Exception {
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

        return sp.getList(AppSettingModel.class);
    }

    //获取一个应用设置项 //如果没有去app_group_setting获取
    @Override
    public AppSettingModel getAppSettingItemEx(@NonNull Integer appID,@NonNull String name) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

        AppSettingModel item = getAppSettingItem(appID, name);
        if (item.app_id > 0) {
            return item;
        } else {
             Integer agroup_id = getAppByID(appID).agroup_id;
            return getAppGroupSettingItem(agroup_id, name);
        }
    }


    //设置一个应用设置项的值
    @Override
    public void setAppSettingItem(@NonNull Integer appID,@NonNull String name, @NonNull Integer type,@NonNull String value,@NonNull  Integer verStart,@NonNull Boolean isClient) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

         Integer agroup_id = getAppByID(appID).agroup_id;

        if (db().table("appx_ex_setting").where("app_id=? AND name=?", appID, name).selectExists()) {
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

        RockUtil.delCacheByApp(appID);
    }


    //获取一个应用组设置集
    @Override
    public AppSettingCollection getAppGroupSetting(@NonNull Integer agroupID,@NonNull  Integer verStart,@NonNull Boolean isClientOnly) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroup_id");
        }

        app_ex_get_setting sp = new app_ex_get_setting(rock_db);
        sp.agroup_id = agroupID;
        sp.is_client_only = isClientOnly;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_group_setting_" + agroupID);

        List<AppSettingModel> tmp = sp.getList(AppSettingModel.class);
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
    @Override
    public AppSettingModel getAppGroupSettingItem(@NonNull Integer agroupID,@NonNull String name) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroup_id");
        }

        app_ex_get_setting_item sp = new app_ex_get_setting_item(rock_db);
        sp.agroup_id = agroupID;
        sp.name = name;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_group_setting_" + agroupID);

        return sp.getItem(AppSettingModel.class);
    }

    @Override
    public AppSettingModel getAppGroupSettingItemNoCache(@NonNull Integer agroupID,@NonNull String name) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroup_id");
        }

        app_ex_get_setting_item sp = new app_ex_get_setting_item(rock_db);
        sp.agroup_id = agroupID;
        sp.name = name;

        return sp.getItem(AppSettingModel.class);
    }


    //仅用于管理
    @Override
    public Boolean delAppGroupSettingItem(@NonNull Integer agroupID,@NonNull String name) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroupID");
        }

        app_ex_del_setting_item sp = new app_ex_del_setting_item(rock_db);
        sp.agroup_id = agroupID;
        sp.name = name;

        Boolean isOk = sp.execute() > 0;

        RockUtil.delCacheByAppGroup(agroupID);

        return isOk;
    }


    //设置一个应用组的设置项的值
    @Override
    public void setAppGroupSettingItem(@NonNull Integer agroupID,@NonNull String name,@NonNull  Integer type,@NonNull String value,@NonNull  Integer verStart,@NonNull Boolean isClient) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroup_id");
        }

        if (db().table("appx_ex_setting").where("agroup_id=? AND app_id=0 AND name=?", agroupID, name).selectExists()) {
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

        RockUtil.delCacheByAppGroup(agroupID);
    }

    //=======================
    /** 检查应用版本更新 */
    @Override
    public AppUpdateModel chkAppUpdate(@NonNull Integer appID, @NonNull  Integer platform, @NonNull  Integer verID) throws SQLException {
        AppVersionModel ver = getAppVersionEx(appID, platform);
        AppUpdateModel udp = new AppUpdateModel();
        udp.bind(ver);

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
    @Override
    public AppVersionModel getAppVersionEx( @NonNull Integer appID, @NonNull Integer platform) throws SQLException {
        AppVersionModel ver = doGetAppVersion(0, appID, platform);

        if (ver.app_id > 0) {
            return ver;
        }

         Integer agroup_id = getAppByID(appID).agroup_id;

        ver = doGetAppVersion(agroup_id, 0, platform);

        return ver;
    }

    @Override
    public AppVersionModel getAppGroupVersion(@NonNull Integer agroupID,@NonNull  Integer platform) throws SQLException {
        return doGetAppVersion(agroupID, 0, platform);
    }

    @Override
    public AppVersionModel getAppVersion(@NonNull Integer appID,@NonNull  Integer platform) throws SQLException {
        return doGetAppVersion(0, appID, platform);
    }


    private AppVersionModel doGetAppVersion(@NonNull Integer agroupID, @NonNull Integer appID, @NonNull Integer platform) throws SQLException {
        app_ex_get_verson sp = new app_ex_get_verson(rock_db);
        sp.agroup_id = agroupID;
        sp.app_id = appID;
        sp.platform = platform;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_group_ver_" + agroupID)
                .cacheTag("app_ver_" + appID);

        return sp.getItem(AppVersionModel.class);
    }

    //=======================

    //获取接口状态码
    @Override
    public AppCodeCollection getAppCodes(@NonNull Integer agroupID) throws SQLException {
        return getAppCodesByLang(agroupID, "");
    }

    @Override
    public AppCodeCollection getAppCodesByLang(@NonNull Integer agroupID, String lang) throws SQLException {
        app_ex_get_codes sp = new app_ex_get_codes(rock_db);
        sp.agroup_id = agroupID;
        sp.lang = lang;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_code_" + agroupID);

        List<AppCodeModel> m = sp.getList(AppCodeModel.class);

        AppCodeCollection ac = new AppCodeCollection();
        ac.bind(m);
        return ac;
    }

    //获取一个Api Code 的描述
    @Override
    public String getAppCode(@NonNull Integer agroupID,@NonNull  Integer code) throws SQLException {
        return getAppCodeByLang(agroupID, code, "");
    }

    @Override
    public String getAppCodeByLang(@NonNull Integer agroupID,@NonNull  Integer code, String lang) throws SQLException {
        app_ex_get_code sp = new app_ex_get_code(rock_db);
        sp.agroup_id = agroupID;
        sp.code = code;
        sp.lang = lang;

        AppCodeModel m = sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_code_" + agroupID)
                .getItem(AppCodeModel.class);

        if (m.note == null) {
            return "";
        }
        else {
            return m.note;
        }
    }

    @Override
    public AppCodeCollection getServiceCodes(String service) throws SQLException {
        return getServiceCodesByLang(service, "");
    }

    @Override
    public AppCodeCollection getServiceCodesByLang(String service, String lang) throws SQLException {
        app_ex_get_codes2 sp = new app_ex_get_codes2(rock_db);
        sp.service = service;
        sp.lang = lang;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_code:" + service);

        List<AppCodeModel> m = sp.getList(AppCodeModel.class);

        AppCodeCollection ac = new AppCodeCollection();
        ac.bind(m);
        return ac;
    }

    @Override
    public AppI18nCollection getServiceCodes2(String service) throws SQLException {
        return getServiceCodesByLang2(service, "");
    }

    @Override
    public AppI18nCollection getServiceCodesByLang2(String service, String lang) throws SQLException {
        app_ex_get_codes2 sp = new app_ex_get_codes2(rock_db);
        sp.service = service;
        sp.lang = lang;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_code:" + service);

        List<AppCodeModel> m = sp.getList(AppCodeModel.class);

        AppI18nCollection ac = new AppI18nCollection();
        ac.bindOfCode(m);
        return ac;
    }

    @Override
    public String getServiceCode(String service, Integer code) throws SQLException {
        return getServiceCodeByLang(service, code, "");
    }

    @Override
    public String getServiceCodeByLang(String service, Integer code, String lang) throws SQLException {
        app_ex_get_code2 sp = new app_ex_get_code2(rock_db);
        sp.service = service;
        sp.code = code;
        sp.lang = lang;

        AppCodeModel m = sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_code:" + service)
                .getItem(AppCodeModel.class);

        if (m.note == null) {
            return "";
        }
        else {
            return m.note;
        }
    }

    @Override
    public AppI18nCollection getServiceI18ns(String service) throws SQLException {
        return getServiceI18nsByLang(service, "");
    }

    @Override
    public AppI18nCollection getServiceI18nsByLang(String service, String lang) throws SQLException {
        app_ex_get_i18ns sp = new app_ex_get_i18ns(rock_db);
        sp.service = service;
        sp.lang = lang;

        sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_i18n:" + service);

        List<AppI18nModel> m = sp.getList(AppI18nModel.class);

        AppI18nCollection ac = new AppI18nCollection();
        ac.bind(m);
        return ac;
    }

    @Override
    public String getServiceI18n(String service, String name) throws SQLException {
        return getServiceI18nByLang(service, name, "");
    }

    @Override
    public String getServiceI18nByLang(String service, String name, String lang) throws SQLException {
        app_ex_get_i18n sp = new app_ex_get_i18n(rock_db);
        sp.service = service;
        sp.name = name;
        sp.lang = lang;

        AppI18nModel m = sp.caching(rock_cache)
                .usingCache(60 * 10)
                .cacheTag("app_i18n:" + service)
                .getItem(AppI18nModel.class);

        if (m.note == null) {
            return "";
        }
        else {
            return m.note;
        }
    }
}
