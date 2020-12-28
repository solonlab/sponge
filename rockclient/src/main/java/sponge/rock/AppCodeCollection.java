package sponge.rock;


import sponge.rock.models.AppCodeModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xq on 2017/7/3.
 */
public class AppCodeCollection extends HashMap<Integer,AppCodeModel> {

    protected void bind(List<AppCodeModel> mod) {
        for (AppCodeModel m : mod) {
            this.put(m.code, m);
        }
    }
}