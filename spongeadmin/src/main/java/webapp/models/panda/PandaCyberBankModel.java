package webapp.models.panda;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.util.Date;

/// <summary>
/// 生成:2019/03/05 11:13:52
/// 
/// </summary>
@Data
public class PandaCyberBankModel implements IBinder
{
    public long row_id;
    public long open_id;
    public String hold_name;
    public int card_type;
    public String bank_name;
    public String card_code;
    public String bank_id;
    public double balance;
    public int open_date;
    public String open_bank;
    public double credit_limit;
    public double cash_balance;
    public double cash_limit;
    public double current_bill_amt;
    public double current_bill_paid_amt;
    public double current_bill_remain_amt;
    public double current_bill_remain_min_payment;
    public int current_bill_start_date;
    public int current_bill_date;
    public int payment_due_date;
    public double dcard_income;
    public double dcard_out;
    public int is_delete;
    public Date create_fulltime;
    public int create_date;
    public Date update_fulltime;
    public int update_date;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        row_id = s.get("row_id").value(0L);
        open_id = s.get("open_id").value(0L);
        hold_name = s.get("hold_name").value(null);
        card_type = s.get("card_type").value(0);
        bank_name = s.get("bank_name").value(null);
        card_code = s.get("card_code").value(null);
        bank_id = s.get("bank_id").value(null);
        balance = s.get("balance").value(0d);
        open_date = s.get("open_date").value(0);
        open_bank = s.get("open_bank").value(null);
        credit_limit = s.get("credit_limit").value(0d);
        cash_balance = s.get("cash_balance").value(0d);
        cash_limit = s.get("cash_limit").value(0d);
        current_bill_amt = s.get("current_bill_amt").value(0d);
        current_bill_paid_amt = s.get("current_bill_paid_amt").value(0d);
        current_bill_remain_amt = s.get("current_bill_remain_amt").value(0d);
        current_bill_remain_min_payment = s.get("current_bill_remain_min_payment").value(0d);
        current_bill_start_date = s.get("current_bill_start_date").value(0);
        current_bill_date = s.get("current_bill_date").value(0);
        payment_due_date = s.get("payment_due_date").value(0);
        dcard_income = s.get("dcard_income").value(0d);
        dcard_out = s.get("dcard_out").value(0d);
        is_delete = s.get("is_delete").value(0);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
	}
	
	public IBinder clone()
	{
		return new PandaCyberBankModel();
	}
}