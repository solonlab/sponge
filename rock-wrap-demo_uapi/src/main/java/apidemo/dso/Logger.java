package apidemo.dso;

import org.noear.solon.cloud.CloudLogger;
import org.noear.solon.cloud.utils.Tags;
import org.noear.sponge.rockuapi.UapiParams;
import apidemo.controller.UapiBase;

public class Logger {

    private static final CloudLogger log_api =  CloudLogger.get("bull_log_api");
    private static final CloudLogger log_api_party =  CloudLogger.get("bull_log_api_party");


    /**
     * 日志拦截器中使用
     * @param cmd
     * @param params
     * @param result
     */
    public static void logOutput(UapiBase cmd, UapiParams params, String result) {

        String summary = (null == params) ? "" : params.org_param;
        if (null == summary) {
            summary = "";
        }
        if (summary.length() > 900) {
            summary = summary.substring(0, 900);
        }

        String content = (null == result) ? "" : result.toString();

        int ver = 0;
        if (null != params) {
            ver = params.verID;
        }

        log_api.info(
                Tags.tag0(cmd.name()).tag1(String.valueOf(cmd.getUserID())).tag2(String.valueOf(ver)),
                "{}\n\n{}",
                summary,
                content
        );
    }

    /**
     * 日志拦截器中使用
     * @param cmd
     * @param params
     * @param e
     */
    public static void logError(UapiBase cmd, UapiParams params, Throwable e) {

        String summary = null != params ? params.org_param : "";
        String tag2 = null != params ? String.valueOf(params.verID) : "";

        log_api.error(
                Tags.tag0(cmd.name()).tag1(String.valueOf(cmd.getUserID())).tag2(tag2),
                "{}\n\n{}",
                summary,
                e
        );

    }


    public static void logOutput(String tag, String tag1, String tag2, String summary, String content) {

        log_api.info(
                Tags.tag0(tag).tag1(tag1).tag2(tag2),
                "{}\n\n{}",
                summary,
                content
        );

    }

    public static void logError(String tag, String tag1, String tag2, String summary, Throwable e) {

        log_api.error(
                Tags.tag0(tag).tag1(tag1).tag2(tag2),
                "{}\n\n{}",
                summary,
                e
        );
    }

    public static void logError(String tag, String tag1, String tag2, String summary, String content) {

        log_api.error(
                Tags.tag0(tag).tag1(tag1).tag2(tag2),
                "{}\n\n{}",
                summary,
                content
        );

    }

    public static void logPartyOutput(String tag, String tag1, String tag2, String summary, String content) {

        log_api_party.info(
                Tags.tag0(tag).tag1(tag1).tag2(tag2),
                "{}\n\n{}",
                summary,
                content
        );

    }

    public static void logPartyError(String tag, String tag1, String tag2, String summary, Throwable e) {

        log_api_party.error(
                Tags.tag0(tag).tag1(tag1).tag2(tag2),
                "{}\n\n{}",
                summary,
                e
        );
    }

    public static void logPartyError(String tag, String tag1, String tag2, String summary, String content) {

        log_api_party.error(
                Tags.tag0(tag).tag1(tag1).tag2(tag2),
                "{}\n\n{}",
                summary,
                content
        );

    }
}
