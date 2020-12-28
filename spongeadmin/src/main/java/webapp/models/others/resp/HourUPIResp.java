package webapp.models.others.resp;

import lombok.Getter;

import java.util.List;

/**
 * @Author:Fei.chu
 * @Description:
 */
@Getter
public class HourUPIResp {

    public List<String> hoursList;
    public List<Long> todays;
    public List<Long> yesterdays;
    public List<Long> beforeYesterdays;
}
