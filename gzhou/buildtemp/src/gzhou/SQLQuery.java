package gzhou;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import txu.TimerInfoAnalysis;

public class SQLQuery {

    private static SQLQuery query_;

    private Connection conn_;

    public String user_ = "azhang";
    public String pass_ = "12345678";
    public String driver_ = "oracle.jdbc.driver.OracleDriver";
    public String url_ = "jdbc:oracle:thin:@10.101.3.81:1521:orcl";

    public SQLQuery() {
    }

    public SQLQuery(String user, String pass, String ip, String port, String sid) {
        user_ = user;
        pass_ = pass;
        url_ = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + sid;
    }

    public SQLQuery(String user, String pass, String url) {
        user_ = user;
        pass_ = pass;
        url_ = url;
    }

    public void taskCount() throws Exception {
        System.out.println("Task count: ");
        query("select count(*) from vt_wf_task");
    }

    public void taskCountNotStarted() throws Exception {
        System.out.println("Task count not startec: ");
        query("select count(*) from vt_wf_task where state = 'NotStarted'");
    }

    public void taskCountStarted() throws Exception {
        System.out.println("Task count startec: ");
        query("select count(*) from vt_wf_task where state = 'Started'");
    }

    public void taskCountClosed() throws Exception {
        System.out.println("Task count closec: ");
        query("select count(*) from vt_wf_task where state = 'Closed'");
    }

    public void activityCount() throws Exception {
        System.out.println("Activity count: ");
        query("select count(*) from vt_wf_activity");
    }

    public void jmsCount() throws Exception {
        System.out.println("WAS JMS count: ");
        query("select count(*) from sib001");
    }

    public void taskClosedTime() throws Exception {
        System.out.println("Task ended time: ");
        query("select id, ended from vt_wf_task order by ended");
    }

    public void taskPerformers() throws Exception {
        System.out.println("Task performers: ");
        query("select distinct performer from vt_wf_task order by performer");
    }

    public void taskCategories() throws Exception {
        System.out.println("Task categories: ");
        query("select distinct category from vt_wf_task order by category");
    }

    public void taskProcessNames() throws Exception {
        System.out.println("Task process names: ");
        query("select distinct process from vt_wf_task order by process");
    }

    public void attachments() throws Exception {
        System.out.println("Task attachments: ");
        query("select * from vt_dm_attachments order by last_update_time");
    }

    public void tasks() throws Exception {
        System.out.println("Task details: ");
        query("select * from vt_wf_task");
    }

    public void txu_mvi_count() throws Exception {
        System.out.println("TXU MVI count: ");
        query("select count(*) from ZCS_MVI_DOC_HDR");
    }

    public List<Object[]> txu_mvi_ext_reference() throws Exception {
        return queryWithResult("select item_guid from ZCS_MVI_DOC_HDR order by ext_reference asc");
    }

    public List<Object[]> txu_mvi_switch_doc(String id) throws Exception {
        return queryWithResult("select count(*) from eideswtdoc where ext_reference = '" + id + "'");
    }

    public List<Object[]> txu_mvi_market_16(String id) throws Exception {
        return queryWithResult("select count(*) from ZTX_TXN_MONITOR where mestyp = 'ZISU_TX814_16_OUT' and switchnum = '"
                + id + "'");
    }

    public List<Object[]> txu_mvi_market_05(String id) throws Exception {
        return queryWithResult("select count(*) from ZTX_TXN_MONITOR where mestyp = 'ZISU_TX814_05_IN' and switchnum = '"
                + id + "'");
    }

    public List<Object[]> txu_mvi_market_04(String id) throws Exception {
        return queryWithResult("select count(*) from ZTX_TXN_MONITOR where mestyp = 'ZISU_TX867_04_IN' and switchnum = '"
                + id + "'");
    }

    public void query(String sql) {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();

            int columns = metadata.getColumnCount();
            List<String[]> list = new ArrayList<String[]>();
            String[] entries = new String[columns];

            for (int i = 1; i <= columns; i++) {
                entries[i - 1] = metadata.getColumnName(i);
            }
            list.add(entries);

            int count = 0;
            while (rs.next()) {
                entries = new String[columns];
                for (int i = 1; i <= columns; i++) {
                    entries[i - 1] = tostr(rs.getObject(i));
                }
                list.add(entries);
                count++;
            }

            rs.close();
            stmt.close();

            print(list, columns);

            System.out.println("Count: " + count);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> queryWithResult(String sql) {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();

            int columns = metadata.getColumnCount();
            List<Object[]> list = new ArrayList<Object[]>();

            @SuppressWarnings("unused")
            int count = 0;
            while (rs.next()) {
                Object[] entries = new Object[columns];
                for (int i = 1; i <= columns; i++) {
                    entries[i - 1] = toObject(rs.getObject(i));
                }
                list.add(entries);
                count++;
            }

            rs.close();
            stmt.close();

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object[] queryWithResultFirstRow(String sql) {
        return queryWithResult(sql).get(0);
    }

    public void execute(String sql) {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
            //            System.out.println(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() throws Exception {
        conn_.close();
    }

    private Connection getConnection() throws Exception {
        if (conn_ != null)
            return conn_;

        Class.forName(driver_);
        conn_ = DriverManager.getConnection(url_, user_, pass_);

        return conn_;
    }

    private String tostr(Object object) throws Exception {
        if (object instanceof oracle.sql.TIMESTAMP) {
            oracle.sql.TIMESTAMP timestamp = (oracle.sql.TIMESTAMP) object;
            Timestamp time = oracle.sql.TIMESTAMP.toTimestamp(timestamp.toBytes());
            return time.toString();
        } else if (object instanceof byte[]) {
            return "byte[" + ((byte[]) object).length + "]";
        }

        return object == null ? "null" : object.toString();
    }

    public String format(String s, int length) {
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        for (int i = 0; i < length - s.length(); i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    private void print(List<String[]> list, int columns) {
        int[] lengths = new int[columns];

        for (String[] entries : list) {
            for (int i = 0; i < columns; i++) {
                lengths[i] = Math.max(lengths[i], entries[i].length());
            }
        }

        for (String[] entries : list) {
            for (int i = 0; i < columns; i++) {
                System.out.print(format(entries[i], lengths[i]));
                if (i < columns - 1)
                    System.out.print("    ");
            }
            System.out.println();
        }
    }

    public Object toObject(Object o) throws Exception {
        if (o instanceof java.math.BigDecimal) {
            return ((java.math.BigDecimal) o).intValue() + "";
        } else if (o instanceof oracle.sql.TIMESTAMP) {
            byte[] bytes = ((oracle.sql.TIMESTAMP) o).getBytes();
            Timestamp t = oracle.sql.TIMESTAMP.toTimestamp(bytes);
            return (java.util.Date) t;
        }
        return o;
    }

    public static SQLQuery getQuery() {
        if (query_ == null) {
            query_ = new SQLQuery();
        }
        return query_;
    }

    public static SQLQuery getQuery(String user, String pass, String url) {
        SQLQuery q = new SQLQuery(user, pass, url);
        setQuery(q);
        return q;
    }

    public static void setQuery(SQLQuery query) {
        SQLQuery.query_ = query;
    }

    public static void main(String[] args) throws Exception {
        SQLQuery query = new SQLQuery("sapsr3", "txu4", "jdbc:oracle:thin:@10.101.3.166:1522:txu64");
        SQLQuery.setQuery(query);
        String sql = "SELECT trigger_name FROM VT_TM_TRIGGERS";
        List<Object[]> results = query.queryWithResult(sql);

        for (Object[] objects : results) {
            String triggerName = (String) objects[0];
            TimerInfoAnalysis.analyze(triggerName);
        }

        query.close();
    }
}
