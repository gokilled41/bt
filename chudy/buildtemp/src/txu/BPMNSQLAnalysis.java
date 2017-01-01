package txu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BPMNSQLAnalysis {

    public static void analyze() throws Exception {

        String dir = "D:\\Workflow-G\\workflow bug fixing\\2010-02-24 TXU\\VITR00158417 Assign Zhihui Liu [TXU]Remove unnecessary select SQLs on BPMN tables";
        String countFile = dir + "\\count.txt";

        int count = 0;
        {
            Properties p = new Properties();
            FileInputStream in = new FileInputStream(countFile);
            p.load(in);
            in.close();
            count = Integer.parseInt(p.getProperty("count"));
            count++;
            p.setProperty("count", "" + count);
            FileOutputStream out = new FileOutputStream(countFile, false);
            p.store(out, null);
            out.close();
        }

        System.out.println("count = " + count);
        System.out.println();

        String sqlfile = dir + "\\bpmn_sql_" + count + ".txt";
        String analysisfile = dir + "\\bpmn_sql_analysis_" + count + ".txt";

        {
            String log = "D:\\jedi\\yoda\\export\\home\\jboss\\server\\vtba\\log\\server.log";
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(log)));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(sqlfile)));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.indexOf("org.hibernate.SQL") > 0 && line.indexOf("VT_BPMN") > 0)
                    out.println(line);
            }
            in.close();
            out.close();
        }

        {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(sqlfile)));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(analysisfile)));
            String line;
            Map<String, Integer> map = new HashMap<String, Integer>();
            while ((line = in.readLine()) != null) {
                line = line.substring(50);
                if (map.keySet().contains(line)) {
                    map.put(line, Integer.valueOf(map.get(line).intValue() + 1));
                } else {
                    map.put(line, Integer.valueOf(1));
                }
            }
            in.close();

            int total = 0;
            for (Integer i : map.values()) {
                total += i.intValue();
            }
            System.out.println("total: " + total);
            System.out.println();
            out.println("total: " + total);
            out.println();

            for (String key : map.keySet()) {
                if (key.indexOf("VT_BPMN_PROCESS") > 0) {
                    System.out.println(key);
                    System.out.println(map.get(key));
                    out.println(key);
                    out.println(map.get(key));
                }
            }
            System.out.println();
            out.println();
            for (String key : map.keySet()) {
                if (key.indexOf("VT_BPMN_ACTIVITY") > 0) {
                    System.out.println(key);
                    System.out.println(map.get(key));
                    out.println(key);
                    out.println(map.get(key));
                }
            }
            System.out.println();
            out.println();
            for (String key : map.keySet()) {
                if (key.indexOf("VT_BPMN_GATEWAY") > 0) {
                    System.out.println(key);
                    System.out.println(map.get(key));
                    out.println(key);
                    out.println(map.get(key));
                }
            }
            System.out.println();
            out.println();
            for (String key : map.keySet()) {
                if (key.indexOf("VT_BPMN_CORRELATION") > 0) {
                    System.out.println(key);
                    System.out.println(map.get(key));
                    out.println(key);
                    out.println(map.get(key));
                }
            }

            out.close();
        }
    }
}
