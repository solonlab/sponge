package org.noear.rock.utils;

import org.noear.solon.Solon;

import java.sql.SQLException;
import java.util.Locale;

/**
 * @author noear 2021/2/24 created
 */
public class I18nUtils {
    static CodeContext codeContext;
    static NameContext nameContext;

    private static CodeContext getCodeContext() {
        if (codeContext == null) {
            synchronized (I18nUtils.class) {
                if (codeContext == null) {
                    codeContext = I18nContextFactory.getCodeContext(Solon.cfg().appName());
                }
            }
        }

        return codeContext;
    }

    private static NameContext getNameContext() {
        if (nameContext == null) {
            synchronized (I18nUtils.class) {
                if (nameContext == null) {
                    nameContext = I18nContextFactory.getNameContext(Solon.cfg().appName());
                }
            }
        }

        return nameContext;
    }

    public static String getByCode(int code, String lang) throws SQLException {
        return getCodeContext().get(code, lang);
    }

    public static String getByCodeAndFormat(int code, String lang, Object... args) throws SQLException {
        return getCodeContext().getAndFormat(code, lang, args);
    }

    public static String getByCodeAndFormat(int code, String lang, Locale locale, Object... args) throws SQLException {
        return getCodeContext().getAndFormat(code, lang, locale, args);
    }

    public static String getByName(String name, String lang) throws SQLException {
        return getNameContext().get(name, lang);
    }

    public static String getByNameAndFormat(String name, String lang, Object... args) throws SQLException {
        return getNameContext().getAndFormat(name, lang, args);
    }

    public static String getByNameAndFormat(String name, String lang, Locale locale, Object... args) throws SQLException {
        return getNameContext().getAndFormat(name, lang, locale, args);
    }
}
