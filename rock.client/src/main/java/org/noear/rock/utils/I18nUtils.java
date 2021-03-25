package org.noear.rock.utils;

import org.noear.solon.Solon;

import java.sql.SQLException;
import java.util.Locale;

/**
 * @author noear 2021/2/24 created
 */
public class I18nUtils {
    static I18nContext context;

    private static I18nContext getContext() {
        if (context == null) {
            synchronized (I18nUtils.class) {
                if (context == null) {
                    context = new I18nContext(Solon.cfg().appName());
                }
            }
        }

        return context;
    }

    public static String getByCode(int code, String lang) throws SQLException {
        return getContext().getByCode(code, lang);
    }

    public static String getByCodeAndFormat(int code, String lang, Object... args) throws SQLException {
        return getContext().getByCodeAndFormat(code, lang, args);
    }

    public static String getByCodeAndFormat(int code, String lang, Locale locale, Object... args) throws SQLException {
        return getContext().getByCodeAndFormat(code, lang, locale, args);
    }

    public static String getByName(String name, String lang) throws SQLException {
        return getContext().getByName(name, lang);
    }

    public static String getByNameAndFormat(String name, String lang, Object... args) throws SQLException {
        return getContext().getByNameAndFormat(name, lang, args);
    }

    public static String getByNameAndFormat(String name, String lang, Locale locale, Object... args) throws SQLException {
        return getContext().getByNameAndFormat(name, lang, locale, args);
    }
}
