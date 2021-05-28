package org.noear.sponge.admin.dso.auth;

import org.noear.bcf.BcfClient;
import org.noear.solon.extend.auth.AuthProcessor;
import org.noear.solon.extend.auth.annotation.Logical;
import org.noear.sponge.admin.dso.Session;

/**
 * @author noear 2021/5/28 created
 */
public class AuthProcessorBcfImpl implements AuthProcessor {
    private int puid() {
        return Session.current().getPUID();
    }

    @Override
    public boolean verifyLogined() {
        return puid() > 0;
    }

    @Override
    public boolean verifyUrl(String url, String method) {
        try {
            if (url.contains("@")) {
                return true;
            }

            if (BcfClient.hasUrlpath(url)) {
                return BcfClient.hasUrlpathByUser(puid(), url);
            }else{
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean verifyPermissions(String[] permissions, Logical logical) {
        int puid = puid();

        try {
            if (logical == Logical.AND) {
                boolean isOk = true;

                for (String p : permissions) {
                    isOk = isOk && BcfClient.hasResourceByUser(puid, p);
                }

                return isOk;
            } else {
                for (String p : permissions) {
                    if (BcfClient.hasResourceByUser(puid, p)) {
                        return true;
                    }
                }
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean verifyRoles(String[] roles, Logical logical) {
        //
        //bcf 的角色也是资源
        //
        int puid = puid();

        try {
            if (logical == Logical.AND) {
                boolean isOk = true;

                for (String p : roles) {
                    isOk = isOk && BcfClient.hasResourceByUser(puid, p);
                }

                return isOk;
            } else {
                for (String p : roles) {
                    if (BcfClient.hasResourceByUser(puid, p)) {
                        return true;
                    }
                }
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
