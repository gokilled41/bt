package gzhou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class ConnectFileTask extends Task {

    protected File src_ = null;
    protected File dest_ = null;
    protected String pattern_ = null;
    protected boolean append_ = true;

    public void setSrc(File src) {
        src_ = src;
    }

    public void setDest(File dest) {
        dest_ = dest;
    }

    public void setPattern(String pattern) {
        pattern_ = pattern;
    }

    public void setAppend(boolean append) {
        append_ = append;
    }

    @Override
    public void execute() throws BuildException {
        try {
            long start = System.currentTimeMillis();
            BufferedReader in1 = new BufferedReader(new InputStreamReader(new FileInputStream(src_)));
            BufferedReader in2 = new BufferedReader(new InputStreamReader(new FileInputStream(dest_)));

            File tmpFile = new File(dest_.getParent() + "\\tmp.txt");
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(tmpFile)));
            if (append_) {
                append(in2, out);
                append(in1, out);
            } else {
                append(in1, out);
                append(in2, out);
            }
            out.close();
            dest_.delete();
            src_.delete();
            tmpFile.renameTo(dest_);
            long end = System.currentTimeMillis();
            log("connect file " + src_.getName() + " to " + dest_.getName() + " cost " + (end - start) + " ms.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void append(BufferedReader in, PrintWriter out) throws IOException {
        String line;
        while ((line = in.readLine()) != null) {
            out.println(line);
        }
        in.close();
    }
}
