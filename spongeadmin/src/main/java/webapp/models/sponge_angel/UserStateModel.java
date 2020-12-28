package webapp.models.sponge_angel;

import lombok.Getter;
import org.noear.weed.*;
import org.apache.http.util.TextUtils;

import java.util.*;

/// <summary>
/// 生成:2018/01/16 01:43:24
/// 
/// </summary>
@Getter
public class UserStateModel implements IBinder
{
    public long open_id;
    public String mobile;
    public String id_name;
    public int zm_score;
    public int td_score;
    public int bs_score;
    public int rubber_score;
    public int borrow_record;
    public int overdue_record;
    public int credit_card;
    public int debit_card;
    public int cash_record;
    public int is_real;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        open_id = s.get("open_id").value(0L);
        mobile = s.get("mobile").value(null);
        id_name = s.get("id_name").value(null);
        zm_score = s.get("zm_score").value(0);
        td_score = s.get("td_score").value(0);
        bs_score = s.get("bs_score").value(0);
        rubber_score = s.get("rubber_score").value(0);
        borrow_record = s.get("borrow_record").value(0);
        overdue_record = s.get("overdue_record").value(0);
        credit_card = s.get("credit_card").value(0);
        debit_card = s.get("debit_card").value(0);
        cash_record = s.get("cash_record").value(0);
        is_real = s.get("is_real").value(0);

	}
	
	public IBinder clone()
	{
		return new UserStateModel();
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