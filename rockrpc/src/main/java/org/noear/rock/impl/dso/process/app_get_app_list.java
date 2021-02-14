package org.noear.rock.impl.dso.process;

import org.noear.weed.DbContext;
import org.noear.weed.DbQueryProcedure;

/// <summary>
/// 生成:2017/04/26 03:49:48
/// 备注:请确保与[存储过程].[参数]的对应关系正确!!!
/// </summary>
public class app_get_app_list extends DbQueryProcedure {
    public app_get_app_list(DbContext db) {
        super(db);

        lazyload(() -> {
            //set("{colname}", ()->{popname});
            //
            set("@agroup_id",  agroup_id);
            set("@ugroup_id",  ugroup_id);

            if(agroup_id>0 && ugroup_id>0){
                sql("select * from appx WHERE agroup_id = @agroup_id AND ugroup_id = @ugroup_id;");
                return;
            }

            if(agroup_id>0) {
                sql("select * from appx WHERE agroup_id = @agroup_id;");
                return;
            }

            if(ugroup_id>0) {
                sql("select * from appx WHERE ugroup_id = @ugroup_id;");
                return;
            }
        });
    }

    public int agroup_id;
    public int ugroup_id;
}