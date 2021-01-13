package org.noear.sponge.rockuapi.interceptor;

import org.noear.sponge.rockuapi.UapiParams;
import org.noear.sponge.rockuapi.encoder.DefEncoder;
import org.noear.sponge.rockuapi.encoder.Encoder;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;

/** 输出签名拦截器（用于输出内容的签名） */
public class OutputSignInterceptor implements Handler {
    private Encoder _encoder;

    public OutputSignInterceptor(){
        _encoder = new DefEncoder();
    }

    public OutputSignInterceptor(Encoder encoder) {
        if (encoder == null) {
            _encoder = new DefEncoder();
        } else {
            _encoder = encoder;
        }
    }

    @Override
    public void handle(Context ctx) throws Throwable {
        /** 获取参数 */
        UapiParams ctp = ctx.attr(Attrs.params);

        if(ctp == null){
            return;
        }

        String output = ctx.attr(Attrs.output, null);

        if (output != null) {
            String out_sign = _encoder.tryEncode(ctx, ctp.getApp(), output);
            ctx.headerSet(Attrs.sign, out_sign);
        }
    }
}
