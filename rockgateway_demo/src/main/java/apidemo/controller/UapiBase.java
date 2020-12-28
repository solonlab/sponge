package apidemo.controller;

import org.noear.solon.annotation.Inject;
import org.noear.solon.extend.validation.annotation.Valid;
import org.noear.sponge.rockgateway.RockUapi;
import apidemo.dso.db.UserService;
import apidemo.models.UserModel;

import java.util.LinkedHashMap;
import java.util.Map;

@Valid
public class UapiBase extends RockUapi {

    private UserModel _user = null;

    private String _g_lkey;
    private String g_lkey(){
        if(_g_lkey == null) {
            _g_lkey = ctx.param("g_lkey");
        }

        return _g_lkey;
    }

    @Inject
    protected UserService userService;

    protected final Map<String,Object> data = new LinkedHashMap<>();

    public UapiBase(){
        super();
    }


    public UserModel getUser() throws Exception {
        if (null == _user) {
            _user = userService.getUser(g_lkey());
        }

        if (null == _user) {
            _user = new UserModel();
        }

        return _user;
    }

    public long getUserID() {
        try {
            return getUser().user_id;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean isLogin() throws Exception {

        if (0 >= getUser().user_id) {
            return false;
        }

        return true;
    }
}
