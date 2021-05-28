package org.noear.sponge.admin;

import org.noear.snack.ONode;
import org.noear.solon.Solon;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Init;
import org.noear.solon.core.handle.Context;
import org.noear.solon.extend.auth.AuthAdapter;
import org.noear.solon.extend.validation.ValidatorManager;
import org.noear.sponge.admin.dso.auth.AuthProcessorBcfImpl;
import org.noear.water.model.ConfigM;
import org.noear.weed.DbContext;
import org.noear.water.*;

@Configuration
public class Config {
    public static String track_uri() {
        return cfg("track_uri").value;
    }

    public static DbContext sponge_track = cfg("sponge_track").getDb(true);
    public static DbContext sponge_rock = cfg("sponge_rock").getDb(true);

    public static final String push_suffix = "_push";

    //是否使用标答检查器？
    public static boolean is_use_tag_checker() {
        return "1".equals(cfg("water", "enable_tag_checker").getString());
    }


    //================================
    //
    //获取一个数据库配置
    public static ConfigM cfg(String key) {
        return cfg(Solon.cfg().appGroup(), key);
    }

    public static ConfigM cfg(String group, String key) {
        return WaterClient.Config.get(group, key);
    }

    @Init
    public void init() {
        AuthAdapter.global()
                .loginUrl("/login")
                .loginProcessingUrl("/login/ajax/check")
                .logoutUrl("/login")
                .usernameParam("userName")
                .passwordParam("passWord")
                .authUrlMatchers(url -> {
                    if (url.contains("@")) {
                        return false;
                    }

                    if (url.startsWith("/rock/") || url.startsWith("/track/")) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .authProcessor(new AuthProcessorBcfImpl())
                .authOnFailure((ctx, rst) -> {
                    ctx.outputAsJson(new ONode().set("code", 403).set("msg", "没有权限").toJson());
                });
    }
}