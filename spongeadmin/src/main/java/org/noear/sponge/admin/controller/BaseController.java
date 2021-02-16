package org.noear.sponge.admin.controller;

import org.noear.solon.Solon;
import org.noear.solon.annotation.Singleton;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.sponge.admin.Config;
import org.noear.sponge.admin.dso.Session;

@Singleton(false)
public abstract class BaseController {

    /*视图数据模型*/
    protected ViewModel viewModel = new ViewModel();

    /*
    * @return 输出一个视图（自动放置viewModel）
    * @param viewName 视图名字(内部uri)
    * */
    public ModelAndView view(String viewName)
    {
        //设置必要参数
        viewModel.put("root", "");

        viewModel.put("app", Solon.cfg().appTitle());

        viewModel.put("css", "/_static/css");
        viewModel.put("js", "/_static/js");
        viewModel.put("img", "/_static/img");
        viewModel.put("title", Solon.cfg().appTitle());

        //viewModel.put("push_suffix", Config.push_suffix);

        //当前用户信息(示例)
        viewModel.put("puid", Session.current().getPUID());
        viewModel.put("cn_name", Session.current().getUserName());

        //操作权限
        int isAdmin = Session.current().getIsAdmin();
        int isOperator = Session.current().getIsOperator();
        viewModel.put("isOperator",isAdmin==1||isOperator==1?1:0);
        viewModel.put("isAdmin",isAdmin);

        return viewModel.view(viewName+".jsp");
    }

    /*
     * @return 输出一个跳转视图
     * @prarm  url 可以是任何URL地址
     * */
    public void redirect(String url) {
        try {
            Context.current().redirect(url);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
