package org.noear.sponge.admin.models.others.ienum;

/**
 * @Author:Fei.chu
 * @Description: 同盾规则类型（哪一套规则）
 */
public enum TDRoleEnum {
    ONE(0,"规则1"),
    TWO(1,"规则2");

    private final Integer code;
    public String value;

    TDRoleEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static TDRoleEnum getEnumByCode(Integer code){
        switch (code){
            case 0:
                return ONE;
            case 1:
                return TWO;
            default:
                return ONE;
        }
    }
}
