package apidemo.dso.db.mapper;

import java.sql.SQLException;
import java.util.*;

import org.noear.weed.annotation.Db;
import org.noear.weed.xml.Namespace;
import apidemo.models.*;

@Db("dobbin")
@Namespace("zm.data.dobbin.bull.dso.db.mapper.BullOrderMapper")
public interface BullOrderMapper{
    //新增进件信息
    long add_bull_order_validate(long order_id, long user_id, int app_id, long p_id, String validate_info, String submit_info, int log_date, Date log_fulltime) throws SQLException;

    //新增订单
    long add_bull_order(long order_id, String order_no, int agroup_id, int ugroup_id, int app_id, long p_id, long user_id, String mobile, int status, int update_date, Date update_fulltime, int create_date, Date create_fulltime) throws SQLException;

    //根据订单号获取订单详情
    BullOrderModel get_bull_order_by_order_no(String order_no) throws SQLException;

    //获取订单详情
    BullOrderModel get_bull_order(long user_id, long p_id) throws SQLException;

    //获取订单列表
    List<BullOrderModel> list_bull_order(long user_id, int status, long order_id, int limit) throws SQLException;

    //更新认证状态
    void set_bull_order_auth_status(int status, int update_date, Date update_fulltime, long user_id, long p_id) throws SQLException;

    //设为产品层面订单
    void set_pm_bull_order(int status, int basic_date, Date basic_fulltime, int update_date, Date update_fulltime, long user_id, long p_id) throws SQLException;

    //设置认证完成状态
    void set_auth_finish_bull_order(int status, int valid_finish_date, int update_date, Date update_fulltime, long user_id, long p_id) throws SQLException;

    //设为进件成功
    void set_submit_success_bull_order(int status, String out_no, int submit_date, Date submit_fulltime, int update_date, Date update_fulltime, long user_id, long p_id) throws SQLException;

    //设为已放款状态
    void set_put_loan_bull_order(int status, int r_amount, int r_term, int update_date, Date update_fulltime, long user_id, long p_id) throws SQLException;

    //设为逾期状态
    void set_over_due_bull_order(int status, int over_date, int update_date, Date update_fulltime, long user_id, long p_id) throws SQLException;

    //设为完结状态
    void set_finish_bull_order(int status, int repay_date, int update_date, Date update_fulltime, long user_id, long p_id) throws SQLException;
}
