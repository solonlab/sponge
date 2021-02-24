package org.noear.sponge.admin.controller.rock;

import org.apache.http.util.TextUtils;
import org.noear.rock.RockUtil;
import org.noear.solon.Utils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.solon.core.handle.UploadedFile;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.controller.ViewModel;
import org.noear.sponge.admin.dso.AgroupCookieUtil;
import org.noear.sponge.admin.dso.BcfTagChecker;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.dso.db.DbRockI18nApi;
import org.noear.sponge.admin.model.TagCountsModel;
import org.noear.sponge.admin.model.rock.AppExI18nModel;
import org.noear.sponge.admin.model.rock.AppGroupModel;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Controller
@Mapping("/rock/api18n")
public class AppI18nController extends BaseController {

    //应用状态码跳转
    @Mapping("")
    public ModelAndView apcode(Context ctx, Integer agroup_id, String sev) throws SQLException {
        //by noear 20180516::添加应用组的权限控制
        BcfTagChecker checker = new BcfTagChecker();


        Integer out_agroup_id = agroup_id;
        if (out_agroup_id == null) {
            out_agroup_id = AgroupCookieUtil.cookieGet();
        }else {
            AgroupCookieUtil.cookieSet(agroup_id);
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


        List<TagCountsModel> sevList = DbRockI18nApi.getApi18nCounts(out_agroup_id);

        if (TextUtils.isEmpty(out_sev) && sevList.size() > 0) {
            out_sev = sevList.get(0).tag;
        }


        viewModel.put("app_groups", apGmap.values());
        viewModel.put("agroup_id", out_agroup_id);
        viewModel.put("sevList", sevList);
        viewModel.put("service", out_sev);

        return view("rock/api18n");
    }

    @Mapping("inner")
    public ModelAndView apcode_inner(Integer agroup_id, String service, String name, String lang) throws SQLException {
        if (agroup_id == null) {
            agroup_id = 0;
        } else {
            AgroupCookieUtil.cookieSet(agroup_id);
        }

        List<TagCountsModel> langs = DbRockI18nApi.getApi18nLangsByService(service);
        for (TagCountsModel m : langs) {
            if (TextUtils.isEmpty(m.tag)) {
                m.tag = "default";
            }
        }

        if ("default".equals(lang)) {
            lang = "";
        }

        List<AppExI18nModel> list = DbRockI18nApi.getApi18nByService(service, name, lang);

        if (TextUtils.isEmpty(lang)) {
            lang = "default";
        }

        viewModel.put("lang", lang);
        viewModel.put("langs", langs);
        viewModel.put("list", list);
        viewModel.put("name", name);
        viewModel.put("agroup_id", agroup_id);
        viewModel.put("service", service);


        return view("rock/api18n_inner");
    }


    //应用状态码编辑页面跳转
    @Mapping("edit")
    public ModelAndView editApcode(Integer row_id) throws SQLException {
        AppExI18nModel model = DbRockI18nApi.getApi18nById(row_id);
        List<AppGroupModel> appGroups = DbRockApi.getAppGroup("");
        viewModel.put("app_groups", appGroups);
        viewModel.put("model", model);
        return view("rock/api18n_edit");
    }


    //应用状态码新增编辑页面跳转
    @Mapping("add")
    public ModelAndView addApcode(Integer agroup_id, String service) throws SQLException {
        List<AppGroupModel> appGroups = DbRockApi.getAppGroup("");
        AppExI18nModel model = new AppExI18nModel();
        if (agroup_id != null) {
            model.agroup_id = agroup_id;
            model.service = service;
        }

        viewModel.put("app_groups", appGroups);
        viewModel.put("model", model);
        viewModel.put("agroup_id", agroup_id);
        return view("rock/api18n_edit");
    }

    //应用状态码新增编辑ajax保存功能
    @Mapping("edit/ajax/save")
    public ViewModel saveApcode(Integer row_id, String name, String lang, String note, Integer agroup_id, String service) throws SQLException {

        boolean result = DbRockI18nApi.editApi18n(row_id, agroup_id, service, name, lang, note);

        if (result) {
            return viewModel.code(1, "保存成功！");
        } else {
            return viewModel.code(0, "保存失败！");
        }
    }

    @Mapping("ajax/import")
    public ViewModel importFile(UploadedFile file) throws SQLException{
        String i18nStr = Utils.getString(file.content, "UTF-8");
        Properties i18n = Utils.buildProperties(i18nStr);

        String agroup_id_str = i18n.getProperty("rock.i18n.agroup_id");
        String service = i18n.getProperty("rock.i18n.service");
        String lang = i18n.getProperty("rock.i18n.lang");

        if (Utils.isEmpty(agroup_id_str) || Utils.isEmpty(service)) {
            return viewModel.code(0, "提示：缺少元信息配置");
        }

        int agroup_id = Integer.parseInt(agroup_id_str);
        boolean isOk = false;

        if(DbRockApi.getAppGroupById(agroup_id).agroup_id != agroup_id){
            return viewModel.code(0, "提示：应用组不存在");
        }

        if(lang == null){
            lang = "";
        }

        //去除元信息
        i18n.remove("rock.i18n.agroup_id");
        i18n.remove("rock.i18n.service");
        i18n.remove("rock.i18n.lang");

        for (Object k : i18n.keySet()) {
            if (k instanceof String) {
                String name = (String) k;
                DbRockI18nApi.impApi18n(agroup_id, service, name, lang, i18n.getProperty(name));
                isOk = true;
            }
        }

        if(isOk) {
            RockUtil.delCacheForCodes(agroup_id);
        }

        return viewModel.code(1, "导入成功");
    }
}
