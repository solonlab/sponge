package webapp.models.others.ienum;

public enum TDdiplomaEnum {

   PRE_HIGH_SCHOOL(0,"PRE_HIGH_SCHOOL"),//高中以下
    HIGH_SCHOOL(1,"HIGH_SCHOOL"),//高中
    JUNIOR_COLLEGE(2,"JUNIOR_COLLEGE"),//大专
    UNDER_GRADUATE(3,"UNDER_GRADUATE"),//本科
    POST_GRADUATE(4,"POST_GRADUATE");//研究僧


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

    TDdiplomaEnum(Integer code, String value){
         this.code = code;
        this.value = value;
    }

    public static TDdiplomaEnum getEnumByCode(Integer code){
        switch (code){
            case 0:
                return PRE_HIGH_SCHOOL;
            case 1:
                return HIGH_SCHOOL;
            case 2:
                return JUNIOR_COLLEGE;
            case 3:
                return UNDER_GRADUATE;
            case 4:
                return POST_GRADUATE;
            default:
                return PRE_HIGH_SCHOOL;
        }
    }

}
