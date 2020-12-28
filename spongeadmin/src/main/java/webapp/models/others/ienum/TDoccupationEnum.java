package webapp.models.others.ienum;

public enum TDoccupationEnum {

    ADVANCED(0,"ADVANCED"),//高级资深人员
    INTERMEDIATES(1,"INTERMEDIATES"),//中级技术人员
    BEGINNERS(2,"BEGINNERS"),//初级、助理人员
    PRACTICE(3,"PRACTICE"),//见习专员
    SENIOR(4,"SENIOR"),//高层管理人员
    MIDDLE(5,"MIDDLE"),//中层管理人员
    JUNIOR(6,"JUNIOR"),//基层管理人员
    NORMAL(7,"NORMAL"),//普通员工
    ;

    private  Integer code;

    public  String value;

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    TDoccupationEnum(Integer code, String value){
         this.code = code;
        this.value = value;
    }

    public static TDoccupationEnum getEnumByCode(Integer code){
        switch (code){
            case 0:
                return ADVANCED;
            case 1:
                return INTERMEDIATES;
            case 2:
                return BEGINNERS;
            case 3:
                return PRACTICE;
            case 4:
                return SENIOR;
            case 5:
                return MIDDLE;
            case 6:
                return JUNIOR;
            case 7:
                return NORMAL;
            default:
                return ADVANCED;
        }
    }
}
