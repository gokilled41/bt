package gzhou;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PerformanceAnalysis implements Constants {

    public static final String performance_file = desktopDir + "System_Overview.csv";
    public static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
    public static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-M-dd HH:mm:ss.SSS");

    public static final List<Entry> list = new ArrayList<Entry>();

    static {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(performance_file)));
            String line;
            in.readLine();
            while ((line = in.readLine()) != null) {
                Entry e = new Entry(line);
                if (e.cpu >= 0)
                    list.add(e);
            }
            in.close();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public static double getAverageCPU(String start, String end) {
        try {
            return getAverageCPU(sdf2.parse(start), sdf2.parse(end));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static double getAverageCPU(Date start, Date end) {
        double total = 0;
        int count = 0;
        for (Entry entry : list) {
            Date timestamp = entry.timestamp;

            if (timestamp.compareTo(start) > 0 && timestamp.compareTo(end) < 0) {
                //System.out.println(entry.cpu);
                total += entry.cpu;
                count++;
            }
        }

        return total / count;
    }

    private static class Entry {
        private Date timestamp;
        private double cpu = -1d;

        //private double memory;
        //private double disk;

        public Entry(String line) {
            try {
                line = line.substring(1, line.length() - 1);
                String[] lines = line.split("\",\"");
                timestamp = sdf.parse(lines[0]);
                cpu = Double.parseDouble(lines[3]);
                //memory = Double.parseDouble(lines[2]);
                //disk = Double.parseDouble(lines[3]);
            } catch (Exception e) {
                //System.out.println(line);
                //e.printStackTrace();
            }
        }
    }
}
