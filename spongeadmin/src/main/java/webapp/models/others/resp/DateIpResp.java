package webapp.models.others.resp;

import lombok.Getter;

import java.util.List;

/**
 * @Author:Yunlong.feng
 * @Description:
 */
@Getter
public class DateIpResp {
    public List<CityResp> yesterdays;
    public long max = 1;
}
