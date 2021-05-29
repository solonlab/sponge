package org.noear.sponge.admin.dso.auth;

import org.noear.bcf.BcfSessionBase;
import org.noear.solon.Solon;
import org.noear.solon.cloud.CloudClient;
import org.noear.solon.core.handle.Context;
import org.noear.solon.extend.auth.AuthInterceptorLogined;
import org.noear.solon.extend.auth.AuthInterceptorUrl;
import org.noear.sponge.admin.dso.Session;

/**
 * @author noear 2021/5/29 created
 */
public class AuthInterceptorImpl extends AuthInterceptorUrl {
    @Override
    public void handle(Context ctx) throws Throwable {
        String ip = ctx.realIp();

        if (Solon.cfg().isWhiteMode() && Solon.cfg().isFilesMode() == false) {
            if (CloudClient.list().inListOfClientIp(ip) == false) {
                ctx.setHandled(true);
                ctx.output(ip + ", not is whitelist!");
                return;
            }
        }

        int puid = Session.current().getPUID();
        if (puid > 0) {
            ctx.attrSet("user_puid", "" + puid);
            ctx.attrSet("user_name", BcfSessionBase.global().getUserName());
        }

        super.handle(ctx);
    }
}
