package org.noear.rock.model;

import java.io.Serializable;

/**
 * 应用版本号
 * */
public class AppVersionModel implements Serializable {
    /**
     * 应用组ID
     * */
    public int agroup_id;
    /**
     * 应用ID
     * */
    public int app_id;
    /**
     * 版本号
     * */
    public int ver;
    /**
     * 内容描述
     * */
    public String content;
    /**
     * 更新方式（0：普通更新， 1：强制更新）
     */
    public int type;
    /**
     * x版本以下提示更新
     */
    public int alert_ver;
    /**
     * x版本以下强制更新
     */
    public int force_ver;
    /**
     * 应用平台
     * */
    public int platform;
    /**
     * 下载地址
     * */
    public String url;
}