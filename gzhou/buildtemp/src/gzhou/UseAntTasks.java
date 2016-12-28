package gzhou;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Expand;

public class UseAntTasks {

    public static void main(String[] args) {
        delete("C:/Drive_D", false);
        unzip("C:/Drive_D.zip", "C:/Drive_D");
        delete("C:/Drive_D", false);
        delete("C:/Drive_D.zip", true);
    }

    /**
     * Unzip a zip file into a given directory.
     *
     * @param zipFilepath A pathname representing a local zip file
     * @param destinationDir where to unzip the archive to
     */
    static public void unzip(String zipFilepath, String destinationDir) {
        final class Expander extends Expand {
            public Expander() {
                Project project = new Project();
                setProject(project);
                project.init();
                setTaskType("unzip");
                setTaskName("unzip");
                setOwningTarget(new Target());
            }
        }
        Expander expander = new Expander();
        expander.setSrc(new File(zipFilepath));
        expander.setDest(new File(destinationDir));
        expander.execute();
    }

    static public void delete(String destinationDir, boolean isFile) {
        final class Deleter extends Delete {
            public Deleter() {
                Project project = new Project();
                setProject(project);
                project.init();
                setTaskType("delete");
                setTaskName("delete");
                setOwningTarget(new Target());
            }
        }
        Deleter deleter = new Deleter();
        if (isFile)
            deleter.setFile(new File(destinationDir));
        else
            deleter.setDir(new File(destinationDir));
        deleter.execute();
    }
}
