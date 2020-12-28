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
        before(new StartInterceptor());//开始计时
        before(new ParamsBuildInterceptor(new RockAesDecoder())); //构建参数
        before(new ParamsAuthInterceptor(new RockSha256Encoder()));//签权，可以没有

        after(new OutputBuildInterceptor(new RockAesEncoder()));//构建输出内容
        after(new OutputSignInterceptor(new RockSha1Encoder()));//输出签名
        after(new OutputInterceptor());//输出
        after(new LogInterceptor());//日志
        after(new EndInterceptor("CMD"));//结束计时，并上报

        Aop.beanOnloaded(() -> {
            Aop.beanForeach((bw) -> {
                if ("cmd".equals(bw.tag())) {
                    add(bw);
                }
            });
        });
    }
}
