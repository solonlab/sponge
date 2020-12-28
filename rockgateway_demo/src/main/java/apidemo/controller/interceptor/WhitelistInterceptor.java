package apidemo.controller.interceptor;

import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.water.WaterClient;
import org.noear.water.solon_plugin.IPUtils;
import apidemo.controller.SysCode;

public class WhitelistInterceptor implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {

        String ip = IPUtils.getIP(ctx);

        if (!WaterClient.Whitelist.existsOfServerIp(ip)) {
            throw SysCode.CODE_16;
        }
    }
}
