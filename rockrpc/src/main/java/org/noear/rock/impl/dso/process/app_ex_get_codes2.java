package org.noear.rock.impl.dso.process;

import org.noear.wood.DbContext;
import org.noear.wood.DbQueryProcedure;

/**
 * Created by xq on 2017/7/3.
 */
public class app_ex_get_codes2 extends DbQueryProcedure {
    public app_ex_get_codes2(DbContext db) {
        super(db);

        lazyload(()->{
            //set("{colname}", ()->{popname});
            //
            if(lang == null) {
                lang = "";
            }

            set("@service", service);
            set("@lang", lang);

            sql("SELECT * from appx_ex_code WHERE service=@service AND lang=@lang");


        });
    }

    public String service;
    public String lang;
}
