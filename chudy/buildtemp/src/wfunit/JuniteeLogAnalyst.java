package wfunit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class JuniteeLogAnalyst extends LogAnalystBase {

    @Override
    protected Result getResult(String line) {
        //  [junitee] com.vitria.wf.test.junitee.ModelRepositoryLibTest (runs: 26 errors: 0 failures: 0 time: 72.020 sec)
        Result result = new Result();
        line = sub(line, "runs: ");
        result.setTests(Integer.parseInt(line.substring(0, line.indexOf(" "))));
        line = sub(line, "errors: ");
        result.setErrors(Integer.parseInt(line.substring(0, line.indexOf(" "))));
        line = sub(line, "failures: ");
        result.setFailures(Integer.parseInt(line.substring(0, line.indexOf(" "))));
        result.calculateSuccessRate();
        line = sub(line, "time: ");
        result.setTime(Float.parseFloat(line.substring(0, line.indexOf(" sec"))));

        return result;
    }

    @Override
    protected String getSuiteName(String keyLine) {
        keyLine = sub(keyLine, "[junitee]");
        keyLine = keyLine.substring(0, keyLine.indexOf("(runs:"));
        return keyLine.trim();
    }

    public void initialize() {
        String[] lines = allLines_;

        String suiteName = null;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            if (isSuite(line)) {
                suiteName = getSuiteName(line);
                suites_.add(suiteName);
                results_.put(suiteName, getResult(line));
            }
        }
    }

    public void logDetails() {
        for (String suiteName : suites_) {

            try {
                File logFile = new File(dir_.getAbsolutePath() + File.separator + suiteName + ".log");
                PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(logFile, false)));

                for (int i = 0; i < allLines_.length; i++) {
                    out.println(allLines_[i]);
                }

                out.flush();
                out.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    private boolean isSuite(String line) {
        return line.indexOf("[junitee]") > -1 && line.indexOf("runs") > -1 && line.indexOf("errors") > -1
                && line.indexOf("failures") > -1;
    }
}
