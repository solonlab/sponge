package org.noear.sponge.rock.process;

import org.noear.weed.DbContext;
import org.noear.weed.DbQueryProcedure;

/**
 * Created by xq on 2017/8/8.
 */
public class app_ex_get_verson extends DbQueryProcedure {
    public app_ex_get_verson(DbContext db)
    {
        super(db);

        lazyload(()->{

            set("@platform", platform);
            set("@agroup_id", agroup_id);
            set("@app_id", app_id);

            if(agroup_id>0){
                sql("SELECT * FROM appx_ex_version WHERE agroup_id=@agroup_id  AND app_id=0 AND platform=@platform AND is_enable=1 ORDER BY ver desc LIMIT 1");
            }else{
                sql("SELECT * FROM appx_ex_version WHERE app_id=@app_id                     AND platform=@platform AND is_enable=1 ORDER BY ver desc LIMIT 1");
            }

        });
    }

    public int agroup_id;
    public int app_id;
    public int platform;
}
