package webapp.models.panda;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.util.Date;

/// <summary>
/// 生成:2019/01/07 02:45:48
/// 
/// </summary>
@Data
public class PandaTbAccountModel implements IBinder
{
    public long open_id;
    public long account_balance;
    public long financial_balance;
    public String credit_quota;
    public String consume_quota;
    public String available_quota;
    public String jiebei_quota;
    public int zhima_point;
    public int huabei_overdue_status;
    public int huabei_overdue_history;
    public long huabei_curreny_penalty;
    public long huabei_history_penalty;
    public long jiebei_available_quota;
    public long jiebei_valid_quota;
    public long jiebei_consume_quota;
    public int jiebei_overdue_status;
    public int jiebei_overdue_history;
    public int create_date;
    public Date create_fulltime;
    public int update_date;
    public Date update_fulltime;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        open_id = s.get("open_id").value(0L);
        account_balance = s.get("account_balance").value(0L);
        financial_balance = s.get("financial_balance").value(0L);
        credit_quota = s.get("credit_quota").value(null);
        consume_quota = s.get("consume_quota").value(null);
        available_quota = s.get("available_quota").value(null);
        jiebei_quota = s.get("jiebei_quota").value(null);
        zhima_point = s.get("zhima_point").value(0);
        huabei_overdue_status = s.get("huabei_overdue_status").value(0);
        huabei_overdue_history = s.get("huabei_overdue_history").value(0);
        huabei_curreny_penalty = s.get("huabei_curreny_penalty").value(0L);
        huabei_history_penalty = s.get("huabei_history_penalty").value(0L);
        jiebei_available_quota = s.get("jiebei_available_quota").value(0L);
        jiebei_valid_quota = s.get("jiebei_valid_quota").value(0L);
        jiebei_consume_quota = s.get("jiebei_consume_quota").value(0L);
        jiebei_overdue_status = s.get("jiebei_overdue_status").value(0);
        jiebei_overdue_history = s.get("jiebei_overdue_history").value(0);
        create_date = s.get("create_date").value(0);
        create_fulltime = s.get("create_fulltime").value(null);
        update_date = s.get("update_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
	}
	
	public IBinder clone()
	{
		return new PandaTbAccountModel();
	}

    public String getHuabeiOverdueStatus() {
        String state = "";
        switch (huabei_overdue_status){
            case -1:state = "未知";
                break;
            case 0:state = "未逾期";
                break;
            case 1:state = "逾期";
                break;
            default:break;
        }
        if (open_id == 0) {
            state = "";
        }
        return state;
    }

    public String getJiebeiOverdueStatus() {
        String state = "";
        switch (jiebei_overdue_status){
            case -1:state = "未知";
                break;
            case 0:state = "未逾期";
                break;
            case 1:state = "逾期";
                break;
            default:break;
        }
        if (open_id == 0) {
            state = "";
        }
        return state;
    }
}