package org.noear.sponge.admin.dso.db;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.noear.snack.ONode;
import org.noear.sponge.admin.dso.CacheUtil;
import org.noear.sponge.admin.dso.IDUtil;
import org.noear.sponge.admin.models.others.ienum.*;
import org.noear.sponge.admin.models.others.resp.*;
import org.noear.sponge.admin.models.sponge_angel.*;
import org.noear.sponge.admin.utils.DateUtil;
import org.noear.sponge.admin.utils.StringUtil;
import org.noear.sponge.admin.utils.BaiduUtil;
import org.noear.sponge.admin.utils.OssUtil;
import org.noear.water.utils.HttpUtils;
import org.noear.weed.DbContext;
import org.noear.weed.DbTableQuery;
import org.apache.http.util.TextUtils;
import org.noear.sponge.admin.Config;
import org.noear.sponge.admin.dso.TongDunApi;
import org.noear.sponge.admin.models.others.CountModel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Author:Yunlong.Feng
 * @Description:
 */
public class DbUserApi {
    private static DbContext db() {
        return Config.sponge_angel;
    }
    //分页查询用户列表
    public static List<UserStateModel> getUserStateList(int page, int pageSize, CountModel count,String mobile,int is_real) throws SQLException {
        int start = (page - 1) * pageSize;
        DbTableQuery query = db().table("user_state");
        query.where("is_real = ?",is_real);
        if (TextUtils.isEmpty(mobile) == false){
            query.and("mobile = ?",mobile);
        }

        //query.caching(CacheUtil.dataCache);

        count.setCount(query.count());
        return  query.orderBy("open_id desc")
                .limit(start, pageSize)
                .select("*")
                .getList(new UserStateModel());

    }
    //根据open_id获取身份证识别信息。
    public static UserExIdentificationModel getCardByOpenId(long open_id) throws SQLException {
        return db().table("user_ex_identification a")
                .leftJoin("log_user_similarcheck b")
                .on("a.open_id = b.open_id")
                .where("a.open_id = ?",open_id)
                .select("a.*,b.face_img")
                .getItem(new UserExIdentificationModel());
    }
    //根据open_id获取运营商基本信息。
    public static AngelOperatorBaseModel getOperatorBase(long open_id) throws SQLException {
        return db().table("angel_operator_base")
                .where("open_id = ?",open_id)
                .select("*")
                .getItem(new AngelOperatorBaseModel());


    }
    //根据open_id获取征信信息。
    public static AngelOperatorCreditModel getOperatorCredit(long open_id) throws SQLException {
        return db().table("angel_operator_credit")
                .where("open_id = ?",open_id)
                .select("*")
                .getItem(new AngelOperatorCreditModel());
    }
    //根据open_id获取运营商信息的oss地址。
    public static AngelOperatorTotalModel getOperatorTotal(long open_id) throws SQLException {
        return db().table("angel_operator_total")
                .where("open_id= ?",open_id)
                .select("*")
                .getItem(new AngelOperatorTotalModel());
    }

    //根据open_id获取联系人信息。
    public static UserExLinkmanModel getUserLink(long open_id) throws SQLException {
        return db().table("user_ex_linkman")
                .where("open_id = ?",open_id)
                .select("*")
                .getItem(new UserExLinkmanModel());
    }

    //根据open_id获取通讯录信息
    public static UserExContactListModel getContactList(long open_id) throws SQLException{
        return db().table("user_ex_contact_list")
                .where("open_id = ?",open_id)
                .select("*")
                .getItem(new UserExContactListModel());
    }

    //解析同盾运营商账单信息
    public static List<AngelOperatorBill> doTdOperatorBill(String billJson,int type) {
        List<AngelOperatorBill> out = new ArrayList<>();
        if (type == 1){
            //同盾运营商
            JSONArray array = JSONArray.parseArray(billJson);
            for (Object m:array){
                JSONObject obj = JSONObject.parseObject(JSON.toJSONString(m));
                String bill_cycle = obj.getString("bill_cycle");
                String bill_total = obj.getString("bill_total");
                DecimalFormat df = new DecimalFormat("0.00");
                String s = df.format((float)Integer.parseInt(bill_total)/100);
                double total = Double.parseDouble(s);
                AngelOperatorBill bill = new AngelOperatorBill();
                bill.bill_fee = total;
                bill.bill_month = Integer.parseInt(bill_cycle.replaceAll("-",""));
                bill.bill_start_date = Integer.parseInt(bill_cycle.replaceAll("-","")+"01");
                bill.bill_end_date = Integer.parseInt(getMonthEnd(bill_cycle.replaceAll("-","")));
                out.add(bill);
            }
        } else if (type == 2){
            //魔蝎运营商
            JSONObject json = JSONObject.parseObject(billJson);
            JSONArray bills = json.getJSONArray("bills");
            for (Object m:bills){
                JSONObject obj = JSONObject.parseObject(JSON.toJSONString(m));
                String bill_month = obj.getString("bill_month");
                String bill_start_date = obj.getString("bill_start_date");
                String bill_end_date = obj.getString("bill_month");
                String bill_total = obj.getString("total_fee");
                DecimalFormat df = new DecimalFormat("0.00");
                String s = df.format((float)Integer.parseInt(bill_total)/100);
                double total = Double.parseDouble(s);
                AngelOperatorBill bill = new AngelOperatorBill();
                bill.bill_fee = total;
                bill.bill_month = Integer.parseInt(bill_month.replaceAll("-",""));
                bill.bill_start_date = Integer.parseInt(bill_start_date.replaceAll("-",""));
                bill.bill_end_date = Integer.parseInt(bill_end_date.replaceAll("-",""));
                out.add(bill);
            }
        }
        return out;
    }

    public static String getMonthEnd(String monthStr) {
        String month = monthStr.substring(4, 6);
        if (month.equals("01")||month.equals("03")||month.equals("05")||month.equals("07")||month.equals("08")||month.equals("10")
                ||month.equals("12")){
            return monthStr+"31";
        } else if (month.equals("04")||month.equals("06")||month.equals("09")||month.equals("11")){
            return monthStr+"30";
        } else {
            String year = monthStr.substring(0, 4);
            if (Integer.parseInt(year)%4==0){
                return monthStr+"28";
            } else {
                return monthStr+"29";
            }
        }
    }

    //解析通话记录和通讯录
    public static CallLogResp dealCallLog(Integer page, Integer pageSize, String query_mobile, AngelOperatorTotalModel total,
                                          String oss_url_concacts, boolean is_hide, int type) throws Exception{

        CallLogResp resp = new CallLogResp();

        if (!TextUtils.isEmpty(total.details_url_all)) {

            List<CallLogDetailsResp> list = new ArrayList<>();

            //产生通讯录字典
            String contactsStr = OssUtil.getOssObject(oss_url_concacts, 0);
            JSONArray contacts = JSONArray.parseArray(contactsStr);
            Map<String, String> links = new HashMap<>();
            for (Object m:contacts) {
                JSONObject map = JSON.parseObject(JSONObject.toJSONString(m));
                String name = map.getString("name");
                try {
                    String mobileValue = map.getString("mobile");
                    if (!TextUtils.isEmpty(mobileValue)){
                        if (mobileValue.startsWith("[")&&mobileValue.endsWith("]")){
                            JSONArray mobileList = map.getJSONArray("mobile");
                            int size = mobileList.size();
                            for (int i = 0; i < size; i++) {
                                links.put(mobileList.getString(i),name);
                            }
                        } else {
                            String mobile = map.getString("mobile");
                            String[] mobiles = mobile.split(",");
                            int length = mobiles.length;
                            for (int i = 0; i < length; i++) {
                                links.put(mobiles[i],name);
                            }
                        }
                    }
                } catch (Exception ex){}
            }

            if (type==0){
                //闪银运营商
                String details = OssUtil.getOssObject(total.details_url_all, 0);
                JSONObject json = JSON.parseObject(details);
                String transportation = json.getJSONObject("data").getString("transportation");
                JSONArray objects = JSONArray.parseArray(transportation);
                JSONObject  jsonObject = objects.getJSONObject(0).getJSONObject("operator_callprofile");
                JSONArray operator_callprofile = JSONArray.parseArray(jsonObject.getString("total_contact_info"));

                for (Object m:operator_callprofile) {
                    JSONObject map = JSON.parseObject(JSONObject.toJSONString(m));
                    String phone = map.getString("phone");
                    int call_in_count = map.getIntValue("call_in_count");
                    int call_out_count = map.getIntValue("call_out_count");
                    CallLogDetailsResp calllog = new CallLogDetailsResp();
                    calllog.phone = phone;
                    calllog.call_in_count = call_in_count;
                    calllog.call_out_count = call_out_count;
                    calllog.total_count = call_in_count+call_out_count;
                    String name = links.get(phone);

                    if (TextUtils.isEmpty(query_mobile)){
                        if(TextUtils.isEmpty(name)){
                            calllog.name = "运营商无匹配";
                        } else {
                            calllog.name = name;
                        }
                        list.add(calllog);
                    } else {
                        if(query_mobile.equals(phone)){
                            if(TextUtils.isEmpty(name)){
                                calllog.name = "运营商无匹配";
                            } else {
                                calllog.name = name;
                            }
                            list.add(calllog);
                        }
                    }
                }

                resp.count = list.size();
            } else if (type==1){
                //同盾运营商
                String details = OssUtil.getOssObject(total.details_url_call_log, 0);
                JSONArray calllog = JSONArray.parseArray(details);
                Map<String, CallLogDetailsResp> map = new HashMap<>();
                int size = calllog.size();
                for (int i = 0; i < size; i++) {
                    JSONObject obj = calllog.getJSONObject(i);
                    JSONArray call_record = obj.getJSONArray("call_record");
                    for (Object m:call_record){
                        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(m));
                        String call_type_name = json.getString("call_type_name");
                        String mobile = json.getString("call_other_number");
                        String name = links.get(mobile);
                        CallLogDetailsResp calllogdetail = map.get(mobile);
                        if (calllogdetail==null){
                            calllogdetail = new CallLogDetailsResp();
                            calllogdetail.phone = mobile;
                        }
                        if (TextUtils.isEmpty(query_mobile)){
                            if (call_type_name.contains("被叫")){
                                calllogdetail.call_in_count = calllogdetail.call_in_count+1;
                            } else if (call_type_name.contains("主叫")){
                                calllogdetail.call_out_count = calllogdetail.call_out_count+1;
                            }
                            if(TextUtils.isEmpty(name)){
                                calllogdetail.name = "运营商无匹配";
                            } else {
                                calllogdetail.name = name;
                            }
                            calllogdetail.total_count = calllogdetail.call_in_count+calllogdetail.call_out_count;
                            map.put(mobile,calllogdetail);
                        } else {
                            if(query_mobile.equals(mobile)){
                                if(TextUtils.isEmpty(name)){
                                    calllogdetail.name = "运营商无匹配";
                                } else {
                                    calllogdetail.name = name;
                                }
                                calllogdetail.total_count = calllogdetail.call_in_count+calllogdetail.call_out_count;
                                map.put(mobile,calllogdetail);
                            }
                        }
                    }
                }
                for (String key : map.keySet()) {
                    CallLogDetailsResp m = map.get(key);
                    list.add(m);
                }
                resp.count = list.size();
            } else if (type == 2){
                //魔蝎运营商
                String details = OssUtil.getOssObject(total.details_url_call_log, 0);
                JSONObject detailsJson = JSONObject.parseObject(details);
                JSONArray calllog = detailsJson.getJSONArray("list");
                Map<String, CallLogDetailsResp> map = new HashMap<>();
                int size = calllog.size();
                for (int i = 0; i < size; i++) {
                    JSONObject obj = calllog.getJSONObject(i);
                    JSONArray call_record = obj.getJSONArray("calls");
                    for (Object m:call_record){
                        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(m));

                        String call_type_name = json.getString("dial_type");
                        String mobile = json.getString("peer_number");
                        String name = links.get(mobile);
                        CallLogDetailsResp calllogdetail = map.get(mobile);
                        if (calllogdetail==null){
                            calllogdetail = new CallLogDetailsResp();
                            calllogdetail.phone = mobile;
                        }
                        if (TextUtils.isEmpty(query_mobile)){
                            if ("DIALED".equals(call_type_name)){
                                //被叫
                                calllogdetail.call_in_count = calllogdetail.call_in_count+1;
                            } else if ("DIAL".equals(call_type_name)){
                                //主叫
                                calllogdetail.call_out_count = calllogdetail.call_out_count+1;
                            }
                            if(TextUtils.isEmpty(name)){
                                calllogdetail.name = "运营商无匹配";
                            } else {
                                calllogdetail.name = name;
                            }
                            calllogdetail.total_count = calllogdetail.call_in_count+calllogdetail.call_out_count;
                            map.put(mobile,calllogdetail);
                        } else {
                            if(query_mobile.equals(mobile)){
                                if(TextUtils.isEmpty(name)){
                                    calllogdetail.name = "运营商无匹配";
                                } else {
                                    calllogdetail.name = name;
                                }
                                calllogdetail.total_count = calllogdetail.call_in_count+calllogdetail.call_out_count;
                                map.put(mobile,calllogdetail);
                            }
                        }
                    }
                }
                for (String key : map.keySet()) {
                    CallLogDetailsResp m = map.get(key);
                    list.add(m);
                }
                resp.count = list.size();
            }

            Collections.sort(list, new Comparator<CallLogDetailsResp>(){
                public int compare(CallLogDetailsResp o1, CallLogDetailsResp o2) {
                    //按照CallLogResp的total_count字段进行降序排列
                    if(o1.total_count < o2.total_count){
                        return 1;
                    }
                    if(o1.total_count == o2.total_count){
                        return 0;
                    }
                    return -1;
                }
            });

            ArrayList<CallLogDetailsResp> callLogDetails = new ArrayList<>();
            int length = (page+1)*pageSize;
            if(length>list.size()){
                length = list.size();
            }
            for (int i = page*pageSize; i < length; i++) {
                CallLogDetailsResp detailList = list.get(i);
                callLogDetails.add(detailList);
            }

            resp.details = callLogDetails;
        } else {
            ArrayList<CallLogDetailsResp> callLogDetails = new ArrayList<>();
            resp.details = callLogDetails;
            resp.count = 0;
        }
        return resp;
    }

    //根据open_id获取用户基本信息。
    public static UserExBaseInfoModel getBaseInfo(long open_id) throws SQLException {
        return db().table("user_ex_base_info")
                .where("open_id = ?",open_id)
                .select("*")
                .getItem(new UserExBaseInfoModel());

    }
    //根据open_id获取用户工作信息。
    public static UserExWorkInfoModel getWorkInfo(long open_id) throws SQLException {
        return db().table("user_ex_work_info")
                .where("open_id = ?",open_id)
                .select("*")
                .getItem(new UserExWorkInfoModel());
    }
    //根据用户open_id获取用户常用地址。
    public static List<LogUserOperationModel> getUserOperation(long open_id) throws SQLException {
        return db().table("log_user_operation")
                .where("open_id = ?",open_id)
                .select("*")
                .getList(new LogUserOperationModel());
    }

    public static UserModel getUserRegisterInfo(long open_id) throws SQLException {
        return db().table("user")
                .where("open_id = ?",open_id)
                .select("*")
                .getItem(new UserModel());

    }

    //获取用户邦盛信息
    public static AngelRiskBModel getBsInfo(long open_id) throws  SQLException{
        return db().table("angel_risk_bs")
                .where("open_id = ?",open_id)
                .orderBy("row_id desc").limit(1)
                .select("*")
                .getItem(new AngelRiskBModel());
    }

    //解析邦盛数据
    public static JSONObject getBSDetail(long open_id) throws Exception{
        JSONObject resp = new JSONObject();
        AngelRiskBModel bs = getBsInfo(open_id);
        if (bs.row_id > 0) {
            String detail = OssUtil.getOssObject(bs.risk_details_url, 0);
            JSONObject json = JSONObject.parseObject(detail);
            json.put("ctime",bs.create_fulltime);
            json.put("utime",bs.update_fulltime);
            return json;
        } else {
            return resp;
        }
    }

    //获取用户众马风险评估信息
    public static AngelRiskRubberModel getRubberInfo(long open_id) throws  SQLException{
        return db().table("angel_risk_rubber")
                .where("open_id = ?",open_id)
                .orderBy("row_id desc").limit(1)
                .select("*")
                .getItem(new AngelRiskRubberModel());
    }

    //解析众马风险数据
    public static JSONObject getRubberDetail(long open_id) throws Exception{
        JSONObject resp = new JSONObject();
        AngelRiskRubberModel rubber = getRubberInfo(open_id);
        if (rubber.row_id > 0) {
            String detail = OssUtil.getOssObject(rubber.details_url, 0);
            JSONObject json = JSONObject.parseObject(detail).getJSONObject("evaluation");

            json.put("ctime",rubber.create_fulltime);
            json.put("utime",rubber.update_fulltime);
            return json;
        } else {
            return resp;
        }
    }


    //获取用户芝麻分
    public static AngelZmScoreModel getUserZmScore(long open_id) throws SQLException{
        return db().table("angel_zm_score")
                .where("open_id = ?",open_id)
                .select("*")
                .getItem(new AngelZmScoreModel());
    }

    //获取用户芝麻反欺诈分
    public static AngelZmAntifraudScoreModel getUserZmAntifraudScore(long open_id) throws SQLException{
        return db().table("angel_zm_antifraud_score")
                .where("open_id = ?",open_id)
                .select("*")
                .getItem(new AngelZmAntifraudScoreModel());
    }

    public static AngelZmAntifraudRisklistModel getUserZmAntifraudRiskliste(long open_id) throws SQLException{
        return db().table("angel_zm_antifraud_risklist")
                .where("open_id = ?",open_id)
                .select("*")
                .getItem(new AngelZmAntifraudRisklistModel());
    }

    //解析芝麻信用欺诈关注名单
    public static List<ZmAntifraudRisklistResp> dealZmAntifraudRisk(String risk_code){
        List<ZmAntifraudRisklistResp> riskList = new ArrayList<>();
        if (TextUtils.isEmpty(risk_code))
            return riskList;
        if (!TextUtils.isEmpty(risk_code.trim())) {
            String[] riskCode = risk_code.trim().split(",");
            for (String code : riskCode) {
                ZmAntifraudRisklistResp resp = new ZmAntifraudRisklistResp();
                resp.setDescribe(ZMRiskDescEnum.getEnumByCode(code).getValue());
                resp.setRiskLevel(ZMRiskLevelEnum.getEnumByCode(code).getValue());
                riskList.add(resp);
            }
        }
        return riskList;
    }

    //获取芝麻信用企业观察名单信息
    public static AngelZmWatchlistModel getZmWatchList(long open_id) throws SQLException{
        return db().table("angel_zm_watchlist")
                .where("open_id = ?",open_id)
                .select("*")
                .getItem(new AngelZmWatchlistModel());
    }

    //解析芝麻信用行业关注名单数据
    public static List<ZmWatchListResp> dealZmWatchList(ONode details){
        List<ZmWatchListResp> resp = new ArrayList<>();
            ONode detailArr = details.asArray();
            if (detailArr != null) {
                for (int i = 0 ; i < detailArr.count() ; i++) {
                    ZmWatchListResp zmWatch = new ZmWatchListResp();
                    ONode detail = detailArr.get(i);
                    zmWatch.setBiz_code(ZMIndustryTypeEnum.getEnumByCode(detail.get("biz_code").getString()).getValue());
                    zmWatch.setRefreshDate(detail.get("refresh_time").getString());
                    zmWatch.setSettlement(detail.get("settlement").getBoolean());
                    zmWatch.setType(ZMIndustryRiskTypeEnum.getEnumByCode(detail.get("type").getString()).getValue());
                    zmWatch.setCode(ZMIndustryCatchEnum.getEnumByCode(detail.get("code").getString()).getValue());
                    ONode extend_info = detail.get("extend_info").asArray();
                    if (extend_info != null){
                        for (int j = 0 ; j < extend_info.count() ; j++){
                            ONode info = extend_info.get(j);
                            if ("event_max_amt_code".equals(info.get("key").getString())) {
                                zmWatch.setOverDueAmount(ZMOverDueAmountEnum.getEnumByCode(info.get("value").getString()).getValue());
                            }
                            if ("event_end_time_desc".equals(info.get("key").getString())) {
                                zmWatch.setBreakDate(info.get("value").getString());
                            }
                        }
                    }
                    resp.add(zmWatch);
                }
            }
        return resp;
    }

    //获取用户同盾信息
    public static AngelRiskTdModel getTongdun(long open_id) throws SQLException{
        return db().table("angel_risk_td")
                .where("open_id = ?",open_id)
                .select("*")
                .getItem(new AngelRiskTdModel());
    }

    //处理同盾风险项数据
    public static TDReportResp dealTDRisk(AngelRiskTdModel td){
        String content = OssUtil.load(td.details_url);
        TDReportResp report = new TDReportResp();
        if(content != null){
            ONode details = ONode.load(content);

            List<TDRisksResp> platform = new ArrayList<>();
            List<TDRisksResp> discredit = new ArrayList<>();
            List<TDRisksResp> crossEvent = new ArrayList<>();
            List<TDRisksResp> others = new ArrayList<>();
            report.setScore(details.get("final_score").getInt());
            report.setReportId(details.get("report_id").getString());
            report.setQueryDate(td.update_fulltime);

            ONode risks = details.get("risk_items").asArray();
            if(risks != null){
                for (int i = 0; i < risks.count(); i++) {
                    ONode risk = risks.get(i);
                    TDRisksResp riskResp = new TDRisksResp();
                    riskResp.setHitRule(risk.get("item_name").getString());
                    if(!TextUtils.isEmpty(risk.get("risk_level").getString())){
                        riskResp.setRiskLevel(RiskLevelEnum.getEnumByCode(risk.get("risk_level").getString()).getValue());
                    }else{
                        riskResp.setRiskLevel("");
                    }
                    ONode riskDetail = risk.get("item_detail");
                    if (riskDetail != null) {
                        String detailType = riskDetail.get("type").getString();
                        if (TDDetailTypeEnum.PLATFORMDETAIL.getCode().equals(detailType)) {
                            //多头借贷类
                            riskResp.setHitDetail("申请平台数："+riskDetail.get("platform_count").getInt());
                            platform.add(riskResp);
                        } else if (TDDetailTypeEnum.DISCREDITCOUNT.getCode().equals(detailType)) {
                            //信贷逾期类
                            ONode overdue_details = riskDetail.get("overdue_details").asArray();
                            StringBuilder hitDetail = new StringBuilder();
                            int overDueSize = overdue_details.count();
                            for (int j = 0; j < overDueSize; j++) {
                                ONode overdue = overdue_details.get(j);
                                hitDetail.append("逾期金额："+overdue.get("overdue_amount_range").getString()+"、逾期笔数："+
                                        overdue.get("overdue_count").getInt()+"、逾期天数："+overdue.get("overdue_day_range").getString()+
                                        "、逾期入库时间："+overdue.get("overdue_time").getString()+"; ");
                            }
                            riskResp.setHitDetail(hitDetail + "");
                            discredit.add(riskResp);
                        } else if (TDDetailTypeEnum.FREQUENCYDETAIL.getCode().equals(detailType)) {
                            //跨事件频度统计
                            ONode frequency_detail_list =  riskDetail.get("frequency_detail_list").asArray();
                            if (frequency_detail_list != null){
                                ONode detail = frequency_detail_list.get(0);
                                riskResp.setHitDetail(detail.get("detail").getString());
                                crossEvent.add(riskResp);
                            }
                        } else {
                            riskResp.setHitDetail("命中");
                            others.add(riskResp);
                        }
                    } else {
                        riskResp.setHitDetail("命中");
                        others.add(riskResp);
                    }
                }
            }
            report.setPlatform(platform);
            report.setDiscredit(discredit);
            report.setCrossEvent(crossEvent);
            report.setOthers(others);
        }
        return report;
    }

    //获取用户主表信息
    public static UserModel getUser(long open_id) throws SQLException{
        return db().table("user")
                .where("open_id = ?",open_id)
                .select("*")
                .getItem(new UserModel());
    }

    /**
    *@Author:Fei.chu
    *@Date:下午5:15 2018/4/2
    *@Description:获取用户设备指纹记录
    */
    public static JSONArray getDeviceLog(long open_id) throws SQLException{
        JSONArray resp = new JSONArray();
        List<LogUserOperationModel> logs = db().table("log_user_operation")
                .where("open_id = ?",open_id)
                .orderBy("row_id desc").limit(10)
                .select("*")
                .getList(new LogUserOperationModel());
        for (LogUserOperationModel m:logs) {
            JSONObject json = new JSONObject();
            DeviceModel device = getDevice(m.device_id);
            json.put("date",m.log_fulltime);
            json.put("gps",m.lat+"/"+m.lng);
            json.put("device_name",device.device_name);
            json.put("device_udid",device.device_udid);
            json.put("address", BaiduUtil.getAddressByLatAndLng(m.lat,m.lng));
            resp.add(json);
        }
        return resp;
    }

    public static DeviceModel getDevice(long device_id) throws SQLException{
        return db().table("device")
                .where("device_id = ?",device_id)
                .select("*")
                .caching(CacheUtil.dataCache)
                .getItem(new DeviceModel());
    }

    /**
    *@Author:Fei.chu
    *@Date:上午10:12 2018/4/3
    *@Description:更新同盾分
    */
    public static boolean updateTD(long open_id) throws Exception{
        UserModel user = getUser(open_id);
        String report = postTongDun(open_id, user.mobile);

        if (TextUtils.isEmpty(report)) {
            return false;
        }
        JSONObject result = new JSONObject();
        try {
            result = JSON.parseObject(report);
        } catch (Exception ex) {
            return false;
        }
        String risk = result.get("risk_items")+"";
        int score = Integer.valueOf(result.get("final_score")+"");

        Date now = new Date();
        int nowInt = DateUtil.getDate(now);
        AngelRiskTdModel td = getTongdun(open_id);
        //添加同盾数据历史备份
        addUserBackH(JSONObject.toJSONString(td),open_id,"angel_risk_td");
        //同盾风险项上传oss
        String details_url = backupsStr(open_id, report);

        boolean tdResp = updateTDTRisk(score, details_url, open_id, now, nowInt);
        boolean stateResp = updateUserStateTDScore(open_id, score);
        if (tdResp && stateResp) {
            return true;
        } else {
            return false;
        }
    }

    /**
    *@Author:Fei.chu
    *@Date:上午11:08 2018/4/3
    *@Description:更新同盾风险项
    */
    public static boolean updateTDTRisk(int score,String details_url,long open_id,Date update_fulltime,int update_date) throws SQLException{
        return db().table("angel_risk_td")
                .where("open_id = ?",open_id)
                .set("score",score)
                .set("details_url",details_url)
                .set("update_fulltime",update_fulltime)
                .set("update_date",update_date)
                .update() > 0;
    }

    /**
    *@Author:Fei.chu
    *@Date:上午11:12 2018/4/3
    *@Description:更新用户状态表同盾分
    */
    public static boolean updateUserStateTDScore(long open_id,int td_score) throws SQLException{
        return db().table("user_state")
                .where("open_id = ?",open_id)
                .set("td_score",td_score)
                .update() > 0;
    }

    /**
     *@Author:Fei.chu
     *@Date:13:47 2018/2/27
     *@Description:上传用户某项信息到oss
     */
    public static String backupsStr(long open_id,String str) throws IOException{
        InputStream inputStream = new ByteArrayInputStream(str.getBytes());
        String back_key = OssUtil.uploadStreamH(inputStream, StringUtil.genUUID(),open_id,"angel");
        return back_key;
    }
    /**
     *@Author:Fei.chu
     *@Date:上午10:27 2018/4/3
     *@Description:请求同盾接口
     */
    public static String postTongDun(long open_id,String mobile) throws Exception{
        HashMap<String,String> param = new HashMap<>();
        UserExIdentificationModel idcard = getIdentification(open_id);

        if (idcard.open_id == 0) {
            return "";
        }
        UserExDcardModel dCard = getDCard(open_id);
        UserExWorkInfoModel work = getWorkInfo(open_id);
        UserExBaseInfoModel base = getBaseInfo(open_id);
        UserExLinkmanModel contact = getContactInfo(open_id);

        param.put("name",idcard.id_name);
        param.put("id_number",idcard.id_code);
        param.put("mobile",mobile);
        param.put("is_id_checked","true");
        param.put("registered_address",idcard.id_address);//户籍地址 xx省xx市xx县xx镇xx村xx组xx号
        if (dCard.open_id > 0) {
            param.put("card_number",dCard.card_code);//银行卡号
        }
        if (work.open_id > 0) {
            param.put("work_phone",work.cophone);//公司座机 格式(区号+号码) 0571-1111111
            param.put("career", CareerEnum.getEnumByCode(work.career).getValue());//职业 枚举

            param.put("work_time", WorkTimeEnum.getEnumByCode(work.career_time).getValue());//工作时间 （1年以下；1年；2年；3-4年；5-7年；8-9年；10年以上）其中之一
            param.put("company_name",work.coname);//工作单位
            param.put("company_address",work.coaddress);//单位地址
            param.put("company_industry", CoIndustryEnum.getEnumByCode(work.co_industry).getValue());//公司行业
            param.put("company_type", ConatureEnum.getEnumByCode(work.conature).getValue());//公司性质
            param.put("occupation",TDoccupationEnum.getEnumByCode(work.position).getValue());//职位 参照职位表
            param.put("annual_income", SalaryEnum.getEnumByCode(work.salary).getValue());//年收入
        }
        if (base.open_id > 0) {
            param.put("qq",base.qq);
            param.put("diploma", TDdiplomaEnum.getEnumByCode(base.qualification).getValue());//学历 参照学历表
            param.put("marriage", TDmarryStatEnum.getEnumByCode(base.mstatus).getValue());//婚姻状况 参照婚姻状态表
            param.put("house_property", HousePropertyEnum.getEnumByCode(base.house_status).getValue());//房产情况  （无房；有房有贷款；有房无贷款；其他）其中之一
            if(!"无房".equals(HousePropertyTypeEnum.getEnumByCode(base.house_type).getValue())){
                param.put("house_type",HousePropertyTypeEnum.getEnumByCode(base.house_type).getValue());//房产类型 （商品房；经济适用房；商住两用房；拆迁房；自建房；宅基地；临建房；期房；预售房；其他 ）其中之一
            }
        }
        if (contact.open_id > 0) {
            param.put("contact1_relation",MainContactsEnum.getEnumByCode(contact.contact_e1_type).getValue());//第一联系人社会关系
            param.put("contact1_name",contact.contact_e1_name);//第一联系人姓名
            param.put("contact1_mobile",contact.contact_e1_mobile);//第一联系人手机号
            param.put("contact2_relation",OtherContactsEnum.getEnumByCode(contact.contact_e2_type).getValue());//第二联系人社会关系
            param.put("contact2_name",contact.contact_e2_name);//第二联系人姓名
            param.put("contact2_mobile",contact.contact_e2_mobile);//第二联系人手机号
        }

        String reportId = TongDunApi.getReportId(param, TDRoleEnum.ONE.getCode());

        String report = TongDunApi.submit(reportId, TDRoleEnum.ONE.getCode());

        return report;
    }

    public static UserExIdentificationModel getIdentification(long open_id) throws SQLException{
        return db().table("user_ex_identification")
                .where("open_id = ?",open_id)
                .select("*")
                .caching(CacheUtil.dataCache)
                .getItem(new UserExIdentificationModel());
    }

    public static UserExDcardModel getDCard(long open_id) throws SQLException{
        return db().table("user_ex_dcard")
                .where("open_id = ?",open_id)
                .select("*")
                .caching(CacheUtil.dataCache)
                .getItem(new UserExDcardModel());
    }

    public static UserExLinkmanModel getContactInfo(long open_id) throws SQLException{
        return db().table("user_ex_linkman")
                .where("open_id = ?",open_id)
                .select("*")
                .caching(CacheUtil.dataCache)
                .getItem(new UserExLinkmanModel());
    }

    /**
     *@Author:Fei.chu
     *@Date:13:47 2018/2/27
     *@Description:添加备份表数据
     */
    public static void addUserBackH(String jsonStr,long open_id,String table_name) throws SQLException,IOException {
        InputStream inputStream = new ByteArrayInputStream(jsonStr.getBytes());
        String back_key = OssUtil.uploadStreamH(inputStream, StringUtil.genUUID(),open_id,"angel");
        Date now = new Date();
        int nowInt = DateUtil.getDate(now);
        db().table("user_back")
                .set("row_id", IDUtil.buildUserBackID())
                .set("open_id",open_id)
                .set("details_url",back_key)
                .set("table_name",table_name)
                .set("s_type",0)
                .set("create_date",nowInt)
                .set("create_fulltime",now)
                .insert();
        /*user_back_add sp = new user_back_add();
        sp.row_id = IDUtil.buildUserBackID();
        sp.open_id = open_id;
        sp.details_url = back_key;
        sp.table_name = table_name;
        sp.s_type = 0;
        sp.create_date = nowInt;
        sp.create_fulltime = now;
        sp.execute();*/
    }

    //拼装计算方案beauty请求参数
    public static Map<String,String>  getRubberReqParam(long open_id,int agroup_id,int ugroup_id) throws SQLException{

        String mobile = getUser(open_id).mobile;

        Long device_id = db().table("user_lk_device")
                .where("open_id = ?", open_id)
                .and("agroup_id = ?",agroup_id)
                .and("ugroup_id = ?",ugroup_id)
                .orderBy("create_fulltime desc").limit(1)
                .select("device_id")
                .getValue(0L);

        String device_udid = db().table("device")
                .where("device_id = ?",device_id)
                .select("device_udid")
                .getValue("");

        Object ip = db().table("log_user_operation")
                .where("open_id = ?", open_id)
                .and("agroup_id = ?",agroup_id)
                .and("ugroup_id = ?",ugroup_id)
                .orderBy("row_id desc")
                .limit(1)
                .select("ip")
                .getValue("");

        Long order_id = db().table("user_lk_order")
                .where("open_id = ?",open_id)
                .and("agroup_id = ?",agroup_id)
                .and("ugroup_id = ?",ugroup_id)
                .orderBy("order_linkid desc")
                .limit(1)
                .select("order_linkid")
                .getValue(0L);

        JSONObject args = new JSONObject();
        args.put("open_id",open_id);
        args.put("mobile",mobile);
        args.put("req_device",device_udid);
        args.put("req_ip",ip);
        args.put("agroup_id",agroup_id);
        args.put("order_id",order_id);

        HashMap<String,String> param = new HashMap<>();
        param.put("args",args.toJSONString());
        param.put("type","0");
        param.put("policy","2001");
        param.put("scheme","angel/risk");
        param.put("callback",Config.sponge_url+"notify/rubber");

        return param;
    }

    public static String postRubber(long open_id,int agroup_id,int ugroup_id) throws Exception{
        Map<String, String> param = getRubberReqParam(open_id,agroup_id,ugroup_id);

        String resp = HttpUtils.postString(Config.raas_uri+"/release", param);

        return resp;
    }

    public static boolean updateUserStateRubberScore(long open_id,int rubber_score) throws SQLException{
        return db().table("user_state")
                .set("rubber_score",rubber_score)
                .where("open_id = ?",open_id)
                .update()>0;
    }

    public static boolean addAngelRiskRubber(long open_id,int agroup_id,long order_id,String request_id,int score,int advise,String details_url) throws SQLException{
        Date now = new Date();
        int nowInt = DateUtil.getDate(now);
        long row_id = IDUtil.buildAngelRiskRubberID();
        return db().table("angel_risk_rubber")
                .set("row_id",row_id)
                .set("open_id",open_id)
                .set("agroup_id",agroup_id)
                .set("order_id",order_id)
                .set("request_id",request_id)
                .set("score",score)
                .set("advise",advise)
                .set("details_url",details_url)
                .set("create_fulltime",now)
                .set("create_date",nowInt)
                .set("update_fulltime",now)
                .set("update_date",nowInt)
                .insert()>0;

    }

}
