package org.noear.trackapi.dso.db_sponge_track;

import org.noear.trackapi.Config;
import org.noear.trackapi.dso.IDUtilEx;
import org.noear.trackapi.model.CodeIpModel;
import org.noear.trackapi.model.CodeUaModel;
import org.noear.trackapi.model.ShortUrlModel;
import org.noear.trackapi.utils.EncryptUtil;
import org.noear.water.utils.LockUtils;
import org.noear.water.utils.TextUtils;
import org.noear.weed.DataItem;
import org.noear.weed.DbContext;


import java.sql.SQLException;
import java.util.List;

public class DbTrackApi {
    public static DbContext db() {
        return Config.sponge_track;
    }

    public static ShortUrlModel getShortUrl(String url_key) throws SQLException {
        return db().table("short_url")
                .where("url_key=?", url_key)
                .limit(1)
                .select("*")
                .caching(Config.cache)
                .cacheTag("track_url_key_" + url_key)//有缓存控制
                .getItem(new ShortUrlModel());
    }


    //==============================
    private static final CodeUaModel _emptyUa = new CodeUaModel();

    public static CodeUaModel getCodeUA(String ua) throws SQLException {
        if (TextUtils.isEmpty(ua)) {
            return _emptyUa;
        }

        if (ua.length() > 600) {
            ua = ua.substring(0, 600);
        }

        String ua_key = EncryptUtil.md5(ua);


        CodeUaModel codeUa = db().table("code_ua")
                .where("ua_key=?", ua_key)
                .limit(1)
                .select("ua_id,platform,client")
                .caching(Config.cache)
                .getItem(new CodeUaModel(), (cu, v) -> {
                    if (v.ua_id == 0) {
                        cu.usingCache(false);
                    }
                });

        if (codeUa.ua_id == 0) {
            if (LockUtils.tryLock(Config.water_service_name, "_ua_" + ua_key) == false) {
                return _emptyUa; //如果不是第一次，则不插入
            }

            codeUa.ua_id = IDUtilEx.buildUaID();

            db().table("code_ua")
                    .set("ua_id", codeUa.ua_id)
                    .set("ua_key", ua_key)
                    .set("ua_val", ua)
                    .insert();
        }

        return codeUa;
    }

    private static final CodeIpModel _emptyIp = new CodeIpModel();

    public static CodeIpModel getCodeIP(String ip) throws SQLException {
        if (TextUtils.isEmpty(ip)) {
            return _emptyIp;
        }

        if (ip.length() > 80) {
            ip = ip.substring(0, 80);
        }

        CodeIpModel codeIp = db().table("code_ip")
                .where("ip_val=?", ip)
                .limit(1)
                .select("ip_id,city_code")
                .caching(Config.cache)
                .getItem(new CodeIpModel(), (cu, v) -> {
                    if (v.ip_id == 0) {
                        cu.usingCache(false);
                    }
                });

        if (codeIp.ip_id == 0) {
            if (LockUtils.tryLock(Config.water_service_name, "_ip_" + ip) == false) {
                return _emptyIp; //如果不是第一次，则不插入
            }

            codeIp.ip_id = IDUtilEx.buildIpID();

            db().table("code_ip")
                    .set("ip_id", codeIp.ip_id)
                    .set("ip_val", ip)
                    .insert();
        }

        return codeIp;
    }

//    public static void addUrlLog(String table, DataItem item) throws SQLException {
//        db().table(table).insert(item);
//    }

    public static void addUrlLogAll(String table, List<DataItem> list) throws SQLException {
        db().table(table).insertList(list);
    }
}
