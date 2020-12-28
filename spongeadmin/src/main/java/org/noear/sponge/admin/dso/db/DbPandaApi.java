package org.noear.sponge.admin.dso.db;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.noear.sponge.admin.controller.ViewModel;
import org.noear.sponge.admin.dso.CacheUtil;
import org.noear.sponge.admin.dso.IDUtil;
import org.noear.sponge.admin.models.others.CountModel;
import org.noear.sponge.admin.models.others.resp.CallLogDetailsResp;
import org.noear.sponge.admin.models.panda.*;
import org.noear.sponge.admin.utils.DateUtil;
import org.noear.sponge.admin.utils.StringUtil;
import org.noear.water.WaterClient;
import org.noear.water.utils.Base64Utils;
import org.noear.water.utils.IDUtils;
import org.noear.weed.DbContext;
import org.noear.weed.DbTableQuery;
import org.apache.http.util.TextUtils;
import org.noear.sponge.admin.Config;
import org.noear.sponge.admin.models.others.resp.CallLogResp;
import org.noear.sponge.admin.models.rock.AppGroupModel;
import org.noear.sponge.admin.models.rock.UserGroupModel;
import org.noear.sponge.admin.utils.OssUtil;

import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.*;

/**
 * @Author:Fei.chu
 * @Date:Created in 11:16 2019/03/07
 * @Description:认证中心
 */
public class DbPandaApi {
    private static DbContext db() {
        return Config.panda;
    }

    private static void clearCache(String tag){
        CacheUtil.clearByTag(tag);
        WaterClient.Notice.updateCache(tag);
    }

    //分页查询用户总览列表
    public static List<UserStateModel> getUserStateList(int page, int pageSize, CountModel count, String mobile, int is_real,
                                                        int date_start, int date_end) throws SQLException{
        int start = (page - 1) * pageSize;
        DbTableQuery query = db().table("user_state");
        query.where("is_real = ?",is_real)
            .expre(tb->{
                if (TextUtils.isEmpty(mobile) == false){
                    tb.and("mobile = ?",mobile);
                }
                if (date_start>0&&date_end>0){
                    tb.and("real_date >= ?",date_start)
                            .and("real_date <= ?",date_end);
                }
        });

        count.setCount(query.count());
        return  query.orderBy("open_id desc")
                .limit(start, pageSize)
                .select("*")
                .getList(new UserStateModel());
    }

    public static UserModel getUser(long open_id) throws SQLException{
        return db().table("user")
                .where("open_id = ?",open_id)
                .caching(CacheUtil.dataCache)
                .select("*")
                .getItem(new UserModel());
    }

    //基本信息
    public static UserExBaseInfoModel getUserBase(long open_id) throws SQLException{
        return db().table("user_ex_base_info")
                .where("open_id = ?",open_id)
                .caching(CacheUtil.dataCache)
                .select("*")
                .getItem(new UserExBaseInfoModel());
    }

    //工作信息
    public static UserExWorkInfoModel getUserWork(long open_id) throws SQLException{
        return db().table("user_ex_work_info")
                .where("open_id = ?",open_id)
                //.caching(CacheUtil.dataCache)
                .select("*")
                .getItem(new UserExWorkInfoModel());
    }

    //身份证
    public static UserExIdentificationModel getIdentification(long open_id) throws SQLException{
        return db().table("user_ex_identification")
                .where("open_id = ?",open_id)
                .caching(CacheUtil.dataCache)
                .select("*")
                .getItem(new UserExIdentificationModel());
    }

    //人脸识别记录
    public static List<LogUserSimilarcheckModel> getLogUserSimilarckecks(long open_id) throws SQLException{
        return db().table("log_user_similarcheck")
                .where("open_id = ?",open_id)
                .orderBy("log_id desc")
                .limit(10)
                .select("*")
                .getList(new LogUserSimilarcheckModel());
    }

    //获取用户淘宝账户信息
    public static PandaTbAccountModel getPandaTbAccount(long open_id) throws SQLException{
        return db().table("panda_tb_account")
                .where("open_id = ?",open_id)
                .caching(CacheUtil.dataCache)
                .select("*")
                .getItem(new PandaTbAccountModel());
    }

    //获取用户淘宝基本信息
    public static PandaTbBaseModel getPandaTbBase(long open_id) throws SQLException{
        return db().table("panda_tb_base")
                .where("open_id = ?",open_id)
                .caching(CacheUtil.dataCache)
                .select("*")
                .getItem(new PandaTbBaseModel());
    }

    //获取用户淘宝汇总信息
    public static PandaTbTotalModel getPandaTbTotal(long open_id) throws SQLException{
        return db().table("panda_tb_total")
                .where("open_id = ?",open_id)
                .caching(CacheUtil.dataCache)
                .select("*")
                .getItem(new PandaTbTotalModel());
    }

    //解析淘宝信息
    public static JSONObject doTaobao(String detail_url) throws Exception{
        JSONObject out = new JSONObject();
        if (!TextUtils.isEmpty(detail_url)){
            String details = OssUtil.getOssObject(detail_url, 0);
            JSONObject json = JSONObject.parseObject(details);
            int code = json.getIntValue("code");
            if (code==0){
                JSONObject data = json.getJSONObject("data");
                JSONObject task_data = data.getJSONObject("task_data");
                out.put("receiver_list",task_data.getJSONArray("receiver_list"));
                JSONObject my_favorite = task_data.getJSONObject("my_favorite");
                out.put("favor_shop_list",my_favorite.getJSONArray("favor_shop_list"));
                out.put("my_footprint",my_favorite.getJSONArray("my_footprint"));
                out.put("favor_good_list",my_favorite.getJSONArray("favor_good_list"));

                //购物车
                out.put("shopping_cart",task_data.getJSONArray("shopping_cart"));

                out.put("huabei_bill_list",task_data.getJSONArray("huabei_bill_list"));
            }
        }
        return out;
    }


    //解析淘宝订单数据
    public static JSONObject doTaobaoOrder(int page, int pageSize,int date_start,int date_end,String order_status,String detail_url) throws Exception{
        JSONObject out = new JSONObject();
        JSONArray array = new JSONArray();
        if (!TextUtils.isEmpty(detail_url)){
            String details = OssUtil.getOssObject(detail_url, 0);
            JSONObject json = JSONObject.parseObject(details);
            int code = json.getIntValue("code");
            if (code==0){
                JSONObject data = json.getJSONObject("data");
                JSONObject task_data = data.getJSONObject("task_data");
                JSONArray order_list = task_data.getJSONArray("order_list");
                int size = order_list.size();
                for (int i = 0; i < size; i++) {
                    boolean flag = true;
                    JSONObject order = order_list.getJSONObject(i);
                    if (date_start>0&&date_end>0){
                        String order_time = order.getString("order_time");
                        int date = DateUtil.getDate(DateUtil.StrToDate(order_time, "yyyy-MM-dd HH:mm:ss"));
                        if (date<date_start||date>date_end){
                            flag=false;
                        }
                    }
                    if (!TextUtils.isEmpty(order_status)&&!"全部".equals(order_status)){
                        String status = order.getString("order_status");
                        if (flag==true){
                            if (!order_status.equals(status)){
                                flag = false;
                            }
                        }
                    }

                    if (flag==true){
                        JSONArray product_list = order.getJSONArray("product_list");
                        int list_size = product_list.size();
                        int product_amount = 0;
                        String productAll = "";
                        String product_name = "";
                        String product_price = "";
                        for (int j = 0; j < list_size; j++) {
                            JSONObject product = product_list.getJSONObject(j);
                            product_amount = product_amount + product.getIntValue("product_amount");
                            String name = product.getString("product_name");
                            productAll = productAll+"[ "+name+" ]; ";
                            if (j==0){
                                product_name = name;
                                product_price = product.getString("product_price");
                            }
                        }
                        order.put("product_amount",product_amount);
                        order.put("product_name",product_name);
                        order.put("product_price",product_price);
                        order.put("productAll",productAll);
                        array.add(order);
                    }
                }
            }
            out.put("counts",array.size());

            JSONArray result = new JSONArray();
            int length = (page+1)*pageSize;
            if(length>array.size()){
                length = array.size();
            }
            for (int k = page*pageSize; k < length; k++) {
                JSONObject json1 = array.getJSONObject(k);
                result.add(json1);
            }
            out.put("array",result);
        }
        return out;
    }


    //获取用户邮箱信用卡信息
    public static PandaEmailCcardModel getEmailCcard(long open_id,String email) throws SQLException{
        return db().table("panda_email_ccard")
                .where("open_id = ?",open_id)
                .expre(tb -> {
                    if (!TextUtils.isEmpty(email)){
                        tb.and("email = ?",email);
                    }
                })
                .limit(1)
                .select("*")
                .getItem(new PandaEmailCcardModel());
    }

    public static List<PandaEmailCcardModel> getEmailCcards(long open_id) throws SQLException{
        List<PandaEmailCcardModel> list = db().table("panda_email_ccard")
                .where("open_id = ?", open_id)
                .select("email")
                .getList(new PandaEmailCcardModel());

        for (PandaEmailCcardModel e:list) {
            e.hide_email = (StringUtil.getHideEmail(e.email));
        }
        return list;
    }


    public static JSONArray getEmailData(PandaEmailCcardModel email) throws Exception{

        if (!TextUtils.isEmpty(email.report_data)){
            String detail = OssUtil.getOssObject(email.report_data, 0);
            JSONArray report = JSONArray.parseArray(detail);
            String detail_bills = OssUtil.getOssObject(email.data_all, 0);
            JSONObject bills = JSONObject.parseObject(detail_bills);
            JSONArray billList = bills.getJSONArray("bills");
            int size = report.size();
            for (int i = 0; i < size; i++) {
                JSONObject reportDate = report.getJSONObject(i);
                String name = reportDate.getJSONObject("user_basic_information").getString("name");
                reportDate.put("name_hide", StringUtil.hideName(name));
                reportDate.put("base64_name", URLEncoder.encode(Base64Utils.encode(name),"UTF-8"));
                Set<Integer> bankSet = new HashSet<>();
                int billSize = billList.size();
                JSONArray banks = new JSONArray();
                for (int j = 0; j < billSize; j++) {
                    JSONObject bill = billList.getJSONObject(j);
                    String name_on_card = bill.getString("name_on_card");
                    int bank_id = bill.getIntValue("bank_id");
                    String bankName = StringUtil.getMoxieBankName(bank_id);
                    bill.put("bankName",bankName);
                    if (name.equals(name_on_card) && !bankSet.contains(bank_id)){
                        banks.add(bill);
                    }
                    bankSet.add(bank_id);
                }
                reportDate.put("bankList",banks);
            }
            return report;
        } else {
            return new JSONArray();
        }
    }

    public static JSONObject getEmailReport(PandaEmailCcardModel email,String email_name) throws Exception{
        JSONObject out = new JSONObject();
        if (!TextUtils.isEmpty(email.report_data)){
            String detail = OssUtil.getOssObject(email.report_data, 0);
            JSONArray report = JSONArray.parseArray(detail);
            int size = report.size();
            for (int i = 0; i < size; i++) {
                JSONObject reportDate = report.getJSONObject(i);
                String name = reportDate.getJSONObject("user_basic_information").getString("name");
                reportDate.put("name_hide",StringUtil.hideName(name));
                reportDate.put("base64_name", Base64Utils.encode(name));

                if (email_name.equals(name)){
                    out = reportDate;
                }
            }
        }
        return out;
    }

    //根据邮箱获取信用卡邮箱数据
    public static PandaEmailCcardModel getEmailCcard(String email,long open_id) throws SQLException{
        return db().table("panda_email_ccard")
                .where("open_id = ?",open_id)
                .and("email = ?",email)
                .caching(CacheUtil.dataCache)
                .select("*")
                .getItem(new PandaEmailCcardModel());
    }

    //解析信用卡账单详情
    public static JSONObject doEmailBills(int page, int pageSize,String bank_id,String oss_url,int type) throws Exception{
        JSONObject out = new JSONObject();
        JSONArray list = new JSONArray();
        String detail = OssUtil.getOssObject(oss_url, 0);
        JSONObject json = JSONObject.parseObject(detail);
        JSONArray billsArr = json.getJSONArray("bills");

        int size = billsArr.size();
        for (int i = 0; i < size; i++) {
            JSONObject bill = billsArr.getJSONObject(i);
            String bankId = bill.getString("bank_id");
            if (bank_id.equals(bankId)){
                if (type==0){
                    //账单
                    String original = bill.getString("original");
                    String originalName = "";
                    switch (original){
                        case "1":originalName="一手账单";break;
                        case "2":originalName="疑似一手账单";break;
                        case "3":originalName="疑似假账单";break;
                        case "-1":originalName="邮件来源未知";break;
                        case "-2":originalName="发件人无效";break;
                        case "-3":originalName="IP无效";break;
                        case "4":originalName="异常";break;
                        default:break;
                    }
                    JSONObject billJson = new JSONObject();
                    billJson.put("bill_start_date",bill.getString("bill_start_date"));
                    billJson.put("original",originalName);
                    billJson.put("name_on_card",bill.getString("name_on_card"));
                    billJson.put("credit_limit",bill.getString("credit_limit"));
                    billJson.put("bill_date",bill.getString("bill_date"));
                    billJson.put("payment_due_date",bill.getString("payment_due_date"));
                    billJson.put("new_balance",bill.getString("new_balance"));
                    billJson.put("min_payment",bill.getString("min_payment"));
                    billJson.put("last_balance",bill.getString("last_balance"));
                    billJson.put("last_payment",bill.getString("last_payment"));
                    billJson.put("point",bill.getString("point"));
                    list.add(billJson);
                } else if (type==1){
                    //消费
                    JSONArray shopping_sheets = bill.getJSONArray("shopping_sheets");//消费
                    if (shopping_sheets!=null){
                        int shoppingSize = shopping_sheets.size();
                        for (int j = 0; j < shoppingSize; j++) {
                            JSONObject shop = shopping_sheets.getJSONObject(j);
                            list.add(shop);
                        }
                    }
                } else if (type==2){
                    //分期
                    JSONArray installments = bill.getJSONArray("installments");//分期
                    if (installments!=null){
                        int instalSize = installments.size();
                        for (int j = 0; j < instalSize; j++) {
                            JSONObject instal = installments.getJSONObject(j);
                            list.add(instal);
                        }
                    }
                }
            }
        }

        out.put("counts",list.size());

        JSONArray result = new JSONArray();
        int length = (page+1)*pageSize;
        if(length>list.size()){
            length = list.size();
        }
        for (int k = page*pageSize; k < length; k++) {
            JSONObject json1 = list.getJSONObject(k);
            result.add(json1);
        }
        out.put("list",result);
        return out;
    }

    //获取用户公积金
    public static PandaAcumFundModel getAcumFund(long open_id,String region) throws SQLException{
        return db().table("panda_acum_fund")
                .where("open_id = ?",open_id)
                .expre(tb -> {
                    if (!TextUtils.isEmpty(region)){
                        tb.and("region = ?",region);
                    }
                })
                //.caching(CacheUtil.dataCache)
                .limit(1)
                .select("*")
                .getItem(new PandaAcumFundModel());
    }

    public static List<PandaAcumFundModel> getAcumFundRegions(long open_id) throws SQLException{
        return db().table("panda_acum_fund")
                .where("open_id = ?",open_id)
                //.caching(CacheUtil.dataCache)
                .select("region")
                .getList(new PandaAcumFundModel());
    }

    //获取公积金缴存记录
    public static JSONObject getAcumfundPayments(int page, int pageSize,String oss_url) throws Exception{
        JSONObject out = new JSONObject();
        if (!TextUtils.isEmpty(oss_url)){
            String detail = OssUtil.getOssObject(oss_url, 0);
            JSONArray list = JSONArray.parseArray(detail);

            out.put("counts",list.size());

            JSONArray result = new JSONArray();
            int length = (page+1)*pageSize;
            if(length>list.size()){
                length = list.size();
            }
            for (int k = page*pageSize; k < length; k++) {
                JSONObject json1 = list.getJSONObject(k);
                result.add(json1);
            }
            out.put("list",result);
        }
        return out;
    }

    //获取用户网银银行列表
    public static List<PandaCyberBankTotalModel> getCyberBankTotals(long open_id) throws SQLException{
        return db().table("panda_cyber_bank_total")
                .where("open_id = ?",open_id)
                .caching(CacheUtil.dataCache)
                .select("bank_name")
                .getList(new PandaCyberBankTotalModel());
    }

    //获取网银汇总信息
    public static PandaCyberBankTotalModel getCyberBankTotal(long open_id,String bank_name) throws SQLException{
        return db().table("panda_cyber_bank_total")
                .where("open_id = ?",open_id)
                .expre(tb -> {
                    if (!TextUtils.isEmpty(bank_name)){
                        tb.and("bank_name = ?",bank_name);
                    }
                })
                .caching(CacheUtil.dataCache)
                .limit(1)
                .select("*")
                .getItem(new PandaCyberBankTotalModel());
    }

    public static JSONObject doCyberBankReport(String oss_url) throws Exception{
        JSONObject out = new JSONObject();
        if (!TextUtils.isEmpty(oss_url)){
            String detail = OssUtil.getOssObject(oss_url, 0);
            JSONObject json = JSONObject.parseObject(detail);
            JSONObject debitcard = json.getJSONObject("debitcard");
            JSONObject creditcard = json.getJSONObject("creditcard");
            if (debitcard!=null){
                JSONObject basic = debitcard.getJSONObject("user_basic_info");
                String name = basic.getString("name");
                out.put("name",StringUtil.hideName(name));
                out.put("certify_no",basic.getString("certify_no"));
                out.put("mobile",basic.getString("mobile"));
                out.put("address",basic.getString("address"));
                String email = basic.getString("email");
                if (!email.contains("未提供")){
                    email = StringUtil.getHideEmail(email);
                }
                out.put("email",email);
                out.put("last_company_name",basic.getString("last_company_name"));
            } else {
                if (creditcard !=null) {
                    JSONObject basic = creditcard.getJSONObject("user_basic_info");
                    String name = basic.getString("name");
                    out.put("name",StringUtil.hideName(name));
                    String email = basic.getString("email");
                    if (!email.contains("未提供")){
                        email = StringUtil.getHideEmail(email);
                    }
                    out.put("email",email);
                }
            }
        }
        return out;
    }

    public static JSONObject doCyberBankInfo(String oss_url) throws Exception{
        JSONObject out = new JSONObject();
        JSONArray dcards = new JSONArray();
        JSONArray ccards = new JSONArray();
        if (!TextUtils.isEmpty(oss_url)){
            String detail = OssUtil.getOssObject(oss_url, 0);
            JSONArray list = JSONArray.parseArray(detail);
            int size = list.size();
            for (int i = 0; i < size; i++) {
                JSONObject json = list.getJSONObject(i);
                String card_type = json.getString("card_type");
                String card_num = json.getString("card_num");
                String bank_name = json.getString("bank_name");
                String balance = json.getString("balance");
                if ("借记卡".equals(card_type)) {
                    JSONObject dcard = new JSONObject();
                    dcard.put("name_on_card",StringUtil.hideName(json.getString("name_on_card")));
                    dcard.put("card_num",card_num);
                    dcard.put("bank_name",bank_name);
                    dcard.put("balance",balance);
                    JSONArray deposits = json.getJSONArray("deposits");
                    int dep_size = deposits.size();
                    if (dep_size>0){
                        JSONObject deposit = deposits.getJSONObject(0);
                        dcard.put("deposit_type",deposit.getString("deposit_type"));
                        dcard.put("deposit_date",deposit.getString("deposit_date"));
                        dcard.put("due_date",deposit.getString("due_date"));
                        dcard.put("period",deposit.getString("period"));
                        dcard.put("interest",deposit.getString("interest"));
                    }
                    dcards.add(dcard);
                } else if ("信用卡".equals(card_type)) {
                    JSONObject ccard = new JSONObject();
                    ccard.put("card_num",card_num);
                    ccard.put("bank_name",bank_name);
                    ccard.put("balance",balance);
                    ccard.put("current_bill_amt",json.getString("current_bill_amt"));
                    ccard.put("current_bill_remain_min_payment",json.getString("current_bill_remain_min_payment"));

                    JSONArray bills = json.getJSONArray("bills");
                    int billSize = bills.size();
                    if (billSize>0){
                        JSONObject bill = bills.getJSONObject(0);
                        ccard.put("bill_date",bill.getString("bill_date"));
                        ccard.put("payment_due_date",bill.getString("payment_due_date"));
                        ccard.put("last_balance",bill.getString("last_balance"));
                        ccard.put("last_payment",bill.getString("last_payment"));
                    }
                    ccards.add(ccard);
                }
            }
        }
        out.put("dcard",dcards);
        out.put("ccard",ccards);
        return out;
    }

    //获取网银报告详情
    public static JSONObject getCyberBankReportDetail(String oss_url,int type) throws Exception{
        JSONObject out = new JSONObject();
        if (!TextUtils.isEmpty(oss_url)){
            String detail = OssUtil.getOssObject(oss_url, 0);
            JSONObject json = JSONObject.parseObject(detail);
            if (type==0){
                //借记卡
                out = json.getJSONObject("debitcard");
            } else if (type==1){
                out = json.getJSONObject("creditcard");
            }
        }
        return out;
    }

    //解析网银借记卡交易记录
    public static JSONArray getCyberBankDcardShops(String oss_url,String bank_name) throws Exception{
        JSONArray out = new JSONArray();
        if (!TextUtils.isEmpty(oss_url)){
            String detail = OssUtil.getOssObject(oss_url, 0);
            JSONArray list = JSONArray.parseArray(detail);
            int size = list.size();
            for (int i = 0; i < size; i++) {
                JSONObject json = list.getJSONObject(i);
                String _bankName = json.getString("bank_name");
                if (bank_name.equals(_bankName)){
                    JSONArray bills = json.getJSONArray("bills");
                    int billSize = bills.size();
                    for (int j = 0; j < billSize; j++) {
                        JSONObject jsonObject = bills.getJSONObject(j);
                        JSONArray shopping_sheets = jsonObject.getJSONArray("shopping_sheets");
                        out.addAll(shopping_sheets);
                    }
                }
            }
        }
        return out;
    }

    //解析网银信用卡账单记录
    public static JSONObject getCyberBankCcardBills(String oss_url,String bank_name) throws Exception{
        JSONObject out = new JSONObject();
        JSONArray bills = new JSONArray();
        JSONArray installments = new JSONArray();
        JSONArray shopping_sheets = new JSONArray();
        String name_on_card = "";
        if (!TextUtils.isEmpty(oss_url)){
            String detail = OssUtil.getOssObject(oss_url, 0);
            JSONArray list = JSONArray.parseArray(detail);
            int size = list.size();
            for (int i = 0; i < size; i++) {
                JSONObject json = list.getJSONObject(i);
                String _bankName = json.getString("bank_name");
                if (bank_name.equals(_bankName)){
                    name_on_card = json.getString("name_on_card");
                    bills.addAll(json.getJSONArray("bills"));
                    int billSize = bills.size();
                    for (int j = 0; j < billSize; j++) {
                        JSONObject jsonObject = bills.getJSONObject(j);
                        shopping_sheets.addAll(jsonObject.getJSONArray("shopping_sheets"));
                        installments.addAll(jsonObject.getJSONArray("installments"));
                    }
                }
            }
        }
        out.put("name_on_card",name_on_card);
        out.put("bills",bills);
        out.put("installments",installments);
        out.put("shopping_sheets",shopping_sheets);
        return out;
    }

    //根据open_id获取运营商基本信息。
    public static PandaOperatorBaseModel getOperatorBase(long open_id) throws SQLException {
        return db().table("panda_operator_base")
                .where("open_id = ?",open_id)
                .select("*")
                .getItem(new PandaOperatorBaseModel());


    }
    //根据open_id获取征信信息。
    public static PandaOperatorCreditModel getOperatorCredit(long open_id) throws SQLException {
        return db().table("panda_operator_credit")
                .where("open_id = ?",open_id)
                .select("*")
                .getItem(new PandaOperatorCreditModel());
    }
    //根据open_id获取运营商信息的oss地址。
    public static PandaOperatorTotalModel getOperatorTotal(long open_id) throws SQLException {
        return db().table("panda_operator_total")
                .where("open_id= ?",open_id)
                .select("*")
                .getItem(new PandaOperatorTotalModel());
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


    //解析通话记录和通讯录
    public static CallLogResp dealCallLog(Integer page, Integer pageSize, String query_mobile, PandaOperatorTotalModel total,
                                          String oss_url_concacts, boolean is_hide, int type) throws Exception{

        CallLogResp resp = new CallLogResp();

        if (!TextUtils.isEmpty(total.details_url_all)) {

            List<CallLogDetailsResp> list = new ArrayList<>();

            Map<String, String> links = new HashMap<>();
            //产生通讯录字典
            if (!TextUtils.isEmpty(oss_url_concacts)){
                String contactsStr = OssUtil.getOssObject(oss_url_concacts, 0);
                JSONArray contacts = JSONArray.parseArray(contactsStr);

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

    //查询完成的各个认证项集合
    public static List<UserStateModel> getStateList(String type,int dateStart,int dateEnd) throws SQLException{
        List<UserStateModel> states = new ArrayList<>();
        if ("identification".equals(type)){
            states = db().table("user_state s")
                    .leftJoin("user u")
                    .on("s.open_id = u.open_id")
                    .where("s.real_date >= ?",dateStart)
                    .and("s.real_date <= ?",dateEnd)
                    .and("s.is_real = 1")
                    .orderBy("s.real_date asc")
                    .limit(100000)
                    .select("u.ukey,s.open_id,s.real_date,s.real_fulltime")
                    .getList(new UserStateModel());
        } else if ("operator".equals(type)) {
            states = db().table("user_state s")
                    .leftJoin("user u")
                    .on("s.open_id = u.open_id")
                    .where("s.operator_date >= ?",dateStart)
                    .and("s.operator_date <= ?",dateEnd)
                    .and("s.is_operator = 1")
                    .orderBy("s.operator_date asc")
                    .limit(100000)
                    .select("u.ukey,s.open_id,s.operator_date,s.operator_fulltime")
                    .getList(new UserStateModel());
        } else if ("taobao".equals(type)) {
            states = db().table("user_state s")
                    .leftJoin("user u")
                    .on("s.open_id = u.open_id")
                    .where("s.taobao_date >= ?",dateStart)
                    .and("s.taobao_date <= ?",dateEnd)
                    .and("s.is_taobao = 1")
                    .orderBy("s.taobao_date asc")
                    .limit(100000)
                    .select("u.ukey,s.open_id,s.taobao_date,s.taobao_fulltime")
                    .getList(new UserStateModel());
        } else if ("base".equals(type)) {
            states = db().table("user_state s")
                    .leftJoin("user u")
                    .on("s.open_id = u.open_id")
                    .where("s.base_date >= ?",dateStart)
                    .and("s.base_date <= ?",dateEnd)
                    .and("s.is_base = 1")
                    .orderBy("s.base_date asc")
                    .limit(100000)
                    .select("u.ukey,s.open_id,s.base_date,s.base_fulltime")
                    .getList(new UserStateModel());
        } else if ("work".equals(type)) {
            states = db().table("user_state s")
                    .leftJoin("user u")
                    .on("s.open_id = u.open_id")
                    .where("s.work_date >= ?",dateStart)
                    .and("s.work_date <= ?",dateEnd)
                    .and("s.is_work = 1")
                    .orderBy("s.work_date asc")
                    .limit(100000)
                    .select("u.ukey,s.open_id,s.work_date,s.work_fulltime")
                    .getList(new UserStateModel());
        } else if ("linkman".equals(type)) {
            states = db().table("user_state s")
                    .leftJoin("user u")
                    .on("s.open_id = u.open_id")
                    .where("s.linkman_date >= ?",dateStart)
                    .and("s.linkman_date <= ?",dateEnd)
                    .and("s.is_linkman = 1")
                    .and("s.is_telbook = 1")
                    .orderBy("s.linkman_date asc")
                    .limit(100000)
                    .select("u.ukey,s.open_id,s.linkman_date,s.linkman_fulltime")
                    .getList(new UserStateModel());
        }
        return states;
    }

    //查询卡bin银行tag
    public static List<BankCardBinModel> getBanks() throws SQLException{
        return db().table("bank_card_bin")
                .groupBy("bank_name")
                .orderBy("bank_code asc")
                .caching(CacheUtil.dataCache).cacheTag("panda_bank_card_bin_bank_code").usingCache(6 * 10)
                .select("bank_name,bank_code")
                .getList(new BankCardBinModel());
    }

    //获取卡类型集合
    public static List<BankCardBinModel> getCardTypes(String bank_name) throws SQLException{

        return db().table("bank_card_bin")
                .where("bank_name = ?",bank_name)
                .groupBy("card_type")
                .caching(CacheUtil.dataCache).cacheTag("panda_bank_card_bin_card_type"+bank_name).usingCache(6 * 10)
                .select("card_type")
                .getList(new BankCardBinModel());
    }

    //分页获取卡bin列表
    public static List<BankCardBinModel> getBankCardBins(int page, int pageSize,CountModel count,String bank_name,String card_type,String bin) throws SQLException{

        int start = (page - 1) * pageSize;
        DbTableQuery query = db().table("bank_card_bin")
                .where("bank_name = ?",bank_name);

        if (!TextUtils.isEmpty(card_type)){
            query.and("card_type = ?",card_type);
        }

        if (!TextUtils.isEmpty(bin)){
            query.and("bin like ?",bin+"%");
        }
        count.setCount(query.count());
        return  query.orderBy("id desc")
                .limit(start, pageSize)
                .select("*")
                .getList(new BankCardBinModel());
    }

    public static BankCardBinModel getBankCardBinById(long id) throws SQLException{
        return db().table("bank_card_bin")
                .where("id = ?",id)
                .limit(1)
                .select("*")
                .getItem(new BankCardBinModel());
    }

    //编辑卡bin
    public static boolean setBankCardBin(long id,String bank_name,String org_no,String logo,String card_type,String card_name,int card_length,
                                         int bin_length,String bin,String bank_code) throws SQLException{
        DbTableQuery dbSet = db().table("bank_card_bin")
                .set("bank_name", bank_name)
                .set("org_no", org_no)
                .set("logo", logo)
                .set("card_type", card_type)
                .set("card_name", card_name)
                .set("card_length", card_length)
                .set("bin_length", bin_length)
                .set("bin", bin)
                .set("bank_code", bank_code);
        if (id==0){
            //insert
            dbSet.set("id", IDUtils.newID("ID","panda_bank_card_bin",60 * 60 * 24 * 365)+10000)
                    .insert();
        } else {
            //update
            dbSet.where("id = ?",id)
                    .update();
        }
        clearCache("panda_bank_card_bin_bank_code");
        clearCache("panda_bank_card_bin_card_type"+bank_name);
        return true;
    }

    //分页获取支行列表
    public static List<BranchBankModel> getBranchBanks(int page, int pageSize,CountModel count,String bank_code,String name) throws SQLException{
        int start = (page - 1) * pageSize;
        DbTableQuery query = db().table("branch_bank")
                .where("1 = 1");

        if (!TextUtils.isEmpty(bank_code)){
            query.and("bank_code = ?",bank_code);
        }

        if (!TextUtils.isEmpty(name)){
            query.and("`name` like ?",name+"%");
        }
        count.setCount(query.count());
        return  query.orderBy("id asc")
                .limit(start, pageSize)
                .select("*")
                .getList(new BranchBankModel());
    }

    public static BranchBankModel getBranchBankById(long id) throws SQLException{
        return db().table("branch_bank")
                .where("id = ?",id)
                .select("*")
                .getItem(new BranchBankModel());
    }

    public static List<BranchBankModel> getBranchBankCodes() throws SQLException{
        return db().table("branch_bank")
                .groupBy("bank_code")
                .caching(CacheUtil.dataCache).cacheTag("panda_branch_bank_code").usingCache(6 * 10)
                .select("bank_code")
                .getList(new BranchBankModel());
    }

    //编辑支行
    public static boolean setBranchBank(long id,String bank_code,String paysys_bank,String name,String bank_no,String phone,String bank_addr) throws SQLException{
        DbTableQuery dbSet = db().table("branch_bank")
                .set("bank_code", bank_code)
                .set("paysys_bank", paysys_bank)
                .set("name", name)
                .set("bank_no", bank_no)
                .expre(tb -> {
                    if (!TextUtils.isEmpty(phone)){
                        tb.set("phone",phone);
                    }
                    if (!TextUtils.isEmpty(bank_addr)){
                        tb.set("bank_addr",bank_addr);
                    }
                });

        if (id==0){
            //insert
            dbSet.set("id", IDUtils.newID("ID","panda_branch_bank",60 * 60 * 24 * 365)+150000)
                    .insert();
        } else {
            //update
            dbSet.where("id = ?",id)
                    .update();
        }
        clearCache("panda_branch_bank_code");
        return true;
    }

    //分页查询信用报告订单
    public static List<RiskReportOrderModel> getRiskReportOrders(int page, int pageSize, CountModel count, String mobile,
                                                                 String order_no,String out_order_no,int create_date_begin,
                                                                 int create_date_end,int finish_date_begin,int finish_date_end,
                                                                 int pay_type,int type,int status) throws SQLException{
        int start = (page - 1) * pageSize;
        DbTableQuery query = db().table("risk_report_order");
        query.where("1 = 1")
                .expre(tb -> {
                    if (TextUtils.isEmpty(mobile) == false){
                        tb.and("mobile = ?",mobile);
                    }
                    if (TextUtils.isEmpty(order_no) == false){
                        tb.and("order_no = ?",order_no);
                    }
                    if (TextUtils.isEmpty(out_order_no) == false){
                        tb.and("out_order_no = ?",out_order_no);
                    }
                    if (create_date_begin<=create_date_end&&create_date_begin>0&&create_date_end>0){
                        tb.and("create_date >= ?",create_date_begin)
                        .and("create_date <= ?",create_date_end);
                    }
                    if (finish_date_begin<=finish_date_end&&finish_date_begin>0&&finish_date_end>0){
                        tb.and("finish_date >= ?",finish_date_begin)
                                .and("finish_date <= ?",finish_date_end);
                    }
                    if (pay_type>0){
                        tb.and("pay_type = ?",pay_type);
                    }
                    if (type>0){
                        tb.and("type = ?",type);
                    }
                    if (status>0){
                        if (status==33){
                            tb.and("(status = 3 or status = 4)");
                        } else {
                            tb.and("status = ?",status);
                        }
                    }
                });

        count.setCount(query.count());
        return  query.orderBy("order_id desc")
                .limit(start, pageSize)
                .select("*")
                .getList(new RiskReportOrderModel());
    }

    //获取用户借记卡
    public static List<UserExDcardModel> getDcardList(long open_id) throws SQLException{
        return db().table("user_ex_dcard")
                .where("open_id = ?",open_id)
                .orderBy("is_main desc")
                .select("*")
                .getList(new UserExDcardModel());
    }

    //获取用户信用卡
    public static List<UserExCcardModel> getCcardList(long open_id) throws SQLException{
        return db().table("user_ex_ccard")
                .where("open_id = ?",open_id)
                .select("*")
                .getList(new UserExCcardModel());
    }

    //分页查询黑名单
    public static List<RiskBlacklistModel> getBlacklist(int page, int pageSize, CountModel count, String key, int agroup_id,int from_agroup_id,int reason_type,int log_date_start,int log_date_end) throws SQLException{
        int start = (page - 1) * pageSize;
        DbTableQuery query = db().table("risk_blacklist")
                .where("1=1")
                .expre(tb->{
                    if (!TextUtils.isEmpty(key)){
                        tb.and("( mobile LIKE ? OR id_code LIKE ? OR id_name LIKE ?)","%"+key+"%","%"+key+"%","%"+key+"%");
                    }
                    if (agroup_id>0){
                        tb.and("agroup_id = ?",agroup_id);
                    }
                    if (from_agroup_id>0){
                        tb.and("from_agroup_id = ?",from_agroup_id);
                    }
                    if (reason_type>0){
                        tb.and("reason_type = ?",reason_type);
                    }
                    if (log_date_start>0&&log_date_end>0){
                        tb.and("log_date >= ?",log_date_start);
                        tb.and("log_date <= ?",log_date_end);
                    }
                });

        count.setCount(query.count());
        return  query.orderBy("row_id desc")
                .limit(start, pageSize)
                .select("*")
                .getList(new RiskBlacklistModel());
    }

    //根据id查询黑名单
    public static RiskBlacklistModel getBlacklist(long row_id) throws SQLException{
        return db().table("risk_blacklist")
                .where("row_id = ?",row_id)
                .select("*")
                .getItem(new RiskBlacklistModel());
    }

    //设置黑名单
    public static boolean setBlackList(long row_id,String mobile,String id_code,int ugroup_id,int agroup_id) throws SQLException{
        DbTableQuery dq = db().table("risk_blacklist")
                .set("mobile", mobile)
                .set("id_code", id_code)
                .set("ugroup_id", ugroup_id)
                .set("agroup_id", agroup_id);

        try {
            if (row_id>0){
                dq.where("row_id = ?",row_id)
                        .update();
            } else {
                long id = IDUtil.buildRiskBlackListID();
                dq.set("row_id",id)
                        .insert();
            }
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    public static ViewModel addBlacklist(JSONArray arr) throws SQLException{

        ViewModel out = new ViewModel();
        if (arr.size()>1001){
            out.put("msg","单次最多导入1000条");
            return out;
        }
        Map<String, Integer> agroup = getAgroupMap();
        int succ = 0;
        for (Object obj:arr) {
            JSONObject json = (JSONObject) obj;
            String mobile = json.getString("手机号").replaceAll(" ", "");
            if (TextUtils.isEmpty(mobile)){
                continue;
            }
            String id_code = json.getString("身份证号码");
            String id_name = json.getString("姓名");
            String from_agroup = json.getString("来源");
            String effect_agroup = json.getString("作用应用");
            String reason = json.getString("拉黑原因");
            Integer agroup_id = agroup.get(effect_agroup);
            Integer from_agroup_id = agroup.get(from_agroup);
            int reason_type = getReasonType(reason);

            boolean isIdCode = true;
            if (!TextUtils.isEmpty(id_code)){
                //校验身份证号格式
                isIdCode = StringUtil.isIDCard(id_code);
            }
            //校验手机号格式
            boolean isMobile = StringUtil.isMobile(mobile);

            try {
                if (isIdCode&&isMobile){
                    addBlacklist(mobile,id_code,id_name,agroup_id,from_agroup_id,reason_type);
                    succ++;
                }
            } catch (Exception ex){
            }
        }
        out.put("msg","成功导入"+succ+"条数据");
        return out;
    }

    public static boolean addBlacklist(String mobile,String id_code,String id_name,int agroup_id,int from_agroup_id,int reason_type) throws SQLException{
        Date log_fulltime = new Date();
        int log_date = DateUtil.getDate(log_fulltime);

        return db().table("risk_blacklist")
                .set("row_id",IDUtil.buildRiskBlackListID())
                .set("mobile",mobile)
                .set("id_code",id_code)
                .set("id_name",id_name)
                .set("agroup_id",agroup_id)
                .set("from_agroup_id",from_agroup_id)
                .set("reason_type",reason_type)
                .set("log_fulltime",log_fulltime)
                .set("log_date",log_date)
                .insert()>0;
    }

    public static int getReasonType(String reason) {
        int reason_type = 0;
        switch (reason){
            case "调单":reason_type=1;break;
            case "小贷":reason_type=2;break;
            case "风控":reason_type=3;break;
            case "用户要求":reason_type=4;break;
            default:break;
        }
        return reason_type;
    }

    //删除黑名单
    public static boolean deleteBlacklist(long row_id) throws SQLException{
        return db().table("risk_blacklist")
                .where("row_id = ?",row_id)
                .delete()>0;
    }

    public static Map<Integer,String> getUgroupNameMap() throws SQLException{
        Map<Integer, String> map = new HashMap<>();
        map.put(0,"全部");
        List<UserGroupModel> list = DbRockApi.getUserGroupList();
        for (UserGroupModel u:list) {
            map.put(u.ugroup_id,u.name);
        }
        return map;
    }

    public static Map<String,Integer> getUgroupMap() throws SQLException{
        Map<String, Integer> map = new HashMap<>();
        List<UserGroupModel> list = DbRockApi.getUserGroupList();
        for (UserGroupModel u:list) {
            map.put(u.name,u.ugroup_id);
        }
        return map;
    }

    public static Map<Integer,String> getAgroupNameMap() throws SQLException{
        Map<Integer, String> map = new HashMap<>();
        map.put(0,"全部");
        List<AppGroupModel> list = DbRockApi.getAppGroupList();
        for (AppGroupModel a:list) {
            map.put(a.agroup_id,a.name);
        }
        return map;
    }

    public static Map<String,Integer> getAgroupMap() throws SQLException{
        Map<String,Integer> map = new HashMap<>();
        map.put("全部",0);
        List<AppGroupModel> list = DbRockApi.getAppGroupList();
        for (AppGroupModel a:list) {
            map.put(a.name,a.agroup_id);
        }
        return map;
    }

    //获取黑名单约束应用组集合
    public static List<RiskBlacklistModel> getBlacklistAgroups() throws SQLException{
        return db().table("risk_blacklist")
                .groupBy("agroup_id")
                .select("agroup_id")
                .getList(new RiskBlacklistModel());
    }

    //获取黑名单应用组集合
    public static List<RiskBlacklistModel> getBlacklistFromAgroups() throws SQLException{
        return db().table("risk_blacklist")
                .groupBy("from_agroup_id")
                .select("from_agroup_id")
                .getList(new RiskBlacklistModel());
    }

    public static List<UserLkAppModel> getUserLkList(int page,int pageSize,String mobile,String id_name,int agroup_id,
                                                     int is_real,int has_face,CountModel count,int date_start,int date_end,
                                                     int real_date) throws SQLException{
        int start = (page - 1) * pageSize;
        DbTableQuery query = db().table("user_lk_app")
                .where("ugroup_id = 1")
                .expre(tb -> {
                    if (!TextUtils.isEmpty(mobile)){
                        tb.and("mobile = ?",mobile);
                    }
                    if (!TextUtils.isEmpty(id_name)){
                        tb.and("id_name = ?",id_name);
                    }
                    if (agroup_id > 0) {
                        tb.and("agroup_id = ?", agroup_id);
                    }
                    if (is_real >= 0) {
                        tb.and("is_real = ?", is_real);
                    }
                    if (has_face == 0) {
                        tb.and("similar_times = 0");
                    }
                    if (has_face > 0) {
                        tb.and("similar_times > 0");
                    }
                    if (date_start > 0 && date_end >0) {
                        tb.and("register_date >= ?",date_start)
                        .and("register_date <= ?",date_end);
                    }
                    if (real_date > 0){
                        tb.and("real_date = ?",real_date);
                    }
                });
        count.setCount(query.select("count(*)").getCount());

        List<UserLkAppModel> list = query.orderBy("app_linkid desc")
                .limit(start, pageSize)
                .select("*")
                .getList(new UserLkAppModel());

        Map<Integer, String> map = getAgroupNameMap();
        list.forEach(m->{
            m.platform = map.get(m.agroup_id);
        });
        return list;
    }
    
    public static String getPlatFormByMobile(String mobile) throws SQLException{
        List<UserLkAppModel> list = db().table("user_lk_app")
                .where("mobile = ?", mobile)
                .orderBy("app_linkid asc")
                .select("agroup_id")
                .getList(new UserLkAppModel());
        Map<Integer, String> map = getAgroupNameMap();
        StringBuilder sb = new StringBuilder();
        list.forEach(m->{
            sb.append(map.get(m.agroup_id)+"；");
        });

        return sb.toString();
    }

    public static List<UserLkAppModel> getUserLkPlatform(List<UserLkAppModel> list) throws SQLException{
        List<String> mobiles = new ArrayList<>();

        list.forEach(m->{mobiles.add(m.mobile);});

        List<UserLkAppModel> agroupList = db().table("user_lk_app")
                .where("mobile in (?...)",mobiles)
                .orderBy("register_date asc")
                .select("agroup_id,mobile")
                .getList(new UserLkAppModel());

        Map<Integer, String> map = getAgroupNameMap();
        HashMap<String, String> mapGroup = new HashMap<>();
        agroupList.forEach(m->{
            String s = mapGroup.get(m.mobile);
            if (TextUtils.isEmpty(s)){
                mapGroup.put(m.mobile,"<span style=\"color: red\">"+map.get(m.agroup_id)+"</span>");
            } else {
                mapGroup.put(m.mobile,s+"；"+map.get(m.agroup_id));
            }
        });

        list.forEach(m->{m.platform = mapGroup.get(m.mobile);});

        return list;
    }

    //获取最新一条认证记录
    public static LogUserAuthModel getLogUserAuth(long open_id,int type) throws SQLException{
        return db().table("log_user_auth")
                .where("open_id = ?",open_id)
                .and("type = ?",type)
                .orderBy("row_id desc")
                .limit(1)
                .select("*")
                .getItem(new LogUserAuthModel());
    }

    public static String getAuthFrom(long open_id,int type) throws SQLException{
        LogUserAuthModel log = getLogUserAuth(open_id, type);

        if (log.agroup_id>0){
            Map<Integer, String> map = getAgroupNameMap();
            return map.get(log.agroup_id);
        }

        return "51卡宝";
    }

    public static List<LogUserOperationModel> getLogOperations(long open_id,int agroup_id) throws SQLException{
        return db().table("log_user_operation")
                .where("open_id = ?",open_id)
                .and("agroup_id = ?",agroup_id)
                .orderBy("log_id desc")
                .select("*")
                .getList(new LogUserOperationModel());
    }
}
