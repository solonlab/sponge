package org.noear.sponge.rock.impl;

import org.noear.solon.Solon;
import org.noear.solon.core.Aop;
import org.noear.sponge.rock.impl.controller.event.err_event;

public class App {
    public static void main(String[] args) {
        Solon.start(App.class, args)
                .onError(Aop.get(err_event.class));
    }
}
