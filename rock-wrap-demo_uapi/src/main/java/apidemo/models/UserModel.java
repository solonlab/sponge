package apidemo.models;

import java.util.Date;

public class UserModel {
    //
    public long user_id;
    //
    public int ugroup_id;
    //
    public int agroup_id;
    //
    public int app_id;
    //
    public String ukey;
    //
    public String lkey;
    // 微信open_id
    public String wechat_open_id;
    // 微信小程序open_id
    public String wechat_js_open_id;
    // 微信union_id
    public String wechat_union_id;

    public String iphone_id;
    // 手机号
    public String mobile;
    // 注册日期 yyyyMMdd
    public int reg_date;
    // 注册时间
    public Date reg_fulltime;
    // 创建日期 yyyyMMdd
    public int create_date;
    // 创建时间
    public Date create_fulltime;
    // 更新日期 yyyyMMdd
    public int update_date;
    // 更新时间
    public Date update_fulltime;
}
