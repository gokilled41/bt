package gzhou;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.vitria.dataflow.dmsdk.framework.util.LinesUtil;

public class DataflowDMSDKLocaleSyncTask extends Task {

    private static final String YODA = "D:\\jedi\\yoda";
    private static final String DMSDK = YODA + "\\unbundled\\af\\java\\dataflow\\tools\\dmsdk";
    private static final String LOCALE = DMSDK + "\\sdk\\src\\locale";
    private static final String LOCALE_EN = LOCALE + "\\en_US";
    private static final String LOCALE_CN = LOCALE + "\\zh_CN";
    private static final String CN_WORDS = "\\u4e2d\\u6587";

    @Override
    public void execute() throws BuildException {
        try {
            convert(LOCALE_EN, LOCALE_CN, CN_WORDS);
        } catch (Exception e) {
            throw new BuildException("Cannot execute: dataflow DMSDK locale sync task.", e);
        }
    }

    private void convert(String from, String to, String words) throws Exception {
        File fromFolder = new File(from);
        File[] files = fromFolder.listFiles();
        for (File file : files) {
            if (!file.getAbsolutePath().contains("svn")) {
                convertFile(file, to, words);
            }
        }
    }

    private void convertFile(File fromFile, String toDir, String words) throws Exception {
        List<String> lines = LinesUtil.getLines(fromFile.getAbsolutePath());
        lines = convertLines(lines, words);
        String toFile = toDir + "\\" + fromFile.getName();
        LinesUtil.setLines(toFile, lines);
        log("convert file: " + toFile);
    }

    private List<String> convertLines(List<String> lines, String words) {
        List<String> list = new ArrayList<String>();
        for (String line : lines) {
            if (isLine(line)) {
                line = fixLine(line, words);
            }
            list.add(line);
        }
        return list;
    }

    private boolean isLine(String line) {
        return countChar(line, '=') == 1 && countChar(line, '-') < 10;
    }

    private int countChar(String line, char c) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

    private String fixLine(String line, String words) {
        return line.replace("=", "=" + words);
    }

}
