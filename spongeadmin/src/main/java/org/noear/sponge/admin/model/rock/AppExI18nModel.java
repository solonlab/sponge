package org.noear.sponge.admin.model.rock;

import lombok.Getter;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

/// <summary>
/// 生成:2018/01/25 05:48:45
/// 
/// </summary>
@Getter
public class AppExI18nModel implements IBinder {
    public int row_id;
    public int agroup_id;
    public String service;
    public String name;
    public String lang;
    public String note;

    public void bind(GetHandlerEx s) {
        //1.source:数据源
        //
        row_id = s.get("row_id").value(0);
        agroup_id = s.get("agroup_id").value(0);
        service = s.get("service").value("");
        name = s.get("name").value("");
        lang = s.get("lang").value("");
        note = s.get("note").value(null);
    }

    public IBinder clone() {
        return new AppExI18nModel();
    }
}