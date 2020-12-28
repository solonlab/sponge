package apidemo.controller.apis;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Mapping;
import apidemo.controller.SysCode;
import apidemo.controller.UapiBase;

@Component(tag = "api")
public class API_0 extends UapiBase {

    @Mapping
    public void exec() throws Exception {
        throw SysCode.CODE_11;
    }

}
