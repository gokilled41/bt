package wfunit;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface LogAnalyst {

    public void setFile(File file);

    public void setDir(File dir);

    public void initialize();

    public void logDetails();

    public List<String> getSuites();

    public Map<String, Result> getResults();

}
