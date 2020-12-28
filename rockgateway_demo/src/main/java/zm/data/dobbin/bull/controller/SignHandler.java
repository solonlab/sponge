package zm.data.dobbin.bull.controller;

import org.noear.snack.ONode;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.sponge.rock.RockClient;
import org.noear.sponge.rock.models.AppModel;
import org.noear.water.WaterClient;
import org.noear.water.solon_plugin.IPUtils;
import org.noear.water.utils.EncryptUtils;

@Controller
public class SignHandler {

    @Mapping("/SIGN/{cmd}")
    public void handle(Context cxt, String cmd) throws Exception {

        String ip = IPUtils.getIP(cxt);

        if (!WaterClient.Whitelist.existsOfServerIp(ip)) {
            cxt.statusSet(403);
            cxt.setHandled(true);
            return;
        }

        cxt.charset("UTF-8");

        String arg = cxt.param("p");//访问参数
        String cid = cxt.param("cid");//访问参数

        ONode n = new ONode();

        try {
            AppModel app = RockClient.getApp(Integer.parseInt(cid));

            String sign = buildSign(app, cmd, arg);

            n.set("sign", sign);
            String r = EncryptUtils.aesEncrypt(arg, app.app_key, null);
            n.set("p64", r);

            cxt.outputAsJson(n.toJson());
        } catch (Exception ex) {
            n.set("error", ex.getMessage());
            cxt.outputAsJson(n.toJson());
        }
    }

    private String buildSign(AppModel app, String cmd, String params) {
        String key = app.app_key;

        StringBuilder sb = new StringBuilder();

        sb.append(cmd).append("#").append(params).append("#").append(key);

        return EncryptUtils.sha256(sb.toString());
    }
}
