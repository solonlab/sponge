package webapp.models.others.resp;

/**
 * @Author:Fei.chu
 * @Description:
 */
public class TDRisksResp {
    //命中规则
    private String hitRule;
    //命中详情
    private String hitDetail;
    //风险等级
    private String riskLevel;

    public String getHitRule() {
        return hitRule;
    }

    public void setHitRule(String hitRule) {
        this.hitRule = hitRule;
    }

    public String getHitDetail() {
        return hitDetail;
    }

    public void setHitDetail(String hitDetail) {
        this.hitDetail = hitDetail;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
