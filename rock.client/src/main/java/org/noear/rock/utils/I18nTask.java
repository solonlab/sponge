package org.noear.rock.utils;

import org.noear.water.utils.TaskUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author noear 2021/2/24 created
 */
class I18nTask implements TaskUtils.ITask {
    static Logger logger = LoggerFactory.getLogger(I18nTask.class);

    @Override
    public long getInterval() {
        return 10 * 1000;
    }

    @Override
    public void exec() throws Throwable {
        I18nUtils.updateCodeMap();
        I18nUtils.updateNameMap();

        logger.debug("I18nUtils update succeed!");
    }
}
