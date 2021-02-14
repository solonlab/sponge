package org.noear.sponge.rockuapi;

import org.noear.snack.ONode;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Gateway;
import org.noear.solon.core.handle.ModelAndView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class UapiGateway extends Gateway {

    /**
     * 应用组ID
     */
    public int agroup_id() {
        return 0;
    }

    /**
     * 语言
     */
    public String g_lang(Context c) {
        return c.param("g_lang");
    }

    @Override
    public void render(Object obj, Context c) throws Throwable {
        if (c.getRendered()) {
            return;
        }

        c.setRendered(true);

        //
        // 有可能根本没数据过来
        //
        if (obj instanceof ModelAndView) {
            //如果有模板，则直接渲染
            //
            c.result = obj;
            c.render(obj);
        } else {
            //如果没有按Result tyle 渲染
            //
            if (obj instanceof UapiCode) {
                //处理标准的状态码
                UapiCode err = (UapiCode) obj;
                Map<String, Object> map = new HashMap<>();

                map.put("code", err.getCode());
                map.put("msg", UapiCodes.CODE_txt(agroup_id(), g_lang(c), err));

                c.result = map;
            } else if (obj instanceof Throwable) {
                //处理未知异常
                Map<String, Object> map = new HashMap<>();

                map.put("code", 0);
                map.put("msg", UapiCodes.CODE_txt(agroup_id(), g_lang(c), UapiCodes.CODE_400));

                c.result = map;
            } else if (obj instanceof ONode) {
                //处理ONode数据（为兼容旧的）
                ONode tmp = new ONode();

                tmp.set("code", 1);
                tmp.set("msg", "");
                tmp.setNode("data", (ONode) obj);

                c.result = tmp;
            } else {
                //处理java bean数据（为扩展新的）
                Map<String, Object> map = new LinkedHashMap<>();

                map.put("code", 1);
                map.put("msg", "");

                if (obj != null) {
                    map.put("data", obj);
                }

                c.result = map;
            }
        }
    }
}
