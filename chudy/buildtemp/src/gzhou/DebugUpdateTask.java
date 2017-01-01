package gzhou;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class DebugUpdateTask extends Task {

    protected String testcase_ = null;

    public void setTestcase(String testcase) {
        testcase_ = testcase;
    }

    @Override
    public void execute() throws BuildException {
        try {
            {
                String file = "d.bat";
                String loc = "C:\\workspace\\buildtemp\\files\\bat\\" + file;

                PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(loc)));
                out.println("c:");
                out.println("cd C:\\zhou\\yoda\\m3o\\server\\devtests\\core\\junit\\component");
                out.println("call ant -Dtestcase=" + testcase_);
                out.flush();
                out.close();
                log("update debug file \"" + file + "\"");
            }

            {
                String file = "dd.bat";
                String loc = "C:\\workspace\\buildtemp\\files\\bat\\" + file;

                PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(loc)));
                out.println("c:");
                out.println("cd C:\\zhou\\yoda\\m3o\\server\\devtests\\core\\junit\\component");
                out.println("call ant debug -Dtestcase=" + testcase_);
                out.flush();
                out.close();
                log("update debug file \"" + file + "\"");
            }

            {
                String file = "de.bat";
                String loc = "C:\\workspace\\buildtemp\\files\\bat\\" + file;

                PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(loc)));
                out.println("c:");
                out.println("cd C:\\zhou\\yoda\\m3o\\server\\devtests\\core\\junit\\component");
                out.println("call ant emma -Dtestcase=" + testcase_);
                out.flush();
                out.close();
                log("update debug file \"" + file + "\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
