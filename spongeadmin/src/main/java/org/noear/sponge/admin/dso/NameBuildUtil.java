package org.noear.sponge.admin.dso;

import org.noear.sponge.admin.Config;

public class NameBuildUtil {

    public static String buildPushTagName(String tag) {
        return tag + Config.push_suffix;
    }

    public static String buildRubberName(String tag, String name) {
        return buildPushTagName(tag) + "/" + name;
    }

}
