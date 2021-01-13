package org.noear.sponge.rockuapi.interceptor;

import org.noear.sponge.rockuapi.UapiParams;
import org.noear.snack.ONode;
import org.noear.sponge.rockuapi.decoder.RockDecoder;
import org.noear.sponge.rockuapi.decoder.RockDefDecoder;
import org.noear.solon.Utils;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;

/**
 * 参数构建拦截器（将输出内容构建为统一的参数模型）
 *
 * 对cmd 有用；对api 没用（api 不要使用了）
 *
 * 支持1:
 *  form : p, k
 * 支持2:
 *  header: Authorization (相当于 form:k)
 *  body: (content type: application/json)（相当于 form:p）
 * */
public class ParamsBuildInterceptor implements Handler {

    private RockDecoder _decoder;


    public ParamsBuildInterceptor() {
        _decoder = new RockDefDecoder();
    }

    public ParamsBuildInterceptor(RockDecoder decoder) {
        if (decoder == null) {
            _decoder = new RockDefDecoder();
        } else {
            _decoder = decoder;
        }
    }

    @Override
    public void handle(Context ctx) throws Throwable {
        /** 如果已处理，不再执行 */
        if (ctx.getHandled()) {
            return;
        }


        /** 处理CMD风格的参数 */
        UapiParams params = new UapiParams(ctx);
        ctx.attrSet(Attrs.params, params);

        //1.获取参数与令牌
        //
        params.org_param = ctx.param("p"); //参数
        params.org_token = ctx.param("k"); //令牌

        if (params.org_token == null) {
            //支持 header 传
            params.org_token = ctx.header("Authorization"); //令牌
        }

        if (params.org_param == null) {
            //支持 body 传
            String ct = ctx.contentType();
            if (ct != null && ct.indexOf("/json") > 0) { //参数
                params.org_param = ctx.body();
            }
        }

        //2.尝试解析令牌
        //
        if (!Utils.isEmpty(params.org_token)) {
            //
            //token:{appid}.{verid}.{sgin}
            //
            String[] token = params.org_token.split("\\.");

            if (token.length >= 3) {
                params.appID = Integer.parseInt(token[0]);
                params.verID = Integer.parseInt(token[1]);
                params.sgin = token[2];
            }

            ctx.attr("org_token", params.org_token);
        }

        //3.尝试解析参数（涉及解码器）
        //
        if (!Utils.isEmpty(params.org_param)) {
            //尝试解码
            //
            params.org_param = _decoder.tryDecode(ctx, params.getApp(), params.org_param);

            //解析数据
            //
            ONode tmp = ONode.load(params.org_param);

            if (tmp.isObject()) {
                //转到上下文参数
                //
                tmp.obj().forEach((k, v) -> {
                    if (v.isValue()) {
                        ctx.paramSet(k, v.getString());
                    }
                });
            }

            if (ctx.paramMap().containsKey("appID")) {
                ctx.paramSet("appID", params.appID + "");
            }

            if (ctx.paramMap().containsKey("verID")) {
                ctx.paramSet("verID", params.verID + "");
            }

            ctx.attr("org_param", params.org_param);
        }
    }
}