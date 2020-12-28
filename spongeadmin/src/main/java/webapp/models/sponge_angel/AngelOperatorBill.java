package webapp.models.sponge_angel;

import lombok.Getter;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.util.Date;

/// <summary>
/// 生成:2018/01/18 02:29:35
/// 
/// </summary>
@Getter
public class AngelOperatorBill implements IBinder
{
    public int bill_start_date ;
    public double bill_fee  ;
    public int bill_month ;
    public int bill_end_date ;


	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        bill_start_date = s.get("bill_start_date").value(0);
        bill_fee = s.get("bill_fee").value(0d);
        bill_month = s.get("bill_month").value(0);
        bill_end_date = s.get("bill_end_date").value(0);
	}
	
	public IBinder clone()
	{
		return new AngelOperatorBill();
	}
}