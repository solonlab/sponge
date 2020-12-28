package apidemo.dso;

public class LockKeyBuilder {

    /**
     * 新建订单锁
     * @param userId
     * @return
     */
    public static String buildUserBullOrder(long userId) {
        return "LOCKER_USER_BULL_ORDER_USER_ID_" + userId;
    }
}
