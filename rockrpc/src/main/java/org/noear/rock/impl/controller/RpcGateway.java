package org.noear.rock.impl.controller;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Gateway;
import org.noear.rock.impl.controller.trigger.EndHandler;
import org.noear.rock.impl.controller.trigger.IpHandler;
import org.noear.rock.impl.controller.trigger.StartHandler;

@Mapping("/*")
@Component
public class RpcGateway extends Gateway {
    @Override
    protected void register() {
        before(StartHandler.class);
        before(IpHandler.class);

        after(EndHandler.class);

        add(RockRpcService.class, true);
    }
}
