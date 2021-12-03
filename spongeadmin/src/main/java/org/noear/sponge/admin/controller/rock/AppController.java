package org.noear.sponge.admin.controller.rock;

import org.noear.solon.Utils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.solon.auth.annotation.AuthPermissions;
import org.noear.sponge.admin.dso.AgroupCookieUtil;
import org.noear.sponge.admin.dso.TagChecker;
import org.noear.sponge.admin.dso.IDUtil;
import org.noear.sponge.admin.dso.SessionPerms;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.model.others.resp.BaseResp;
import org.noear.sponge.admin.model.rock.AppGroupModel;
import org.noear.sponge.admin.model.rock.AppModel;
import org.noear.sponge.admin.model.rock.UserGroupModel;
import org.noear.sponge.admin.controller.BaseController;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@Mapping("/rock/")
public class AppController extends BaseController {

    @Mapping("app")
    public ModelAndView app(Context ctx, Integer agroup_id) throws SQLException {

        //by noear 20180516::添加应用组的权限控制
        TagChecker checker = new TagChecker();

        List<AppGroupModel> agroups = DbRockApi.getAppGroup("");
        List<AppModel> appCounts = DbRockApi.getAppCounts();
        Map<Integer, AppGroupModel> apGmap = new LinkedHashMap<>();

        Integer out_agroup_id = agroup_id;
        if (out_agroup_id == null) {
            out_agroup_id = AgroupCookieUtil.cookieGet();
        } else {
            AgroupCookieUtil.cookieSet(agroup_id);
        }

        for (AppGroupModel ap : agroups) {
            //检测是否有这个应用组的权限
            if (checker.find(ap.tag)) {
                apGmap.put(ap.agroup_id, ap);

                if (out_agroup_id == 0) {
                    out_agroup_id = ap.agroup_id;
                }
            }
        }

        for (AppModel aps : appCounts) {
            if (apGmap.containsKey(aps.agroup_id)) {
                AppGroupModel apG = apGmap.get(aps.agroup_id);
                apG.counts = aps.counts;
                apGmap.put(aps.agroup_id, apG);
            }
        }

        viewModel.put("apGmap", apGmap);
        viewModel.put("agroup_id", out_agroup_id);

        return view("rock/app");
    }

    @Mapping("app/inner")
    public ModelAndView app_inner(String name, Integer agroup_id, Integer _state) throws SQLException {
        Integer ar_is_examine = null;
        if (_state != null) {
            viewModel.put("_state", _state);
            if (_state == 1) {
                ar_is_examine = 0;
            } else if (_state == 2) {
                ar_is_examine = 1;
            }
        }

        if (agroup_id == null) {
            agroup_id = 0;
        } else {
            AgroupCookieUtil.cookieSet(agroup_id);
        }

        List<AppModel> appList = DbRockApi.getApps(name, agroup_id, ar_is_examine);
        viewModel.put("appList", appList);
        viewModel.put("name", name);
        viewModel.put("agroup_id", agroup_id);
        return view("rock/app_inner");
    }

    //应用编辑页面跳转
    @Mapping("app/edit")
    public ModelAndView editApp(Integer app_id) throws SQLException {
        AppModel app = DbRockApi.getAppById(app_id);

        List<UserGroupModel> userGroups = DbRockApi.getUserGroup("");

        //如果被禁了，则尝试添加
        if (app.ugroup_id > 0 && userGroups.stream().noneMatch(m -> m.ugroup_id == app.ugroup_id)) {
            UserGroupModel um = DbRockApi.getUserGroupById(app.ugroup_id);
            if (um.ugroup_id > 0) {
                userGroups.add(um);
            }
        }

        viewModel.put("user_groups", userGroups);
        viewModel.put("agroup_id", app.agroup_id);
        viewModel.put("appEdit", app);

        return view("rock/app_edit");
    }

    //应用新增编辑页面跳转
    @Mapping("app/add")
    public ModelAndView addApp(int agroup_id) throws SQLException {
        List<UserGroupModel> userGroups = DbRockApi.getUserGroup("");
        AppModel appEdit = new AppModel();
        if (agroup_id > 0) {
            appEdit.agroup_id = agroup_id;
        }

        appEdit.app_key = IDUtil.buildGuid();
        appEdit.app_secret_key = IDUtil.getAppSecretkey();
        appEdit.app_secret_salt = IDUtil.getAppSecretkey();

        viewModel.put("user_groups", userGroups);
        viewModel.put("agroup_id", agroup_id);
        viewModel.put("appEdit", appEdit);

        return view("rock/app_edit");
    }

    //应用新增编辑ajax保存功能
    @AuthPermissions(SessionPerms.admin)
    @Mapping("app/edit/ajax/save")
    public BaseResp saveApp(Integer app_id, String name, Integer agroup_id, Integer ugroup_id, String app_key, String app_secret_key, String app_secret_salt, Integer ar_is_examine, Integer ar_is_setting, String note, Integer ar_examine_ver) throws SQLException {
        BaseResp resp = new BaseResp();
        app_key = app_key.trim();
        app_secret_key = app_secret_key.trim();
        app_secret_salt = app_secret_salt.trim();

        if (Utils.isEmpty(app_key)) {
            app_key = IDUtil.buildGuid();
        }

        if (Utils.isEmpty(app_secret_key)) {
            app_secret_key = IDUtil.getAppSecretkey();
        }

        if (Utils.isEmpty(app_secret_salt)) {
            app_secret_salt = IDUtil.getAppSecretkey();
        }

        boolean result = DbRockApi.editApp(app_id, name, agroup_id, ugroup_id, app_key, app_secret_key, app_secret_salt, ar_is_examine, ar_is_setting, note, ar_examine_ver);

        if (result) {
            resp.code = 1;
            resp.msg = "操作成功";
        } else {
            resp.code = 0;
            resp.msg = "操作失败";
        }
        return resp;
    }
}
