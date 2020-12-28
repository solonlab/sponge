package webapp.controller.auth;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;
import webapp.controller.BaseController;
import webapp.dso.db.DbPandaApi;
import webapp.models.others.CountModel;
import webapp.models.panda.RiskReportOrderModel;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author:Fei.chu
 * @Date:Created in 10:33 2019/04/28
 * @Description:信用报告流水
 */
@Controller
public class ReportOrderController extends BaseController {

    //列表
    @Mapping("auth/order")
    public ModelAndView query(Integer page, Integer pageSize, String mobile,String order_no,String out_order_no,Integer create_date_begin,
                              Integer create_date_end,Integer finish_date_begin,Integer finish_date_end,Integer pay_type,
                              Integer type,Integer status) throws SQLException {
        if (page == null) {
            page = 1; //从1开始（数据库那边要减1）
        }

        if (pageSize == null || pageSize == 0) {
            pageSize = 12;
        }

        viewModel.put("create_date_begin",create_date_begin);
        viewModel.put("create_date_end",create_date_end);
        viewModel.put("finish_date_begin",finish_date_begin);
        viewModel.put("finish_date_end",finish_date_end);

        if (pay_type==null){
            pay_type = 0;
        }
        if (type==null){
            type = 0;
        }
        if (status==null){
            status = 0;
        }

        if (create_date_begin==null){
            create_date_begin = 0;
        }
        if (create_date_end==null){
            create_date_end = 0;
        }
        if (finish_date_begin==null){
            finish_date_begin = 0;
        }
        if (finish_date_end==null){
            finish_date_end = 0;
        }

        CountModel count = new CountModel();

        List<RiskReportOrderModel> orders = DbPandaApi.getRiskReportOrders(page, pageSize, count, mobile,order_no,out_order_no,create_date_begin,
                create_date_end,finish_date_begin,finish_date_end,pay_type,type,status);

        viewModel.put("order_no",order_no);
        viewModel.put("out_order_no",out_order_no);
        viewModel.put("pay_type",pay_type);
        viewModel.put("type",type);
        viewModel.put("status",status);

        viewModel.put("pageSize", pageSize);
        viewModel.put("rowCount", count.getCount());
        viewModel.put("orders", orders);
        viewModel.put("mobile", mobile);

        return view("auth/order");
    }
}
