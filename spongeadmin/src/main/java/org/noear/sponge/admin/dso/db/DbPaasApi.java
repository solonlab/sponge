package org.noear.sponge.admin.dso.db;

import org.noear.weed.DbContext;
import org.apache.http.util.TextUtils;
import org.noear.sponge.admin.Config;

import java.sql.SQLException;

public class DbPaasApi {
    private static DbContext db() {
        return Config.water;
    }

    public static boolean setPaasFun(String tag,String fun_name,String name_display,String args,
                                     String code,String note,int is_enabled) throws SQLException{
        Integer fun_id = db().table("paas_fun")
                .where("tag = ?", tag)
                .and("fun_name = ?", fun_name)
                .select("fun_id")
                .getValue(0);
        if (fun_id==0) {
            return db().table("paas_fun")
                    .set("tag",tag)
                    .set("fun_name",fun_name)
                    .expre(tb -> {
                        if (!TextUtils.isEmpty(name_display)) {
                            tb.set("name_display",name_display);
                        }
                        if (!TextUtils.isEmpty(note)) {
                            tb.set("note",note);
                        }
                    })
                    .set("args",args)
                    .set("code",code)
                    .set("is_enabled",is_enabled)
                    .insert()>0;
        }

        return true;
    }

    public static boolean setEtl(String tag,String etl_name,String code,int is_enabled,int is_extract,
                                 int is_load,int is_transform,int cursor_type) throws SQLException{
        Integer etl_id = db().table("paas_etl")
                .where("tag = ?",tag)
                .and("etl_name = ?",etl_name)
                .select("etl_id")
                .getValue(0);
        if (etl_id==0) {
            return db().table("paas_etl")
                    .set("tag",tag)
                    .set("etl_name",etl_name)
                    .set("code",code)
                    .set("is_enabled",is_enabled)
                    .set("is_extract",is_extract)
                    .set("is_load",is_load)
                    .set("is_transform",is_transform)
                    .set("cursor_type",cursor_type)
                    .insert()>0;
        }

        return true;
    }

    public static String getModelPk(int model_id) throws SQLException{
        return db().table("rubber_model_field")
                .where("model_id = ?", model_id)
                .and("is_pk = 1")
                .limit(1)
                .select("name")
                .getValue(new String());
    }
//
//    public static boolean setPaasAndETL(DoveMsgModel msg, SchemeModel scheme, AppGroupModel agroup) throws Exception{
//        //1 生成paas公共函数
//        String scheme_tag = scheme.tag;
//        String[] actions = msg.actions.split(",");
//        String include = "";
//        String stream = "";
//        String streamParams = ",'"+msg.title+"','"+msg.content+"','"+msg.msg_link+"','"+msg.push_link+"'";
//        for (String action_id:actions) {
//            DoveMsgActionModel action = DbDoveApi.getMsgAction(Integer.parseInt(action_id));
//            String code = action.code;
//            DbPaasApi.setPaasFun(scheme_tag,action.name,action.name_display,"m,title,content,msgLink,pushLink",code,"",1);
//            include = include + ";" + scheme_tag+"/"+action.name;
//            stream = stream + ",$"+scheme_tag+"_"+action.name+"(m"+streamParams+")";
//        }
//        System.out.println(Config.raas_uri + "/preview.js?scheme="+scheme_tag+"/"+scheme.name+"&type=11&limit=1000");
//        String SQL = HttpUtil.getString(Config.raas_uri + "/preview.js?scheme="+scheme_tag+"/"+scheme.name+"&type=11&limit=1000");
//        JSONObject etl = new JSONObject();
//        etl.put("name",scheme_tag+"."+msg.msg_id);
//        etl.put("include",include.substring(1,include.length()));
//        JSONObject source = new JSONObject();
//        //从sql语句中获取数据库配置内容
//        SQL = SQL.replaceAll("(?u) as "," AS ").replaceAll("(?u) from "," FROM ");
//        String[] asArr = SQL.split("AS");
//        String db = "";
//        String table = "";
//        for (String sql:asArr) {
//            if (sql.contains("FROM")){
//                int fIndex = sql.indexOf("FROM");
//                int tIndex = sql.indexOf(".");
//                db = sql.substring(fIndex+4,tIndex).trim();
//                table = sql.substring(tIndex+1,sql.length()-1).replaceAll("`","").trim();
//                break;
//            }
//        }
//        source.put("db",agroup.tag+"/"+db);
//        source.put("table",table);
//        source.put("cursor",DbPaasApi.getModelPk(msg.model_id));
//        source.put("key",DbPaasApi.getModelPk(msg.model_id));
//        source.put("model",SQL.substring(0,SQL.indexOf("LIMIT")));
//        source.put("limit",1000);
//        etl.put("source",source);
//
//        JSONObject target = new JSONObject();
//        target.put("policy",10);
//        target.put("stream",stream.substring(1,stream.length()));
//        etl.put("target",target);
//        DbPaasApi.setEtl(scheme_tag,agroup.tag+"."+msg.msg_id,etl.toJSONString().replaceAll("\"(\\w+)\"(\\s*:\\s*)", "$1$2"),0,0,0,0,1);
//
//        return true;
//    }

    public static boolean updatePaasFun(int agroup_id,int action_id,String name,String name_display,String code) throws SQLException{

        String old_name = DbDoveApi.getActionNameById(action_id);
        String tag = DbRockApi.getAppGroupById(agroup_id).tag;

        return db().table("paas_fun")
                .set("fun_name",name)
                .set("name_display",name_display)
                .set("code",code)
                .where("tag = ?",tag+Config.push_suffix)
                .and("fun_name = ?",old_name)
                .and("name_display = ?",name_display)
                .update()>0;
    }
}
