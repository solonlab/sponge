package org.noear.rock.utils;

import org.noear.rock.RockClient;
import org.noear.rock.model.AppI18nCollection;
import org.noear.water.utils.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 消息国际化上下文（其实例必须静态化）
 *
 * @author noear 2021/2/26 created
 */
public class MessageContext {
    static Logger log = LoggerFactory.getLogger(MessageContext.class);

    Map<String, AppI18nCollection> nameMap = new HashMap<>();
    String service;

    protected MessageContext(String bundleName) {
        this.service = bundleName;
    }

    public Map<String, String> getMap(String lang) {
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
                    try {
                        coll = RockClient.getServiceI18nsByLang(service, lang);
                        nameMap.put(lang, coll);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return coll.data;
    }

    public String get(String name, String lang) {
        return getMap(lang).get(name);
    }

    public String getAndFormat(String name, String lang, Object[] args) {
        if (TextUtils.isEmpty(lang)) {
            return getAndFormat(name, lang, null, args);
        } else {
            return getAndFormat(name, lang, new Locale(lang), args);
        }
    }

    public String getAndFormat(String name, String lang, Locale locale, Object[] args) {
        String tml = get(name, lang);

        MessageFormat mf = new MessageFormat("");
        mf.applyPattern(tml);
        if (locale != null) {
            mf.setLocale(locale);
        }

        return mf.format(args);
    }


    protected void update() throws SQLException {
        Map<String, AppI18nCollection> nameMap2 = new HashMap<>();

        for (Map.Entry<String, AppI18nCollection> kv : nameMap.entrySet()) {
            AppI18nCollection coll = RockClient.getServiceI18nsByLang(service, kv.getKey());
            nameMap2.put(kv.getKey().toLowerCase(Locale.ROOT), coll);
        }

        nameMap = nameMap2;

        log.debug("{}: {}", service, "i18n message context update succeed!");
    }
}
