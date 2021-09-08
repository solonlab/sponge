package org.noear.rock.utils;

import org.noear.rock.RockClient;
import org.noear.rock.model.AppCodeCollection;
import org.noear.rock.model.AppI18nCollection;
import org.noear.water.utils.TaskUtils;
import org.noear.water.utils.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 国际化上下文（其实例必须静态化）
 *
 * @author noear 2021/2/26 created
 */
public class I18nContext {
    static Logger log = LoggerFactory.getLogger(I18nContext.class);

    Map<String, AppCodeCollection> codeMap = new HashMap<>();
    Map<String, AppI18nCollection> nameMap = new HashMap<>();
    String service;
    boolean serviceOnlyName;

    public I18nContext(String service) {
        this(service, false);
    }

    public I18nContext(String service, boolean onlyName) {
        this.service = service;
        this.serviceOnlyName = onlyName;
        TaskUtils.run(10 * 1000, 10 * 1000, this::update);
    }

    public Map<Integer, String> getCodeMap(String lang) throws SQLException {
        if (lang == null) {
            lang = "";
        } else {
            lang = lang.toLowerCase(Locale.ROOT);
        }

        AppCodeCollection coll = codeMap.get(lang);
        if (coll == null) {
            synchronized (lang.intern()) {
                coll = codeMap.get(lang);
                if (coll == null) {
                    coll = RockClient.getServiceCodesByLang(service, lang);
                    codeMap.put(lang, coll);
                }
            }
        }

        return coll.data;
    }

    public String getByCode(int code, String lang) throws SQLException {
        return getCodeMap(lang).get(code);
    }

    public String getByCodeAndFormat(int code, String lang, Object[] args) throws SQLException {
        if (TextUtils.isEmpty(lang)) {
            return getByCodeAndFormat(code, lang, null, args);
        } else {
            return getByCodeAndFormat(code, lang, new Locale(lang), args);
        }
    }

    public String getByCodeAndFormat(int code, String lang, Locale locale, Object[] args) throws SQLException {
        String tml = getByCode(code, lang);

        MessageFormat mf = new MessageFormat("");
        mf.applyPattern(tml);
        if (locale != null) {
            mf.setLocale(locale);
        }

        return mf.format(args);
    }


    public Map<String, String> getNameMap(String lang) throws SQLException {
        if (lang == null) {
            lang = "";
        } else {
            lang = lang.toLowerCase(Locale.ROOT);
        }

        AppI18nCollection coll = nameMap.get(lang);
        if (coll == null) {
            synchronized (lang.intern()) {
                coll = nameMap.get(lang);
                if (coll == null) {
                    coll = RockClient.getServiceI18nsByLang(service, lang);
                    nameMap.put(lang, coll);
                }
            }
        }

        return coll.data;
    }

    public String getByName(String name, String lang) throws SQLException {
        return getNameMap(lang).get(name);
    }

    public String getByNameAndFormat(String name, String lang, Object[] args) throws SQLException {
        if (TextUtils.isEmpty(lang)) {
            return getByNameAndFormat(name, lang, null, args);
        } else {
            return getByNameAndFormat(name, lang, new Locale(lang), args);
        }
    }

    public String getByNameAndFormat(String name, String lang, Locale locale, Object[] args) throws SQLException {
        String tml = getByName(name, lang);

        MessageFormat mf = new MessageFormat("");
        mf.applyPattern(tml);
        if (locale != null) {
            mf.setLocale(locale);
        }

        return mf.format(args);
    }


    protected void updateCodeMap() throws SQLException {
        Map<String, AppCodeCollection> codeMap2 = new HashMap<>();

        for (Map.Entry<String, AppCodeCollection> kv : codeMap.entrySet()) {
            AppCodeCollection coll = RockClient.getServiceCodesByLang(service, kv.getKey());
            codeMap2.put(kv.getKey().toLowerCase(Locale.ROOT), coll);
        }

        codeMap = codeMap2;
    }

    protected void updateNameMap() throws SQLException {
        Map<String, AppI18nCollection> nameMap2 = new HashMap<>();

        for (Map.Entry<String, AppI18nCollection> kv : nameMap.entrySet()) {
            AppI18nCollection coll = RockClient.getServiceI18nsByLang(service, kv.getKey());
            nameMap2.put(kv.getKey().toLowerCase(Locale.ROOT), coll);
        }

        nameMap = nameMap2;
    }

    protected void update() throws SQLException {
        updateNameMap();
        if (serviceOnlyName == false) {
            updateCodeMap();
        }

        log.debug("{}: {}", service, "i18n context update succeed!");
    }
}
