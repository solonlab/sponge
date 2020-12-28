package org.noear.sponge.rock;

import org.noear.sponge.rock.models.AppModel;
import org.noear.sponge.rock.protocol.RockRpc;
import org.noear.water.WaterClient;
import org.noear.weed.cache.ICacheServiceEx;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class RockUtil {

    private static ICacheServiceEx _cache;
    private static boolean _isService;

    public static void tryInit(ICacheServiceEx cache, RockRpc service) {
        _cache = cache;

        if(service == null){
            AppModel.rockclient = RockClient.instance();
        }else {
            AppModel.rockclient = service;
            _isService = true;
        }
    }

    public static void delCacheByApp(int appID) {
        if (appID < 1) {
            return;
        }

        if (_isService) {
            _cache.tags()
                    .clear("app_setting_" + appID)
                    .clear("app_" + appID);
        }


        RockUtil.sendMessage("app_" + appID + ";app_setting_" + appID);
    }

    public static void delCacheByAppGroup(int agroupID) {
        if (agroupID < 1) {
            return;
        }
        if (_isService) {
            _cache.tags()
                    .clear("app_group_" + agroupID)
                    .clear("app_group_setting_" + agroupID)
                    .clear("app_setting_by_name_" + agroupID)
                    .clear("app_list_" + agroupID);
        }


        RockUtil.sendMessage("app_group_" + agroupID + ";app_group_setting_" + agroupID + ";app_setting_by_name_" + agroupID + ";app_list_" + agroupID);
    }

    public static void delCacheForVersion(int agroupID, int appID) {
        if (appID > 0) {
            if (_isService) {
                _cache.tags()
                        .clear("app_ver_" + appID);
            }

            RockUtil.sendMessage("app_ver_" + appID);
        } else {
            if (_isService) {
                _cache.tags()
                        .clear("app_group_ver_" + agroupID);
            }

            RockUtil.sendMessage("app_group_ver_" + agroupID);
        }
    }

    public static void delCacheForCodes(int agroupID) {
        if (_isService) {
            _cache.tags()
                    .clear("app_code_" + agroupID);
        }

        RockUtil.sendMessage("app_code_" + agroupID);
    }

    public static void delCacheForWhiteList(String tag, int type) {
        if (_isService) {
            _cache.tags()
                    .clear("app_whitelist_" + type + "_" + tag);
        }

        RockUtil.sendMessage("app_whitelist_" + type + "_" + tag);
    }

    //======================================
    //
    // 消息相关的操作
    //

    //private static String msg_topic = "water.cache.update";

    protected static void sendMessage(String message) {
        //
        //还有个RockClient本地版；需要通过消息总线更新
        //
        try {
            WaterClient.Notice.updateCache(message);
            //WaterClient.Message.sendMessage(RockUtil.buildGuid(), msg_topic, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    //======================================
    //
    // 生成各种KEY
    //

    public static String buildUkey(int ugroupID, String mobile) {
        return md5(ugroupID + "#" + mobile);
    }


    public static String buildAppkey() {
        char[] chars = new char[16];
        int i = 0;
        while (i < 16) {
            int f = (int) (Math.random() * 3);
            if (f == 0)
                chars[i] = (char) ('A' + Math.random() * 26);
            else if (f == 1)
                chars[i] = (char) ('a' + Math.random() * 26);
            else
                chars[i] = (char) ('0' + Math.random() * 10);
            i++;
        }
        return new String(chars);
    }

    public static String buildAkey() {
        return buildGuid();
    }

    public static String buildGuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String md5(String str) {
        String s = null;
        try {
            byte[] data = str.getBytes("UTF-8");

            s = DigestUtils.md5Hex(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return s;
    }
}
