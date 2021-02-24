package org.noear.sponge.admin.model.others.resp;

/**
 * @Author:Fei.chu
 * @Description: 芝麻信用欺诈关注名单
 */
public class ZmAntifraudRisklistResp {

    private String describe;
    private String riskLevel;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
