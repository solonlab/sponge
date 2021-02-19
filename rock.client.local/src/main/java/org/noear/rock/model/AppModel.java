package org.noear.rock.model;

import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;
import org.noear.rock.AppSettingCollection;
import org.noear.rock.RockClient;

import java.util.HashMap;
import java.util.Map;

/// <summary>
/// 生成:2017/05/25 09:41:35
/// 
/// </summary>
public class AppModel implements IBinder
{
    public int app_id;
    public String app_key;
    public String app_secret_key;

    public int agroup_id;
    public int ugroup_id;

    public String name;
    public String note;

    private int _ar_is_examine;
    private int _ar_examine_ver;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        app_id = s.get("app_id").value(0);
        app_key = s.get("app_key").value(null);
        app_secret_key = s.get("app_secret_key").value(null);

        agroup_id = s.get("agroup_id").value(0);
        ugroup_id = s.get("ugroup_id").value(0);

        name = s.get("name").value(null);
        note = s.get("note").value(null);

        _ar_is_examine = s.get("ar_is_examine").value(0);
        _ar_examine_ver = s.get("ar_examine_ver").value(0);
	}

	public IBinder clone()
	{
		return new AppModel();
	}

    //是否为审核中
    public int ar_is_examine() throws Exception {
        return _ar_is_examine;
    }

    //审核中的版本号
    public int ar_examine_ver() throws Exception {
        return _ar_examine_ver;
    }

	//=============
    //
    //

	//获取对应的应用组
	public AppGroupModel agroup() throws Exception {
        return RockClient.getAppGroup(agroup_id);
    }

    //获取对应的用户组
    public UserGroupModel ugroup() throws Exception {
        return RockClient.getUserGroup(ugroup_id);
    }

    //=============
    //
    //
    private Map<Integer,AppSettingCollection> _setting = null;
	private void tryInitSetting(int ver) throws Exception {
	    if(_setting == null){
            _setting = new HashMap<>();
        }

        if(_setting.containsKey(ver) == false){
            _setting.put(ver, RockClient.getAppSettingEx2(agroup_id, app_id, ver, false));
        }
    }
    private Map<Integer,AppSettingCollection> _client_setting = null;
    private void tryInitClientSetting(int ver) throws Exception {
        if(_client_setting == null){
            _client_setting = new HashMap<>();

        }
        if (_client_setting.containsKey(ver) == false) {
            _client_setting.put(ver, RockClient.getAppSettingEx2(agroup_id, app_id, ver, true));
        }
    }


    //获取所有设置集合
	public AppSettingCollection getSetting(int ver) throws Exception {
        tryInitSetting(ver);

        return _setting.get(ver);
    }


	//获取客户端的设置集合
    public AppSettingCollection getClientSetting(int ver) throws Exception {
        tryInitClientSetting(ver);

        return _client_setting.get(ver);
    }

    private AppVersionModel _ver;
    public AppVersionModel getVersion(int platform) throws Exception {
        if (_ver == null) {
            _ver = RockClient.getAppVersionEx(app_id, platform);
        }

        return _ver;
    }

    //获取一个设置项
    public AppSettingModel get(String key) throws Exception {
        tryInitSetting(0);

        if(_setting.get(0).containsKey(key)){
           return _setting.get(0).get(key);
        }else {
            return null;
        }
    }
}