package org.noear.sponge.admin.models.others.ienum;

/**
 * @Author:Fei.chu
 * @Description: 芝麻信用行业关注 风险类型
 */
public enum ZMIndustryRiskTypeEnum {

    AA001("AA001","逾期未还款"),
    AA002("AA002","套现"),
    AB001("AB001","被执行人"),
    AC001("AC001","盗卡者/盗卡者同伙"),
    AC002("AC002","欺诈者/欺诈者同伙"),
    AC003("AC003","盗用操作/盗用者同伙"),
    AC004("AC004","盗用支出/盗用者同伙"),
    AC005("AC005","骗赔"),
    AC007("AC007","案件库黑名单"),
    AD001("AD001","汽车租赁逾期未付款"),
    AD002("AD002","汽车租赁逾期未还车"),
    AD003("AD003","汽车租赁违章逾期未付款"),
    AD004("AD004","汽车租赁其他逾期未付款"),
    AD005("AD005","单车租赁逾期未付款"),
    AD006("AD006","单车租赁逾期未还车"),
    AD007("AD007","交通工具乘用费逾期未付款"),
    AD008("AD008","路桥费逾期未付款"),
    AD009("AD009","停车费逾期未付款"),
    AD010("AD010","相关违约"),
    AE001("AE001","逾期未付款"),
    AF001("AF001","虚假交易"),
    AF002("AF002","恶意差评"),
    AF003("AF003","涉嫌售假"),
    AF004("AF004","卖家套现"),
    AF005("AF005","逾期未付款"),
    AG001("AG001","房租逾期未付款"),
    AG002("AG002","杂费逾期未付款"),
    AG003("AG003","其他违约未付款"),
    AG004("AG004","租客其他违约"),
    AG005("AG005","非正常解约"),
    AH001("AH001","逾期未付款"),
    AI003("AI003","保证保险出险"),
    AK001("AK001","逾期未付款"),
    AK002("AK002","逾期未归还");

    private final String code;
    public String value;

    ZMIndustryRiskTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static ZMIndustryRiskTypeEnum getEnumByCode(String code){
        switch (code){
            case "AA001":return AA001;
            case "AA002":return AA002;
            case "AB001":return AB001;
            case "AC001":return AC001;
            case "AC002":return AC002;
            case "AC003":return AC003;
            case "AC004":return AC004;
            case "AC005":return AC005;
            case "AC007":return AC007;
            case "AD001":return AD001;
            case "AD002":return AD002;
            case "AD003":return AD003;
            case "AD004":return AD004;
            case "AD005":return AD005;
            case "AD006":return AD006;
            case "AD007":return AD007;
            case "AD008":return AD008;
            case "AD009":return AD009;
            case "AD010":return AD010;
            case "AE001":return AE001;
            case "AF001":return AF001;
            case "AF002":return AF002;
            case "AF003":return AF003;
            case "AF004":return AF004;
            case "AF005":return AF005;
            case "AG001":return AG001;
            case "AG002":return AG002;
            case "AG003":return AG003;
            case "AG004":return AG004;
            case "AG005":return AG005;
            case "AH001":return AH001;
            case "AI003":return AI003;
            case "AK001":return AK001;
            case "AK002":return AK002;

            default:
                return AA001;
        }
    }
}
