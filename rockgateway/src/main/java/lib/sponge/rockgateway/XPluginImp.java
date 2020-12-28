package lib.sponge.rockgateway;

import lib.sponge.rockgateway.validation.NoRepeatLockNew;
import lib.sponge.rockgateway.validation.ValidatorManagerNew;
import lib.sponge.rockgateway.validation.WhitelistCheckerNew;
import org.noear.solon.SolonApp;
import org.noear.solon.core.Plugin;
import org.noear.solon.extend.validation.ValidatorManager;

public class XPluginImp implements Plugin {
    @Override
    public void start(SolonApp app) {
        ValidatorManager.globalSet(new ValidatorManagerNew());

        ValidatorManager.setNoRepeatLock(new NoRepeatLockNew());
        ValidatorManager.setWhitelistChecker(new WhitelistCheckerNew());
    }
}
