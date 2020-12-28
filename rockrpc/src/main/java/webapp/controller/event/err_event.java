package webapp.controller.event;

import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.event.EventListener;
import org.noear.solon.core.handle.Context;
import org.noear.water.log.WaterLogger;

@Component
public class err_event implements EventListener<Throwable> {
    WaterLogger log = new WaterLogger("rock_log");

    @Override
    public void onEvent(Throwable err) {
        Context ctx = Context.current();

        if (ctx != null) {
            String _in = ONode.stringify(ctx.paramMap());

            log.error(ctx.path(), _in, err);
        }
    }
}
