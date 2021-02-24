package org.noear.sponge.admin.controller.rock;

import org.apache.http.util.TextUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.sponge.admin.dso.BcfTagChecker;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.model.others.resp.BaseResp;
import org.noear.sponge.admin.model.rock.AppExVersionModel;
import org.noear.sponge.admin.model.rock.AppGroupModel;
import org.noear.sponge.admin.controller.BaseController;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@Mapping("/rock/")
public class AppVerController extends BaseController {

    //应用版本发布页面跳转
    @Mapping("apver")
    public ModelAndView apver(Context ctx, Integer agroup_id) throws SQLException {
        //by noear 20180516::添加应用组的权限控制
        BcfTagChecker checker = new BcfTagChecker();

        List<AppGroupModel> agroups = DbRockApi.getAppGroup("");
        List<AppExVersionModel> apverCounts = DbRockApi.getApverCounts();
        Map<Integer, AppGroupModel> apGmap = new LinkedHashMap<>();

        Integer out_agroup_id = agroup_id;
        if (out_agroup_id == null) {
            out_agroup_id = Integer.parseInt(ctx.cookie("spongeadmin_agroup", "0"));
        }else {
            ctx.cookieSet("spongeadmin_agroup", String.valueOf(out_agroup_id));
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
        viewModel.put("apGmap", apGmap);
        viewModel.put("agroup_id", out_agroup_id);

        return view("rock/apver");
    }


    @Mapping("apver/inner")
    public ModelAndView apver_inner(Integer agroup_id, Integer _state) throws SQLException {
        Integer is_disabled = null;
        if (_state != null) {
            viewModel.put("_state", _state);

            if (_state == 1) {
                is_disabled = 0;
            } else if (_state == 2) {
                is_disabled = 1;
            }
        } else {
            is_disabled = 0; //默认只显示有效的
        }

        List<AppExVersionModel> apverList = DbRockApi.getApvers(agroup_id, is_disabled);
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


    @Mapping("apver/edit/ajax/save")
    public BaseResp editApver(Integer app_id, Integer row_id, Integer ver, String content, Integer type, Integer alert_ver, Integer force_ver, Integer platform, String url, Integer is_enable, Integer agroup_id) throws SQLException {
        BaseResp resp = new BaseResp();
        if (TextUtils.isEmpty(content) == false) {
            content = content.trim();
        }
        boolean result = DbRockApi.editApver(app_id, row_id, ver, content, type, alert_ver, force_ver, platform, url, is_enable, agroup_id);
        if (result) {
            resp.code = 1;
            resp.msg = "保存成功！";
        } else {
            resp.code = 0;
            resp.msg = "保存失败！";
        }
        return resp;
    }
}
