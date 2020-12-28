package org.noear.sponge.admin.controller.push;


import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Controller;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.sponge.admin.Config;
import org.noear.sponge.admin.dso.db.DbRockApi;
import org.noear.sponge.admin.models.rock.AppGroupModel;
import org.noear.sponge.admin.controller.BaseController;

import java.sql.SQLException;
import java.util.List;

/**
 * 2018.07.24
 *
 * @author konar
 */

@Controller
@Mapping("push/scheme")
public class SchemeController extends BaseController {

    @Mapping("")
    public ModelAndView scheme() throws SQLException {

        // 有Tag的app_group
        List<AppGroupModel> agroups = DbRockApi.getAppGroupWithTag();
        viewModel.set("agroups", agroups);

        // 默认打开
        if (agroups.size() > 0) {
            viewModel.set("tag", agroups.get(0).tag);
        }

        // 内嵌地址
        viewModel.set("water_rubber_url", Config.water_uri + "/rubber/scheme/inner?f=sponge&tag_name=");

        return view("push/scheme");
    }

}
