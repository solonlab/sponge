package org.noear.sponge.rockgateway.validation;

import org.noear.solon.core.handle.Context;
import org.noear.solon.extend.validation.annotation.Whitelist;
import org.noear.solon.extend.validation.annotation.WhitelistChecker;
import org.noear.water.WaterClient;
import org.noear.water.solon_plugin.IPUtils;

public class WhitelistCheckerNew implements WhitelistChecker {
    @Override
    public boolean check(Whitelist anno, Context ctx) {
        String ip = IPUtils.getIP(ctx);

        return WaterClient.Whitelist.existsOfServerIp(ip);
    }
}
