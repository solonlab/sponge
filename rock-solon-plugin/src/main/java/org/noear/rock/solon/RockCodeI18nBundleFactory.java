package org.noear.rock.solon;

import org.noear.rock.i18n.I18nContextManager;
import org.noear.solon.Solon;
import org.noear.solon.i18n.I18nBundle;
import org.noear.solon.i18n.I18nBundleFactory;
import org.noear.solon.i18n.I18nUtil;

import java.util.Locale;

/**
 * 适配 solon.i18n 规范（）
 *
 * @author noear 2022/4/8 created
 */
public class RockCodeI18nBundleFactory implements I18nBundleFactory {
    private final String codeBundleNameDef;

    public RockCodeI18nBundleFactory() {
        codeBundleNameDef = Solon.cfg().appName() + "__code";
    }

    @Override
    public I18nBundle create(String bundleName, Locale locale) {
        if (I18nUtil.getMessageBundleName().equals(bundleName)) {
            bundleName = Solon.cfg().appName();
        }

        if (bundleName.equals(codeBundleNameDef)) {
            String bundleName2 = bundleName.substring(0, bundleName.length()-6);
            return new RockCodeI18nBundle(I18nContextManager.getCodeContext(bundleName2), locale);
        }

        return new RockI18nBundle(I18nContextManager.getMessageContext(bundleName), locale);
    }
}
