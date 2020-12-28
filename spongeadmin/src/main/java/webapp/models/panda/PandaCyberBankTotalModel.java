package webapp.models.panda;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;
import org.apache.http.util.TextUtils;
import webapp.utils.DateUtil;

import java.util.Date;

/// <summary>
/// 生成:2019/03/05 11:13:52
/// 
/// </summary>
@Data
public class PandaCyberBankTotalModel implements IBinder
{
    public long row_id;
    public long open_id;
    public String bank_name;
    public String details_url_report;
    public String details_url_bill;
    public int type;
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
        bank_name = s.get("bank_name").value(null);
        details_url_report = s.get("details_url_report").value(null);
        details_url_bill = s.get("details_url_bill").value(null);
        type = s.get("type").value(0);
        is_delete = s.get("is_delete").value(0);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
	}
	
	public IBinder clone()
	{
		return new PandaCyberBankTotalModel();
	}

	public String getBillStartDate(String date) {
	    try {
            Date now = new Date();
            int nowInt = DateUtil.getDate(now);
            int dateInt = Integer.parseInt(date.replaceAll("-", ""));
            if (nowInt>dateInt){
                //已经到账单日
                Date date1 = DateUtil.addMonth(DateUtil.StrToDate(date, "yyyy-MM-dd"), -1);
                return DateUtil.format(DateUtil.addDay(date1, 1),"yyyy-MM-dd");
            } else {
                return DateUtil.format(DateUtil.addDay(DateUtil.addMonth(DateUtil.StrToDate(date, "yyyy-MM-dd"), -2),1),"yyyy-MM-dd");
            }
        } catch (Exception ex){
	        return "未知";
        }
    }

    public String getInstallmentType(String type){
	    String name = "";
	    if (!TextUtils.isEmpty(type)){
            switch (type){
                case "CONSUME":name="消费";break;
                case "CASH":name="现金";break;
                case "BILL":name="账单";break;
                default:break;
            }
        }
        return name;
    }

    public String hideName(String name) {
        if (!TextUtils.isEmpty(name)){
            String hideName = name;
            if (name.length()==2){
                hideName = name.substring(0,1)+"*";
            } else if (name.length()==3){
                hideName = name.substring(0,1)+"**";
            } else if (name.length()>3){
                hideName = name.substring(0,1)+"**";
            }
            return hideName;
        } else {
            return name;
        }
    }
}