package wfunit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class LogAnalystBase implements LogAnalyst {

    protected File file_;
    protected File dir_;

    protected Map<String, Result> results_ = new HashMap<String, Result>();
    protected List<String> suites_ = new ArrayList<String>();
    protected String[] allLines_;

    public void setDir(File dir) {
        dir_ = dir;
    }

    public void setFile(File file) {
        file_ = file;
        read(file);
    }

    public Map<String, Result> getResults() {
        return results_;
    }

    public List<String> getSuites() {
        return suites_;
    }

    protected abstract String getSuiteName(String keyLine);

    protected abstract Result getResult(String keyLine);

    protected void read(File file) {
        try {
            List<String> list = new ArrayList<String>();
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = in.readLine()) != null) {
                list.add(line);
            }
            in.close();
            allLines_ = list.toArray(new String[list.size()]);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    protected String sub(String line, String index) {
        return line.substring(line.indexOf(index) + index.length());
    }
}
