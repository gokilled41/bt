package gzhou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class ServerLogFormatter extends Task {

    private static final List<String> prefixes_ = new ArrayList<String>();
    private File src_ = null;

    static {
        prefixes_.add(Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR)).toString() + "-");
        prefixes_.add("at ");
        prefixes_.add("java.");
        prefixes_.add("org.");
        prefixes_.add("com.");
        prefixes_.add("...");
        prefixes_.add("Caused by");
        prefixes_.add("[junit]");
    }

    public void setSrc(File src) {
        src_ = src;
    }

    @Override
    public void execute() throws BuildException {
        try {

            List<String> lines = readLines();
            lines = merge1(lines);
            lines = merge2(lines);
            print(lines);

            log("format log file: " + src_.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> readLines() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(src_)));
        List<String> list = new ArrayList<String>();
        String line;
        while ((line = in.readLine()) != null) {
            list.add(line);
        }
        in.close();
        return list;
    }

    private List<String> merge1(List<String> lines) throws Exception { // remove empty lines
        List<String> list = new ArrayList<String>();
        for (String s : lines) {
            if (s == null || s.trim().length() == 0)
                continue;
            list.add(s);
        }
        return list;
    }

    private List<String> merge2(List<String> lines) throws Exception { // combine to one line
        List<String> list = new ArrayList<String>();
        for (String s : lines) {
            if (!isStartingOfOneLine(s)) {
                String line = "";
                if (list.size() > 0)
                    line = list.remove(list.size() - 1);
                line = line + s;
                list.add(line);
            } else {
                list.add(s);
            }
        }
        return list;
    }

    private boolean isStartingOfOneLine(String s) {
        for (String prefix : prefixes_) {
            if (s.trim().startsWith(prefix))
                return true;
        }
        if (s.length() > 10 && s.substring(2, 3).equals(":") && s.substring(5, 6).equals(":"))
            return true;
        return false;
    }

    private void print(List<String> lines) throws Exception {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(src_, false)));
        for (String s : lines) {
            out.println(s);
        }
        out.close();
    }

}
