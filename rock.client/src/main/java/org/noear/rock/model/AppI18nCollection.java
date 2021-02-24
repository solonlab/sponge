package org.noear.rock.model;


import java.util.HashMap;
import java.util.List;


public class AppI18nCollection extends HashMap<String, AppI18nModel> {

    public void bind(List<AppI18nModel> mod) {
        for (AppI18nModel m : mod) {
            this.put(m.name, m);
        }
    }
}