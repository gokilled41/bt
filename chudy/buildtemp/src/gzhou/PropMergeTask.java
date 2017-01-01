package gzhou;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class PropMergeTask extends Task {

    private File src_;
    private File dest_;
    private String key_;
    private String value_;

    public void setSrc(File src) {
        src_ = src;
    }

    public void setDest(File dest) {
        dest_ = dest;
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
            Properties srcProp = getSrcProp();
            Properties destProp = new Properties();

            InputStream destIn = new FileInputStream(dest_);
            destProp.load(destIn);
            destIn.close();

            for (Object key : srcProp.keySet()) {
                destProp.put(key, srcProp.get(key));
            }

            OutputStream out = new FileOutputStream(dest_, false);
            destProp.store(out, null);
            out.close();

            log("Append " + getSrcString() + " to " + dest_);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Properties getSrcProp() throws Exception {
        Properties srcProp = new Properties();

        if (src_ != null) {
            InputStream srcIn = new FileInputStream(src_);
            srcProp.load(srcIn);
            srcIn.close();
        } else if (key_ != null) {
            srcProp.put(key_, value_);
        }

        return srcProp;
    }

    private String getSrcString() {
        if (src_ != null) {
            return src_.toString();
        } else {
            return "a property";
        }
    }
}
