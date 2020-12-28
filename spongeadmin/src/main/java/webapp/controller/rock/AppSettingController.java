package webapp.controller.rock;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;
import webapp.controller.BaseController;
import webapp.dso.BcfTagChecker;
import webapp.dso.db.DbRockApi;
import webapp.models.others.resp.BaseResp;
import webapp.models.rock.AppExSettingModel;
import webapp.models.rock.AppGroupModel;
import webapp.models.rock.AppModel;

import java.sql.SQLException;
import java.util.*;

@Controller
@Mapping("/rock/")
public class AppSettingController extends BaseController {


    @Mapping("apsets")
    public ModelAndView apsets(Integer app_id, Integer agroup_id) throws SQLException {

        //by xyj 20180516::添加应用组的权限控制
        BcfTagChecker checker = new BcfTagChecker();

        Integer out_agroup_id = agroup_id;
        if (out_agroup_id == null) {
            out_agroup_id = 0;
        }

        Integer out_app_id = app_id;
        if (out_app_id == null) {
            out_app_id = 0;
        }

        Map<Integer, AppGroupModel> apGmap = new LinkedHashMap<>();
        List<AppGroupModel> app_temp = DbRockApi.getAppGroup(null);
        for (AppGroupModel ap : app_temp) {
            //检测是否有这个应用组的权限
            if (checker.find(ap.tag)) {
                apGmap.put(ap.agroup_id,ap);

                if (out_agroup_id == 0) {
                    out_agroup_id = ap.agroup_id;
                }
            }
        }

        if(out_app_id>0){
            out_agroup_id = DbRockApi.getAppById(out_app_id).agroup_id;
        }

        List<AppModel> apps = new ArrayList<>();
        List<AppModel> tmp_apps = DbRockApi.getAppsByAgroupIdForSetting(out_agroup_id);
        for (AppModel ap : tmp_apps) {
            if (apGmap.containsKey(ap.agroup_id)) {
                apps.add(ap);

                if (out_app_id == 0) {
                    out_app_id = ap.app_id;
                }
            }
        }


        viewModel.put("appList", apps);
        viewModel.put("app_id", out_app_id);

        viewModel.put("agroup_id", out_agroup_id);
        viewModel.put("app_groups", apGmap.values());
        return view("rock/apsets");
    }

    @Mapping("apsets/inner")
    public ModelAndView apsets_inner(Integer app_id,String name)throws SQLException{
        if(app_id==null){
            app_id = 0;
        }

        List<AppExSettingModel> apsetsList = DbRockApi.getAppSets(app_id,name);
        viewModel.put("apsetsList",apsetsList);
        viewModel.put("app_id",app_id);
        viewModel.put("name",name);
        return view("rock/apsets_inner");
    }


    //apsets编辑页面跳转
    @Mapping("apsets/edit")
    public ModelAndView apsets_edit(Integer row_id,Integer app_id) throws SQLException {
        AppExSettingModel apsets = DbRockApi.getAppsetsById(row_id);
        viewModel.put("app_id",app_id);
        viewModel.put("apsets",apsets);
        return view("rock/apsets_edit");
    }

    //apsets新增页面跳转
    @Mapping("apsets/add")
    public ModelAndView apsets_add(Integer app_id){
        viewModel.put("app_id",app_id);
        viewModel.put("apsets",new AppExSettingModel());
        return view("rock/apsets_edit");
    }

    //ajax保存编辑
    @Mapping("apsets/edit/ajax/save")
    public BaseResp saveAppsets(Integer row_id, String name, Integer type, String value, String note,Integer is_client, Integer ver_start, Integer app_id) throws SQLException {
        BaseResp resp = new BaseResp();
        boolean result = DbRockApi.editAppsets(row_id, name, type, value, note, is_client, ver_start, app_id);
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
