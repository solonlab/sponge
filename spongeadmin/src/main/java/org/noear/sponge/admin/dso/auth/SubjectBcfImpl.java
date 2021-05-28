package org.noear.sponge.admin.dso.auth;

import org.noear.bcf.models.BcfUserModel;
import org.noear.solon.extend.auth.model.Subject;

/**
 * @author noear 2021/5/28 created
 */
public class SubjectBcfImpl implements Subject {
    BcfUserModel user;

    public SubjectBcfImpl(BcfUserModel user) {
        this.user = user;
    }

    public BcfUserModel getUser() {
        return user;
    }
}
