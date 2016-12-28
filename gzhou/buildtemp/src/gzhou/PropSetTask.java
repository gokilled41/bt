package gzhou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class PropSetTask extends Task {

    private File file_;
    private String key_;
    private String value_;

    public void setFile(File file) {
        file_ = file;
    }

    public void setKey(String key) {
        key_ = key;
    }

    public void setValue(String value) {
        value_ = value;
    }

    @Override
    public void execute() throws BuildException {
        try {
            List<String> list = read(file_);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file_, false)));
            boolean replace = false;
            for (String line : list) {
                if (!line.startsWith("#")) {
                    if (line.startsWith(key_)) {
                        line = key_ + "=" + value_;
                        replace = true;
                    }
                }
                out.println(line);
            }
            if (replace == false)
                out.println(key_ + "=" + value_);
            out.close();
            log(key_ + "=" + value_);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> read(File file) throws Exception {
        List<String> list = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        while ((line = in.readLine()) != null) {
            list.add(line);
        }
        in.close();
        return list;
    }
}
