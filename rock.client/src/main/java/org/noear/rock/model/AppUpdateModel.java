package org.noear.rock.model;


import java.io.Serializable;

/**
 * 应用更新模型
 * */
public class AppUpdateModel implements Serializable {
    public void bind(AppVersionModel version) {
        this.version = version;
        this.type = UpdateType.none;
    }

    /**
     * 版本号
     * */
    public AppVersionModel version;
    /**
     * 更新类型
     * */
    public UpdateType type;
}