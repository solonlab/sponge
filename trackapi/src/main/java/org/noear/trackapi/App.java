package org.noear.trackapi;

import org.noear.solon.Solon;

public class App {
    public static void main(String[] args) {
        Solon.start(App.class, args, (app) -> {
            Config.tryInit();
        });
    }
}
