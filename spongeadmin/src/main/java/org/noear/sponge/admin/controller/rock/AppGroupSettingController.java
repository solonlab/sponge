package org.noear.sponge.admin.controller.rock;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.solon.auth.annotation.AuthPermissions;
import org.noear.solon.core.handle.UploadedFile;
import org.noear.sponge.admin.controller.ViewModel;
import org.noear.sponge.admin.dso.AgroupCookieUtil;
import org.noear.sponge.admin.dso.TagChecker;
import org.noear.sponge.admin.dso.Session;
import org.noear.sponge.admin.dso.SessionPerms;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.model.rock.AppExSettingModel;
import org.noear.sponge.admin.model.rock.AppGroupModel;
import org.noear.water.utils.Datetime;
import org.noear.water.utils.IOUtils;
import org.noear.water.utils.JsondEntity;
import org.noear.water.utils.JsondUtils;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Mapping("/rock/")
public class AppGroupSettingController extends BaseController {
    @Mapping("agsets")
    public ModelAndView agesets(Integer agroup_id, int _state) throws SQLException {

        //by noear 20180516::添加应用组的权限控制
        TagChecker checker = new TagChecker();

        List<AppGroupModel> agroups = DbRockApi.getAppGroup("");
        List<AppExSettingModel> apsetting = DbRockApi.getAppGroupCounts();

        Integer out_agroup_id = agroup_id;
        if (out_agroup_id == null) {
            out_agroup_id = AgroupCookieUtil.cookieGet();
        } else {
            AgroupCookieUtil.cookieSet(agroup_id);
        }

        Map<Integer, AppGroupModel> apGmap = new LinkedHashMap<>();
        for (AppGroupModel ap : agroups) {

            //检测是否有这个应用组的权限
            if (checker.find(ap.tag)) {
                apGmap.put(ap.agroup_id, ap);

                if (out_agroup_id == 0) {
                    out_agroup_id = ap.agroup_id;
                }
            }
        }

        for (AppExSettingModel aps : apsetting) {
            if (apGmap.containsKey(aps.agroup_id)) {
                AppGroupModel apG = apGmap.get(aps.agroup_id);
                apG.counts = aps.counts;
            }
        }

        viewModel.put("_state", _state);
        viewModel.put("apGmap", apGmap);
        viewModel.put("agroup_id", out_agroup_id);

        return view("rock/agsets");
    }

    //agsets的iframe页面。
    @Mapping("agsets/inner")
    public ModelAndView agesets_inner(Integer agroup_id, String name, int _state) throws SQLException {
        if (agroup_id == null) {
            agroup_id = 0;
        } else {
            AgroupCookieUtil.cookieSet(agroup_id);
        }

        List<AppExSettingModel> agsetsList = DbRockApi.getAppGroupSetsById(agroup_id, name, _state == 1);

        viewModel.put("_state", _state);
        viewModel.put("name", name);
        viewModel.put("agroup_id", agroup_id);
        viewModel.put("agsetsList", agsetsList);

        return view("rock/agsets_inner");
    }

    //agsets编辑页面跳转
    @Mapping("agsets/edit")
    public ModelAndView agsets_edit(Integer row_id, Integer agroup_id) throws SQLException {
        AppExSettingModel agsets = DbRockApi.getAgsetsById(row_id);
        viewModel.put("agroup_id", agroup_id);
        viewModel.put("agsets", agsets);

        return view("rock/agsets_edit");
    }

    //agsets新增页面跳转
    @Mapping("agsets/add")
    public ModelAndView agsets_add(Integer agroup_id) {
        viewModel.put("agroup_id", agroup_id);
        viewModel.put("agsets", new AppExSettingModel());
        return view("rock/agsets_edit");
    }

    //ajax保存编辑
    @AuthPermissions(SessionPerms.admin)
    @Mapping("agsets/edit/ajax/save")
    public ViewModel saveAgsets(Integer row_id, String name, Integer type, String value, String note, Integer is_client, Integer ver_start, Integer agroup_id, int is_disabled) throws SQLException {
        boolean result = DbRockApi.editAgsets(row_id, name, type, value, note, is_client, ver_start, agroup_id, is_disabled);

        if (result) {
            return viewModel.code(1, "操作成功");
        } else {
            return viewModel.code(0, "操作失败");
        }
    }


    @AuthPermissions(SessionPerms.operator)
    @Mapping("agsets/ajax/import")
    public ViewModel ajaxImport(int agroup_id, UploadedFile file) throws Exception {
        if (Session.current().isAdmin() == false) {
            return viewModel.code(0, "没有权限！");
        }

        String jsonD = IOUtils.toString(file.getContent());
        JsondEntity entity = JsondUtils.decode(jsonD);

        if (entity == null || "agroup_setting".equals(entity.table) == false) {
            return viewModel.code(0, "数据不对！");
        }

        List<AppExSettingModel> list = entity.data.toBeanList(AppExSettingModel.class);

        for (AppExSettingModel m : list) {
            DbRockApi.impAgsets(agroup_id, m);
        }

        return viewModel.code(1, "ok");
    }

    @Mapping("agsets/ajax/export")
    public void ajaxExport(Context ctx, int agroup_id, String ids) throws Exception {
        List<Object> ids2 = Arrays.asList(ids.split(","))
                .stream()
                .map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());

        List<AppExSettingModel> list = DbRockApi.getAppGroupSetsList(agroup_id, ids2);

        String jsonD = JsondUtils.encode("agroup_setting", list);

        String filename2 = "agroup_setting_" + agroup_id + "_" + Datetime.Now().getDate() + ".jsond";

        ctx.headerSet("Content-Disposition", "attachment; filename=\"" + filename2 + "\"");
        ctx.output(jsonD);
    }


    @AuthPermissions(SessionPerms.operator)
    @Mapping("agsets/ajax/batch")
    public ViewModel ajaxBatch(int act, String ids) throws Exception {
        try {
            List<Object> ids2 = Arrays.asList(ids.split(","))
                    .stream()
                    .map(s -> Integer.parseInt(s))
                    .collect(Collectors.toList());

            DbRockApi.delAppGroupSets(act, ids2);

            return viewModel.code(1, "ok");
        } catch (Throwable e) {
            return viewModel.code(0, e.getLocalizedMessage());
        }
    }
}
