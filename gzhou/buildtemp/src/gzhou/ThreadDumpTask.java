package gzhou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class ThreadDumpTask extends Task implements Constants {

    private String name_;
    private File output_;

    public void setName(String name) {
        name_ = name;
    }

    public void setOutput(File output) {
        output_ = output;
    }

    @Override
    public void execute() throws BuildException {
        try {
            String id = getId(excuteCommand("tasklist"));
            if (id != null) {
                saveThreadDump(excuteCommand("jstack " + id));
                log("Got thread dump of " + name_ + "(" + id + ") to: " + output_.getAbsolutePath());
            } else {
                log("Cannot get process id of " + name_);
            }
        } catch (Exception e) {
            throw new BuildException("Cannot get thread dump.", e);
        }
    }

    private String getId(List<String> result) {
        for (String string : result) {
            if (string.contains(getProcessName())) {
                String type = getTypeFromResult(string);
                if (type.equals("Console")) {
                    return getPidFromResult(string);
                }
            }
        }
        return null;
    }

    private static String getTypeFromResult(String s) {
        int firstSpace = s.indexOf(" ");
        s = s.substring(firstSpace);
        s = s.trim();
        firstSpace = s.indexOf(" ");
        s = s.substring(firstSpace);
        s = s.trim();
        firstSpace = s.indexOf(" ");
        s = s.substring(0, firstSpace);
        return s;
    }

    private static String getPidFromResult(String s) {
        int firstSpace = s.indexOf(" ");
        s = s.substring(firstSpace);
        s = s.trim();
        firstSpace = s.indexOf(" ");
        s = s.substring(0, firstSpace);
        return s;
    }

    private String getProcessName() {
        return "java.exe";
    }

    private void saveThreadDump(List<String> result) throws Exception {
        FileUtil.mkdirs(output_.getParent());
        PrintWriter out = null;
        try {
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(output_, false)));
            for (String string : result) {
                // log(string);
                out.println(string);
            }
        } finally {
            if (out != null)
                out.close();
        }
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
