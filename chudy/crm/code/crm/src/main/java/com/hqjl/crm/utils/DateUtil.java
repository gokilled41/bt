package com.hqjl.crm.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具�?
 * 
 * @author andy
 * @date 2015-5-22 下午3:56:06
 * 
 */
public class DateUtil {

    private DateUtil() {
        super();
    }

    /**
     * 使用给定的年、月、日、时、分、秒等数据生成对应的日期
     * 
     * @param year
     * @param month
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date getADate(int year, int month, int date, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, date, hour, minute, second);

        return calendar.getTime();
    }

    /**
     * 获得当前的Date，并且时、分、秒设置�?0
     * 
     * @return
     */
    public static Date getCurrDate() {
        return setZeroForHMS(new Date());
    }

    /**
     * 设置时�?�分、秒�?0
     * 
     * @param date
     * @return
     */
    public static Date setZeroForHMS(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    /**
     * 获取startDate，是以满足：endDate - startDate = days(两个日期相隔的天�?)
     */
    public static Date getStartDate(Date endDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_MONTH, -1 * days);
        return calendar.getTime();
    }

    /**
     * 获取endDate，是以满足：endDate - startDate = days(两个日期相隔的天�?)
     */
    public static Date getEndDate(Date startDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    /**
     * 获得两个日期之间的间隔天�?
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static int getDaysBetween(Date date1, Date date2) {
        Calendar d1 = Calendar.getInstance();
        d1.setTime(date1);

        Calendar d2 = Calendar.getInstance();
        d2.setTime(date2);

        if (d1.after(d2)) {// swap dates so that d1 is start and d2 is end
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }

        int years = d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR);// 年份�?
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);// 日期�?

        for (int i = 0; i < years; i++) {
            days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);// �?年的天数
            d1.add(Calendar.YEAR, 1);// 增加�?�?
        }

        return days;
    }

    public static long getMillisDiff(Date sDate, Date eDate) {
        return eDate.getTime() - sDate.getTime();
    }
}
