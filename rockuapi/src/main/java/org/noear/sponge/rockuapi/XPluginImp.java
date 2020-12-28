package org.noear.sponge.rockuapi;

import org.noear.sponge.rockuapi.validation.NoRepeatLockNew;
import org.noear.sponge.rockuapi.validation.ValidatorManagerNew;
import org.noear.sponge.rockuapi.validation.WhitelistCheckerNew;
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
