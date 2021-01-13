package apidemo.controller.cmds;

import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.extend.validation.annotation.NotZero;
import org.noear.water.utils.LockUtils;
import apidemo.Config;
import apidemo.controller.SysCodes;
import apidemo.controller.UapiBase;
import apidemo.dso.LockKeyBuilder;
import apidemo.dso.RedisUtil;
import apidemo.dso.StandardApi;
import apidemo.dso.db.BullOrderService;
import apidemo.dso.db.CoProductService;
import apidemo.models.*;

import java.util.Map;

@Component(tag = "cmd")
public class CMD_A_A_0_2 extends UapiBase {

    @Inject
    BullOrderService bullOrderService;

    @Inject
    CoProductService coProductService;

    @Inject
    StandardApi standardApi;

    @NotZero({"pId"})
    @Mapping("A.A.0.2")
    public Map<String, Object> exec(long pId) throws Exception {

        // 判断用户是否登录
        if (!isLogin()) {
            throw SysCodes.CODE_102;
        }

        int status = 0;
        int disabled = 0;
        String link = "";

        CoProductModel cpm = coProductService.get_co_product(pId);

        // 检测产品是否存在
        if (cpm.product_id <= 0) {
            throw SysCodes.CODE_1000;
        }

        // 查询用户是否存在该产品订单
        BullOrderModel addBom = bullOrderService.get_bull_order(getUserID(), pId);

        // 如果不存在订单，或者订单已经完结，新生成软件层面订单
        if (addBom.order_id <= 0 || addBom.status == BullOrderStatusEnum.ORDER_FINISH.type()
                || addBom.status == BullOrderStatusEnum.REFUSE.type()) {

            // 对该用户上锁
            if (!LockUtils.tryLock(Config.group_name, LockKeyBuilder.buildUserBullOrder(getUser().user_id))) {
                throw SysCodes.CODE_212;
            }

            bullOrderService.addBullOrder(getUser().agroup_id,
                    getUser().ugroup_id,
                    getUser().app_id,
                    pId, getUserID(),
                    getUser().mobile);

            // 解锁用户
            LockUtils.unLock(Config.group_name, LockKeyBuilder.buildUserBullOrder(getUser().user_id));
        }

        // 是否存在进行中的订单
        BullOrderModel bom = bullOrderService.get_bull_order(getUserID(), pId);

        if (bom.status > BullOrderStatusEnum.SUBMIT_SUCCESS.type()) {
            ONode oNode = standardApi.getOrderDetail(getUserID(), bom.out_no);
//            // 回调如果失败修正数据
//            if (oNode.get("status").equals(bom.status)) {
                status = bom.status;
//            } else {
//                status = oNode.get("status").getInt();
//            }
            if (bom.status == BullOrderStatusEnum.PUT_LOAN.type()) {
                link = oNode.get("link").getString();
            }

        } else {

            // todo 找用户认证表的状态 如果ID在其他产品认证过
            UserValidateModel uvm = userService.get_user_validate(getUserID());
            if (uvm.id_status == 2 && bom.status == BullOrderStatusEnum.USER_CLICKED.type()) {
                status = 89;
            } else {
                status = bom.status;
            }
        }

        // 进件关闭0 1开启
        if (cpm.bull_submit_state == 0) {
            disabled = 1;
        }

        // 进件数量超线
        if (cpm.bull_submit_max < RedisUtil.getSubmitStock(pId)) {
            disabled = 1;
        }

        data.put("disabled", disabled);
        data.put("status", status);
        data.put("link", link);

        return data;
    }
}
