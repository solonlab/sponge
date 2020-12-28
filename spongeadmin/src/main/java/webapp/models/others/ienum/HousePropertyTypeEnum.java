package webapp.models.others.ienum;

/**
 * 房产类型
 */
public enum HousePropertyTypeEnum {
    COMMODITY_HOUSE(0,"商品房"),
    AFFORDABLE_HOUSE(1,"经济适用房"),
    COMMERCIAL_AND_RESIDENTIAL(2,"商住两用房"),
    MOVE_BACK_ROOM(3,"回迁房"),
    DEMOLITION_HOUSE(4,"拆迁房"),
    SELF_HOUSE(5,"自建房"),
    HOMESTEAD(6,"宅基地"),
    TEMPORARY_HOUSE(7,"临建房"),
    FORWARD_DELIVERY_HOUSE(8,"期房"),
    ADVANCE_BOOKING_HOUSE(9,"预售房"),
    OTHER(10,"其他 ");

    private final Integer code;
    public String value;

    HousePropertyTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static HousePropertyTypeEnum getEnumByCode(Integer code){
        switch (code){
            case 0:
                return COMMODITY_HOUSE;
            case 1:
                return AFFORDABLE_HOUSE;
            case 2:
                return COMMERCIAL_AND_RESIDENTIAL;
            case 3:
                return MOVE_BACK_ROOM;
            case 4:
                return DEMOLITION_HOUSE;
            case 5:
                return SELF_HOUSE;
            case 6:
                return HOMESTEAD;
            case 7:
                return TEMPORARY_HOUSE;
            case 8:
                return FORWARD_DELIVERY_HOUSE;
            case 9:
                return ADVANCE_BOOKING_HOUSE;
            case 10:
                return OTHER;
            default:
                return OTHER;
        }
    }
}
