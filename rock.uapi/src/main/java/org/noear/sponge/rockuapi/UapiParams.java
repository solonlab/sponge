package org.noear.sponge.rockuapi;

import org.noear.sponge.rock.RockClient;
import org.noear.sponge.rock.models.AppModel;
import org.noear.solon.core.handle.Context;
import org.noear.water.utils.TextUtils;

import java.sql.SQLException;

public class UapiParams {
    private Context ctx;

    public UapiParams(Context ctx){
        this.ctx = ctx;
    }

    //原始参数
    public String org_param;
    //原始令牌
    public String org_token;

    //签名（由客户端传入）
    public String sgin;
    //通道ID（由客户端传入）
    public int appID;
    //版本ID（由客户端传入）
    public int verID;


    public int agroup_id() throws SQLException {
        if(appID >0) {
            return getApp().agroup_id;
        }else{
            return 0;
        }
    }

    private AppModel _app;
    /**
     * 当前通道信息（通过appID获取）
     * */
    public AppModel getApp() throws SQLException {
        if (_app == null) {
            if (appID > 0) {
                _app = RockClient.getAppByID(appID);
            } else {
                String akey = ctx.param("akey");
                if (TextUtils.isEmpty(akey) == false) {
                    _app = RockClient.getAppByKey(akey);
                }
            }
        }
        return _app;
    }
}
