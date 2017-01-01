package gzhou;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.SQLExec;

public class DropSQLExec extends SQLExec {

    private File dropSqlFile_;
    private File outputFile_;
    private File handledFile_;

    private String driver_, url_, userId_, password_;
    private OnError onError;

    public DropSQLExec() {

    }

    @Override
    public void setDriver(String driver) {
        driver_ = driver;
        super.setDriver(driver);
    }

    @Override
    public void setPassword(String password) {
        password_ = password;
        super.setPassword(password);
    }

    @Override
    public void setUrl(String url) {
        url_ = url;
        super.setUrl(url);
    }

    @Override
    public void setUserid(String userId) {
        userId_ = userId;
        super.setUserid(userId);
    }

    @Override
    public void setOnerror(OnError action) {
        onError = action;
        super.setOnerror(action);
    }

    @Override
    public void setSrc(File arg0) {
        dropSqlFile_ = arg0;

        outputFile_ = new File(System.getProperty("java.io.tmp") + "output." + dropSqlFile_.getName());
        handledFile_ = new File(System.getProperty("java.io.tmp") + "handled." + outputFile_.getName());

        doquery();

        handle();

        super.setSrc(handledFile_);
    }

    private void doquery() {

        final class SQLExecutor extends SQLExec {
            public SQLExecutor() {
                Project project = new Project();
                setProject(project);
                project.init();
                setTaskType("sql");
                setTaskName("sql");
                setOwningTarget(new Target());
            }
        }
        SQLExecutor executor = new SQLExecutor();
        executor.setDriver(driver_);
        executor.setUrl(url_);
        executor.setUserid(userId_);
        executor.setPassword(password_);

        executor.setOnerror(onError);

        executor.setPrint(true);
        executor.setOutput(outputFile_);

        executor.execute();
    }

    private void handle() {

    }

}
