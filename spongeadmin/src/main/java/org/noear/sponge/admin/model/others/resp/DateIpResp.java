package org.noear.sponge.admin.model.others.resp;

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
