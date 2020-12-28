package org.noear.sponge.admin.models.others.ienum;

/**
 * 房产状况
 */
public enum HousePropertyEnum {

    NO(0,"无房"),
    HAS_HOUSE_AND_LOAN(1,"有房有贷款"),
    HAS_HOUSE_AND_NOLOAN(2,"有房无贷款"),
    OTHER(3,"其他");

    private final Integer code;
    public String value;

    HousePropertyEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static HousePropertyEnum getEnumByCode(Integer code){
        switch (code){
            case 0:
                return NO;
            case 1:
                return HAS_HOUSE_AND_LOAN;
            case 2:
                return HAS_HOUSE_AND_NOLOAN;
            case 3:
                return OTHER;
            default:
                return NO;
        }
    }
}
