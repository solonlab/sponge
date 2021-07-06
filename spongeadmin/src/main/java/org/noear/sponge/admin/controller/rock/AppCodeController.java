package org.noear.sponge.admin.controller.rock;

import org.noear.snack.ONode;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.solon.auth.annotation.AuthRoles;
import org.noear.solon.core.handle.UploadedFile;
import org.noear.sponge.admin.controller.ViewModel;
import org.noear.sponge.admin.dso.AgroupCookieUtil;
import org.noear.sponge.admin.dso.BcfTagChecker;
import org.noear.sponge.admin.dso.Session;
import org.noear.sponge.admin.dso.SessionRoles;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.dso.db.DbRockI18nApi;
import org.noear.sponge.admin.model.TagCountsModel;
import org.noear.sponge.admin.model.rock.AppExCodeModel;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.model.rock.AppGroupModel;
import org.noear.sponge.admin.model.rock.I18nModel;
import org.noear.water.utils.*;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Mapping("/rock/apcode")
public class AppCodeController extends BaseController {

    //应用状态码跳转
    @Mapping("")
    public ModelAndView apcode(Context ctx, Integer agroup_id, String sev) throws SQLException {
        //by noear 20180516::添加应用组的权限控制
        BcfTagChecker checker = new BcfTagChecker();


        Integer out_agroup_id = agroup_id;
        if (out_agroup_id == null) {
            out_agroup_id = AgroupCookieUtil.cookieGet();
        } else {
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


        List<TagCountsModel> sevList = DbRockI18nApi.codeGetCounts(out_agroup_id);

        if (TextUtils.isEmpty(out_sev) && sevList.size() > 0) {
            out_sev = sevList.get(0).tag;
        }


        viewModel.put("app_groups", apGmap.values());
        viewModel.put("agroup_id", out_agroup_id);
        viewModel.put("sevList", sevList);
        viewModel.put("service", out_sev);

        return view("rock/apcode");
    }

    @Mapping("inner")
    public ModelAndView apcode_inner(Integer agroup_id, String service, Integer code_num, String lang) throws SQLException {
        List<TagCountsModel> langs = DbRockI18nApi.codeGetLangsByService(service);

        for (TagCountsModel m : langs) {
            if (TextUtils.isEmpty(m.tag)) {
                m.tag = "default";
            }
        }

        if (lang == null || "default".equals(lang)) {
            if (langs.size() > 0) {
                lang = langs.get(0).tag;
            }
        }

        if ("default".equals(lang)) {
            lang = "";
        }

        List<AppExCodeModel> list = DbRockI18nApi.codeGetListByService(service, code_num, lang);

        if (TextUtils.isEmpty(lang)) {
            lang = "default";
        }

        if (agroup_id == null) {
            agroup_id = 0;
        } else {
            AgroupCookieUtil.cookieSet(agroup_id);
        }

        viewModel.put("lang", lang);
        viewModel.put("langs", langs);
        viewModel.put("list", list);
        viewModel.put("code_num", code_num);
        viewModel.put("agroup_id", agroup_id);
        viewModel.put("service", service);


        return view("rock/apcode_inner");
    }


    //应用状态码编辑页面跳转
    @Mapping("edit")
    public ModelAndView editApcode(Integer row_id) throws SQLException {
        AppExCodeModel model = DbRockI18nApi.codeGetById(row_id);
        List<AppGroupModel> appGroups = DbRockApi.getAppGroup("");

        List<I18nModel> langs =  DbRockI18nApi.codeGetByName(model.service, model.code);

        viewModel.put("app_groups", appGroups);
        viewModel.put("model", model);
        viewModel.put("langs", ONode.stringify(langs));
        viewModel.put("agroup_id", model.agroup_id);

        return view("rock/apcode_edit");
    }


    //应用状态码新增编辑页面跳转
    @Mapping("add")
    public ModelAndView addApcode(Integer agroup_id, String service) throws SQLException {
        List<AppGroupModel> appGroups = DbRockApi.getAppGroup("");
        AppExCodeModel model = new AppExCodeModel();

        List<I18nModel> langs = new ArrayList<>();
        langs.add(new I18nModel());

        if (agroup_id != null) {
            model.agroup_id = agroup_id;
            model.service = service;
        }

        viewModel.put("app_groups", appGroups);
        viewModel.put("model", model);
        viewModel.put("langs", ONode.stringify(langs));
        viewModel.put("agroup_id", agroup_id);

        return view("rock/apcode_edit");
    }

    //应用状态码新增编辑ajax保存功能
    @AuthRoles(SessionRoles.role_admin)
    @Mapping("edit/ajax/save")
    public ViewModel saveApcode(Integer agroup_id, String service, Integer code, Integer codeOld, String items) throws SQLException {
        List<I18nModel> itemList = ONode.loadStr(items).toObjectList(I18nModel.class);

        boolean result = true;

        for (I18nModel m : itemList) {
            if (TextUtils.isEmpty(m.lang) && TextUtils.isEmpty(m.note)) {
                continue;
            }

            result = result && DbRockI18nApi.codeSave(agroup_id, service, code, codeOld, m.lang, m.note);
        }

        if (result) {
            return viewModel.code(1, "保存成功！");
        } else {
            return viewModel.code(0, "保存失败！");
        }
    }

    @AuthRoles(SessionRoles.role_admin)
    @Mapping("edit/ajax/del")
    public ViewModel delApcode(Integer agroup_id, String service, Integer code, Integer codeOld) throws SQLException {
        boolean result = DbRockI18nApi.codeDel(agroup_id, service, codeOld);

        if (result) {
            return viewModel.code(1, "保存成功！");
        } else {
            return viewModel.code(0, "保存失败！");
        }
    }

    @Mapping("ajax/export")
    public void ajaxExport(Context ctx, int agroup_id, String service, String ids) throws Exception {
        List<Object> ids2 = Arrays.asList(ids.split(","))
                .stream()
                .map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());

        List<AppExCodeModel> list = DbRockI18nApi.codeGetListByService(service, ids2);

        String jsonD = JsondUtils.encode("agroup_code", list);

        String filename2 = "agroup_code_" + agroup_id + "_" + service + "_" + Datetime.Now().getDate() + ".jsond";

        ctx.headerSet("Content-Disposition", "attachment; filename=\"" + filename2 + "\"");
        ctx.output(jsonD);
    }

    @AuthRoles(SessionRoles.role_admin)
    @Mapping("ajax/import")
    public ViewModel importFile(int agroup_id, String service, UploadedFile file) throws Exception {
        if (Session.current().isAdmin() == false) {
            return viewModel.code(0, "没有权限！");
        }

        String jsonD = IOUtils.toString(file.content);
        JsondEntity entity = JsondUtils.decode(jsonD);

        if (entity == null || "agroup_code".equals(entity.table) == false) {
            return viewModel.code(0, "数据不对！");
        }

        List<AppExCodeModel> list = entity.data.toObjectList(AppExCodeModel.class);

        for (AppExCodeModel m : list) {
            if (service == null) {
                service = m.service;
            }

            DbRockI18nApi.codeImp(agroup_id, service, m.code, m.lang, m.note);
        }

        return viewModel.code(1, "ok");
    }
}
