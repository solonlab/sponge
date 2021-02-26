package org.noear.rock.impl;

import org.noear.solon.Solon;
import org.noear.solon.core.handle.Context;
import org.noear.solon.logging.utils.TagsMDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    static Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        Solon.start(App.class, args, app -> {
            app.enableSafeStop(app.cfg().isFilesMode() == false);
        }).onError((ex) -> {
            Context ctx = Context.current();

            if (ctx != null) {
                TagsMDC.tag0(ctx.path());

                log.error("{}\r\n{}", ctx.paramMap().toString(), ex);
            } else {
                TagsMDC.tag0("global");

                log.error("{}", ex);
            }
        });
    }
}
