package org.noear.sponge.rock.protocol;

import org.noear.nami.annotation.NamiClient;
import org.noear.sponge.rock.models.*;

import java.sql.SQLException;
import java.util.List;

@NamiClient("rockrpc")
public interface RockRpc {

    /**
     * 添加一个应用模型
     * */
    AppModel addApp(Integer agroupID, Integer ugroupID, String name) throws SQLException;

    /**
     * 更新应用的名称
     * */
    void udpAppName(Integer appID, String name) throws SQLException;

    /**
     * 更新应用的审核状态
     * */
    void udpAppExamine(Integer appID, Integer isExamine, Integer examineVer) throws SQLException;

    /**
     * 获取一个应用模型
     * */
    AppModel getAppByID(Integer appID) throws SQLException;

    /**
     * 获取一个应用模型
     * */
    AppModel getAppByKey(String akey) throws SQLException;

    /**
     * 可以只输入一个分组
     * */
    List<AppModel> getAppsByGroup(Integer agroupID, Integer ugroupID) throws Exception;

    /**
     * 获取一个应用组模型
     * */
    AppGroupModel getAppGroup(Integer agroupID) throws Exception;

    /**
     * 获取一个用户组模型
     * */
    UserGroupModel getUserGroup(Integer ugroupID) throws Exception;

    //=======================
    /**
     * 获取一个应用设置集
     * */
    AppSettingCollection getAppSetting(Integer appID, Integer verStart, Boolean isClientOnly) throws Exception;

    /**
     * 获取一个应用设置项
     * */
    AppSettingModel getAppSettingItem(Integer appID, String name) throws Exception;

    AppSettingModel getAppSettingItemNoCache(Integer appID, String name) throws Exception;

    /**
     * 仅用于管理
     * */
    Boolean delAppSettingItem(Integer appID, String name) throws Exception;

    /**
     * 获取应用设置项（已包函时间）
     * */
    AppSettingCollection getAppSettingEx(Integer appID, Integer ver, Boolean isClientOnly) throws Exception;

    /**
     * 获取应用设置项（已包函时间）
     * */
    AppSettingCollection getAppSettingEx2(Integer groupID, Integer appID, Integer ver, Boolean isClientOnly) throws Exception;

    List<AppSettingModel> getAppSettingItemsByName(Integer agroupID, String name) throws Exception;

    /**
     * 获取一个应用设置项（如果没有去app_group_setting获取）
     * */
    AppSettingModel getAppSettingItemEx(Integer appID, String name) throws Exception;


    /**
     * 设置一个应用设置项的值
     * */
    void setAppSettingItem(Integer appID, String name, Integer type, String value, Integer verStart, Boolean isClient) throws Exception;


    /**
     * 获取一个应用组设置集
     * */
    AppSettingCollection getAppGroupSetting(Integer agroupID, Integer verStart, Boolean isClientOnly) throws Exception;

    /**
     * 获取一个应用组的设置项
     * */
    AppSettingModel getAppGroupSettingItem(Integer agroupID, String name) throws Exception;

    AppSettingModel getAppGroupSettingItemNoCache(Integer agroupID, String name) throws Exception;


    /**
     * 仅用于管理
     * */
    Boolean delAppGroupSettingItem(Integer agroupID, String name) throws Exception;


    /**
     * 设置一个应用组的设置项的值
     * */
    void setAppGroupSettingItem(Integer agroupID, String name, Integer type, String value, Integer verStart, Boolean isClient) throws Exception;

    //=======================

    /**
     * 检查应用版本更新
     */
    AppUpdateModel chkAppUpdate(Integer appID, Integer platform, Integer verID) throws SQLException;

    /**
     * 获取一个应用版本
     */
    AppVersionModel getAppVersionEx(Integer appID, Integer platform) throws SQLException;

    AppVersionModel getAppGroupVersion(Integer agroupID, Integer platform) throws SQLException;

    AppVersionModel getAppVersion(Integer appID, Integer platform) throws SQLException;


    //=======================

    /**
     * 获取接口状态码
     * */
    AppCodeCollection getAppCodes(Integer agroupID) throws SQLException;

    AppCodeCollection getAppCodesByLang(Integer agroupID, String lang) throws SQLException;

    /**
     * 获取一个Api Code 的描述
     * */
    String getAppCode(Integer agroupID, Integer code) throws SQLException;

    String getAppCodeByLang(Integer agroupID, Integer code, String lang) throws SQLException;


}
