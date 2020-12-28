package org.noear.sponge.rock.process;

import org.noear.weed.DbContext;
import org.noear.weed.DbQueryProcedure;

/// <summary>
/// 生成:2017/04/26 03:49:48
/// 备注:请确保与[存储过程].[参数]的对应关系正确!!!
/// </summary>
public class app_get_agroup extends DbQueryProcedure
{
    public app_get_agroup(DbContext db)
    {
        super(db);

        lazyload(()->{
            //set("{colname}", ()->{popname});
            //
            set("@agroup_id", agroup_id);

            sql("select * from appx_agroup WHERE agroup_id = @agroup_id;");
        });
    }      

    public int agroup_id;
}