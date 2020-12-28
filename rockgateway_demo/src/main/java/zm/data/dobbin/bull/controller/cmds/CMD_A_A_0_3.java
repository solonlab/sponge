package zm.data.dobbin.bull.controller.cmds;

import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.extend.validation.annotation.NotNull;
import zm.data.dobbin.bull.controller.SysCode;
import zm.data.dobbin.bull.controller.UapiBase;
import zm.data.dobbin.bull.dso.RedisUtil;
import zm.data.dobbin.bull.dso.StandardApi;
import zm.data.dobbin.bull.dso.db.BullOrderService;
import zm.data.dobbin.bull.dso.db.CoProductService;
import zm.data.dobbin.bull.models.*;

import java.util.Map;

@Component(tag = "cmd")
public class CMD_A_A_0_3 extends UapiBase {

    @Inject
    BullOrderService bullOrderService;

    @Inject
    CoProductService coProductService;

    @Inject
    StandardApi standardApi;


    @NotNull({"pId"})
    @Mapping("A.A.0.3")
    public Map<String,Object> exec(int pId) throws Exception {

        // 判断用户是否登录
        if (!isLogin()) {
            throw SysCode.CODE_102;
        }

        // 先判断产品是否可用
        CoProductModel cpm = coProductService.get_co_product(pId);

        if (cpm.product_id <= 0) {
            throw SysCode.CODE_1000;
        }

        // 进件关闭0 开启1
        if (cpm.bull_submit_state == 0) {
            throw SysCode.CODE_1001;
        }

        // 判断进件数量是否有已满
        int submitTotal = RedisUtil.getSubmitStock(pId);

        if (submitTotal > cpm.bull_submit_max) {
            throw SysCode.CODE_1001;
        }

        BullOrderModel bom = bullOrderService.get_bull_order(getUserID(), pId);

        // 如果当前订单状态不是银行卡认证成功，则不能进行进件
        if (bom.status != BullOrderStatusEnum.BANK_SUCCESS.type()) {
            throw SysCode.CODE_1002;
        }

        UserValidateModel uvm = userService.get_user_validate(getUserID());

        // 把数据传输给合作方
        ONode oNode = standardApi.submitUserInfo(getUserID(),
                bom.order_no,
                getUser().mobile,
                ONode.load(uvm.validate_info));

        if (oNode.get("code").getInt() != 1) {
            throw SysCode.CODE_1003;
        }

        // 修改订单状态
        bullOrderService.set_submit_success_bull_order(getUserID(), pId, oNode.get("data").get("outNo").getString());

        // 增加进件数量
        RedisUtil.increaseProductStock(pId, 1);

        // 记录当前进件的数据
        bullOrderService.add_bull_order_validate(bom.order_id,
                getUserID(),
                getUser().app_id,
                pId,
                uvm.validate_info,
                uvm.validate_info);

        return data;
    }
}
