package org.noear.sponge.admin.controller.rock;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.solon.auth.annotation.AuthPermissions;
import org.noear.sponge.admin.dso.AgroupCookieUtil;
import org.noear.sponge.admin.dso.TagChecker;
import org.noear.sponge.admin.dso.SessionPerms;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.model.others.resp.BaseResp;
import org.noear.sponge.admin.model.rock.AppExVersionModel;
import org.noear.sponge.admin.model.rock.AppGroupModel;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.water.utils.TextUtils;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@Mapping("/rock/")
public class AppVerController extends BaseController {

    //应用版本发布页面跳转
    @Mapping("apver")
    public ModelAndView apver(Context ctx, Integer agroup_id, int _state) throws SQLException {
        //by noear 20180516::添加应用组的权限控制
        TagChecker checker = new TagChecker();

        List<AppGroupModel> agroups = DbRockApi.getAppGroup("");
        List<AppExVersionModel> apverCounts = DbRockApi.getApverCounts();
        Map<Integer, AppGroupModel> apGmap = new LinkedHashMap<>();

        Integer out_agroup_id = agroup_id;
        if (out_agroup_id == null) {
            out_agroup_id = AgroupCookieUtil.cookieGet();
        }else{
            AgroupCookieUtil.cookieSet(agroup_id);
        }

        for (AppGroupModel ap : agroups) {
            if (checker.find(ap.tag)) {
                apGmap.put(ap.agroup_id, ap);

                if (out_agroup_id == 0) {
                    out_agroup_id = ap.agroup_id;
                }
            }
        }
        for (AppExVersionModel aps : apverCounts) {
            if (apGmap.containsKey(aps.agroup_id)) {
                AppGroupModel apG = apGmap.get(aps.agroup_id);
                apG.counts = aps.counts;
                apGmap.put(aps.agroup_id, apG);
            }
        }

        viewModel.put("_state", _state);
        viewModel.put("apGmap", apGmap);
        viewModel.put("agroup_id", out_agroup_id);

        return view("rock/apver");
    }


    @Mapping("apver/inner")
    public ModelAndView apver_inner(int agroup_id, int _state) throws SQLException {
        List<AppExVersionModel> apverList = DbRockApi.getApvers(agroup_id, _state == 1);

        viewModel.put("_state", _state);
        viewModel.put("apverList", apverList);
        viewModel.put("agroup_id", agroup_id);

        return view("rock/apver_inner");
    }

    //新建更新页面跳转
    @Mapping("apver/add")
    public ModelAndView apver_add(Integer agroup_id) {
        viewModel.put("agroup_id", agroup_id);
        viewModel.put("apver", new AppExVersionModel());

        return view("rock/apver_edit");
    }

    //编辑更新页面跳转
    @Mapping("apver/edit")
    public ModelAndView apver_edit(Integer agroup_id, Integer row_id) throws SQLException {
        AppExVersionModel apver = DbRockApi.getApverByRowId(row_id);
        viewModel.put("apver", apver);
        viewModel.put("agroup_id", agroup_id);
        return view("rock/apver_edit");
    }


    @AuthPermissions(SessionPerms.admin)
    @Mapping("apver/edit/ajax/save")
    public BaseResp editApver(Integer app_id, Integer row_id, Integer ver, String content, Integer type, Integer alert_ver, Integer force_ver, Integer platform, String url, Integer is_disabled, Integer agroup_id) throws SQLException {
        BaseResp resp = new BaseResp();
        if (TextUtils.isEmpty(content) == false) {
            content = content.trim();
        }

        boolean result = DbRockApi.editApver(app_id, row_id, ver, content, type, alert_ver, force_ver, platform, url, is_disabled, agroup_id);

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
