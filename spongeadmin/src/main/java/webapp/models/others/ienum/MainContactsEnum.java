package webapp.models.others.ienum;

/**
 * 主要联系人
 */
public enum  MainContactsEnum {
    //父亲
    FATHER(0,"father"),
    //母亲
    MOTHER(1,"mother"),
    //配偶
    SPOUSE(2,"spouse"),
    //子女
    CHILD(3,"child");

    private final Integer code;
    public String value;

    MainContactsEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static MainContactsEnum getEnumByCode(Integer code){
        switch (code){
            case 0:
                return FATHER;
            case 1:
                return MOTHER;
            case 2:
                return SPOUSE;
            case 3:
                return CHILD;
            default:
                return FATHER;
        }
    }

    public static String getDescribeByCode(Integer code){
        switch (code){
            case 0:
                return "父亲";
            case 1:
                return "母亲";
            case 2:
                return "配偶";
            case 3:
                return "子女";
            default:
                return "父亲";
        }
    }
}
