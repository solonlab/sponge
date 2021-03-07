package org.noear.rock.model;

import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.io.Serializable;
import java.util.Date;

public class AppVersionModel implements Serializable {
    public int agroup_id;
    public int app_id;
    public int ver;
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
    public int platform;
    public String url;
}