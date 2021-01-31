package org.noear.sponge.rock.impl.controller;

import org.noear.solon.cloud.CloudEventHandler;
import org.noear.solon.cloud.annotation.CloudEvent;
import org.noear.solon.cloud.model.Event;

/**
 * @author noear 2021/1/29 created
 */
@CloudEvent("hello.test")
public class MessageTest implements CloudEventHandler {

    @Override
    public boolean handler(Event event) throws Throwable {
        return true;
    }
}
