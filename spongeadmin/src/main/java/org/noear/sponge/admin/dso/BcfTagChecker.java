package org.noear.sponge.admin.dso;

import org.noear.bcf.BcfClient;
import org.noear.bcf.models.BcfResourceModel;
import org.noear.sponge.admin.Config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//不能用静态函数
public class BcfTagChecker {
    private Map<String,String> tmpCache = null;

    private void tryLoadTagByUser() throws SQLException {
        if(tmpCache == null){
            tmpCache = new HashMap<>();

            List<BcfResourceModel> list = BcfClient.getUserResourcesByPack(Session.current().getPUID(), "tag");

            list.forEach((r) -> {
                tmpCache.put(r.en_name, r.en_name);
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
