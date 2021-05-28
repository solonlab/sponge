package org.noear.sponge.admin;

import org.noear.snack.ONode;
import org.noear.solon.Solon;
import org.noear.solon.SolonApp;
import org.noear.solon.Utils;
import org.noear.solon.cloud.CloudClient;
import org.noear.solon.core.Plugin;
import org.noear.solon.core.handle.Context;
import org.noear.solon.logging.utils.TagsMDC;
import org.noear.water.WW;
import org.noear.water.WaterClient;
import org.noear.water.utils.TextUtils;
import org.noear.weed.WeedConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author noear 2021/5/14 created
 */
public class InitPlugin implements Plugin {
    static Logger log = LoggerFactory.getLogger(InitPlugin.class);

    boolean isDebugMode;
    boolean isWeedStyle2;
    boolean isTrackEnable;
    boolean isErrorLogEnable;

    @Override
    public void start(SolonApp app) {
        Utils.loadClass("com.mysql.jdbc.Driver");
        Utils.loadClass("com.mysql.cj.jdbc.Driver");


        isDebugMode = Solon.cfg().isDebugMode() || Solon.cfg().isFilesMode();

        String style = Solon.cfg().get("srww.weed.print.style");
        isWeedStyle2 = "sql".equals(style);
        isTrackEnable = Solon.cfg().getBool("srww.weed.track.enable", true);
        isErrorLogEnable = Solon.cfg().getBool("srww.weed.error.log.enable", true);


        initWeed();
    }


    /**
     * 初始化Weed监听事件
     */
    protected void initWeed() {
        Class<?> bcfClz = Utils.loadClass(WW.clz_BcfClient);

        if (bcfClz == null) {
            initWeedForApi();
        } else {
            initWeedForAdmin();
        }


        WeedConfig.onException((cmd, err) -> {
            TagsMDC.tag0("weed");

            if (isErrorLogEnable) {
                if (cmd == null) {
                    log.error("::Error= {}", err);
                } else {
                    log.error("::Sql= {}\n::Args= {}\n\n::Error= {}", cmd.text, ONode.stringify(cmd.paramMap()), err);
                }
            } else {
                if (cmd == null) {
                    log.debug("::Error= {}", err);
                } else {
                    log.debug("::Sql= {}\n::Args= {}\n\n::Error= {}", cmd.text, ONode.stringify(cmd.paramMap()), err);
                }
            }
        });
    }

    private void initWeedForApi() {
        //api项目
        WeedConfig.onExecuteAft(cmd -> {
            if (isDebugMode) {
                if (isWeedStyle2) {
                    log.debug(cmd.toSqlString());
                } else {
                    log.debug(cmd.text + "\r\n" + ONode.stringify(cmd.paramMap()));
                }
            }

            WaterClient.Track.track(service_name(), cmd, 1000);

            if (isTrackEnable) {
                String tag = cmd.context.schema();
                if (TextUtils.isEmpty(tag)) {
                    tag = "sql";
                }

                CloudClient.metric().addMeter(service_name() + "_sql", tag, cmd.text, cmd.timespan());
            }
        });
    }

    private void initWeedForAdmin() {
        //admin 项目
        WeedConfig.onExecuteAft((cmd) -> {
            if (isDebugMode) {
                if (isWeedStyle2) {
                    log.debug(cmd.toSqlString());
                } else {
                    log.debug(cmd.text + "\r\n" + ONode.stringify(cmd.paramMap()));
                }
            }

            if (cmd.isLog < 0) {
                return;
            }

            Context ctx = Context.current();
            String user_name = user_name(ctx);
            int user_puid = user_puid(ctx);

//            if (user_name == null) {
//                return;
//            }


            String sqlUp = cmd.text.toUpperCase();
            String chkUp = "User_Id=? AND Pass_Wd=? AND Is_Disabled=0".toUpperCase();

            if (cmd.timespan() > 2000 || cmd.isLog > 0 || sqlUp.indexOf("INSERT INTO ") >= 0 || sqlUp.indexOf("UPDATE ") >= 0 || sqlUp.indexOf("DELETE ") >= 0 || sqlUp.indexOf(chkUp) >= 0) {
                WaterClient.Track.track(service_name(), cmd, ctx.userAgent(), ctx.pathNew(), user_puid + "." + user_name, ctx.realIp());
            }

            if (isTrackEnable) {
                String tag = cmd.context.schema();
                if (TextUtils.isEmpty(tag)) {
                    tag = "sql";
                }

                CloudClient.metric().addMeter(service_name() + "_sql", tag, cmd.text, cmd.timespan());
            }
        });
    }

    public String service_name() {
        return Solon.cfg().appName();
    }

    //用于作行为记录
    public int user_puid(Context ctx) {
        if (ctx != null) {
            String tmp = ctx.attr("user_puid", "0");
            return Integer.parseInt(tmp);
        } else {
            return 0;
        }
    }

    public String user_name(Context ctx) {
        if (ctx != null) {
            return ctx.attr("user_name", null);
        } else {
            return null;
        }
    }
}
