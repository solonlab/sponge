package org.noear.sponge.admin.dso.auth;

import org.noear.bcf.BcfSessionBase;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Component;
import org.noear.solon.cloud.CloudClient;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;
import org.noear.sponge.admin.dso.Session;

/**
 * @author noear 2021/5/28 created
 */
@Component
public class WhitelistFilter implements Filter {
    @Override
    public void doFilter(Context ctx, FilterChain chain) throws Throwable {
        String ip = ctx.realIp();

        if (Solon.cfg().isWhiteMode() && Solon.cfg().isFilesMode() == false) {
            if (CloudClient.list().inListOfClientIp(ip) == false) {
                ctx.setHandled(true);
                ctx.output(ip + ", not is whitelist!");
                return;
            }
        }

        int puid = Session.global().getPUID();
        if (puid > 0) {
            ctx.attrSet("user_puid", "" +puid);
            ctx.attrSet("user_name", BcfSessionBase.global().getUserName());
        }

        chain.doFilter(ctx);
    }
}
