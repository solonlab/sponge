package webapp.controller.rock;

import org.apache.http.util.TextUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;
import webapp.controller.BaseController;
import webapp.dso.db.DbRockApi;
import webapp.models.others.resp.BaseResp;
import webapp.models.rock.AppxWhitelistModel;

import java.sql.SQLException;
import java.util.List;

@Controller
@Mapping("/rock/")
public class WhitelistController extends BaseController {

    //应用白名单
    @Mapping("whitelist")
    public ModelAndView whiteList(String tag) throws SQLException {
        List<AppxWhitelistModel> tags = DbRockApi.getWhiteListGroup();
        viewModel.put("tags",tags);
        if (TextUtils.isEmpty(tag) == false) {
            viewModel.put("tag",tag);
        } else {
            if (tags.isEmpty() == false) {
                viewModel.put("tag",tags.get(0).tag);
            }
        }
        return view("rock/whitelist");
    }

    @Mapping("whitelist/inner")
    public ModelAndView whiteListInner(String tag,String value) throws SQLException{
        List<AppxWhitelistModel> tags = DbRockApi.getWhiteLists(tag,value);
        viewModel.put("whitelists",tags);
        viewModel.put("tag",tag);
        viewModel.put("value",value);
        return view("rock/whitelist_inner");
    }

    //跳转安全白名单新增页面
    @Mapping("whitelist/add")
    public ModelAndView whiteListAdd(String tag) throws SQLException{
        viewModel.put("whitelist",new AppxWhitelistModel());
        viewModel.put("tag",tag);
        return view("rock/whitelist_edit");
    }

    //跳转安全白名单编辑页面
    @Mapping("whitelist/edit")
    public ModelAndView whiteListEdit(Integer row_id) throws SQLException{
        AppxWhitelistModel whitelist = DbRockApi.getWhiteListById(row_id);
        viewModel.put("whitelist",whitelist);
        viewModel.put("tag",whitelist.tag);
        return view("rock/whitelist_edit");
    }

    //保存安全白名单编辑
    @Mapping("whitelist/edit/ajax/save")
    public BaseResp editWhiteList(Integer row_id, Integer type, String value, String note, String tag) throws SQLException {
        BaseResp resp = new BaseResp();
        boolean result = DbRockApi.editWhiteList(row_id,type,value,note,tag);
        if (result){
            resp.code = 1;
            resp.msg = "保存成功！";
        }else{
            resp.code = 0;
            resp.msg = "保存失败！";
        }
        return resp;
    }
}
