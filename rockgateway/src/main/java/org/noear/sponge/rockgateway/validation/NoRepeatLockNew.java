package org.noear.sponge.rockgateway.validation;

import org.noear.solon.extend.validation.annotation.NoRepeatLock;
import org.noear.water.solon_plugin.WaterAdapter;
import org.noear.water.utils.LockUtils;

public class NoRepeatLockNew implements NoRepeatLock {
    @Override
    public boolean tryLock(String key, int seconds) {
        return LockUtils.tryLock(WaterAdapter.global().service_name(), key, seconds);
    }
}
