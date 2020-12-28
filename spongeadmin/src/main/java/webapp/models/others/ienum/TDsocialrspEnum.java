package webapp.models.others.ienum;

public enum TDsocialrspEnum {

    father(0,"father"),//父亲
    mother(1,"mother"),//母亲
    spouse(2,"spouse"),//配偶
    child(3,"child"),//子女
    other_relative(4,"other_relative"),//其他亲属
    friend(3,"friend"),//子女
    coworker(4,"coworker"),//其他亲属
    others(3,"others")

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

    TDsocialrspEnum(Integer code, String value){
         this.code = code;
        this.value = value;
    }

}
