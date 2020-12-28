package webapp.utils;

import org.noear.water.utils.Datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yuety on 2017/6/20.
 */
public class TimeUtil {
    public static final String liveTimeFormat(Date time) {
        if (time == null)
            time = new Date();

        return new Datetime(time).toString("yyyy-MM-dd HH:mm");
    }

    //获取逾期时间
    public static int getDebtDay(int pay_back_date) throws ParseException {


        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setLenient(false);
        Date newDate=formatter.parse(pay_back_date+"");

        long time1 =new Date().getTime();

        long time2 = newDate.getTime();
        int between_days = (int)Math.ceil ((double)( time1-time2) / (1000 * 3600 * 24));

        return  between_days-1;
    }

    public static long getUpdateTag() {
        return System.currentTimeMillis() / 1000 - 1500000000;
    }

    public static int getCreateDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String tdate = formatter.format(new Date());
        return Integer.parseInt(tdate);
    }

    public static String getCreateDateName() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(new Date());
    }

    public static Date getCreateTime() {
        Date now = new Date();
        return now;
    }


    public static String timeToString(Date date){
        if(date==null){
            return "";
        }else{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return formatter.format(date);
        }

    }

    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static int getDateInt(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return Integer.valueOf(sdf.format(date));
    }

    public static Date getDateFuture(Date date,int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        date = calendar.getTime();
        return date;
    }
}
