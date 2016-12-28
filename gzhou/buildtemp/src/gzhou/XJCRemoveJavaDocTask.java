package gzhou;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class XJCRemoveJavaDocTask extends Task implements Constants {

    private File dir_;

    public void setDir(File dir) {
        dir_ = dir;
    }

    @Override
    public void execute() throws BuildException {
        try {

            File[] files = dir_.listFiles();
            for (File file : files) {
                doRemove(file);
            }

        } catch (Exception e) {
            throw new BuildException("Cannot remove java doc of xjc files.", e);
        }
    }

    private void doRemove(File file) {
        try {
            String path = file.getAbsolutePath();
            List<String> lines = FileUtil.getLines(path);
            List<String> list = new ArrayList<String>();
            for (String line : lines) {
                if (!isJavaDoc(line) && !isFirstEmptyLines(list, line)) {
                    list.add(line);
                }
            }
            FileUtil.setLines(path, list);
            log("remove java doc in: " + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isFirstEmptyLines(List<String> list, String line) {
        return list.isEmpty() && line.isEmpty();
    }

    private boolean isJavaDoc(String line) {
        line = line.trim();
        if (line.startsWith("//"))
            return true;
        if (line.startsWith("/**"))
            return true;
        if (line.startsWith("*"))
            return true;
        return false;
    }

}
