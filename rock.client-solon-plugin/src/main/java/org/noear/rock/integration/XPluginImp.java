package org.noear.rock.integration;

import org.noear.solon.SolonApp;
import org.noear.solon.core.Plugin;

/**
 * @author noear 2021/10/29 created
 */
public class XPluginImp implements Plugin {
    @Override
    public void start(SolonApp app) {
        app.beanMake(msg_updatecache.class);
    }
}
