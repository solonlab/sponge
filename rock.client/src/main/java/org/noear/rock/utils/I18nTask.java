package org.noear.rock.utils;

import org.noear.mlog.Logger;
import org.noear.water.utils.TaskUtils;

/**
 * @author noear 2021/2/24 created
 */
class I18nTask implements TaskUtils.ITask {
    Logger logger = Logger.get(I18nTask.class);

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
