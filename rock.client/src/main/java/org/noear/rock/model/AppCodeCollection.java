package org.noear.rock.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用状态码集合
 * */
@Deprecated
public class AppCodeCollection  implements Serializable {
    public Map<Integer, String> data = new HashMap<>();

    public void bind(List<AppCodeModel> mod) {
        for (AppCodeModel m : mod) {
            data.put(m.code, m.note);
        }
    }

    public String get(Integer code){
        return data.get(code);
    }

    @Override
    public String toString() {
        return "AppCodeCollection{" +
                "data=" + data +
                '}';
    }
}