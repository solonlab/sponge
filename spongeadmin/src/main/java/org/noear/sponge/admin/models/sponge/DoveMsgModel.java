package org.noear.sponge.admin.models.sponge;

import org.noear.weed.*;

import java.util.*;

/// <summary>
/// 生成:2018/07/25 04:55:24
/// 
/// </summary>
public class DoveMsgModel implements IBinder {
    public long msg_id;
    public String title;
    public String content;
    public int agroup_id;
    public String push_link;
    public String msg_link;
    public String mobile;
    public int model_id;
    public String model_name;
    public String model_name_disp;
    public int rule_id;
    public String rule_name;
    public String rule_name_disp;
    public String actions;
    public Date target_time;
    public int status;
    public String operator;
    public int create_date;
    public Date create_fulltime;
    public int update_date;
    public Date update_fulltime;

    public void bind(GetHandlerEx s) {
        //1.source:数据源
        //
        msg_id = s.get("msg_id").value(0L);
        title = s.get("title").value(null);
        content = s.get("content").value(null);
        agroup_id = s.get("agroup_id").value(0);
        push_link = s.get("push_link").value(null);
        msg_link = s.get("msg_link").value(null);
        mobile = s.get("mobile").value(null);
        model_id = s.get("model_id").value(0);
        model_name = s.get("model_name").value(null);
        model_name_disp = s.get("model_name_disp").value(null);
        rule_id = s.get("rule_id").value(0);
        rule_name = s.get("rule_name").value(null);
        rule_name_disp = s.get("rule_name_disp").value(null);
        actions = s.get("actions").value(null);
        target_time = s.get("target_time").value(null);
        status = s.get("status").value(0);
        operator = s.get("operator").value(null);
        create_date = s.get("create_date").value(0);
        create_fulltime = s.get("create_fulltime").value(null);
        update_date = s.get("update_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
    }

    public IBinder clone() {
        return new DoveMsgModel();
    }

    public long getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(long msg_id) {
        this.msg_id = msg_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAgroup_id() {
        return agroup_id;
    }

    public void setAgroup_id(int agroup_id) {
        this.agroup_id = agroup_id;
    }

    public String getPush_link() {
        return push_link;
    }

    public void setPush_link(String push_link) {
        this.push_link = push_link;
    }

    public String getMsg_link() {
        return msg_link;
    }

    public void setMsg_link(String msg_link) {
        this.msg_link = msg_link;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getModel_name_disp() {
        return model_name_disp;
    }

    public void setModel_name_disp(String model_name_disp) {
        this.model_name_disp = model_name_disp;
    }

    public int getRule_id() {
        return rule_id;
    }

    public void setRule_id(int rule_id) {
        this.rule_id = rule_id;
    }

    public String getRule_name() {
        return rule_name;
    }

    public void setRule_name(String rule_name) {
        this.rule_name = rule_name;
    }

    public String getRule_name_disp() {
        return rule_name_disp;
    }

    public void setRule_name_disp(String rule_name_disp) {
        this.rule_name_disp = rule_name_disp;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public Date getTarget_time() {
        return target_time;
    }

    public void setTarget_time(Date target_time) {
        this.target_time = target_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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