package org.noear.rock.i18n;

import org.noear.rock.RockClient;
import org.noear.rock.model.AppCodeCollection;
import org.noear.water.utils.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 代码国际化上下文（其实例必须静态化）
 *
 * @author noear 2021/2/26 created
 */
public class CodeContext {
    static Logger log = LoggerFactory.getLogger(CodeContext.class);

    Map<String, AppCodeCollection> codeMap = new HashMap<>();
    String service;

    protected CodeContext(String service) {
        this.service = service;
    }

    public Map<Integer, String> getMap(String lang) {
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
                    try {
                        coll = RockClient.getServiceCodesByLang(service, lang);
                        codeMap.put(lang, coll);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return coll.data;
    }

    public String get(int code, String lang) {
        return getMap(lang).get(code);
    }

    public String getAndFormat(int code, String lang, Object[] args) {
        if (TextUtils.isEmpty(lang)) {
            return getAndFormat(code, lang, null, args);
        } else {
            return getAndFormat(code, lang, new Locale(lang), args);
        }
    }

    public String getAndFormat(int code, String lang, Locale locale, Object[] args) {
        String tml = get(code, lang);

        MessageFormat mf = new MessageFormat("");
        mf.applyPattern(tml);
        if (locale != null) {
            mf.setLocale(locale);
        }

        return mf.format(args);
    }

    protected void update() throws SQLException {
        Map<String, AppCodeCollection> codeMap2 = new HashMap<>();

        for (Map.Entry<String, AppCodeCollection> kv : codeMap.entrySet()) {
            AppCodeCollection coll = RockClient.getServiceCodesByLang(service, kv.getKey());
            codeMap2.put(kv.getKey().toLowerCase(Locale.ROOT), coll);
        }

        codeMap = codeMap2;

        log.debug("{}: {}", service, "i18n code context update succeed!");
    }
}
