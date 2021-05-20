package org.noear.rock.model;

import org.noear.rock.protocol.RockRpc;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用模型
 * */
public class AppModel {
    public static transient RockRpc rockclient;

    /**
     * 应用ID
     * */
    public int app_id;
    /**
     * 应用标识
     * */
    public String app_key;
    /**
     * 应用签名或加密密钥
     * */
    public String app_secret_key;

    /**
     * 应用组ID
     * */
    public int agroup_id;
    /**
     * 用户组ID
     * */
    public int ugroup_id;

    /**
     * 应用名称
     * */
    public String name;
    /**
     * 应用备注
     * */
    public String note;

    /**
     * 是否在审核中
     * */
    public int ar_is_examine;
    /**
     * 审核中的版本号
     * */
    public int ar_examine_ver;


    //是否为审核中

    //=============
    //
    //

    /**
     * 获取对应的应用组
     * */
    public AppGroupModel agroup() throws Exception {
        return rockclient.getAppGroup(agroup_id);
    }

    /**
     * 获取对应的用户组
     * */
    public UserGroupModel ugroup() throws Exception {
        return rockclient.getUserGroup(ugroup_id);
    }

    //=============
    //
    //
    private Map<Integer, AppSettingCollection> _setting = null;

    private void tryInitSetting(int ver) throws Exception {
        if (_setting == null) {
            _setting = new HashMap<>();
        }

        if (_setting.containsKey(ver) == false) {
            _setting.put(ver, rockclient.getAppSettingEx2(agroup_id, app_id, ver, false));
        }
    }

    private transient Map<Integer, AppSettingCollection> _client_setting = null;

    private void tryInitClientSetting(int ver) throws Exception {
        if (_client_setting == null) {
            _client_setting = new HashMap<>();

        }
        if (_client_setting.containsKey(ver) == false) {
            _client_setting.put(ver, rockclient.getAppSettingEx2(agroup_id, app_id, ver, true));
        }
    }


    /**
     * 获取所有设置集合
     * */
    public AppSettingCollection getSetting(int ver) throws Exception {
        tryInitSetting(ver);

        return _setting.get(ver);
    }


    /**
     * 获取客户端的设置集合
     * */
    public AppSettingCollection getClientSetting(int ver) throws Exception {
        tryInitClientSetting(ver);

        return _client_setting.get(ver);
    }

    private transient AppVersionModel _ver;

    /**
     * 获取一个版本号
     * */
    public AppVersionModel getVersion(int platform) throws Exception {
        if (_ver == null) {
            _ver = rockclient.getAppVersionEx(app_id, platform);
        }

        return _ver;
    }

    /**
     * 获取一个设置项
     * */
    public AppSettingModel getSetting(String key) throws Exception {
        tryInitSetting(0);

        if (_setting.get(0).containsKey(key)) {
            return _setting.get(0).get(key);
        } else {
            return null;
        }
    }
}