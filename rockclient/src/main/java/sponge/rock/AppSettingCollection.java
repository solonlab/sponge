package sponge.rock;


import sponge.rock.models.AppSettingModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yuety on 2017/6/23.
 */
public class AppSettingCollection extends HashMap<String,AppSettingModel> {

    protected void bind(List<AppSettingModel> mod){
        for(AppSettingModel m:mod){
            this.put(m.name,m);
        }
    }

}
