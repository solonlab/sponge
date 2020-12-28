package org.noear.sponge.admin.controller._bcf;


import org.noear.bcf.BcfInterceptorBase;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.water.WaterClient;
import org.noear.water.solon_plugin.IPUtils;
import org.noear.sponge.admin.dso.Session;

@Mapping(value = "**", before = true)
@Component
public class BcfInterceptor extends BcfInterceptorBase implements Handler {
    @Override
    public void handle(Context ctx) throws Throwable {
        verifyHandle(ctx);
    }


    @Override
    public int getPUID() {
        return Session.current().getPUID();
    }

    @Override
    public void verifyHandle(Context ctx) throws Exception {
        //IP白名单校验
        String path = ctx.path();

        if (path.startsWith("/rock/") || path.startsWith("/auth/")) {
            String ip = IPUtils.getIP(ctx);

            if (WaterClient.Whitelist.existsOfClientIp(ip) == false) {
                ctx.setHandled(true);
                ctx.output(ip + ",not is whitelist!");
                return;
            }
        }

        super.verifyHandle(ctx);
    }

}