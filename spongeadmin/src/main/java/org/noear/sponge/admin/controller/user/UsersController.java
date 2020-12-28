package org.noear.sponge.admin.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.noear.bcf.BcfClient;
import org.noear.snack.ONode;

import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Controller;
import org.noear.solon.core.handle.MethodType;
import org.noear.solon.core.handle.ModelAndView;

import org.noear.sponge.admin.controller.ViewModel;
import org.noear.sponge.admin.dso.MessageUtil;
import org.noear.sponge.admin.dso.Session;
import org.noear.sponge.admin.dso.SwichUtil;
import org.noear.sponge.admin.dso.db.DbUserApi;
import org.noear.sponge.admin.models.others.CountModel;
import org.noear.sponge.admin.models.others.resp.CallLogResp;
import org.noear.sponge.admin.models.others.resp.TDReportResp;
import org.noear.sponge.admin.models.others.resp.ZmWatchListResp;
import org.noear.sponge.admin.models.sponge_angel.*;
import org.noear.sponge.admin.utils.OssUtil;
import org.noear.water.WaterProxy;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.models.others.resp.ZmAntifraudRisklistResp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Controller
public class UsersController extends BaseController {

    //用户列表获取
    @Mapping(value = "user/query", method = MethodType.GET)
    public ModelAndView userList(Integer page, Integer pageSize, String mobile,Integer _state) throws Exception {

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
        } else {
            _state = 0;
        }
        CountModel count = new CountModel();
        List<UserStateModel> userList = DbUserApi.getUserStateList(page, pageSize, count, mobile,_state);

        boolean is_hide = BcfClient.hasResourceByUser(Session.current().getPUID(), "sponge_user_content");
        if (is_hide) {
            //关键字是否脱敏权限
            viewModel.put("is_hide",0);
        } else {
            viewModel.put("is_hide",1);
        }

        viewModel.put("pageSize", pageSize);
        viewModel.put("rowCount", count.getCount());
        viewModel.put("userList", userList);
        viewModel.put("mobile", mobile);
        return view("user/query");
    }

    //身份证识别页面信息
    @Mapping("user/query_identification")
    public ModelAndView getCardByOpenId(Integer open_id) throws SQLException {
        boolean is_hide = BcfClient.hasResourceByUser(Session.current().getPUID(), "sponge_user_content");
        if (is_hide) {
            //关键字是否脱敏权限
            viewModel.put("is_hide",0);
        } else {
            viewModel.put("is_hide",1);
        }

        UserExIdentificationModel user = DbUserApi.getCardByOpenId(open_id);
        viewModel.put("user", user);
        return view("user/query_identification");
    }

    //运营商认证页面
    @Mapping("user/query_operator")
    public ModelAndView getOperatorInfo(Integer open_id) throws Exception {
        boolean is_hide = BcfClient.hasResourceByUser(Session.current().getPUID(), "sponge_user_content");
        if (is_hide) {
            //关键字是否脱敏权限
            viewModel.put("is_hide",0);
        } else {
            viewModel.put("is_hide",1);
        }

        //基本细信息
        AngelOperatorBaseModel operatorBase = DbUserApi.getOperatorBase(open_id);
        //征信
        AngelOperatorCreditModel operatorCredit = DbUserApi.getOperatorCredit(open_id);
        //用户运营商汇总信息
        AngelOperatorTotalModel operatorTotal = DbUserApi.getOperatorTotal(open_id);
        //用户联系人信息
        UserExLinkmanModel userLinkMan = DbUserApi.getUserLink(open_id);

        if (operatorTotal.open_id > 0) {
            //通话账单。
            try {
                String ossData_url_bill = OssUtil.getOssObject(operatorTotal.details_url_bill, 0);
                List<AngelOperatorBill> operator_bill = new ArrayList<>();
                if (operatorTotal.type==0){
                    //闪银
                    operator_bill = JSONArray.parseArray(ossData_url_bill, AngelOperatorBill.class);
                } else {
                    //同盾/魔蝎运营商
                    operator_bill = DbUserApi.doTdOperatorBill(ossData_url_bill,operatorTotal.type);
                }
                viewModel.put("operator_bill", operator_bill);
            } catch (Exception ex) {
                ArrayList<AngelOperatorBill> operator_bill = new ArrayList<>();
                viewModel.put("operator_bill", operator_bill);
            }
        }
        viewModel.put("userLinkMan", userLinkMan);
        viewModel.put("operatorBase", operatorBase);
        viewModel.put("operatorCredit", operatorCredit);
        viewModel.put("open_id", open_id);

        return view("user/query_operator");
    }

    //运营商通话记录
    @Mapping("user/query_operator/query_operator_inner")
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

        AngelOperatorTotalModel operatorTotal = DbUserApi.getOperatorTotal(open_id);
        UserExContactListModel contacts = DbUserApi.getContactList(open_id);
        CallLogResp calllogs = DbUserApi.dealCallLog(page,pageSize,mobile,operatorTotal, contacts.details_url,is_hide,operatorTotal.type);
        viewModel.put("calllogs", calllogs.details);
        viewModel.put("pageSize", pageSize);
        viewModel.put("rowCount",calllogs.count);
        viewModel.put("open_id", open_id);
        viewModel.put("mobile", mobile);
        return view("user/query_operator_inner");

    }

    @Mapping("user/query_baseInfo")
    public ModelAndView userBaseInfo(Integer open_id) throws SQLException {
        boolean is_hide = BcfClient.hasResourceByUser(Session.current().getPUID(), "sponge_user_content");
        if (is_hide) {
            //关键字是否脱敏权限
            viewModel.put("is_hide",0);
        } else {
            viewModel.put("is_hide",1);
        }

        UserModel userRegister = DbUserApi.getUserRegisterInfo(open_id);
        UserExBaseInfoModel baseInfo = DbUserApi.getBaseInfo(open_id);
        UserExWorkInfoModel workInfo = DbUserApi.getWorkInfo(open_id);

        String qualification = "";
        String mstatus = "";
        String house_status = "";
        String house_type = "";
        if (baseInfo.open_id > 0) {
            //学历
            qualification = SwichUtil.getQualification(baseInfo.qualification);
            //婚姻情况
            mstatus = SwichUtil.getMstatus(baseInfo.mstatus);
            //房产状况
            house_status = SwichUtil.getHouseStatus(baseInfo.house_status);
            //房产类型
            house_type = SwichUtil.getHouseType(baseInfo.house_type);
        }
        String career = "";
        String career_time = "";
        String salary = "";
        String co_industry = "";
        String conature = "";
        String position = "";
        if (workInfo.open_id > 0) {
            //职业类型
            career = SwichUtil.getCareer(workInfo.career);
            //工作时间
            career_time = SwichUtil.getCareer_time(workInfo.career_time);
            //年收入
            salary = SwichUtil.getSalary(workInfo.salary);
            //公司行业
            co_industry = SwichUtil.getCo_industry(workInfo.co_industry);
            //公司性质
            conature = SwichUtil.getConature(workInfo.conature);
            //职位
            position = SwichUtil.getPosition(workInfo.position);
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

        viewModel.put("userRegister",userRegister);
        viewModel.put("baseInfo", baseInfo);
        viewModel.put("workInfo", workInfo);
        return view("user/query_baseInfo");
    }

    //邦盛详情页
    @Mapping("user/query_bs")
    public ModelAndView userBsInfo(Long open_id) throws Exception {
        JSONObject resp = DbUserApi.getBSDetail(open_id);
        viewModel.put("risks",resp);
        return view("user/query_bs");
    }


    @Mapping("user/query_credit")
    public ModelAndView queryZm(Long open_id) throws Exception{
        //芝麻分
        AngelZmScoreModel zmscore = DbUserApi.getUserZmScore(open_id);
        viewModel.put("zmScore",zmscore.zmscore);
        //芝麻反欺诈分
        AngelZmAntifraudScoreModel antifraudScore = DbUserApi.getUserZmAntifraudScore(open_id);
        viewModel.put("zmAntifraudScore",antifraudScore.score);

        //芝麻风险项
        AngelZmAntifraudRisklistModel riskliste = DbUserApi.getUserZmAntifraudRiskliste(open_id);
        List<ZmAntifraudRisklistResp> zmrisk = DbUserApi.dealZmAntifraudRisk(riskliste.risk_code);
        viewModel.put("antifraudRisks",zmrisk);

        //芝麻企业观察名单
        AngelZmWatchlistModel zmWatchList = DbUserApi.getZmWatchList(open_id);
        ONode details = ONode.load(zmWatchList.details);
        System.out.println(details.toJson());
        List<ZmWatchListResp> watchList = DbUserApi.dealZmWatchList(details);
        viewModel.put("watchList", watchList);

        //同盾信息
        AngelRiskTdModel td = DbUserApi.getTongdun(open_id);
        TDReportResp tdResp = DbUserApi.dealTDRisk(td);
        viewModel.put("tdReport", tdResp);

        JSONObject resp = DbUserApi.getBSDetail(open_id);
        viewModel.put("risks_bs",resp);

        JSONObject rubberResp = DbUserApi.getRubberDetail(open_id);
        viewModel.put("risks_rubber",rubberResp);
        viewModel.put("rubber",new AngelRiskRubberModel());

        JSONArray device_log = DbUserApi.getDeviceLog(open_id);
        viewModel.put("device_log",device_log);
        return view("user/query_credit");
    }

    //交易记录
    @Mapping("user/query_trade")
    public ModelAndView queryTrade(Long open_id) throws Exception{
        HashMap<String, Object> param = new HashMap<>();
        param.put("mobile",DbUserApi.getUser(open_id).mobile);
        String hold = WaterProxy.paas("hold/user.order.list.get", param);
        String beauty = WaterProxy.paas("beauty/user.order.list.get", param);
        String beast = WaterProxy.paas("beast/user.iou.fact.list.get", param);
        ONode holdJson = ONode.load(hold);
        ONode beautyJson = ONode.load(beauty);
        ONode beastJson = ONode.load(beast);
        if (holdJson.get("code").getInt() == 1) {
            JSONArray data = JSON.parseArray(holdJson.get("data").toJson());
            int length = data.size();
            JSONArray newarr = new JSONArray();
            for (int i = length-1; i >= length - 10 && i >= 0 ; i--) {
                newarr.add(data.get(i));
            }
            viewModel.put("hold",newarr);
        }
        if (beautyJson.get("code").getInt() == 1) {
            JSONArray data = JSON.parseArray(beautyJson.get("data").toJson());
            int length = data.size();
            JSONArray newarr = new JSONArray();
            for (int i = length-1; i >= length - 10 && i >= 0 ; i--) {
                newarr.add(data.get(i));
            }
            viewModel.put("beauty",newarr);
        }
        if (beastJson.get("code").getInt() == 1) {
            JSONArray data = JSON.parseArray(beastJson.get("data").toJson());
            int length = data.size();
            JSONArray newarr = new JSONArray();
            for (int i = length-1; i >= length - 10 && i >= 0 ; i--) {
                newarr.add(data.get(i));
            }
            viewModel.put("beast",newarr);
        }
        return view("user/query_trade");
    }

    //更新同盾分
    @Mapping("user/updateTD")
    public static boolean updateTD(Long open_id) throws Exception{
        boolean result = DbUserApi.updateTD(open_id);
        if (result){
            //更新同盾分，发送消息，业务方自行订阅消息做相关更新操作(同时通知123有钱和钱豹)
            UserModel user = DbUserApi.getUser(open_id);
            MessageUtil.doTongDunUpdate(user.mobile,8,8);
            MessageUtil.doTongDunUpdate(user.mobile,5,5);
        }
        return result;
    }

    //请求橡皮分
    @Mapping("user/reqRubberScore")
    public ViewModel reqRubberScore(Long open_id) throws Exception{

        //同时更新123有钱和钱豹的橡皮分
        String resp1 = DbUserApi.postRubber(open_id, 8, 8);
        String resp = DbUserApi.postRubber(open_id, 5, 5);

        JSONObject json = JSONObject.parseObject(resp);
        JSONObject json1 = JSONObject.parseObject(resp1);
        if (json.getInteger("code") == 1 && json1.getInteger("code") == 1) {
            viewModel.put("code",1);
            viewModel.put("msg","请求成功");
        } else {
            viewModel.put("code",0);
            viewModel.put("msg","请求异常");
        }
        return viewModel;
    }


}
