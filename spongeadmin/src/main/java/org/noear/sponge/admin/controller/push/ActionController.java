package org.noear.sponge.admin.controller.push;



import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Controller;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.sponge.admin.controller.ViewModel;
import org.noear.sponge.admin.dso.IDUtil;
import org.noear.sponge.admin.dso.db.DbPaasApi;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.dso.Session;
import org.noear.sponge.admin.dso.db.DbDoveApi;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.models.others.resp.BaseResp;
import org.noear.sponge.admin.models.rock.AppGroupModel;
import org.noear.sponge.admin.models.sponge.DoveMsgActionModel;

import java.sql.SQLException;
import java.util.List;

/**
 * 2018.08.02
 *
 * @author konar
 */

@Controller
@Mapping("push/action")
public class ActionController extends BaseController {

    @Mapping("")
    public ModelAndView action() throws SQLException {

        // 有Tag的app_group
        List<AppGroupModel> agroups = DbRockApi.getAppGroupWithTag();
        viewModel.set("agroups", agroups);

        // 默认打开
        if (agroups.size() > 0) {
            viewModel.set("agroup_id", agroups.get(0).agroup_id);
        }

        return view("push/action");
    }

    @Mapping("inner/{agroup_id}")
    public ModelAndView action_inner(int agroup_id) throws SQLException {

        List<DoveMsgActionModel> actions = DbDoveApi.getMsgActions(agroup_id);
        viewModel.set("actions", actions);

        return view("push/action_inner");
    }

    @Mapping("add/{agroup_id}")
    public ModelAndView action_add(int agroup_id) {

        viewModel.set("agroup_id", agroup_id);

        viewModel.set("is_edit", false);

        return view("push/action_add");
    }

    @Mapping("edit/{action_id}")
    public ModelAndView action_edit(int action_id) throws SQLException {

        DoveMsgActionModel action = DbDoveApi.getMsgAction(action_id);
        viewModel.set("action", action);

        viewModel.set("agroup_id", action.agroup_id);

        viewModel.set("is_edit", true);

        return view("push/action_add");
    }

    @Mapping("ajax/save")
    public BaseResp action_save(Integer action_id,Integer agroup_id,String name,String name_display,String code) {

        BaseResp resp = new BaseResp();

        resp.code = 1;
        resp.msg = "成功";

        try {

            String operator = Session.current().getUserName();
            if (0 == action_id) {
                action_id = IDUtil.buildDoveMsgActionID();

                DbDoveApi.addMsgAction(action_id,agroup_id,name,name_display,code,operator);

            } else {
                DbPaasApi.updatePaasFun(agroup_id,action_id,name,name_display,code);

                DbDoveApi.modMsgAction(action_id,agroup_id,name,name_display,code,operator);
            }

        } catch (Exception e) {
            resp.code = 0;
            resp.msg = e.getLocalizedMessage();
        }

        return resp;
    }

    @Mapping("ajax/del")
    public ViewModel action_del(Integer action_id) throws SQLException{
        boolean result = DbDoveApi.delMsgAction(action_id);

        if (result) {
            viewModel.put("code",1);
            viewModel.put("msg","删除成功");
        } else {
            viewModel.put("code",0);
            viewModel.put("msg","删除失败");
        }

        return viewModel;
    }
    
}
