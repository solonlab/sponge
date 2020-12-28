package webapp.controller.push;



import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Controller;
import org.noear.solon.core.handle.ModelAndView;
import webapp.controller.BaseController;
import webapp.controller.ViewModel;
import webapp.dso.IDUtil;
import webapp.dso.Session;
import webapp.dso.db.DbDoveApi;
import webapp.dso.db.DbRockApi;
import webapp.models.others.resp.BaseResp;
import webapp.models.rock.AppGroupModel;
import webapp.models.sponge.DoveSmsChannelModel;

import java.sql.SQLException;
import java.util.List;

/**
 * 2018.08.06
 *
 * @author konar
 */

@Controller
@Mapping("push/channel")
public class ChannelController extends BaseController {

    @Mapping("")
    public ModelAndView channel() throws SQLException {

        // 有Tag的app_group
        List<AppGroupModel> agroups = DbRockApi.getAppGroupWithTag();
        viewModel.set("agroups", agroups);

        // 默认打开
        if (agroups.size() > 0) {
            viewModel.set("agroup_id", agroups.get(0).agroup_id);
        }

        return view("push/channel");
    }

    @Mapping("inner/{agroup_id}")
    public ModelAndView channel_inner( int agroup_id) throws SQLException {

        List<DoveSmsChannelModel> cnls = DbDoveApi.getSmsChannels(agroup_id);

        viewModel.set("cnls", cnls);

        viewModel.set("agroup_id", agroup_id);

        return view("push/channel_inner");
    }

    @Mapping("add/{agroup_id}")
    public ModelAndView channel_add( int agroup_id) {

        viewModel.set("agroup_id", agroup_id);

        viewModel.set("is_edit", false);

        return view("push/channel_add");
    }

    @Mapping("edit/{channel_id}")
    public ModelAndView channel_edit( int channel_id) throws SQLException {

        DoveSmsChannelModel cnl = DbDoveApi.getSmsChannel(channel_id);

        viewModel.set("cnl", cnl);

        viewModel.set("agroup_id", cnl.agroup_id);

        viewModel.set("is_edit", true);

        return view("push/channel_add");
    }

    @Mapping("ajax/save")
    public BaseResp channel_save(Integer channel_id,Integer agroup_id,String name,String name_display,String code) {

        BaseResp resp = new BaseResp();

        resp.code = 1;
        resp.msg = "成功";

        try {
            String operator = Session.current().getUserName();

            if (0 == channel_id) {
                channel_id = IDUtil.buildDoveSmsChannelID();
                DbDoveApi.addSmsChannel(channel_id,agroup_id,name,name_display,code,operator);
            } else {
                DbDoveApi.modSmsChannel(channel_id,name,name_display,code,operator);
            }
        } catch (Exception e) {

            resp.code = 0;
            resp.msg = e.getLocalizedMessage();
        }

        return resp;
    }

    @Mapping("ajax/del")
    public ViewModel channel_del(Integer channel_id) throws SQLException{
        boolean result = DbDoveApi.delSmsChannel(channel_id);

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
