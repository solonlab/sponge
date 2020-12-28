package lib.sponge.rockgateway.interceptor;

import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.water.WaterClient;
import org.noear.water.solon_plugin.FromUtils;
import org.noear.water.solon_plugin.XWaterAdapter;
import org.noear.water.utils.Timecount;

/** 结束计时拦截器（完成计时，并发送到WATER） */
public class EndInterceptor implements Handler {
    private String _tag;
    public EndInterceptor(String tag){
        _tag = tag;
    }

    @Override
    public void handle(Context ctx) throws Throwable {
        /** 获取一下计时器（开始计时的时候设置的） */
        Timecount timecount = ctx.attr(Attrs.timecount, null);

        if (timecount == null) {
            return;
        }

        String _from = FromUtils.getFrom(ctx);

        String service = XWaterAdapter.global().service_name();
        String path = ctx.path();
        String node = XWaterAdapter.global().localHost();

        WaterClient.Track.track(service, _tag, path, timecount.stop().milliseconds(), node, _from);
    }
}
