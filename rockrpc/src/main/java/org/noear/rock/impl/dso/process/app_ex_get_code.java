package org.noear.rock.impl.dso.process;

import org.noear.wood.DbContext;
import org.noear.wood.DbQueryProcedure;

/**
 * Created by xq on 2017/7/3.
 */
public class app_ex_get_code extends DbQueryProcedure {
    public app_ex_get_code(DbContext db) {
        super(db);

        lazyload(() -> {
            //set("{colname}", ()->{popname});
            //
            if(lang == null){
                lang = "";
            }

            set("@agroup_id", agroup_id);
            set("@code", code);
            set("@lang", lang);

            sql("SELECT * from appx_ex_code WHERE agroup_id=@agroup_id AND code=@code AND lang=@lang");


        });
    }

    public int agroup_id;
    public int code;
    public String lang;
}
