package org.noear.sponge.admin.model.others.resp;

import lombok.Getter;

import java.util.List;

/**
 * @Author:Fei.chu
 * @Date:Created in 14:55 2018/07/26
 * @Description:
 */
@Getter
public class CallLogResp {

    public List<CallLogDetailsResp> details;
    public long count;

}
