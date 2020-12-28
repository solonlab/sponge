package webapp.utils;

import org.noear.snack.ONode;
import org.noear.water.utils.HttpUtils;

/**
 * create at 2018-03-15 by liht
 * 百度API的工具类
 */
public class BaiduUtil {

    public static final String ak = "4pIT2cLsPnfdW9gqAguqluPU1IQLOWoo";

    /**
     * 根据经纬度获取详细地址
     * @param log_lat  经度
     * @param log_lng  维度
     * @return  详情地址
     */
    public static String getAddressByLatAndLng(double log_lat,double log_lng){
        try {
            String location = log_lat + "," + log_lng;
            String text = HttpUtils.getString("http://api.map.baidu.com/geocoder/v2/?location="+location+"&output=json&ak="+ak);
            ONode result = ONode.load(text);
            if(result.get("status").getInt() == 0){
                return result.get("result").get("formatted_address").getString();
            }
        }catch (Exception e){
        }
        return "";
    }
}
