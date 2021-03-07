package org.noear.rock.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 国际化配置集合
 * */
public class AppI18nCollection implements Serializable {
    public Map<String, String> data = new HashMap<>();

    public void bind(List<AppI18nModel> mod) {
        for (AppI18nModel m : mod) {
            data.put(m.name, m.note);
        }
    }

    public String get(String name) {
        return data.get(name);
    }

    @Override
    public String toString() {
        return "AppI18nCollection{" +
                "data=" + data +
                '}';
    }
}