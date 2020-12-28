package zm.data.dobbin.bull.models;

public enum BullOrderStatusEnum {


    // 0 用户点击过此产品
    USER_CLICKED(0),
    // 90 Identification 认证完成
    ID_SUCCESS(90),
    // 100 Basic info认证完成（记录认证时间）
    BASIC_SUCCESS(100),
    // 110 Supplementary info认证完成
    SUPPLEMENTARY_SUCCESS(110),
    // 120 Bank card info认证完成
    BANK_SUCCESS(120),
    // 200 进件成功 （记录进件时间）
    SUBMIT_SUCCESS(200),
    // 205 验真不通过
    VERIFY_FAIL(205),
    // 210 进件失败
    SUBMIT_FAIL(210),
    // 220 已放款
    PUT_LOAN(220),
    // 222 逾期
    OVER_DAY(222),
    // 230 审核被拒
    REFUSE(230),
    // 299 完成 （如果完成需再次新建状态0的订单）
    ORDER_FINISH(299);


    private int status;

    BullOrderStatusEnum(int status) {
        this.status = status;
    }

    public static BullOrderStatusEnum of(int type) {
        for (BullOrderStatusEnum activityAuctionStatus : values()) {
            if (type == activityAuctionStatus.status) {
                return activityAuctionStatus;
            }
        }
        throw new IllegalArgumentException();
    }

    public int type() {
        return status;
    }
}
