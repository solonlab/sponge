package org.noear.rock.impl.controller.trigger;

import org.noear.solon.Solon;
import org.noear.solon.cloud.CloudClient;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;
import org.noear.solon.logging.utils.TagsMDC;
import org.noear.water.WaterClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IpHandler implements Filter {

    static Logger log = LoggerFactory.getLogger(IpHandler.class);

    @Override
    public void doFilter(Context ctx, FilterChain chain) throws Throwable {
        String ip = ctx.realIp();

        if (Solon.cfg().isWhiteMode()) {
            if (WaterClient.Whitelist.existsOfServerIp(ip) == false) {
                String _from = CloudClient.trace().getFromId();
                String warn = ip + " is not whitelist!";

                TagsMDC.tag0(ctx.path()).tag3(_from);
                log.warn(warn);

                ctx.attrSet("output", "warn::" + warn);
                ctx.render(new RuntimeException(warn));
                ctx.setHandled(true);
            }
        }


        chain.doFilter(ctx);
    }
}
