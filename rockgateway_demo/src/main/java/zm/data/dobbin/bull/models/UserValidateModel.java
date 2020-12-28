package zm.data.dobbin.bull.models;

import java.util.Date;

public class UserValidateModel {
    /**  */
    public long user_id;

    /** 1 认证中 2 认证完成 */
    public int id_status;
    /**  */
    public int base_status;
    /**  */
    public int sup_status;
    /**  */
    public int bank_status;

    /** 闪云验证信息 */
    public String validate_info;
    /** yyyyMMdd */
    public int log_date;
    /**  */
    public Date log_fulltime;
    /** yyyyMMdd */
    public int update_date;
    /**  */
    public Date update_fulltime;

}
