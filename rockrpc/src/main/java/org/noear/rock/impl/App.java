package org.noear.rock.impl;

import org.noear.mlog.Logger;
import org.noear.mlog.utils.Tags;
import org.noear.solon.Solon;
import org.noear.solon.core.handle.Context;

public class App {
    static Logger log = Logger.get(App.class);

    public static void main(String[] args) {

        Solon.start(App.class, args, app -> {
            app.enableSafeStop(app.cfg().isFilesMode() == false);
        }).onError((ex) -> {
            Context ctx = Context.current();

            if (ctx != null) {
                log.error(Tags.tag0(ctx.path()), "{}\r\n{}", ctx.paramMap().toString(), ex);
            } else {
                log.error(Tags.tag0("global"), ex);
            }
        });
    }
}
