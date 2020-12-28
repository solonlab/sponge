package webapp.controller.auth;

import com.alibaba.fastjson.JSONArray;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.MethodType;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.solon.core.handle.UploadedFile;
import webapp.controller.BaseController;
import webapp.controller.ViewModel;
import webapp.dso.ExcelUtil;
import webapp.dso.LogUtil;
import webapp.dso.Session;
import webapp.dso.db.DbPandaApi;
import webapp.models.others.CountModel;
import webapp.models.panda.RiskBlacklistModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:Fei.chu
 * @Date:Created in 15:26 2019/05/23
 * @Description:
 */
@Controller
@Mapping("auth")
public class BlacklistController extends BaseController {

    @Mapping("blacklist")
    public ModelAndView blacklist(Integer page, Integer pageSize,String key,Integer date_begin,Integer date_end,Integer agroup_id,Integer from_agroup_id,Integer reason_type) throws SQLException{

        int isOperator = Session.current().getIsOperator();
        viewModel.put("isOperator",isOperator);
        if (page == null) {
            page = 1; //从1开始（数据库那边要减1）
        }

        if (pageSize == null || pageSize == 0) {
            pageSize = 16;
        }

        viewModel.put("date_begin", date_begin);
        viewModel.put("date_end", date_end);

        if (key==null) key = "";
        if (date_begin==null) date_begin = 0;
        if (date_end==null) date_end = 0;
        if (agroup_id==null) agroup_id = 0;
        if (from_agroup_id==null) from_agroup_id = 0;
        if (reason_type==null) reason_type = 0;

        CountModel count = new CountModel();
        List<RiskBlacklistModel> blacklists = DbPandaApi.getBlacklist(page, pageSize, count, key,agroup_id,from_agroup_id,reason_type,date_begin,date_end);
        Map<Integer, String> agroup = DbPandaApi.getAgroupNameMap();

        for (RiskBlacklistModel m:blacklists) {
            m.agroup_name = agroup.get(m.agroup_id);
        }

        List<RiskBlacklistModel> agroups = DbPandaApi.getBlacklistAgroups();
        List<RiskBlacklistModel> from_agroups = DbPandaApi.getBlacklistFromAgroups();
        for (RiskBlacklistModel m:agroups) {
            m.agroup_name = agroup.get(m.agroup_id);
        }
        for (RiskBlacklistModel m:from_agroups) {
            m.from_agroup_name = agroup.get(m.from_agroup_id);
        }

        viewModel.put("pageSize", pageSize);
        viewModel.put("rowCount", count.getCount());
        viewModel.put("blacklists", blacklists);
        viewModel.put("agroups", agroups);
        viewModel.put("from_agroups", from_agroups);
        viewModel.put("key", key);
        viewModel.put("agroup_id", agroup_id);
        viewModel.put("from_agroup_id", from_agroup_id);
        viewModel.put("reason_type", reason_type);


        return view("auth/blacklist");
    }

    //删除黑名单记录
    @Mapping("blacklist/ajax/deleteBlacklist")
    public ViewModel saveEdit(Long row_id) throws SQLException {
        boolean isOk = DbPandaApi.deleteBlacklist(row_id);
        if (isOk)
            viewModel.put("code",1);
        else
            viewModel.put("code",0);
        return viewModel;
    }

    //下载示例模版
    @Mapping("blacklist/downloadExcel")
    public void downloadExcel() {
        // 表头
        String[] head = new String[]{"手机号","姓名","身份证号码","来源","作用应用","拉黑原因"};
        List<Object[]> sheet = new ArrayList<Object[]>();
        //对象
        ExcelUtil e = new ExcelUtil("工作表1", head, sheet);
        List<ExcelUtil> sheets = new ArrayList<ExcelUtil>();
        sheets.add(e);
        ExcelUtil.exportExcel("Example", sheets); //生成sheet
    }

    //导入数据
    @Mapping(value = "blacklist/uploadExcle", method = MethodType.POST)
    public ViewModel upload(UploadedFile file) throws Exception {
        try {
            JSONArray arr = ExcelUtil.readExcel(file);
            return DbPandaApi.addBlacklist(arr);
        } catch (Exception ex){
            LogUtil.error("blacklist/uploadExcle","uploadExcel",ex);
            return viewModel;
        }
    }

}
