package org.noear.rock.utils;

import org.noear.rock.RockClient;
import org.noear.rock.model.AppCodeCollection;
import org.noear.rock.model.AppI18nCollection;
import org.noear.solon.Solon;
import org.noear.water.utils.TaskUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author noear 2021/2/24 created
 */
public class I18nUtils {
    static Map<String, AppCodeCollection> codeMap = new HashMap<>();
    static Map<String, AppI18nCollection> nameMap = new HashMap<>();

    static {
        TaskUtils.run(new I18nTask());
    }

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

    public static void updateCodeMap() throws SQLException {
        Map<String, AppCodeCollection> codeMap2 = new HashMap<>();

        for (Map.Entry<String, AppCodeCollection> kv : codeMap.entrySet()) {
            AppCodeCollection coll = RockClient.getServiceCodesByLang(Solon.cfg().appName(), kv.getKey());
            codeMap2.put(kv.getKey(), coll);
        }

        codeMap = codeMap2;
    }

    public static void updateNameMap() throws SQLException {
        Map<String, AppI18nCollection> nameMap2 = new HashMap<>();

        for (Map.Entry<String, AppI18nCollection> kv : nameMap.entrySet()) {
            AppI18nCollection coll = RockClient.getServiceI18nsByLang(Solon.cfg().appName(), kv.getKey());
            nameMap2.put(kv.getKey(), coll);
        }

        nameMap = nameMap2;
    }
}
