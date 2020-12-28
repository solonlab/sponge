package sponge.rock.process;

import org.noear.weed.DbContext;
import org.noear.weed.DbQueryProcedure;

/// <summary>
/// 生成:2017/04/26 03:49:48
/// 备注:请确保与[存储过程].[参数]的对应关系正确!!!
/// </summary>
public class app_ex_get_setting_item_by_name extends DbQueryProcedure
{
    public app_ex_get_setting_item_by_name(DbContext db)
    {
        super(db);

        lazyload(()->{
            //set("{colname}", ()->{popname});
            //
            set("@agroup_id", agroup_id);
            set("@name", name);

            sql("SELECT * from appx_ex_setting WHERE agroup_id=@agroup_id AND app_id>0 AND name=@name;");

        });
    }

    public int agroup_id;
    public String name;
}