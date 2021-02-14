package org.noear.rock.model;


import java.util.HashMap;
import java.util.List;

public class AppSettingCollection extends HashMap<String, AppSettingModel> {

    public void bind(List<AppSettingModel> mod){
        for(AppSettingModel m:mod){
            this.put(m.name,m);
        }
    }

}
