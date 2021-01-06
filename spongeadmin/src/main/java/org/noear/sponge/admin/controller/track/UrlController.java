package org.noear.sponge.admin.controller.track;

import org.apache.http.util.TextUtils;

import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Controller;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.sponge.admin.dso.BcfTagChecker;
import org.noear.sponge.admin.dso.GroupUtil;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.models.sponge_track.ShortUrlModel;
import org.noear.sponge.admin.Config;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.dso.db.DbTrackApi;
import org.noear.sponge.admin.models.others.resp.BaseResp;
import org.noear.sponge.admin.models.others.resp.UrlListResp;
import org.noear.sponge.admin.models.rock.AppGroupModel;
import org.noear.sponge.admin.models.sponge_track.TrackTagModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UrlController extends BaseController {

    //跳转进入短网址管理页面
    @Mapping("track/url")
    public ModelAndView shortUrl(Integer tag_id,Integer agroup_id) throws SQLException{
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
        List<AppGroupModel> tmp_agroups = DbRockApi.getAppGroup(null);
        for (AppGroupModel ag : tmp_agroups) {
            //检测是否有这个应用组的权限
            if (checker.find(ag.tag)) {
                agroups.add(ag);

                if(out_agroup_id == 0){
                    out_agroup_id = ag.agroup_id;
                }
            }
        }

        List<TrackTagModel> tagList = DbTrackApi.getTrackTagList(out_agroup_id);
        if(tag_id == null){
            tag_id = 0;
        }
        if(tag_id == 0 && tagList.isEmpty() == false){
            tag_id = tagList.get(0).tag_id;
        }


        viewModel.put("tag_id",tag_id);
        viewModel.put("agroup_id",out_agroup_id);
        viewModel.put("app_groups",agroups);
        viewModel.put("tagList",tagList);

        return view("track/url");
    }


    //新增短网址-保存新增
    @Mapping("track/url/add/ajax/save")
    public BaseResp saveAddShortUrl(Integer tag_id,String url_name,String url_val,String track_params,
                                    String trans_params,String note,String user_field,String build_link,
                                    String url_partner_key) throws SQLException{
        BaseResp resp = new BaseResp();
        if (DbTrackApi.checkBuildLink(build_link) == false){
            resp.code = 0;
            resp.msg = "构建链接格式不正确";
            return resp;
        } else {
            String url_key = DbTrackApi.addShortUrl(url_val, url_name, tag_id, track_params, trans_params,
                    note,user_field,build_link,url_partner_key);
            if (TextUtils.isEmpty(url_key)){
                resp.code = 0;
                resp.msg = "保存失败";
                return resp;
            } else {
                resp.code = 1;
                resp.msg = "新增成功";
                return resp;
            }
        }

    }

    //新增短网址
    @Mapping("track/url/add")
    public ModelAndView addUrl(Integer tag_id) throws SQLException{
        TrackTagModel tag = DbTrackApi.getTag(tag_id);
        viewModel.put("tag",tag);
        viewModel.put("tag_id",tag_id);
        return view("track/url_add");
    }

    //编辑短网址
    @Mapping("track/url/edit")
    public ModelAndView editUrl(Integer url_id) throws SQLException{
        ShortUrlModel url = DbTrackApi.getShortUrlsByUrlId(url_id);
        TrackTagModel tag = DbTrackApi.getTag(url.tag_id);
        viewModel.put("url",url);
        viewModel.put("tag",tag);
        return view("track/url_edit");
    }


    //编辑短网址-保存编辑
    @Mapping("track/url/edit/ajax/save")
    public BaseResp saveEditShortUrl(Integer url_id, String url_name, String url_partner_key,String url_val, String track_params,
                                     String trans_params, String note, String user_field, String build_link,Integer is_disable) throws SQLException{
        BaseResp resp = new BaseResp();
        if (DbTrackApi.checkBuildLink(build_link) == false){
            resp.code = 0;
            resp.msg = "构建链接格式不正确";
            return resp;
        } else {
            DbTrackApi.updateUrl(url_id,url_name,url_partner_key,url_val,track_params,trans_params,note,user_field,build_link,is_disable);
            resp.code = 1;
            resp.msg = "编辑成功";
            return resp;
        }
    }

    //短网址列表
    @Mapping("track/url/inner")
    public ModelAndView innerUrl(Integer tag_id,String url_name,Integer _state) throws SQLException {
        if (null == _state) {
            _state = 0;
        }

        if (null == tag_id) {
            tag_id = 0;
        }

        List<ShortUrlModel> urls = DbTrackApi.getShortUrlsByTagId(tag_id, url_name, _state);
        List<UrlListResp> urlResp = DbTrackApi.getUrlResp(urls);
        TrackTagModel tag = DbTrackApi.getTag(tag_id);
        viewModel.put("urls", urlResp);
        viewModel.put("tag_id", tag_id);
        viewModel.put("url_name", url_name);
        if (TextUtils.isEmpty(tag.tag_host)){
            viewModel.put("track_uri", Config.track_uri());
        } else {
            String host = tag.tag_host;
            if (!tag.tag_host.endsWith("/")){
                host = host + "/";
            }
            viewModel.put("track_uri", host);
        }
        return view("track/url_inner");
    }
}
