package org.noear.rock.impl.controller.trigger;

import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;
import org.noear.solon.cloud.CloudClient;
import org.noear.solon.cloud.model.Instance;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.solon.logging.utils.TagsMDC;
import org.noear.water.WaterClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class EndHandler implements Handler {

    static Logger log = LoggerFactory.getLogger(EndHandler.class);

    @Override
    public void handle(Context ctx) throws Exception {
        long start = ctx.attr("_start", 0L);
        long times = System.currentTimeMillis() - start;

        CloudClient.metric().addMeter("rpc", ctx.path(), times, false);


        String _from = CloudClient.trace().getFromId();
        String _out = ctx.attr("output", "");
        String _in = ONode.stringify(ctx.paramMap());


        TagsMDC.tag0(ctx.path()).tag3(_from);

        if (_out != null && _out.startsWith("warn::")) {
            log.warn("::{}\r\n::{}", _in, _out);
        } else {
            log.info("::{}\r\n::{}", _in, _out);
        }
    }
}
