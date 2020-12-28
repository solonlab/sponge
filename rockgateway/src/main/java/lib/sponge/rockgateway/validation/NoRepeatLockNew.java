package lib.sponge.rockgateway.validation;

import org.noear.solon.extend.validation.annotation.NoRepeatLock;
import org.noear.water.solon_plugin.XWaterAdapter;
import org.noear.water.utils.LockUtils;

public class NoRepeatLockNew implements NoRepeatLock {
    @Override
    public boolean tryLock(String key, int seconds) {
        return LockUtils.tryLock(XWaterAdapter.global().service_name(), key, seconds);
    }
}
