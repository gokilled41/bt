package gzhou;

import java.util.Calendar;

public class TimeUtil implements Constants {

    public static void print(long l) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);
        System.out.println(sdf3.format(c.getTime()));
    }

    public static void print2(long l) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);
        System.out.println(sdf2.format(c.getTime()));
    }

    public static void printLong2(String s) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(sdf2.parse(s));
        System.out.println(c.getTimeInMillis());
    }

}
