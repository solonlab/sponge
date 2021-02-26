package org.noear.rock.utils;

import org.noear.solon.Solon;

import java.sql.SQLException;

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

    public static String getByName(String name, String lang) throws SQLException {
        return getContext().getByName(name, lang);
    }
}
