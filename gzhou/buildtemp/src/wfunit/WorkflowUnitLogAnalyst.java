package wfunit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

@SuppressWarnings("all")
public class WorkflowUnitLogAnalyst extends Task {

    private String src = null;
    private File dir = null;
    private List<File> files = new ArrayList<File>();

    public void setSrc(String src) {
        src = src;

        String[] fileNames = src.split(";");
        for (int i = 0; i < fileNames.length; i++) {
            files.add(new File(fileNames[i]));
        }
    }

    public void setDir(File dir) {
        if (!dir.isDirectory())
            throw new BuildException("dir should be one directory.");
        dir = dir;
    }

    @Override
    public void execute() throws BuildException {
        try {
            ReportHTMLGenerator generator = new ReportHTMLGenerator();
            for (File file : files) {
                LogAnalyst analyst = getLogAnalyst(file);
                analyst.setFile(file);
                analyst.setDir(dir);
                analyst.initialize();
                analyst.logDetails();
                generator.addSuites(analyst.getSuites(), analyst.getResults());
                log("analyzing file " + file.getAbsolutePath());
            }

            generator.calculateSummary();
            generator.write(dir.getAbsolutePath() + File.separator + "workflow_unit_testing_summary.html");
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }

    private boolean isJunitee(File file) {
        return file.getAbsolutePath().indexOf("workflow_junitee") > -1;
    }

    private LogAnalyst getLogAnalyst(File file) {
        if (isJunitee(file))
            return new JuniteeLogAnalyst();
        else
            return new JunitLogAnalyst();
    }
}
