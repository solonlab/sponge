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

@Mapping("/CMD/*")
@Controller
public class CmdGateway extends RockGateway {

    @Override
    public int agroup_id() {
        return Config.agroupId;
    }

    @Override
    protected void register() {
        before(new StartInterceptor());
        before(new ParamsBuildInterceptor(new RockAesDecoder()));
        before(new ParamsAuthInterceptor(new RockSha256Encoder()));

        after(new OutputBuildInterceptor(new RockAesEncoder()));
        after(new OutputInterceptor());
        after(new OutputSignInterceptor(new RockSha1Encoder()));
        after(new LogInterceptor());
        after(new EndInterceptor("CMD"));


        Aop.beanOnloaded(() -> {
            Aop.beanForeach((bw) -> {
                if ("cmd".equals(bw.tag())) {
                    add(bw);
                }
            });
        });
    }
}
