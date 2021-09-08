package org.noear.rock.i18n;

import org.noear.water.utils.TaskUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author noear 2021/9/8 created
 */
public class I18nContextManager {
    Map<String, CodeContext> codeLib = new HashMap<>();
    Map<String, MessageContext> messageLib = new HashMap<>();

    private static final I18nContextManager instance = new I18nContextManager();

    private I18nContextManager() {
        TaskUtils.run(30 * 1000, 30 * 1000, this::update);
    }

    private void update() throws SQLException {
        for (CodeContext c : codeLib.values()) {
            c.update();
        }

        for (MessageContext c : messageLib.values()) {
            c.update();
        }
    }


    /**
     * 获取代码国际化上下文
     */
    public static CodeContext getCodeContext(String service) {
        CodeContext tmp = instance.codeLib.get(service);
        if (tmp == null) {
            synchronized (service.intern()) {
                tmp = instance.codeLib.get(service);
                if (tmp == null) {
                    tmp = new CodeContext(service);
                    instance.codeLib.put(service, tmp);
                }
            }
        }

        return tmp;
    }

    /**
     * 获取名字国际化上下文
     */
    public static MessageContext getMessageContext(String bundleName) {
        MessageContext tmp = instance.messageLib.get(bundleName);
        if (tmp == null) {
            synchronized (bundleName.intern()) {
                tmp = instance.messageLib.get(bundleName);
                if (tmp == null) {
                    tmp = new MessageContext(bundleName);
                    instance.messageLib.put(bundleName, tmp);
                }
            }
        }

        return tmp;
    }
}
