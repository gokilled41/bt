package gzhou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class HandleTask extends Task {

    protected File src_ = null;
    protected File dest_ = null;

    public void setDest(File dest) {
        dest_ = dest;
    }

    public void setSrc(File src) {
        src_ = src;
    }

    @Override
    public void execute() throws BuildException {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(src_)));
            PrintWriter out = new PrintWriter(dest_);

            String line;
            while ((line = in.readLine()) != null) {
                if (line.endsWith(";"))
                    out.println(line);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
