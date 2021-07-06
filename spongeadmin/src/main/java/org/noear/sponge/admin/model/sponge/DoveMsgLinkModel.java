package org.noear.sponge.admin.model.sponge;

import org.noear.weed.*;

import java.util.*;

/// <summary>
/// 生成:2018/08/02 04:06:53
/// 
/// </summary>
public class DoveMsgLinkModel implements IBinder {
    public int link_id;
    public int agroup_id;
    public String name;
    public String link;
    public String operator;
    public int create_date;
    public Date create_fulltime;
    public int update_date;
    public Date update_fulltime;

    public void bind(GetHandlerEx s) {
        //1.source:数据源
        //
        link_id = s.get("link_id").value(0);
        agroup_id = s.get("agroup_id").value(0);
        name = s.get("name").value(null);
        link = s.get("link").value(null);
        operator = s.get("operator").value(null);
        create_date = s.get("create_date").value(0);
        create_fulltime = s.get("create_fulltime").value(null);
        update_date = s.get("update_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
    }

    public IBinder clone() {
        return new DoveMsgLinkModel();
    }

    public int getLink_id() {
        return link_id;
    }

    public void setLink_id(int link_id) {
        this.link_id = link_id;
    }

    public int getAgroup_id() {
        return agroup_id;
    }

    public void setAgroup_id(int agroup_id) {
        this.agroup_id = agroup_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getCreate_date() {
        return create_date;
    }

    public void setCreate_date(int create_date) {
        this.create_date = create_date;
    }

    public Date getCreate_fulltime() {
        return create_fulltime;
    }

    public void setCreate_fulltime(Date create_fulltime) {
        this.create_fulltime = create_fulltime;
    }

    public int getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(int update_date) {
        this.update_date = update_date;
    }

    public Date getUpdate_fulltime() {
        return update_fulltime;
    }

    public void setUpdate_fulltime(Date update_fulltime) {
        this.update_fulltime = update_fulltime;
    }
}