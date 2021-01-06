import org.junit.Test;
import org.noear.sponge.admin.models.others.resp.CallLogDetailsResp;
import org.noear.sponge.admin.utils.StringUtil;


import java.util.*;

/**
 * @Author:Fei.chu
 * @Description: 单元测试
 */
public class TestApi {
    public static void main(String[] args) throws Exception {
        boolean mobile = StringUtil.isMobile("15183854646");
        System.out.println(mobile);
    }

    @Test
    public void test1() throws Exception{
        //String rsp = HttpUtil.getString(Config.raas_uri + "/preview.js?scheme=hold.push/user.query&type=11&limit=1000");
        String rsp = "SELECT m.`id_birthday` as  `id_birthday`,m.`state` AS  `state`,m.`user_id` as  `user_id` from rock_user.`user_real` AS m WHERE 1=1 AND (m.state=1) AND (m.id_birthday<=20000101) LIMIT 10";

        rsp = rsp.replaceAll("(?u) as "," AS ").replaceAll("(?u) from "," FROM ");
        String[] asArr = rsp.split("AS");
        String db = "";
        String table = "";
        for (String sql:asArr) {
            if (sql.contains("FROM")){
                int fIndex = sql.indexOf("FROM");
                int tIndex = sql.indexOf(".");
                db = sql.substring(fIndex+4,tIndex).trim();
                table = sql.substring(tIndex+1,sql.length()-1).replaceAll("`","").trim();
                break;
            }
        }

    }


    @Test
    public void getOss() throws Exception{
        Map<String, CallLogDetailsResp> map = new HashMap<>();
//        CallLogDetailsResp m = new CallLogDetailsResp();
//        m.phone = "15555670661";
//        map.put("15555670661",m);
        CallLogDetailsResp callLogDetailsResp = map.get("15555670661");
        if (callLogDetailsResp==null){
            callLogDetailsResp = new CallLogDetailsResp();
            callLogDetailsResp.phone = "155555";
        }
        System.out.println(callLogDetailsResp.total_count);
    }

}
