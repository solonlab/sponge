package org.noear.sponge.rock.impl.controller;

import org.noear.water.annotation.WaterMessage;
import org.noear.water.dso.MessageHandler;
import org.noear.water.model.MessageM;

/**
 * @author noear 2021/1/29 created
 */
@WaterMessage("hello.test")
public class MessageTest implements MessageHandler {
    @Override
    public boolean handler(MessageM msg) throws Throwable {
        return true;
    }
}
