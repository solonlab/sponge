package org.noear.rock.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppSettingCollection implements Serializable {
    public Map<String, AppSettingModel> data = new HashMap<>();

    public void bind(List<AppSettingModel> mod) {
        for (AppSettingModel m : mod) {
            data.put(m.name, m);
        }
    }

    public AppSettingModel get(String name){
        return data.get(name);
    }

    @Override
    public String toString() {
        return "AppSettingCollection{" +
                "data=" + data +
                '}';
    }
}
