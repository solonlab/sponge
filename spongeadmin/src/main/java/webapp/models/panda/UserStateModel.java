package webapp.models.panda;

import lombok.Data;
import lombok.Setter;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;
import org.apache.http.util.TextUtils;

import java.util.Date;

/// <summary>
/// 生成:2018/08/31 10:27:54
/// 
/// </summary>
@Data
public class UserStateModel implements IBinder
{
    public long open_id;
    public String mobile;
    public String id_name;
    public int zm_score;
    public int td_score;
    public int rubber_score;
    public int borrow_record;
    public int overdue_record;
    public int credit_card;
    public int debit_card;
    public int cash_record;
    public int is_real;
    public Date real_fulltime;
    public int real_date;
    public int is_zm;
    public Date zm_fulltime;
    public int zm_date;
    public int is_operator;
    public Date operator_fulltime;
    public int operator_date;
    public int is_telbook;
    public Date telbook_fulltime;
    public int telbook_date;
    public int is_taobao;
    public Date taobao_fulltime;
    public int taobao_date;
    public int is_base;
    public Date base_fulltime;
    public int base_date;
    public int is_work;
    public Date work_fulltime;
    public int work_date;
    public int is_email_ccard;
    public Date email_ccard_fulltime;
    public int email_ccard_date;
    public int is_acum_fund;
    public Date acum_fund_fulltime;
    public int acum_fund_date;
    public int is_linkman;
    public Date linkman_fulltime;
    public int linkman_date;
    public int is_cyber_bank;
    public Date cyber_bank_fulltime;
    public int cyber_bank_date;
    public String ukey;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        open_id = s.get("open_id").value(0L);
        mobile = s.get("mobile").value(null);
        id_name = s.get("id_name").value(null);
        zm_score = s.get("zm_score").value(0);
        td_score = s.get("td_score").value(0);
        rubber_score = s.get("rubber_score").value(0);
        borrow_record = s.get("borrow_record").value(0);
        overdue_record = s.get("overdue_record").value(0);
        credit_card = s.get("credit_card").value(0);
        debit_card = s.get("debit_card").value(0);
        cash_record = s.get("cash_record").value(0);
        is_real = s.get("is_real").value(0);
        real_fulltime = s.get("real_fulltime").value(null);
        real_date = s.get("real_date").value(0);
        is_zm = s.get("is_zm").value(0);
        zm_fulltime = s.get("zm_fulltime").value(null);
        zm_date = s.get("zm_date").value(0);
        is_operator = s.get("is_operator").value(0);
        operator_fulltime = s.get("operator_fulltime").value(null);
        operator_date = s.get("operator_date").value(0);
        is_telbook = s.get("is_telbook").value(0);
        telbook_fulltime = s.get("telbook_fulltime").value(null);
        telbook_date = s.get("telbook_date").value(0);
        is_taobao = s.get("is_taobao").value(0);
        taobao_fulltime = s.get("taobao_fulltime").value(null);
        taobao_date = s.get("taobao_date").value(0);
        is_base = s.get("is_base").value(0);
        base_fulltime = s.get("base_fulltime").value(null);
        base_date = s.get("base_date").value(0);
        is_work = s.get("is_work").value(0);
        work_fulltime = s.get("work_fulltime").value(null);
        work_date = s.get("work_date").value(0);
        is_email_ccard = s.get("is_email_ccard").value(0);
        email_ccard_fulltime = s.get("email_ccard_fulltime").value(null);
        email_ccard_date = s.get("email_ccard_date").value(0);
        is_acum_fund = s.get("is_acum_fund").value(0);
        acum_fund_fulltime = s.get("acum_fund_fulltime").value(null);
        acum_fund_date = s.get("acum_fund_date").value(0);
        is_linkman = s.get("is_linkman").value(0);
        linkman_fulltime = s.get("linkman_fulltime").value(null);
        linkman_date = s.get("linkman_date").value(0);
        is_cyber_bank = s.get("is_cyber_bank").value(0);
        cyber_bank_fulltime = s.get("cyber_bank_fulltime").value(null);
        cyber_bank_date = s.get("cyber_bank_date").value(0);
        ukey = s.get("ukey").value(null);
	}
	
	public IBinder clone()
	{
		return new UserStateModel();
	}

	public String getRealStateName(int state) {
	    String stateName = "";
	    switch (state){
            case 0:stateName = "未认证";break;
            case 1:stateName = "已认证";break;
            case 2:stateName = "待补全";break;
        }
        return stateName;
    }

	public String getStateName(int state) {
        String stateName = "";
        switch (state){
            case 0:stateName = "未认证";break;
            case 1:stateName = "已认证";break;
            case 2:stateName = "认证中";break;
            case 31:stateName = "实名不符";break;
            case 32:stateName = "认证失败";break;
        }
        return stateName;
    }

    public String hideName(String name) {
        if (!TextUtils.isEmpty(name)) {
            int len = name.length();
            switch (len) {
                case 2:name = name.substring(0,1)+"*";break;
                default:name = name.substring(0,1)+"**";break;
            }
        }
        return name;
    }
}