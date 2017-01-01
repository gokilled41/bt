package gzhou;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Mkdir;

public class MakeBugFolderTask extends Task {

    private File dir_;
    private String bugId_;
    private String task_;
    private boolean delete_ = false;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void setDir(File dir) {
        dir_ = dir;
    }

    public void setBugId(String bugId) {
        bugId_ = bugId;
    }

    public void setTask(String task) {
        task_ = task;
    }

    public void setDelete(boolean delete) {
        delete_ = delete;
    }

    @Override
    public void execute() throws BuildException {
        String folder = dir_.getAbsolutePath();
        Date date = new Date();
        String today = sdf.format(date);
        folder = folder + "\\" + today + " " + getFolderName();

        if (!delete_) {
            makeDir(folder);
            if (isBug()) {
                folder = folder + "\\" + "VITR00" + bugId_;
                makeDir(folder);

                String from = "C:\\workspace\\buildtemp\\files\\doc\\BugAnalysis.doc";
                String to = folder + "\\" + "VITR00" + bugId_ + "_BugAnalysis.doc";
                copyFile(from, to);
            }
        } else {
            deleteDir(folder);
        }
    }

    protected void makeDir(String dir) {
        File dirFile = new File(dir);
        Mkdir mkdir = new Mkdir();
        mkdir.setProject(getProject());
        mkdir.setDir(dirFile);
        mkdir.setTaskName("mkdir");
        mkdir.execute();
    }

    protected void deleteDir(String dir) {
        File dirFile = new File(dir);
        Delete delete = new Delete();
        delete.setProject(getProject());
        delete.setDir(dirFile);
        delete.setTaskName("delete");
        delete.execute();
    }

    protected void copyFile(String from, String to) {
        File fromFile = new File(from);
        File toFile = new File(to);
        Copy copy = new Copy();
        copy.setProject(getProject());
        copy.setFile(fromFile);
        copy.setTofile(toFile);
        copy.setTaskName("copy");
        copy.execute();
    }

    protected boolean isBug() {
        return bugId_ != null;
    }

    protected String getFolderName() {
        if (isBug()) {
            return bugId_;
        } else {
            return task_;
        }
    }
}
