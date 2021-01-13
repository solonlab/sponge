package apidemo.controller;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.Aop;
import org.noear.sponge.rockuapi.UapiGateway;
import org.noear.sponge.rockuapi.decoder.AesDecoder;
import org.noear.sponge.rockuapi.encoder.AesEncoder;
import org.noear.sponge.rockuapi.encoder.Sha1Encoder;
import org.noear.sponge.rockuapi.encoder.Sha256Encoder;
import apidemo.Config;
import apidemo.controller.interceptor.LogInterceptor;
import org.noear.sponge.rockuapi.interceptor.ParamsAuthInterceptor;

@Mapping("/API/*")
@Controller
public class ApiGateway extends UapiGateway {

    @Override
    public int agroup_id() {
        return Config.agroupId;
    }

    @Override
    protected void register() {
        before(new org.noear.sponge.rockuapi.interceptor.StartInterceptor()); //开始计时
        before(new org.noear.sponge.rockuapi.interceptor.ParamsBuildInterceptor(new AesDecoder())); //构建参数
        before(new ParamsAuthInterceptor(new Sha256Encoder()));//签权，可以没有

        after(new org.noear.sponge.rockuapi.interceptor.OutputBuildInterceptor(new AesEncoder()));//构建输出内容
        after(new org.noear.sponge.rockuapi.interceptor.OutputSignInterceptor(new Sha1Encoder()));//输出签名
        after(new org.noear.sponge.rockuapi.interceptor.OutputInterceptor());//输出
        after(new LogInterceptor());//记录日志
        after(new org.noear.sponge.rockuapi.interceptor.EndInterceptor("API"));//结束计时，并上报

        Aop.beanOnloaded(() -> {
            Aop.beanForeach((bw) -> {
                if ("api".equals(bw.tag())) {
                    add(bw);
                }
            });
        });
    }
}
