package org.noear.sponge.rock.impl.controller.trigger;

import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.water.WaterClient;
import org.noear.water.integration.solon.WaterAdapter;
import org.noear.water.log.WaterLogger;
import org.noear.water.utils.FromUtils;

@Component
public class EndHandler implements Handler {

    @Inject("rock_log")
    WaterLogger log;

    @Override
    public void handle(Context ctx) throws Exception {
        long start = ctx.attr("_start", 0L);
        long times = System.currentTimeMillis() - start;
        String _node = WaterAdapter.global().localHost();

        String _from = FromUtils.getFrom(ctx);

        WaterClient.Track.track(WaterAdapter.global().service_name(), "rpc", ctx.path(), times, _node, _from);

        String _out = ctx.attr("output", "");
        String _in = ONode.stringify(ctx.paramMap());


        if (_out != null && _out.startsWith("warn::")) {
            log.warn(ctx.path(), null, null, _from, _in, _out);
        } else {
            log.info(ctx.path(), null, null, _from, _in, _out);
        }
    }
}
