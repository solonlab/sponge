package org.noear.sponge.admin.models.others.ienum;

/**
 * @Author:Fei.chu
 * @Description: 年收入
 */
public enum SalaryEnum {
    BELOW10000(0,"10000以下"),
    AMOUNT10000TO50000(1,"10000-50000"),
    AMOUNT50000TO100000(2,"50000-100000"),
    AMOUNT100000TO200000(3,"100000-200000"),
    ABOVE200000(4,"200000以上");


    private final Integer code;
    public String value;

    SalaryEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static SalaryEnum getEnumByCode(Integer code){
        switch (code){
            case 0:
                return BELOW10000;
            case 1:
                return AMOUNT10000TO50000;
            case 2:
                return AMOUNT50000TO100000;
            case 3:
                return AMOUNT100000TO200000;
            case 4:
                return ABOVE200000;
            default:
                return BELOW10000;
        }
    }
}
