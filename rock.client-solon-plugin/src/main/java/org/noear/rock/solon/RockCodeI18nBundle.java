package org.noear.rock.solon;

import org.noear.rock.i18n.CodeContext;
import org.noear.solon.i18n.I18nBundle;

import java.util.LinkedHashMap;
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

    @Override
    public Map<String, String> toMap() {
        Map<Integer, String> tmp = context.getMap(locale_lang);
        Map<String, String> tmp2 = new LinkedHashMap<>(tmp.size());

        tmp.forEach((k, v) -> {
            tmp2.put(String.valueOf(k), v);
        });

        return tmp2;
    }

    @Override
    public Locale locale() {
        return locale;
    }

    @Override
    public String get(String key) {
        return context.get(Integer.parseInt(key), locale_lang);
    }
}
