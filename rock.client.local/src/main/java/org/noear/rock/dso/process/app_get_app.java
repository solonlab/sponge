package org.noear.rock.dso.process;

import org.noear.weed.DbContext;
import org.noear.weed.DbQueryProcedure;

/// <summary>
/// 生成:2017/04/26 03:49:48
/// 备注:请确保与[存储过程].[参数]的对应关系正确!!!
/// </summary>
public class app_get_app extends DbQueryProcedure
{
    public app_get_app(DbContext db)
    {
		super(db);
        lazyload(()->{

            //set("{colname}", ()->{popname});
            //
            set("@app_id", app_id);
            set("@app_key", app_key);

            if(app_id>0){
                sql("select * from appx WHERE app_id = @app_id;");
            }else{
                sql("select * from appx WHERE app_key = @app_key;");
            }
        });
    }

    public int app_id;
    public String app_key;
}