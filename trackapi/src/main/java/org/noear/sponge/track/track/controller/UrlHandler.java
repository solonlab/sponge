package org.noear.sponge.track.track.controller;

import org.noear.snack.ONode;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.sponge.track.track.dso.HttpRequestX;
import org.noear.sponge.track.track.dso.LogUtil;
import org.noear.sponge.track.track.dso.TrackApi;
import org.noear.sponge.track.track.dso.db_sponge_track.DbTrackApi;
import org.noear.sponge.track.track.models.ShortUrlModel;
import org.noear.water.utils.TextUtils;

@Controller
public class UrlHandler{

    @Mapping("/{key}")
    public void handle(Context cxt, String key) {
        try {
            ShortUrlModel url = DbTrackApi.getShortUrl(key);

            cmd_exec(cxt, url);
        } catch (Exception ex) {
            String txt = new ONode()
                    .set("code", 0)
                    .set("msg", ex.getMessage())
                    .toJson();

            cxt.outputAsJson(txt);
        }
    }

    private void cmd_exec(Context cxt, ShortUrlModel url) throws Exception {

        if (TextUtils.isEmpty(url.url_key) || url.is_disable == 1) {
            cxt.status(404);
        } else {
            HttpRequestX requestX = new HttpRequestX(cxt);

            //1.记录日志
            try {
                TrackApi.addUrlLog(url, requestX);
            } catch (Exception ex) {
                LogUtil.error("API_url_log", url.tag_id + "/" + url.url_id, url.url_name, Context.current().uri().toString(), ex);
            }

            //2.1.构建透传统数 //可能没有值
            String target_url = null;
            if (url.isRequireBuildUrl()) {
                target_url = buildUrlByGet(requestX, url);
            } else {
                target_url = url.url_val;
            }

            //2.2.跳转（如果有目标地址） //如果没有地址，只用于自身的跟踪
            if (TextUtils.isEmpty(target_url) == false && target_url.indexOf("://") > 0) {
                cxt.redirect(target_url);
            } else {
                String txt = new ONode()
                        .set("code", 1)
                        .set("msg", "Succeed.")
                        .toJson();

                cxt.outputAsJson(txt);
            }
        }
    }

    //构建跳转用的get-url //用于后面跳转
    private String buildUrlByGet(HttpRequestX requestX ,ShortUrlModel url){
        StringBuilder sb = new StringBuilder();

        sb.append(url.url_val);

        for (String kmap : url.trans_params.split(",")) { //x=c,y=d

            if (kmap.indexOf("=") > 0) {
                //1.2.尝试做map处理
                //
                String tk = kmap.split("=")[0];
                String vk = kmap.split("=")[1];

                String vVal = requestX.getParameter(vk, true);

                if (TextUtils.isEmpty(vVal) == false) {
                    if (sb.indexOf("?") > 0) {
                        sb.append("&");
                    }
                    else {
                        sb.append("?");
                    }

                    sb.append(tk).append("=").append(vVal);
                }
            }
        }

        return sb.toString();
    }
}
