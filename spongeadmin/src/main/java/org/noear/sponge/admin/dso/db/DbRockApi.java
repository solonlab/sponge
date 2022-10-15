package org.noear.sponge.admin.dso.db;

import org.noear.sponge.admin.dso.CacheUtil;
import org.noear.sponge.admin.model.rock.*;
import org.noear.rock.RockUtil;
import org.noear.water.utils.TextUtils;
import org.noear.wood.DbContext;
import org.noear.wood.DbTableQuery;
import org.noear.sponge.admin.Config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DbRockApi {
    private static DbContext db() {
        return Config.sponge_rock;
    }

    public static List<UserGroupModel> getUserGroup(String name) throws SQLException{
        return getUserGroup(name,0);
    }

    //获取ugroup列表
    public static List<UserGroupModel> getUserGroup(String name,Integer _state) throws SQLException {
        if(_state == null){
            _state = 0;
        }

        return db().table("appx_ugroup")
                .whereEq("is_disabled",(_state == 0 ? 0: 1))
                .build((tb) -> {
                    if (TextUtils.isEmpty(name) == false) {
                        tb.and("name like ?", name + "%");
                    }
                })
                .select("*")
                .getList(UserGroupModel.class);
    }

    //根据id获取用户组
    public static UserGroupModel getUserGroupById(Integer ugroup_id) throws SQLException {
        return db().table("appx_ugroup")
                .where("ugroup_id = ?",ugroup_id)
                .limit(1)
                .select("*")
                .getItem(UserGroupModel.class);
    }
    //编辑或新增用户组
    public static Boolean editUgroup(Integer ugroup_id, String name,Integer new_ugroup_id,boolean is_disabled) throws SQLException {
        DbTableQuery tb = db().table("appx_ugroup")
                .set("name", name)
                .set("is_disabled", is_disabled ? 1 : 0);

        if (new_ugroup_id > 0) {
            tb.set("ugroup_id", new_ugroup_id);
        }

        if (ugroup_id > 0) {
            return tb.where("ugroup_id = ?", ugroup_id).update() > 0;
        } else {
            return tb.insert() > 0;
        }
    }

    public static List<AppGroupModel> getAppGroup(String name) throws SQLException{
        return getAppGroup(name,false);
    }

    //获取 app_agroup 列表
    public static List<AppGroupModel> getAppGroup(String name, boolean is_disabled) throws SQLException {
        return db().table("appx_agroup")
                .whereEq("is_disabled", is_disabled ? 1 : 0)
                .build((tb) -> {
                    if (TextUtils.isEmpty(name) == false) {
                        tb.and("name like ?", name + "%");
                    }
                })
                .selectList("*", AppGroupModel.class);
    }

    //根据id获取应用组
    public static AppGroupModel getAppGroupById(Integer agroup_id) throws SQLException {
        if(agroup_id<1){
            return new AppGroupModel();
        }

        return db().table("appx_agroup")
                .where("agroup_id = ?",agroup_id)
                .limit(1)
                .select("*")
                .getItem(AppGroupModel.class);
    }


    //编辑或新增应用组
    public static Boolean editAgroup(Integer agroup_id, Integer new_agroup_id,String name, String tag,Integer ugroup_id,Integer enable_track, boolean is_disabled) throws SQLException {
        DbTableQuery tb = db().table("appx_agroup")
                .set("name", name)
                .set("tag", tag)
                .set("enable_track", enable_track)
                .set("ugroup_id", ugroup_id)
                .set("is_disabled", is_disabled ? 1 : 0);

        boolean isOk = true;
        if (new_agroup_id > 0) {
            tb.set("agroup_id", new_agroup_id);
        }

        if (agroup_id > 0) {
            isOk = tb.where("agroup_id = ?", agroup_id).update() > 0;
        } else {
            isOk = tb.insert() > 0;
        }

        if (isOk) {
            RockUtil.delCacheByAppGroup(agroup_id);
        }

        return isOk;
    }

    //获取应用组设置列表
    public static List<AppExSettingModel> getAppGroupSetsById(Integer agroup_id,String name, boolean is_disabled) throws SQLException {
        if (agroup_id < 1) {
            return new ArrayList<>();
        }

        return db().table("appx_ex_setting")
                .where("agroup_id = ? AND app_id=0", agroup_id)
                .andEq("is_disabled", is_disabled ? 1 : 0)
                .build((tb) -> {
                    if (TextUtils.isEmpty(name) == false) {
                        tb.and("name like ?", name + "%");
                    }
                })
                .orderBy("name ASC")
                .selectList("*", AppExSettingModel.class);
    }

    public static List<AppExSettingModel> getAppGroupSetsList(Integer agroup_id, List<Object> ids) throws SQLException {
        if (agroup_id < 1) {
            return new ArrayList<>();
        }

        return db().table("appx_ex_setting")
                .where("agroup_id = ? AND app_id=0", agroup_id).andIn("row_id", ids)
                .orderBy("name ASC")
                .selectList("*", AppExSettingModel.class);
    }

    public static void delAppGroupSets(int act,  List<Object> ids) throws SQLException {
        if (act == 9) {
//            db().table("appx_ex_setting")
//                    .whereIn("row_id", ids)
//                    .delete();
        } else {
            db().table("appx_ex_setting")
                    .set("is_disabled", (act == 1 ? 0 : 1))
                    .whereIn("row_id", ids)
                    .update();
        }
    }

    //获取应用组设置分组 计数。
    public static List<AppExSettingModel> getAppGroupCounts() throws SQLException {
        return db().table("appx_ex_setting")
                .where("app_id=0")
                .groupBy("agroup_id")
                .selectList("agroup_id,count(*) counts", AppExSettingModel.class);
    }

    //编辑 根据id获取用户组设置
    public static AppExSettingModel getAgsetsById(Integer row_id) throws SQLException {
        return db().table("appx_ex_setting")
                .where("row_id = ?", row_id)
                .limit(1)
                .selectItem("*", AppExSettingModel.class);
    }

    //保存应用组配置
    public static boolean editAgsets(Integer row_id, String name, Integer type, String value, String note,Integer is_client, Integer ver_start, Integer agroup_id, int is_disabled) throws SQLException {
         DbTableQuery tb = db().table("appx_ex_setting")
                 .set("name",name)
                 .set("type",type)
                 .set("value",value)
                 .set("note",note)
                 .set("is_client",is_client)
                 .set("is_disabled", is_disabled)
                 .set("ver_start",ver_start)
                 .set("agroup_id",agroup_id);

         boolean isOk = true;
         if(row_id>0){
           isOk =  tb.where("row_id = ?",row_id).update()>0;
         }else{
           isOk =  tb.insert()>0;
         }

         if(isOk){
             RockUtil.delCacheByAppGroup(agroup_id);
         }

         return isOk;
    }

    public static void impAgsets( Integer agroup_id, AppExSettingModel m) throws SQLException {
        DbTableQuery tb = db().table("appx_ex_setting")
                .set("name", m.name)
                .set("type", m.type)
                .set("value", m.value)
                .set("note", m.note)
                .set("is_client", m.is_client)
                .set("ver_start", m.ver_start)
                .set("agroup_id", agroup_id);

        if (tb.whereEq("agroup_id", agroup_id).andEq("app_id", 0).andEq("name", m.name).selectExists()) {
            tb.update();
        }else{
            tb.insert();
        }
    }

    //获取app列表。
    public static List<AppModel> getApps(String name, Integer agroup_id, Integer ar_is_examine) throws SQLException {


        return db().table("appx")
                .where("1 = 1")
                .build((tb)->{
                    if (TextUtils.isEmpty(name)==false){
                        tb.and("name like ?",name + "%");
                    }
                    if (ar_is_examine!=null){
                        tb.and("ar_is_examine = ?",ar_is_examine);
                    }
                    if (agroup_id!=null){
                        tb.and("agroup_id = ?",agroup_id);
                    }
                })
                .orderBy("name asc")
                .select("*")
                .getList(AppModel.class);
    }

    //获取应用分组 计数。
    public static List<AppModel> getAppCounts() throws SQLException {
        return db().table("appx")
                .groupBy("agroup_id")
                .select("agroup_id,count(*) counts")
                .getList(AppModel.class);
    }


    //根据id获取app
    public static AppModel getAppById(Integer app_id) throws SQLException {
        return db().table("appx")
                .where("app_id = ?",app_id)
                .limit(1)
                .select("*")
                .getItem(AppModel.class);
    }

    //保存app信息
    public static boolean editApp(Integer app_id, String name, Integer agroup_id, Integer ugroup_id, String app_key,String app_secret_key, String app_secret_salt,Integer ar_is_examine,Integer ar_is_setting, String note, Integer ar_examine_ver) throws SQLException {
        DbTableQuery tb = db().table("appx")
                .set("name",name)
                .set("agroup_id",agroup_id)
                .set("ugroup_id",ugroup_id)
                .set("app_key",app_key)
                .set("app_secret_key",app_secret_key)
                .set("app_secret_salt", app_secret_salt)
                .set("ar_is_examine",ar_is_examine)
                .set("ar_is_setting",ar_is_setting)
                .set("note",note)
                .set("ar_examine_ver",ar_examine_ver);

        boolean isOk = true;
        if(app_id>0){
          isOk =  tb.where("app_id = ?",app_id).update()>0;
        }else{
          isOk =  tb.insert()>0;
        }

        if(isOk){
            RockUtil.delCacheByApp(app_id);
        }

        return isOk;
    }

    //获取应用设置列表
    public static List<AppExSettingModel> getAppSets(Integer app_id, String name, boolean is_disabled) throws SQLException {
        if (app_id < 1) {
            return new ArrayList<>();
        }

        return db().table("appx_ex_setting")
                .whereEq("app_id", app_id)
                .andEq("is_disabled", is_disabled ? 1 : 0)
                .build((tb) -> {
                    if (TextUtils.isEmpty(name) == false) {
                        tb.and("name like ?", name + "%");
                    }
                })
                .orderBy("name ASC")
                .selectList("*", AppExSettingModel.class);
    }

    public static List<AppExSettingModel> getAppSetsList(Integer app_id, List<Object> ids) throws SQLException {
        if (app_id < 1) {
            return new ArrayList<>();
        }

        return db().table("appx_ex_setting")
                .where("app_id = ?", app_id).andIn("row_id", ids)
                .orderBy("name ASC")
                .selectList("*", AppExSettingModel.class);
    }

    public static void delAppSets(int act,List<Object> ids) throws SQLException {
        if (act == 9) {
//            db().table("appx_ex_setting")
//                    .whereIn("row_id", ids)
//                    .delete();
        } else {
            db().table("appx_ex_setting")
                    .set("is_disabled", (act == 1 ? 0 : 1))
                    .whereIn("row_id", ids)
                    .update();
        }
    }

    //根据id获取应用配置
    public static AppExSettingModel getAppsetsById(Integer row_id) throws SQLException {
        return db().table("appx_ex_setting")
                .where("row_id = ?", row_id)
                .limit(1)
                .selectItem("*", AppExSettingModel.class);
    }

    //保存应用配置
    public static boolean editAppsets(Integer row_id, String name, Integer type, String value, String note,Integer is_client, Integer ver_start, Integer app_id, int is_disabled) throws SQLException {
        int agroup_id = getAppById(app_id).agroup_id;

        DbTableQuery tb = db().table("appx_ex_setting")
                .set("name", name)
                .set("type", type)
                .set("value", value)
                .set("note", note)
                .set("is_client", is_client)
                .set("is_disabled", is_disabled)
                .set("ver_start", ver_start)
                .set("agroup_id", agroup_id)
                .set("app_id", app_id);

        boolean isOk = true;
        if (row_id > 0) {
            isOk = tb.where("row_id = ?", row_id).update() > 0;
        } else {
            isOk = tb.insert() > 0;
        }

        if (isOk) {
            RockUtil.delCacheByApp(app_id);
        }

        return isOk;
    }

    public static void impAppsets(int agroup_id, int app_id, AppExSettingModel m) throws SQLException {
        DbTableQuery tb = db().table("appx_ex_setting")
                .set("name", m.name)
                .set("type", m.type)
                .set("value", m.value)
                .set("note", m.note)
                .set("is_client", m.is_client)
                .set("ver_start", m.ver_start)
                .set("agroup_id", agroup_id)
                .set("app_id", app_id);

        if (tb.whereEq("app_id", app_id).andEq("name", m.name).selectExists()) {
            tb.update();
        }else{
            tb.insert();
        }
    }

    //根据agroup_id 获取 app列表
    public static List<AppModel> getAppsByAgroupId(Integer agroup_id) throws SQLException {
        if(agroup_id<1){
            return new ArrayList<>();
        }

        return db().table("appx")
                .where("1=1")
                .build((tb)->{
                    if (agroup_id!=null && agroup_id!=0){
                        tb.and("agroup_id = ?",agroup_id);
                    }
                })
                .orderBy("app_id asc")
                .select("*")
                .getList(AppModel.class);
    }

    public static List<AppModel> getAppsByAgroupIdForSetting(Integer agroup_id) throws SQLException {
        return db().table("appx")
                .where("ar_is_setting=1")
                .build((tb)->{
                    if (agroup_id!=null && agroup_id!=0){
                        tb.and("agroup_id = ?",agroup_id);
                    }
                })
                .orderBy("app_id asc")
                .select("*")
                .getList(AppModel.class);
    }




    public static List<AppxWhitelistModel> getWhiteListGroup() throws SQLException{
        return db().table("appx_whitelist")
                .groupBy("tag")
                .select("*,COUNT(tag) count")
                .getList(AppxWhitelistModel.class);

    }

    public static List<AppxWhitelistModel> getWhiteLists(String tag,String value) throws SQLException{
        return db().table("appx_whitelist")
                .where("1=1")
                .build(tb->{
                    if (TextUtils.isEmpty(tag) == false){
                        tb.and("tag = ?",tag);
                    }
                    if (TextUtils.isEmpty(value) == false) {
                        tb.and("value like ?", value + "%");
                    }
                })
                .select("*")
                .getList(AppxWhitelistModel.class);
    }

    public static AppxWhitelistModel getWhiteListById(int row_id) throws SQLException{
        return db().table("appx_whitelist")
                .where("row_id = ?",row_id)
                .select("*")
                .getItem(AppxWhitelistModel.class);

    }

    //新增或修改安全白名单
    public static Boolean editWhiteList(Integer row_id,Integer type,String value,String note,String tag) throws SQLException {
        DbTableQuery db = db().table("appx_whitelist")
                .set("type", type)
                .set("value", value)
                .set("note", note)
                .set("tag", tag);

        boolean isOk = true;

        if (row_id > 0) {
            isOk = db.where("row_id = ?", row_id).update() > 0;
        } else {
            isOk = db.insert() > 0;
        }

        if (isOk) {
            RockUtil.delCacheForWhiteList(tag, type);
        }

        return isOk;
    }

    //获取全部应用组
    public static List<AppGroupModel> getAppGroupList() throws SQLException{
        return db().table("appx_agroup")
                .caching(CacheUtil.dataCache)
                .select("*")
                .getList(AppGroupModel.class);
    }

    //获取全部用户组
    public static List<UserGroupModel> getUserGroupList() throws SQLException{
        return db().table("appx_ugroup")
                .caching(CacheUtil.dataCache)
                .select("*")
                .getList(UserGroupModel.class);
    }


    //获取app版本更新分组
    public static List<AppExVersionModel> getApverCounts() throws SQLException {
        return db().table("appx_ex_version")
                .groupBy("agroup_id")
                .selectList("agroup_id,count(*) counts", AppExVersionModel.class);
    }

    //根据agoup_id获取发布版本列表。
    public static List<AppExVersionModel> getApvers(int agroup_id, boolean is_disabled) throws SQLException {
        return db().table("appx_ex_version")
                .whereEq("is_disabled", is_disabled ? 1 : 0)
                .build((tb) -> {
                    if (agroup_id > 0) {
                        tb.andEq("agroup_id", agroup_id);
                    }
                })
                .orderBy("app_id ASC,ver DESC")
                .selectList("*", AppExVersionModel.class);
    }

    //根据row_id 获取apver
    public static AppExVersionModel getApverByRowId(Integer row_id) throws SQLException {
        return db().table("appx_ex_version")
                .where("row_id = ?",row_id)
                .select("*")
                .getItem(AppExVersionModel.class);

    }


    public static Boolean editApver(Integer app_id,Integer row_id, Integer ver, String content, Integer type, Integer alert_ver,Integer force_ver,Integer platform, String url, Integer is_disabled,Integer agroup_id) throws SQLException {
        DbTableQuery db = db().table("appx_ex_version")
                .set("app_id", app_id)
                .set("ver", ver)
                .set("content", content)
                .set("type", type)
                .set("alert_ver", alert_ver)
                .set("force_ver", force_ver)
                .set("platform", platform)
                .set("url", url)
                .set("is_disabled", is_disabled)
                .set("agroup_id", agroup_id);

        boolean isOk = true;
        if (row_id > 0) {
            isOk = db.where("row_id = ?", row_id).update() > 0;
        } else {
            db.set("log_fulltime", "$NOW()");
            isOk = db.insert() > 0;
        }

        if (isOk) {
            RockUtil.delCacheForVersion(agroup_id, app_id);
        }

        return isOk;
    }

}

