package org.noear.sponge.admin.controller.push;


import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.sponge.admin.dso.IDUtil;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.models.others.CountModel;
import org.noear.sponge.admin.models.sponge.DoveMsgLinkModel;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.dso.Session;
import org.noear.sponge.admin.dso.db.DbDoveApi;
import org.noear.sponge.admin.models.others.resp.BaseResp;
import org.noear.sponge.admin.models.rock.AppGroupModel;
import org.noear.sponge.admin.models.sponge.DoveMsgActionModel;
import org.noear.sponge.admin.models.sponge.DoveMsgModel;

import java.sql.SQLException;
import java.util.List;


@Controller
@Mapping("push/msg")
public class MsgController extends BaseController {

    @Mapping("")
    public ModelAndView msg() throws SQLException {

        // 有Tag的app_group
        List<AppGroupModel> agroups = DbRockApi.getAppGroupWithTag();
        viewModel.set("agroups", agroups);

        // 默认打开
        if (agroups.size() > 0) {
            viewModel.set("agroup_id", agroups.get(0).agroup_id);
        }

        return view("push/msg");
    }

    @Mapping("inner/{agroup_id}")
    public ModelAndView msg_inner( int agroup_id, Integer page, Integer page_size, Integer _state) throws SQLException {

        if (null == page || 1 > page) {
            page = 1;
        }
        if (null == page_size || 0 == page_size) {
            page_size = 10;
        }
        if (null == _state || 0 > _state || 3 < _state) {
            _state = 0;
        }

        CountModel countModel = new CountModel();
        List<DoveMsgModel> msgs = DbDoveApi.getMsgs(agroup_id, _state + 1, page, page_size, countModel);

        viewModel.set("msgs", msgs);

        viewModel.set("page_size", page_size);
        viewModel.set("row_count", countModel.getCount());

        viewModel.set("agroup_id", agroup_id);

        // 有Tag的app_group
        List<AppGroupModel> agroups = DbRockApi.getAppGroupWithTag();
        viewModel.set("agroups", agroups);

        return view("push/msg_inner");
    }

    @Mapping("add/{agroup_id}")
    public ModelAndView msg_add( int agroup_id) throws Exception {

        AppGroupModel agroup = DbRockApi.getAppGroupById(agroup_id);

        List<DoveMsgLinkModel> links = DbDoveApi.getMsgLinks(agroup_id);
        List<DoveMsgActionModel> actions = DbDoveApi.getMsgActions(agroup_id);
        viewModel.set("links", links);
        viewModel.set("actions", actions);

//        List<SchemeModel> rules = RubberClient.getSchemesByTag(NameBuildUtil.buildPushTagName(agroup.tag));
//        List<ModelModel> models = RubberClient.getModelsByTag(NameBuildUtil.buildPushTagName(agroup.tag));
//        viewModel.set("rules", rules);
//        viewModel.set("models", models);

        viewModel.set("agroup_id", agroup_id);
        viewModel.set("agroup", agroup);

        viewModel.set("is_edit", false);

        return view("push/msg_add");
    }

    @Mapping("edit/{msg_id}")
    public ModelAndView msg_edit( long msg_id) throws SQLException {

        DoveMsgModel msg = DbDoveApi.getMsg(msg_id);

        viewModel.set("msg", msg);

        AppGroupModel agroup = DbRockApi.getAppGroupById(msg.agroup_id);

        List<DoveMsgLinkModel> links = DbDoveApi.getMsgLinks(msg.agroup_id);
        List<DoveMsgActionModel> actions = DbDoveApi.getMsgActions(msg.agroup_id);
        viewModel.set("links", links);
        viewModel.set("actions", actions);

//        List<SchemeModel> rules = RubberClient.getSchemesByTag(NameBuildUtil.buildPushTagName(agroup.tag));
//        List<ModelModel> models = RubberClient.getModelsByTag(NameBuildUtil.buildPushTagName(agroup.tag));
//        viewModel.set("rules", rules);
//        viewModel.set("models", models);

        viewModel.set("agroup_id", msg.agroup_id);
        viewModel.set("agroup", agroup);

        viewModel.set("is_edit", true);

        return view("push/msg_add");
    }

    @Mapping("ajax/save")
    public BaseResp msg_save(DoveMsgModel msg) {

        BaseResp resp = new BaseResp();

        resp.code = 1;
        resp.msg = "成功";

        try {

            AppGroupModel agroup = DbRockApi.getAppGroupById(msg.agroup_id);
//            ModelModel model = RubberClient.getModelById(msg.model_id);
//            SchemeModel scheme = RubberClient.getSchemeById(msg.rule_id);

            msg.msg_id = IDUtil.buildDoveMsgID();
            msg.status = 1;
            msg.operator = Session.current().getUserName();
//            msg.model_name = NameBuildUtil.buildRubberName(agroup.tag, model.name);
//            msg.model_name_disp = model.name_display;
//            msg.rule_name = NameBuildUtil.buildRubberName(agroup.tag, scheme.name);
//            msg.rule_name_disp = scheme.name_display;

            DbDoveApi.addMsg(msg.msg_id,
                    msg.title,
                    msg.content,
                    msg.agroup_id,
                    msg.push_link,
                    msg.msg_link,
                    msg.mobile,
                    msg.model_id,
                    msg.model_name,
                    msg.model_name_disp,
                    msg.rule_id,
                    msg.rule_name,
                    msg.rule_name_disp,
                    msg.actions,
                    msg.target_time,
                    msg.status,
                    msg.operator
            );

            //生成paas公共函数和etl
//            DbPaasApi.setPaasAndETL(msg,scheme,agroup);

        } catch (Exception e) {

            resp.code = 0;
            resp.msg = e.getLocalizedMessage();

        }

        return resp;
    }



    @Mapping("ajax/cancel")
    public BaseResp msg_cancel(long msg_id) {
        BaseResp resp = new BaseResp();

        resp.code = 1;
        resp.msg = "成功";

        try {

            DbDoveApi.cancelMsg(msg_id);

        } catch (Exception e) {

            resp.code = 0;
            resp.msg = e.getLocalizedMessage();

        }

        return resp;
    }

}
