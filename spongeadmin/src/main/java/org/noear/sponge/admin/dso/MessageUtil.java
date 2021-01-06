package org.noear.sponge.admin.dso;

import org.noear.snack.ONode;
import org.noear.water.WaterClient;

/**
 * Created by yuety on 2017/7/20.
 */
public class MessageUtil {

    //更新同盾分
    public static boolean doTongDunUpdate(String mobile,int g_agroupId,int g_ugroupId) throws Exception{
        ONode m = new ONode();
        m.set("mobile", mobile);
        m.set("g_agroupId", g_agroupId);
        m.set("g_ugroupId", g_ugroupId);
        return WaterClient.Message.sendMessage(IDUtil.buildGuid(), MessageTopic.tongdunUpdate, m.toJson());
    }

    //更新橡皮分
    public static boolean duRubberUpdate(String mobile,int g_agroupId,int g_ugroupId) throws Exception{
        ONode m = new ONode();
        m.set("mobile", mobile);
        m.set("g_agroupId", g_agroupId);
        m.set("g_ugroupId", g_ugroupId);
        return WaterClient.Message.sendMessage(IDUtil.buildGuid(), MessageTopic.rubberUpdate, m.toJson());
    }

}
