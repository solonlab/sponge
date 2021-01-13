package apidemo.dso;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.noear.sponge.rockuapi.UapiParams;
import org.noear.water.log.WaterLogger;
import apidemo.controller.UapiBase;

public class Logger {

    private static final WaterLogger log_api = new WaterLogger("bull_log_api");
    private static final WaterLogger log_api_party = new WaterLogger("bull_log_api_party");


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
                cmd.name(), String.valueOf(cmd.getUserID()), String.valueOf(ver),
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

        log_api.error(
                cmd.name(), String.valueOf(cmd.getUserID()), null != params ? String.valueOf(params.verID) : "",
                summary,
                ExceptionUtils.getFullStackTrace(e)
        );

    }


    public static void logOutput(String tag, String tag1, String tag2, String summary, String content) {

        log_api.info(
                tag, tag1, tag2,
                summary,
                content
        );

    }

    public static void logError(String tag, String tag1, String tag2, String summary, Throwable e) {

        log_api.error(
                tag, tag1, tag2,
                summary,
                ExceptionUtils.getFullStackTrace(e)
        );
    }

    public static void logError(String tag, String tag1, String tag2, String summary, String content) {

        log_api.error(
                tag, tag1, tag2,
                summary,
                content
        );

    }

    public static void logPartyOutput(String tag, String tag1, String tag2, String summary, String content) {

        log_api_party.info(
                tag, tag1, tag2,
                summary,
                content
        );

    }

    public static void logPartyError(String tag, String tag1, String tag2, String summary, Throwable e) {

        log_api_party.error(
                tag, tag1, tag2,
                summary,
                ExceptionUtils.getFullStackTrace(e)
        );
    }

    public static void logPartyError(String tag, String tag1, String tag2, String summary, String content) {

        log_api_party.error(
                tag, tag1, tag2,
                summary,
                content
        );

    }
}
