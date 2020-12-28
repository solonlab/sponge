package zm.data.dobbin.bull.models;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;
import java.math.BigDecimal;
import java.util.*;

@Data
public class CoProductModel {

    /**  */
    public long product_id;
    /** 合作伙伴id */
    public long partner_id;
    /** 合作伙伴名称 */
    public String partner_name;
    /** logo地址 */
    public String logo;
    /** 产品名 */
    public String product_name;
    /** 跳转链接 */
    public String link_url;
    /** 可贷金额下限 */
    public String loan_min;
    /** 可贷金额上限 */
    public String loan_max;
    /** 放款时间 */
    public String loan_giventime;
    /** 利息 */
    public double loan_interest;
    /** 利息计量方式: 0月息   1日息 */
    public int loan_interest_unit;
    /** 可借款时间 */
    public String loan_limit_time;
    /** 申请资格 */
    public String loan_qualification;
    /** 结算方式 */
    public String settle;
    /** 产品类型：0借贷产品  1游戏 */
    public int type;
    /** 状态：0入库中 1已出库  */
    public int status;
    /** 操作人 */
    public String operator;
    /** yyyyMMdd */
    public int log_date;
    /**  */
    public Date log_fulltime;
    /** 应用组id */
    public int agroup_id;
    /** 附件名 */
    public String file_name;
    /** 附件地址 */
    public String file_url;
    /** 链接类型1gp  2apk  3落地页  */
    public int link_type;
    /** 0-不开启后置登录；1-中间页登录；2-直接登录 */
    public int is_postlogin;
    /** 0-不开启中间页；1-开启中间页 */
    public int is_middlepage;
    /** 0-不是api产品；1-是 */
    public int bull_api_product;
    /** 产品简介 */
    public String bull_product_intro;
    /** 产品访问key */
    public String bull_product_key;
    /** 最大进件数 */
    public int bull_submit_max;
    /** 进件状态0关闭 1开启 */
    public int bull_submit_state;
    /** 借贷产品api相关操作更新时间 */
    public Date bull_update_time;

}
