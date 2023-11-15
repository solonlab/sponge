package org.noear.sponge.admin;

import org.noear.solon.Solon;
import org.noear.solon.annotation.SolonMain;
import org.noear.sponge.admin.dso.InitPlugin;

@SolonMain
public class SpongeApp {
    public static void main(String[] args) {
        Solon.start(SpongeApp.class, args, x -> {
            x.pluginAdd(0, new InitPlugin());
        });
    }
}

