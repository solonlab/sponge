package org.noear.rock.utils;

import org.noear.rock.RockClient;
import org.noear.rock.model.AppCodeCollection;
import org.noear.rock.model.AppI18nCollection;
import org.noear.solon.Solon;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author noear 2021/2/24 created
 */
public class I18nUtils {
    static Map<String, AppCodeCollection> codeMap = new HashMap<>();
    static Map<String, AppI18nCollection> nameMap = new HashMap<>();

    public static String getByCode(int code, String lang) throws SQLException {
        if (lang == null) {
            lang = "";
        }

        AppCodeCollection coll = codeMap.get(lang);
        if (coll == null) {
            synchronized (lang.intern()) {
                coll = codeMap.get(lang);
                if (coll == null) {
                    coll = RockClient.getServiceCodesByLang(Solon.cfg().appName(), lang);
                    codeMap.put(lang, coll);
                }
            }
        }

        return coll.get(code);
    }

    public static String getByName(String name, String lang) throws SQLException {
        if (lang == null) {
            lang = "";
        }

        AppI18nCollection coll = nameMap.get(lang);
        if (coll == null) {
            synchronized (lang.intern()) {
                coll = nameMap.get(lang);
                if (coll == null) {
                    coll = RockClient.getServiceI18nsByLang(Solon.cfg().appName(), lang);
                    nameMap.put(lang, coll);
                }
            }
        }

        return coll.get(name);
    }

    public static void update(){

    }
}
