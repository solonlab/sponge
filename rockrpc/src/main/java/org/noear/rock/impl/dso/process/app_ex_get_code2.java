package org.noear.rock.impl.dso.process;

import org.noear.weed.DbContext;
import org.noear.weed.DbQueryProcedure;

/**
 * Created by xq on 2017/7/3.
 */
public class app_ex_get_code2 extends DbQueryProcedure {
    public app_ex_get_code2(DbContext db) {
        super(db);

        lazyload(() -> {
            //set("{colname}", ()->{popname});
            //
            if(lang == null){
                lang = "";
            }

            set("@service", service);
            set("@code", code);
            set("@lang", lang);

            sql("SELECT * from appx_ex_code WHERE service=@service AND code=@code AND lang=@lang");

        });
    }

    public String service;
    public int code;
    public String lang;
}
