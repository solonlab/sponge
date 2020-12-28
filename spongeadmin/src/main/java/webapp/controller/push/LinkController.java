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
import webapp.models.sponge.DoveMsgLinkModel;

import java.sql.SQLException;
import java.util.List;

/**
 * 2018.08.02
 *
 * @author konar
 */

@Controller
@Mapping("push/link")
public class LinkController extends BaseController {

    @Mapping("")
    public ModelAndView link() throws SQLException {

        // 有Tag的app_group
        List<AppGroupModel> agroups = DbRockApi.getAppGroupWithTag();
        viewModel.set("agroups", agroups);

        // 默认打开
        if (agroups.size() > 0) {
            viewModel.set("agroup_id", agroups.get(0).agroup_id);
        }

        return view("push/link");
    }

    @Mapping("inner/{agroup_id}")
    public ModelAndView link_inner( int agroup_id) throws SQLException {

        List<DoveMsgLinkModel> links = DbDoveApi.getMsgLinks(agroup_id);
        viewModel.set("links", links);

        return view("push/link_inner");
    }

    @Mapping("add/{agroup_id}")
    public ModelAndView link_add( int agroup_id) {

        viewModel.set("agroup_id", agroup_id);

        viewModel.set("is_edit", false);

        return view("push/link_add");
    }

    @Mapping("edit/{link_id}")
    public ModelAndView link_edit( int link_id) throws SQLException {

        DoveMsgLinkModel link = DbDoveApi.getMsgLink(link_id);
        viewModel.set("link", link);

        viewModel.set("agroup_id", link.agroup_id);

        viewModel.set("is_edit", true);

        return view("push/link_add");
    }

    @Mapping("ajax/save")
    public BaseResp link_save(DoveMsgLinkModel link) {

        BaseResp resp = new BaseResp();

        resp.code = 1;
        resp.msg = "成功";

        try {

            if (0 == link.link_id) {

                link.link_id = IDUtil.buildDoveMsgLinkID();
                link.operator = Session.current().getUserName();

                DbDoveApi.addMsgLink(link.link_id,
                        link.agroup_id,
                        link.name,
                        link.link,
                        link.operator);

            } else {

                link.operator = Session.current().getUserName();

                DbDoveApi.modMsgLink(link.link_id,
                        link.agroup_id,
                        link.name,
                        link.link,
                        link.operator);

            }

        } catch (Exception e) {

            resp.code = 0;
            resp.msg = e.getLocalizedMessage();

        }

        return resp;

    }

    @Mapping("ajax/del")
    public ViewModel link_del(Integer link_id) throws SQLException{
        boolean result = DbDoveApi.delMsgLink(link_id);

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
