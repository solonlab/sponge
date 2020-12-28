package webapp.dso.process;

import org.noear.weed.DbContext;
import org.noear.weed.DbQueryProcedure;

/// <summary>
/// 生成:2017/04/26 03:49:48
/// 备注:请确保与[存储过程].[参数]的对应关系正确!!!
/// </summary>
public class app_get_whitelist extends DbQueryProcedure {
    public app_get_whitelist(DbContext db) {
        super(db);

        lazyload(()->{
            //set("{colname}", ()->{popname});
            //
            set("@type", type);
            set("@tag", tag);
            set("@value", value);

            sql("select `value` from appx_whitelist WHERE `tag` = @tag AND `type` = @type AND `value`=@value;");

        });
    }

    public int type;
    public String tag;
    public String value;
}