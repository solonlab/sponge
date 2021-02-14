package org.noear.sponge.rockuapi;

import org.noear.sponge.rock.RockClient;
import org.noear.water.utils.TextUtils;

import java.sql.SQLException;

public class UapiCodes {
    /**
     * 成功
     */
    public static final UapiCode CODE_200 = new UapiCode(200, "Succeed");

    /**
     * 失败，未知错误
     */
    public static final UapiCode CODE_400 = new UapiCode(400, "Unknown error");


    /**
     * 请求的接口不存在或不再支持
     */
    public static final UapiCode CODE_4001011 = new UapiCode(4001011, "The api not exist");

    /**
     * 请求的不符合规范
     */
    public static final UapiCode CODE_4001012 = new UapiCode(4001012, "The request is not up to par");


    /**
     * 请求的签名校验失败
     */
    public static final UapiCode CODE_4001013 = new UapiCode(4001013, "The signature error");

    /**
     * 请求的参数缺少或有错误
     */
    public static final UapiCode CODE_4001014(String names) {
        return new UapiCode(4001014, names);
    }

    /**
     * 请求太频繁了
     */
    public static final UapiCode CODE_4001015 = new UapiCode(4001015, "Too many requests");
    /**
     * 请求不在白名单
     */
    public static final UapiCode CODE_4001016 = new UapiCode(4001016, "The request is not in the whitelist");


    public static final String CODE_txt(int agroup_id, String lang, UapiCode error) throws SQLException {
        if (agroup_id > 0) {
            String description = RockClient.getAppCode(agroup_id, error.getCode(), lang);

            if (TextUtils.isEmpty(description) == false) {
                return description;
            }
        }

        if (error.getCode() == 4001014) {
            return "Parameter missing or error" + (error.getDescription() == null ? "" : "(" + error.getDescription() + ")");
        } else {
            return error.getDescription();
        }
    }
}
