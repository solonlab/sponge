package webapp.models.panda;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.util.Date;

/// <summary>
/// 生成:2018/08/27 05:22:23
/// 
/// </summary>
@Data
public class PandaOperatorCreditModel implements IBinder
{
    public long open_id;
    public Date register_fulltime;
    public int register_date;
    public int friends_total_count;
    public int total_call_day_count;
    public int contact_total_count;
    public int contact_total_time;
    public double average_time;
    public int call_cnt_one_month;
    public int call_cnt_in_one_month;
    public int call_cnt_out_one_month;
    public int call_cnt_three_month;
    public double p_balance;
    public double usual_phone_eval_value;
    public Date create_fulltime;
    public int create_date;
    public Date update_fulltime;
    public int update_date;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        open_id = s.get("open_id").value(0L);
        register_fulltime = s.get("register_fulltime").value(null);
        register_date = s.get("register_date").value(0);
        friends_total_count = s.get("friends_total_count").value(0);
        total_call_day_count = s.get("total_call_day_count").value(0);
        contact_total_count = s.get("contact_total_count").value(0);
        contact_total_time = s.get("contact_total_time").value(0);
        average_time = s.get("average_time").value(0d);
        call_cnt_one_month = s.get("call_cnt_one_month").value(0);
        call_cnt_in_one_month = s.get("call_cnt_in_one_month").value(0);
        call_cnt_out_one_month = s.get("call_cnt_out_one_month").value(0);
        call_cnt_three_month = s.get("call_cnt_three_month").value(0);
        p_balance = s.get("p_balance").value(0d);
        usual_phone_eval_value = s.get("usual_phone_eval_value").value(0d);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
	}
	
	public IBinder clone()
	{
		return new PandaOperatorCreditModel();
	}

    public String getFriendsTotalCount(){
        if (friends_total_count==-1){
            return "-";
        }
        return friends_total_count+"";
    }

    public String getTotalCallDayCount(){
        if (total_call_day_count==-1){
            return "-";
        }
        return total_call_day_count+"";
    }

    public String getUsualPhoneEvalValue(){
        if (usual_phone_eval_value==-1){
            return "-";
        }
        return usual_phone_eval_value+"";
    }
}