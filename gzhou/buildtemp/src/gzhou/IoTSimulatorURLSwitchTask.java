package gzhou;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class IoTSimulatorURLSwitchTask extends Task implements Constants {

    private File file_;
    private String type_;

    public void setFile(File file) {
        file_ = file;
    }

    public void setType(String type) {
        type_ = type;
    }

    @Override
    public void execute() throws BuildException {
        try {
            String filePath = file_.getAbsolutePath();
            List<String> lines = Util.getLines(filePath);
            List<String> list = new ArrayList<String>();
            for (String line : lines) {
                String s = line.trim();
                int indent = line.length() - s.length();
                if (s.contains("<URL>")) {
                    if (s.startsWith("<!--"))
                        s = s.substring(4);
                    if (s.endsWith("-->"))
                        s = s.substring(0, s.length() - 3);
                    s = s.trim();
                    String type = s.substring(5, s.indexOf(":"));
                    if (type.equalsIgnoreCase(type_))
                        list.add(Util.getSpaces(indent) + s);
                    else
                        list.add(Util.getSpaces(indent) + "<!--" + s + "-->");
                } else {
                    list.add(line);
                }
            }
            Util.setLines(filePath, list);
            log("IoT Simulator URL switch to: " + type_ + ". The file is: " + file_.getAbsolutePath());
        } catch (Exception e) {
            throw new BuildException("Cannot switch url in iot simulator.", e);
        }
    }
}
