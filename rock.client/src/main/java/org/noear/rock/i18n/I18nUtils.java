package org.noear.rock.i18n;

import org.noear.solon.Solon;

import java.sql.SQLException;
import java.util.Locale;

/**
 * @author noear 2021/2/24 created
 */
public class I18nUtils {
    static CodeContext codeContext;
    static MessageContext nameContext;

    private static CodeContext getCodeContext() {
        if (codeContext == null) {
            synchronized (I18nUtils.class) {
                if (codeContext == null) {
                    codeContext = I18nContextManager.getCodeContext(Solon.cfg().appName());
                }
            }
        }

        return codeContext;
    }

    private static MessageContext getMessageContext() {
        if (nameContext == null) {
            synchronized (I18nUtils.class) {
                if (nameContext == null) {
                    nameContext = I18nContextManager.getMessageContext(Solon.cfg().appName());
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
        return getMessageContext().get(name, lang);
    }

    public static String getByNameAndFormat(String name, String lang, Object... args) throws SQLException {
        return getMessageContext().getAndFormat(name, lang, args);
    }

    public static String getByNameAndFormat(String name, String lang, Locale locale, Object... args) throws SQLException {
        return getMessageContext().getAndFormat(name, lang, locale, args);
    }
}
