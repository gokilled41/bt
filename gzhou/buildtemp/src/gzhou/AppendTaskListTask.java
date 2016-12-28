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

public class AppendTaskListTask extends Task {

    private static final String DATE_FORMAT_STRING = "yyyy-MM-dd"; // 2007-12-28
    private static final SimpleDateFormat sdf_ = new SimpleDateFormat(DATE_FORMAT_STRING);
    private static final String STATUS_FILE = "C:\\workspace\\buildtemp\\files\\txt\\append_tasklist_task_status.txt";

    private File src_ = null;

    public void setSrc(File src) {
        src_ = src;
    }

    @Override
    public void execute() throws BuildException {
        try {

            Date lastDay = readStatus();
            int id = readId();

            id = id + 1;

            Calendar cal = Calendar.getInstance();
            Date today = cal.getTime();

            if (sdf_.format(lastDay).equals(sdf_.format(today))) {
                log("already appended for today.");
                return;
            }

            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(src_, true)));

            StringBuffer sb = new StringBuffer();
            sb.append("\n");
            sb.append(id + ". TODO " + sdf_.format(today));
            sb.append("\n");
            
            out.println(sb.toString());
            out.close();

            writeStatus(today);
            writeId(id);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Date readStatus() {
        try {
            Properties prop = new Properties();
            InputStream in = new FileInputStream(STATUS_FILE);
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

    private int readId() {
        try {
            Properties prop = new Properties();
            InputStream in = new FileInputStream(STATUS_FILE);
            prop.load(in);
            String str = prop.getProperty("id");
            int id = Integer.parseInt(str);
            in.close();
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    private void writeStatus(Date today) {
        try {
            Properties prop = new Properties();
            InputStream in = new FileInputStream(STATUS_FILE);
            prop.load(in);
            OutputStream out = new FileOutputStream(STATUS_FILE, false);
            String dateStr = sdf_.format(today);
            prop.setProperty(src_.getName(), dateStr);
            prop.store(out, null);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeId(int id) {
        try {
            Properties prop = new Properties();
            InputStream in = new FileInputStream(STATUS_FILE);
            prop.load(in);
            OutputStream out = new FileOutputStream(STATUS_FILE, false);
            String str = id + "";
            prop.setProperty("id", str);
            prop.store(out, null);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
