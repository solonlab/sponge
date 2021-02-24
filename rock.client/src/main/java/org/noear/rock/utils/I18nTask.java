package org.noear.rock.utils;

import org.noear.water.utils.TaskUtils;

/**
 * @author noear 2021/2/24 created
 */
public class I18nTask implements TaskUtils.ITask {
    @Override
    public long getInterval() {
        return 10 * 1000;
    }

    @Override
    public void exec() throws Throwable {

    }
}
