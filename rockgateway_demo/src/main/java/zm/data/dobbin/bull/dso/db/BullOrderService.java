package zm.data.dobbin.bull.dso.db;

import org.noear.solon.annotation.Inject;
import org.noear.solon.extend.aspect.annotation.Service;
import org.noear.solon.extend.data.annotation.Tran;
import org.noear.water.utils.Datetime;
import zm.data.dobbin.bull.dso.IDBuilder;
import zm.data.dobbin.bull.dso.db.mapper.BullOrderMapper;
import zm.data.dobbin.bull.models.BullOrderModel;
import zm.data.dobbin.bull.models.BullOrderStatusEnum;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
public class BullOrderService {
    @Inject
    BullOrderMapper mapper;

    //新增进件信息
    public long add_bull_order_validate(long order_id, long user_id, int app_id, long p_id, String validate_info, String submit_info) throws SQLException {
        Date now = new Date();
        int nowInt = new Datetime(now).getDate();
        return mapper.add_bull_order_validate(order_id, user_id, app_id, p_id, validate_info, submit_info, nowInt, now);
    }


    @Tran
    public long addBullOrder(int agroup_id, int ugroup_id, int app_id, long pId, long userId,
                             String mobile) throws Exception {

        Date now = new Date();
        int nowInt = new Datetime(now).getDate();

        long orderId = IDBuilder.buildBullOrderID();

        return mapper.add_bull_order(orderId,
                IDBuilder.buildBullOrderNO(orderId),
                agroup_id,
                ugroup_id,
                app_id,
                pId,
                userId,
                mobile,
                BullOrderStatusEnum.USER_CLICKED.type(),
                nowInt,
                now,
                nowInt,
                now);
    }

    //根据订单号获取订单详情
    public BullOrderModel get_bull_order_by_order_no(String order_no) throws SQLException {
        return mapper.get_bull_order_by_order_no(order_no);
    }

    public BullOrderModel get_bull_order(long userId, long p_id) throws SQLException {
        return mapper.get_bull_order(userId, p_id);
    }

    //获取订单列表
    public List<BullOrderModel> list_bull_order(long user_id, int status, long order_id, int limit) throws SQLException {
        return mapper.list_bull_order(user_id, status, order_id, limit);
    }

    public void set_bull_order_auth_status(int status, long user_id, long p_id) throws SQLException {
        Date now = new Date();
        int nowInt = new Datetime(now).getDate();
        mapper.set_bull_order_auth_status(status, nowInt, now, user_id, p_id);
    }


    //设为产品层面订单
    public void set_pm_bull_order(long user_id, long p_id) throws SQLException {
        Date now = new Date();
        int nowInt = new Datetime(now).getDate();
        mapper.set_pm_bull_order(BullOrderStatusEnum.BASIC_SUCCESS.type(), nowInt, now, nowInt, now, user_id, p_id);
    }

    //设置认证完成状态
    public void set_auth_finish_bull_order(int status, long user_id, long p_id) throws SQLException {
        Date now = new Date();
        int nowInt = new Datetime(now).getDate();
        mapper.set_auth_finish_bull_order(status, nowInt, nowInt, now, user_id, p_id);
    }


    //设为进件成功
    public void set_submit_success_bull_order(long user_id, long p_id, String outNo) throws SQLException {
        Date now = new Date();
        int nowInt = new Datetime(now).getDate();
        mapper.set_submit_success_bull_order(BullOrderStatusEnum.SUBMIT_SUCCESS.type(), outNo, nowInt, now, nowInt, now, user_id, p_id);
    }

    //设为已放款状态
    public void set_put_loan_bull_order(int status, int r_amount, int r_term, long user_id, long p_id) throws SQLException {
        Date now = new Date();
        int nowInt = new Datetime(now).getDate();
        mapper.set_put_loan_bull_order(status, r_amount, r_term, nowInt, now, user_id, p_id);
    }


    //设为逾期状态
    public void set_over_due_bull_order(int status, int over_date, long user_id, long p_id) throws SQLException {
        Date now = new Date();
        int nowInt = new Datetime(now).getDate();
        mapper.set_over_due_bull_order(status, over_date, nowInt, now, user_id, p_id);
    }

    //设为完结状态
    public void set_finish_bull_order(int status, int repay_date, long user_id, long p_id) throws SQLException {
        Date now = new Date();
        int nowInt = new Datetime(now).getDate();
        mapper.set_finish_bull_order(status, repay_date, nowInt, now, user_id, p_id);
    }

}
