package wfunit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class JunitLogAnalyst extends LogAnalystBase {

    private Map<String, Integer> endsMap_ = new HashMap<String, Integer>();
    private Map<String, Integer> startsMap_ = new HashMap<String, Integer>();

    public void initialize() {

        String[] lines = allLines_;

        int start = -1, end = -1;
        String suiteName = null;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            if (isStart(line)) {

                if (start > -1) {
                    end = i - 1;
                    startsMap_.put(suiteName, start);
                    endsMap_.put(suiteName, end);
                    results_.put(suiteName, null);
                }

                start = i;
                suiteName = getSuiteName(line);
                suites_.add(suiteName);
            }

            if (isSuite(line)) {
                start = -1;
                end = -1;
                suiteName = null;
            }

            if (start > -1 && suiteName != null && (isEnd(line) || isReport(line))) {

                end = i;
                startsMap_.put(suiteName, start);
                endsMap_.put(suiteName, end);

                Result result = isEnd(line) ? getResult(line) : null;
                results_.put(suiteName, result);

                //reset
                start = -1;
                end = -1;
                suiteName = null;
            }
        }

    }

    public void logDetails() {
        for (String suiteName : suites_) {

            try {
                File logFile = new File(dir_.getAbsolutePath() + File.separator + suiteName + ".log");
                PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(logFile, false)));

                int start = startsMap_.get(suiteName);
                int end = endsMap_.get(suiteName);

                for (int i = start; i <= end; i++) {
                    out.println(allLines_[i]);
                }

                out.flush();
                out.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected Result getResult(String line) {
        if (!isEnd(line))
            return null;

        //    [junit] Tests run: 12, Failures: 0, Errors: 0, Time elapsed: 272.228 sec
        Result result = new Result();
        line = sub(line, "    [junit] Tests run: ");
        result.setTests(Integer.parseInt(line.substring(0, line.indexOf(","))));
        line = sub(line, ", Failures: ");
        result.setFailures(Integer.parseInt(line.substring(0, line.indexOf(","))));
        line = sub(line, ", Errors: ");
        result.setErrors(Integer.parseInt(line.substring(0, line.indexOf(","))));
        result.calculateSuccessRate();
        line = sub(line, ", Time elapsed: ");
        result.setTime(Float.parseFloat(line.substring(0, line.indexOf(" sec"))));

        return result;
    }

    @Override
    protected String getSuiteName(String keyLine) {
        return keyLine.substring("    [junit] Running".length()).trim();
    }

    private boolean isEnd(String line) {
        return line != null && line.startsWith("    [junit] Tests run");
    }

    private boolean isStart(String line) {
        return line != null && line.startsWith("    [junit] Running");
    }

    private boolean isSuite(String line) {
        return line != null && line.startsWith("    [junit] Testsuite");
    }

    private boolean isReport(String line) {
        return line != null && line.startsWith("do-report:");
    }
}
