package org.noear.sponge.admin.controller.rock;

import org.noear.rock.RockUtil;
import org.noear.snack.ONode;
import org.noear.solon.Utils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.solon.core.handle.UploadedFile;
import org.noear.solon.auth.annotation.AuthPermissions;
import org.noear.sponge.admin.Config;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.controller.ViewModel;
import org.noear.sponge.admin.dso.AgroupCookieUtil;
import org.noear.sponge.admin.dso.TagChecker;
import org.noear.sponge.admin.dso.Session;
import org.noear.sponge.admin.dso.SessionPerms;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.dso.db.DbRockI18nApi;
import org.noear.sponge.admin.model.TagCountsModel;
import org.noear.sponge.admin.model.rock.AppExI18nModel;
import org.noear.sponge.admin.model.rock.AppGroupModel;
import org.noear.sponge.admin.model.rock.I18nModel;
import org.noear.sponge.admin.utils.JsonUtils;
import org.noear.sponge.admin.utils.MapKeyComparator;
import org.noear.water.utils.*;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Mapping("/rock/api18n")
public class AppI18nController extends BaseController {
    static final String _i18n_lang = "_i18n.lang";
    static final String _i18n_bundle = "_i18n.bundle";

    //应用状态码跳转
    @Mapping("")
    public ModelAndView apcode(Context ctx, Integer agroup_id, String sev) throws SQLException {
        //by noear 20180516::添加应用组的权限控制
        TagChecker checker = new TagChecker();


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


        List<TagCountsModel> sevList = DbRockI18nApi.i18nGetCounts(out_agroup_id);

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
    public ModelAndView apcode_inner(Context ctx, Integer agroup_id, String service, String name, String lang) throws SQLException {
        if (agroup_id == null) {
            agroup_id = 0;
        } else {
            AgroupCookieUtil.cookieSet(agroup_id);
        }

        List<TagCountsModel> langs = DbRockI18nApi.i18nGetLangsByService(service);
        for (TagCountsModel m : langs) {
            if (TextUtils.isEmpty(m.tag)) {
                m.tag = "default";
            }
        }

        if (Utils.isEmpty(lang) || "default".equals(lang)) {
            lang = ctx.cookie("lang");
        }

        if (Utils.isEmpty(lang) || "default".equals(lang)) {
            if (langs.size() > 0) {
                lang = langs.get(0).tag;
            }
        }

        if ("default".equals(lang)) {
            lang = "";
        }

        List<AppExI18nModel> list = DbRockI18nApi.i18nGetListByService(service, name, lang);

        if (TextUtils.isEmpty(lang)) {
            lang = "default";
        }

        ctx.cookieSet("lang",lang);

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
        AppExI18nModel model = DbRockI18nApi.i18nGetById(row_id);
        List<AppGroupModel> appGroups = DbRockApi.getAppGroup("");

        List<I18nModel> langs =  DbRockI18nApi.i18nGetByName(model.service, model.name);

        if (TextUtils.isNotEmpty(model.note)) {
            model.note = model.note.replace("\n", "\\n");
        }

        Map lang_type = new TreeMap(new MapKeyComparator());
        lang_type.putAll(Config.cfg("lang_type").getProp().toMap());

        viewModel.put("app_groups", appGroups);
        viewModel.put("model", model);
        viewModel.put("lang_type", lang_type);
        viewModel.put("langs", ONode.stringify(langs));
        viewModel.put("agroup_id", model.agroup_id);

        return view("rock/api18n_edit");
    }


    //应用状态码新增编辑页面跳转
    @Mapping("add")
    public ModelAndView addApcode(Integer agroup_id, String service) throws SQLException {
        List<AppGroupModel> appGroups = DbRockApi.getAppGroup("");
        AppExI18nModel model = new AppExI18nModel();

        List<I18nModel> langs = new ArrayList<>();
        langs.add(new I18nModel());

        if (agroup_id != null) {
            model.agroup_id = agroup_id;
            model.service = service;
        }

        Map lang_type = new TreeMap(new MapKeyComparator());
        lang_type.putAll(Config.cfg("lang_type").getProp().toMap());

        viewModel.put("app_groups", appGroups);
        viewModel.put("model", model);
        viewModel.put("lang_type", lang_type);
        viewModel.put("langs", ONode.stringify(langs));
        viewModel.put("agroup_id", agroup_id);

        return view("rock/api18n_edit");
    }

    //应用状态码新增编辑ajax保存功能
    @AuthPermissions(SessionPerms.admin)
    @Mapping("edit/ajax/save")
    public ViewModel saveApi18n(Integer agroup_id, String service, String name,  String nameOld, String items) throws SQLException {
        List<I18nModel> itemList = ONode.loadStr(items).toObjectList(I18nModel.class);

        boolean result = true;

        for (I18nModel m : itemList) {
            if (TextUtils.isEmpty(m.lang) && TextUtils.isEmpty(m.note)) {
                continue;
            }

            result = result && DbRockI18nApi.i18nSave(agroup_id, service, name, nameOld, m.lang, m.note);
        }

        if (result) {
            RockUtil.delCacheForI18ns(service);

            return viewModel.code(1, "操作成功");
        } else {
            return viewModel.code(0, "操作失败");
        }
    }

    @AuthPermissions(SessionPerms.admin)
    @Mapping("edit/ajax/del")
    public ViewModel delApi18n(Integer agroup_id, String service, String name,  String nameOld) throws SQLException {
        boolean result = DbRockI18nApi.i18nDel(agroup_id, service, nameOld);

        if (result) {
            return viewModel.code(1, "操作成功");
        } else {
            return viewModel.code(0, "操作失败");
        }
    }

    @Mapping("ajax/export")
    public void ajaxExport(Context ctx, int agroup_id, String service, String fmt, String ids) throws Exception {
        List<Object> ids2 = Arrays.asList(ids.split(","))
                .stream()
                .map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());

        List<AppExI18nModel> list = DbRockI18nApi.i18nGetListByService(service, ids2);
        String filename = "agroup_i18n_" + agroup_id + "_" + service + "_" + Datetime.Now().getDate();

        if("jsond".equals(fmt)) {
            String data = JsondUtils.encode("agroup_i18n", list);
            String filename2 = filename + ".jsond";

            ctx.headerSet("Content-Disposition", "attachment; filename=\"" + filename2 + "\"");
            ctx.output(data);
            return;
        }

        Map<String, String> i18nMap = new LinkedHashMap<>();
        for (AppExI18nModel m1 : list) {
            i18nMap.put(m1.name, m1.note);
        }

        if(i18nMap.containsKey(_i18n_lang) == false){
            i18nMap.put(_i18n_lang, list.get(0).lang);
        }


        if("json".equals(fmt)){
            String data = JsonUtils.format(ONode.stringify(i18nMap));//格式化一下好看些
            String filename2 = filename + ".json";

            ctx.headerSet("Content-Disposition", "attachment; filename=\"" + filename2 + "\"");
            ctx.output(data);
            return;
        }

        if("properties".equals(fmt)){
            StringBuilder data = new StringBuilder();
            i18nMap.forEach((name, value)->{
                data.append(name).append("=").append(value.replace("\n","\\n")).append("\n");
            });

            String filename2 = filename + ".properties";

            ctx.headerSet("Content-Disposition", "attachment; filename=\"" + filename2 + "\"");
            ctx.output(data.toString());
            return;
        }

        if("yml".equals(fmt)){
            StringBuilder data = new StringBuilder();
            i18nMap.forEach((name, value)->{
                data.append(name);
                if (value.contains("'")) { // 如果有单引号，则用双引号
                    data.append(": \"").append(value.replace("\n", "\\n")).append("\"\n");
                } else {
                    data.append(": '").append(value.replace("\n", "\\n")).append("'\n");
                }
            });

            String filename2 = filename + ".yml";

            ctx.headerSet("Content-Disposition", "attachment; filename=\"" + filename2 + "\"");
            ctx.output(data.toString());
            return;
        }
    }

    @AuthPermissions(SessionPerms.admin)
    @Mapping("ajax/import")
    public ViewModel importFile(int agroup_id, String service, UploadedFile file) throws Exception {
        if ("jsond".equals(file.extension)) {
            return importFileForJsond(agroup_id, service, file);
        } else {
            return importFileForProfile(agroup_id, service, file);
        }
    }

    private ViewModel importFileForJsond(int agroup_id, String service, UploadedFile file) throws Exception {
        if (Session.current().isAdmin() == false) {
            return viewModel.code(0, "没有权限！");
        }

        String jsonD = IOUtils.toString(file.content);
        JsondEntity entity = JsondUtils.decode(jsonD);

        if (entity == null || "agroup_i18n".equals(entity.table) == false) {
            return viewModel.code(0, "数据不对！");
        }

        List<AppExI18nModel> list = entity.data.toObjectList(AppExI18nModel.class);

        for (AppExI18nModel m : list) {
            if (service == null) {
                service = m.service;
            }

            DbRockI18nApi.i18nImp(agroup_id, service, m.name, m.lang, m.note);
        }

        RockUtil.delCacheForI18ns(service);

        return viewModel.code(1, "ok");
    }

    private ViewModel importFileForProfile(int agroup_id, String service, UploadedFile file) throws Exception {
        String i18nStr = Utils.transferToString(file.content, "UTF-8");
        Properties i18n = Utils.buildProperties(i18nStr);

        //初始化 _i18n.lang (_开头可以排序在前)
        String lang = i18n.getProperty(_i18n_lang);
        if(Utils.isEmpty(lang)){
            lang = i18n.getProperty("rock.i18n.lang"); //兼容旧的
        }

        //初始化 _i18n.bundle
        if (Utils.isEmpty(service)) {
            service = i18n.getProperty(_i18n_bundle);

            if (Utils.isEmpty(service)) {
                service = i18n.getProperty("rock.i18n.service"); //兼容旧的
            }
        }

        if (Utils.isEmpty(service)) {
            return viewModel.code(0, "提示：缺少元信息配置");
        }


        if (DbRockApi.getAppGroupById(agroup_id).agroup_id != agroup_id) {
            return viewModel.code(0, "提示：应用组不存在");
        }

        if (lang == null) {
            lang = "";
        }


        boolean isOk = false;

        //去除元信息
        i18n.remove("rock.i18n.service");
        i18n.remove("rock.i18n.lang");
        i18n.remove(_i18n_bundle);
        i18n.remove(_i18n_lang);

        for (Object k : i18n.keySet()) {
            if (k instanceof String) {
                String name = (String) k;
                DbRockI18nApi.i18nImp(agroup_id, service, name, lang, i18n.getProperty(name));
                isOk = true;
            }
        }

        if (isOk) {
            RockUtil.delCacheForI18ns(service);
        }

        return viewModel.code(1, "导入成功");
    }
}
