package webapp.models.others.ienum;

public enum TDmarryStatEnum {

    SPINSTERHOOD(0,"SPINSTERHOOD"),//未婚
    MARRIED(1,"MARRIED"),//已婚
    DIVORCED(2,"DIVORCED"),//离异
    WIDOWED(3,"WIDOWED"),//丧偶
    REMARRY(4,"REMARRY"),//再婚
    REMARRY_FORMER(5,"REMARRY_FORMER")//复婚
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

    TDmarryStatEnum(Integer code, String value){
         this.code = code;
        this.value = value;
    }

    public static TDmarryStatEnum getEnumByCode(Integer code){
        switch (code){
            case 0:
                return SPINSTERHOOD;
            case 1:
                return MARRIED;
            case 2:
                return DIVORCED;
            case 3:
                return WIDOWED;
            case 4:
                return REMARRY;
            default:
                return REMARRY_FORMER;
        }
    }

}
