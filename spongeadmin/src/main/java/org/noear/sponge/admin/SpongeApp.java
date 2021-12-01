package org.noear.sponge.admin;

import org.noear.solon.Solon;
import org.noear.sponge.admin.dso.ErrorListener;
import org.noear.sponge.admin.dso.InitPlugin;

public class SpongeApp {
    public static void main(String[] args) {
        Solon.start(SpongeApp.class, args, x -> {
            x.enableErrorAutoprint(false);
            x.onError(new ErrorListener());
            x.pluginAdd(0, new InitPlugin());
        });
    }
}

