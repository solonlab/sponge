package org.noear.sponge.admin.model.others.ienum;

/**
 * @Author:Fei.chu
 * @Description: 同盾风险等级
 */
public enum RiskLevelEnum {
    HIGH("high","高风险"),
    MEDIUM("medium","中风险"),
    LOW("low","低风险");

    private final String code;
    public String value;

    RiskLevelEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static RiskLevelEnum getEnumByCode(String code){
        switch (code){
            case "high":
                return HIGH;
            case "medium":
                return MEDIUM;
            case "low":
                return LOW;
            default:
                return MEDIUM;
        }
    }
}
