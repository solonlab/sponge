package org.noear.sponge.admin.model.sponge;

import lombok.Getter;
import org.noear.wood.*;

import java.util.*;

/// <summary>
/// 生成:2018/08/02 04:06:53
/// 
/// </summary>
@Getter
public class DoveMsgActionModel implements IBinder {
    public int action_id;
    public int agroup_id;
    public String name;
    public String name_display;
    public String code;
    public String operator;
    public int create_date;
    public Date create_fulltime;
    public int update_date;
    public Date update_fulltime;

    public void bind(GetHandlerEx s) {
        //1.source:数据源
        //
        action_id = s.get("action_id").value(0);
        agroup_id = s.get("agroup_id").value(0);
        name = s.get("name").value(null);
        name_display = s.get("name_display").value(null);
        code = s.get("code").value(null);
        operator = s.get("operator").value(null);
        create_date = s.get("create_date").value(0);
        create_fulltime = s.get("create_fulltime").value(null);
        update_date = s.get("update_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
    }

    public IBinder clone() {
        return new DoveMsgActionModel();
    }
}