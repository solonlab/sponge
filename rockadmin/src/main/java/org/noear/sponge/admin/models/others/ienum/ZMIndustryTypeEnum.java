package org.noear.sponge.admin.models.others.ienum;

/**
 * @Author:Fei.chu
 * @Description:
 */
public enum ZMIndustryTypeEnum {
    AA("AA","金融信贷类"),
    AB("AB","公检法"),
    AC("AC","支付行业"),
    AD("AD","出行行业"),
    AE("AE","酒店行业"),
    AF("AF","电商行业"),
    AG("AG","租房行业"),
    AH("AH","运营商行业"),
    AI("AI","保险行业"),
    AK("AK","租赁行业");

    private final String code;
    public String value;

    ZMIndustryTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static ZMIndustryTypeEnum getEnumByCode(String code){
        switch (code){
            case "AA":
                return AA;
            case "AB":
                return AB;
            case "AC":
                return AC;
            case "AD":
                return AD;
            case "AE":
                return AE;
            case "AF":
                return AF;
            case "AG":
                return AG;
            case "AH":
                return AH;
            case "AI":
                return AI;
            case "AK":
                return AK;
            default:
                return AA;
        }
    }
}
