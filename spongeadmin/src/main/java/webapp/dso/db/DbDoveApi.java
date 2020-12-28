package webapp.dso.db;

import org.noear.weed.DbContext;
import org.noear.weed.DbTableQuery;
import webapp.Config;
import webapp.models.others.CountModel;
import webapp.models.sponge.*;
import webapp.utils.DateUtil;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 2018.07.25
 *
 * @author konar
 */
public class DbDoveApi {
    private static DbContext db() {
        return Config.sponge;
    }

    public static void addMsg(long msg_id,
                              String title,
                              String content,
                              int agroup_id,
                              String push_link,
                              String msg_link,
                              String mobile,
                              int model_id,
                              String model_name,
                              String model_name_disp,
                              int rule_id,
                              String rule_name,
                              String rule_name_disp,
                              String actions,
                              Date target_time,
                              int status,
                              String operator) throws SQLException {
        db().table("dove_msg")
                .set("msg_id", msg_id)
                .set("title", title)
                .set("content", content)
                .set("agroup_id", agroup_id)
                .set("push_link", push_link)
                .set("msg_link", msg_link)
                .set("mobile", mobile)
                .set("model_id", model_id)
                .set("model_name", model_name)
                .set("model_name_disp", model_name_disp)
                .set("rule_id", rule_id)
                .set("rule_name", rule_name)
                .set("rule_name_disp", rule_name_disp)
                .set("actions", actions)
                .set("target_time", target_time)
                .set("status", status)
                .set("operator", operator)
                .set("create_date", DateUtil.getDate(new Date()))
                .set("update_date", DateUtil.getDate(new Date()))
                .insert();
    }

    public static List<DoveMsgModel> getMsgs(int agroup_id, int status, int page, int page_size, CountModel countModel) throws SQLException {
        int start = (page - 1) * page_size;

        DbTableQuery query = db().table("dove_msg")
                .where("agroup_id = ?", agroup_id)
                .and("status = ?", status);

        countModel.setCount(query.count());

        return query
                .limit(start, page_size)
                .select("*")
                .getList(new DoveMsgModel());
    }

    public static void addMsgLink(int link_id,
                                    int agroup_id,
                                    String name,
                                    String link,
                                    String operator) throws SQLException {
        db().table("dove_msg_link")
                .set("link_id", link_id)
                .set("agroup_id", agroup_id)
                .set("name", name)
                .set("link", link)
                .set("operator", operator)
                .set("create_date", DateUtil.getDate(new Date()))
                .set("update_date", DateUtil.getDate(new Date()))
                .insert();
    }

    public static void modMsgLink(int link_id,
                                  int agroup_id,
                                  String name,
                                  String link,
                                  String operator) throws SQLException {
        db().table("dove_msg_link")
                .set("agroup_id", agroup_id)
                .set("name", name)
                .set("link", link)
                .set("operator", operator)
                .set("update_date", DateUtil.getDate(new Date()))
                .where("link_id = ?", link_id)
                .update();
    }

    public static boolean delMsgLink(int link_id) throws SQLException{
        return db().table("dove_msg_link")
                .where("link_id = ?",link_id)
                .delete()>0;
    }

    public static List<DoveMsgLinkModel> getMsgLinks(int agroup_id) throws SQLException {
        return db().table("dove_msg_link")
                .where("agroup_id = ?", agroup_id)
                .select("*")
                .getList(new DoveMsgLinkModel());
    }

    public static DoveMsgLinkModel getMsgLink(int link_id) throws SQLException {
        return db().table("dove_msg_link")
                .where("link_id = ?", link_id)
                .limit(1)
                .select("*")
                .getItem(new DoveMsgLinkModel());
    }

    public static void addMsgAction(int action_id,
                                    int agroup_id,
                                    String name,
                                    String name_display,
                                    String code,
                                    String operator) throws SQLException {
        db().table("dove_msg_action")
                .set("action_id", action_id)
                .set("agroup_id", agroup_id)
                .set("name", name)
                .set("name_display", name_display)
                .set("code", code)
                .set("operator", operator)
                .set("create_date", DateUtil.getDate(new Date()))
                .set("update_date", DateUtil.getDate(new Date()))
                .insert();
    }

    public static void modMsgAction(int action_id,
                                    int agroup_id,
                                    String name,
                                    String name_display,
                                    String code,
                                    String operator) throws SQLException {
        db().table("dove_msg_action")
                .set("agroup_id", agroup_id)
                .set("name", name)
                .set("name_display", name_display)
                .set("code", code)
                .set("operator", operator)
                .set("update_date", DateUtil.getDate(new Date()))
                .where("action_id = ?", action_id)
                .update();
    }

    public static String getActionNameById(int action_id) throws SQLException{
        return db().table("dove_msg_action")
                .where("action_id = ?", action_id)
                .select("name")
                .getValue(null);
    }

    public static boolean delMsgAction(int action_id) throws SQLException{
        return db().table("dove_msg_action")
                .where("action_id = ?",action_id)
                .delete() > 0;
    }

    public static List<DoveMsgActionModel> getMsgActions(int agroup_id) throws SQLException {
        return db().table("dove_msg_action")
                .where("agroup_id = ?", agroup_id)
                .select("*")
                .getList(new DoveMsgActionModel());
    }

    public static DoveMsgActionModel getMsgAction(int action_id) throws SQLException {
        return db().table("dove_msg_action")
                .where("action_id = ?", action_id)
                .limit(1)
                .select("*")
                .getItem(new DoveMsgActionModel());
    }

    public static DoveMsgModel getMsg(long msg_id) throws SQLException {
        return db().table("dove_msg")
                .where("msg_id = ?", msg_id)
                .select("*")
                .getItem(new DoveMsgModel());
    }

    public static void cancelMsg(long msg_id) throws SQLException {
        db().table("dove_msg")
                .set("status", 4)
                .where("msg_id = ?", msg_id)
                .and("status = ?", 1)
                .update();
    }

    public static void addSms(long sms_id,
                              int agroup_id,
                              String channels,
                              String var_setting,
                              int template_id,
                              String content,
                              String mobile,
                              int model_id,
                              String model_name,
                              String model_name_disp,
                              int rule_id,
                              String rule_name,
                              String rule_name_disp,
                              Date target_time,
                              int status,
                              String operator) throws SQLException {
        db().table("dove_sms")
                .set("sms_id", sms_id)
                .set("agroup_id", agroup_id)
                .set("channels", channels)
                .set("template_id", template_id)
                .set("var_setting", var_setting)
                .set("content", content)
                .set("mobile", mobile)
                .set("model_id", model_id)
                .set("model_name", model_name)
                .set("model_name_disp", model_name_disp)
                .set("rule_id", rule_id)
                .set("rule_name", rule_name)
                .set("rule_name_disp", rule_name_disp)
                .set("target_time", target_time)
                .set("status", status)
                .set("operator", operator)
                .set("create_date", DateUtil.getDate(new Date()))
                .set("update_date", DateUtil.getDate(new Date()))
                .insert();
    }

    public static List<DoveSmsModel> getSmss(int agroup_id, int status, int page, int page_size, CountModel countModel) throws SQLException {
        int start = (page - 1) * page_size;

        DbTableQuery query = db().table("dove_sms")
                .where("agroup_id = ?", agroup_id)
                .and("status = ?", status);

        countModel.setCount(query.count());

        return query
                .limit(start, page_size)
                .select("*")
                .getList(new DoveSmsModel());
    }

    public static DoveSmsModel getSms(long sms_id) throws SQLException {
        return db().table("dove_sms")
                .where("sms_id = ?", sms_id)
                .select("*")
                .getItem(new DoveSmsModel());
    }

    public static void cancelSms(long sms_id) throws SQLException {
        db().table("dove_sms")
                .set("status", 4)
                .where("sms_id = ?", sms_id)
                .and("status = ?", 1)
                .update();
    }

    public static List<DoveSmsChannelModel> getSmsChannels(int agroup_id) throws SQLException {
        return db().table("dove_sms_channel")
                .where("agroup_id = ?", agroup_id)
                .select("*")
                .getList(new DoveSmsChannelModel());
    }

    public static DoveSmsChannelModel getSmsChannel(int channel_id) throws SQLException {
        return db().table("dove_sms_channel")
                .where("channel_id = ?", channel_id)
                .select("*")
                .getItem(new DoveSmsChannelModel());
    }

    public static void addSmsChannel(int channel_id,
                                      int agroup_id,
                                      String name,
                                      String name_display,
                                      String code,
                                      String operator) throws SQLException {
        db().table("dove_sms_channel")
                .set("channel_id", channel_id)
                .set("agroup_id", agroup_id)
                .set("name", name)
                .set("name_display", name_display)
                .set("code", code)
                .set("operator", operator)
                .set("create_date", DateUtil.getDate(new Date()))
                .set("update_date", DateUtil.getDate(new Date()))
                .insert();
    }

    public static void modSmsChannel(int channel_id,
                                      String name,
                                      String name_display,
                                      String code,
                                      String operator) throws SQLException {
        db().table("dove_sms_channel")
                .set("name", name)
                .set("name_display", name_display)
                .set("code", code)
                .set("operator", operator)
                .set("update_date", DateUtil.getDate(new Date()))
                .where("channel_id = ?", channel_id)
                .update();
    }

    public static boolean delSmsChannel(int channel_id) throws SQLException{
        return db().table("dove_sms_channel")
                .where("channel_id = ?",channel_id)
                .delete()>0;
    }

    public static List<DoveSmsTemplateModel> getSmsTemplates(int agroup_id) throws SQLException {
        return db().table("dove_sms_template")
                .where("agroup_id = ?", agroup_id)
                .select("*")
                .getList(new DoveSmsTemplateModel());
    }

    public static DoveSmsTemplateModel getSmsTemplate(int template_id) throws SQLException {
        return db().table("dove_sms_template")
                .where("template_id = ?", template_id)
                .select("*")
                .getItem(new DoveSmsTemplateModel());
    }

    public static void addSmsTemplate(int template_id,
                                      int agroup_id,
                                      String name,
                                      String out_id,
                                      String content,
                                      String operator) throws SQLException {
        db().table("dove_sms_template")
                .set("template_id", template_id)
                .set("agroup_id", agroup_id)
                .set("name", name)
                .set("out_id", out_id)
                .set("content", content)
                .set("operator", operator)
                .set("create_date", DateUtil.getDate(new Date()))
                .set("update_date", DateUtil.getDate(new Date()))
                .insert();
    }

    public static void modSmsTemplate(int template_id,
                                      String name,
                                      String out_id,
                                      String content,
                                      String operator) throws SQLException {
        db().table("dove_sms_template")
                .set("name", name)
                .set("out_id", out_id)
                .set("content", content)
                .set("operator", operator)
                .set("update_date", DateUtil.getDate(new Date()))
                .where("template_id = ?", template_id)
                .update();
    }

    public static boolean delSmsTemplate(int template_id) throws SQLException{
        return db().table("dove_sms_template")
                .where("template_id = ?",template_id)
                .delete()>0;
    }

}
