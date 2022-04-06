package org.noear.rock.impl.controller.msg;

import org.noear.solon.cloud.CloudEventHandler;
import org.noear.solon.cloud.annotation.CloudEvent;
import org.noear.solon.cloud.model.Event;

/**
 * @author noear 2021/4/22 created
 */
@CloudEvent(value = "hello.test2", group = "rock")
public class MSg_hello_test2 implements CloudEventHandler {
    @Override
    public boolean handle(Event event) throws Throwable {
        throw new RuntimeException("出错测试");
    }
}
