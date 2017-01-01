package wfunit;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class ReportHTMLGenerator {

    private Result summary;
    private List<String> suites = new ArrayList<String>();
    private Map<String, Result> results = new HashMap<String, Result>();

    private static final String lineSeperator = (String) java.security.AccessController
            .doPrivileged(new sun.security.action.GetPropertyAction("line.separator"));

    public void addSuite(String suiteName, Result result) {
        suites.add(suiteName);
        results.put(suiteName, result);
    }

    public void addSuites(List<String> suites, Map<String, Result> results) {
        for (String suite : suites) {
            addSuite(suite, results.get(suite));
        }
    }

    public void calculateSummary() {
        summary = new Result();

        int tests = 0;
        int failures = 0;
        int errors = 0;
        float time = 0;

        for (String suiteName : suites) {
            Result result = results.get(suiteName);
            if (result == null)
                continue;

            tests += result.getTests();
            failures += result.getFailures();
            errors += result.getErrors();
            time += result.getTime();
        }

        summary.setTests(tests);
        summary.setFailures(failures);
        summary.setErrors(errors);
        summary.setTime(time);
        summary.calculateSuccessRate();
    }

    public String generate() {

        StringBuilder sb = new StringBuilder();

        appendline(sb, "<html>");
        appendline(sb, "<head>");
        appendline(sb, "<META http-equiv=\"Content-Type\" content=\"text/html; charset=US-ASCII\">");
        appendline(sb, "<title>Unit Test Results: Summary</title>");
        appendline(sb, "<link rel=\"stylesheet\" type=\"text/css\" title=\"Style\"");
        appendline(sb, "   href=\"file://10.101.3.108/workflowunit/reports/html/stylesheet.css\">");
        appendline(sb, "</head>");
        appendline(sb, "<h1>Unit Test Results</h1>");
        appendline(sb, "<hr size=\"1\">");
        appendline(sb, "<h2>Summary</h2>");
        sb.append("<table class=\"details\" border=\"0\" cellpadding=\"5\" cellspacing=\"2\" width=\"95%\">");
        appendline(sb, "   <tr valign=\"top\">");
        appendline(sb, "       <th>Tests</th>");
        appendline(sb, "       <th>Failures</th>");
        appendline(sb, "       <th>Errors</th>");
        appendline(sb, "       <th>Success rate</th>");
        appendline(sb, "       <th>Time(s)</th>");
        appendline(sb, "   </tr>");

        appendline(sb, "   <tr valign=\"top\" class=\"" + summary.getClassType() + "\">");
        appendline(sb, "       <td>" + summary.getTests() + "</td>");
        appendline(sb, "       <td>" + summary.getFailures() + "</td>");
        appendline(sb, "       <td>" + summary.getErrors() + "</td>");
        appendline(sb, "       <td>" + summary.getFormattedSuccessRate() + "</td>");
        appendline(sb, "       <td>" + summary.getTime() + "</td>");
        appendline(sb, "   </tr>");

        appendline(sb, "");
        appendline(sb, "</table>");
        appendline(sb, "");
        appendline(sb, "<h2>Test Suites</h2>");
        sb.append("<table class=\"details\" border=\"0\" cellpadding=\"5\" cellspacing=\"2\" width=\"95%\">");
        appendline(sb, "   <tr valign=\"top\">");
        appendline(sb, "       <th width=\"80%\">Name</th>");
        appendline(sb, "       <th>Tests</th>");
        appendline(sb, "       <th>Failures</th>");
        appendline(sb, "       <th>Errors</th>");
        appendline(sb, "       <th>Success rate</th>");
        appendline(sb, "       <th nowrap=\"nowrap\">Time(s)</th>");
        appendline(sb, "   </tr>");

        for (String suiteName : suites) {
            Result result = results.get(suiteName);

            if (result != null) {
                appendline(sb, "   <tr valign=\"top\" class=\"" + result.getClassType() + "\">");
                appendline(sb, "       <td><a href=\"file://10.101.3.108/workflowunit/log/" + suiteName + ".log\">"
                        + suiteName + "</a></td>");
                appendline(sb, "       <td>" + result.getTests() + "</td>");
                appendline(sb, "       <td>" + result.getFailures() + "</td>");
                appendline(sb, "       <td>" + result.getErrors() + "</td>");
                appendline(sb, "       <td>" + result.getFormattedSuccessRate() + "</td>");
                appendline(sb, "       <td>" + result.getTime() + "</td>");
                appendline(sb, "   </tr>");
            } else {
                appendline(sb, "   <tr valign=\"top\" class=\"Error\">");
                appendline(sb, "       <td><a href=\"file://10.101.3.108/workflowunit/log/" + suiteName + ".log\">"
                        + suiteName + " (Test Failed)</a></td>");
                appendline(sb, "       <td></td>");
                appendline(sb, "       <td></td>");
                appendline(sb, "       <td></td>");
                appendline(sb, "       <td></td>");
                appendline(sb, "       <td></td>");
                appendline(sb, "   </tr>");
            }
        }

        appendline(sb, "</table>");
        appendline(sb, "<hr size=\"1\">");
        appendline(sb,
                "<h2>Reports: <a href=\"file://10.101.3.108/workflowunit/reports\">file://10.101.3.108/workflowunit/reports</a></h2>");
        appendline(sb,
                "<h2>Logs: <a href=\"file://10.101.3.108/workflowunit/log\">file://10.101.3.108/workflowunit/log</a></h2>");
        appendline(sb, "</body>");
        appendline(sb, "</html>");

        return sb.toString();
    }

    public void write(String file) {
        try {
            String html = generate();
            FileOutputStream out = new FileOutputStream(file);
            out.write(html.getBytes("UTF-8"));
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendline(StringBuilder sb, String line) {
        sb.append(line);
        sb.append(lineSeperator);
    }
}
