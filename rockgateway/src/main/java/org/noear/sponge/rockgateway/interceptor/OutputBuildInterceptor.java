package org.noear.sponge.rockgateway.interceptor;

import org.noear.sponge.rockgateway.encoder.RockDefEncoder;
import org.noear.snack.ONode;

import org.noear.sponge.rockgateway.encoder.RockEncoder;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;

/** 输出拦截器（用于内容格式化并输出） */
public class OutputBuildInterceptor implements Handler {

    RockEncoder _encoder;

    public OutputBuildInterceptor(){
        _encoder = new RockDefEncoder();
    }

    public OutputBuildInterceptor(RockEncoder encoder) {
        if (encoder == null) {
            _encoder = new RockDefEncoder();
        } else {
            _encoder = encoder;
        }
    }

    @Override
    public void handle(Context ctx) throws Throwable {
        if (ctx.result == null) {
            return;
        }

        Object result = ctx.result;
        String output = null;

        if (result instanceof ONode) {
            output = ((ONode) result).toJson();
        } else {
            output = ONode.stringify(result);
        }

        ctx.attrSet(Attrs.output, output);
    }
}
