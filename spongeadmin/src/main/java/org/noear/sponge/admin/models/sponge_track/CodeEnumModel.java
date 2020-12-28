package org.noear.sponge.admin.models.sponge_track;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

/**
 * @Author:Fei.chu
 * @Date:Created in 15:59 2019/06/06
 * @Description:
 */
@Data
public class CodeEnumModel implements IBinder {

    /**  */
    public int row_id;
    /** 类型 */
    public int type;
    /** 值 */
    public int value;
    /** 标题 */
    public String title;
    /** 关键字 */
    public String keyword;

    public void bind(GetHandlerEx s) {
        row_id = s.get("row_id").value(0);
        type = s.get("type").value(0);
        value = s.get("value").value(0);
        title = s.get("title").value(null);
        keyword = s.get("keyword").value(null);
    }

    public IBinder clone() {
        return new CodeEnumModel();
    }
}
