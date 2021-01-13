package apidemo.controller;

import org.noear.sponge.rockuapi.UapiCodes;
import org.noear.sponge.rockuapi.UapiCode;

/**
 * 系统状态码
 * */
public class SysCodes extends UapiCodes {

    public static final UapiCode CODE_102 = new UapiCode(102); // 用户不存在

    public static final UapiCode CODE_212 = new UapiCode(212); // 请求频繁

    public static final UapiCode CODE_1000 = new UapiCode(1000);  // 该产品不存在

    public static final UapiCode CODE_1001 = new UapiCode(1001);  // 今天额度已满

    public static final UapiCode CODE_1002 = new UapiCode(1002);  // 不符合进件条件

    public static final UapiCode CODE_1003 = new UapiCode(1003);  // 合作方接口异常

}
