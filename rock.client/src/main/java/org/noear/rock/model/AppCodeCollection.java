package org.noear.rock.model;


import java.util.HashMap;
import java.util.List;


public class AppCodeCollection extends HashMap<Integer, AppCodeModel> {

    public void bind(List<AppCodeModel> mod) {
        for (AppCodeModel m : mod) {
            this.put(m.code, m);
        }
    }
}