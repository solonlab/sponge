package org.noear.sponge.admin.models.panda;

import lombok.Data;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.util.Date;

/// <summary>
/// 生成:2018/08/27 05:22:24
/// 
/// </summary>
@Data
public class UserExWorkInfoModel implements IBinder
{
    public long open_id;
    public int career;
    public int career_time;
    public String coname;
    public String cophone;
    public int salary;
    public int co_city_code;
    public int co_industry;
    public int conature;
    public int position;
    public String coaddress;
    public Date create_fulltime;
    public int create_date;
    public Date update_fulltime;
    public int update_date;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        open_id = s.get("open_id").value(0L);
        career = s.get("career").value(0);
        career_time = s.get("career_time").value(0);
        coname = s.get("coname").value(null);
        cophone = s.get("cophone").value(null);
        salary = s.get("salary").value(0);
        co_city_code = s.get("co_city_code").value(0);
        co_industry = s.get("co_industry").value(0);
        conature = s.get("conature").value(0);
        position = s.get("position").value(0);
        coaddress = s.get("coaddress").value(null);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
	}
	
	public IBinder clone()
	{
		return new UserExWorkInfoModel();
	}
}