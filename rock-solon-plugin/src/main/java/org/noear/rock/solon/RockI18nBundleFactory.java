package org.noear.rock.solon;

import org.noear.rock.i18n.I18nContextManager;
import org.noear.solon.Solon;
import org.noear.solon.i18n.I18nBundle;
import org.noear.solon.i18n.I18nBundleFactory;
import org.noear.solon.i18n.I18nUtil;

import java.util.Locale;

/**
 * 适配 solon.i18n 规范
 *
 * @author noear 2021/9/8 created
 */
public class RockI18nBundleFactory implements I18nBundleFactory {
    @Override
    public I18nBundle create(String bundleName, Locale locale) {
        if (I18nUtil.getMessageBundleName().equals(bundleName)) {
            bundleName = Solon.cfg().appName();
        }

        return new RockI18nBundle(I18nContextManager.getMessageContext(bundleName), locale);
    }
}
