package org.noear.sponge.admin.utils;

import java.util.Comparator;

/**
 * @author noear 2021/9/8 created
 */
public class MapKeyComparator implements Comparator<String> {
    @Override
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }
}
