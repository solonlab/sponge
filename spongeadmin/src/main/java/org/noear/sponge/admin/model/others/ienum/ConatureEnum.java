package org.noear.sponge.admin.model.others.ienum;

/**
 * @Author:Fei.chu
 * @Description: 公司性质
 */
public enum ConatureEnum {
    SOE(0,"国有企业"),
    JOINTVENTURE(1,"合资"),
    PRIVATE(2,"私营"),
    INDIVIDUAL(3,"个体"),
    OTHER(4,"其他");


    private final Integer code;
    public String value;

    ConatureEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static ConatureEnum getEnumByCode(Integer code){
        switch (code){
            case 0:
                return SOE;
            case 1:
                return JOINTVENTURE;
            case 2:
                return PRIVATE;
            case 3:
                return INDIVIDUAL;
            case 4:
                return OTHER;
            default:
                return PRIVATE;
        }
    }
}
