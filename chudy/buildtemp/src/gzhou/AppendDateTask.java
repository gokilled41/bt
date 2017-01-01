package gzhou;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class AppendDateTask extends Task {

    protected static final String DATE_FORMAT_STRING = "yyyy-MM-dd"; // 2007-12-28
    protected static final SimpleDateFormat sdf_ = new SimpleDateFormat(DATE_FORMAT_STRING);
    protected static final String APPEND_DATE_TASK_STATUS = "C:\\workspace\\buildtemp\\files\\txt\\append_date_task_status.txt";

    protected File src_ = null;
    protected boolean continuous_ = false;

    public void setSrc(File src) {
        src_ = src;
    }

    public void setContinuous(boolean continuous) {
        continuous_ = continuous;
    }

    @Override
    public void execute() throws BuildException {
        try {

            Date lastDay = readStatus();
            Calendar cal = Calendar.getInstance();
            Date today = cal.getTime();

            if (sdf_.format(lastDay).equals(sdf_.format(today))) {
                log("already appended for today.");
                return;
            }

            boolean isAppendLineSeparator = !isContinuous(lastDay, today);

            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(src_, true)));

            StringBuffer sb = new StringBuffer();
            sb.append("\n");

            if (isAppendLineSeparator && !continuous_) {
                sb.append("*******************************************************");
                sb.append("\n");
                sb.append("\n");
            } else {
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(lastDay);
                while (true) {
                    cal2.add(Calendar.DAY_OF_YEAR, 1);

                    if (cal2.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)) {
                        break;
                    } else {
                        printOneDay(sb, cal2.getTime());
                        sb.append("\n");
                        sb.append("\n");
                    }
                }
            }

            printOneDay(sb, today);
            out.println(sb.toString());
            out.close();

            writeStatus(today);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void printOneDay(StringBuffer sb, Date day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        String line = sdf_.format(cal.getTime()) + " " + getWeekday(cal.get(Calendar.DAY_OF_WEEK));
        sb.append(line);
        log(line);
    }

    private static String getWeekday(int i) {
        String result = null;
        switch (i) {
        case Calendar.SUNDAY:
            result = "周日";
            break;
        case Calendar.MONDAY:
            result = "周一";
            break;
        case Calendar.TUESDAY:
            result = "周二";
            break;
        case Calendar.WEDNESDAY:
            result = "周三";
            break;
        case Calendar.THURSDAY:
            result = "周四";
            break;
        case Calendar.FRIDAY:
            result = "周五";
            break;
        case Calendar.SATURDAY:
            result = "周六";
            break;
        default:
            break;
        }

        return result;
    }

    private boolean isContinuous(Date lastDay, Date today) {
        Calendar lastCal = Calendar.getInstance();
        lastCal.setTime(lastDay);
        lastCal.get(Calendar.YEAR);

        Calendar todayCal = Calendar.getInstance();
        todayCal.setTime(today);
        todayCal.get(Calendar.YEAR);

        lastCal.add(Calendar.DAY_OF_MONTH, 1);

        return lastCal.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR)
                && lastCal.get(Calendar.MONTH) == todayCal.get(Calendar.MONTH)
                && lastCal.get(Calendar.DAY_OF_MONTH) == todayCal.get(Calendar.DAY_OF_MONTH);

    }

    private Date readStatus() {
        try {
            Properties prop = new Properties();
            InputStream in = new FileInputStream(APPEND_DATE_TASK_STATUS);
            prop.load(in);
            String dateStr = prop.getProperty(src_.getName());
            Date lastDay = sdf_.parse(dateStr);
            in.close();
            return lastDay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeStatus(Date today) {
        try {
            Properties prop = new Properties();
            InputStream in = new FileInputStream(APPEND_DATE_TASK_STATUS);
            prop.load(in);
            OutputStream out = new FileOutputStream(APPEND_DATE_TASK_STATUS, false);
            String dateStr = sdf_.format(today);
            prop.setProperty(src_.getName(), dateStr);
            prop.store(out, null);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
