package org.noear.sponge.rock.models;

import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

/**
 * Created by xq on 2017/7/3.
 */
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