package zm.data.dobbin.bull.dso;

import org.noear.sponge.rock.RockUtil;
import org.noear.water.utils.Datetime;
import org.noear.water.utils.IDUtils;
import zm.data.dobbin.bull.Config;

import java.util.Date;
import java.util.Random;

public class IDBuilder {

    /**
     * ID生成工具
     * @param tag 存储在redis中的键
     * @param start ID起始值
     * @return
     */
    private static long buildID(String tag, long start) {
        return IDUtils.newID(Config.water_config_tag, tag) + start;
    }

    /**
     * 生成唯一值
     * @return 生成唯一值
     */
    public static String buildGuid() {
        return RockUtil.buildGuid();
    }

    /**
     * 订单ID
     * @return 订单ID
     */
    public static long buildBullOrderID() {
        return buildID("bull_order_id", 10000);
    }



    public static String buildBullOrderNO(long orderId) {
        String time = Datetime.format(new Date(), "yyyyMMddHHmm");
        Random rand =new Random(3);
        int a = rand.nextInt(10);
        int b = rand.nextInt(10);
        int c = rand.nextInt(10);
        int d = rand.nextInt(10);
        return time + "" + a + "" + orderId + "" + b + "" + c + "" + d;
    }
}
