import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.codec.binary.Hex;

public class DatabaseTool {

    public static final SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void main(String[] args) throws Exception {
        String op = args[0];
        args = cutFirstArg(args);
        if (op.equals("backup")) {
            backup(args);
        } else {
            restore(args);
        }
    }

    public static void backup(String[] args) throws Exception {
        if (args.length < 2) {
            log("backup <sql> <outputFile>");
            return;
        }

        String sql = args[0];
        String outputFile = args[1];

        if (sql == null || sql.isEmpty() || outputFile == null || outputFile.isEmpty()) {
            log("backup <sql> <outputFile>");
            return;
        }

        Properties p = getProperties("../conf/driver.properties");
        String driver = p.getProperty("driver");
        String url = p.getProperty("url");
        String user = p.getProperty("user");
        String password = p.getProperty("password");
        log(format("execute \"{0}\" on \"{1}\".", sql, url));

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        if (sql.toLowerCase().startsWith("select")) {
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int n = md.getColumnCount();
            // data
            List<List<String>> lists = new ArrayList<List<String>>();
            List<String> header = new ArrayList<String>();
            for (int i = 1; i <= n; i++) {
                String cn = md.getColumnName(i);
                header.add(cn);
            }
            lists.add(header);
            List<String> types = new ArrayList<String>();
            for (int i = 1; i <= n; i++) {
                String cn = "" + md.getColumnType(i);
                types.add(cn);
            }
            lists.add(types);
            while (rs.next()) {
                List<String> row = new ArrayList<String>();
                for (int i = 1; i <= n; i++) {
                    Object o = rs.getObject(i);
                    String cv = formatColumnObject(o);
                    row.add(cv);
                }
                lists.add(row);
            }
            // size
            List<Integer> sizes = new ArrayList<Integer>();
            for (int i = 0; i < n; i++) {
                List<Integer> sl = new ArrayList<Integer>();
                for (List<String> l : lists) {
                    sl.add(getWordCount(l.get(i)));
                }
                Integer size = Collections.max(sl);
                sizes.add(size);
            }
            int recordsSize = lists.size() - 2;
            if (recordsSize <= 0) {
                System.out.println("no records.");
                return;
            } else {
                System.out.println(format("output {0} records into file: {1}", recordsSize, outputFile));
            }
            // print
            StringBuilder sb = new StringBuilder();
            for (List<String> l : lists) {
                for (int i = 0; i < n; i++) {
                    int size = sizes.get(i);
                    String v = l.get(i);
                    sb.append(formatColumn(v, size + 2, true));
                }
                sb.append(LINE_SEPARATOR);
            }
            String output = sb.toString();
            setLines(outputFile, toList(output), "UTF-8");
        } else {
            stmt.executeUpdate(sql);
            log("execute successfully.");
        }
        stmt.close();
        conn.close();
    }

    public static void restore(String[] args) throws Exception {
        if (args.length < 2) {
            log("restore <tableName> <outputFile>");
            return;
        }

        String tableName = args[0];
        String outputFile = args[1];

        if (tableName == null || tableName.isEmpty() || outputFile == null || outputFile.isEmpty()) {
            log("restore <tableName> <outputFile>");
            return;
        }

        Properties p = getProperties("../conf/driver.properties");
        String driver = p.getProperty("driver");
        String url = p.getProperty("url");
        String user = p.getProperty("user");
        String password = p.getProperty("password");

        // get data
        List<String> lines = getLines(outputFile, "UTF-8");
        String header = lines.get(0);
        lines.remove(0);
        List<String> headers = splitToList(header, " ");
        String typesStr = lines.get(0);
        lines.remove(0);
        List<String> types = splitToList(typesStr, " ");

        // parameters
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < headers.size(); i++) {
            sb.append("?");
            if (i < headers.size() - 1)
                sb.append(",");
        }
        sb.append(")");
        String parameters = sb.toString();

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);
        String sql = "insert into " + tableName + " values " + parameters;

        log(format("execute \"{0}\" on \"{1}\".", sql, url));

        for (int k = 0; k < lines.size(); k++) {
            String line = lines.get(k);
            if (!line.isEmpty()) {
                List<String> columnValues = splitToList(line, " ");
                List<Object> columnObjects = toObjects(columnValues, types);

                PreparedStatement stmt = conn.prepareStatement(sql);
                for (int i = 0; i < columnObjects.size(); i++) {
                    int t = toInt(types.get(i));
                    Object v = columnObjects.get(i);
                    setObject(stmt, i + 1, v, t);
                }
                stmt.executeUpdate();
                stmt.close();
                System.out.println("insert line: " + (k + 1));
            }
        }
        conn.close();
    }

    private static void setObject(PreparedStatement stmt, int i, Object v, int t) throws Exception {
        if (t == 2004) {
            byte[] buf = (byte[]) v;
            InputStream is = new ByteArrayInputStream(buf);
            stmt.setBinaryStream(i, is);
        } else {
            stmt.setObject(i, v, t);
        }
    }

    private static List<Object> toObjects(List<String> columnValues, List<String> types) throws Exception {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < columnValues.size(); i++) {
            int t = toInt(types.get(i));
            String v = columnValues.get(i);
            Object o = toObject(v, t);
            list.add(o);
        }
        return list;
    }

    private static Object toObject(String v, int t) throws Exception {
        if (t == 2) {
            return Long.valueOf(v);
        }
        if (t == 2004) {
            return Hex.decodeHex(v.toCharArray());
        }
        if (v.equals("null"))
            return null;
        return v;
    }

    private static void log(String m) {
        System.out.println(m);
    }

    private static String[] cutFirstArg(String[] args) {
        int n = args.length;
        String[] args2 = new String[n - 1];
        for (int i = 0; i < args2.length; i++) {
            args2[i] = args[i + 1];
        }
        return args2;
    }

    private static Properties getProperties(String propertyFile) throws Exception {
        try {
            FileInputStream fis = new FileInputStream(propertyFile);
            Properties props = new Properties();
            props.load(fis);
            fis.close();
            return props;
        } catch (Exception e) {
            throw new Exception("Cannot load properties: " + propertyFile, e);
        }
    }

    private static String format(String message, Object... objects) {
        message = MessageFormat.format(message, objects);
        return message;
    }

    private static String formatColumnObject(Object o) throws Exception {
        if (o == null)
            return "null";
        if (o instanceof Date) {
            return sdf4.format(o);
        } else if (o instanceof Blob) {
            Blob b = (Blob) o;
            byte[] bytes = b.getBytes((long) 1, (int) b.length());
            return new String(Hex.encodeHex(bytes));
        } else {
            return o.toString();
        }
    }

    private static int getWordCount(String s) {
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = Character.codePointAt(s, i);
            if (ascii >= 0 && ascii <= 255)
                length++;
            else
                length += 2;

        }
        return length;

    }

    private static String formatColumn(String s, int n, boolean left) {
        int len = getWordCount(s);
        if (len < n) {
            for (int i = 0; i < n - len; i++) {
                if (left)
                    s = s + " ";
                else
                    s = " " + s;
            }
        }
        return s;
    }

    private static List<String> toList(String string) {
        List<String> list = new ArrayList<String>();
        if (string != null) {
            list.add(string);
        }
        return list;
    }

    private static void setLines(String filePath, List<String> lines, String encoding) throws Exception {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath, false), encoding));
        for (String line : lines) {
            out.println(line);
        }
        out.close();
    }

    private static List<String> getLines(String path, String encoding) throws Exception {
        List<String> list = new ArrayList<String>();
        InputStream is = new FileInputStream(path);
        BufferedReader in = new BufferedReader(new UnicodeReader(is, encoding));
        String line;
        while ((line = in.readLine()) != null) {
            list.add(line);
        }
        in.close();
        return list;
    }

    private static List<String> splitToList(String listString, String separator) {
        List<String> list = new ArrayList<String>();
        if (listString != null) {
            String[] strings = listString.split(separator);
            for (String string : strings) {
                String item = string.trim();
                if (item != null && item.length() > 0) {
                    list.add(item);
                }
            }
        }
        return list;
    }

    private static int toInt(String s) {
        return Integer.valueOf(s);
    }

    private static class UnicodeReader extends Reader {
        PushbackInputStream internalIn;
        InputStreamReader internalIn2 = null;
        String defaultEnc;

        private static final int BOM_SIZE = 4;

        public UnicodeReader(InputStream in, String defaultEnc) {
            internalIn = new PushbackInputStream(in, BOM_SIZE);
            this.defaultEnc = defaultEnc;
        }

        protected void init() throws IOException {
            if (internalIn2 != null)
                return;

            String encoding;
            byte bom[] = new byte[BOM_SIZE];
            int n, unread;
            n = internalIn.read(bom, 0, bom.length);

            if ((bom[0] == (byte) 0x00) && (bom[1] == (byte) 0x00) && (bom[2] == (byte) 0xFE)
                    && (bom[3] == (byte) 0xFF)) {
                encoding = "UTF-32BE";
                unread = n - 4;
            } else if ((bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE) && (bom[2] == (byte) 0x00)
                    && (bom[3] == (byte) 0x00)) {
                encoding = "UTF-32LE";
                unread = n - 4;
            } else if ((bom[0] == (byte) 0xEF) && (bom[1] == (byte) 0xBB) && (bom[2] == (byte) 0xBF)) {
                encoding = "UTF-8";
                unread = n - 3;
            } else if ((bom[0] == (byte) 0xFE) && (bom[1] == (byte) 0xFF)) {
                encoding = "UTF-16BE";
                unread = n - 2;
            } else if ((bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE)) {
                encoding = "UTF-16LE";
                unread = n - 2;
            } else {
                encoding = defaultEnc;
                unread = n;
            }

            if (unread > 0)
                internalIn.unread(bom, (n - unread), unread);

            if (encoding == null) {
                internalIn2 = new InputStreamReader(internalIn);
            } else {
                internalIn2 = new InputStreamReader(internalIn, encoding);
            }
        }

        public void close() throws IOException {
            init();
            internalIn2.close();
        }

        public int read(char[] cbuf, int off, int len) throws IOException {
            init();
            return internalIn2.read(cbuf, off, len);
        }

    }
}
