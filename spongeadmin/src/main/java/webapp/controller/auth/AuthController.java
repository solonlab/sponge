package webapp.controller.auth;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.noear.bcf.BcfClient;
import org.apache.http.util.TextUtils;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.water.utils.Base64Utils;
import webapp.Config;
import webapp.controller.BaseController;
import webapp.dso.Session;
import webapp.dso.SwichUtil;
import webapp.dso.db.DbPandaApi;
import webapp.dso.db.DbUserApi;
import webapp.models.others.CountModel;
import webapp.models.others.resp.CallLogResp;
import webapp.models.panda.*;
import webapp.models.sponge_angel.AngelOperatorBill;
import webapp.utils.OssUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Fei.chu
 * @Date:Created in 17:11 2019/03/06
 * @Description:认证中心
 */
@Controller
public class AuthController extends BaseController {

    //列表
    @Mapping("auth/query")
    public ModelAndView query(Integer page, Integer pageSize, String mobile,Integer _state,Integer date_start,Integer date_end) throws SQLException {
        if (page == null) {
            page = 1; //从1开始（数据库那边要减1）
        }

        if (pageSize == null || pageSize == 0) {
            pageSize = 16;
        }
        if (_state == null) {
            _state = 0;
        }
        if (_state == 0) {
            _state = 1;
        } else if (_state==1){
            _state = 2;
        } else if (_state==2){
            _state = 0;
        }

        viewModel.put("date_start",date_start);
        viewModel.put("date_end",date_end);
        date_start = date_start==null?0:date_start;
        date_end = date_end==null?0:date_end;

        CountModel count = new CountModel();
        List<UserStateModel> users = DbPandaApi.getUserStateList(page, pageSize, count, mobile, _state,date_start,date_end);
        boolean is_hide = BcfClient.hasResourceByUser(Session.current().getPUID(), "panda_user_content");
        if (is_hide) {
            //关键字是否脱敏权限
            viewModel.put("is_hide",0);
        } else {
            viewModel.put("is_hide",1);
        }

        viewModel.put("pageSize", pageSize);
        viewModel.put("rowCount", count.getCount());
        viewModel.put("users", users);
        viewModel.put("mobile", mobile);

        return view("auth/query");
    }

    //基本信息
    @Mapping("auth/query_base")
    public ModelAndView base(Long open_id) throws SQLException{
        boolean is_hide = BcfClient.hasResourceByUser(Session.current().getPUID(), "panda_user_content");
        if (is_hide) {
            //关键字是否脱敏权限
            viewModel.put("is_hide",0);
        } else {
            viewModel.put("is_hide",1);
        }

        UserModel user = DbPandaApi.getUser(open_id);
        String plat = DbPandaApi.getPlatFormByMobile(user.mobile);
        viewModel.put("plat",plat);
        UserExBaseInfoModel baseInfo = DbPandaApi.getUserBase(open_id);
        String qualification = "";
        String mstatus = "";
        String house_status = "";
        String house_type = "";
        if (baseInfo.open_id > 0) {
            //学历
            qualification = SwichUtil.getQualification(baseInfo.qualification-1);
            //婚姻情况
            mstatus = SwichUtil.getMstatus(baseInfo.mstatus-1);
            //房产状况
            house_status = SwichUtil.getHouseStatus(baseInfo.house_status-1);
            //房产类型
            house_type = SwichUtil.getHouseType(baseInfo.house_type-1);
        }
        UserExWorkInfoModel workInfo = DbPandaApi.getUserWork(open_id);
        String career = "";
        String career_time = "";
        String salary = "";
        String co_industry = "";
        String conature = "";
        String position = "";
        if (workInfo.open_id > 0) {
            //职业类型
            career = SwichUtil.getCareer(workInfo.career-1);
            //工作时间
            career_time = SwichUtil.getCareer_time(workInfo.career_time-1);
            //年收入
            salary = SwichUtil.getSalary(workInfo.salary-1);
            //公司行业
            co_industry = SwichUtil.getCo_industry(workInfo.co_industry-1);
            //公司性质
            conature = SwichUtil.getConature(workInfo.conature-1);
            //职位
            position = SwichUtil.getPosition(workInfo.position-1);
        }

        viewModel.put("qualification", qualification);
        viewModel.put("mstatus", mstatus);
        viewModel.put("house_status", house_status);
        viewModel.put("house_type", house_type);
        viewModel.put("conature", conature);
        viewModel.put("position", position);

        viewModel.put("career", career);
        viewModel.put("career_time", career_time);
        viewModel.put("salary", salary);
        viewModel.put("co_industry", co_industry);

        viewModel.put("userRegister",user);
        viewModel.put("baseInfo",baseInfo);
        viewModel.put("workInfo",workInfo);
        viewModel.put("open_id",open_id);
        return view("auth/query_base");
    }

    //身份证信息
    @Mapping("auth/query_identification")
    public ModelAndView identification(Long open_id) throws SQLException{
        boolean is_hide = BcfClient.hasResourceByUser(Session.current().getPUID(), "panda_user_content");
        if (is_hide) {
            //关键字是否脱敏权限
            viewModel.put("is_hide",0);
        } else {
            viewModel.put("is_hide",1);
        }

        UserExIdentificationModel user = DbPandaApi.getIdentification(open_id);
        List<LogUserSimilarcheckModel> logs = DbPandaApi.getLogUserSimilarckecks(open_id);
        viewModel.put("from",DbPandaApi.getAuthFrom(open_id, 1));
        viewModel.put("user", user);
        viewModel.put("logs", logs);
        viewModel.put("open_id",open_id);
        return view("auth/query_identification");
    }

    //运营商
    @Mapping("auth/query_operator")
    public ModelAndView operator(Long open_id) throws SQLException{
        boolean is_hide = BcfClient.hasResourceByUser(Session.current().getPUID(), "panda_user_content");
        if (is_hide) {
            //关键字是否脱敏权限
            viewModel.put("is_hide",0);
        } else {
            viewModel.put("is_hide",1);
        }

        //基本细信息
        PandaOperatorBaseModel operatorBase = DbPandaApi.getOperatorBase(open_id);
        //征信
        PandaOperatorCreditModel operatorCredit = DbPandaApi.getOperatorCredit(open_id);
        //用户运营商汇总信息
        PandaOperatorTotalModel operatorTotal = DbPandaApi.getOperatorTotal(open_id);
        //用户联系人信息
        UserExLinkmanModel userLinkMan = DbPandaApi.getUserLink(open_id);

        if (operatorTotal.open_id > 0) {
            //通话账单。
            try {
                String ossData_url_bill = OssUtil.getOssObject(operatorTotal.details_url_bill, 0);
                List<AngelOperatorBill> operator_bill = new ArrayList<>();
                if (operatorTotal.type==0){
                    operator_bill = JSONArray.parseArray(ossData_url_bill, AngelOperatorBill.class);
                } else {
                    //同盾/魔蝎
                    operator_bill = DbUserApi.doTdOperatorBill(ossData_url_bill,operatorTotal.type);
                }
                viewModel.put("operator_bill", operator_bill);
            } catch (Exception ex) {
                ArrayList<AngelOperatorBill> operator_bill = new ArrayList<>();
                viewModel.put("operator_bill", operator_bill);
            }
        }
        viewModel.put("from",DbPandaApi.getAuthFrom(open_id, 2));
        viewModel.put("userLinkMan", userLinkMan);
        viewModel.put("operatorBase", operatorBase);
        viewModel.put("operatorCredit", operatorCredit);
        viewModel.put("open_id", open_id);


        return view("auth/query_operator");
    }

    //运营商通话记录
    @Mapping("auth/query_operator/query_operator_inner")
    public ModelAndView operatorInner(Long open_id,String mobile,Integer page, Integer pageSize) throws Exception{
        boolean is_hide = BcfClient.hasResourceByUser(Session.current().getPUID(), "sponge_user_content");
        if (is_hide) {
            //关键字是否脱敏权限
            viewModel.put("is_hide",0);
        } else {
            viewModel.put("is_hide",1);
        }

        if (page == null) {
            page = 0;
        } else {
            page = page -1;
        }

        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }

        PandaOperatorTotalModel operatorTotal = DbPandaApi.getOperatorTotal(open_id);
        UserExContactListModel contacts = DbPandaApi.getContactList(open_id);
        CallLogResp calllogs = DbPandaApi.dealCallLog(page,pageSize,mobile,operatorTotal, contacts.details_url,is_hide,operatorTotal.type);
        viewModel.put("calllogs", calllogs.details);
        viewModel.put("pageSize", pageSize);
        viewModel.put("rowCount",calllogs.count);
        viewModel.put("open_id", open_id);
        viewModel.put("mobile", mobile);
        return view("user/query_operator_inner");

    }

    //淘宝
    @Mapping("auth/query_taobao")
    public ModelAndView taobao(Long open_id) throws Exception{

        PandaTbBaseModel base = DbPandaApi.getPandaTbBase(open_id);
        PandaTbAccountModel account = DbPandaApi.getPandaTbAccount(open_id);
        PandaTbTotalModel total = DbPandaApi.getPandaTbTotal(open_id);
        JSONObject data = DbPandaApi.doTaobao(total.detail_url_all);

        viewModel.put("from",DbPandaApi.getAuthFrom(open_id, 4));
        viewModel.put("base",base);
        viewModel.put("account",account);
        viewModel.put("data",data);
        viewModel.put("open_id",open_id);

        return view("auth/query_taobao");
    }

    //淘宝订单
    @Mapping("auth/query_taobao_order")
    public ModelAndView taobaoOrder(Long open_id,Integer page, Integer pageSize,Integer date_start,Integer date_end,String order_status) throws Exception{

        if (page == null) {
            page = 0;
        } else {
            page = page -1;
        }

        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }
        viewModel.put("date_start", date_start);
        viewModel.put("date_end", date_end);
        if (date_start==null){
            date_start=0;
        }
        if (date_end==null){
            date_end=0;
        }

        PandaTbTotalModel total = DbPandaApi.getPandaTbTotal(open_id);
        JSONObject out = DbPandaApi.doTaobaoOrder(page, pageSize, date_start, date_end, order_status, total.detail_url_all);

        viewModel.put("open_id", open_id);
        viewModel.put("orders", out.getJSONArray("array"));
        viewModel.put("order_status", order_status);

        viewModel.put("pageSize", pageSize);
        viewModel.put("rowCount",out.getLongValue("counts"));

        return view("auth/query_taobao_order");
    }

    //信用卡邮箱
    @Mapping("auth/query_email")
    public ModelAndView email(Long open_id,String email) throws Exception{
        PandaEmailCcardModel emailInfo = DbPandaApi.getEmailCcard(open_id, email);
        List<PandaEmailCcardModel> emails = DbPandaApi.getEmailCcards(open_id);
        if (TextUtils.isEmpty(email)){
            if (emails.size()>0){
                email = emails.get(0).email;
            }
        }
        UserExIdentificationModel identification = DbPandaApi.getIdentification(open_id);
        JSONArray report = DbPandaApi.getEmailData(emailInfo);
        viewModel.put("from",DbPandaApi.getAuthFrom(open_id, 7));
        viewModel.put("identification",identification);
        viewModel.put("email",email);
        viewModel.put("emailInfo",emailInfo);
        viewModel.put("emails",emails);
        viewModel.put("report",report);
        viewModel.put("open_id",open_id);
        return view("auth/query_email");
    }

    //信用卡邮箱报告详情
    @Mapping("auth/query_email_report")
    public ModelAndView emailReport(Long open_id,String name,String email) throws Exception{
        name = Base64Utils.decode(name);
        PandaEmailCcardModel emailInfo = DbPandaApi.getEmailCcard(open_id, email);
        JSONObject report = DbPandaApi.getEmailReport(emailInfo, name);
        viewModel.put("report",report);
        return view("auth/query_email_report");
    }

    //信用卡账单详情
    @Mapping("auth/query_email_bill")
    public ModelAndView emailBill(Integer page, Integer pageSize,Long open_id,String email,String bank_id) throws Exception{
        if (page == null) {
            page = 0;
        } else {
            page = page -1;
        }

        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }

        PandaEmailCcardModel emailCcard = DbPandaApi.getEmailCcard(open_id, email);
        JSONObject out = DbPandaApi.doEmailBills(page,pageSize,bank_id, emailCcard.data_all,0);
        viewModel.put("open_id",open_id);
        viewModel.put("email",email);
        viewModel.put("bank_id",bank_id);
        viewModel.put("pageSize", pageSize);
        viewModel.put("rowCount",out.getLongValue("counts"));
        viewModel.put("list",out.getJSONArray("list"));
        return view("auth/query_email_bill");
    }

    //信用卡账单详情-消费
    @Mapping("auth/query_email_bill_expense")
    public ModelAndView emailBillExpense(Integer page, Integer pageSize,Long open_id,String email,String bank_id) throws Exception{
        if (page == null) {
            page = 0;
        } else {
            page = page -1;
        }

        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }

        PandaEmailCcardModel emailCcard = DbPandaApi.getEmailCcard(open_id, email);
        JSONObject out = DbPandaApi.doEmailBills(page,pageSize,bank_id, emailCcard.data_all,1);

        viewModel.put("pageSize", pageSize);
        viewModel.put("rowCount",out.getLongValue("counts"));
        viewModel.put("list",out.getJSONArray("list"));
        return view("auth/query_email_bill_expense");
    }

    //信用卡账单详情-分期
    @Mapping("auth/query_email_bill_installment")
    public ModelAndView emailBillInstallment(Integer page, Integer pageSize,Long open_id,String email,String bank_id) throws Exception{
        if (page == null) {
            page = 0;
        } else {
            page = page -1;
        }

        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }

        PandaEmailCcardModel emailCcard = DbPandaApi.getEmailCcard(open_id, email);
        JSONObject out = DbPandaApi.doEmailBills(page,pageSize,bank_id, emailCcard.data_all,2);
        viewModel.put("pageSize", pageSize);
        viewModel.put("rowCount",out.getLongValue("counts"));
        viewModel.put("list",out.getJSONArray("list"));
        return view("auth/query_email_bill_installment");
    }

    //公积金
    @Mapping("auth/query_fund")
    public ModelAndView fund(Long open_id,String region) throws SQLException{
        PandaAcumFundModel fund = DbPandaApi.getAcumFund(open_id, region);
        List<PandaAcumFundModel> funds = DbPandaApi.getAcumFundRegions(open_id);
        if (TextUtils.isEmpty(region)){
            if (funds.size()>0){
                region = funds.get(0).region;
            }
        }
        viewModel.put("from",DbPandaApi.getAuthFrom(open_id, 8));
        viewModel.put("funds",funds);
        viewModel.put("fund",fund);
        viewModel.put("region",region);
        viewModel.put("open_id",open_id);
        return view("auth/query_fund");
    }

    //公积金缴存记录
    @Mapping("auth/query_fund_payment")
    public ModelAndView fundPayment(Integer page, Integer pageSize,Long open_id,String region) throws Exception{
        if (page == null) {
            page = 0;
        } else {
            page = page -1;
        }

        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }

        PandaAcumFundModel fund = DbPandaApi.getAcumFund(open_id, region);
        JSONObject out = DbPandaApi.getAcumfundPayments(page, pageSize, fund.details_url_payment);
        viewModel.put("pageSize", pageSize);
        viewModel.put("rowCount",out.getLongValue("counts"));
        viewModel.put("list",out.getJSONArray("list"));

        return view("auth/query_fund_payment");
    }

    //公积金还款记录
    @Mapping("auth/query_fund_repay")
    public ModelAndView fundRepay(Integer page, Integer pageSize,Long open_id,String region) throws Exception{
        if (page == null) {
            page = 0;
        } else {
            page = page -1;
        }

        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }

        PandaAcumFundModel fund = DbPandaApi.getAcumFund(open_id, region);
        JSONObject out = DbPandaApi.getAcumfundPayments(page, pageSize, fund.details_url_repay);
        viewModel.put("pageSize", pageSize);
        viewModel.put("rowCount",out.getLongValue("counts"));
        viewModel.put("list",out.getJSONArray("list"));

        return view("auth/query_fund_repay");
    }

    //网银
    @Mapping("auth/query_bank")
    public ModelAndView bank(Long open_id,String bank_name) throws Exception{
        List<PandaCyberBankTotalModel> banks = DbPandaApi.getCyberBankTotals(open_id);
        if (TextUtils.isEmpty(bank_name)){
            if (banks.size()>0){
                bank_name = banks.get(0).bank_name;
            }
        }
        PandaCyberBankTotalModel bank = DbPandaApi.getCyberBankTotal(open_id, bank_name);
        JSONObject report = DbPandaApi.doCyberBankReport(bank.details_url_report);
        JSONObject bills = DbPandaApi.doCyberBankInfo(bank.details_url_bill);
        viewModel.put("from",DbPandaApi.getAuthFrom(open_id, 9));
        viewModel.put("bank_name",bank_name);
        viewModel.put("banks",banks);
        viewModel.put("report",report);
        viewModel.put("bills",bills);
        viewModel.put("open_id",open_id);
        return view("auth/query_bank");
    }

    //网银借记卡报告详情
    @Mapping("auth/query_bank_dcard_report")
    public ModelAndView bankDcardReport(Long open_id,String bank_name) throws Exception{
        PandaCyberBankTotalModel bank = DbPandaApi.getCyberBankTotal(open_id, bank_name);
        JSONObject report = DbPandaApi.getCyberBankReportDetail(bank.details_url_report, 0);
        viewModel.put("report",report);
        return view("auth/query_bank_dcard_report");
    }

    //网银信用卡报告详情
    @Mapping("auth/query_bank_ccard_report")
    public ModelAndView bankCcardReport(Long open_id,String bank_name) throws Exception{
        PandaCyberBankTotalModel bank = DbPandaApi.getCyberBankTotal(open_id, bank_name);
        JSONObject report = DbPandaApi.getCyberBankReportDetail(bank.details_url_report, 1);
        viewModel.put("report",report);
        return view("auth/query_bank_ccard_report");
    }

    //网银借记卡交易记录
    @Mapping("auth/query_bank_dcard_shop")
    public ModelAndView dcardShop(Long open_id,String bank_name,String card_num) throws Exception{
        PandaCyberBankTotalModel bank = DbPandaApi.getCyberBankTotal(open_id, bank_name);
        JSONArray shops = DbPandaApi.getCyberBankDcardShops(bank.details_url_bill, bank_name);
        viewModel.put("shops",shops);
        viewModel.put("bank_name",bank_name);
        viewModel.put("card_num",card_num);
        return view("auth/query_bank_dcard_shop");
    }

    //网银借记卡交易记录
    @Mapping("auth/query_bank_ccard_bill")
    public ModelAndView ccardBills(Long open_id,String bank_name,String card_num) throws Exception{
        PandaCyberBankTotalModel bank = DbPandaApi.getCyberBankTotal(open_id, bank_name);
        JSONObject list = DbPandaApi.getCyberBankCcardBills(bank.details_url_bill, bank_name);
        viewModel.put("list",list);
        viewModel.put("bank_name",bank_name);
        viewModel.put("card_num",card_num);
        viewModel.put("total",bank);
        return view("auth/query_bank_ccard_bill");
    }


    //银行卡列表
    @Mapping("auth/query_card_list")
    public ModelAndView bankList(Long open_id,String mobile,Integer ugroup_id) throws Exception{
        boolean is_hide = BcfClient.hasResourceByUser(Session.current().getPUID(), "panda_user_content");
        if (is_hide) {
            //关键字是否脱敏权限
            viewModel.put("is_hide",0);
        } else {
            viewModel.put("is_hide",1);
        }
        List<UserExDcardModel> dcardList = DbPandaApi.getDcardList(open_id);
        List<UserExCcardModel> ccardList = DbPandaApi.getCcardList(open_id);
        viewModel.put("dcardList",dcardList);
        viewModel.put("ccardList",ccardList);

        String url = "http://admin.mustang.zmapi.cn";
        if (Config.is_debug)
            url = "http://admin.mustang.dev.zmapi.cn";
        viewModel.put("url",url+"/order/detail2?mobile="+mobile+"&ugroup_id="+ugroup_id);

        return view("auth/query_card_list");
    }

    //收乎用户信息
    @Mapping("auth/query_shouhu")
    public ModelAndView shouhu(String mobile,Integer ugroup_id,Long open_id){
        String url = "http://admin.mustang.zmapi.cn";
        if (Config.is_debug)
            url = "http://admin.mustang.dev.zmapi.cn";
        viewModel.put("url",url+"/user/detail2?mobile="+mobile+"&ugroup_id="+ugroup_id);
        viewModel.put("open_id",open_id);
        return view("auth/query_shouhu");
    }

    //卡宝用户信息
    @Mapping("auth/query_kabao")
    public ModelAndView kabao(String mobile,Integer ugroup_id,Long open_id){
        String url = "http://admin.hold.zmapi.cn";
        if (Config.is_debug)
            url = "http://admin.hold.dev.zmapi.cn";
        viewModel.put("url",url+"/external/userInfo?mobile="+mobile+"&ugroup_id="+ugroup_id);
        viewModel.put("open_id",open_id);
        return view("auth/query_kabao");
    }

    //借贷信息
    @Mapping("auth/query_loan")
    public ModelAndView loan(String mobile,Integer ugroup_id){
        String url = "http://admin.hold.zmapi.cn";
        if (Config.is_debug)
            url = "http://admin.hold.dev.zmapi.cn";
        viewModel.put("url",url+"/external/userDebitClick?mobile="+mobile+"&ugroup_id="+ugroup_id);
        return view("auth/query_loan");
    }

    //gps信息 frame页
    @Mapping("auth/query_gps")
    public ModelAndView gps(Long open_id,Integer agroup_id) throws SQLException{
        List<LogUserOperationModel> list = DbPandaApi.getLogOperations(open_id, agroup_id);
        viewModel.put("list",list);
        return view("auth/query_gps");
    }

}
