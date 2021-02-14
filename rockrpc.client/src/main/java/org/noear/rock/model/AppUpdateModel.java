package org.noear.rock.model;


public class AppUpdateModel {
    public void bind(AppVersionModel version) {
        this.version = version;
        this.type = UpdateType.none;
    }

    public AppVersionModel version;
    public UpdateType type;
}