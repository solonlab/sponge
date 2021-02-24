package org.noear.rock.model;

import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;


public class AppI18nModel implements IBinder {
    public String name;
    public String note;

    @Override
    public void bind(GetHandlerEx s) {
        name = s.get("name").value(null);
        note = s.get("note").value(null);

    }

    @Override
    public IBinder clone() {
        return new AppI18nModel();
    }
}