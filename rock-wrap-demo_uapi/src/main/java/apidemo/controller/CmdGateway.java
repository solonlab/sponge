package apidemo.controller;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.Aop;
import org.noear.sponge.rockuapi.UapiGateway;
import org.noear.sponge.rockuapi.decoder.RockAesDecoder;
import org.noear.sponge.rockuapi.encoder.RockAesEncoder;
import org.noear.sponge.rockuapi.encoder.RockSha1Encoder;
import org.noear.sponge.rockuapi.encoder.RockSha256Encoder;
import apidemo.Config;
import apidemo.controller.interceptor.LogInterceptor;
import org.noear.sponge.rockuapi.interceptor.ParamsAuthInterceptor;

@Mapping("/CMD/*")
@Controller
public class CmdGateway extends UapiGateway {

    @Override
    public int agroup_id() {
        return Config.agroupId;
    }

    @Override
    protected void register() {
        before(new org.noear.sponge.rockuapi.interceptor.StartInterceptor());//开始计时
        before(new org.noear.sponge.rockuapi.interceptor.ParamsBuildInterceptor(new RockAesDecoder())); //构建参数
        before(new ParamsAuthInterceptor(new RockSha256Encoder()));//签权，可以没有

        after(new org.noear.sponge.rockuapi.interceptor.OutputBuildInterceptor(new RockAesEncoder()));//构建输出内容
        after(new org.noear.sponge.rockuapi.interceptor.OutputSignInterceptor(new RockSha1Encoder()));//输出签名
        after(new org.noear.sponge.rockuapi.interceptor.OutputInterceptor());//输出
        after(new LogInterceptor());//日志
        after(new org.noear.sponge.rockuapi.interceptor.EndInterceptor("CMD"));//结束计时，并上报

        Aop.beanOnloaded(() -> {
            Aop.beanForeach((bw) -> {
                if ("cmd".equals(bw.tag())) {
                    add(bw);
                }
            });
        });
    }
}
