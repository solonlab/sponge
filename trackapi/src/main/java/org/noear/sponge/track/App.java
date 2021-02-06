package org.noear.sponge.track;

import org.noear.solon.Solon;
import org.noear.solon.core.event.PluginLoadEndEvent;
import org.noear.solon.core.handle.Context;
import org.noear.sponge.track.track.controller.UrlHandler;
import org.noear.sponge.track.track.dso.LogUtil;

public class App {
    public static void main(String[] args) {
        Solon.start(App.class, args, (app) -> {
            Config.tryInit();
        }).onError((ex) -> {
            Context ctx = Context.current();

            if (ctx != null) {
                LogUtil.error(ctx.path(), "", "", ctx.paramMap().toString(), ex);
            } else {
                LogUtil.error("global", ex);
            }
        });
    }
}
