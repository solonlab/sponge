package org.noear.sponge.admin.models.others.resp;

import java.util.Date;
import java.util.List;

/**
 * @Author:Fei.chu
 * @Description: 同盾报告
 */
public class TDReportResp {
    //报告编号
    private String reportId;
    //报告分
    private int score;
    //查询时间
    private Date queryDate;
    //多头借贷
    private List<TDRisksResp> platform;
    //信贷逾期
    private List<TDRisksResp> discredit;
    //跨事件频度统计
    private List<TDRisksResp> crossEvent;
    //其他
    private List<TDRisksResp> others;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public List<TDRisksResp> getPlatform() {
        return platform;
    }

    public void setPlatform(List<TDRisksResp> platform) {
        this.platform = platform;
    }

    public List<TDRisksResp> getDiscredit() {
        return discredit;
    }

    public void setDiscredit(List<TDRisksResp> discredit) {
        this.discredit = discredit;
    }

    public List<TDRisksResp> getCrossEvent() {
        return crossEvent;
    }

    public void setCrossEvent(List<TDRisksResp> crossEvent) {
        this.crossEvent = crossEvent;
    }

    public List<TDRisksResp> getOthers() {
        return others;
    }

    public void setOthers(List<TDRisksResp> others) {
        this.others = others;
    }
}
