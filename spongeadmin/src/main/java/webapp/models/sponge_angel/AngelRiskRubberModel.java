package webapp.models.sponge_angel;

import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.util.Date;

/// <summary>
/// 生成:2018/07/17 11:41:38
/// 
/// </summary>
public class AngelRiskRubberModel implements IBinder
{
    public long row_id;
    public long open_id;
    public int agroup_id;
    public long order_id;
    public String request_id;
    public int score;
    public int advise;
    public String details_url;
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
        agroup_id = s.get("agroup_id").value(0);
        order_id = s.get("order_id").value(0L);
        request_id = s.get("request_id").value(null);
        score = s.get("score").value(0);
        advise = s.get("advise").value(0);
        details_url = s.get("details_url").value(null);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
	}
	
	public IBinder clone()
	{
		return new AngelRiskRubberModel();
	}

    public static String getEvaluationEnum(int evalua) {
        //0-,1交易放行,2审慎审核,3阻断交易
        String resp = "";
        switch (evalua) {
            case -1:
                resp = "异常";
                break;
            case 0:
                resp = "-";
                break;
            case 1:
                resp = "交易放行";
                break;
            case 2:
                resp = "审慎审核";
                break;
            case 3:
                resp = "阻断交易";
                break;
            default:
                break;
        }
        return resp;
    }
}