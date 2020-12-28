package org.noear.sponge.admin.models.others.resp;

import lombok.Getter;
import org.apache.http.util.TextUtils;

/**
 * @Author:Fei.chu
 * @Date:Created in 17:13 2018/07/26
 * @Description:
 */
@Getter
public class CallLogDetailsResp {
    public String name;
    public String phone;
    public int call_in_count;
    public int call_out_count;
    public int total_count;

    public String hideName(String name) {
        if (!TextUtils.isEmpty(name)) {
            if ("运营商无匹配".equals(name))
                return name;
            int len = name.length();
            switch (len) {
                case 2:name = name.substring(0,1)+"*";break;
                default:name = name.substring(0,1)+"**";break;
            }
        }
        return name;
    }

    public String hideMobile(String mobile) {
        if (!TextUtils.isEmpty(mobile)) {
            mobile = mobile.substring(0,3)+"********";
        }
        return mobile;
    }
}
