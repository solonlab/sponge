package webapp.controller;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Gateway;
import webapp.controller.trigger.EndHandler;
import webapp.controller.trigger.IpHandler;
import webapp.controller.trigger.StartHandler;

@Mapping("/*")
@Controller
public class RpcGateway extends Gateway {
    @Override
    protected void register() {
        before(StartHandler.class);
        before(IpHandler.class);

        after(EndHandler.class);

        add(RockRpcService.class, true);
    }
}
