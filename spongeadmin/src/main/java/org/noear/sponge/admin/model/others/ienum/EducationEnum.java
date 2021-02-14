package org.noear.sponge.admin.model.others.ienum;

/**
 * 学历
 */
public enum EducationEnum {
    //高中以下
    PRE_HIGH_SCHOOL(0,"PRE_HIGH_SCHOOL"),
    //高中／中专
    HIGH_SCHOOL(1,"HIGH_SCHOOL"),
    //大专
    JUNIOR_COLLEGE(2,"JUNIOR_COLLEGE"),
    //本科
    UNDER_GRADUATE(3,"UNDER_GRADUATE"),
    //研究生
    POST_GRADUATE(4,"POST_GRADUATE");

    private final Integer code;
    public String value;

    EducationEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static EducationEnum getEnumByCode(Integer code){
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

    public static String getDescribeByCode(Integer code){
        switch (code){
            case 0:
                return "高中以下";
            case 1:
                return "高中／中专";
            case 2:
                return "大专";
            case 3:
                return "本科";
            case 4:
                return "研究生";
            default:
                return "高中以下";
        }
    }
}
