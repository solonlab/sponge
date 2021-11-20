package org.noear.rock.impl.controller.trigger;

import org.noear.snack.ONode;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Component;
import org.noear.solon.cloud.CloudClient;
import org.noear.solon.cloud.model.Instance;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.solon.logging.utils.TagsMDC;
import org.noear.water.WW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class EndHandler implements Handler {

    static Logger log = LoggerFactory.getLogger(EndHandler.class);

    @Override
    public void handle(Context ctx) throws Exception {
        long start = ctx.attr("_start", 0L);
        long times = System.currentTimeMillis() - start;

        String service = Solon.cfg().appName();
        String _node = Instance.local().address();
        String _from = CloudClient.trace().getFromId(); //FromUtils.getFrom(ctx);

        CloudClient.metric().addMeter(service, "rpc", ctx.path(), times);
        CloudClient.metric().addMeter(WW.track_service, service, _node, times);
        CloudClient.metric().addMeter(WW.track_from, service, _from, times);

        String _out = ctx.attr("output", "");
        String _in = ONode.stringify(ctx.paramMap());

        StringBuilder buf = new StringBuilder();
        buf.append("> Param: ").append(_in).append("\n");
        buf.append("T Elapsed time: ").append(times).append("ms");
        buf.append("\n\n");
        buf.append("< Body: ").append(_out);


        TagsMDC.tag0(ctx.path());
        TagsMDC.tag3(_from);

        if (_out != null && _out.startsWith("warn::")) {
            log.warn(buf.toString());
        } else {
            log.info(buf.toString());
        }
    }
}
