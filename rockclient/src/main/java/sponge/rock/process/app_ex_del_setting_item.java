package sponge.rock.process;

import org.noear.weed.DbContext;
import org.noear.weed.DbQueryProcedure;

/// <summary>
/// 生成:2017/04/26 03:49:48
/// 备注:请确保与[存储过程].[参数]的对应关系正确!!!
/// </summary>
public class app_ex_del_setting_item extends DbQueryProcedure
{
    public app_ex_del_setting_item(DbContext db)
    {
        super(db);

        lazyload(()->{
            //set("{colname}", ()->{popname});
            //
            set("@agroup_id", agroup_id);
            set("@app_id", app_id);
            set("@name", name);

            if(agroup_id>0) {
                sql("DELETE FROM appx_ex_setting WHERE agroup_id=@agroup_id AND app_id=0 AND name=@name;");
            }else{
                sql("DELETE FROM appx_ex_setting WHERE app_id>0 AND app_id=@app_id AND name=@name;");
            }
        });
    }

    public int agroup_id;
    public int app_id;
    public String name;
}