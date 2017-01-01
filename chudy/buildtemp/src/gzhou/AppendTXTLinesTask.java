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

public class AppendTXTLinesTask extends Task {

    private File file_;
    private String line_;
    private int position_;

    public void setFile(File file) {
        file_ = file;
    }

    public void setLine(String line) {
        line_ = line;
    }

    public void setPosition(int position) {
        position_ = position;
    }

    @Override
    public void execute() throws BuildException {
        try {
            List<String> lines = new ArrayList<String>();
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file_)));
            String line;
            while ((line = in.readLine()) != null) {
                lines.add(line);
            }
            in.close();

            int size = lines.size();
            boolean reverse = (position_ < 0);
            int pos = reverse ? size + position_ : position_;
            if (line_.equals("delete")) {
                int pos2 = reverse ? pos - 1 : pos;
                lines.remove(pos2);
            } else {
                lines.add(pos, line_);
            }

            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file_, false)));
            for (String s : lines) {
                out.println(s);
            }
            out.close();

            if (line_.equals("delete")) {
                log("delete one line at position \"" + position_ + "\" in \"" + file_.getAbsolutePath() + "\"");
            } else {
                log("add one line \"" + line_ + "\" at position \"" + position_ + "\" in \"" + file_.getAbsolutePath()
                        + "\"");
            }

        } catch (Exception e) {
            throw new BuildException(e);
        }
    }

}
