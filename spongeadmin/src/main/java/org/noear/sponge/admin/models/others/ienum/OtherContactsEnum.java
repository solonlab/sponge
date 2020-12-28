package org.noear.sponge.admin.models.others.ienum;

/**
 * 其他联系人
 */
public enum OtherContactsEnum {
    //其他亲属
    OTHER_RELATIVE(0,"other_relative"),
    //同事
    COWORKER(1,"coworker"),
    //朋友
    FRIEND(2,"friend");

    private final Integer code;
    public String value;

    OtherContactsEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static OtherContactsEnum getEnumByCode(Integer code){
        switch (code){
            case 0:
                return OTHER_RELATIVE;
            case 1:
                return COWORKER;
            case 2:
                return FRIEND;
            default:
                return OTHER_RELATIVE;
        }
    }

    public static String getDescribeByCode(Integer code){
        switch (code){
            case 0:
                return "其他亲属";
            case 1:
                return "同事";
            case 2:
                return "朋友";
            default:
                return "其他亲属";
        }
    }
}
