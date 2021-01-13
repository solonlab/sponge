package apidemo.controller.interceptor;

import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.water.WaterClient;
import org.noear.water.utils.IPUtils;
import apidemo.controller.SysCodes;

public class WhitelistInterceptor implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {

        String ip = IPUtils.getIP(ctx);

        if (!WaterClient.Whitelist.existsOfServerIp(ip)) {
            throw SysCodes.CODE_16;
        }
    }
}
