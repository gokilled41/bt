package gzhou;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatter {
    public static final String DATE_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss.SSS"; // 2007-12-28 12:30:00.000
    public static final TimeZone ASIA_SHANGHAI = TimeZone.getTimeZone("Asia/Shanghai");
    public static final TimeZone PST = TimeZone.getTimeZone("PST");
    public static final TimeZone GMT = TimeZone.getTimeZone("GMT");

    public static String format(Calendar time) {
        return format(time, TimeZone.getDefault());
    }

    public static Calendar parse(String str) throws Exception {
        return parse(str, TimeZone.getDefault());
    }

    public static String format(Calendar time, TimeZone timeZone) {
        TimeZone defaultTimeZone = TimeZone.getDefault();
        TimeZone.setDefault(timeZone);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STRING);
        String result = sdf.format(time.getTime());
        TimeZone.setDefault(defaultTimeZone);
        return result;
    }

    public static Calendar parse(String str, TimeZone timeZone) throws Exception {
        TimeZone defaultTimeZone = TimeZone.getDefault();
        TimeZone.setDefault(timeZone);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STRING);
        Date date = sdf.parse(str);
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTime(date);
        TimeZone.setDefault(defaultTimeZone);
        return calendar;
    }
}
