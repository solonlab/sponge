package apidemo.controller.interceptor;

import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.sponge.rockuapi.UapiParams;
import org.noear.sponge.rockuapi.interceptor.Attrs;
import apidemo.controller.UapiBase;
import apidemo.dso.Logger;

public class LogInterceptor implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        UapiBase api = UapiBase.current();

        UapiParams params = api.params();
        Throwable error = api.error();

        String output = ctx.attr(Attrs.output);

        if (null != output) {
            Logger.logOutput(api, params, output);
        }

        if (null != error) {
            Logger.logError(api, params, error);
        }
    }
}
