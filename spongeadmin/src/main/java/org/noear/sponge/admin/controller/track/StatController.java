package org.noear.sponge.admin.controller.track;

import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Controller;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.sponge.admin.dso.BcfTagChecker;
import org.noear.sponge.admin.dso.GroupUtil;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.dso.db.DbTrackApi;
import org.noear.sponge.admin.model.others.resp.*;
import org.noear.sponge.admin.model.sponge_track.ShortUrlModel;
import org.noear.sponge.admin.model.sponge_track.TrackTagExTrackStatModel;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.model.rock.AppGroupModel;
import org.noear.sponge.admin.model.sponge_track.ShortUrlExTrackStatModel;
import org.noear.sponge.admin.model.sponge_track.TrackTagModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class StatController extends BaseController {

    //短网址统计
    @Mapping("track/stat")
    public ModelAndView urlStatistics(Integer tag_id,Integer agroup_id) throws SQLException{
        //by noear 20180516::添加应用组的权限控制
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

        return view("track/stat");
    }

    //短网址统计列表
    @Mapping("track/stat/inner")
    public ModelAndView innerUrlStat(Integer tag_id,Integer _state,Integer stateVal) throws SQLException{
        if (null == _state)
            _state = 0;
        if (null == stateVal)
            stateVal = 0;
        TrackTagModel tag = DbTrackApi.getTag(tag_id);
        TrackTagVIResp params = DbTrackApi.getTrackParams(tag,_state);
        viewModel.put("tabname",params.params);
        viewModel.put("stateVal",stateVal);
        viewModel.put("tag_id",tag_id);
        return view("track/stat_inner");
    }

    @Mapping("track/stat/url")
    public ModelAndView statUrl(Integer url_id,Integer _state) throws SQLException{
        if (null == _state)
            _state = 0;
        TrackTagVIResp param = DbTrackApi.getTrackParamsNoUrl(url_id, _state + 1);
        viewModel.put("tabname",param.params);
        List<ShortUrlExTrackStatModel> list = DbTrackApi.getShortUrlExTrackStatList(url_id, param.vi);
        ShortUrlModel url = DbTrackApi.getShortUrlsByUrlId(url_id);

        if(list.isEmpty() == false) {
            viewModel.put("tag_id", list.get(0).tag_id);
        }

        viewModel.put("url_name",url.url_name);
        viewModel.put("url_id",url_id);
        return view("track/stat_url");
    }


    //短地址 uv pv ip （小时）图表渲染数据
    @Mapping("track/stat/ajax/getcharts")
    public HourUPIResp getcharts(Integer id, Integer vi, String vd, Integer type, Integer queryType) throws SQLException{
        if (vi==null)
            vi = 0;
        HourUPIResp charts = DbTrackApi.getCharts(id, vi,vd,type,queryType);
        return charts;
    }

    //短地址 uv pv ip （30天）图表渲染数据
    @Mapping("track/stat/ajax/get30dayscharts")
    public Days30UPIResp get30Dayscharts(Integer id, Integer vi, String vd, Integer type) throws SQLException{
        if (vi==null)
            vi = 0;
        Days30UPIResp charts = DbTrackApi.get30DaysUPICharts(id,vi,vd,type);
        return charts;
    }

    //地图渲染
    @Mapping("track/stat/ajax/getCitycharts")
    public DateIpResp getCityMapCharts(Integer id, Integer type, Integer queryType) throws SQLException {
        DateIpResp charts = DbTrackApi.getMapData(id,type,queryType);
        return charts;
    }

    //系统饼图渲染
    @Mapping("track/stat/ajax/getPlatformCharts")
    public Map getPlatformCharts(Integer tag_id, Integer type, Integer url_id) throws SQLException{
        if (url_id==null)
            url_id = 0;

        Map out = DbTrackApi.getPlatformCharts(tag_id, type,url_id);
        return out;
    }

    //终端柱形图渲染
    @Mapping("track/stat/ajax/getClientCharts")
    public Map getClientCharts(Integer tag_id,Integer type,Integer url_id) throws SQLException{
        if (url_id==null)
            url_id = 0;
        Map out = DbTrackApi.getClientCharts(tag_id, type,url_id);
        return out;
    }

    @Mapping("track/stat/url/inner")
    public ModelAndView getUrlDetailTable(Integer url_id,Integer _state) throws SQLException{
        if (null == _state)
            _state = 0;
        TrackTagVIResp param = DbTrackApi.getTrackParamsNoUrl(url_id, _state + 1);
        List<ShortUrlExTrackStatModel> list = DbTrackApi.getShortUrlExTrackStatList(url_id, param.vi);
        viewModel.put("list",list);
        viewModel.put("vi",param.vi);
        viewModel.put("params",param.params);
        return view("track/stat_url_inner");
    }

    //短地址pv，uv，ip第三级详情展示
    @Mapping("track/stat/url/detail")
    public ModelAndView statUrlDetail(Integer url_id,Integer vi,String vd,String params) throws SQLException{
        ShortUrlModel url = DbTrackApi.getShortUrlsByUrlId(url_id);
        String param_name = DbTrackApi.getParamName(vi, params);
        viewModel.put("url_id",url_id);
        viewModel.put("vi",vi);
        viewModel.put("vd",vd);
        viewModel.put("url_name",url.url_name);
        viewModel.put("param_name",param_name);
        return view("track/stat_url_detail");
    }

    @Mapping("track/stat/inner/table")
    public ModelAndView getInnerTable(Integer tag_id,Integer _state) throws SQLException{
        if (null == _state)
            _state = 0;
        TrackTagModel tag = DbTrackApi.getTag(tag_id);
        TrackTagVIResp params = DbTrackApi.getTrackParams(tag,_state);
        if (_state == 0) {
            List<UrlStatResp> list = DbTrackApi.getAllShortUrlExStatResp(DbTrackApi.getAllShortUrlExStat(tag_id));
            viewModel.put("type",0);
            viewModel.put("list",list);
        }
        if (_state != 0) {
            List<TrackTagExTrackStatModel> list = DbTrackApi.getTrackTagExTrackStatByVi(params.vi,tag_id);
            viewModel.put("list",list);
            viewModel.put("type",1);
        }
        return view("track/stat_inner_table");
    }

}
