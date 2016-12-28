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

public class AppendCopyrightTask extends Task {

    private File dir_;

    public void setDir(File dir) {
        dir_ = dir;
    }

    @Override
    public void execute() throws BuildException {
        try {
            appendFolder(dir_);
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }

    private void appendFolder(File dir) throws Exception {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                appendFolder(file);
            } else {
                appendFile(file);
            }
        }
    }

    private void appendFile(File file) throws Exception {

        if (!file.getName().endsWith(".java"))
            return;

        List<String> list = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        while ((line = in.readLine()) != null) {
            list.add(line);
        }
        in.close();

        boolean start = false;
        List<String> list2 = new ArrayList<String>();

        list2.add("/*******************************************************************************");
        list2.add(" * Copyright (c) 2010 Vitria Technology, Inc.  All Rights Reserved.");
        list2.add(" * All Rights Reserved.");
        list2.add(" *******************************************************************************/");
        list2.add("");

        for (String string : list) {
            if (string.startsWith("package"))
                start = true;
            if (start)
                list2.add(string);
        }

        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false)));
        for (String string : list2) {
            out.println(string);
        }
        out.close();

        log("add copy right to file " + file.getAbsolutePath());
    }
}
