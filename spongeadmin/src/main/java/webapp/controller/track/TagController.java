package webapp.controller.track;

import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Controller;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import webapp.controller.BaseController;
import webapp.dso.BcfTagChecker;
import webapp.dso.GroupUtil;
import webapp.dso.db.DbRockApi;
import webapp.dso.db.DbTrackApi;
import webapp.models.others.resp.BaseResp;
import webapp.models.others.resp.TagResp;
import webapp.models.rock.AppGroupModel;
import webapp.models.sponge_track.TrackTagModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class TagController extends BaseController {

    //标签管理
    @Mapping("track/tag")
    public ModelAndView tag(Integer agroup_id) throws SQLException{
        //by xyj 20180516::添加应用组的权限控制
        BcfTagChecker checker = new BcfTagChecker();

        //应用组cookie记忆处理
        Integer out_agroup_id = agroup_id;
        if (out_agroup_id==null){
            out_agroup_id = 0;
            agroup_id = 0;
        }
        out_agroup_id = GroupUtil.groupCookie(out_agroup_id,agroup_id);

        List<AppGroupModel> agroups = new ArrayList<>();
        List<AppGroupModel> tmp_agroups = DbRockApi.getAppGroup(null, 0);
        for (AppGroupModel ag : tmp_agroups) {
            //检测是否有这个应用组的权限
            if (checker.find(ag.tag)) {
                agroups.add(ag);

                if(out_agroup_id == 0){
                    out_agroup_id = ag.agroup_id;
                }
            }
        }

        viewModel.put("agroup_id",out_agroup_id);
        viewModel.put("app_groups",agroups);

        return view("track/tag");
    }

    //标签管理
    @Mapping("track/tag/inner")
    public ModelAndView tag_inner(Integer agroup_id) throws SQLException {

        Context.current().cookieSet("agroup", agroup_id + "");

        List<TagResp> list = DbTrackApi.getTagListInfo(agroup_id);
        viewModel.put("agroup_id", agroup_id);
        viewModel.put("tags", list);
        return view("track/tag_inner");
    }

    //添加标签
    @Mapping("track/tag/add")
    public ModelAndView addTag(Integer agroup_id) throws SQLException{

        viewModel.put("agroup_id",agroup_id);
        return view("track/tag_add");
    }

    //保存标签
    @Mapping("track/tag/add/ajax/save")
    public BaseResp saveTag(String tagName,String tagHost,int agroup_id,String note,String t_user_field,String t_track_params,

                            String t_trans_params,String t_build_link) throws SQLException{

        BaseResp resp = new BaseResp();

        if(agroup_id<1){
            return resp;
        }


        if (DbTrackApi.checkBuildLink(t_build_link) == false){
            resp.code = 0;
            resp.msg = "构建链接格式不正确";
            return resp;
        } else {
            TrackTagModel tag = new TrackTagModel();
            tag.tag_name = tagName;
            tag.agroup_id = agroup_id;
            tag.note = note;
            tag.t_user_field = t_user_field.trim();
            tag.t_track_params = t_track_params.trim();
            tag.t_trans_params = t_trans_params.trim();
            tag.t_build_link = t_build_link.trim();
            tag.tag_host = tagHost;
            DbTrackApi.addTrackTag(tag);
            resp.code = 1;
            resp.msg = "添加成功";
            return resp;
        }
    }

    //编辑标签
    @Mapping("track/tag/edit")
    public ModelAndView editTag(Integer tag_id) throws SQLException{
        TrackTagModel tag = DbTrackApi.getTag(tag_id);

        int agroup_id = tag.agroup_id;

        viewModel.put("tag",tag);
        viewModel.put("agroup_id",agroup_id);
        return view("track/tag_edit");
    }

    //保存标签编辑
    @Mapping("track/tag/edit/ajax/save")
    public BaseResp saveEdit(Integer tag_id,int agroup_id,String tag_name,String tag_host,String note,String t_user_field,
                             String t_track_params,String t_trans_params,String t_build_link) throws SQLException{
        BaseResp resp = new BaseResp();

        if(agroup_id<1){
            return resp;
        }

        if (DbTrackApi.checkBuildLink(t_build_link) == false){
            resp.code = 0;
            resp.msg = "构建链接格式不正确";
            return resp;
        } else {
            TrackTagModel tag = new TrackTagModel();
            tag.tag_id = tag_id;
            tag.agroup_id = agroup_id;
            tag.tag_name = tag_name;
            tag.note = note;
            tag.t_user_field = t_user_field;
            tag.t_track_params = t_track_params;
            tag.t_trans_params = t_trans_params;
            tag.t_build_link = t_build_link;
            tag.tag_host = tag_host;
            DbTrackApi.updateTrackTag(tag);
            resp.code = 1;
            resp.msg = "修改成功";
            return resp;
        }

    }
}
