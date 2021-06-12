package org.noear.sponge.admin.controller.rock;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.solon.auth.annotation.AuthRoles;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.dso.SessionRoles;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.model.others.resp.BaseResp;
import org.noear.sponge.admin.model.rock.AppGroupModel;
import org.noear.sponge.admin.model.rock.UserGroupModel;

import java.sql.SQLException;
import java.util.List;

@Controller
@Mapping("/rock/")
public class AppGroupController extends BaseController {
    @Mapping("agroup")
    public ModelAndView agroup(String name, Integer _state) throws SQLException {
        List<AppGroupModel> agroupList = DbRockApi.getAppGroup(name, _state);
        viewModel.put("agroupList",agroupList);
        return view("rock/agroup");
    }


    //新增应用组跳转
    @Mapping("agroup/add")
    public ModelAndView add_agroup() throws SQLException {
        List<UserGroupModel> ugroupList = DbRockApi.getUserGroup("");
        viewModel.put("ugroupList",ugroupList);
        viewModel.put("agroup",new AppGroupModel());
        return view("rock/agroup_edit");
    }

    //编辑应用组跳转
    @Mapping("agroup/edit")
    public ModelAndView edit_agroup(Integer agroup_id) throws SQLException {
        AppGroupModel appGroup = DbRockApi.getAppGroupById(agroup_id);
        List<UserGroupModel> ugroupList = DbRockApi.getUserGroup("");

        //如果被禁了，则尝试添加
        if (appGroup.ugroup_id > 0 && ugroupList.stream().noneMatch(m -> m.ugroup_id == appGroup.ugroup_id)) {
            UserGroupModel um = DbRockApi.getUserGroupById(appGroup.ugroup_id);
            if (um.ugroup_id > 0) {
                ugroupList.add(um);
            }
        }

        viewModel.put("ugroupList", ugroupList);
        viewModel.put("agroup", appGroup);
        return view("rock/agroup_edit");
    }

    //编辑或新增用户组
    @AuthRoles(SessionRoles.role_admin)
    @Mapping("agroup/edit/ajax/save")
    public BaseResp editAgroup(Integer agroup_id, Integer new_agroup_id, String name, String tag, Integer ugroup_id,Integer enable_track, Integer is_enabled) throws SQLException {
        BaseResp resp = new BaseResp();
        Boolean result = DbRockApi.editAgroup(agroup_id,new_agroup_id,name,tag,ugroup_id,enable_track, is_enabled);
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
