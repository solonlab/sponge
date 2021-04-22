package org.noear.rock.impl.controller.msg;

import org.noear.solon.cloud.CloudEventHandler;
import org.noear.solon.cloud.annotation.CloudEvent;
import org.noear.solon.cloud.model.Event;

/**
 * @author noear 2021/4/22 created
 */
@CloudEvent("hello.test")
public class MSg_test_hello implements CloudEventHandler {
    @Override
    public boolean handler(Event event) throws Throwable {
        return false;
    }
}
