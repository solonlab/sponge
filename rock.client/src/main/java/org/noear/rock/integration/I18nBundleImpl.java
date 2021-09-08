package org.noear.rock.integration;

import org.noear.rock.utils.NameContext;
import org.noear.solon.i18n.I18nBundle;

import java.util.Locale;
import java.util.Map;

/**
 * 适配 solon.i18n 规范
 *
 * @author noear 2021/9/8 created
 */
public class I18nBundleImpl implements I18nBundle {
    final NameContext context;
    final Locale locale;
    final String locale_lang;

    public I18nBundleImpl(NameContext context, Locale locale) {
        this.context = context;
        this.locale = locale;
        this.locale_lang = locale.toString();
    }

    @Override
    public Map<String, String> toMap() {
        return context.getMap(locale_lang);
    }

    @Override
    public Locale locale() {
        return locale;
    }

    @Override
    public String get(String key) {
        return context.get(key, locale_lang);
    }
}
