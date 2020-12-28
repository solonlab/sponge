package zm.data.dobbin.bull.controller.callback;

import org.noear.snack.ONode;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.water.utils.EncryptUtils;
import zm.data.dobbin.bull.dso.Logger;
import zm.data.dobbin.bull.models.BullOrderModel;
import zm.data.dobbin.bull.models.BullOrderStatusEnum;
import zm.data.dobbin.bull.models.CoProductModel;

import java.util.LinkedHashMap;
import java.util.Map;


@Controller
@Mapping("/CB/")
public class StandardOrderController extends BaseController {

    @Mapping("C.B.0.1")
    public Map<String, Object> receiveOrderStatus(Context ctx) throws Exception {
        Map<String,Object> result = new LinkedHashMap<>();

        String p = ctx.param("p");

        String k = ctx.param("k");

        Logger.logPartyOutput("callback", "raw", "", p, k);

        String cmd = ctx.path().replace("/CB/", "");

        String[] kArray = k.split("\\.");

        String cId = kArray[0];
        String sign = kArray[2];

        CoProductModel cpm = coProductService.get_co_product(Integer.valueOf(cId));

        if (cpm.product_id <= 0) {
            result.put("code", 2);
            result.put("msg", "Loan Master没有该产品");
            return result;
        }

        String encryptData = EncryptUtils.aesDecrypt(p, cpm.bull_product_key, null);

        Logger.logPartyOutput("callback", "encrypt", cId, k, encryptData);

        String s = buildSign(cmd, encryptData, cpm.bull_product_key);

        if (!s.equals(sign)) {
            // 验签不通过
            Logger.logPartyOutput("callback", "sign", cId, "req = " + sign, "lm = " + s);
            result.put("code", 3);
            result.put("msg", "验签失败");
            return result;
        }

        ONode params = ONode.load(encryptData);

        String lmNo = params.get("lmNo").getString();
        int status = params.get("status").getInt();
        int amount = params.get("amount").getInt();
        int term = params.get("term").getInt();
        String overDate = params.get("overDate").getString();
        String repayDate = params.get("repayDate").getString();
        String link = params.get("link").getString();


        // 只用于获取用户ID
        BullOrderModel tmp = bullOrderService.get_bull_order_by_order_no(lmNo);


        // 获取用户
        BullOrderModel bom = bullOrderService.get_bull_order(tmp.user_id, tmp.p_id);

        if (bom.order_id <= 0) {
            result.put("code", 4);
            result.put("msg", "Loan Master没有该订单号");
            return result;
        }

        // 如果订单号不一致说明是旧订单，回调过来的订单状态小于表中的状态 说明消息已经过期
        if (!bom.order_no.equals(lmNo) || status < bom.status) {
            result.put("code", 1);
            result.put("msg", "消息重复");
            return result;
        }

        // 被拒订单
        if (status == BullOrderStatusEnum.REFUSE.type()) {
            bullOrderService.set_bull_order_auth_status(status, tmp.user_id, tmp.p_id);
        }

        // 放款状态
        if (status == BullOrderStatusEnum.PUT_LOAN.type()) {
            bullOrderService.set_put_loan_bull_order(status, amount, term, tmp.user_id, tmp.p_id);
        }

        // 逾期
        if (status == BullOrderStatusEnum.PUT_LOAN.type()) {
            bullOrderService.set_over_due_bull_order(status, dateStringToInt(overDate), tmp.user_id, tmp.p_id);
        }

        // 完结
        if (status == BullOrderStatusEnum.ORDER_FINISH.type()) {
            bullOrderService.set_finish_bull_order(status, dateStringToInt(repayDate), tmp.user_id, tmp.p_id);
        }

        result.put("code", 1);
        result.put("msg", "成功");

        return result;
    }

    public int dateStringToInt(String date) {
        return Integer.valueOf(date.replace("-", ""));
    }


    private String buildSign(String cmd, String params, String productKey) {

        StringBuilder sb = new StringBuilder();

        sb.append(cmd).append("#").append(params).append("#").append(productKey);

        return EncryptUtils.sha256(sb.toString());
    }

}
