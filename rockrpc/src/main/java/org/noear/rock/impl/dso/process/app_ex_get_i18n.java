package org.noear.rock.impl.dso.process;

import org.noear.weed.DbContext;
import org.noear.weed.DbQueryProcedure;

/**
 * Created by xq on 2017/7/3.
 */
public class app_ex_get_i18n extends DbQueryProcedure {
    public app_ex_get_i18n(DbContext db) {
        super(db);

        lazyload(() -> {
            //set("{colname}", ()->{popname});
            //
            if(lang == null){
                lang = "";
            }

            set("@service", service);
            set("@name", name);
            set("@lang", lang);

            sql("SELECT * from appx_ex_i18n WHERE service=@service AND name=@name AND lang=@lang");


        });
    }

    public String service;
    public String name;
    public String lang;
}
