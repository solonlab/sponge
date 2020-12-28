package apidemo.controller;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.Aop;
import org.noear.sponge.rockgateway.RockGateway;
import org.noear.sponge.rockgateway.decoder.RockAesDecoder;
import org.noear.sponge.rockgateway.encoder.RockAesEncoder;
import org.noear.sponge.rockgateway.encoder.RockSha1Encoder;
import org.noear.sponge.rockgateway.encoder.RockSha256Encoder;
import org.noear.sponge.rockgateway.interceptor.*;
import apidemo.Config;
import apidemo.controller.interceptor.LogInterceptor;

@Mapping("/API/*")
@Controller
public class ApiGateway extends RockGateway {

    @Override
    public int agroup_id() {
        return Config.agroupId;
    }

    @Override
    protected void register() {
//        before(new StartInterceptor());
//        before(new ParamsBuildInterceptor(null)); //构建参数
//        addInterceptor(new AuthInterceptor()); //可以不要签权
//
//        after(new OutputBuildInterceptor(new RockXorEncoder()));
//        after(new OutputInterceptor()); //输出
//        after(new LogInterceptor()); //也可以不做日志
//        after(new EndInterceptor("API"));

        before(new StartInterceptor());
        before(new ParamsBuildInterceptor(new RockAesDecoder()));
        before(new ParamsAuthInterceptor(new RockSha256Encoder()));

        after(new OutputBuildInterceptor(new RockAesEncoder()));
        after(new OutputInterceptor());
        after(new OutputSignInterceptor(new RockSha1Encoder()));
        after(new LogInterceptor());
        after(new EndInterceptor("API"));


        Aop.beanOnloaded(() -> {
            Aop.beanForeach((bw) -> {
                if ("api".equals(bw.tag())) {
                    add(bw);
                }
            });
        });
    }
}
