package org.noear.rock.model;

import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;


public class AppCodeModel implements IBinder {
    public int code;
    public String note;

    @Override
    public void bind(GetHandlerEx s) {
        code = s.get("code").value(0);
        note = s.get("note").value(null);

    }

    @Override
    public IBinder clone() {
        return new AppCodeModel();
    }
}