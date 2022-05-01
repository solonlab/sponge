package org.noear.sponge.admin.controller.rock;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.solon.auth.annotation.AuthPermissions;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.dso.SessionPerms;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.model.others.resp.BaseResp;
import org.noear.sponge.admin.model.rock.UserGroupModel;

import java.sql.SQLException;
import java.util.List;

@Controller
@Mapping("/rock/")
public class UserGroupController extends BaseController {
    //用户组界面
    @Mapping("ugroup")
    public ModelAndView ugroup(String name, Integer _state) throws SQLException {
        List<UserGroupModel> ugroupList = DbRockApi.getUserGroup(name, _state);
        viewModel.put("ugroupList", ugroupList);
        viewModel.put("name", name);
        return view("rock/ugroup");
    }

    //新增用户组跳转
    @Mapping("ugroup/add")
    public ModelAndView add_ugroup() {
        viewModel.put("ugroup", new UserGroupModel());
        return view("rock/ugroup_edit");
    }

    //编辑用户组跳转
    @Mapping("ugroup/edit")
    public ModelAndView edit_ugroup(Integer ugroup_id) throws SQLException {
        UserGroupModel userGroup = DbRockApi.getUserGroupById(ugroup_id);
        viewModel.put("ugroup", userGroup);
        return view("rock/ugroup_edit");
    }

    //编辑或新增用户组
    @AuthPermissions(SessionPerms.admin)
    @Mapping("ugroup/edit/ajax/save")
    public BaseResp editUgroup(Integer ugroup_id, String name, Integer new_ugroup_id, int is_disabled) throws SQLException {
        BaseResp resp = new BaseResp();

        Boolean result = DbRockApi.editUgroup(ugroup_id, name, new_ugroup_id, is_disabled == 1);
        if (result) {
            resp.code = 1;
            resp.msg = "操作成功";
        } else {
            resp.code = 0;
            resp.msg = "操作失败";
        }
        return resp;
    }
}
