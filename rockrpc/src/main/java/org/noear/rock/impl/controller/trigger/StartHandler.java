package org.noear.rock.impl.controller.trigger;


import org.noear.solon.annotation.Component;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;

@Component
public class StartHandler implements Handler {
    @Override
    public void handle(Context ctx) throws Exception {
        ctx.attrSet("_start", System.currentTimeMillis());
    }
}
