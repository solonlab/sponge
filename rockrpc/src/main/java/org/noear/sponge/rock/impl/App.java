package org.noear.sponge.rock.impl;

import org.noear.solon.Solon;

public class App {
    public static void main(String[] args) {
        Solon.start(App.class, args, app ->
                app.enableSocketD(true)
                        .enableSafeStop(app.cfg().isFilesMode() == false));
    }
}
