package lib.sponge.rockgateway.interceptor;


import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;

/** 输出拦截器（用于内容格式化并输出） */
public class OutputInterceptor implements Handler {

    @Override
    public void handle(Context context) throws Throwable {
        String output = context.attr(Attrs.output, null);

        if (output != null) {
            context.outputAsJson(output);
        }
    }
}
