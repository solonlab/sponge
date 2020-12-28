package sponge.rock.process;

import org.noear.weed.DbContext;
import org.noear.weed.DbQueryProcedure;

/**
 * Created by xq on 2017/7/3.
 */
public class app_ex_get_codes extends DbQueryProcedure {
    public app_ex_get_codes(DbContext db) {
        super(db);

        lazyload(()->{
            //set("{colname}", ()->{popname});
            //
            set("@agroup_id", agroup_id);

            sql("SELECT * from appx_ex_code WHERE agroup_id=@agroup_id");
        });
    }

    public int agroup_id;
}
