package org.noear.rock.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.noear.rock.protocol.RockRpc;

import java.util.HashMap;
import java.util.Map;

public class AppModel {
    public static transient RockRpc rockclient;

    public int app_id;
    public String app_key;
    public String app_secret_key;

    public int agroup_id;
    public int ugroup_id;

    public String name;
    public String note;

    public int ar_is_examine;
    public int ar_examine_ver;


    //是否为审核中

    //=============
    //
    //

    /**
     * 获取对应的应用组
     * */
    @JSONField(serialize = false)
    public AppGroupModel agroup() throws Exception {
        return rockclient.getAppGroup(agroup_id);
    }

    /**
     * 获取对应的用户组
     * */
    @JSONField(serialize = false)
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
    @JSONField(serialize = false)
    public AppSettingCollection getSetting(int ver) throws Exception {
        tryInitSetting(ver);

        return _setting.get(ver);
    }


    /**
     * 获取客户端的设置集合
     * */
    @JSONField(serialize = false)
    public AppSettingCollection getClientSetting(int ver) throws Exception {
        tryInitClientSetting(ver);

        return _client_setting.get(ver);
    }

    private transient AppVersionModel _ver;

    /**
     * 获取一个版本号
     * */
    @JSONField(serialize = false)
    public AppVersionModel getVersion(int platform) throws Exception {
        if (_ver == null) {
            _ver = rockclient.getAppVersionEx(app_id, platform);
        }

        return _ver;
    }

    /**
     * 获取一个设置项
     * */
    @JSONField(serialize = false)
    public AppSettingModel getSetting(String key) throws Exception {
        tryInitSetting(0);

        if (_setting.get(0).containsKey(key)) {
            return _setting.get(0).get(key);
        } else {
            return null;
        }
    }
}