package org.noear.rock.solon;

import org.noear.rock.i18n.CodeContext;
import org.noear.solon.core.Props;
import org.noear.solon.i18n.I18nBundle;

import java.util.Locale;
import java.util.Map;

/**
 * @author noear 2022/4/9 created
 */
public class RockCodeI18nBundle implements I18nBundle {
    final CodeContext context;
    final Locale locale;
    final String locale_lang;

    public RockCodeI18nBundle(CodeContext context, Locale locale) {
        this.context = context;
        this.locale = locale;
        this.locale_lang = locale.toString();
    }

    @Deprecated
    //@Override
    public Map<String, String> toMap() {
        return context.getMap(locale_lang);
    }

    @Override
    public Props toProps() {
        return new Props(context.getMap(locale_lang));
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
