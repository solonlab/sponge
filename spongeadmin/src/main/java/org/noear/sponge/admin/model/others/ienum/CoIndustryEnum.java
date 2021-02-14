package org.noear.sponge.admin.model.others.ienum;

/**
 * @Author:Fei.chu
 * @Description: 公司行业
 */
public enum CoIndustryEnum {
    AGRICULTURE(0,"农/林/牧/渔业"),
    MINING(1,"采掘业"),
    MANUFACTURE(2,"制造业"),
    POWER(3,"电力/仓储和邮政业"),
    SOFTWARE(4,"信息传输/计算机服务和软件业"),
    WHOLESALE(5,"批发和零售业 "),
    RESTAURANT(6,"住宿和餐饮业"),
    FINANCE(7,"金融业"),
    REALTY(8,"房地产业"),
    LEASE(9,"租赁和商务服务业"),
    SCIENTIFIC(10,"科学研究/技术服务业和地址勘察业"),
    COMMUNALFACILITIES(11,"水里/环境和公共设施管理业 "),
    RESIDENTSERVICE(12,"居民服务和其他服务业"),
    EDUCATION(13,"教育 "),
    SOCIALWELFARE(14,"卫生/社会保障和社会福利业"),
    ENTERTAINMENT(15,"文化/体育和娱乐业"),
    PUBLICADMINISTRATION(16,"公共管理和社会组织"),
    INTERNATIONALORG(17,"国际组织"),
    UNKNOW(18,"未知");

    private final Integer code;
    public String value;

    CoIndustryEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static CoIndustryEnum getEnumByCode(Integer code){
        switch (code){
            case 0:
                return AGRICULTURE;
            case 1:
                return MINING;
            case 2:
                return MANUFACTURE;
            case 3:
                return POWER;
            case 4:
                return SOFTWARE;
            case 5:
                return WHOLESALE;
            case 6:
                return RESTAURANT;
            case 7:
                return FINANCE;
            case 8:
                return REALTY;
            case 9:
                return LEASE;
            case 10:
                return SCIENTIFIC;
            case 11:
                return COMMUNALFACILITIES;
            case 12:
                return RESIDENTSERVICE;
            case 13:
                return EDUCATION;
            case 14:
                return SOCIALWELFARE;
            case 15:
                return ENTERTAINMENT;
            case 16:
                return PUBLICADMINISTRATION;
            case 17:
                return INTERNATIONALORG;
            case 18:
                return UNKNOW;
            default:
                return UNKNOW;
        }
    }
}
