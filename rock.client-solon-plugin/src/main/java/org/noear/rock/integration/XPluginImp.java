package org.noear.rock.integration;

import org.noear.rock.solon.I18nBundleFactoryImpl;
import org.noear.solon.SolonApp;
import org.noear.solon.core.Aop;
import org.noear.solon.core.Plugin;
import org.noear.solon.i18n.I18nBundleFactory;

/**
 * @author noear 2021/10/29 created
 */
public class XPluginImp implements Plugin {
    @Override
    public void start(SolonApp app) {
        app.beanMake(msg_updatecache.class);
        Aop.wrapAndPut(I18nBundleFactory.class, new I18nBundleFactoryImpl());
    }
}
