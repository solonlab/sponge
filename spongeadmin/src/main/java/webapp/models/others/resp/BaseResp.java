package webapp.models.others.resp;

import lombok.Getter;

/**
 * @Author:Fei.chu
 * @Description: code:1-成功 0-失败
 */
@Getter
public class BaseResp {
    public int code;
    public String msg;
}
