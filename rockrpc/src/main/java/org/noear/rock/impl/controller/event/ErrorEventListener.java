package org.noear.rock.impl.controller.event;

import org.noear.mlog.Logger;
import org.noear.mlog.utils.Tags;
import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.event.EventListener;
import org.noear.solon.core.handle.Context;

@Component
public class ErrorEventListener implements EventListener<Throwable> {
    static Logger log = Logger.get("sponge_log_rock");

    @Override
    public void onEvent(Throwable err) {
        Context ctx = Context.current();

        if (ctx != null) {
            String _in = ONode.stringify(ctx.paramMap());

            log.error(Tags.tag0(ctx.path()), "{}\r\n{}", _in, err);
            //WaterClient.Log.append("sponge_log_rock", Level.ERROR, ctx.path(), _in, err);
        }
    }
}
