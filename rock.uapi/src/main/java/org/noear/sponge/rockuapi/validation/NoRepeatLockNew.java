package org.noear.sponge.rockuapi.validation;

import org.noear.solon.Solon;
import org.noear.solon.extend.validation.annotation.NoRepeatLock;
import org.noear.water.utils.LockUtils;

public class NoRepeatLockNew implements NoRepeatLock {
    @Override
    public boolean tryLock(String key, int seconds) {
        return LockUtils.tryLock(Solon.cfg().appName(), key, seconds);
    }
}
