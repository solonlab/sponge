package org.noear.rock.solon;

import org.noear.rock.i18n.MessageContext;
import org.noear.solon.i18n.I18nBundle;

import java.util.Locale;
import java.util.Map;

/**
 * 适配 solon.i18n 规范
 *
 * @author noear 2021/9/8 created
 */
public class RockNameI18nBundle implements I18nBundle {
    final MessageContext context;
    final Locale locale;
    final String locale_lang;

    public RockNameI18nBundle(MessageContext context, Locale locale) {
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
