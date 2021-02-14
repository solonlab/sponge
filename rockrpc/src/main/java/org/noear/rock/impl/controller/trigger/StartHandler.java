package org.noear.rock.impl.controller.trigger;


import org.noear.solon.annotation.Component;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;

@Component
public class StartHandler implements Handler {
    @Override
    public void handle(Context context) throws Exception {
        context.attrSet("_start", System.currentTimeMillis());
    }
}
