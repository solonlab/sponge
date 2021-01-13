package org.noear.sponge.rockuapi;

import org.noear.sponge.rock.RockClient;
import org.noear.sponge.rock.models.AppModel;
import org.noear.sponge.rockuapi.interceptor.Attrs;
import org.noear.solon.annotation.Singleton;
import org.noear.solon.core.handle.Context;
import org.noear.water.utils.IPUtils;

import java.sql.SQLException;

@Singleton(false)
public class Uapi {
    protected final Context ctx;

    public Uapi(){
        ctx = Context.current();
    }

    /**
     * 接口名称（不一定会有）
     * */
    public String name(){
        if(ctx == null){
            return null;
        }else{
            return ctx.attr(Attrs.handler_name);
        }
    }

    public static <T extends Uapi> T current(){
        return (T) Context.current().controller();
    }


    private String _outs;
    private String _nouts;

    /**
     * 检查参数是否需要输出
     * */
    protected boolean isOut(String key) {
        if (this._outs == null) {
            this._outs = ctx.param("outs");
        }

        if (this._outs == null) {
            return false;
        } else {
            return this._outs.indexOf(key) > -1;
        }
    }

    /**
     * 检查是否不需要输出
     * */
    protected boolean isNotout(String key) {
        if (this._nouts == null) {
            this._nouts = ctx.param("nouts");
        }

        if (this._nouts == null) {
            return false;
        } else {
            return this._nouts.indexOf(key) > -1;
        }
    }


    ///////////

    private UapiParams _params;
    /**
     * 基础参数（不一定会有）
     * */
    public UapiParams params() {
        if (_params != null) {
            return _params;
        }

        if (ctx != null) {
            _params = ctx.attr(Attrs.params);
        }

        return _params;
    }

    /**
     * 未知错误
     * */
    public Throwable error(){
        return ctx.attr(Attrs.error);
    }

    private String _ip;
    public String ip() {
        if (_ip == null) {
            _ip = IPUtils.getIP(ctx);
        }

        return _ip;
    }

    private AppModel _app;
    public AppModel getApp() throws SQLException {
        if (_app == null) {
            if (ctx.attrMap().containsKey(Attrs.params)) {
                _app = params().getApp();
            }
        }

        if (_app == null) {
            _app = getApp(ctx.paramAsInt("appID"));
        }

        return _app;
    }

    public AppModel getApp(int appID) throws SQLException{
        return RockClient.getApp(appID);
    }
}
