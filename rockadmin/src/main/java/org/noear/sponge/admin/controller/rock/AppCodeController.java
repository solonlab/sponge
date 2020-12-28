package org.noear.sponge.admin.controller.rock;

import org.apache.http.util.TextUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.sponge.admin.dso.BcfTagChecker;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.models.TagCountsModel;
import org.noear.sponge.admin.models.rock.AppExCodeModel;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.models.others.resp.BaseResp;
import org.noear.sponge.admin.models.rock.AppGroupModel;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@Mapping("/rock/")
public class AppCodeController extends BaseController {

    //应用状态码跳转
    @Mapping("apcode")
    public ModelAndView apcode(Integer agroup_id) throws SQLException {
        //by xyj 20180516::添加应用组的权限控制
        BcfTagChecker checker = new BcfTagChecker();

        List<AppGroupModel> agroups = DbRockApi.getAppGroup("", 0);
        List<AppExCodeModel> apcodeCounts = DbRockApi.getApCodeCounts();
        Map<Integer, AppGroupModel> apGmap = new LinkedHashMap<>();

        Integer out_agroup_id = agroup_id;
        if (out_agroup_id == null) {
            out_agroup_id = 0;
        }


        for (AppGroupModel ap : agroups) {
            if (checker.find(ap.tag)) {
                apGmap.put(ap.agroup_id, ap);

                if (out_agroup_id == 0) {
                    out_agroup_id = ap.agroup_id;
                }
            }
        }

        for (AppExCodeModel aps : apcodeCounts) {
            if (apGmap.containsKey(aps.agroup_id)) {
                AppGroupModel apG = apGmap.get(aps.agroup_id);
                apG.counts = aps.counts;
                apGmap.put(aps.agroup_id, apG);
            }
        }
        viewModel.put("apGmap", apGmap);
        viewModel.put("agroup_id", out_agroup_id);

        return view("rock/apcode");
    }

    @Mapping("apcode/inner")
    public ModelAndView apcode_inner(Integer agroup_id,Integer code_num, String lang) throws SQLException {
        List<TagCountsModel> langs =  DbRockApi.getApcodeLangsByAgroupId(agroup_id);
        for(TagCountsModel m: langs){
            if(TextUtils.isEmpty(m.tag)){
                m.tag = "default";
            }
        }

        if("default".equals(lang)){
            lang="";
        }

        List<AppExCodeModel> codes = DbRockApi.getApcodeByAgroupId(agroup_id,code_num, lang);

        if(TextUtils.isEmpty(lang)){
            lang = "default";
        }

        viewModel.put("lang",lang);
        viewModel.put("langs",langs);
        viewModel.put("codes",codes);
        viewModel.put("code_num",code_num);
        viewModel.put("agroup_id",agroup_id);


        return  view("rock/apcode_inner");
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
    public ModelAndView addApcode(Integer agroup_id) throws SQLException {
        List<AppGroupModel> appGroups = DbRockApi.getAppGroup("");
        AppExCodeModel code = new AppExCodeModel();
        if (agroup_id!=null) {
            code.agroup_id = agroup_id;
        }
        viewModel.put("app_groups",appGroups);
        viewModel.put("code",code);
        viewModel.put("agroup_id",agroup_id);
        return view("rock/apcode_edit");
    }

    //应用状态码新增编辑ajax保存功能
    @Mapping("apcode/edit/ajax/save")
    public BaseResp saveApcode(Integer row_id, Integer code, String lang, String note, Integer agroup_id) throws SQLException {
        BaseResp resp = new BaseResp();
        boolean result = DbRockApi.editApcode(row_id,agroup_id,code,lang,note);
        if (result){
            resp.code = 1;
            resp.msg = "保存成功！";
        } else {
            resp.code = 0;
            resp.msg = "保存失败！";
        }
        return resp;
    }
}
