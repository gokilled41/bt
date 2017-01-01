package gzhou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class FindStringTask extends Task {

    private File src_ = null;
    private File dest_ = null;
    private String pattern_ = null;
    private String[] filters_ = null;

    private String fromPattern_ = null;
    private String toPattern_ = null;
    private int from_ = 0;
    private int to_ = 0;
    private boolean cut_ = false;
    private String startPattern_ = null;
    private String endPattern_ = null;

    private boolean started_ = false;
    private boolean checkStarted_ = false;
    private boolean checkEnded_ = false;

    private boolean isMultiple_ = false;

    private static final String YEAR = "" + Calendar.getInstance().get(Calendar.YEAR);

    public void setSrc(File src) {
        src_ = src;
    }

    public void setDest(File dest) {
        dest_ = dest;
    }

    public void setPattern(String pattern) {
        pattern_ = pattern;
    }

    public void setFilter(String filter) {
        if (filter != null) {
            filters_ = filter.split(", ");
        }
    }

    public void setFromPattern(String fromPattern) {
        fromPattern_ = fromPattern;
    }

    public void setToPattern(String toPattern) {
        toPattern_ = toPattern;
    }

    public void setFrom(int from) {
        from_ = from;
    }

    public void setTo(int to) {
        to_ = to;
    }

    public void setCut(boolean cut) {
        cut_ = cut;
    }

    public void setStartPattern(String startPattern) {
        startPattern_ = startPattern;
    }

    public void setEndPattern(String endPattern) {
        endPattern_ = endPattern;
    }

    public void setMultiple(boolean isMultiple) {
        isMultiple_ = isMultiple;
    }

    @Override
    public void execute() throws BuildException {
        try {
            long start = System.currentTimeMillis();
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(dest_)));

            if (src_.isFile()) {
                findInFile(src_, out);
            } else if (src_.isDirectory()) {
                File[] srcFiles = src_.listFiles();
                for (File srcFile : srcFiles) {
                    if (srcFile.isFile())
                        findInFile(srcFile, out);
                }
            }

            out.close();
            long end = System.currentTimeMillis();
            log("find string in \"" + src_.getName() + "\" cost " + format(end - start) + " ms: \"" + pattern_ + "\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void findInFile(File srcFile, PrintWriter out) throws IOException {
        out.println();
        out.println("===== " + srcFile.getName() + ": ");
        out.println();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(srcFile)));
        String line;
        int matched = 0;
        while ((line = in.readLine()) != null) {
            checkStarted(line);
            if (started_) {
                if (pattern_ != null) {
                    if ((line.indexOf(pattern_) > -1 && !isFiltered(line)) || isMultiple(matched, line, pattern_)) {
                        matched++;
                        if (!cut_) {
                            out.println(line);
                        } else {
                            out.println(cut(line));
                        }
                    } else if (matched > 1 && !isMultiple(matched, line, pattern_)) {
                        matched = 0;
                    }
                } else {
                    out.println(line);
                }
            }
        }
        in.close();
    }

    protected boolean isFiltered(String line) {
        if (filters_ != null && filters_.length > 0) {
            for (int i = 0; i < filters_.length; i++) {
                if (line.contains(filters_[i]))
                    return true;
            }
        }
        return false;
    }

    protected String cut(String line) {
        StringBuilder sb = new StringBuilder();
        sb.append(cutIndex(line));
        sb.append(cutPattern(line));
        return sb.toString();
    }

    protected String cutIndex(String line) {
        return line.substring(from_, to_);
    }

    protected String cutPattern(String line) {
        int fromIndex = 0;
        int toIndex = line.length();
        if (fromPattern_ != null)
            fromIndex = line.indexOf(fromPattern_) + fromPattern_.length();
        if (toPattern_ != null)
            toIndex = line.indexOf(toPattern_);
        if (fromPattern_ == null && toPattern_ == null)
            return "";
        return line.substring(fromIndex, toIndex);
    }

    protected void checkStarted(String line) {
        if (startPattern_ == null && endPattern_ == null) {
            started_ = true;
            return;
        }
        if (!checkStarted_ && !started_ && startPattern_ != null) {
            started_ = (line.indexOf(startPattern_) > -1);
            checkStarted_ = (started_ == true);
        }
        if (checkStarted_ && !checkEnded_ && started_ && endPattern_ != null) {
            started_ = !(line.indexOf(endPattern_) > -1);
            checkEnded_ = (started_ == false);
        }
    }

    protected boolean isMultiple(int matched, String line, String pattern) {
        if (isError(matched, line, pattern))
            return true;
        if (isNC(matched, line, pattern))
            return true;
        if (isDataflow(matched, line, pattern))
            return true;
        return matched > 0 && isMultiple_ && (!line.startsWith(YEAR));
    }

    protected boolean isError(int matched, String line, String pattern) {
        return matched > 0 && pattern.equals("ERROR") && (!line.startsWith(YEAR) && !line.trim().startsWith("<"));
    }

    protected boolean isNC(int matched, String line, String pattern) {
        return matched > 0 && pattern.contains("com.vitria.nc") && (!line.startsWith(YEAR));
    }

    protected boolean isDataflow(int matched, String line, String pattern) {
        return matched > 0 && pattern.contains("com.vitria.dataflow") && (!line.startsWith(YEAR));
    }

    protected String format(long l) {
        return format("" + l, 6);
    }

    protected String format(String s, long size) {
        int len = s.length();
        if (len < size) {
            for (int i = 0; i < size - len; i++) {
                s = " " + s;
            }
        }
        return s;
    }
}
