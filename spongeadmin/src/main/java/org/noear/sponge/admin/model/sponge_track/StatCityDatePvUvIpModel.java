package org.noear.sponge.admin.model.sponge_track;

import lombok.Getter;
import org.noear.wood.GetHandlerEx;
import org.noear.wood.IBinder;

/// <summary>
/// 生成:2017/12/13 01:51:26
/// 
/// </summary>
@Getter
public class StatCityDatePvUvIpModel implements IBinder
{
    public long url_id;
    public int tag_id;
    public int province_code;
    public int city_code;
    public int log_date;
    public long pv;
    public long uv;
    public long ip;
    public long uv2;

    public long _val;


	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
        url_id = s.get("url_id").value(0L);
        tag_id = s.get("tag_id").value(0);
        log_date = s.get("log_date").value(0);
        city_code = s.get("city_code").value(0);
        province_code = s.get("province_code").value(0);
        pv = s.get("pv").value(0L);
        uv = s.get("uv").value(0L);
        ip = s.get("ip").value(0L);
        uv2 = s.get("uv2").value(0L);

        _val= s.get("_val").longValue(0L);
	}
	
	public IBinder clone()
	{
		return new StatCityDatePvUvIpModel();
	}
}