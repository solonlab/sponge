package org.noear.trackapi;

import org.noear.solon.Solon;
import org.noear.solon.core.handle.Context;
import org.noear.trackapi.dso.LogUtil;

public class App {
    public static void main(String[] args) {
        Solon.start(App.class, args, (app) -> {
            Config.tryInit();
        });
    }
}
