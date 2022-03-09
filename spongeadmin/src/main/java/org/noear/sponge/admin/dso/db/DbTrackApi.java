package org.noear.sponge.admin.dso.db;

import org.noear.snack.ONode;
import org.noear.sponge.admin.dso.CacheUtil;
import org.noear.sponge.admin.dso.IDUtil;
import org.noear.sponge.admin.model.others.ienum.ProvinceEnum;
import org.noear.sponge.admin.model.others.resp.*;
import org.noear.sponge.admin.model.sponge_track.*;
import org.noear.water.WaterClient;
import org.noear.water.utils.Datetime;
import org.noear.water.utils.EncryptUtils;
import org.noear.water.utils.TextUtils;
import org.noear.weed.DbContext;
import org.noear.sponge.admin.Config;

import java.sql.SQLException;
import java.util.*;

public class DbTrackApi {
    private static DbContext db() {
        return Config.sponge_track;
    }

    public static void addTrackTag(TrackTagModel tag) throws SQLException{
        if (TextUtils.isEmpty(tag.t_track_params) == false){
            tag.t_track_params_num = tag.t_track_params.split(",").length;
        } else {
            tag.t_track_params_num = 0;
        }
        long tag_id = db().table("track_tag")
                .set("agroup_id",tag.agroup_id)
                .set("tag_name",tag.tag_name)
                .expre(tb -> {
                    if (!TextUtils.isEmpty(tag.tag_host)){
                        tb.set("tag_host",tag.tag_host);
                    }
                })
                .set("note",tag.note)
                .set("admin_group",tag.admin_group)
                .set("t_user_field",tag.t_user_field)
                .set("t_track_params",tag.t_track_params)
                .set("t_track_params_num",tag.t_track_params_num)
                .set("t_trans_params",tag.t_trans_params)
                .set("t_build_link",tag.t_build_link)
                .insert();

        //同时插入扩展表
        db().table("track_tag_ex_stat")
                .set("tag_id",tag_id)
                .set("admin_group",tag.admin_group)
                .insert();
    }

    //获取所有标签
    public static List<TrackTagModel> getTrackTagList(int agroup_id) throws SQLException{
        return db().table("track_tag").where("agroup_id=?",agroup_id)
                .select("*")
                .getList(new TrackTagModel());
    }

    public static List<TagResp> getTagListInfo(int agroup_id) throws SQLException{
        return db().table("track_tag t")
                .leftJoin("track_tag_ex_stat s")
                .on("t.tag_id = s.tag_id")
                .where("t.agroup_id=?",agroup_id)
                .select("t.tag_id,t.agroup_id,t.tag_name,t.note,t.admin_group,s.pv_today,s.ip_today,s.uv_today," +
                        "s.pv_yesterday,s.uv_yesterday,s.ip_yesterday,t.t_user_field,t.t_track_params,t.t_trans_params")
                .getList(new TagResp());
    }


    public static TrackTagModel getTag(int tag_id) throws SQLException{
        return db().table("track_tag")
                .where("tag_id = ?",tag_id)
                .select("*")
                .getItem(new TrackTagModel());
    }

    public static void updateTrackTag(TrackTagModel tag) throws SQLException{
        db().table("track_tag")
                .where("tag_id = ?",tag.tag_id)
//                .set("agroup_id",tag.agroup_id) //agroup_id 不充许更新
                .set("tag_name",tag.tag_name)
                .set("note",tag.note)
                .set("t_user_field",tag.t_user_field)
                .set("t_track_params",tag.t_track_params)
                .set("t_trans_params",tag.t_trans_params)
                .set("t_build_link",tag.t_build_link)
                .set("tag_host",tag.tag_host)
                .update();
    }

    public static List<ShortUrlModel> getShortUrlsByTagId(int tag_id, String url_name, int is_disable) throws SQLException{
        return db().table("short_url")
                .where("tag_id = ?",tag_id)
                .and("is_disable = ?",is_disable)
                .expre((tb) -> {
                    if (TextUtils.isEmpty(url_name) == false) {
                        tb.and("url_name like ?", "%" + url_name + "%");
                    }
                })
                .select("*")
                .getList(new ShortUrlModel());
    }

    public static String addShortUrl(String url, String name, int tagID, String trackParams,
                                     String transParams,String note,String user_field,String build_link,
                                     String url_partner_key) throws SQLException{
        String url_md5 = null;

        if(TextUtils.isEmpty(url) == false) {
            url_md5 = EncryptUtils.md5(url);

           /* ShortUrlModel mod = getShortUrlByMd5(url_md5);

            if (TextUtils.isEmpty(mod.url_key) == false) {
                return mod.url_key;
            }*/
        }else{
            url_md5 = "";
        }

        long url_id = IDUtil.buildUrlID();
        String url_key = IDUtil.getCodeByID(url_id);

        int trackParamsNum = 0;
        if (TextUtils.isEmpty(trackParams) == false){
            trackParamsNum = trackParams.split(",").length;
        }

        db().table("short_url")
                .set("url_id", url_id)
                .set("tag_id", tagID)
                .set("url_key", url_key)
                .set("url_name",name)
                .set("url_val_md5", url_md5)
                .set("url_val", url)
                .set("track_params", trackParams)
                .set("track_params_num", trackParamsNum)
                .set("trans_params", transParams)
                .set("note",note)
                .set("user_field",user_field)
                .set("build_link",build_link)
                .set("url_partner_key",url_partner_key)
                .set("create_fulltime", "$NOW()")
                .insert();


        //同时插入扩展表
        db().table("short_url_ex_stat")
                .set("url_id", url_id)
                .set("tag_id", tagID)
                .insert();

        return url_key;
    }

    public static ShortUrlModel getShortUrlByMd5(String url_md5) throws SQLException {
        return db().table("short_url")
                .where("url_val_md5=?", url_md5)
                .select("*")
                .getItem(new ShortUrlModel());
    }

    //更新短网址
    public static void updateUrl(long url_id,String url_name,String url_partner_key,String url_val,String track_params,
                                 String trans_params,String note,String user_field,String build_link,int is_disable) throws SQLException{

        int trackParamsNum = 0;
        if (TextUtils.isEmpty(track_params) == false){
            track_params = track_params.trim();
            trackParamsNum = track_params.split(",").length;
        }

        if(TextUtils.isEmpty(trans_params)==false){
            trans_params=trans_params.trim();
        }

        db().table("short_url")
                .where("url_id = ?",url_id)
                .set("url_name",url_name)
                .set("url_partner_key",url_partner_key)
                .set("url_val",url_val)
                .set("track_params",track_params)
                .set("track_params_num",trackParamsNum)
                .set("trans_params",trans_params)
                .set("note",note)
                .set("user_field",user_field)
                .set("build_link",build_link)
                .set("is_disable",is_disable)
                .update();

        //添加缓存更新
        try{
            ShortUrlModel url = getShortUrlsByUrlId(url_id);
            WaterClient.Notice.updateCache("track_url_key_" + url.url_key);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static ShortUrlModel getShortUrlsByUrlId(long url_id) throws SQLException{
        return db().table("short_url")
                .where("url_id = ?",url_id)
                .select("*")
                .getItem(new ShortUrlModel());
    }

    public static String getParamName(int vi,String params) {
        String name = "";
        if (!TextUtils.isEmpty(params)){
            String[] list = params.split(",");
            name = list[vi - 1].replaceAll("\\?","");
        }
        return name;
    }

    public static List<UrlStatResp> getAllShortUrlExStat(int tag_id) throws SQLException {
        return db().table("short_url u")
                .innerJoin("short_url_ex_stat s")
                .on("u.url_id = s.url_id")
                .where("s.tag_id = ? AND u.is_disable=0", tag_id)
                .orderBy("s.pv_today desc")
                .limit(1000)//todo: 保护一下
                .selectList("u.url_name,u.track_params,s.*", UrlStatResp.class);
    }

    public static List<UrlStatResp> getAllShortUrlExStatResp(List<UrlStatResp> urlStatResp){
        for (UrlStatResp resp:urlStatResp) {
//            if (resp.track_params.indexOf("*") > 0) {
//                resp.has_track = 1;
//            }
            resp.has_track = 1;
        }
        return urlStatResp;
    }

    public static List<UrlListResp> getUrlResp(List<ShortUrlModel> urls) throws SQLException{
        List<UrlListResp> resp = new ArrayList<>();
        for (ShortUrlModel url:urls) {
            UrlListResp listResp = new UrlListResp();
            listResp.url_id = url.url_id;
            listResp.tag_id = url.tag_id;
            listResp.url_name = url.url_name;
            listResp.url_partner_key = url.url_partner_key;
            listResp.url_key = url.url_key;
            listResp.user_field = url.user_field;
            listResp.track_params = url.track_params;
            listResp.trans_params = url.trans_params;

            listResp.has_track = url.has_track;

            if(TextUtils.isEmpty(url.url_val) == false) {
                listResp.url_val = url.url_val.replaceAll("&", "&amp;");
            }

            List<CopyHrefResp> copyHrefResps = new ArrayList<>();
            if (TextUtils.isEmpty(url.url_key) == false && TextUtils.isEmpty( url.build_link) == true) {
                CopyHrefResp co = new CopyHrefResp();
                co.name = "默认";
                co.url = "";
                copyHrefResps.add(co);
            }

            if (TextUtils.isEmpty( url.build_link) == false) {
                CopyHrefResp co = new CopyHrefResp();
                co.name = "默认";
                co.url = "";
                copyHrefResps.add(co);
                //网站::f=web;卡片::f=app
                String[] links =  url.build_link.split(";");
                for (String link:links) {
                    String[] l = link.split("::");
                    CopyHrefResp copy = new CopyHrefResp();
                    copy.name = l[0];
                    copy.url = l[1];
                    copyHrefResps.add(copy);
                }
            }

            listResp.hrefList = copyHrefResps;
            resp.add(listResp);
        }
        return resp;
    }

    public static boolean checkBuildLink(String build_link){
        try {
            if (TextUtils.isEmpty(build_link) == false) {
                //网站::f=web;卡片::f=app
                String[] links = build_link.split(";");
                for (String link:links) {
                    String[] l = link.split("::");
                    String s1 = l[0];
                    String s2 = l[1];
                }
            }
        } catch (Exception e){
            return false;
        }

        return true;
    }

    public static List<TrackTagExTrackStatModel> getTrackTagExTrackStatByVi(int vi, int tag_id) throws SQLException {

        return db().table("track_tag_ex_track_stat")
                .where("vi = ?", vi)
                .and("tag_id = ?", tag_id)
                .orderBy("pv_today desc")
                .limit(1000) //todo: 保护一下
                .caching(CacheUtil.dataCache)
                .cacheTag("track_tag_ex_track_stat"+vi+tag_id)
                .selectList("*,vd as url_name", TrackTagExTrackStatModel.class);

    }


    public static TrackTagVIResp getTrackParams(TrackTagModel tag, int _state) throws SQLException{
        TrackTagVIResp resp = new TrackTagVIResp();
        String respStr = "url";
        String trackParams = tag.t_track_params;
        if (TextUtils.isEmpty(trackParams) == false) {
            String[] s = trackParams.split(",");
            int vi = 1;
            int count = 5;
            if (s.length <5) {
                count = s.length;
            }
            for (int i = 0; i < count; i++) {
                String str = s[i];
                if (str.endsWith("*")){
                    if (vi == _state)
                        resp.vi = i+1;
                    respStr = respStr + ","+str.substring(0,str.indexOf("*"))+"=?";
                    vi = vi + 1;
                } else {
                    if (vi == _state)
                        resp.vi = i+1;
                    respStr = respStr + ","+str+"=?";
                    vi = vi + 1;
                }
            }
        }

        resp.params = respStr;
        return resp;
    }

    public static List<ShortUrlExTrackStatModel> getShortUrlExTrackStatList(Integer url_id,Integer vi) throws SQLException {
        return db().table("short_url_ex_track_stat")
                .where("url_id = ?", url_id)
                .and("vi = ?", vi)
                .orderBy("pv_today desc")
                .limit(1000) //todo: 保护一下，免得太多了
                .caching(CacheUtil.dataCache)
                .cacheTag("short_url_ex_track_stat"+url_id+vi)
                .selectList("*,vd as url_name", ShortUrlExTrackStatModel.class);
    }

    public static ShortUrlModel getTargIdByUrlId(Integer url_id) throws SQLException{
        return db().table("short_url s")
                .where("url_id = ?",url_id)
                .selectItem("s.tag_id", ShortUrlModel.class);
    }

    public static TrackTagVIResp getTrackParamsNoUrl(int url_id,int _state) throws SQLException{
        ShortUrlModel url = getShortUrlsByUrlId(url_id);
        TrackTagVIResp resp = new TrackTagVIResp();
        String respStr = "";
        String trackParams = url.track_params;
        if (TextUtils.isEmpty(trackParams) == false) {
            String[] s = trackParams.split(",");
            int vi = 1;
            int count = 5;
            if (s.length <5) {
                count = s.length;
            }
            for (int i = 0; i < count; i++) {
                String str = s[i];
                if (str.endsWith("*")){
                    if (vi == _state)
                        resp.vi = i+1;
                    respStr = respStr + ","+str.substring(0,str.indexOf("*"))+"=?";
                    vi = vi + 1;
                } else {
                    if (vi == _state)
                        resp.vi = i+1;
                    respStr = respStr + ","+str+"=?";
                    vi = vi + 1;
                }
            }
            respStr = respStr.substring(1,respStr.length());
        }

        resp.params = respStr;
        return resp;
    }

    public static List<UserIpModel> getIP() throws SQLException {
        return db().table("user_ip")
                .selectList("*", UserIpModel.class);
    }

    //获取短地址 uv pv ip 小时数据
    public static List<StatDateHourPvUvIpModel> getHourPUIListByTagId(int tag_id, int log_date) throws SQLException {
        return db().table("stat_date_hour_pv_uv_ip")
                .where("tag_id = ?", tag_id)
                .and("log_date = ?", log_date)
                .and("url_id = ?", -1)
                .and("log_hour >= ?", 0)
                .caching(CacheUtil.dataCache).cacheTag("stat_date_hour_pv_uv_ip" + tag_id + log_date)
                .selectList("*", StatDateHourPvUvIpModel.class);
    }

    public static List<StatDateHourPvUvIpModel> getHourPUIListByUrlId(int url_id,int log_date) throws SQLException {
        return db().table("stat_date_hour_pv_uv_ip")
                .where("url_id = ?", url_id)
                .and("log_date = ?", log_date)
                .and("log_hour >= ?", 0)
                .caching(CacheUtil.dataCache).cacheTag("stat_date_hour_pv_uv_ip" + url_id + log_date)
                .selectList("*", StatDateHourPvUvIpModel.class);
    }

    //根据url_id，vi，vd查询短地址小时统计
    public static List<StatDateHourPvUvIpModel> getHourPUIListByUrlIdViVd(int url_id,int vi,String vd,int log_date) throws SQLException {
        return db().table("stat_track_date_hour_pv_uv_ip")
                .where("url_id = ?", url_id)
                .and("vi = ?", vi)
                .and("vd = ?", vd)
                .and("log_date = ?", log_date)
                .and("log_hour >= ?", 0)
                .caching(CacheUtil.dataCache).cacheTag("stat_date_hour_pv_uv_ip" + url_id + log_date)
                .selectList("*", StatDateHourPvUvIpModel.class);
    }

    //处理短地址 uv pv ip 小时 图表渲染数据
    public static HourUPIResp getCharts(int id, int vi, String vd, int type, int queryType) throws SQLException{
        HourUPIResp resp = new HourUPIResp();
        Datetime datetime = Datetime.Now();
        int todayInt = datetime.getDate();
        int yesterdayInt = datetime.addDay(-1).getDate();
        int beforeYesInt = datetime.addDay(-1).getDate();

        List<StatDateHourPvUvIpModel> todayList = null;
        List<StatDateHourPvUvIpModel> yesList = null;
        List<StatDateHourPvUvIpModel> beforeYesList = null;

        if (queryType == 0){
            todayList = getHourPUIListByTagId(id, todayInt);
            yesList = getHourPUIListByTagId(id, yesterdayInt);
            beforeYesList = getHourPUIListByTagId(id, beforeYesInt);
        } else if (queryType == 1) {
            if (vi > 0) {
                todayList = getHourPUIListByUrlIdViVd(id,vi,vd, todayInt);
                yesList = getHourPUIListByUrlIdViVd(id,vi,vd, yesterdayInt);
                beforeYesList = getHourPUIListByUrlIdViVd(id,vi,vd, beforeYesInt);
            } else {
                todayList = getHourPUIListByUrlId(id, todayInt);
                yesList = getHourPUIListByUrlId(id, yesterdayInt);
                beforeYesList = getHourPUIListByUrlId(id, beforeYesInt);
            }

        }

        Map<Integer, StatDateHourPvUvIpModel> todaymap = new HashMap<>();
        Map<Integer, StatDateHourPvUvIpModel> yesmap = new HashMap<>();
        Map<Integer, StatDateHourPvUvIpModel> beforeyesmap = new HashMap<>();
        for (StatDateHourPvUvIpModel model:todayList) {
            todaymap.put(model.log_hour,model);
        }
        for (StatDateHourPvUvIpModel model:yesList) {
            yesmap.put(model.log_hour,model);
        }
        for (StatDateHourPvUvIpModel model:beforeYesList) {
            beforeyesmap.put(model.log_hour,model);
        }

        List<String> hoursList = new ArrayList<>();
        List<Long> todays = new ArrayList<>();
        List<Long> yesterdays = new ArrayList<>();
        List<Long> beforeYesterdays = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                hoursList.add("0"+i+":00");
            } else {
                hoursList.add(i+":00");
            }

            //今日
            if (todaymap.get(i) == null) {
                todays.add(0L);
            } else {
                if (type == 0) {
                    todays.add(todaymap.get(i).pv);
                } else if (type == 1) {
                    todays.add(todaymap.get(i).uv);
                } else if (type == 2) {
                    todays.add(todaymap.get(i).ip);
                }else if (type == 3) {
                    todays.add(todaymap.get(i).uv2);
                }

            }
            //昨日
            if (yesmap.get(i) == null) {
                yesterdays.add(0L);
            } else {
                if (type == 0) {
                    yesterdays.add(yesmap.get(i).pv);
                } else if (type == 1) {
                    yesterdays.add(yesmap.get(i).uv);
                } else if (type == 2) {
                    yesterdays.add(yesmap.get(i).ip);
                } else if (type == 3) {
                    yesterdays.add(yesmap.get(i).uv2);
                }

            }
            //前日
            if (beforeyesmap.get(i) == null) {
                beforeYesterdays.add(0L);
            } else {
                if (type == 0) {
                    beforeYesterdays.add(beforeyesmap.get(i).pv);
                } else if (type == 1) {
                    beforeYesterdays.add(beforeyesmap.get(i).uv);
                } else if (type == 2) {
                    beforeYesterdays.add(beforeyesmap.get(i).ip);
                } else if (type == 3) {
                    beforeYesterdays.add(beforeyesmap.get(i).uv2);
                }

            }
        }
        resp.hoursList = hoursList;
        resp.todays = todays;
        resp.yesterdays = yesterdays;
        resp.beforeYesterdays = beforeYesterdays;
        return resp;
    }

    //获取短地址 uv pv ip 30天数据
    public static List<StatDateHourPvUvIpModel> getStatDateHourPvUvIpByDateTagId(int tag_id,int startDate,int endDate) throws SQLException{
        return db().table("stat_date_hour_pv_uv_ip")
                .where("url_id = ?",-1)
                .and("log_hour = ?",-1)
                .and("tag_id = ?",tag_id)
                .and("log_date >= ?",startDate)
                .and("log_date <= ?",endDate)
                .select("*")
                .caching(CacheUtil.dataCache).cacheTag("stat_date_hour_pv_uv_ip"+tag_id+startDate+endDate)
                .getList(new StatDateHourPvUvIpModel());
    }

    public static List<StatDateHourPvUvIpModel> getStatDateHourPvUvIpByDateUrlId(int url_id,int startDate,int endDate) throws SQLException{
        return db().table("stat_date_hour_pv_uv_ip")
                .where("log_hour = ?",-1)
                .and("url_id = ?",url_id)
                .and("log_date >= ?",startDate)
                .and("log_date <= ?",endDate)
                .select("*")
                .caching(CacheUtil.dataCache).cacheTag("stat_date_hour_pv_uv_ip"+url_id+startDate+endDate)
                .getList(new StatDateHourPvUvIpModel());
    }

    //根据url_id，vi，vd查询短地址近几天统计
    public static List<StatDateHourPvUvIpModel> getStatDateHourPvUvIpByDateUrlIdViVd(int url_id,int vi,String vd,int startDate,int endDate) throws SQLException{
        return db().table("stat_track_date_hour_pv_uv_ip")
                .where("log_hour = ?",-1)
                .and("url_id = ?",url_id)
                .and("vi = ?",vi)
                .and("vd = ?",vd)
                .and("log_date >= ?",startDate)
                .and("log_date <= ?",endDate)
                .select("*")
                .caching(CacheUtil.dataCache).cacheTag("stat_track_date_hour_pv_uv_ip"+url_id+startDate+endDate)
                .getList(new StatDateHourPvUvIpModel());
    }

    //处理短地址 uv pv ip  30天图表渲染数据
    public static Days30UPIResp get30DaysUPICharts(int id, int vi, String vd, int type) throws SQLException{
        Days30UPIResp resp = new Days30UPIResp();
        Datetime today = Datetime.Now();
        Datetime today30bef = Datetime.Now().addDay(-30);
        List<StatDateHourPvUvIpModel> list = null;
        if (type == 0) {
            list = getStatDateHourPvUvIpByDateTagId(id, today30bef.getDate(), today.getDate());
        } else if (type == 1) {
            if (vi > 0) {
                list = getStatDateHourPvUvIpByDateUrlIdViVd(id,vi,vd, today30bef.getDate(), today.getDate());
            } else {
                list = getStatDateHourPvUvIpByDateUrlId(id, today30bef.getDate(), today.getDate());
            }
        }
        Map<Integer, StatDateHourPvUvIpModel> map = new HashMap<>();
        List<String> daysList = new ArrayList<>();
        List<Long> pv_list = new ArrayList<>();
        List<Long> uv_list = new ArrayList<>();
        List<Long> ip_list = new ArrayList<>();
        List<Long> uv2_list = new ArrayList<>();

        for (StatDateHourPvUvIpModel stat:list) {
            map.put(stat.log_date,stat);
        }

        for (int i = 29; i >= 0; i--) {
            int dateInt = Datetime.Now().addDay(-i).getDate();
            String dateStr = dateInt+"";
            dateStr = dateStr.substring(4,8);
            daysList.add(dateStr.substring(0,2)+"-"+dateStr.substring(2,4));
            if (map.get(dateInt) == null) {
                pv_list.add(0L);
                uv_list.add(0L);
                ip_list.add(0L);
                uv2_list.add(0L);
            } else {
                pv_list.add(map.get(dateInt).pv);
                uv_list.add(map.get(dateInt).uv);
                ip_list.add(map.get(dateInt).ip);
                uv2_list.add(map.get(dateInt).uv2);
            }
        }
        resp.daysList = daysList;
        resp.pv_list = pv_list;
        resp.uv_list = uv_list;
        resp.ip_list = ip_list;
        resp.uv2_list = uv2_list;
        return resp;
    }

    //获取tag_id对应的省份的 uv pv ip 数据。
    private static List<StatCityDatePvUvIpModel> getProvincePUIList(int id, int yesterday,String upi) throws SQLException {
        List<StatCityDatePvUvIpModel> list = db().table("stat_city_date_pv_uv_ip")
                .where("url_id =-1")
                .and("tag_id = ?", id)
                .and("(log_date >= ?)", yesterday)
                .and("province_code >0")
                .groupBy("province_code")
                .caching(CacheUtil.dataCache)
//                .orderBy(upi+" desc")
                .selectList("province_code,SUM(" + upi + ") _val", StatCityDatePvUvIpModel.class);

        return list;
    }

    public static List<StatCityDatePvUvIpModel> getProvincePUIListByUrl(int url_id,int yesterday,String upi)throws SQLException {
        List<StatCityDatePvUvIpModel> list = db().table("stat_city_date_pv_uv_ip")
                .where("url_id = ?", url_id)
                .and("(log_date >= ?)", yesterday)
                .and("province_code >0")
                .groupBy("province_code")
                .caching(CacheUtil.dataCache)
                //.orderBy(upi+" desc")
                .selectList("province_code,SUM(" + upi + ") _val", StatCityDatePvUvIpModel.class);

        return list;
    }

    private static String fieldOfType(int type){
        switch (type){
            case 1:return "uv";
            case 2:return "ip";
            case 3:return "uv2";
            default:return "pv";
        }
    }

    public static DateIpResp getMapData(int id,int type,int queryType) throws  SQLException{
        DateIpResp resp = new DateIpResp();
        int yesterday = Datetime.Now().addDay(-7).getDate();
        List<StatCityDatePvUvIpModel> yesList = new ArrayList<>();

        String upi = fieldOfType(type);

        if (queryType==0) {
             yesList = getProvincePUIList(id, yesterday, upi);
        }else if(queryType==1){
             yesList = getProvincePUIListByUrl(id, yesterday, upi);
        }

        //创建List
        List<CityResp> yesmapList = new ArrayList<>();

        for (StatCityDatePvUvIpModel model : yesList) {
            CityResp tmp = new CityResp();
            tmp.name = ProvinceEnum.getEnumByCode(model.province_code).value;
            tmp.value = model._val;
            yesmapList.add(tmp);

            if(resp.max<model._val){
                resp.max = model._val;
            }
        }

        resp.yesterdays = yesmapList;

        return resp;
    }

    public static Map getPlatformCharts(int tag_id,int type,int url_id) throws SQLException {
        String tField = fieldOfType(type);
        Map out = new LinkedHashMap();

        int yesterday = Datetime.Now().addDay(-7).getDate();

        List<StatUaPlatformDatePvUvIpModel> platforms = db().table("stat_ua_platform_date_pv_uv_ip")
                .where("log_date >= ?", yesterday)
                .expre(tb -> {
                    if (tag_id > 0) {
                        tb.and("tag_id = ?", tag_id);
                    }
                    if (url_id > 0) {
                        tb.and("url_id = ?", url_id);
                    }
                })
                .groupBy("ua_platform")
                .select("ua_platform,SUM(" + tField + ") _val") //减少求合
                .getList(new StatUaPlatformDatePvUvIpModel());

        Map<Integer, String> enumMap = getCodeEnum(1);

        List<Object> titles = new ArrayList<>();
        List<Object> datas = new ArrayList();

        if (platforms.size() == 0) {
            platforms.add(new StatUaPlatformDatePvUvIpModel());
        }

        platforms.forEach((val) -> {
            String name = enumMap.get(val.ua_platform);
            if (TextUtils.isEmpty(name)) {
                name = "未知";
            }

            titles.add(name);

            Map obj = new LinkedHashMap();
            obj.put("name", name);
            obj.put("value", (val == null ? 0 : val._val));
            datas.add(obj);

        });

        out.put("titles", titles);
        out.put("datas", datas);

        return out;
    }



    private static Map getDataVal(String name, long val){
        Map obj = new LinkedHashMap();
        obj.put("name", name);
        obj.put("value", val);

        return obj;
    }
    public static Map getClientCharts(int tag_id,int type,int url_id) throws SQLException {
        String tField = fieldOfType(type);
        Map out = new LinkedHashMap();

        int yesterday = Datetime.Now().addDay(-7).getDate();

        List<StatUaClientDatePvUvIpModel> clients = db().table("stat_ua_client_date_pv_uv_ip")
                .where("log_date >= ?", yesterday)
                .build(tb -> {
                    if (tag_id > 0) {
                        tb.and("tag_id = ?", tag_id);
                    }
                    if (url_id > 0) {
                        tb.and("url_id = ?", url_id);
                    }
                })
                .groupBy("ua_client")
                .select("ua_client,sum(" + tField + ") _val")
                .getList(new StatUaClientDatePvUvIpModel());

        clients.sort(Comparator.comparingLong(m -> -m._val));//倒排用负数

        Map<String, Object> defs = new LinkedHashMap<>(); //用于数据按序位补齐（min=6）
        defs.put("Chrome", getDataVal("Chrome", 0));
        defs.put("Firefox", getDataVal("Firefox", 0));
        defs.put("MSIE", getDataVal("MSIE", 0));
        defs.put("Safari", getDataVal("Safari", 0));
        defs.put("Opera", getDataVal("Opera", 0));
        defs.put("Edge", getDataVal("Edge", 0));

        int size = clients.size();
        List<Object> titles = new ArrayList<>();
        List<Object> datas = new ArrayList<>();
        Map<Integer, String> clientEnum = getCodeEnum(2);

        long otherVal = 0;

        for (int i = 0; i<size; i++) {
            StatUaClientDatePvUvIpModel client = clients.get(i);
            if (i <= 9) {
                String name = clientEnum.get(client.ua_client);
                if(TextUtils.isEmpty(name)){
                    name = "未知";
                }
                titles.add(name);


                datas.add(getDataVal(name, client._val));
            } else {
                otherVal = otherVal + client._val;
            }
        }

        if (otherVal > 0) {
            titles.add("其它");
            datas.add(getDataVal("其它", otherVal));
        }

        //补齐6个
        while (titles.size() < 6) {
            for (String k1 : defs.keySet()) {
                if (titles.contains(k1) == false) {
                    titles.add(k1);
                    datas.add(getDataVal(k1, 0));
                    break;
                }
            }
        }

        out.put("titles", titles);
        out.put("datas", datas);
        return out;
    }


    public static Map<Integer,String> getCodeEnum(int type) throws SQLException{
        Map<Integer, String> out = new HashMap<>();
        List<CodeEnumModel> list = db().table("code_enum")
                .where(" type = ?",type)
                .select("*")
                .getList(new CodeEnumModel());
        list.forEach(li->{out.put(li.value,li.title);});
        return out;
    }

    public static long getClientPUI(StatUaClientDatePvUvIpModel model,int type) {
        long count = 0;
        switch (type){
            case 0 :count = model.pv;break;
            case 1 :count = model.uv;break;
            case 2 :count = model.ip;break;
            case 3 :count = model.uv2;break;
            default:break;
        }
        return count;
    }


    ///======外部接口========================================================================///
    public static ShortUrlModel getShortUrlByPartnerKey(String url_partner_key,int tag_id) throws SQLException{
        return db().table("short_url")
                .where("url_partner_key = ?",url_partner_key)
                .and("tag_id = ?",tag_id)
                .selectItem("*", ShortUrlModel.class);
    }

    public static void updateShortUrl(ShortUrlModel url) throws SQLException{
        db().table("short_url")
                .where("url_id = ?",url.url_id)
                .set("tag_id",url.tag_id)
                .set("url_name",url.url_name)
                .set("url_val_md5",url.url_val_md5)
                .set("url_val",url.url_val)
                .set("track_params",url.track_params)
                .set("track_params_num",url.track_params_num)
                .set("trans_params",url.trans_params)
                .set("user_field",url.user_field)
                .set("build_link",url.build_link)
                .set("update_time", "$NOW()")
                .update();
    }

    //生成短地址
    public static ONode addShortUrlApi(String url_partner_key,String url_val, String url_name, int tagID,
                                        String trackParams, String transParams,String note,String user_field,
                                        String build_link) throws SQLException{
        ONode data = new ONode();
        String prefix = "https://zmi6.cn/";
        if (!TextUtils.isEmpty(url_val) && url_val.startsWith("https://zmi6.cn/")) {
            return data;
        }
        ShortUrlModel shortUrl = getShortUrlByPartnerKey(url_partner_key,tagID);
        long url_id = shortUrl.url_id;

        int trackParamsNum = 0;
        TrackTagModel tag = getTag(tagID);
        if (TextUtils.isEmpty(trackParams) == false){
            trackParamsNum = trackParams.split(",").length;
        } else {
            trackParams = tag.t_track_params;
            trackParamsNum = trackParams.split(",").length;
        }

        if (TextUtils.isEmpty(transParams)) {
            transParams = tag.t_trans_params;
        }

        if (TextUtils.isEmpty(user_field)) {
            user_field = tag.t_user_field;
        }

        if (TextUtils.isEmpty(build_link)) {
            build_link = tag.t_build_link;
        }

        String url_md5 = null;

        if(TextUtils.isEmpty(url_val) == false) {
            url_md5 = EncryptUtils.md5(url_val);
        }else{
            url_md5 = "";
        }
        shortUrl.url_partner_key = url_partner_key;
        shortUrl.tag_id = tagID;
        shortUrl.url_name = url_name;
        shortUrl.url_val_md5 = url_md5;
        shortUrl.url_val = url_val;
        shortUrl.track_params = trackParams;
        shortUrl.track_params_num = trackParamsNum;
        shortUrl.trans_params = transParams;
        shortUrl.user_field = user_field;
        shortUrl.build_link = build_link;
        shortUrl.note = note;

        if (url_id > 0) {
            updateShortUrl(shortUrl);
            data.set("url",prefix+shortUrl.url_key);
            data.set("url_id",url_id);

            return data;
        } else {
            url_id = IDUtil.buildUrlID();
            String url_key = IDUtil.getCodeByID(url_id);

            db().table("short_url")
                    .set("url_id", url_id)
                    .set("tag_id", tagID)
                    .set("url_partner_key", url_partner_key)
                    .set("url_key", url_key)
                    .set("url_name",url_name)
                    .set("url_val_md5", url_md5)
                    .set("url_val", url_val)
                    .set("track_params", trackParams)
                    .set("track_params_num", trackParamsNum)
                    .set("trans_params", transParams)
                    .set("note",note)
                    .set("user_field",user_field)
                    .set("build_link",build_link)
                    .set("create_fulltime", "$NOW()")
                    .insert();
            //同时插入扩展表
            db().table("short_url_ex_stat")
                    .set("url_id", url_id)
                    .set("tag_id", tagID)
                    .insert();
            data.set("url",prefix+url_key);
            data.set("url_id",url_id);

            return data;
        }
    }

    //获取用户短地址日志信息
    public static ONode getLogUrls(String user_key,int tag_id,int date) throws SQLException{
        ONode resp = new ONode();
        List<ShortRedirectLog30dModel> list = db().table("short_redirect_log_30d s")
                .innerJoin("short_url u")
                .innerJoin("track_tag t")
                .on("s.url_id = u.url_id")
                .and("s.tag_id = t.tag_id")
                .where("s.user_key = ?",user_key)
                .expre((tb) -> {
                    if (tag_id > 0) {
                        tb.and("s.tag_id = ?", tag_id);
                    }
                    if (date > 0) {
                        tb.and("s.log_date = ?",date);
                    }
                })
                .orderBy("s.log_fulltime desc")
                .limit(50)
                .select("u.url_name,t.tag_name,s.url_id,s.tag_id,s.log_fulltime")
                .getList(new ShortRedirectLog30dModel());


        for (ShortRedirectLog30dModel log:list) {
            ONode oNode = new ONode();
            oNode.set("url_id",log.url_id);
            oNode.set("url_name",log.url_name);
            oNode.set("tag_id",log.tag_id);
            oNode.set("tag_name",log.tag_name);
            oNode.set("log_fulltime", Datetime.format(log.log_fulltime,"yyyy-MM-dd HH:mm:ss"));
            resp.add(oNode);
        }

        return resp;
    }

    //获取所有用户短地址访问信息
    public static ONode getPUIUrl(int tag_id,String url_partner_key,int log_date) throws SQLException{
        ONode resp = new ONode();

        ShortUrlModel short_url = db().table("short_url")
                .where("url_partner_key = ?", url_partner_key)
                .and("tag_id = ?",tag_id)
                .select("*")
                .getItem(new ShortUrlModel());

        List<StatDateHourPvUvIpModel> list = db().table("stat_date_hour_pv_uv_ip")
                .where("url_id = ?", short_url.url_id)
                .and("tag_id = ?", tag_id)
                .and("log_hour = -1")
                .and("log_date = ?",log_date)
                .select("*")
                .getList(new StatDateHourPvUvIpModel());


        for (StatDateHourPvUvIpModel pui: list) {
            ONode oNode = new ONode();
            oNode.set("url_id",pui.url_id);
            oNode.set("url_name",short_url.url_name);
            oNode.set("url_partner_key",short_url.url_partner_key);
            oNode.set("pv",pui.pv);
            oNode.set("uv",pui.uv);
            oNode.set("ip",pui.ip);
            resp.add(oNode);
        }
        return resp;
    }

    public static List<ShortUrlModel> getUrlNamesAndPartnerKeys() throws SQLException{
        return db().table("short_url")
                .select("url_id,url_name,url_partner_key")
                .getList(new ShortUrlModel());
    }

    //获取指定标签下所有短地址的访问信息
    public static ONode getPUITagUrls(int tag_id,int log_date) throws SQLException{
        ONode resp = new ONode();

        List<PUIUrlResp> list = db().table("stat_date_hour_pv_uv_ip t")
                .innerJoin("short_url s")
                .on("t.url_id = s.url_id")
                .where("t.tag_id = ?",tag_id)
                .and("t.log_date = ?",log_date)
                .and("t.log_hour = -1")
                .select("s.url_id,s.url_name,s.url_partner_key,t.pv,t.uv,t.ip")
                .getList(new PUIUrlResp());

        for (PUIUrlResp pui: list) {
            ONode oNode = new ONode();
            oNode.set("url_id",pui.url_id);
            oNode.set("url_partner_key",pui.url_partner_key);
            oNode.set("url_name",pui.url_name);
            oNode.set("pv",pui.pv);
            oNode.set("uv",pui.uv);
            oNode.set("ip",pui.ip);
            resp.add(oNode);
        }

        return resp;
    }

    public static WhitelistModel getIPWhite(String ip) throws SQLException{
        return db().table("whitelist")
                .where("ip = ?",ip)
                .select("*")
                .getItem(new WhitelistModel());
    }

    public static TrackTagModel getTagByIdAndAccKey(String tag_access_key,int tag_id) throws SQLException{
        return db().table("track_tag")
                .where("tag_id = ?",tag_id)
                .and("tag_access_key = ?",tag_access_key)
                .select("*")
                .getItem(new TrackTagModel());
    }
}
