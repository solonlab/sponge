package org.noear.rock.integration;

import org.noear.solon.core.AppContext;
import org.noear.solon.core.Plugin;

/**
 * @author noear 2021/10/29 created
 */
public class XPluginImp implements Plugin {
    @Override
    public void start(AppContext context) {
        context.beanMake(msg_updatecache.class);
    }
}
