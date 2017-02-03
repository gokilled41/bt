package gzhou;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class HZFSQLFormatTask extends Task {

    private File from_;
    private File to_;

    public void setFrom(File from) {
        from_ = from;
    }

    public void setTo(File to) {
        to_ = to;
    }

    @Override
    public void execute() throws BuildException {
        try {
            List<String> ls = gzhou.Util.getLines(from_.getAbsolutePath());
            List<String> ls2 = new ArrayList<String>();
            for (String l : ls) {
                if (l.endsWith("*/") && !l.startsWith("insert"))
                    continue;
                if (l.endsWith("*/") && l.startsWith("insert")) {
                    l = gzhou.Util.cut(l, null, "/*");
                }
                ls2.add(l);
            }
            gzhou.Util.setLines(to_.getAbsolutePath(), ls2, "UTF-8");
            log("hzf format sql: " + to_.getAbsolutePath());
        } catch (Exception e) {
            throw new BuildException(e);
        }

    }

}
