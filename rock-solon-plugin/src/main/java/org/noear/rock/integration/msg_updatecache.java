package org.noear.rock.integration;

import org.noear.rock.i18n.I18nContextManager;
import org.noear.solon.Utils;
import org.noear.solon.cloud.CloudEventHandler;
import org.noear.solon.cloud.annotation.CloudEvent;
import org.noear.solon.cloud.annotation.EventLevel;
import org.noear.solon.cloud.model.Event;

/**
 * @author noear
 */
@CloudEvent(topic = "water.cache.update", level = EventLevel.instance)
public class msg_updatecache implements CloudEventHandler {
    @Override
    public boolean handle(Event event) throws Throwable {
        if (event.content().contains("app_")) {
            for (String tag : event.content().split(";")) {
                if (Utils.isEmpty(tag) == false) {
                    handlerDo(tag);
                }
            }
        }

        return true;
    }

    private void handlerDo(String tag) throws Exception {
        if (tag.indexOf(":") > 0 && tag.contains("app_")) {
            String[] ss = tag.split(":");

            if ("app_i18n".equals(ss[0])) {
                I18nContextManager.getInstance().updateMessageContext(ss[1]);
                return;
            }

            if ("app_code".equals(ss[0])) {
                I18nContextManager.getInstance().updateCodeContext(ss[1]);
                return;
            }
        }
    }
}
