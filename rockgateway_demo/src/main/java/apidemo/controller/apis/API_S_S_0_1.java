package apidemo.controller.apis;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import apidemo.controller.SysCode;
import apidemo.controller.UapiBase;
import apidemo.dso.Logger;
import apidemo.dso.db.BullOrderService;
import apidemo.dso.db.CoProductService;
import apidemo.models.BullOrderModel;
import apidemo.models.BullOrderStatusEnum;
import apidemo.models.CoProductModel;
import apidemo.models.UserModel;

import java.util.Map;

@Component(tag = "api")
public class API_S_S_0_1 extends UapiBase {

    @Inject
    BullOrderService orderService;

    @Inject
    CoProductService coProductService;

//    @NotNull({"userId, type, pId"})
    @Mapping("S.S.0.1")
    public Map<String, Object> exec(long userId, long pId, int type) throws Exception {

        // 90 Identification 认证完成 100 Basic info认证完成
        // 110 Supplementary info认证完成 120 Bank card info认证完成

        UserModel um = userService.get_user_by_user_id(userId);

        // 用户是否存在
        if (um.user_id <= 0) {
            throw SysCode.CODE_102;
        }

        CoProductModel cpm = coProductService.get_co_product(pId);

        // 产品是否存在
        if (cpm.product_id <= 0){
            throw SysCode.CODE_1000;
        }

        BullOrderModel bom = orderService.get_bull_order(userId, pId);

        // 如果请求的状态小于数据库中的状态，不能进行更新。
        if (type < bom.status) {
            Logger.logOutput("S.S.0.1", userId + "", pId + "", type + "",
                    "orderId = " + bom.order_id + ",当前 status = " + bom.status);
            return data;
        }


        if (type == BullOrderStatusEnum.BASIC_SUCCESS.type()) {
            // 产品层面的订单生成
            orderService.set_pm_bull_order(userId, pId);

        } else if (type == BullOrderStatusEnum.BANK_SUCCESS.type()) {

            orderService.set_auth_finish_bull_order(type, userId, pId);

        } else {

            orderService.set_bull_order_auth_status(type, userId, pId);
        }

        return data;
    }

}
