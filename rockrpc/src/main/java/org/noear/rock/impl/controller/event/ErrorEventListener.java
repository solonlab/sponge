package org.noear.rock.impl.controller.event;

import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.event.EventListener;
import org.noear.solon.core.handle.Context;
import org.noear.solon.logging.utils.TagsMDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ErrorEventListener implements EventListener<Throwable> {
    static Logger log = LoggerFactory.getLogger("sponge_log_rock");

    @Override
    public void onEvent(Throwable err) {
        Context ctx = Context.current();

        if (ctx != null) {
            String _in = ONode.stringify(ctx.paramMap());

            TagsMDC.tag0(ctx.path());
            log.error("{}\r\n{}", _in, err);
            //WaterClient.Log.append("sponge_log_rock", Level.ERROR, ctx.path(), _in, err);
        }
    }
}
