package org.noear.sponge.admin.controller.push;

//import com.alibaba.fastjson.JSONObject;
//import org.noear.rubber.RubberClient;
//import org.noear.rubber.models.ModelModel;
//import org.noear.rubber.models.SchemeModel;


import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Controller;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.dso.IDUtil;
import org.noear.sponge.admin.dso.Session;
import org.noear.sponge.admin.dso.db.DbDoveApi;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.models.others.CountModel;
import org.noear.sponge.admin.models.others.resp.BaseResp;
import org.noear.sponge.admin.models.rock.AppGroupModel;
import org.noear.sponge.admin.models.sponge.DoveSmsChannelModel;
import org.noear.sponge.admin.models.sponge.DoveSmsModel;
import org.noear.sponge.admin.models.sponge.DoveSmsTemplateModel;

import java.sql.SQLException;
import java.util.List;

/**
 * 2018.07.24
 *
 * @author konar
 */

@Controller
@Mapping("push/sms")
public class SmsController extends BaseController {

    @Mapping("")
    public ModelAndView sms() throws SQLException {

        // 有Tag的app_group
        List<AppGroupModel> agroups = DbRockApi.getAppGroupWithTag();
        viewModel.set("agroups", agroups);

        // 默认打开
        if (agroups.size() > 0) {
            viewModel.set("agroup_id", agroups.get(0).agroup_id);
        }

        return view("push/sms");
    }

    @Mapping("inner/{agroup_id}")
    public ModelAndView sms_inner( int agroup_id, Integer page, Integer page_size, Integer _state) throws SQLException {

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
        List<DoveSmsModel> smss = DbDoveApi.getSmss(agroup_id, _state + 1, page, page_size, countModel);

        viewModel.set("smss", smss);

        viewModel.set("page_size", page_size);
        viewModel.set("row_count", countModel.getCount());

        viewModel.set("agroup_id", agroup_id);

        return view("push/sms_inner");
    }

    @Mapping("add/{agroup_id}")
    public ModelAndView sms_add( int agroup_id) throws SQLException {

        AppGroupModel agroup = DbRockApi.getAppGroupById(agroup_id);

        List<DoveSmsTemplateModel> tmps = DbDoveApi.getSmsTemplates(agroup_id);
        List<DoveSmsChannelModel> cnls = DbDoveApi.getSmsChannels(agroup_id);
        viewModel.set("tmps", tmps);
        viewModel.set("cnls", cnls);

//        List<SchemeModel> rules = RubberClient.getSchemesByTag(NameBuildUtil.buildPushTagName(agroup.tag));
//        List<ModelModel> models = RubberClient.getModelsByTag(NameBuildUtil.buildPushTagName(agroup.tag));
//        viewModel.set("rules", rules);
//        viewModel.set("models", models);

        viewModel.set("agroup_id", agroup_id);
        viewModel.set("agroup", agroup);

        viewModel.set("is_edit", false);

        return view("push/sms_add");
    }

    @Mapping("edit/{sms_id}")
    public ModelAndView sms_edit( long sms_id) throws SQLException {

        DoveSmsModel sms = DbDoveApi.getSms(sms_id);

        viewModel.set("sms", sms);

        AppGroupModel agroup = DbRockApi.getAppGroupById(sms.agroup_id);

        List<DoveSmsTemplateModel> tmps = DbDoveApi.getSmsTemplates(sms.agroup_id);
        List<DoveSmsChannelModel> cnls = DbDoveApi.getSmsChannels(sms.agroup_id);
        viewModel.set("tmps", tmps);
        viewModel.set("cnls", cnls);

//        List<SchemeModel> rules = RubberClient.getSchemesByTag(NameBuildUtil.buildPushTagName(agroup.tag));
//        List<ModelModel> models = RubberClient.getModelsByTag(NameBuildUtil.buildPushTagName(agroup.tag));
//        viewModel.set("rules", rules);
//        viewModel.set("models", models);

        viewModel.set("agroup_id", sms.agroup_id);
        viewModel.set("agroup", agroup);

        viewModel.set("is_edit", true);

        return view("push/sms_add");

    }

    @Mapping("ajax/save")
    public BaseResp sms_save(DoveSmsModel sms) {

        BaseResp resp = new BaseResp();

        resp.code = 1;
        resp.msg = "成功";

        try {

            AppGroupModel agroup = DbRockApi.getAppGroupById(sms.agroup_id);
//            ModelModel model = RubberClient.getModelById(sms.model_id);
//            SchemeModel scheme = RubberClient.getSchemeById(sms.rule_id);
            DoveSmsTemplateModel tmp = DbDoveApi.getSmsTemplate(sms.template_id);

            sms.sms_id = IDUtil.buildDoveSmsID();
            sms.status = 1;
            sms.operator = Session.current().getUserName();
//            sms.model_name = NameBuildUtil.buildRubberName(agroup.tag, model.name);
//            sms.model_name_disp = model.name_display;
//            sms.rule_name = NameBuildUtil.buildRubberName(agroup.tag, scheme.name);
//            sms.rule_name_disp = scheme.name_display;
            sms.content = tmp.content;

            DbDoveApi.addSms(sms.sms_id,
                    sms.agroup_id,
                    sms.channels,
                    sms.var_setting,
                    sms.template_id,
                    sms.content,
                    sms.mobile,
                    sms.model_id,
                    sms.model_name,
                    sms.model_name_disp,
                    sms.rule_id,
                    sms.rule_name,
                    sms.rule_name_disp,
                    sms.target_time,
                    sms.status,
                    sms.operator);




        } catch (Exception e) {

            resp.code = 0;
            resp.msg = e.getLocalizedMessage();

        }

        return resp;
    }

    @Mapping("ajax/cancel")
    public BaseResp msg_cancel(long sms_id) {
        BaseResp resp = new BaseResp();

        resp.code = 1;
        resp.msg = "成功";

        try {

            DbDoveApi.cancelSms(sms_id);

        } catch (Exception e) {

            resp.code = 0;
            resp.msg = e.getLocalizedMessage();

        }

        return resp;
    }

}
