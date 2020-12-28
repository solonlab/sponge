package org.noear.sponge.admin.controller;

import org.noear.solon.annotation.Singleton;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.sponge.admin.Config;
import org.noear.sponge.admin.dso.Session;
import org.noear.water.utils.IPUtils;


/**
 * Created by yuety on 14-9-11.
 */
@Singleton(false)
public abstract class BaseController {

    //获取 StateSelectorTag 传来的值
    public int getState(Context request)
    {
        return getInt(request,"_state");
    }

    public int getInt(Context request, String key) {
        String p = request.param(key);

        if(p==null|| p.length()==0)
            return -1;
        else
            return Integer.parseInt(p);
    }

    public String getIP(Context request) {
        return IPUtils.getIP(request);
    }

    /*视图数据模型*/
    protected ViewModel viewModel = new ViewModel();

    /*分页默认长度(适合内容单行的列表)*/
    protected static int pageBigSize = 16;
    /*分页默认长度(适合内容两行的列表)*/
    protected static int pageSmlSize = 6;

    /*
    * @return 输出一个视图（自动放置viewModel）
    * @param viewName 视图名字(内部uri)
    * */
    public ModelAndView view(String viewName)
    {
        //设置必要参数
        viewModel.put("root", "");

        viewModel.put("app", Config.web_title);

        viewModel.put("css", "/_static/css");
        viewModel.put("js", "/_static/js");
        viewModel.put("img", "/_static/img");
        viewModel.put("title","SPONGE");

        viewModel.put("push_suffix", Config.push_suffix);

        //当前用户信息(示例)
        viewModel.put("puid", Session.current().getPUID());
        viewModel.put("cn_name", Session.current().getUserName());

        //操作权限
        int isAdmin = Session.current().getIsAdmin();
        int isOperator = Session.current().getIsOperator();
        viewModel.put("isOperator",isAdmin==1||isOperator==1?1:0);
        viewModel.put("isAdmin",isAdmin);

        return new ModelAndView(viewName+".jsp", viewModel);
    }

    /*
    * @return 输出一个跳转视图
    * @prarm  url 可以是任何URL地址
    * */
    public void redirect(String url) throws Exception{
        Context.current().redirect(url);//跳转到另一个url上
    }
}
