package org.noear.sponge.admin.model.others.ienum;

/**
 * @Author:Fei.chu
 * @Description: 工作时间
 */
public enum WorkTimeEnum {
    BELOWONEYEAR(0,"1年以下"),
    ONEYEAR(1,"1年"),
    TWOYEAR(2,"2年"),
    THREETOFOURYEAR(3,"3-4年"),
    FIVETOSEVENYEAR(4,"5-7年"),
    EIGHTTONINEYEAR(5,"8-9年"),
    ABOVETENYEAR(6,"10年以上");


    private final Integer code;
    public String value;

    WorkTimeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static WorkTimeEnum getEnumByCode(Integer code){
        switch (code){
            case 0:
                return BELOWONEYEAR;
            case 1:
                return ONEYEAR;
            case 2:
                return TWOYEAR;
            case 3:
                return THREETOFOURYEAR;
            case 4:
                return FIVETOSEVENYEAR;
            case 5:
                return EIGHTTONINEYEAR;
            case 6:
                return ABOVETENYEAR;
            default:
                return BELOWONEYEAR;
        }
    }
}
