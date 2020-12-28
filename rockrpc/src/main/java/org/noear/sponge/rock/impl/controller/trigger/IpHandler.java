package org.noear.sponge.rock.impl.controller.trigger;

import org.noear.solon.annotation.Component;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.sponge.rock.impl.utils.IpUtil;
import org.noear.water.WaterClient;

@Component
public class IpHandler implements Handler {

    @Override
    public void handle(Context ctx) throws Throwable {
        String ip = IpUtil.getIP(ctx);

        if (WaterClient.Whitelist.existsOfServerIp(ip) == false) {
            String warn = ip + " is not whitelist!";

            System.err.println(warn);
            ctx.attrSet("output", "warn::" + warn);
            ctx.render(new RuntimeException(warn));
            ctx.setHandled(true);
        }
    }
}
