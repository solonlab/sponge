package org.noear.sponge.admin.controller.rock;

import org.apache.http.util.TextUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.sponge.admin.controller.ViewModel;
import org.noear.sponge.admin.dso.BcfTagChecker;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.model.TagCountsModel;
import org.noear.sponge.admin.model.rock.AppExCodeModel;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.model.rock.AppGroupModel;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@Mapping("/rock/")
public class AppCodeController extends BaseController {

    //应用状态码跳转
    @Mapping("apcode")
    public ModelAndView apcode(Integer agroup_id, String sev) throws SQLException {
        //by noear 20180516::添加应用组的权限控制
        BcfTagChecker checker = new BcfTagChecker();


        Integer out_agroup_id = agroup_id;
        if (out_agroup_id == null) {
            out_agroup_id = 0;
        }

        String out_sev = sev;
        if (out_sev == null) {
            out_sev = "";
        }


        Map<Integer, AppGroupModel> apGmap = new LinkedHashMap<>();
        List<AppGroupModel> app_temp = DbRockApi.getAppGroup(null);
        for (AppGroupModel ap : app_temp) {
            //检测是否有这个应用组的权限
            if (checker.find(ap.tag)) {
                apGmap.put(ap.agroup_id, ap);

                if (out_agroup_id == 0) {
                    out_agroup_id = ap.agroup_id;
                }
            }
        }


        List<TagCountsModel> sevList = DbRockApi.getApCodeCounts(out_agroup_id);

        if (TextUtils.isEmpty(out_sev) && sevList.size() > 0) {
            out_sev = sevList.get(0).tag;
        }


        viewModel.put("app_groups", apGmap.values());
        viewModel.put("agroup_id", out_agroup_id);
        viewModel.put("sevList", sevList);
        viewModel.put("service", out_sev);

        return view("rock/apcode");
    }

    @Mapping("apcode/inner")
    public ModelAndView apcode_inner(Integer agroup_id, String service , Integer code_num, String lang) throws SQLException {
        List<TagCountsModel> langs = DbRockApi.getApcodeLangsByService(service);
        for (TagCountsModel m : langs) {
            if (TextUtils.isEmpty(m.tag)) {
                m.tag = "default";
            }
        }

        if ("default".equals(lang)) {
            lang = "";
        }

        List<AppExCodeModel> codes = DbRockApi.getApcodeByAgroupId(agroup_id, code_num, lang);

        if (TextUtils.isEmpty(lang)) {
            lang = "default";
        }

        viewModel.put("lang", lang);
        viewModel.put("langs", langs);
        viewModel.put("codes", codes);
        viewModel.put("code_num", code_num);
        viewModel.put("agroup_id", agroup_id);
        viewModel.put("service",service);


        return view("rock/apcode_inner");
    }


    //应用状态码编辑页面跳转
    @Mapping("apcode/edit")
    public ModelAndView editApcode(Integer row_id) throws SQLException {
        AppExCodeModel code = DbRockApi.getApCodeById(row_id);
        List<AppGroupModel> appGroups = DbRockApi.getAppGroup("");
        viewModel.put("app_groups",appGroups);
        viewModel.put("code",code);
        return view("rock/apcode_edit");
    }


    //应用状态码新增编辑页面跳转
    @Mapping("apcode/add")
    public ModelAndView addApcode(Integer agroup_id, String service) throws SQLException {
        List<AppGroupModel> appGroups = DbRockApi.getAppGroup("");
        AppExCodeModel code = new AppExCodeModel();
        if (agroup_id!=null) {
            code.agroup_id = agroup_id;
            code.service = service;
        }

        viewModel.put("app_groups",appGroups);
        viewModel.put("code",code);
        viewModel.put("agroup_id",agroup_id);
        return view("rock/apcode_edit");
    }

    //应用状态码新增编辑ajax保存功能
    @Mapping("apcode/edit/ajax/save")
    public ViewModel saveApcode(Integer row_id, Integer code, String lang, String note, Integer agroup_id, String service) throws SQLException {

        boolean result = DbRockApi.editApcode(row_id,agroup_id, service,code,lang,note);

        if (result) {
            return viewModel.code(1, "保存成功！");
        } else {
            return viewModel.code(0, "保存失败！");
        }
    }
}
