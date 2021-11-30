package org.noear.sponge.admin.dso;

import org.noear.grit.client.GritClient;
import org.noear.grit.model.domain.ResourceEntity;
import org.noear.sponge.admin.Config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//不能用静态函数
public class TagChecker {
    private Map<String,String> tmpCache = null;

    private void tryLoadTagByUser() throws SQLException {
        if(tmpCache == null){
            tmpCache = new HashMap<>();

            List<ResourceEntity> list = GritClient.global().auth()
                    .getResListByGroup(Session.current().getSubjectId(), "tag");

            list.forEach((r) -> {
                tmpCache.put(r.display_name, r.display_name);
            });
        }

    }

    public boolean find(String tag) throws SQLException {
        if (tag == null) {
            return false;
        }

        if(Session.current().getIsAdmin()==1){
            return true;
        }

        if (Config.is_use_tag_checker() == false) {
            return true;
        }

        tryLoadTagByUser();

        return tmpCache.containsKey(tag);
    }
}
