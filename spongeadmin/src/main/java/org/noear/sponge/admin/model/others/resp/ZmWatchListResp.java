package org.noear.sponge.admin.model.others.resp;

/**
 * @Author:Fei.chu
 * @Description: 芝麻信用行业关注名单
 */
public class ZmWatchListResp {

    //行业类型
    private String biz_code;
    //风险类型
    private String type;
    //命中代码
    private String code;
    //逾期金额（元）
    private String overDueAmount;
    //违约时间
    private String breakDate;
    //数据更新时间
    private String refreshDate;
    //结清状态
    private boolean settlement;

    public String getBiz_code() {
        return biz_code;
    }

    public void setBiz_code(String biz_code) {
        this.biz_code = biz_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOverDueAmount() {
        return overDueAmount;
    }

    public void setOverDueAmount(String overDueAmount) {
        this.overDueAmount = overDueAmount;
    }

    public String getBreakDate() {
        return breakDate;
    }

    public void setBreakDate(String breakDate) {
        this.breakDate = breakDate;
    }

    public String getRefreshDate() {
        return refreshDate;
    }

    public void setRefreshDate(String refreshDate) {
        this.refreshDate = refreshDate;
    }

    public boolean isSettlement() {
        return settlement;
    }

    public void setSettlement(boolean settlement) {
        this.settlement = settlement;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
