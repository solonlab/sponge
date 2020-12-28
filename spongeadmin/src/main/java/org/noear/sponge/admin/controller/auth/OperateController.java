package org.noear.sponge.admin.controller.auth;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.dso.ExcelUtil;
import org.noear.sponge.admin.dso.db.DbPandaApi;
import org.noear.sponge.admin.models.panda.UserStateModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Fei.chu
 * @Date:Created in 10:12 2019/03/29
 * @Description:运营管理
 */
@Controller
public class OperateController extends BaseController {

    //认证数量导出视图
    @Mapping("auth/operate_export")
    public ModelAndView operateExport() {
        return view("auth/operate_export");
    }

    @Mapping("auth/operate_export/doExport")
    public void export(Boolean identification, Boolean operator, Boolean taobao, Boolean base,
                       Boolean work, Boolean linkman, Integer date_begin, Integer date_end) throws Exception{

        // 表头
        String[] head = new String[]{"ukey","用户id","认证日期","认证时间"};
        //表格内容
        List<Object[]> sheet1 = new ArrayList<Object[]>();
        if (identification!=null){
            List<UserStateModel> list = DbPandaApi.getStateList("identification", date_begin, date_end);
            int size = list.size();
            Object[] objs = null;
            for (int i = 0; i < size; i++) {
                objs = new Object[head.length];
                objs[0] = list.get(i).ukey+"";
                objs[1] = list.get(i).open_id+"";
                objs[2] = list.get(i).real_date+"";
                objs[3] = list.get(i).real_fulltime+"";
                sheet1.add(objs);
            }
        }

        List<Object[]> sheet2 = new ArrayList<Object[]>();
        if (operator!=null){
            List<UserStateModel> list = DbPandaApi.getStateList("operator", date_begin, date_end);

            int size = list.size();
            Object[] objs = null;
            for (int i = 0; i < size; i++) {
                objs = new Object[head.length];
                objs[0] = list.get(i).ukey+"";
                objs[1] = list.get(i).open_id;
                objs[2] = list.get(i).operator_date;
                objs[3] = list.get(i).operator_fulltime;
                sheet2.add(objs);
            }
        }


        List<Object[]> sheet3 = new ArrayList<Object[]>();
        if (taobao!=null){
            List<UserStateModel> list = DbPandaApi.getStateList("taobao", date_begin, date_end);

            int size = list.size();
            Object[] objs = null;
            for (int i = 0; i < size; i++) {
                objs = new Object[head.length];
                objs[0] = list.get(i).ukey+"";
                objs[1] = list.get(i).open_id;
                objs[2] = list.get(i).taobao_date;
                objs[3] = list.get(i).taobao_fulltime;
                sheet3.add(objs);
            }
        }

        List<Object[]> sheet4 = new ArrayList<Object[]>();
        if (base!=null){
            List<UserStateModel> list = DbPandaApi.getStateList("base", date_begin, date_end);

            int size = list.size();
            Object[] objs = null;
            for (int i = 0; i < size; i++) {
                objs = new Object[head.length];
                objs[0] = list.get(i).ukey+"";
                objs[1] = list.get(i).open_id;
                objs[2] = list.get(i).base_date;
                objs[3] = list.get(i).base_fulltime;
                sheet4.add(objs);
            }
        }

        List<Object[]> sheet5 = new ArrayList<Object[]>();
        if (work!=null){
            List<UserStateModel> list = DbPandaApi.getStateList("work", date_begin, date_end);

            int size = list.size();
            Object[] objs = null;
            for (int i = 0; i < size; i++) {
                objs = new Object[head.length];
                objs[0] = list.get(i).ukey+"";
                objs[1] = list.get(i).open_id;
                objs[2] = list.get(i).work_date;
                objs[3] = list.get(i).work_fulltime;
                sheet5.add(objs);
            }
        }

        List<Object[]> sheet6 = new ArrayList<Object[]>();
        if (linkman!=null){
            List<UserStateModel> list = DbPandaApi.getStateList("linkman", date_begin, date_end);

            int size = list.size();
            Object[] objs = null;
            for (int i = 0; i < size; i++) {
                objs = new Object[head.length];
                objs[0] = list.get(i).ukey+"";
                objs[1] = list.get(i).open_id;
                objs[2] = list.get(i).linkman_date;
                objs[3] = list.get(i).linkman_fulltime;
                sheet6.add(objs);
            }
        }

        //对象
        ExcelUtil e1 = new ExcelUtil("身份认证", head, sheet1);
        ExcelUtil e2 = new ExcelUtil("运营商", head, sheet2);
        ExcelUtil e3 = new ExcelUtil("淘宝", head, sheet3);
        ExcelUtil e4 = new ExcelUtil("基本信息", head, sheet4);
        ExcelUtil e5 = new ExcelUtil("工作信息", head, sheet5);
        ExcelUtil e6 = new ExcelUtil("联系人", head, sheet6);

        List<ExcelUtil> sheets = new ArrayList<ExcelUtil>();
        if (identification!=null){
            sheets.add(e1);
        }
        if (operator!=null){
            sheets.add(e2);
        }
        if (taobao!=null){
            sheets.add(e3);
        }
        if (base!=null){
            sheets.add(e4);
        }
        if (work!=null){
            sheets.add(e5);
        }
        if (linkman!=null){
            sheets.add(e6);
        }

        ExcelUtil.exportExcel(date_begin+"-"+date_end, sheets); //生成sheet
    }
}
