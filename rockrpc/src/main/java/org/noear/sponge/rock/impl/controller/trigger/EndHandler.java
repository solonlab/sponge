package org.noear.sponge.rock.impl.controller.trigger;

import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;
import org.noear.solon.cloud.CloudLogger;
import org.noear.solon.cloud.model.Instance;
import org.noear.solon.cloud.utils.Tags;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.water.WaterClient;
import org.noear.water.utils.FromUtils;

@Component
public class EndHandler implements Handler {

    CloudLogger log = CloudLogger.get("sponge_log_rock");

    @Override
    public void handle(Context ctx) throws Exception {
        long start = ctx.attr("_start", 0L);
        long times = System.currentTimeMillis() - start;
        String _node = Instance.local().address();
        String _from = FromUtils.getFrom(ctx);

        WaterClient.Track.track(Instance.local().service(), "rpc", ctx.path(), times, _node, _from);

        String _out = ctx.attr("output", "");
        String _in = ONode.stringify(ctx.paramMap());


        if (_out != null && _out.startsWith("warn::")) {
            log.warn(Tags.tag0(ctx.path()).tag3(_from), "{}\n\n{}", _in, _out);
            //WaterClient.Log.append("rock_log", Level.WARN, ctx.path(), null, null, _from, _in, _out);
        } else {
            log.info(Tags.tag0(ctx.path()).tag3(_from), "{}\n\n{}", _in, _out);
            //WaterClient.Log.append("rock_log", Level.INFO, ctx.path(), null, null, _from, _in, _out);
        }
    }
}
