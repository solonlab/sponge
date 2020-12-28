package org.noear.sponge.admin.models.others.ienum;

/**
 * @Author:Fei.chu
 * @Description: 同盾命中项分类
 */
public enum TDDetailTypeEnum {
    PLATFORMDETAIL("platform_detail","多头借贷类"),
    DISCREDITCOUNT("discredit_count","信贷逾期类"),
    FREQUENCYDETAIL("frequency_detail","跨事件频度统计"),
    OTHERS("others","其他");

    private final String code;
    public String value;

    TDDetailTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static TDDetailTypeEnum getEnumByCode(String code){
        switch (code){
            case "platform_detail":
                return PLATFORMDETAIL;
            case "discredit_count":
                return DISCREDITCOUNT;
            case "frequency_detail":
                return FREQUENCYDETAIL;
            case "others":
                return OTHERS;
            default:
                return OTHERS;
        }
    }
}
