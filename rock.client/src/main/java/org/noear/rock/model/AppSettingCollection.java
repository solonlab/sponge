package org.noear.rock.model;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 应用设置集合
 * */
public class AppSettingCollection extends HashMap<String, AppSettingModel> implements Serializable {

    public void bind(List<AppSettingModel> mod){
        for(AppSettingModel m:mod){
            this.put(m.name,m);
        }
    }

}
