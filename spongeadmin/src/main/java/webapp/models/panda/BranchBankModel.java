package webapp.models.panda;

import lombok.Data;
import org.noear.weed.*;
import java.util.*;

/// <summary>
/// 生成:2019/03/25 02:52:12
/// 
/// </summary>
@Data
public class BranchBankModel implements IBinder
{
    public long id;
    public String paysys_bank;
    public String name;
    public String phone;
    public String bank_addr;
    public String bank_no;
    public String bank_code;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        id = s.get("id").value(0L);
        paysys_bank = s.get("paysys_bank").value(null);
        name = s.get("name").value(null);
        phone = s.get("phone").value(null);
        bank_addr = s.get("bank_addr").value(null);
        bank_no = s.get("bank_no").value(null);
        bank_code = s.get("bank_code").value(null);
	}
	
	public IBinder clone()
	{
		return new BranchBankModel();
	}
}