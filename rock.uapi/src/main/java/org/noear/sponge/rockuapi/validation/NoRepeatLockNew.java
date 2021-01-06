package org.noear.sponge.rockuapi.validation;

import org.noear.solon.extend.validation.annotation.NoRepeatLock;
import org.noear.water.integration.solon.WaterAdapter;
import org.noear.water.utils.LockUtils;

public class NoRepeatLockNew implements NoRepeatLock {
    @Override
    public boolean tryLock(String key, int seconds) {
        return LockUtils.tryLock(WaterAdapter.global().service_name(), key, seconds);
    }
}
