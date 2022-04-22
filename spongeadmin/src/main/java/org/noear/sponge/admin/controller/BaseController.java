package org.noear.sponge.admin.controller;

import org.noear.solon.Solon;
import org.noear.solon.annotation.Singleton;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.sponge.admin.Config;
import org.noear.sponge.admin.dso.Session;
import org.noear.water.WW;

@Singleton(false)
public abstract class BaseController {

    /*视图数据模型*/
    protected ViewModel viewModel = new ViewModel();

    /*
     * @return 输出一个视图（自动放置viewModel）
     * @param viewName 视图名字(内部uri)
     * */
    public ModelAndView view(String viewName) {
        //设置必要参数
        viewModel.put("root", "");

        viewModel.put("app", Solon.cfg().appTitle());

        viewModel.put("css", "/_static/css");
        viewModel.put("js", "/_static/js");
        viewModel.put("img", "/_static/img");
        viewModel.put("title", Solon.cfg().appTitle());

        //当前用户信息, new
        viewModel.put("user_id", Session.current().getSubjectId());
        viewModel.put("user_display_name", Session.current().getDisplayName());
        viewModel.put("_version", Config.sponge_version);

        ///操作权限
        int is_admin = Session.current().getIsAdmin();
        int is_operator = Session.current().getIsOperator();
        if (is_admin == 1) {
            is_operator = 1;
        }

        viewModel.put("isOperator", is_operator);
        viewModel.put("isAdmin", is_admin);

        return viewModel.view(viewName + ".jsp");
    }
}