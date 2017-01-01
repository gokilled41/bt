package gzhou;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class TaskListTask extends Task implements Constants {

    private boolean all_ = false;

    public void setAll(boolean all) {
        all_ = all;
    }

    @Override
    public void execute() throws BuildException {
        try {
            // list
            List<String> lines = excuteCommand("tasklist");
            lines.remove(0);
            for (int i = 0; i < 2; i++) {
                System.out.println(lines.remove(0));
            }

            // sort
            Comparator<String> c = new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String m1 = getMemoryFromResult(o1);
                    String m2 = getMemoryFromResult(o2);
                    Integer i1 = (m1 == null ? 0 : Integer.valueOf(m1));
                    Integer i2 = (m2 == null ? 0 : Integer.valueOf(m2));
                    return (-1) * i1.compareTo(i2);
                }
            };
            Collections.sort(lines, c);
            // print
            int i = 0;
            for (String l : lines) {
                System.out.println(l);
                i++;
                if (!all_ && i > 10)
                    break;
            }
        } catch (Exception e) {
            throw new BuildException("Cannot get thread dump.", e);
        }
    }

    private static String getMemoryFromResult(String s) {
        if (s.endsWith(" K")) {
            s = s.substring(0, s.length() - 2);
            int lastSpace = s.lastIndexOf(" ");
            s = s.substring(lastSpace + 1);
            s = s.trim();
            s = s.replace(",", "");
            return s;
        }
        return null;
    }

    public List<String> excuteCommand(String command) throws Exception {
        // log("execute command: " + command);
        Runtime r = Runtime.getRuntime();
        Process p;
        List<String> list = new ArrayList<String>();
        BufferedReader br = null;
        try {
            p = r.exec(command);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String inline;
            while ((inline = br.readLine()) != null) {
                // log(inline);
                list.add(inline);
            }
            return list;
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }
}
