package org.noear.rock.impl.dso.process;

import org.noear.weed.DbContext;
import org.noear.weed.DbQueryProcedure;

/// <summary>
/// 生成:2017/04/26 03:49:48
/// 备注:请确保与[存储过程].[参数]的对应关系正确!!!
/// </summary>
public class app_ex_get_setting extends DbQueryProcedure
{
    public app_ex_get_setting(DbContext db)
    {
        super(db);
        lazyload(()->{
            //set("{colname}", ()->{popname});
            //
            set("@agroup_id", agroup_id);
            set("@app_id", app_id);


            if(agroup_id>0){
                if (is_client_only) {
                    sql("SELECT * from appx_ex_setting WHERE agroup_id=@agroup_id AND app_id=0 AND is_client=1;");
                } else {
                    sql("SELECT * from appx_ex_setting WHERE agroup_id=@agroup_id AND app_id=0;");
                }
            }
            else {
                if (is_client_only) {
                    sql("SELECT * from appx_ex_setting WHERE app_id=@app_id AND is_client=1;");
                } else {
                    sql("SELECT * from appx_ex_setting WHERE app_id=@app_id;");
                }
            }


        });


    }

    public int agroup_id;
    public int app_id;
    public boolean is_client_only;
}