package apidemo.controller.cmds;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.extend.validation.annotation.NotNull;
import org.noear.water.utils.Datetime;
import apidemo.controller.SysCodes;
import apidemo.controller.UapiBase;
import apidemo.dso.db.BullOrderService;
import apidemo.dso.db.CoProductService;
import apidemo.models.BullOrderModel;
import apidemo.models.BullOrderStatusEnum;
import apidemo.models.CoProductModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


@Component(tag = "cmd")
public class CMD_A_A_0_4 extends UapiBase {

    @Inject
    BullOrderService bullOrderService;

    @Inject
    CoProductService coProductService;

    @NotNull({"type", "next", "size"})
    @Mapping("A.A.0.4")
    public Map<String, Object> exec(int type, long next, int size) throws Exception {

        // 判断用户是否登录
        if (!isLogin()) {
            throw SysCodes.CODE_102;
        }

        List<BullOrderModel> orders = bullOrderService.list_bull_order(getUserID(), type, next, size);

        ArrayList<Map<String, Object>> list = new ArrayList<>();

        if (orders.size() == 0) {
            data.put("next", next);
            data.put("list", new ArrayList<>());
            return data;
        }

        List<CoProductModel> products = coProductService.list_co_product();

        for (int i = 0; i < orders.size(); i++) {

            BullOrderModel bom = orders.get(i);
            HashMap<String, Object> map = new HashMap<>();

            String dString = intToDateString(bom.over_date);


            map.put("pId", bom.p_id);
            map.put("overDate", dString);

            if (!dString.equals("")) {
                Date date = Datetime.tryParse(dString, "yyyy-MM-dd").getFulltime();
                map.put("overDue", betweenDays(date));
            } else {
                map.put("overDue", 0);
            }

            map.put("repayDate", intToDateString(bom.repay_date));
            map.put("link", "");

            if (bom.status == BullOrderStatusEnum.PUT_LOAN.type() ||
                    bom.status == BullOrderStatusEnum.OVER_DAY.type() ||
                    bom.status == BullOrderStatusEnum.ORDER_FINISH.type()) {

                map.put("amount", bom.r_amount / 100);
                map.put("term", bom.r_term + "days");

            } else {
                map.put("amount", bom.l_amount / 100);
                map.put("term", bom.l_term + "days");
            }

            if (bom.status == BullOrderStatusEnum.REFUSE.type()) {
                // 被拒跳转链接
                map.put("link", "");
            }

            if (bom.status == BullOrderStatusEnum.ORDER_FINISH.type()) {
                map.put("link", "");
            }

            for (int j = 0; j < products.size(); j++) {
                CoProductModel cpm = products.get(j);

                if (bom.p_id == cpm.product_id) {

                    if (bom.status == BullOrderStatusEnum.BASIC_SUCCESS.type()) {
                        map.put("link", cpm.link_url);
                    }

                    map.put("logo", cpm.logo);
                    map.put("name", cpm.product_name);
                    break;
                }
            }
            list.add(map);
        }

        data.put("list", list);

        int ordersSize = orders.size();

        data.put("next", orders.get(ordersSize - 1).order_id);

        return data;
    }


    /**
     * yyyyMMdd转 yyyy-MM-dd
     * @param dateInt
     * @return
     */
    public static String intToDateString(int dateInt) {

        StringBuffer dateSb =new StringBuffer(dateInt);

        if (dateSb.length() > 7) {

            dateSb.insert(6,"-");
            dateSb.insert(4,"-");

            return dateSb.toString();

        } else {
            return "";
        }
    }


    /**
     *
     * @param date
     * @return
     */
    public static int betweenDays(Date date) {
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return (int) (LocalDate.now().toEpochDay() - ldt.toLocalDate().toEpochDay());
    }
}
