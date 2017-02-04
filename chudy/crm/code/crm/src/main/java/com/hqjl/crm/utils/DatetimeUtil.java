package com.hqjl.crm.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatetimeUtil {

    public final static String DATETIME_PATTERN = "yyyyMMddHHmmss";
    public final static String DATE_PATTERN = "yyyyMMdd";
    public final static String TIME_PATTERN = "HHmmss";
    public final static String STANDARD_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public final static String STANDARD_DATETIME_PATTERN_HM = "yyyy-MM-dd HH:mm";
    public final static String STANDARD_DATE_PATTERN = "yyyy-MM-dd";
    public final static String STANDARD_TIME_PATTERN = "HH:mm:ss";
    public final static String STANDARD_DATETIME_PATTERN_SOLIDUS = "yyyy/MM/dd HH:mm:ss";
    public final static String STANDARD_DATETIME_PATTERN_SOLIDUS_HM = "yyyy/MM/dd HH:mm";
    public final static String STANDARD_DATE_PATTERN_SOLIDUS = "yyyy/MM/dd";

    private DatetimeUtil() {
        super();
    }

    public static Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String currentDatetime() {
        return formatDate(new Date());
    }

    public static Timestamp parseDate(String dateStr, String pattern) throws ParseException {
        Date d = DatetimeUtil.parse(dateStr, pattern);
        return new Timestamp(d.getTime());
    }

    public static Timestamp parseDate(String dateStr) throws ParseException {
        return parseDate(dateStr, STANDARD_DATE_PATTERN);
    }

    public static java.sql.Date parseSQLDate(String dateStr, String pattern) throws ParseException {
        Date d = parse(dateStr, pattern);
        return new java.sql.Date(d.getTime());
    }

    public static java.sql.Date parseSQLDate(String dateStr) throws ParseException {
        Date d = parse(dateStr, STANDARD_DATE_PATTERN);
        return new java.sql.Date(d.getTime());
    }

    public static Timestamp getFutureTime(int month) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, month);
        return new Timestamp(c.getTimeInMillis());
    }

    public static String today() {
        return formatDate(new Date());
    }

    public static String formatDate(Timestamp t) {
        return formatDate(new Date(t.getTime()));
    }

    public static String formatDate(Timestamp t, String pattern) {
        return formatDate(new Date(t.getTime()), STANDARD_DATE_PATTERN);
    }

    public static String formatDate(Date date) {
        return formatDate(date, STANDARD_DATE_PATTERN);
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null)
            return null;
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static Date parse(String dateStr) {
        return parse(dateStr, STANDARD_DATE_PATTERN);
    }

    public static Date parse(String dateStr, String pattern) {

        try {
            DateFormat format = new SimpleDateFormat(pattern);
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}
