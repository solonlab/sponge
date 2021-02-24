package org.noear.sponge.admin.controller.rock;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.sponge.admin.controller.ViewModel;
import org.noear.sponge.admin.dso.AgroupCookieUtil;
import org.noear.sponge.admin.dso.BcfTagChecker;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.model.rock.AppExSettingModel;
import org.noear.sponge.admin.model.rock.AppGroupModel;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@Mapping("/rock/")
public class AppGroupSettingController extends BaseController {
    @Mapping("agsets")
    public ModelAndView agesets(Integer agroup_id) throws SQLException {

        //by noear 20180516::添加应用组的权限控制
        BcfTagChecker checker = new BcfTagChecker();

        List<AppGroupModel> agroups = DbRockApi.getAppGroup("");
        List<AppExSettingModel> apsetting = DbRockApi.getAppGroupCounts();

        Integer out_agroup_id = agroup_id;
        if (out_agroup_id == null) {
            out_agroup_id = AgroupCookieUtil.cookieGet();
        }else {
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
                apGmap.put(aps.agroup_id, apG);
            }
        }

        viewModel.put("apGmap", apGmap);
        viewModel.put("agroup_id", out_agroup_id);

        return view("rock/agsets");
    }

    //agsets的iframe页面。
    @Mapping("agsets/inner")
    public ModelAndView agesets_inner(Integer agroup_id, String name) throws SQLException {
        if (agroup_id == null) {
            agroup_id = 0;
        } else {
            AgroupCookieUtil.cookieSet(agroup_id);
        }

        List<AppExSettingModel> agsetsList = DbRockApi.getAppGroupSetsById(agroup_id, name);
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
    @Mapping("agsets/edit/ajax/save")
    public ViewModel saveAgsets(Integer row_id, String name, Integer type, String value, String note, Integer is_client, Integer ver_start, Integer agroup_id) throws SQLException {

        boolean result = DbRockApi.editAgsets(row_id, name, type, value, note, is_client, ver_start, agroup_id);

        if (result) {
            return viewModel.code(1, "保存成功！");
        } else {
            return viewModel.code(0, "保存失败！");
        }
    }
}
