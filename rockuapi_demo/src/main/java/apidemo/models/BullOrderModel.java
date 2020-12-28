package apidemo.models;

import java.util.Date;

public class BullOrderModel {
    /**  */
    public long order_id;
    /** 内部订单号 */
    public String order_no;
    /** 应用组id */
    public int agroup_id;
    /** 用户组id */
    public int ugroup_id;
    /** 应用id */
    public int app_id;
    /** 产品id */
    public long p_id;
    /** 外部订单号 */
    public String out_no;
    /** 用户id */
    public long user_id;

    /** 注册手机号*/
    public String mobile;

    /** 订单状态
     0 用户点击过此产品
     90 Identification 认证完成
     100 Basic info认证完成（记录认证时间）
     110 Supplementary info认证完成
     120 Bank card info认证完成
     150 后台关闭该产品进件
     200 进件成功 （记录进件时间）
     205 验真不通过
     210 进件失败
     220 已放款
     222 逾期
     230 审核被拒
     299 完成 （如果完成需再次新建状态0的订单） */
    public int status;
    /** 曾经有过逾期 0没有 1有过 */
    public int l_amount;
    /** 借款期数 */
    public int l_term;
    /** 总还款金额 */
    public int r_amount;
    /** 借款期数 */
    public int r_term;
    /** 曾经有过逾期 */
    public int is_over;
    /** 还款日期 yyyyMMdd */
    public int repay_date;
    /** 过期日期 yyyyMMdd */
    public int over_date;
    /** 更新日期 yyyyMMdd */
    public int update_date;
    /** 更新时间 */
    public Date update_fulltime;
    /** 创建日期 yyyyMMdd */
    public int create_date;
    /** 创建时间 */
    public Date create_fulltime;
}
