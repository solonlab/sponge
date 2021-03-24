package org.noear.rock.impl.controller.event;

import org.noear.solon.cloud.CloudEventHandler;
import org.noear.solon.cloud.annotation.CloudEvent;
import org.noear.solon.cloud.model.Event;

/**
 * @author noear 2021/3/11 created
 */
@CloudEvent("hello.test")
public class HelloTest implements CloudEventHandler {
    @Override
    public boolean handler(Event event) throws Throwable {
        return true;
    }
}
