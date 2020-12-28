package demo;

import org.noear.sponge.rockgateway.RockGateway;
import org.noear.sponge.rockgateway.UapiCode;
import org.noear.sponge.rockgateway.interceptor.EndInterceptor;
import org.noear.sponge.rockgateway.interceptor.OutputInterceptor;
import org.noear.sponge.rockgateway.interceptor.StartInterceptor;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;

@Mapping("/api/*")
@Controller
class _demo_api extends RockGateway {
    @Override
    protected void register() {
        //开始计时
        before(new StartInterceptor());

        //输出
        before(new OutputInterceptor());

        //结束计时
        before(new EndInterceptor("{tag}"));

        // 添加接口
        //
        add(API_0_0_1.class);
    }

    public static class API_0_0_1 {
        @Mapping
        public Object call() {
            return new UapiCode(0, "接口不存在");
        }
    }
}
