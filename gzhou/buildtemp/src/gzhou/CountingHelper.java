package gzhou;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountingHelper {

    public Map<String, String> getUuids(String file) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        List<Line> lines = parse(file);
        for (Line line : lines) {
            map.put(line.accountName, line.accountUuid);
        }
        return map;
    }
    
    public List<Line> parse(String file) throws Exception {
        List<String> lines = gzhou.Util.getLines(file);
        List<Line> list = new ArrayList<Line>();
        for (String l : lines) {
            list.add(toLine(l));
        }
        return list;
    }

    private Line toLine(String l) {
        String[] arr = l.split(",");
        Line line = new Line();
        try {
            line.date = arr[0];
            line.currency = arr[1];
            line.number = arr[2];
            line.type = arr[3];
            line.category = arr[4];
            line.accountName = arr[5];
            line.accountType = arr[6];
            line.address = arr[7];
            line.desc = arr[8];
            line.accountUuid = arr[9];
        } catch (Exception e) {
        }
        return line;
    }

    public static class Line {
        public String date = "";
        public String currency = "CNY";
        public String number = "";
        public String type = ""; // 支出，收入
        public String category = "";
        public String accountName = "";
        public String accountType = "";
        public String address = "";
        public String desc = "";
        public String accountUuid = "";

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(date).append(",");
            sb.append(currency).append(",");
            sb.append(number).append(",");
            sb.append(type).append(",");
            sb.append(category).append(",");
            sb.append(accountName).append(",");
            sb.append(accountType).append(",");
            sb.append(address).append(",");
            sb.append(desc).append(",");
            sb.append(accountUuid);
            return sb.toString();
        }
    }

    public static final SimpleDateFormat sdfDay1 = new SimpleDateFormat("yyyy-M-d");
    public static final SimpleDateFormat sdfDay2 = new SimpleDateFormat("yyyy-MM-dd");
    
    public void processDate(Line line) throws Exception {
        Date d = sdfDay1.parse(line.date);
        line.date = sdfDay2.format(d);
    }

}
