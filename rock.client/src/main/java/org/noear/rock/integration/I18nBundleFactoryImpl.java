package org.noear.rock.integration;

import org.noear.rock.utils.I18nContextManager;
import org.noear.solon.i18n.I18nBundle;
import org.noear.solon.i18n.I18nBundleFactory;

import java.util.Locale;

/**
 * 适配 solon.i18n 规范
 *
 * @author noear 2021/9/8 created
 */
public class I18nBundleFactoryImpl implements I18nBundleFactory {
    @Override
    public I18nBundle create(String bundleName, Locale locale) {
        return new I18nBundleImpl(I18nContextManager.getNameContext(bundleName), locale);
    }
}
