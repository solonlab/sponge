package zm.data.dobbin.bull.dso;

import org.noear.water.utils.Datetime;
import org.noear.water.utils.RedisX;
import zm.data.dobbin.bull.Config;

import java.util.Date;


public class RedisUtil {

    private static final RedisX redis18 = Config.rd_pepper_18;

    private static final int EXPIRE_IN_1_DAY = 60 * 60 * 24;


    public static int increaseProductStock(long p_id, int quantity) {
        Date now = new Date();
        int nowInt = new Datetime(now).getDate();

        return redis18.open1(r ->
                r.key("BULL_co_product_id").expire(EXPIRE_IN_1_DAY)
                        .hashIncr("p_id_" + nowInt + "_" + p_id, quantity)
        ).intValue();
    }

    /**
     * 获取已经进件的数据
     * @param p_id
     * @return
     */
    public static int getSubmitStock(long p_id) {
        Date now = new Date();
        int nowInt = new Datetime(now).getDate();
        return redis18.open1(r ->
                r.key("BULL_co_product_id")
                        .hashVal("p_id_" + nowInt + "_" + p_id)
        ).intValue();
    }
}
