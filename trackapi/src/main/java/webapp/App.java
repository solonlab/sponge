package webapp;

import org.noear.solon.Solon;
import org.noear.solon.core.handle.Context;
import webapp.track.controller.UrlHandler;
import webapp.track.dso.LogUtil;

public class App {
    public static void main(String[] args) {
        Solon.start(App.class, args, (app) -> {
            app.all("/*", new UrlHandler());

            Config.tryInit();
        }).onError((ex) -> {
            Context ctx = Context.current();

            if (ctx != null) {
                LogUtil.error(ctx.path(), "", "", ctx.paramMap().toString(), ex);
            }
        });
    }
}
