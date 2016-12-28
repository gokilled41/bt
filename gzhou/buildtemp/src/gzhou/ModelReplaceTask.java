package gzhou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class ModelReplaceTask extends Task implements Constants {

    protected File src_ = null;
    protected File root_ = null;
    protected boolean zip_ = false;

    private List<String> fromList_ = new ArrayList<String>();
    private List<String> toList_ = new ArrayList<String>();

    public void setSrc(File src) {
        src_ = src;
    }

    public void setRoot(File root) {
        root_ = root;
    }

    public void setZip(boolean zip) {
        zip_ = zip;
    }

    @Override
    public void execute() throws BuildException {
        try {

            prepare();
            if (!zip_) {
                fixFolder(src_);
            } else {
                List<String> lines = new ArrayList<String>();
                zipFolder(src_, lines);
                FileUtil.setLines("D:\\models\\zip.lines.txt", lines);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void prepare() {
        if (root_ == null)
            root_ = src_;

        initConf();
    }

    private void initConf() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filesDir
                    + "modelreplace\\modelreplace.txt")));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("-"))
                    continue;
                if (line.length() == 0)
                    continue;

                if (line.startsWith("from: ")) {
                    String s = line.substring("from: ".length()).trim();
                    s = unwrap(s);
                    fromList_.add(s);
                }
                if (line.startsWith("to: ")) {
                    String s = line.substring("to: ".length()).trim();
                    s = unwrap(s);
                    toList_.add(s);
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String unwrap(String s) {
        if (s.startsWith("\"") && s.endsWith("\"")) {
            // unwrap ""
            s = s.substring(1, s.length() - 1);
            s = s.replace("\\\"", "\"");
        }
        return s;
    }

    private void fixFolder(File folder) throws Exception {
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                fixFolder(file);
            } else {
                fixModel(file);
                log("fix model: " + file.getAbsolutePath());
            }
        }
    }

    private void fixModel(File file) throws Exception {
        // unzip
        String filePath = file.getAbsolutePath();
        String folderPath = filePath.replace(root_.getAbsolutePath(), "D:\\models\\temp");
        FileUtil.cleanFolder(folderPath);
        FileUtil.extractZip(filePath, folderPath);
        // fix model contents
        File folder = new File(folderPath);
        File[] contentFiles = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith("ModelContentFile");
            }
        });
        for (File cf : contentFiles) {
            fixContent(cf);
        }
        // zip
        // FileUtil.zip(filePath, folderPath);
        // clean up
        // FileUtil.cleanFolder(folderPath);
    }

    private void fixContent(File cf) throws Exception {
        String f = cf.getAbsolutePath();
        Object o = read(f);
        if (o instanceof String) {
            String c = (String) o;
            String n = replace(c);
            if (!n.equals(c)) {
                write(n, f);
                log("    fix content: " + cf.getName());
            }
        }
    }

    private void zipFolder(File folder, List<String> lines) {
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                zipFolder(file, lines);
            } else {
                zipModel(file, lines);
            }
        }
    }

    private void zipModel(File file, List<String> lines) {
        String filePath = file.getAbsolutePath();
        String toFilePath = filePath.replace(root_.getAbsolutePath(), "D:\\models\\to");
        String tmpFilePath = filePath.replace(root_.getAbsolutePath(), "D:\\models\\temp");
        zipFile(tmpFilePath, toFilePath, lines);
    }

    private void zipFile(String tmpFilePath, String toFilePath, List<String> lines) {
        String s = "        <zip destfile=\"{0}\" basedir=\"{1}\" />";
        lines.add(MessageFormat.format(s, toFilePath, tmpFilePath));
    }

    private static Object read(String f) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    private static void write(Object c, String f) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f, false));
        oos.writeObject(c);
        oos.flush();
        oos.close();
    }

    private String replace(String c) {
        for (int i = 0; i < fromList_.size(); i++) {
            String from = fromList_.get(i);
            String to = toList_.get(i);
            c = c.replace(from, to);
        }
        return c;
    }
}
