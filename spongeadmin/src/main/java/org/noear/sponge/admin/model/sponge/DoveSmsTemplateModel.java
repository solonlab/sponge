package org.noear.sponge.admin.model.sponge;

import org.noear.weed.*;

import java.util.*;

/// <summary>
/// 生成:2018/08/02 04:10:09
/// 
/// </summary>
public class DoveSmsTemplateModel implements IBinder {
    public int template_id;
    public int agroup_id;
    public String name;
    public String out_id;
    public String content;
    public String operator;
    public int create_date;
    public Date create_fulltime;
    public int update_date;
    public Date update_fulltime;

    public void bind(GetHandlerEx s) {
        //1.source:数据源
        //
        template_id = s.get("template_id").value(0);
        agroup_id = s.get("agroup_id").value(0);
        name = s.get("name").value(null);
        out_id = s.get("out_id").value(null);
        content = s.get("content").value(null);
        operator = s.get("operator").value(null);
        create_date = s.get("create_date").value(0);
        create_fulltime = s.get("create_fulltime").value(null);
        update_date = s.get("update_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
    }

    public IBinder clone() {
        return new DoveSmsTemplateModel();
    }

    public int getTemplate_id() {
        return template_id;
    }

    public String getOut_id() {
        return out_id;
    }

    public void setOut_id(String out_id) {
        this.out_id = out_id;
    }

    public void setTemplate_id(int template_id) {
        this.template_id = template_id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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