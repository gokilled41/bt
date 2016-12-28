package gzhou;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class CreateBugDetailTask extends Task {

    protected String text_;

    public void setText(String text) {
        text_ = text;
    }

    @Override
    public void execute() throws BuildException {
        try {
            String bugNumber = text_.substring(0, 12);
            // String bugTitle = text.substring(13);
            String fileName = "D:\\Workflow-G\\workflow bug fixing\\2011-10-11 Domain Service Bugs\\" + bugNumber
                    + ".txt";
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
            out.println(text_);
            out.println();
            out.println("------------------------------------------------------------------------------------------------------------");
            out.println("------------------------------------------------------------------------------------------------------------");
            out.println();
            out.println();
            out.close();
            log("Create bug detail: " + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
