package apidemo.controller.cmds;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Mapping;
import apidemo.controller.SysCode;
import apidemo.controller.UapiBase;

@Component(tag = "cmd")
public class CMD_0 extends UapiBase {

    @Mapping
    public void exec() throws Exception {
        throw SysCode.CODE_11;
    }
}
