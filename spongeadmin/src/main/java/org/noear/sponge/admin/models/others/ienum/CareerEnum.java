package org.noear.sponge.admin.models.others.ienum;

/**
 * 职业类型
 */
public enum CareerEnum {
    FREELANCE(0,"自由职业"),
    STUDENT(1,"学生"),
    HOUSEWIFE(2,"家庭主妇"),
    RETIREMAN(3,"退休"),
    INTERNSHIPER(4,"实习生"),
    HAWKER(5,"摊贩"),
    CLEANER(6,"清洁人员"),
    AGRICULTURE(7,"农林牧业"),
    SERVICE(8,"保安/司机/服务/外送人员"),
    NOTECHNOLOGYMAN(9,"无技术工人"),
    HARVEST(10,"收获人员 "),
    BUSINESSMAN(11,"业务人员"),
    WORKER(12,"一般工人"),
    INDIVIDUALBUSINESSOWNER(13,"个体商店老板"),
    ARTWORKERS(14,"技能工作/文艺工作者"),
    SERVICEMEN(15,"现役军人"),
    OPERATOR(16,"操作人员"),
    POLICE(17,"警察/消防员"),
    ENTERPRISEBASECHARGE(18,"企业基层主管/半专业人员"),
    CHARGEENTERPRISE(19,"企业负责人、股东"),
    OFFICER(20,"军官"),
    EXECUTIVES(21,"企业高中级主管"),
    MAJOR(22,"专业人员"),
    GOVERNMENTAGENT(23,"政府官员/公务员");

    private final Integer code;
    public String value;

    CareerEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static CareerEnum getEnumByCode(Integer code){
        switch (code){
            case 0:
                return FREELANCE;
            case 1:
                return STUDENT;
            case 2:
                return HOUSEWIFE;
            case 3:
                return RETIREMAN;
            case 4:
                return INTERNSHIPER;
            case 5:
                return HAWKER;
            case 6:
                return CLEANER;
            case 7:
                return AGRICULTURE;
            case 8:
                return SERVICE;
            case 9:
                return NOTECHNOLOGYMAN;
            case 10:
                return HARVEST;
            case 11:
                return BUSINESSMAN;
            case 12:
                return WORKER;
            case 13:
                return INDIVIDUALBUSINESSOWNER;
            case 14:
                return ARTWORKERS;
            case 15:
                return SERVICEMEN;
            case 16:
                return OPERATOR;
            case 17:
                return POLICE;
            case 18:
                return ENTERPRISEBASECHARGE;
            case 19:
                return CHARGEENTERPRISE;
            case 20:
                return OFFICER;
            case 21:
                return EXECUTIVES;
            case 22:
                return MAJOR;
            case 23:
                return GOVERNMENTAGENT;
            default:
                return FREELANCE;
        }
    }
}
