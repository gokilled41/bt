package gzhou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class SJBRenameTask extends Task {

    private String dir_ = null;

    public void setDir(String dir) {
        dir_ = dir;
    }

    @Override
    public void execute() throws BuildException {
        try {

            File dirFile = new File(dir_);
            File[] files = dirFile.listFiles();
            for (File file : files) {
                if (isSJBDeployFile(file)) {
                    file.delete();
                    log("remove: " + file.getName());
                }
            }

            for (File file : files) {
                if (isDeployment(file)) {
                    createJSBDeployedFile(file);
                }
            }

            cleanupStandaloneFull();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cleanupStandaloneFull() throws Exception {
        File deployDir = new File(dir_);
        String sf = deployDir.getParent() + File.separator + "configuration" + File.separator + "standalone-full.xml";
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(sf)));
        String line;
        List<String> list = new ArrayList<String>();
        boolean ignore = false;
        while ((line = in.readLine()) != null) {
            if (line.contains("<deployments>"))
                ignore = true;
            if (!ignore)
                list.add(line);
            if (line.contains("</deployments>"))
                ignore = false;
        }
        in.close();

        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(sf, false)));
        for (String string : list) {
            out.println(string);
        }
        out.close();

        log("clean up: " + sf);
    }

    private boolean isSJBDeployFile(File file) {
        String name = file.getName();

        if (!file.isFile())
            return false;

        if (name.contains("mondrian"))
            return true;
        if (name.contains("vtm3oserver"))
            return true;
        if (name.contains("vtm3oui"))
            return true;

        if (name.endsWith(".dodeploy"))
            return true;
        if (name.endsWith(".isdeploying"))
            return true;
        //        if (name.endsWith(".deployed"))
        //            return true;
        if (name.endsWith(".failed"))
            return true;
        if (name.endsWith(".skipdeploy"))
            return true;
        if (name.endsWith(".isundeploying"))
            return true;
        if (name.endsWith(".undeployed"))
            return true;
        if (name.endsWith(".pending"))
            return true;

        return false;
    }

    private boolean isDeployment(File file) {
        String name = file.getName();
        if (name.contains("mondrian"))
            return false;
        if (name.contains("vtm3oserver"))
            return false;
        if (name.contains("vtm3oui"))
            return false;

        if (name.endsWith(".rar"))
            return true;
        if (name.endsWith(".ear"))
            return true;
        if (name.endsWith(".xml"))
            return true;
        if (name.endsWith(".war"))
            return true;
        return false;
    }

    private void createJSBDeployedFile(File file) throws Exception {
        String name = file.getName();
        String jsbName = name + ".deployed";
        File sjbFile = new File(dir_ + File.separator + jsbName);
        if (!sjbFile.exists()) {
            FileOutputStream fos = new FileOutputStream(sjbFile, false);
            fos.write(name.getBytes());
            fos.close();
            log("adc: " + jsbName);
        }
    }
}
