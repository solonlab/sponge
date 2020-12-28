package zm.data.dobbin.bull.controller.cmds;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Mapping;
import zm.data.dobbin.bull.controller.SysCode;
import zm.data.dobbin.bull.controller.UapiBase;

@Component(tag = "cmd")
public class CMD_0 extends UapiBase {

    @Mapping
    public void exec() throws Exception {
        throw SysCode.CODE_11;
    }
}
