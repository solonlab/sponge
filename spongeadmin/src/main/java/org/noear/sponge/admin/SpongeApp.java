package org.noear.sponge.admin;

import org.noear.solon.Solon;
import org.noear.solon.core.handle.Context;
import org.noear.sponge.admin.dso.LogUtil;

public class SpongeApp {
    public static void main(String[] args) {
        Solon.start(SpongeApp.class, args).onError((ex) -> {
            Context ctx = Context.current();

            if (ctx != null) {
                LogUtil.error("global", ctx.path(), ctx.paramMap().toString(), ex);
            }else{
                LogUtil.error("global", "", "", ex);
            }
        });
    }
}

