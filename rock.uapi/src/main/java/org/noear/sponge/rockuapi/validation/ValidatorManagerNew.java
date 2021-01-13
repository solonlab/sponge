package org.noear.sponge.rockuapi.validation;

import org.noear.sponge.rockuapi.UapiCodes;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.noear.solon.extend.validation.ValidatorManager;
import org.noear.solon.extend.validation.annotation.*;

import java.lang.annotation.Annotation;

public class ValidatorManagerNew extends ValidatorManager {
    public ValidatorManagerNew() {
        super();
    }

    @Override
    protected boolean failureDo(Context ctx, Annotation ano, Result result, String message) {
        ctx.setHandled(true);

        Class<?> type = ano.annotationType();

        if (type.equals(NoRepeatSubmit.class)) {
            throw UapiCodes.CODE_15;
        } else if (type.equals(Whitelist.class)) {
            throw UapiCodes.CODE_16;
        } else {
            throw UapiCodes.CODE_13(result.getDescription());
        }
    }
}
