package org.noear.sponge.admin.controller.auth;

import org.noear.bcf.BcfClient;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.sponge.admin.dso.db.DbPandaApi;
import org.noear.sponge.admin.models.others.CountModel;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.dso.Session;
import org.noear.sponge.admin.models.panda.UserLkAppModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Author:Fei.chu
 * @Date:Created in 16:20 2019/06/14
 * @Description:
 */
@Controller
public class UserListController extends BaseController {

    @Mapping("auth/list")
    public ModelAndView list(Integer page,Integer pageSize,String mobile,String id_name,Integer agroup_id,Integer is_real,
                             Integer has_face,Integer date_start,Integer date_end,Integer real_date) throws SQLException{

        page = page==null?1:page;
        pageSize = (pageSize==null || pageSize==0)?16:pageSize;

        agroup_id = agroup_id==null?0:agroup_id;
        is_real = is_real==null?-1:is_real;
        has_face = has_face==null?-1:has_face;

        viewModel.put("agroup_id",agroup_id);
        viewModel.put("is_real",is_real);
        viewModel.put("has_face",has_face);
        viewModel.put("date_start",date_start);
        viewModel.put("date_end",date_end);
        viewModel.put("real_date",real_date);
        date_start = date_start==null?0:date_start;
        date_end = date_end==null?0:date_end;
        real_date = real_date==null?0:real_date;

        Map<String, Integer> agroups = DbPandaApi.getAgroupMap();
        viewModel.put("agroups",agroups);

        CountModel count = new CountModel();
        List<UserLkAppModel> list = DbPandaApi.getUserLkList(page, pageSize, mobile,id_name, agroup_id, is_real, has_face,
                count,date_start,date_end,real_date);

        boolean is_hide = BcfClient.hasResourceByUser(Session.current().getPUID(), "panda_user_content");
        if (is_hide) {
            //关键字是否脱敏权限
            viewModel.put("is_hide",0);
        } else {
            viewModel.put("is_hide",1);
        }

        viewModel.put("list",list);
        viewModel.put("pageSize", pageSize);
        viewModel.put("rowCount", count.getCount());
        viewModel.put("mobile",mobile);
        viewModel.put("id_name",id_name);

        return view("auth/list");
    }

    @Mapping("auth/list/ajax/getPlatform")
    public String getPlatform(String mobile) throws SQLException{
        return DbPandaApi.getPlatFormByMobile(mobile);
    }

}
