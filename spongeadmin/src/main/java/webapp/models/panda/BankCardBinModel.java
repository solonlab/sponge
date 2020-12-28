package webapp.models.panda;

import lombok.Data;
import org.noear.weed.*;
import java.util.*;

/// <summary>
/// 生成:2019/03/25 02:52:12
/// 
/// </summary>
@Data
public class BankCardBinModel implements IBinder
{
    public long id;
    public String org_no;
    public String bank_name;
    public String card_type;
    public String card_name;
    public int card_length;
    public int bin_length;
    public String bin;
    public String bank_code;
    public String logo;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        id = s.get("id").value(0L);
        org_no = s.get("org_no").value(null);
        bank_name = s.get("bank_name").value(null);
        card_type = s.get("card_type").value(null);
        card_name = s.get("card_name").value(null);
        card_length = s.get("card_length").value(0);
        bin_length = s.get("bin_length").value(0);
        bin = s.get("bin").value(null);
        bank_code = s.get("bank_code").value(null);
        logo = s.get("logo").value(null);
	}
	
	public IBinder clone()
	{
		return new BankCardBinModel();
	}
}