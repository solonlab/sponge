package webapp.models.others.ienum;

/**
 * @Author:Fei.chu
 * @Description: 芝麻信用 历史最大逾期金额（元）
 */
public enum ZMOverDueAmountEnum {
    M01("M01","(0,500]"),
    M02("M02","(500,1000]"),
    M03("M03","(1000,2000]"),
    M04("M04","(2000,3000]"),
    M05("M05","(3000,4000]"),
    M06("M06","(4000,6000]"),
    M07("M07","(6000,8000]"),
    M08("M08","(8000,10000]"),
    M09("M09","(10000,15000]"),
    M10("M10","(15000,20000]"),
    M11("M11","(20000,25000]"),
    M12("M12","(25000,30000]"),
    M13("M13","(30000,40000]"),
    M14("M14","(40000,∞)");

    private final String code;
    public String value;

    ZMOverDueAmountEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static ZMOverDueAmountEnum getEnumByCode(String code){
        switch (code){
            case "M01":
                return M01;
            case "M02":
                return M02;
            case "M03":
                return M03;
            case "M04":
                return M04;
            case "M05":
                return M05;
            case "M06":
                return M06;
            case "M07":
                return M08;
            case "M09":
                return M09;
            case "M10":
                return M10;
            case "M11":
                return M11;
            case "M12":
                return M12;
            case "M13":
                return M13;
            case "M14":
                return M14;
            default:
                return M01;
        }
    }
}
