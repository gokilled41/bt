package gzhou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class ReplaceStringTask extends Task implements gzhou.Constants {

    private File file_ = null;
    private String encoding_ = "UTF-8";
    private String from_ = null;
    private String to_ = null;
    private File append_ = null;
    private File conf_ = null;

    private List<String> appendLines_;
    private boolean isAppend_ = false;
    private boolean after_ = true;
    private boolean remove_ = false;
    private boolean caseSensitive_ = true;
    private boolean appendTmp_ = false;
    private boolean onlySearch_ = false;
    private boolean onlyName_ = false;
    private boolean onlyFile_ = false;
    private boolean printSearch_ = false;
    private boolean printAll_ = false;
    private boolean logToFile_ = false;
    private boolean sortByErrorCode_ = false;
    private boolean printErrorCode_ = false;
    private String fileNamePattern_ = null;
    private String prefix_ = null;
    private String suffix_ = null;
    private String contains_ = null;
    private String msgCodePrefix_ = "";
    private String mcFile_;
    private String msgFile_;

    private List<String> fromList_ = new ArrayList<String>();
    private List<String> toList_ = new ArrayList<String>();
    private List<String> txtList_ = new ArrayList<String>();
    private List<String> dirList_ = new ArrayList<String>();
    private List<String> excludeList_ = new ArrayList<String>();
    private List<String> excludeFileNameList_ = new ArrayList<String>();
    private Map<String, List<String>> fromHas_ = new HashMap<String, List<String>>();
    private Map<String, List<String>> fromNotHas_ = new HashMap<String, List<String>>();

    private int currentLineIndex_;
    private File currentFile_;
    private Particular currentParticular_;
    private List<String> inList_;

    public void setFile(File file) {
        file_ = handleFile(file);
    }

    private File handleFile(File file) {
        try {
            // also find path from type and run
            String p = file.getAbsolutePath();
            String prefix = "C:\\workspace\\buildtemp\\";
            if (p.startsWith(prefix)) {
                p = gzhou.Util.cutFirst(p, prefix.length());
                if (!p.contains("\\")) {
                    List<String> list = gzhou.Util.getLines(TYPEANDRUN_CONFIG);
                    for (String s : list) {
                        if (s.startsWith(p + "|")) {
                            p = gzhou.Util.cut(s, "|", null);
                            if (p.contains("|")) {
                                p = gzhou.Util.cut(p, null, "|");
                            }
                        }
                    }
                }
            }
            return new File(p);
        } catch (Exception e) {
        }
        return file;
    }

    public void setEncoding(String encoding) {
        encoding_ = encoding;
    }

    public void setFrom(String from) {
        from_ = unwrap(from);
    }

    public void setTo(String to) {
        to_ = to;
    }

    public void setAppend(File append) {
        append_ = append;
        initAppendLines();
    }

    public void setConf(File conf) {
        conf_ = conf;
    }

    public void setAfter(boolean after) {
        after_ = after;
    }

    public void setRemove(boolean remove) {
        remove_ = remove;
    }

    public void setCase(boolean caseSensitive) {
        caseSensitive_ = caseSensitive;
    }

    public void setOnlySearch(boolean onlySearch) {
        onlySearch_ = onlySearch;
    }

    public void setOnlyName(boolean onlyName) {
        onlyName_ = onlyName;
    }

    public void setOnlyFile(boolean onlyFile) {
        onlyFile_ = onlyFile;
    }

    public void setPrintSearch(boolean printSearch) {
        printSearch_ = printSearch;
    }

    public void setContains(String contains) {
        contains_ = contains;
    }

    private void initAppendLines() {
        try {
            isAppend_ = true;
            appendLines_ = new ArrayList<String>();
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(append_), encoding_));
            String line;
            while ((line = in.readLine()) != null) {
                appendLines_.add(line);
            }
            in.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void execute() throws BuildException {
        try {
            doInit();
            if (conf_ != null) {
                for (int i = 0; i < fromList_.size(); i++) {
                    from_ = fromList_.get(i);
                    to_ = toList_.get(i);
                    for (String dir : dirList_) {
                        log("starting to replace: " + dir);
                        replace(new File(dir));
                    }
                }

            } else {
                replace(file_);
            }
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }

    private void doInit() {
        initConf();
        initLogFile();
        initTxt();
        initExclude();
        initFileNameExclude();
    }

    private void initLogFile() {
        if (logToFile_) {
            String logFilePath = getLogFile();
            File logFile = new File(logFilePath);
            if (logFile.exists()) {
                boolean recent = (System.currentTimeMillis() - logFile.lastModified() < 30 * 1000);
                if (!recent) {
                    logFile.delete();
                }
            }
        }
        // remove mc.txt and msg.txt
        File f = new File(desktopDir + "mc.txt");
        if (f.exists()) {
            f.delete();
        }
        f = new File(desktopDir + "msg.txt");
        if (f.exists()) {
            f.delete();
        }
    }

    private void initTxt() {
        add(txtList_, ".txt");
        add(txtList_, ".java");
        add(txtList_, ".xml");
        add(txtList_, ".xsd");
        add(txtList_, ".html");
        add(txtList_, ".ini");
        add(txtList_, ".properties");
        add(txtList_, ".bat");
        add(txtList_, ".project");
        add(txtList_, ".js");
        add(txtList_, ".jsp");
        add(txtList_, ".module");
        add(txtList_, ".component");
        add(txtList_, ".cmd");
        add(txtList_, ".sql");
        add(txtList_, ".lst");
        add(txtList_, ".log");
        add(txtList_, ".conf");
        add(txtList_, ".scala");
    }

    private void initExclude() {
        add(excludeList_, "svn");
        add(excludeList_, "tmp.txt");
        add(excludeList_, "log.txt");
    }

    private void initFileNameExclude() {
        add(excludeFileNameList_, "rename");
    }

    private void add(List<String> list, String s) {
        if (s != null && !list.contains(s))
            list.add(s);
    }

    private void initConf() {
        if (conf_ != null) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(conf_), encoding_));
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.startsWith("-"))
                        continue;
                    if (line.length() == 0)
                        continue;

                    if (line.startsWith("case: ")) {
                        String s = line.substring("case: ".length()).trim();
                        caseSensitive_ = Boolean.valueOf(s);
                    }
                    if (line.startsWith("appendTmp: ")) {
                        String s = line.substring("appendTmp: ".length()).trim();
                        appendTmp_ = Boolean.valueOf(s);
                    }
                    if (line.startsWith("onlySearch: ")) {
                        String s = line.substring("onlySearch: ".length()).trim();
                        onlySearch_ = Boolean.valueOf(s);
                    }
                    if (line.startsWith("printSearch: ")) {
                        String s = line.substring("printSearch: ".length()).trim();
                        printSearch_ = Boolean.valueOf(s);
                    }
                    if (line.startsWith("printAll: ")) {
                        String s = line.substring("printAll: ".length()).trim();
                        printAll_ = Boolean.valueOf(s);
                    }
                    if (line.startsWith("logToFile: ")) {
                        String s = line.substring("logToFile: ".length()).trim();
                        logToFile_ = Boolean.valueOf(s);
                    }
                    if (line.startsWith("sortByErrorCode: ")) {
                        String s = line.substring("sortByErrorCode: ".length()).trim();
                        sortByErrorCode_ = Boolean.valueOf(s);
                    }
                    if (line.startsWith("printErrorCode: ")) {
                        String s = line.substring("printErrorCode: ".length()).trim();
                        printErrorCode_ = Boolean.valueOf(s);
                    }
                    if (line.startsWith("fileNamePattern: ")) {
                        String s = line.substring("fileNamePattern: ".length()).trim();
                        fileNamePattern_ = s;
                    }
                    if (line.startsWith("prefix: ")) {
                        String s = line.substring("prefix: ".length()).trim();
                        prefix_ = s;
                    }
                    if (line.startsWith("suffix: ")) {
                        String s = line.substring("suffix: ".length()).trim();
                        suffix_ = s;
                    }
                    if (line.startsWith("contains: ")) {
                        String s = line.substring("contains: ".length()).trim();
                        contains_ = s;
                    }
                    if (line.startsWith("msgCodePrefix: ")) {
                        String s = line.substring("msgCodePrefix: ".length()).trim();
                        msgCodePrefix_ = s;
                    }
                    if (line.startsWith("mcFile: ")) {
                        String s = line.substring("mcFile: ".length()).trim();
                        mcFile_ = s;
                    }
                    if (line.startsWith("msgFile: ")) {
                        String s = line.substring("msgFile: ".length()).trim();
                        msgFile_ = s;
                    }
                    if (line.startsWith("txt: ")) {
                        String s = line.substring("txt: ".length()).trim();
                        add(txtList_, s);
                    }
                    if (line.startsWith("from: ")) {
                        String s = line.substring("from: ".length()).trim();
                        s = unwrap(s);
                        fromList_.add(s);
                    }
                    if (line.startsWith("to: ")) {
                        String s = line.substring("to: ".length()).trim();
                        s = unwrap(s);
                        toList_.add(s);
                    }
                    extractDir(line);
                    if (line.startsWith("exclude: ")) {
                        String s = line.substring("exclude: ".length()).trim();
                        add(excludeList_, s);
                    }
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void extractDir(String line) throws Exception {
        if (line.startsWith("dir: ")) {
            String s = line.substring("dir: ".length()).trim();
            if (s.equals("yodast.txt")) {
                try {
                    List<String> lines = FileUtil.getLines(desktopDir + "yodast.txt");
                    for (String lineString : lines) {
                        extractDir(lineString);
                    }
                } catch (Exception e) {
                }
            } else {
                add(dirList_, s);
            }
        }
        if (line.startsWith("M      ")) {
            String s = line.substring("M      ".length()).trim();
            s = YODA_DIR + FILE_SEPARATOR + s;
            add(dirList_, s);
        }
        if (line.startsWith("A      ")) {
            String s = line.substring("A      ".length()).trim();
            s = YODA_DIR + FILE_SEPARATOR + s;
            add(dirList_, s);
        }
    }

    private String unwrap(String s) {
        if (s.startsWith("\"") && s.endsWith("\"")) {
            // unwrap ""
            s = s.substring(1, s.length() - 1);
            // not contains strings
            List<String> fromNotHasList = null;
            if (s.contains("\\")) {
                String[] arr = s.split("\\\\");
                s = arr[0];

                fromNotHasList = new ArrayList<String>();
                for (int i = 1; i < arr.length; i++) {
                    fromNotHasList.add(arr[i]);
                }
            }
            // constains strings
            List<String> fromHasList = null;
            if (s.contains("/")) {
                String[] arr = s.split("/");
                s = arr[0];

                fromHasList = new ArrayList<String>();
                for (int i = 1; i < arr.length; i++) {
                    fromHasList.add(arr[i]);
                }
            }
            // put strings in maps
            if (fromHasList != null)
                fromHas_.put(s, fromHasList);
            if (fromNotHasList != null)
                fromNotHas_.put(s, fromNotHasList);
        }
        return s;
    }

    private void replace(File file) throws Exception {
        if (isExcluded(file))
            return;
        if (file.isFile()) {
            replaceInFile(file);
        } else {
            replaceInFolder(file);
        }
        if (!isFileNameExcluded(file)) {
            replaceInName(file);
        }
    }

    private boolean isExcluded(File file) {
        String name = file.getName();
        for (String ex : excludeList_) {
            if (name.contains(ex))
                return true;
        }
        return false;
    }

    private boolean isFileNameExcluded(File file) {
        if (onlyFile_)
            return true;
        String name = file.getName();
        for (String ex : excludeFileNameList_) {
            if (name.contains(ex))
                return true;
        }
        return false;
    }

    private void replaceInFile(File file) {
        if (isTextFile(file) && fileNameMatch(file))
            doReplaceInFile(file);
    }

    private boolean isTextFile(File file) {
        if (onlyName_)
            return false;
        String name = file.getName();
        for (String suffix : txtList_) {
            if (name.endsWith(suffix))
                return true;
        }
        return false;
    }

    private boolean fileNameMatch(File file) {
        String name = file.getName();
        if (prefix_ != null && !name.startsWith(prefix_))
            return false;
        if (suffix_ != null && !name.endsWith(suffix_))
            return false;
        if (contains_ != null && !contains(name, contains_))
            return false;
        if (fileNamePattern_ != null && !name.matches(fileNamePattern_))
            return false;
        return true;
    }

    private boolean contains(String name, String contains) {
        // support and "&", or "|"
        if (contains.contains("&")) {
            String[] arr = contains.split("&");
            for (String s : arr) {
                if (!name.contains(s)) {
                    return false;
                }
            }
            return true;
        }
        if (contains.contains("|")) {
            String[] arr = contains.split("\\|");
            for (String s : arr) {
                if (name.contains(s)) {
                    return true;
                }
            }
            return false;
        }
        return name.contains(contains);
    }

    private void replaceInFolder(File folder) throws Exception {
        // replace files
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                replace(file);
            }
        }
    }

    private void replaceInName(File file) throws Exception {
        if (!isAppend_) {
            String oldName = file.getName();
            List<String> list = replaceLine(oldName);
            if (list.size() > 0) {
                String newName = list.get(0);
                if (!newName.equals(oldName)) {
                    // folder name changed
                    renameFile(file, oldName, newName);
                } else {
                    // do nothing
                }
            }
        }
    }

    private void renameFile(File file, String oldName, String newName) {
        if (fileNameMatch(file)) {
            if (!onlySearch_) {
                File parent = file.getParentFile();
                String parentPath = parent.getAbsolutePath();
                String newFolderPath = parentPath + File.separator + newName;
                File newFolder = new File(newFolderPath);
                file.renameTo(newFolder);
                log("  rename: " + oldName + " --> " + newName);
            } else {
                log("  found \"" + from_ + "\" in file or folder name: \"" + file.getAbsolutePath() + "\"");
            }
        }
    }

    private void doReplaceInFile(File file) {
        String line = null;
        boolean hasChange = false;
        try {
            long start = System.currentTimeMillis();

            List<String> inList = new ArrayList<String>();
            List<String> outList = new ArrayList<String>();
            List<AffectLine> affectedList = new ArrayList<AffectLine>();
            currentFile_ = file;
            currentParticular_ = null;
            inList_ = inList;

            // load in lines
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding_));
            while ((line = in.readLine()) != null) {
                inList.add(line);
            }
            in.close();

            // replace or append or remove lines
            // outList.addAll(inList);

            for (int i = 0; i < inList.size(); i++) {
                TodoLines todoLines = new TodoLines();
                boolean changed = handleLines(outList, inList.get(i), i, affectedList, todoLines);
                if (changed)
                    hasChange = true;

                if (todoLines.op_ != null) {
                    if (todoLines.op_.equals("ignore")) {
                        i += todoLines.n_;
                    }
                }
            }

            if (hasChange) {
                if (!onlySearch_) {
                    // write out lines
                    File tmpFile = new File(file.getParent() + "\\tmp.txt");
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(tmpFile, appendTmp_),
                            encoding_));
                    for (String string : outList) {
                        out.println(string);
                    }
                    out.close();

                    if (!appendTmp_) {
                        file.delete();
                        tmpFile.renameTo(file);
                    }
                    long end = System.currentTimeMillis();

                    log("  replace \"{0}\" with \"{1}\" in \"{2}\" cost {3} ms.", from_, to_, file.getName(), end
                            - start);
                } else {
                    log("  found \"" + from_ + "\" in file: \"" + file.getAbsolutePath() + "\"");
                    log(" ");
                    if (printSearch_) {
                        if (printAll_) {
                            // log("  ");
                            int i = 1;
                            for (String string : inList) {
                                log("    " + i + ": " + string);
                                i++;
                            }
                            // log("  ");
                        } else {
                            // log("  ");
                            for (AffectLine affectLine : affectedList) {
                                log("    " + (affectLine.i_ + 1) + ": " + affectLine.line_);
                            }
                            // log("  ");
                        }
                    }
                    log(" ");
                    log(" ");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(line);
            e.printStackTrace();
        }
        resetReplaceInFile();
    }

    private void resetReplaceInFile() {
        currentFile_ = null;
        currentParticular_ = null;
        inList_ = null;
        matchedLineMapDFI18N_.clear();
    }

    public class AffectLine {
        public AffectLine(String line, int i) {
            line_ = line;
            i_ = i;
        }

        public String line_;
        public int i_;
    }

    public class TodoLines {
        public String op_;
        public int n_;
    }

    private boolean handleLines(List<String> outList, String line, int i, List<AffectLine> affectedList,
            TodoLines todoLines) throws Exception {
        currentLineIndex_ = i;
        if (contains(line)) {
            affectedList.add(new AffectLine(line, i));
            if (isAppend_) {
                appendLines(outList, line, i, todoLines);
            } else {
                replaceLines(outList, line, i);
            }
            return true;
        } else {
            outList.add(line);
            return false;
        }
    }

    private boolean contains(String line) throws Exception {
        if (isParticularConversion(line))
            return true;

        if (caseSensitive_)
            return lineConstains(line, from_);
        else
            return lineConstains(line, toLowerCase(from_)) || lineConstains(line, toUpperCase(from_))
                    || lineConstains(line, toCamelCase(from_));
    }

    private boolean lineConstains(String line, String from) {
        if (!line.contains(from))
            return false;
        List<String> fromNotHasList = fromNotHas_.get(from);
        if (fromNotHasList != null) {
            for (String fromNotHasString : fromNotHasList) {
                if (line.contains(fromNotHasString))
                    return false;
            }
        }
        List<String> fromHasList = fromHas_.get(from);
        if (fromHasList != null) {
            for (String fromHasString : fromHasList) {
                if (!line.contains(fromHasString))
                    return false;
            }
        }
        return true;
    }

    private void replaceLines(List<String> outList, String line, int i) throws Exception {
        setLinesInList(outList, i, replaceLine(line));
    }

    private void setLinesInList(List<String> outList, int i, List<String> replacedLines) {
        // outList.remove(i);
        outList.addAll(replacedLines);
    }

    private List<String> replaceLine(String line) throws Exception {
        if (isParticularConversion(line))
            return replaceParticular(line);
        else
            return replaceAll(line, from_, to_, caseSensitive_);
    }

    private void appendLines(List<String> outList, String line, int i, TodoLines todoLines) {
        if (lineConstains(line, from_)) {
            if (!remove_) {
                if (after_) {
                    List<String> list = new ArrayList<String>();
                    list.addAll(appendLines_);
                    outList.add(line);
                    for (String appendLine : list) {
                        outList.add(appendLine);
                    }
                } else {
                    List<String> list = new ArrayList<String>();
                    list.addAll(appendLines_);
                    for (String appendLine : list) {
                        outList.add(appendLine);
                    }
                    outList.add(line);
                }
            } else {
                if (after_) {
                    int size = appendLines_.size();
                    todoLines.op_ = "ignore";
                    todoLines.n_ = size;
                    outList.add(line);
                } else {
                    int size = appendLines_.size();
                    for (int j = 0; j < size; j++) {
                        outList.remove(i - size);
                    }
                    outList.add(line);
                }
            }
        } else {
            // do nothing
        }
    }

    //    private static String fixValue(String value) {
    //        if (value == null)
    //            return value;
    //
    //        if (value.contains("\\"))
    //            value = value.replaceAll("\\\\", "\\\\\\\\");
    //        if (value.contains("$"))
    //            value = value.replaceAll("\\$", "\\\\\\$");
    //        if (value.contains("("))
    //            value = value.replaceAll("\\(", "\\\\\\(");
    //        if (value.contains(")"))
    //            value = value.replaceAll("\\)", "\\\\\\)");
    //        if (value.contains("*"))
    //            value = value.replaceAll("\\*", "\\\\*");
    //        // System.out.println(value);
    //        return value;
    //    }

    //    private static String fixToValue(String value) {
    //        if (value == null)
    //            return value;
    //
    //        if (value.contains("\\"))
    //            value = value.replaceAll("\\\\", "\\\\\\\\");
    //        // System.out.println(value);
    //        return value;
    //    }

    private static List<String> replaceAll(String input, String from, String to, boolean caseSensitive) {
        String output;
        // from = fixValue(from);
        // to = fixToValue(to);
        if (caseSensitive) {
            output = input.replace(from, to);
        } else {
            output = input;
            output = output.replace(toLowerCase(from), toLowerCase(to));
            if (needUpper(from, to))
                output = output.replace(toUpperCase(from), toUpperCase(to));
            if (needCamel(from, to))
                output = output.replace(toCamelCase(from), toCamelCase(to));
        }
        return toList(output);
    }

    private static boolean needUpper(String from, String to) {
        return !from.equals(toUpperCase(from)) || !to.equals(toUpperCase(to));
    }

    private static boolean needCamel(String from, String to) {
        return !from.equals(toCamelCase(from)) || !to.equals(toCamelCase(to));
    }

    private static List<String> toList(String line) {
        List<String> list = new ArrayList<String>();
        list.add(line);
        return list;
    }

    private static String toCamelCase(String s) {
        if (!s.isEmpty())
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        return s;
    }

    private static String toUpperCase(String s) {
        return s.toUpperCase();
    }

    private static String toLowerCase(String s) {
        return s;
    }

    public void log(String msg, Object... objects) {
        msg = Util.format(msg, objects);
        super.log(msg);
        if (logToFile_)
            logToFile(getLogFile(), msg);
    }

    private void logToFile(String logFile, String msg) {
        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(logFile, true), encoding_));
            out.println(msg);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getLogFile() {
        return desktopDir + "rename\\log.txt";
    }

    private boolean isParticularConversion(String line) throws Exception {
        Particular particular = getParticular(line);
        if (particular != null)
            return particular.match(line);
        return false;
    }

    private List<String> replaceParticular(String line) throws Exception {
        return getParticular(line).replace(line);
    }

    private Particular getParticular(String line) throws Exception {
        if (currentParticular_ == null)
            currentParticular_ = doGetParticular(line);
        return currentParticular_;
    }

    private Particular doGetParticular(String line) throws Exception {
        if ((new LocaleP2MC()).match(line))
            return (new LocaleP2MC());
        if ((new LocaleCL2L()).match(line))
            return (new LocaleCL2L());
        if ((new LocaleSortMC()).match(line))
            return (new LocaleSortMC());
        if ((new LocaleSortMSG()).match(line))
            return (new LocaleSortMSG());
        if ((new CopyrightA2JF()).match(line))
            return (new CopyrightA2JF());
        if ((new DFI18NError()).match(line))
            return (new DFI18NError());
        if ((new DFI18NWarn()).match(line))
            return (new DFI18NWarn());
        if ((new DFI18NInfo()).match(line))
            return (new DFI18NInfo());
        if ((new DFI18NValidation()).match(line))
            return (new DFI18NValidation());
        if ((new DFI18NEx()).match(line))
            return (new DFI18NEx());
        if ((new DFModuleSrc()).match(line))
            return (new DFModuleSrc());
        if ((new XMLFormatNoReturn()).match(line))
            return (new XMLFormatNoReturn());
        return null;
    }

    private String nextLine() {
        if (currentLineIndex_ < inList_.size() - 1)
            return inList_.get(currentLineIndex_ + 1);
        return null;
    }

    private String nextLine2() {
        if (currentLineIndex_ < inList_.size() - 2)
            return inList_.get(currentLineIndex_ + 2);
        return null;
    }

    private static boolean lineMatch(List<String> a, String s) {
        for (String as : a) {
            if (s.contains(as))
                return true;
        }
        return false;
    }

    private List<String> removeDup(List<String> list) {
        List<String> list2 = new ArrayList<String>();
        for (String string : list) {
            if (!list2.contains(string)) {
                list2.add(string);
            }
        }
        return list2;
    }

    public interface Particular {
        public boolean match(String line) throws Exception;

        public List<String> replace(String line) throws Exception;
    }

    public abstract class ParticularBase implements Particular {
        public boolean match(String line) throws Exception {
            return matchFrom() && matchLine(line);
        }

        protected abstract boolean matchLine(String line) throws Exception;

        private boolean matchFrom() {
            return getClass().getName().toUpperCase().contains(from_.toUpperCase());
        }
    }

    public class LocaleP2MC extends ParticularBase {
        protected boolean matchLine(String line) throws Exception {
            return line != null && line.indexOf("=") > 0;
        }

        public List<String> replace(String line) throws Exception {
            String template = "    public static final String @keySlash@ = \"@keyDot@\";";
            line = line.trim();
            String keyDot = line.substring(0, line.indexOf("="));
            String keySlash = keyDot.replace(".", "_");
            template = template.replace("@keySlash@", keySlash);
            template = template.replace("@keyDot@", keyDot);
            return toList(template);
        }
    }

    public class LocaleCL2L extends ParticularBase {
        protected boolean matchLine(String line) throws Exception {
            return line != null && line.indexOf("LogFactory.getLog") > 0;
        }

        public List<String> replace(String line) throws Exception {
            String template = "    @a@ static final ZGFLogger logger_ = ZGFLogger.getLogger(@b@.class, \"dataflow\");";
            line = line.trim();
            String a = line.substring(0, line.indexOf(" "));
            String b = line.substring(line.indexOf("LogFactory.getLog") + "LogFactory.getLog".length() + 1,
                    line.indexOf(".class"));
            template = template.replace("@a@", a);
            template = template.replace("@b@", b);
            return toList(template);
        }
    }

    public class LocaleSortMC extends ParticularBase {
        private List<String> list_ = new ArrayList<String>();

        protected boolean matchLine(String line) throws Exception {
            return currentFile_ != null && currentFile_.getAbsolutePath().equals(mcFile_) && line != null
                    && lineConstains(line, "public static final String");
        }

        public List<String> replace(String line) throws Exception {
            list_.add(line);
            String nextLine = nextLine();
            if (nextLine == null || !matchLine(nextLine)) {
                addMCLines();
                excludeLines();
                list_ = removeDup(list_);
                Collections.sort(list_);
                return list_;
            } else {
                return new ArrayList<String>();
            }
        }

        private void addMCLines() throws Exception {
            if (currentFile_ != null && currentFile_.getAbsolutePath().equals(mcFile_)) {
                String mcFilePath = desktopDir + "mc.txt";
                if ((new File(mcFilePath)).exists()) {
                    List<String> lines = FileUtil.getLines(mcFilePath);
                    for (String line : lines) {
                        if (!list_.contains(line)) {
                            list_.add(line);
                        }
                    }
                }
            }
        }

        private void excludeLines() throws Exception {
            if (currentFile_ != null && currentFile_.getAbsolutePath().equals(mcFile_)
                    && currentFile_.getAbsolutePath().contains("model")) {
                String mcFilePath = desktopDir + "mc_exclude.txt";
                if ((new File(mcFilePath)).exists()) {
                    List<String> excludes = FileUtil.getLines(mcFilePath);
                    List<String> list = new ArrayList<String>();
                    for (String line : list_) {
                        if (lineMatch(excludes, line))
                            list.add(line);
                    }
                    list_.removeAll(list);
                }
            }
        }

    }

    public class LocaleSortMSG extends ParticularBase {
        private List<String> list_ = new ArrayList<String>();

        protected boolean matchLine(String line) throws Exception {
            return currentFile_ != null && currentFile_.getAbsolutePath().equals(msgFile_) && line != null;
        }

        public List<String> replace(String line) throws Exception {
            list_.add(line);
            String nextLine = nextLine();
            if (nextLine == null || !matchLine(nextLine)) {
                addMSGLines();
                excludeLines();
                sort();
                return list_;
            } else {
                return new ArrayList<String>();
            }
        }

        private void sort() {
            list_ = removeDup(list_);
            if (!sortByErrorCode_) {
                Collections.sort(list_);
            } else {
                Comparator<String> c = new Comparator<String>() {
                    public int compare(String o1, String o2) {
                        o1 = o1.substring(o1.indexOf("=") + 1);
                        o2 = o2.substring(o2.indexOf("=") + 1);
                        return o1.compareTo(o2);
                    }
                };
                Collections.sort(list_, c);
                printErrorCode();
            }
        }

        private void printErrorCode() {
            if (printErrorCode_) {
                String logFile = desktopDir + "ec.txt";
                for (String line : list_) {
                    String msg = line.substring(line.indexOf("=") + 1);
                    if (msg.startsWith("DF")) {
                        logToFile(logFile, msg);
                    }
                }
            }
        }

        private String fixLine(String line) {
            if (!line.endsWith("."))
                line = line + ".";
            return line;
        }

        private void addMSGLines() throws Exception {
            if (currentFile_ != null && currentFile_.getAbsolutePath().equals(msgFile_)) {
                String msgFilePath = desktopDir + "msg.txt";
                if ((new File(msgFilePath)).exists()) {
                    List<String> lines = FileUtil.getLines(msgFilePath);
                    for (String line : lines) {
                        if (!list_.contains(line)) {
                            list_.add(fixLine(line));
                        }
                    }
                }
            }
        }

        private void excludeLines() throws Exception {
            if (currentFile_ != null && currentFile_.getAbsolutePath().equals(msgFile_)) {
                String mcFilePath = desktopDir + "msg_exclude.txt";
                if ((new File(mcFilePath)).exists()) {
                    List<String> excludes = FileUtil.getLines(mcFilePath);
                    List<String> list = new ArrayList<String>();
                    for (String line : list_) {
                        if (lineMatch(excludes, line))
                            list.add(line);
                    }
                    list_.removeAll(list);
                }
            }
        }
    }

    public class CopyrightA2JF extends ParticularBase {
        private List<String> copyrightLines_;

        protected boolean matchLine(String line) throws Exception {
            return line != null && line.startsWith("package com.vitria") && currentLineIndex_ == 0;
        }

        public List<String> replace(String line) throws Exception {
            List<String> list = new ArrayList<String>();
            list.addAll(getCopyrightLines());
            list.add(line);
            return list;
        }

        private List<String> getCopyrightLines() throws Exception {
            if (copyrightLines_ == null) {
                copyrightLines_ = new ArrayList<String>();
                String file = txtDir + "copyright.txt";
                copyrightLines_.addAll(FileUtil.readLines(file));
            }
            return copyrightLines_;
        }
    }

    protected static StringBuilder sbDFI18N_;
    protected static String matchedLineDFI18N_;
    protected static Map<String, Boolean> matchedLineMapDFI18N_ = new HashMap<String, Boolean>();

    public abstract class DFI18N extends ParticularBase {

        @Override
        protected boolean matchLine(String line) throws Exception {
            String key = getClass().getName() + "_"
                    + (currentFile_ != null ? currentFile_.getAbsolutePath() + "_" : "") + line;
            Boolean matched = matchedLineMapDFI18N_.get(key);
            if (matched == null) {
                matched = doMatchLine(line);
                matchedLineMapDFI18N_.put(key, matched);
            }
            return matched;
        }

        private boolean doMatchLine(String line) {
            if (lineConstains(line, getContains() + getContainsSuffix()) && NotContains(line)) {
                sbDFI18N_ = new StringBuilder();
                sbDFI18N_.append(line);
            } else if (sbDFI18N_ != null) {
                sbDFI18N_.append(" ").append(line.trim());
            }
            if (sbDFI18N_ != null) {
                String s = sbDFI18N_.toString();
                if (s.endsWith(";")) {
                    matchedLineDFI18N_ = s;
                    sbDFI18N_ = null;
                    System.out.println("matched line: " + matchedLineDFI18N_);
                    return true;
                }
            }
            if (sbDFI18N_ != null || matchedLineDFI18N_ != null)
                return true;
            return false;
        }

        public List<String> replace(String line) throws Exception {
            if (matchedLineDFI18N_ != null) {
                String matchLine = matchedLineDFI18N_;
                matchedLineDFI18N_ = null;
                return toList(handleDataflowLogging(matchLine, currentFile_.getName(), getContains())[2]);
            } else {
                return new ArrayList<String>();
            }
        }

        protected abstract String getContains();

        protected String getContainsSuffix() {
            return "(\"";
        }

        protected boolean NotContains(String line) {
            return true;
        }

        protected String[] handleDataflowLogging(String s, String fileName, String loggerPrefix) {
            String prefix = s.substring(0, s.indexOf(loggerPrefix + getContainsSuffix()) + loggerPrefix.length() + 1);
            s = s.substring((prefix).length(), s.length() - ");".length());
            String anotherPrefix = s.substring(0, s.indexOf("\""));
            prefix += anotherPrefix;
            s = s.substring(s.indexOf("\""));

            String[] arr = s.split(", ");
            String e = null;
            if (arr.length > 1) {
                e = arr[1];
            }
            s = arr[0];

            arr = s.split("\\+");
            List<String> params = new ArrayList<String>();
            StringBuilder sb = new StringBuilder();
            int n = 0;
            for (int i = 0; i < arr.length; i++) {
                String item = arr[i].trim();
                if (item.startsWith("\"") && item.endsWith("\"")) {
                    sb.append(item.substring(1, item.length() - 1));
                } else {
                    params.add(item);
                    sb.append("\"{" + n + "}\"");
                    n++;
                }
            }

            String message = sb.toString();

            String keySentense = message;
            if (keySentense.contains("."))
                keySentense = keySentense.substring(0, keySentense.indexOf("."));
            if (keySentense.contains(":"))
                keySentense = keySentense.substring(0, keySentense.indexOf(":"));

            arr = keySentense.split(" ");
            sb = new StringBuilder();
            for (int i = 0; i < arr.length; i++) {
                s = arr[i];
                String cs = s.substring(0, 1).toUpperCase() + s.substring(1);
                sb.append(cs);
            }
            String key = sb.toString();
            key = key.replaceAll("FailTo", "FailedTo");
            key = key.replaceAll("Cannot", "FailedTo");
            key = key.replaceAll("CanNot", "FailedTo");
            key = key.replaceAll("\"{0}\"", "");
            key = key.replaceAll("\\{0\\}", "");
            key = key.replaceAll("\"{1}\"", "");
            key = key.replaceAll("\\{1\\}", "");
            key = key.replaceAll("\"{2}\"", "");
            key = key.replaceAll("\\{2\\}", "");
            key = key.replaceAll("#", "");

            if (key.endsWith("Failed"))
                key = "FailedTo" + key.substring(0, key.length() - "Failed".length());
            // if (key.contains("With"))
            //     key = key.substring(0, key.lastIndexOf("With"));
            if (key.contains("For"))
                key = key.substring(0, key.lastIndexOf("For"));
            if (key.contains("Of"))
                key = key.substring(0, key.lastIndexOf("Of"));
            if (key.contains("Since"))
                key = key.substring(0, key.lastIndexOf("Since"));
            if (key.contains("When"))
                key = key.substring(0, key.lastIndexOf("When"));
            // if (key.contains("From"))
            //     key = key.substring(0, key.lastIndexOf("From"));
            // if (key.lastIndexOf("To") > 8)
            //     key = key.substring(0, key.lastIndexOf("To"));

            fileName = fileName.substring(0, fileName.indexOf("."));
            String mcKey = fileName + "_" + key;
            String msgKey = fileName + "." + key;

            sb = new StringBuilder();
            sb.append(prefix);
            sb.append(getPrefixFollowing1());
            sb.append(msgCodePrefix_ + "MessageCode.");
            sb.append(mcKey);
            if (e != null) {
                sb.append(", ");
                sb.append("e");
            }
            for (int i = 0; i < params.size(); i++) {
                sb.append(", ");
                sb.append(params.get(i));
            }
            sb.append(getPrefixFollowing2());
            sb.append(");");
            String code = sb.toString();

            sb = new StringBuilder();
            sb.append("    public static final String ");
            sb.append(mcKey);
            sb.append(" = \"");
            sb.append(msgKey);
            sb.append("\";");
            String mc = sb.toString();

            // log(mc);
            logToFile(desktopDir + "mc.txt", mc);

            sb = new StringBuilder();
            sb.append(msgKey);
            sb.append("=");
            sb.append(message);
            String msg = sb.toString();

            // log(msg);
            logToFile(desktopDir + "msg.txt", msg);

            return new String[] { key, message, code };
        }

        protected String getPrefixFollowing1() {
            return "";
        }

        protected String getPrefixFollowing2() {
            return "";
        }
    }

    public class DFI18NError extends DFI18N {
        private static final String LOGGER_ERROR = "error";

        @Override
        protected String getContains() {
            return LOGGER_ERROR;
        }

    }

    public class DFI18NWarn extends DFI18N {
        private static final String LOGGER_WARN = "warn";

        @Override
        protected String getContains() {
            return LOGGER_WARN;
        }

    }

    public class DFI18NInfo extends DFI18N {
        private static final String LOGGER_INFO = "info";

        @Override
        protected String getContains() {
            return LOGGER_INFO;
        }

    }

    public class DFI18NValidation extends DFI18N {
        private static final String LOGGER_VALIDATION = "results.addResult";

        @Override
        protected String getContains() {
            return LOGGER_VALIDATION;
        }

        @Override
        protected String getContainsSuffix() {
            return "(";
        }

        @Override
        protected String getPrefixFollowing1() {
            return "logger_.getMessage(";
        }

        @Override
        protected String getPrefixFollowing2() {
            return ")";
        }

        @Override
        protected boolean NotContains(String line) {
            if (line != null && lineConstains(line, "logger_.getMessage("))
                return false;
            String nextLine = nextLine();
            if (nextLine != null && lineConstains(nextLine, "logger_.getMessage("))
                return false;
            String nextLine2 = nextLine2();
            if (nextLine2 != null && lineConstains(nextLine2, "logger_.getMessage("))
                return false;
            return true;
        }

    }

    public class DFI18NEx extends DFI18N {
        private static final String EXCEPTION = "Exception";

        @Override
        protected String getContains() {
            return EXCEPTION;
        }

        public List<String> replace(String line) throws Exception {
            if (matchedLineDFI18N_ != null) {
                String matchedLine = matchedLineDFI18N_;
                matchedLineDFI18N_ = null;
                return toList(handleDataflowExceptionLogging(matchedLine, currentFile_.getName())[2]);
            } else {
                return new ArrayList<String>();
            }
        }

        private String[] handleDataflowExceptionLogging(String s, String fileName) {
            // System.out.println("handleDataflowExceptionLogging: " + s);
            String prefix = s.substring(0, s.indexOf(EXCEPTION + getContainsSuffix()) + EXCEPTION.length() + 1);
            // System.out.println(prefix);
            s = s.substring(prefix.length(), s.length() - ");".length());
            // System.out.println(s);
            String[] arr = s.split(", ");
            String e = null;
            if (arr.length > 1) {
                e = arr[1];
                // System.out.println(e);
            }
            s = arr[0];
            // System.out.println(s);

            arr = s.split("\\+");
            List<String> params = new ArrayList<String>();
            StringBuilder sb = new StringBuilder();
            int n = 0;
            for (int i = 0; i < arr.length; i++) {
                String item = arr[i].trim();
                // System.out.println(item);
                if (item.startsWith("\"") && item.endsWith("\"")) {
                    sb.append(item.substring(1, item.length() - 1));
                } else {
                    params.add(item);
                    sb.append("\"{" + n + "}\"");
                    n++;
                }
            }

            String message = sb.toString();
            // System.out.println(message);

            String keySentense = message;
            if (keySentense.contains("."))
                keySentense = keySentense.substring(0, keySentense.indexOf("."));
            if (keySentense.contains(":"))
                keySentense = keySentense.substring(0, keySentense.indexOf(":"));

            // System.out.println(keySentense);

            arr = keySentense.split(" ");
            sb = new StringBuilder();
            for (int i = 0; i < arr.length; i++) {
                s = arr[i];
                if (s.length() > 0) {
                    String cs = s.substring(0, 1).toUpperCase() + s.substring(1);
                    sb.append(cs);
                }
            }
            String key = sb.toString();
            key = key.replaceAll("FailTo", "FailedTo");
            key = key.replaceAll("Cannot", "FailedTo");
            key = key.replaceAll("CanNot", "FailedTo");
            key = key.replaceAll("\"{0}\"", "");
            key = key.replaceAll("\\{0\\}", "");
            key = key.replaceAll("\"{1}\"", "");
            key = key.replaceAll("\\{1\\}", "");
            key = key.replaceAll("\"{2}\"", "");
            key = key.replaceAll("\\{2\\}", "");
            key = key.replaceAll("#", "");

            if (key.endsWith("Failed"))
                key = "FailedTo" + key.substring(0, key.length() - "Failed".length());
            // if (key.contains("With"))
            //     key = key.substring(0, key.lastIndexOf("With"));
            if (key.contains("For"))
                key = key.substring(0, key.lastIndexOf("For"));
            if (key.contains("Of"))
                key = key.substring(0, key.lastIndexOf("Of"));
            if (key.contains("Since"))
                key = key.substring(0, key.lastIndexOf("Since"));
            if (key.contains("When"))
                key = key.substring(0, key.lastIndexOf("When"));
            // if (key.contains("From"))
            //     key = key.substring(0, key.lastIndexOf("From"));
            // if (key.lastIndexOf("To") > 8)
            //     key = key.substring(0, key.lastIndexOf("To"));

            // System.out.println(key);

            if (fileName != null && fileName.length() > 0)
                fileName = fileName.substring(0, fileName.indexOf("."));
            String mcKey = fileName + "_" + key;
            String msgKey = fileName + "." + key;

            sb = new StringBuilder();
            sb.append(prefix);
            sb.append("logger_.getMessage(");
            sb.append(msgCodePrefix_ + "MessageCode.");
            sb.append(mcKey);
            for (int i = 0; i < params.size(); i++) {
                sb.append(", ");
                sb.append(params.get(i));
            }
            sb.append(")");
            if (e != null) {
                sb.append(", ");
                sb.append("e");
            }
            sb.append(");");
            String code = sb.toString();
            // System.out.println(code);

            sb = new StringBuilder();
            sb.append("    public static final String ");
            sb.append(mcKey);
            sb.append(" = \"");
            sb.append(msgKey);
            sb.append("\";");
            String mc = sb.toString();

            // log(mc);
            logToFile(desktopDir + "mc.txt", mc);

            sb = new StringBuilder();
            sb.append(msgKey);
            sb.append("=");
            sb.append(message);
            String msg = sb.toString();

            // log(msg);
            logToFile(desktopDir + "msg.txt", msg);

            return new String[] { key, message, code };
        }
    }

    public class DFModuleSrc extends ParticularBase {
        private static final String DFMS_TXT = "dfms.txt";
        private List<String> pathes_ = new ArrayList<String>();

        protected boolean matchLine(String line) throws Exception {
            if (currentFile_ != null) {
                return isModuleComponent(currentFile_);
            }
            return false;
        }

        public List<String> replace(String line) throws Exception {
            return toList(line);
        }

        private boolean isModuleComponent(File f) throws Exception {
            String path = f.getAbsolutePath();
            boolean b = path.contains("modules") && path.contains("components") && path.endsWith("build.xml");
            if (b)
                write();
            return b;
        }

        private void write() throws Exception {
            String srcName = getSrcName();
            String srcLoc = getSrcLoc();
            if (needWrite(srcName, srcLoc)) {
                load();
                add(pathes_, srcName);
                add(pathes_, srcLoc);
                FileUtil.setDesktopLines(DFMS_TXT, pathes_);
            }
        }

        private boolean needWrite(String srcName, String srcLoc) {
            return srcLoc != null && !pathes_.contains(srcLoc) && !srcLoc.endsWith("components")
                    && !srcLoc.contains("@");
        }

        private String getSrcLoc() {
            String path = currentFile_.getAbsolutePath();
            String[] arr = path.split("\\\\");
            // System.out.println(Arrays.toString(arr));
            if (arr.length == 12) {
                String module = arr[8];
                module = module.substring(0, module.length() - "module".length());
                // System.out.println(module);
                // String c = arr[arr.length - 2];
                // System.out.println(c);
                String srcLoc = path.substring(0, path.length() - "build.xml".length() - 1);
                // System.out.println(srcLoc);
                // String srcName = "udf_dev_module_" + module + "_" + c;
                // System.out.println(srcName);
                return srcLoc;
            } else if (arr.length == 13) {
                String module = arr[8 + 1];
                module = module.substring(0, module.length() - "module".length());
                // System.out.println(module);
                // String c = arr[arr.length - 2];
                // System.out.println(c);
                String srcLoc = path.substring(0, path.length() - "build.xml".length() - 1);
                // System.out.println(srcLoc);
                // String srcName = "udf_dev_module_" + module + "_" + c;
                // System.out.println(srcName);
                return srcLoc;
            }
            return null;
        }

        private String getSrcName() {
            String path = currentFile_.getAbsolutePath();
            String[] arr = path.split("\\\\");
            // System.out.println(Arrays.toString(arr));
            if (arr.length == 12) {
                String module = arr[8];
                module = module.substring(0, module.length() - "module".length());
                // System.out.println(module);
                String c = arr[arr.length - 2];
                // System.out.println(c);
                // String srcLoc = path.substring(0, path.length() - "build.xml".length() - 1);
                // System.out.println(srcLoc);
                String srcName = "udf_dev_module_" + module + "_" + c;
                // System.out.println(srcName);
                return srcName;
            } else if (arr.length == 13) {
                String module = arr[8 + 1];
                module = module.substring(0, module.length() - "module".length());
                // System.out.println(module);
                String c = arr[arr.length - 2];
                // System.out.println(c);
                // String srcLoc = path.substring(0, path.length() - "build.xml".length() - 1);
                // System.out.println(srcLoc);
                String srcName = "udf_dev_module_" + module + "_" + c;
                // System.out.println(srcName);
                return srcName;
            }
            return null;
        }

        private void load() throws Exception {
            try {
                pathes_ = FileUtil.getDesktopLines(DFMS_TXT);
            } catch (Exception e) {
                pathes_ = new ArrayList<String>();
            }
        }

    }

    protected static StringBuilder sbXMLFormatNoReturn_;
    protected static String matchedLineXMLFormatNoReturn_;

    public class XMLFormatNoReturn extends ParticularBase {

        @Override
        protected boolean matchLine(String line) throws Exception {
            if (line.trim().length() == 0)
                return true;
            if (line.trim().startsWith("<")) {
                sbXMLFormatNoReturn_ = new StringBuilder();
                sbXMLFormatNoReturn_.append(line);
            } else if (sbXMLFormatNoReturn_ != null) {
                if (sbXMLFormatNoReturn_.indexOf(line.trim()) < 0)
                    sbXMLFormatNoReturn_.append(" ").append(line.trim());
            }
            if (sbXMLFormatNoReturn_ != null) {
                String s = sbXMLFormatNoReturn_.toString();
                if (s.endsWith(">")) {
                    matchedLineXMLFormatNoReturn_ = s;
                    sbXMLFormatNoReturn_ = null;
                    return true;
                }
            }
            if (sbXMLFormatNoReturn_ != null || matchedLineXMLFormatNoReturn_ != null)
                return true;
            return false;
        }

        public List<String> replace(String line) throws Exception {
            if (matchedLineXMLFormatNoReturn_ != null) {
                String matchLine = matchedLineXMLFormatNoReturn_;
                matchedLineXMLFormatNoReturn_ = null;
                return toList(matchLine);
            } else {
                return new ArrayList<String>();
            }
        }

    }

    public class DefaultParticular extends ParticularBase {
        protected boolean matchLine(String line) throws Exception {
            return false;
        }

        public List<String> replace(String line) throws Exception {
            throw new UnsupportedOperationException("DefaultParticular do not need replace any lines.");
        }
    }

}
