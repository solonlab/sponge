package org.noear.sponge.admin.dso;

import com.alibaba.fastjson.JSONObject;
import org.noear.water.utils.HttpUtils;
import org.noear.sponge.admin.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:Fei.chu
 * @Date:Created in 13:40 2018/2/27
 * @Description:同盾api调用工具类
 */
public class TongDunApi {

    //505d3d0e9cb34e15ae4a07271635678c  正式环境key
    //f1f69a3e1ab04f50b129a2c5a8983308  测试环境key
//    static String reportIdUrl= "https://api.tongdun.cn/preloan/apply/v5?partner_code=kalai&partner_key=505d3d0e9cb34e15ae4a07271635678c&app_name=kalai_web";
//    static String reportUrl = "https://api.tongdun.cn/preloan/report/v9?partner_code=kalai&partner_key=505d3d0e9cb34e15ae4a07271635678c&app_name=kalai_web&report_id=";

    static String reportIdUrl= Config.cfg("tongdun").value;
    static String reportUrl = Config.cfg("tongdun_report").value;

    static String reportIdUrl1= Config.cfg("tongdun1").value;
    static String reportUrl1 = Config.cfg("tongdun_report1").value;

    //获取同盾报告
    public static String submit(String reportID,int type) throws Exception{
        //后续有新规则在此处增加分支
        System.out.println("---");
        String report = null;
        if (type == 0) {
            report = HttpUtils.getString(reportUrl + reportID);
        } else {
            report = HttpUtils.getString(reportUrl1 + reportID);
        }

        JSONObject jsonObject= com.alibaba.fastjson.JSON.parseObject(report);
        if((boolean)jsonObject.get("success"))
        {
            return jsonObject.toJSONString();
        }else{
            return jsonObject.get("reason_desc")+"";
        }

    }

    //获取同盾报告id
    public static String getReportId(Map<String,String> param, int type) throws Exception {
        //后续有新规则在此处增加分支
        String getReport = null;
        if (type == 0) {
            getReport =  HttpUtils.postString(reportIdUrl,param);
        } else {
            getReport =  HttpUtils.postString(reportIdUrl1,param);
        }
        JSONObject jsonObject= com.alibaba.fastjson.JSON.parseObject(getReport);
        if((boolean)jsonObject.get("success"))
        {
            return jsonObject.get("report_id")+"";
        }else{
            return jsonObject.get("reason_desc")+"";
        }
    }

    //根据传入身份信息获取同盾报告
    public static String getReport(HashMap<String,String> param,int type,long user_id) throws Exception{
        String result = submit(getReportId(param,type),type);
        return result;
    }

    //根据传入身份信息获取同盾分
    public static String getTongDunScore(HashMap<String,String> param,int type) throws Exception{
        JSONObject jsonObject= com.alibaba.fastjson.JSON.parseObject(submit(getReportId(param,type),type));
        if((boolean)jsonObject.get("success"))
        {
            return jsonObject.get("final_score") + "";
        }else{

            return jsonObject.get("reason_desc")+"";
        }
    }

    /*public static void main(String[] args) throws Exception{
        HashMap<String,Object> param = new HashMap<>();
        param.put("name","xxx");
        param.put("id_number","340822xxxx1610");
        param.put("mobile","xxxx");
        param.put("card_number","xxxx");
        System.out.println(getReportId(param,TDRoleEnum.TWO.getCode()));
        System.out.println(submit(getReportId(param,TDRoleEnum.TWO.getCode()),TDRoleEnum.TWO.getCode()));
    }*/
}
