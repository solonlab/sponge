package webapp.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.util.TextUtils;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Controller;
import org.noear.water.utils.HttpUtils;
import webapp.Config;
import webapp.dso.MessageUtil;
import webapp.dso.db.DbUserApi;

import java.util.HashMap;

/**
 * @Author:Fei.chu
 * @Date:Created in 10:36 2018/11/12
 * @Description:异步回调
 */
@Controller
public class NotifyController {

    /**
    *@Author:Fei.chu
    *@Date:14:36 2018/11/12
    *@Description:橡皮分回调处理
    */
    @Mapping("notify/rubber")
    public String rubberNotify(String request_id) throws Exception{

        HashMap<String,Object> param = new HashMap<>();
        param.put("request_id",request_id);
        param.put("scheme","angel/risk");

        String resp = HttpUtils.postString(Config.raas_uri + "/s/angel/risk", param);

        JSONObject result = JSONObject.parseObject(resp);
        Integer code = result.getInteger("code");
        if (code == 1) {

            JSONObject args = result.getJSONObject("request").getJSONObject("args");
            long open_id = args.getLong("open_id");
            long order_id = args.getLong("order_id");
            String mobile = args.getString("mobile");
            int agroup_id = args.getIntValue("agroup_id");

            JSONObject response = result.getJSONObject("response");
            JSONObject evaluation = response.getJSONObject("evaluation");
            Integer score = evaluation.getInteger("score");
            Integer advise = evaluation.getInteger("advise");

            String details_url = DbUserApi.backupsStr(open_id, response.toJSONString());
            if (!TextUtils.isEmpty(details_url)) {
                DbUserApi.addAngelRiskRubber(open_id,agroup_id,order_id,request_id,score,advise,details_url);

                DbUserApi.updateUserStateRubberScore(open_id,score);

                MessageUtil.duRubberUpdate(mobile,agroup_id,agroup_id);
            }

            return "OK";
        } else {
            return "fail";
        }

    }
}
