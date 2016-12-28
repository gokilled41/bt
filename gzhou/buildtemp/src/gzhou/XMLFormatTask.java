package gzhou;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class XMLFormatTask extends Task implements Constants {

    private File sample_;
    private File src_;

    private String n_;
    private List<Attr> attrs_ = new ArrayList<Attr>();

    public void setSample(File sample) {
        sample_ = sample;
    }

    public void setSrc(File src) {
        src_ = src;
    }

    @Override
    public void execute() throws BuildException {
        try {
            loadSample();
            formatXML();
            log("format xml: " + src_.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException("Cannot format xml.", e);
        }
    }

    private void loadSample() throws Exception {
        String line = Util.getLines(sample_.getAbsolutePath()).get(0);
        line = line.trim();
        String[] arr = line.split(" ");
        n_ = arr[0];
        for (int i = 1; i < arr.length; i++) {
            String a = arr[i];
            if (a.contains("=")) {
                String an = a.substring(0, a.indexOf("="));
                Attr attr = new Attr();
                attr.n_ = an;
                attr.loc_ = line.indexOf(an);
                attrs_.add(attr);
            }
        }
    }

    private void formatXML() throws Exception {
        List<String> lines = Util.getLines(src_.getAbsolutePath());
        List<String> list = new ArrayList<String>();
        for (String line : lines) {
            list.add(format(line));
        }
        Util.setLines(src_.getAbsolutePath(), list);
    }

    private String format(String line) {
        String s = line.trim();
        int i = line.length() - s.length();
        if (s.startsWith(n_)) {
            for (Attr attr : attrs_) {
                line = format(line, attr, i);
            }
        }
        // System.out.println(line);
        return line;
    }

    private String format(String line, Attr attr, int i) {
        if (line.contains(attr.n_)) {
            int loc = line.indexOf(attr.n_);
            int toloc = attr.loc_ + i;
            if (loc < toloc) {
                for (int j = 0; j < toloc - loc; j++) {
                    line = line.replace(" " + attr.n_, "  " + attr.n_);
                }
            }
        }
        return line;
    }

    private class Attr {
        private String n_;
        private int loc_;
    }
}
