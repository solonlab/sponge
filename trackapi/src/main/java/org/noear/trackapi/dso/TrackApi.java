package org.noear.trackapi.dso;

import org.noear.solon.cloud.CloudClient;
import org.noear.solon.core.handle.Context;
import org.noear.trackapi.Config;
import org.noear.trackapi.dso.db_sponge_track.DbTrackApi;
import org.noear.trackapi.model.CodeIpModel;
import org.noear.trackapi.model.CodeUaModel;
import org.noear.trackapi.model.ShortUrlModel;
import org.noear.water.utils.Datetime;
import org.noear.water.utils.TextUtils;
import org.noear.weed.DataItem;
import org.noear.weed.DbContext;

import java.sql.SQLException;

public class TrackApi {
    public static DbContext db() {
        return Config.sponge_track;
    }

    public static void addUrlLog(ShortUrlModel url, HttpRequestX requestX) throws SQLException {
        long log_id = CloudClient.id().generate(); //IDUtilEx.buildLogID("short_redirect_log");

        String ua = requestX.getUserAgent();
        String ip = requestX.getUserIP();


        String user_key = null;
        if (TextUtils.isEmpty(url.user_field) == false) {
            user_key = requestX.getParameter(url.user_field, true);

            if (TextUtils.isEmpty(user_key)) {
                user_key = requestX.getHeader(url.user_field);
            }
        }

        if (TextUtils.isEmpty(user_key)) {
            user_key = requestX.getCookie(Config.uv_cookie_key);

            if (TextUtils.isEmpty(user_key)) {
                user_key = "_" + IDUtilEx.buildGuid();//生成的用_开头，可直接识别是不是uk
            }
        }

        if (user_key != null && user_key.length() > 40) {
            //记录可疑的长user_key

            StringBuilder sb = new StringBuilder();

            sb.append(user_key).append("\r\n\r\n");

            if (ua != null) {
                sb.append("ua=").append(ua);
            } else {
                sb.append("ua=null");
            }


            LogUtil.debug("user_key", url.tag_id + "/" + url.url_id, url.url_name, Context.current().uri().toString(), sb.toString());

            user_key = user_key.substring(0, 40);
        }

        requestX.setCookie(Config.uv_cookie_key, user_key); //每次都更新cookie，将uk更新到cookie里可达到用uk统一标识

        String[] pp = url.track_params.split(","); ////c,b,d

        CodeUaModel cUA = DbTrackApi.getCodeUA(ua);
        CodeIpModel cIP = DbTrackApi.getCodeIP(ip);

        String referer_url = requestX.getReferer();

        if (referer_url != null && referer_url.length() > 250) {
            referer_url = referer_url.substring(0, 250);
        }

        Datetime time = Datetime.Now();

        doAddUrlLog2(url, requestX, user_key, log_id, cIP, cUA, time, pp, referer_url);
    }



    private static void doAddUrlLog2(ShortUrlModel url, HttpRequestX requestX, String user_key, long log_id, CodeIpModel cIP, CodeUaModel cUA, Datetime time, String[] pp, String referer_url) throws SQLException {
        DataItem dm = new DataItem();

        dm.set("log_id", log_id)
                .set("url_id", url.url_id)
                .set("tag_id", url.tag_id)
                .set("user_key", user_key)
                .set("admin_group", url.admin_group)
                .set("log_ip_id", cIP.ip_id)
                .set("log_city_code", (cIP.city_code / 10000) * 10000)
                .set("log_ua_id", cUA.ua_id)
                .set("referer_url", referer_url)
                .set("log_date", time.getDate())
                .set("log_hour", time.getHours())
                .set("log_fulltime", time.getFulltime());

        if (pp.length > 0) {
            dm.set("v1", requestX.getParameter(pp[0], true));
        } else {
            dm.set("v1", "");
        }

        if (pp.length > 1) {
            dm.set("v2", requestX.getParameter(pp[1], true));
        } else {
            dm.set("v2", "");
        }

        if (pp.length > 2) {
            dm.set("v3", requestX.getParameter(pp[2], true));
        } else {
            dm.set("v3", "");
        }

        if (pp.length > 3) {
            dm.set("v4", requestX.getParameter(pp[3], true));
        } else {
            dm.set("v4", "");
        }

        if (pp.length > 4) {
            dm.set("v5", requestX.getParameter(pp[4], true));
        } else {
            dm.set("v5", "");
        }

        TrackPipeline.singleton().add(dm);
    }
}
