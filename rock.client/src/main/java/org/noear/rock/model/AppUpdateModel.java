package org.noear.rock.model;


import java.io.Serializable;

public class AppUpdateModel implements Serializable {
    public void bind(AppVersionModel version) {
        this.version = version;
        this.type = UpdateType.none;
    }

    public AppVersionModel version;
    public UpdateType type;
}