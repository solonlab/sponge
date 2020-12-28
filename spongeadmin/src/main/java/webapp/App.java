package webapp;

import org.noear.solon.Solon;
import org.noear.solon.core.handle.Context;
import webapp.dso.LogUtil;

public class App
{
    public static void main(String[] args) {
        Solon.start(App.class, args, app -> {
            //
            // 在此处初始化配置
            //
            Config.tryInit();
        }).onError((ex)->{
            Context ctx = Context.current();

            if (ctx != null) {
                LogUtil.error("XAPP", ctx.path(), ctx.paramMap().toString(),ex);
            }
        });
    }
}

