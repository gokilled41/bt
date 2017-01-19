package gzhou;

import gzhou.FileUtil.ExpandLinesResult.ExpandLines;
import gzhou.FileUtil.FileTimestampResult.FileTimestamp;
import gzhou.FileUtil.GoDirResult.GoDir;
import gzhou.FileUtil.ListConditionResult.ListCondition;
import gzhou.FileUtil.OperateLinesResult.OperateLines;
import gzhou.FileUtil.OperateLinesResult.OperateLinesUtil;
import gzhou.FileUtil.OutputSummaryResult.OutputSummary;
import gzhou.FileUtil.ZipOperationsResult.ZipOperations;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLType;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.codec.binary.Hex;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.vitria.domainservice.util.DOMUtil;

public class FileUtil extends Util implements Constants {

    private static List<String> paOps_ = new ArrayList<String>();
    private static Map<String, String> paOpTypes_ = new HashMap<String, String>();
    public static boolean debug_ = false;
    public static boolean debug2_ = false;
    public static String logTab_;
    public static boolean outputToFile_ = false;

    private static TARAliasMatchNodeItem matchedItem_;

    static {
        PAOperations.initPA();
    }

    public static void main(String[] args) throws Exception {
        if (args[0].equals("help")) { // fu
            log("fuc:     clean");
            log("fucas:   copyAppSrc");
            log("furas:   removeAppSrc");
            log("fucmg:   copyMigration");
            log("fut:     translate");
            log("fub:     generateAllBatFiles");
            log("fubc:    generateDevBatFiles");
            log("fubp:    generatePrivateBranchBatFiles");
            log("fubnc:   generateAllBatFilesNoCommon");
            log("fubncf:  generateAllBatFilesNoCommonOnlyFile");
            log("fubdnc:  generateDevBatFilesNoCommon");
            log("fubpnc:  generatePrivateBranchBatFilesNoCommon");
            log("fubk:    generateKstBatFiles");
            log("fug:     gettersetter");
            log("fus:     generateCreateUserSql");
            log("func:    generateNCTemplate");
            log("fufp:    backUpFeedPublisher");
            log("fufpb:   overwriteFeedPublisher");
            log("fusast:  updateSastFile");
            log("fuwf:    watchFile");
            log("dfjs:    generateJSFileForDataflowComponent");
            log("futol:   toOneLine");
            log("gadd:    addSearchAndReplace");
            log("newbat:  newBat");
            log("badd:    addBat");
            log("tadd:    addTypeAndRunItem");
            log("tdel:    delTypeAndRunItem");
            log("flist:   listFiles");
            log("jlist:   listJavaClasses");
            log("godir:   goDir");
            log("sql:     sql");
            log("taj:     taj"); // ta for sa1,2,3
            log("tam:     tam"); // ta for yoda_main
            log("tas:     tas"); // ta for yoda_sjb
            log("tar:     tar"); // ta rename
            log("p2st:    p2st"); // patch to dstf
            log("palias:  palias"); // print tar alias
            log("git:     git"); // git
            log("custom:  custom"); // custom
        } else if (args[0].equals("clean")) { // fuc
            clean();
        } else if (args[0].equals("copyAppSrc")) { // fucas
            copyAppSrc();
        } else if (args[0].equals("removeAppSrc")) { // furas
            removeAppSrc();
        } else if (args[0].equals("cleanGzhouOld")) {
            cleanGzhouOld();
        } else if (args[0].equals("copyMigration")) { // fucmg
            copyMigration();
        } else if (args[0].equals("translate")) { // fut
            translate(args);
        } else if (args[0].equals("generateAllBatFiles")) { // fub
            generateBatFiles(false, false);
        } else if (args[0].equals("generateDevBatFiles")) { // fubd
            generateBatFiles(true, false);
        } else if (args[0].equals("generatePrivateBranchBatFiles")) { // fubp
            generateBatFiles(false, true);
        } else if (args[0].equals("generateAllBatFilesNoCommon")) { // fubnc
            generateBatFiles(false, false, false);
        } else if (args[0].equals("generateAllBatFilesNoCommonOnlyFile")) { // fubncf
            generateBatFiles(false, false, false, true);
        } else if (args[0].equals("generateDevBatFilesNoCommon")) { // fubd
            generateBatFiles(true, false, false);
        } else if (args[0].equals("generatePrivateBranchBatFilesNoCommon")) { // fubpnc
            generateBatFiles(false, true, false);
        } else if (args[0].equals("generateKstBatFiles")) { // fubk
            generateKstBatFiles();
        } else if (args[0].equals("gettersetter")) { // fug
            gettersetter();
        } else if (args[0].equals("generateCreateUserSql")) { // fus
            generateCreateUserSql();
        } else if (args[0].equals("generateNCTemplate")) { // fus
            generateNCTemplate();
            generateViewTemplate();
        } else if (args[0].equals("backUpFeedPublisher")) { // fus
            backUpFeedPublisher();
        } else if (args[0].equals("overwriteFeedPublisher")) { // fus
            overwriteFeedPublisher();
        } else if (args[0].equals("updateSastFile")) { // fusast
            updateSastFile(args[1], Boolean.valueOf(args[2]));
        } else if (args[0].equals("watchFile")) { // fuwf
            watchFile(args[1]);
        } else if (args[0].equals("generateJSFileForDataflowComponent")) { // dfjs
            if (args.length > 1)
                generateJSFileForDataflowComponent(args[1]);
            else
                log("dfjs <Component XML File>");
        } else if (args[0].equals("toOneLine")) { // futol
            toOneLine(args[1]);
        } else if (args[0].equals("addSearchAndReplace")) { // gadd
            addSearchAndReplace(args);
        } else if (args[0].equals("newBat")) { // newbat
            newBat(args);
        } else if (args[0].equals("addBat")) { // badd
            addBat(args);
        } else if (args[0].equals("addTypeAndRunItem")) { // tadd
            addTypeAndRunItem(args);
        } else if (args[0].equals("delTypeAndRunItem")) { // tdel
            delTypeAndRunItem(args);
        } else if (args[0].equals("listFiles")) { // flist
            listFiles(args);
        } else if (args[0].equals("listJavaClasses")) { // jlist
            listJavaClasses(args);
        } else if (args[0].equals("goDir")) { // godir
            goDir(args);
        } else if (args[0].equals("sql")) { // godir
            sql(args);
        } else if (args[0].equals("taj")) { // taj
            taj(args);
        } else if (args[0].equals("tam")) { // tam
            tam(args);
        } else if (args[0].equals("tas")) { // tas
            tas(args);
        } else if (args[0].equals("tar")) { // taj
            tar(args);
        } else if (args[0].equals("p2st")) { // p2st
            p2st(args);
        } else if (args[0].equals("palias")) { // palias
            palias(cutFirstArg(args));
        } else if (args[0].equals("git")) { // git
            git(cutFirstArg(args));
        } else if (args[0].equals("custom")) { // custom
            custom(cutFirstArg(args));
        }
    }

    public static void clean() throws Exception {
        cleanOld();
        cleanTester();
    }

    public static void cleanGzhouOld() throws Exception {
        cleanFolder("C:\\gzhou\\old");
    }

    public static void copyMigration() throws Exception {
        copyFolder("C:\\gzhou\\m3o_3.2GA", "C:\\gzhou\\Copy of m3o_3.2GA");
    }

    public static void createTesterFolders() throws Exception {
        String[] names = new String[] { "Changping_You", "Di_Wang", "Dongshi_Xia", "Kai_Shen", "Long_Lin", "Nan_Zhang",
                "Qunsheng_Wan", "Yu_Zhou", "Boyu_Guo" };

        for (String name : names) {
            String file = desktopDir + "Tester\\" + name;
            File f = new File(file);
            if (!f.exists()) {
                f.mkdirs();
                log("mkdir: " + file);
            }
        }
    }

    public static void increaseLogFileTime(String file, int sec) throws Exception {
        List<String> list = new ArrayList<String>();

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        while ((line = in.readLine()) != null) {
            list.add(line);
        }
        in.close();

        List<String> list2 = new ArrayList<String>();
        for (String string : list) {
            if (string.startsWith("2012-")) {
                Date d = sdf3.parse(string.substring(0, 23));
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                c.add(Calendar.SECOND, sec);
                String timeStr = sdf3.format(c.getTime());
                string = timeStr + string.substring(23);
            }
            list2.add(string);
        }

        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
        for (String string : list2) {
            out.println(string);
        }
        out.close();

        log("increase time " + sec + " seconds in log file: " + file);
    }

    /**
     * FileUtil.addVTBAInTypeAndRun("dmg", "C:\\\\gzhou\\\\m3o_3.2GA");
     */
    public static void addVTBAInTypeAndRun(String prefix, String vtba_home) throws Exception {
        String file = TYPEANDRUN_CONFIG;
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true)));

        String tmpFile = BAT_TEMPLATE_FILE;
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(tmpFile)));
        String line;
        while ((line = in.readLine()) != null) {
            line = line.replaceAll("PREFIX", prefix);
            line = line.replaceAll("VTBA_HOME", vtba_home);
            log("add line: " + line);
            out.println(line);
        }
        in.close();

        out.close();
    }

    /**
     * FileUtil.removeVTBAInTypeAndRun("dmg", "C:\\\\gzhou\\\\m3o_3.2GA");
     */
    public static void removeVTBAInTypeAndRun(String prefix, String vtba_home) throws Exception {
        String file = TYPEANDRUN_CONFIG;

        List<String> list = new ArrayList<String>();
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = in.readLine()) != null) {
                list.add(line);
            }
            in.close();
        }

        List<String> list2 = new ArrayList<String>();
        {
            String tmpFile = BAT_TEMPLATE_FILE;
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(tmpFile)));
            String line;
            while ((line = in.readLine()) != null) {
                line = line.replaceAll("PREFIX", prefix);
                line = line.replaceAll("VTBA_HOME", vtba_home);
                list2.add(line);
            }
            in.close();
        }

        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false)));
            for (String line : list) {
                if (!list2.contains(line)) {
                    out.println(line);
                } else {
                    log("remove line: " + line);
                }
            }
            out.close();
        }
    }

    public static void copyAppSrc() throws Exception {
        Set<String> set = new HashSet<String>();
        set.add(".svn");
        copyFolder(
                "C:\\Workflow-G\\documents\\M3O\\M3O_Projects\\Cypress\\Server\\5_Test\\2_Integration\\App_Support\\code",
                btDir + "src", null, set);
    }

    public static void removeAppSrc() throws Exception {
        deleteFolder(btDir + "src\\com", btDir + "src\\com", null, false);
        deleteFolder(btDir + "bin\\com", btDir + "bin\\com", null, false);
    }

    public static void translate(String[] args) throws Exception {

        String file = desktopDir + "translate.txt";
        String from = "target";
        String to = "source";
        boolean caseSensitive = true;

        if (args.length >= 2)
            file = args[1];
        if (args.length >= 3)
            from = args[2];
        if (args.length >= 4)
            to = args[3];
        if (args.length >= 5)
            caseSensitive = Boolean.valueOf(args[4]);

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        List<String> list = new ArrayList<String>();
        String line;
        while ((line = in.readLine()) != null) {
            line = line.replaceAll(from, to);
            if (!caseSensitive) {
                line = line.replaceAll(from.toUpperCase(), to.toUpperCase());
                line = line.replaceAll(firstToUpperCase(from), firstToUpperCase(to));
            }
            list.add(line);
        }
        in.close();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
        for (String string : list) {
            out.println(string);
        }
        out.close();
        log("translation from " + from + " to " + to + " is done.");
    }

    private static String firstToUpperCase(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static void generateBatFiles(boolean dev, boolean privateBranch) throws Exception {
        generateBatFiles(dev, privateBranch, true);
    }

    public static void generateBatFiles(boolean dev, boolean privateBranch, boolean withCommon) throws Exception {
        generateBatFiles(dev, privateBranch, withCommon, false);
    }

    public static void generateBatFiles(boolean dev, boolean privateBranch, boolean withCommon, boolean onlyFile)
            throws Exception {
        String prefix = "d";
        String dir = batDir;

        String batFile;
        String stFile = "sast.txt";

        String line;
        List<String> list = new ArrayList<String>();

        if (new File(desktopDir + stFile).exists()) {
            log("Read folders from " + desktopDir + stFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(desktopDir + stFile)));
            while ((line = in.readLine()) != null) {
                if (line == null || line.length() == 0 || line.startsWith("N"))
                    continue;

                if (line.substring(1).startsWith("      ")) {
                    line = line.substring(1).trim();

                    if (onlyFile) {
                        if (!line.endsWith(FILE_SEPARATOR))
                            addToListWithoutDup(list, line);
                    } else {
                        if (line.contains("\\com\\vitria\\")) {
                            // line = line.substring(0, line.indexOf("\\com\\vitria\\"));
                        }
                        if (line.contains(".")) {
                            line = line.substring(0, line.lastIndexOf("\\"));
                        }
                        if (line.endsWith("\\")) {
                            line = line.substring(0, line.lastIndexOf("\\"));
                        }
                        addToListWithoutDup(list, line);
                    }
                }
            }
            in.close();
        } else {
            log("File " + desktopDir + stFile + " does not exist.");
        }

        if (withCommon) {
            log("Add common folders.");
            addToListWithoutDup(list, "m3o\\server\\src\\client");
            addToListWithoutDup(list, "m3o\\server\\src\\core");
            addToListWithoutDup(list, "m3o\\server\\src\\virtualserver");
            addToListWithoutDup(list, "m3o\\server\\src\\domainservice");
            addToListWithoutDup(list, "m3o\\j2ee\\src\\application");
            addToListWithoutDup(list, "m3o\\server\\locale\\en_US");
        }

        // combine same folders
        List<String> list2 = new ArrayList<String>();
        list2.addAll(list);
        // Collections.sort(list2);
        // log("Sort folders.");

        List<String> list3 = new ArrayList<String>();
        String[] arr = list2.toArray(new String[list2.size()]);
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].startsWith(arr[i]))
                    list3.add(arr[j]);
            }
        }
        list2.removeAll(list3);
        log("Combine folders.");

        // remove devtests
        if (dev) {
            List<String> list4 = new ArrayList<String>();
            for (String s : list2) {
                if (s.contains("devtests"))
                    list4.add(s);
            }
            list2.removeAll(list4);
            log("Remove devtests folders.");
        }

        log("Folders are: ");
        StringBuilder sb = new StringBuilder();
        for (String s : list2) {
            log("    " + s);
            sb.append(s);
            sb.append(" ");
        }
        String folders = sb.toString().trim();

        // st
        batFile = dir + prefix + "st.bat";
        log("Generate: " + batFile);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
        out.println("@echo off");
        out.println("call yodadir");
        out.println("call svn st " + folders);
        out.close();

        // up
        batFile = dir + prefix + "up.bat";
        log("Generate: " + batFile);
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
        out.println("@echo off");
        out.println("call yodadir");
        out.println("call svn up " + folders);
        out.close();

        // sd
        batFile = dir + prefix + "sd.bat";
        log("Generate: " + batFile);
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
        out.println("@echo off");
        out.println("call yodadir");
        out.println("call svn diff " + folders);
        out.close();

        // stf
        batFile = dir + prefix + "stf.bat";
        log("Generate: " + batFile);
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
        out.println("@echo off");
        out.println("call yodadir");
        out.println("call svn st " + folders + " > \"" + desktopDir + "yodast.txt\"");
        out.close();

        // sdf
        batFile = dir + prefix + "sdf.bat";
        log("Generate: " + batFile);
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
        out.println("@echo off");
        out.println("call yodadir");
        out.println("call svn diff " + folders + " > \"" + desktopDir + "yoda.patch\"");
        out.println("if \"%mdf%\"==\"\" " + toExplorerLine(desktopDir + "yoda.patch"));
        out.close();

        // sc
        batFile = dir + prefix + "sc.bat";
        log("Generate: " + batFile);
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
        out.println("@echo off");
        out.println("call yodadir");
        out.println("call svn commit " + folders + " -m %1 ");
        out.close();

        // sr
        batFile = dir + prefix + "sr.bat";
        log("Generate: " + batFile);
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
        for (String s : list2) {
            out.println("@echo off");
            out.println("call yodadir");
            out.println("cd " + s);
            out.println("call sr");
            out.println();
        }
        out.close();

        // si
        batFile = dir + prefix + "si.bat";
        log("Generate: " + batFile);
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
        for (String s : list2) {
            out.println("@echo off");
            out.println("call yodadir");
            out.println("cd " + s);
            out.println("call svn info");
            out.println();
        }
        out.close();

        // b
        batFile = dir + prefix + "b.bat";
        log("Generate: " + batFile);
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
        for (String s : list2) {
            out.println("@echo off");
            out.println("call yodadir");
            out.println("cd " + s);
            out.println("call ant");
            out.println();
        }
        out.close();

        // sa
        {
            String yodast = desktopDir + "yodast.txt";
            File yodastFile = new File(yodast);
            if (yodastFile.exists()) {
                batFile = dir + prefix + "sa.bat";
                log("Generate: " + batFile);
                out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));

                List<String> addList = new ArrayList<String>();
                List<String> delList = new ArrayList<String>();
                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(yodastFile)));
                String addLine;
                while ((addLine = in.readLine()) != null) {
                    if (addLine.startsWith("?"))
                        addList.add(addLine.substring(1).trim());
                    if (addLine.startsWith("!"))
                        delList.add(addLine.substring(1).trim());
                }

                out.println("@echo off");
                out.println("call yodadir");
                for (String s : addList) {
                    out.println("call svn add " + wrapWithQuota(s));
                }
                for (String s : delList) {
                    out.println("call svn del " + wrapWithQuota(s));
                }
                out.close();
                in.close();
            }
        }

        // srm
        {
            String yodast = desktopDir + "yodast.txt";
            File yodastFile = new File(yodast);
            if (yodastFile.exists()) {
                batFile = dir + prefix + "srm.bat";
                log("Generate: " + batFile);
                out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));

                List<String> addList = new ArrayList<String>();
                List<String> delList = new ArrayList<String>();
                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(yodastFile)));
                String addLine;
                while ((addLine = in.readLine()) != null) {
                    if (addLine.startsWith("?"))
                        addList.add(addLine.substring(1).trim());
                    if (addLine.startsWith("!"))
                        delList.add(addLine.substring(1).trim());
                }

                out.println("@echo off");
                out.println("call yodadir");
                for (String s : addList) {
                    if (isFilePath(s))
                        out.println("call del " + wrapWithQuota(s));
                    else
                        out.println("call rmdir /s/q " + wrapWithQuota(s));
                }
                out.close();
                in.close();
            }
        }

        if (privateBranch) {
            // mdf
            batFile = privateBranchDir + "mdf.bat";
            log("Generate: " + batFile);
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
            Set<String> parent = new HashSet<String>();
            for (String s : list2) {
                parent.add(getParent(s));
            }
            for (String s : parent) {
                out.println("md " + s);
            }
            out.close();

            // svncp
            batFile = privateBranchDir + "svncp.bat";
            log("Generate: " + batFile);
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
            for (String s : list2) {
                sb = new StringBuilder();
                sb.append("call svn cp ");
                sb.append(mainBranchUrl + toLeftSlash(s));
                sb.append(" ");
                sb.append(privateBranchUrl + toLeftSlash(getParent(s)));
                sb.append(" -m \"copy from main branch\"");
                out.println(sb.toString());
            }
            out.close();

            // swet
            batFile = privateBranchDir + "sw" + featureShortCut + ".bat";
            log("Generate: " + batFile);
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
            out.println("c:");
            out.println("set YODA_HOME=C:\\zhou\\yoda");
            out.println("");

            for (String s : list2) {
                sb = new StringBuilder();
                sb.append("cd %YODA_HOME%\\");
                sb.append(s);
                out.println(sb.toString());

                sb = new StringBuilder();
                sb.append("call svn sw ");
                sb.append(privateBranchUrl + toLeftSlash(s));
                out.println(sb.toString());

                out.println();
            }
            out.close();

            // swbet
            batFile = privateBranchDir + "swb" + featureShortCut + ".bat";
            log("Generate: " + batFile);
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
            out.println("c:");
            out.println("set YODA_HOME=C:\\zhou\\yoda");
            out.println("");

            for (String s : list2) {
                sb = new StringBuilder();
                sb.append("cd %YODA_HOME%\\");
                sb.append(s);
                out.println(sb.toString());

                sb = new StringBuilder();
                sb.append("call svn sw ");
                sb.append(mainBranchUrl + toLeftSlash(s));
                out.println(sb.toString());

                out.println();
            }
            out.close();

            // svnadd
            String yodast = desktopDir + "yodast.txt";
            File yodastFile = new File(yodast);
            if (yodastFile.exists()) {
                batFile = privateBranchDir + "svnadd.bat";
                log("Generate: " + batFile);
                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(yodastFile)));
                out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));

                while ((line = in.readLine()) != null) {
                    if (line.startsWith("A      ") && line.contains(".")) {
                        out.println("call svn add " + line.substring("A      ".length()));
                    } else if (line.startsWith("D      ") && line.contains(".")) {
                        out.println("call svn del " + line.substring("D      ".length()));
                    }
                }

                in.close();
                out.close();
            }
        }
    }

    public static void generateKstBatFiles() throws Exception {
        String prefix = "k";
        String dir = batDir;
        String batFile;
        PrintWriter out;

        // sa
        {
            String yodast = desktopDir + "kst.txt";
            File yodastFile = new File(yodast);
            if (yodastFile.exists()) {
                batFile = dir + prefix + "sa.bat";
                log("Generate: " + batFile);
                out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));

                List<String> addList = new ArrayList<String>();
                List<String> delList = new ArrayList<String>();
                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(yodastFile)));
                String addLine;
                while ((addLine = in.readLine()) != null) {
                    if (addLine.startsWith("?"))
                        addList.add(addLine.substring(1).trim());
                    if (addLine.startsWith("!"))
                        delList.add(addLine.substring(1).trim());
                }

                for (String s : addList) {
                    out.println("call svn add " + wrapWithQuota(s));
                }
                for (String s : delList) {
                    out.println("call svn del " + wrapWithQuota(s));
                }
                out.close();
                in.close();
            }
        }

    }

    private static void addToListWithoutDup(List<String> list, String line) {
        if (!list.contains(line))
            list.add(line);
    }

    public static void gettersetter() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(desktopDir + "translate.txt")));
        List<String> list = new ArrayList<String>();
        String line;
        String s = "";
        while ((line = in.readLine()) != null) {
            s = line;
            while (true) {
                if (line.contains("_")) {
                    int i = line.lastIndexOf("_");
                    String next = line.substring(i + 1, i + 2);
                    if (next.equals("(")
                            || next.equals(")")
                            || (next.equals(";") && !line.trim().startsWith("return")
                                    && !line.trim().startsWith("private") && !line.trim().startsWith("public"))) {
                        s = line.substring(0, i) + line.substring(i + 1, line.length());
                    }
                }
                if (line.equals(s))
                    break;
                else
                    line = s;
            }

            line = line.replaceAll("", "");
            list.add(line);
        }
        in.close();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(desktopDir + "translate.txt")));
        for (String string : list) {
            out.println(string);
        }
        out.close();
        log("translation for getter and setter is done.");
    }

    public static void generateCreateUserSql() throws Exception {
        File file = new File(desktopDir + "user.sql");
        if (file.exists()) {
            log("Delete: " + file.getAbsolutePath());
            file.delete();
        }
        generateCreateUserSql("zgf_domainds");
        generateCreateUserSql("zgf_m3ods1");
        generateCreateUserSql("zgf_m3ods2");
        generateCreateUserSql("zgf_migration");
    }

    public static void generateNCTemplate() throws Exception {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                "C:\\zhou\\yoda\\unbundled\\apps\\activity_stream\\server\\libs\\src\\engine\\com\\vitria\\as\\ncgenerator.xsl")));
        PrintWriter out = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream(
                                "C:\\zhou\\yoda\\unbundled\\apps\\activity_stream\\server\\libs\\src\\engine\\com\\vitria\\as\\NCTemplate.java",
                                false)));

        out.println("// Copyright (c) 2013 Vitria Technology, Inc.");
        out.println("// All Rights Reserved.");
        out.println("//");
        out.println();
        out.println("package com.vitria.as;");
        out.println();
        out.println("import com.vitria.nc.util.util.DOMUtil;");
        out.println();
        out.println("public class NCTemplate {");
        out.println();
        out.println("    private static String template_;");
        out.println();
        out.println("    public synchronized static String getTemplate() {");
        out.println("        if (template_ == null) {");
        out.println("            StringBuilder sb = new StringBuilder();");
        out.println("");

        String line;
        while ((line = in.readLine()) != null) {

            line = line.replaceAll("\"", "\\\\\"");

            StringBuilder sb = new StringBuilder();
            sb.append("            sb.append(\"");
            sb.append(line);
            for (int i = 0; i < 200 - line.length(); i++) {
                sb.append(" ");
            }
            sb.append("\");");

            out.println(sb.toString());
        }

        out.println();
        out.println("            template_ = DOMUtil.format(sb.toString());");
        out.println("        }");
        out.println("");
        out.println("        return template_;");
        out.println("    }");
        out.println("}");

        in.close();
        out.close();

        log("NCTemplate.java is generated from ncgenerator.xsl.");

    }

    public static void generateViewTemplate() throws Exception {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                "C:\\zhou\\yoda\\unbundled\\apps\\activity_stream\\server\\libs\\src\\engine\\com\\vitria\\as\\instance_view.xml")));
        PrintWriter out = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream(
                                "C:\\zhou\\yoda\\unbundled\\apps\\activity_stream\\server\\libs\\src\\engine\\com\\vitria\\as\\ViewTemplate.java",
                                false)));

        out.println("// Copyright (c) 2013 Vitria Technology, Inc.");
        out.println("// All Rights Reserved.");
        out.println("//");
        out.println();
        out.println("package com.vitria.as;");
        out.println();
        out.println("import com.vitria.nc.util.util.DOMUtil;");
        out.println();
        out.println("public class ViewTemplate {");
        out.println();
        out.println("    private static String template_;");
        out.println();
        out.println("    public synchronized static String getTemplate() {");
        out.println("        if (template_ == null) {");
        out.println("            StringBuilder sb = new StringBuilder();");
        out.println("");

        String line;
        while ((line = in.readLine()) != null) {

            line = line.replaceAll("\"", "\\\\\"");

            StringBuilder sb = new StringBuilder();
            sb.append("            sb.append(\"");
            sb.append(line);
            for (int i = 0; i < 100 - line.length(); i++) {
                sb.append(" ");
            }
            sb.append("\");");

            out.println(sb.toString());
        }

        out.println();
        out.println("            template_ = DOMUtil.format(sb.toString());");
        out.println("        }");
        out.println("");
        out.println("        return template_;");
        out.println("    }");
        out.println("}");

        in.close();
        out.close();

        log("ViewTemplate.java is generated from instance_view.xml.");

    }

    public static void backUpFeedPublisher() throws Exception {
        copyFile(
                "C:\\zhou\\yoda\\unbundled\\af\\java\\nc_framework\\utility\\com\\vitria\\o2\\nc\\publisher\\FeedPublisher.java",
                "C:\\Workflow-G\\workflow bug fixing\\2012-12-11 Hadoop\\feed_publisher\\modified\\FeedPublisher.java");
        copyFile(
                "C:\\Workflow-G\\workflow bug fixing\\2012-12-11 Hadoop\\feed_publisher\\not_modified\\FeedPublisher.java",
                "C:\\zhou\\yoda\\unbundled\\af\\java\\nc_framework\\utility\\com\\vitria\\o2\\nc\\publisher\\FeedPublisher.java");
    }

    public static void overwriteFeedPublisher() throws Exception {
        copyFile(
                "C:\\zhou\\yoda\\unbundled\\af\\java\\nc_framework\\utility\\com\\vitria\\o2\\nc\\publisher\\FeedPublisher.java",
                "C:\\Workflow-G\\workflow bug fixing\\2012-12-11 Hadoop\\feed_publisher\\not_modified\\FeedPublisher.java");
        copyFile(
                "C:\\Workflow-G\\workflow bug fixing\\2012-12-11 Hadoop\\feed_publisher\\modified\\FeedPublisher.java",
                "C:\\zhou\\yoda\\unbundled\\af\\java\\nc_framework\\utility\\com\\vitria\\o2\\nc\\publisher\\FeedPublisher.java");
    }

    public static void updateSastFile(String key, boolean comment) throws Exception {
        String fromkey = key;
        key = "--- " + key;
        String file = desktopDir + "sast.txt";
        List<String> lines = getLines(file);
        List<String> list = new ArrayList<String>();
        boolean start = false;
        for (String line : lines) {
            if (start && !line.isEmpty()) {
                if (comment) {
                    if (!line.startsWith("-"))
                        line = "-" + line;
                } else {
                    if (line.startsWith("-"))
                        line = line.substring(1);
                }
            }
            list.add(line);

            if (line.equals(key)) {
                start = true;
            }
            if (line.isEmpty()) {
                start = false;
            }
        }
        setLines(file, list);
        if (comment)
            log("disable sast: " + fromkey);
        else
            log("enable sast: " + fromkey);
    }

    public static void watchFile(String file) throws Exception {
        int i = 0;
        List<String> lines = getLinesNoEx(file);
        while (true) {
            if (lines.size() > i) {
                for (int j = i; j < lines.size(); j++) {
                    log(lines.get(j));
                }
                i = lines.size();
            }
            Thread.sleep(2000);
            lines = getLinesNoEx(file);
        }
    }

    public static void generateJSFileForDataflowComponent(String file) throws Exception {
        Document doc = DOMUtil.getDocumentFromFile(file);
        Node root = doc.getDocumentElement();
        String compLabel = DOMUtil.getAttribute(root, "@label");
        String jsFileName = compLabel.replace(" ", "") + ".js";
        String moduleDir = getModuleDir(file);
        String jsFile = moduleDir + "\\resources\\html\\vitria\\flow\\nls\\" + jsFileName;
        String jsCNFile = moduleDir + "\\resources\\html\\vitria\\flow\\nls\\zh\\" + jsFileName;

        List<String> lines = new ArrayList<String>();
        lines.add("define({");
        lines.add("    root : {");
        lines.add("    \"label\": \"" + compLabel + "\",");
        Node[] nodes = DOMUtil.getNodes(root, "/component/properties/property");
        for (int i = 0; i < nodes.length; i++) {
            Node node = nodes[i];
            boolean last = (i == nodes.length - 1);
            printNode(node, last, lines);
        }
        lines.add("    },");
        lines.add("    \"zh\" : true");
        lines.add("});");
        setLines(jsFile, lines);

        lines = new ArrayList<String>();
        log(" ");
        log(" ");
        lines.add("define({");
        lines.add("    \"label\": \"" + compLabel + "麒麟\",");
        for (int i = 0; i < nodes.length; i++) {
            Node node = nodes[i];
            boolean last = (i == nodes.length - 1);
            printCNNode(node, last, lines);
        }
        lines.add("    \"_END_\" : \"_END_\"})");
        setLines(jsCNFile, lines);
    }

    public static void toOneLine(String file) throws Exception {
        List<String> lines = getLinesNoEx(file);
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line.trim());
        }
        File f = new File(file);
        String n = f.getName();
        String ext = n.substring(n.lastIndexOf(".") + 1);
        String sn = n.substring(0, n.lastIndexOf("."));
        String p = f.getParent();
        String newfile = p + FILE_SEPARATOR + sn + ".1." + ext;
        setLines(newfile, toList(sb.toString()));
        log("generate: " + newfile);
    }

    public static void addSearchAndReplace(String[] args) throws Exception {
        if (args.length < 3) {
            log("gadd <suffix> <dir>");
            return;
        }
        String suffix = args[1];
        String dir = args[2];
        doAddSearchAndReplace(suffix, dir, "gf");
        doAddSearchAndReplace(suffix, dir, "gfc");
        doAddSearchAndReplace(suffix, dir, "gr");
        doAddSearchAndReplace(suffix, dir, "grc");
    }

    private static void doAddSearchAndReplace(String suffix, String dir, String cmd) throws Exception {
        String filePath = batDir + cmd + suffix + ".bat";
        setLines(filePath, toList("call " + cmd + " \"" + dir + "\" %*"));
        log("generate: " + filePath);
    }

    public static void newBat(String[] args) throws Exception {
        if (args.length < 3) {
            log("newbat <batName> <callBatName>");
            return;
        }
        String batName = args[1];
        String callBatName = args[2];
        String f = batDir + batName + ".bat";
        List<String> list = new ArrayList<String>();
        list.add("@echo off");
        list.add("call c");
        list.add("call " + callBatName);
        setLines(f, list);
        log("generate: " + f);
    }

    public static void addBat(String[] args) throws Exception {
        if (args.length < 3) {
            log("badd <batName> <lineStrings>");
            return;
        }
        String batName = args[1];
        String lineStrings = args[2];
        String[] arr = lineStrings.split(";");
        List<String> list = Arrays.asList(arr);
        addBat(batName, list);
    }

    public static void addTypeAndRunItem(String[] args) throws Exception {
        if (args.length < 3) {
            log("tadd <name> <command>");
            return;
        }
        String name = args[1];
        String command = toCommand(args);
        String line = name + "|" + command;
        String f = TYPEANDRUN_CONFIG;
        List<String> list = getLines(f, GBK);
        list.add(line);
        Comparator<String> c = new Comparator<String>() {
            public int compare(String o1, String o2) {
                String n1 = o1.substring(0, o1.indexOf("|")).toLowerCase();
                String n2 = o2.substring(0, o2.indexOf("|")).toLowerCase();
                return n1.compareTo(n2);
            }
        };
        Collections.sort(list, c);
        setLines(f, list, GBK);
        log("add typeandrun item: " + line);
    }

    private static String toCommand(String[] args) {
        int l = args.length;
        if (l == 3) {
            return args[2];
        } else { // l > 3
            StringBuilder sb = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                sb.append(args[i]);
                if (i < args.length - 1) {
                    sb.append(" ");
                }
            }
            return sb.toString();
        }
    }

    public static void delTypeAndRunItem(String[] args) throws Exception {
        if (args.length < 2) {
            log("tdel <name>");
            return;
        }
        String name = args[1];
        String prefix = name + "|";
        String f = TYPEANDRUN_CONFIG;
        String line = null;
        List<String> list = getLines(f, GBK);
        List<String> list2 = new ArrayList<String>();
        for (String s : list) {
            if (!s.startsWith(prefix)) {
                list2.add(s);
            } else {
                line = s;
            }
        }
        if (line != null) {
            setLines(f, list2, GBK);
            log("del typeandrun item: " + line);
        } else {
            log("typeandrun item not found: " + name);
        }
    }

    public static void listFiles(String[] args) throws Exception {
        if (args.length < 2) {
            log("flist <dir> <-t timestamp>");
            log("  - timestamp: list files after this timestamp. like: 2015-01-01 10:00:00");
            return;
        }
        String dir = args[1];
        dir = toTARAlias(dir);
        long t = 0;
        boolean viewFileContent = false;
        int viewLines = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-t")) {
                i++;
                t = sdf4.parse(args[i]).getTime();
            } else if (args[i].equalsIgnoreCase("-v")) {
                i++;
                viewFileContent = true;
                viewLines = Integer.valueOf(args[i]);
            }
        }
        List<File> files = listFiles(new File(dir), true);
        for (File file : files) {
            if (t > 0) {
                if (file.lastModified() < t)
                    continue;
            }

            log(file.getAbsolutePath() + "   " + sdf4.format(new Date(file.lastModified())));
            if (viewFileContent) {
                List<String> lines = getLines(file.getAbsolutePath(), viewLines);
                int i = 0;
                for (String line : lines) {
                    log("    " + (++i) + ": " + line);
                }
            }
        }

    }

    public static void listJavaClasses(String[] args) throws Exception {
        if (args.length < 2) {
            log("jlist <dir> <className>");
            return;
        }
        String dir = args[1];
        dir = toTARAlias(dir);
        String className = fixClassName(args[2]);
        List<String> javaClasses = listJavaClasses(dir, className);
        for (String c : javaClasses) {
            log(c);
        }
    }

    private static String fixClassName(String c) {
        if (c.contains("\\"))
            c = c.replace("\\", ".");
        if (c.endsWith(".java"))
            c = cut(c, null, ".java");
        return c;
    }

    public static void goDir(String[] args) throws Exception {
        if (args.length < 2) {
            log("godir <alias>");
            return;
        }
        String godir = args[1];
        godir = toTARAlias(godir);

        String dir = batDir;
        String batFile;
        PrintWriter out;

        // gothisdir
        {
            try {
                batFile = dir + "gothisdir.bat";
                out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
                String driver = cut(godir, null, ":").toLowerCase();
                out.println(driver + ":");
                out.println("cd " + godir);
                out.close();
            } catch (Exception e) {
                log("ERROR: alias not found: " + godir);
            }
        }
    }

    public static void sql(String[] args) throws Exception {
        if (args.length < 3) {
            log("sql <type> <sql>");
            return;
        }

        String type = args[1];
        String sql = args[2];

        Properties p = getProperties(sqlDir + "driver.properties");
        String driver = p.getProperty(type + ".driver");
        String url = p.getProperty(type + ".url");
        String user = p.getProperty(type + ".user");
        String password = p.getProperty(type + ".password");
        log(format("execute \"{0}\" on \"{1}\".", sql, url));

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        if (sql.toLowerCase().startsWith("select")) {
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int n = md.getColumnCount();
            // data
            List<List<String>> lists = new ArrayList<List<String>>();
            List<String> header = new ArrayList<String>();
            for (int i = 1; i <= n; i++) {
                String cn = md.getColumnName(i);
                header.add(cn);
            }
            lists.add(header);
            List<String> types = new ArrayList<String>();
            for (int i = 1; i <= n; i++) {
                String cn = "" + md.getColumnType(i);
                types.add(cn);
            }
            lists.add(types);
            while (rs.next()) {
                List<String> row = new ArrayList<String>();
                for (int i = 1; i <= n; i++) {
                    Object o = rs.getObject(i);
                    String cv = formatColumnObject(o);
                    row.add(cv);
                }
                lists.add(row);
            }
            // size
            List<Integer> sizes = new ArrayList<Integer>();
            for (int i = 0; i < n; i++) {
                List<Integer> sl = new ArrayList<Integer>();
                for (List<String> l : lists) {
                    sl.add(getWordCount(l.get(i)));
                }
                Integer size = Collections.max(sl);
                sizes.add(size);
            }
            // print
            StringBuilder sb = new StringBuilder();
            for (List<String> l : lists) {
                for (int i = 0; i < n; i++) {
                    int size = sizes.get(i);
                    String v = l.get(i);
                    sb.append(formatColumn(v, size + 2, true));
                }
                sb.append(LINE_SEPARATOR);
            }
            String output = sb.toString();
            log(output);
        } else {
            stmt.executeUpdate(sql);
            log("execute successfully.");
        }
        stmt.close();
        conn.close();
    }

    public static void taj(String[] args) throws Exception {
        if (args.length < 2) {
            log("taj <alias>");
            return;
        }

        String alias = args[1];
        String path = toTARAlias(alias);

        String dir = batDir;
        String batFile;
        PrintWriter out;

        // tajtmp
        {
            batFile = dir + "tajtmp.bat";
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
            out.println("call ta " + alias + "1 \""
                    + fixPath(path).replace("D:\\jedi\\yoda\\export\\home", "D:\\gzhou\\sa\\OI_1") + "\"");
            out.println("call ta " + alias + "2 \""
                    + fixPath(path).replace("D:\\jedi\\yoda\\export\\home", "D:\\gzhou\\sa\\OI_2") + "\"");
            out.println("call ta " + alias + "3 \""
                    + fixPath(path).replace("D:\\jedi\\yoda\\export\\home", "D:\\gzhou\\sa\\OI_3") + "\"");
            out.close();
        }
    }

    public static void tam(String[] args) throws Exception {
        if (args.length < 2) {
            log("tam <alias>");
            return;
        }

        String alias = args[1];
        String path = toTARAlias(alias);

        String dir = batDir;
        String batFile;
        PrintWriter out;

        // tajtmp
        {
            batFile = dir + "tamtmp.bat";
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
            out.println("call ta m" + alias + " " + path.replace("D:\\jedi\\yoda", "D:\\jedi\\yoda_main"));
            out.close();
        }
    }

    public static void tas(String[] args) throws Exception {
        if (args.length < 2) {
            log("tas <alias>");
            return;
        }

        String alias = args[1];
        String path = toTARAlias(alias);

        String dir = batDir;
        String batFile;
        PrintWriter out;

        // tajtmp
        {
            batFile = dir + "tastmp.bat";
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
            out.println("call ta s" + alias + " " + path.replace("D:\\jedi\\yoda", "D:\\jedi\\yoda_sjb"));
            out.close();
        }
    }

    public static void tar(String[] args) throws Exception {
        if (args.length < 4) {
            log("tar <aliasFrom> <aliasTo>");
            return;
        }

        String op = args[1];
        String aliasFrom = args[2];
        String aliasTo = args[3];
        String path = toTARAlias(aliasFrom);

        String dir = batDir;
        String batFile;
        PrintWriter out;

        // tajtmp
        {
            batFile = dir + "tartmp.bat";
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
            if (op.equals("rename")) {
                out.println("call tdel " + aliasFrom);
            }
            out.println("call ta " + aliasTo + " \"" + fixPath(path) + "\"");
            out.close();
        }
    }

    public static void p2st(String[] args) throws Exception {
        List<String> lines = getLines(rn);
        List<String> list = new ArrayList<String>();
        for (String l : lines) {
            if (l.startsWith("Modified :")) {
                l = cut(l, "Modified :", null).trim();
                l = cut(l, "/yoda/", null).trim();
                l = l.replace("/", "\\");
                l = "M       " + l;
                list.add(l);
            }
        }
        setLines(desktopDir + "yodast.txt", list);
    }

    public static void palias(String[] args) throws Exception {
        if (args.length < 1) {
            log("palias <type> <args>");
            return;
        }
        String[] oArgs = args;
        args = PAOperations.debug(args);
        Params.log("begin", oArgs);
        Params.log("removed debug params", args);
        args = PAOperations.redirectPAOperations(args);
        Params.log("after redirect", args);
        String type = args[0];
        args = cutFirstArg(args);
        Params.log("cut type", args);
        args = OutputToFile.outputToFile(args, type);
        Params.log("output to file", args);
        args = Params.handleFirstParam(args);
        if (type.equals("print")) {
            PAOperations.paPrint(args);
        } else if (type.equals("printline")) {
            PAOperations.paPrintLine(args);
        } else if (type.equals("printfind")) {
            PAOperations.paPrintFind(args);
        } else if (type.equals("printreplace")) {
            PAOperations.paPrintReplace(args);
        } else if (type.equals("copy")) {
            PAOperations.paCopy(args);
        } else if (type.equals("remove")) {
            PAOperations.paRemove(args);
        } else if (type.equals("list")) {
            PAOperations.paList(args);
        } else if (type.equals("rename")) {
            PAOperations.paRename(args);
        } else if (type.equals("find")) {
            PAOperations.paFind(args);
        } else if (type.equals("open")) {
            PAOperations.paOpen(args);
        } else if (type.equals("replace")) {
            PAOperations.paReplace(args);
        }
    }

    public static void git(String[] args) throws Exception {
        if (args.length < 1) {
            log("git <type> <args>");
            return;
        }
        String[] oArgs = args;
        args = PAOperations.debug(args);
        Params.log("begin", oArgs);
        String type = args[0];
        args = cutFirstArg(args);
        Params.log("cut type", args);
        args = OutputToFile.outputToFile(args, "git_" + type);
        Params.log("output to file", args);
        if (type.equals("add")) {
            GitOperations.gitAdd(args);
        }
    }

    public static class GitOperations {

        public static void gitAdd(String[] args) throws Exception {
            Params params = Params.toParams("gitadd", args);
            args = params.args;

            String log = "D:\\alogs\\gst.log";
            List<String> lines = getLines(log);
            List<String> list = new ArrayList<String>();
            for (String line : lines) {
                if (line.startsWith("\t")) {
                    line = cutFirst(line, 1);
                    String m;
                    if (line.startsWith("deleted:    ")) {
                        line = cutFirst(line, 12);
                        list.add("call git rm " + line);
                        m = "    D      ";
                    } else if (line.startsWith("modified:   ")) {
                        // do nothing
                        line = cutFirst(line, 12);
                        m = "    M      ";
                    } else {
                        list.add("call git add " + line);
                        m = "    A      ";
                    }
                    log(m + line);
                }
            }
            setLines(batDir + "agitaddtmp.bat", list);
        }
    }

    public static void custom(String[] args) throws Exception {
        if (args.length < 1) {
            log("custom <type> <args>");
            return;
        }
        String[] oArgs = args;
        args = PAOperations.debug(args);
        Params.log("begin", oArgs);
        String type = args[0];
        args = cutFirstArg(args);
        Params.log("cut type", args);
        args = OutputToFile.outputToFile(args, "custom_" + type);
        Params.log("output to file", args);
        if (type.equals("formatjson")) {
            CustomOperations.customFormatJSON(args);
        } else if (type.equals("exist")) {
            CustomOperations.customExist(args);
        } else if (type.equals("diff")) {
            CustomOperations.customDiff(args);
        } else if (type.equals("edit")) {
            CustomOperations.customEdit(args);
        }
    }

    public static class CustomOperations {

        public static void customFormatJSON(String[] args) throws Exception {
            Params params = Params.toParams("custom_format_json", args);
            args = params.args;

            String p = toTARAlias(args[0]);

            boolean tol = false;
            String last = getLastArg(args);
            if (last.equals("tol"))
                tol = true;

            if (!tol) {
                List<String> lines = getLines(p);
                List<String> list = new ArrayList<String>();
                for (String s : lines) {
                    list.add(JSONUtil.format(s));
                }
                setLines(p, list);
            } else {
                List<String> lines = getLinesNoEx(p);
                StringBuilder sb = new StringBuilder();
                for (String line : lines) {
                    sb.append(line.trim());
                }
                setLines(p, toList(sb.toString()));
            }
        }

        public static void customExist(String[] args) throws Exception {
            Params params = Params.toParams("custom_exist", args);
            args = params.args;

            String p = toTARAlias(args[0]);

            if (exists(p)) {
                setLines(batDir + "aenvtmp.bat", toList("call set AEXIST=true"));
            } else {
                setLines(batDir + "aenvtmp.bat", toList("call set AEXIST="));
            }
        }

        public static void customDiff(String[] args) throws Exception {
            Params params = Params.toParams("custom_diff", args);
            args = params.args;

            if (args.length < 2) {
                log("adf <file1> <file2>");
                return;
            }

            List<String> list = toAdfLines(args[0], args[1]);
            setLines(batDir + "adifftmp.bat", list);
        }

        public static void customEdit(String[] args) throws Exception {
            Params params = Params.toParams("custom_edit", args);
            args = params.args;

            if (args.length < 1) {
                log("e <file>");
                return;
            }

            String p = toTARAlias(args[0]);
            mkdirs(getParent(p));
            setLines(batDir + "aedittmp.bat", toList(toEditLine(p)));
        }

        public static List<String> toAdfLines(String from, String to) throws Exception {
            String p1 = toTARAlias(from);
            String n1 = getFileName(p1);
            List<String> list = new ArrayList<String>();
            list.add("@echo off");
            list.add(format("call set ADFING=true"));
            list.add(format("call adfdo \"{0}\" \"{1}\" \"{2}\" > D:\\alogs\\adfdo.log", from, to, n1));
            if (outputToFile_) {
                list.add(format("call al alogs\\svn.diff ap nl -lt4 > D:\\alogs\\adfdiff.log"));
                list.add(toEditLine("D:\\alogs\\svn.diff"));
            } else {
                list.add(format("call al alogs\\svn.diff ap nl -lt4"));
            }
            list.add(format("call set ADFING="));
            log("diff \"{0}\" with \"{1}\"", from, to);
            return list;
        }

    }

    private static String fixPath(String path) {
        if (path.contains("\""))
            path = path.replace("\"", "\"\"");
        return path;
    }

    public static String toTARAlias(String p) throws Exception {
        p = unwrapTARAlias(p);
        if (isAbsolutePath(p))
            return p;
        boolean containsLeft = p.contains("/");
        boolean containsRight = p.contains("\\");
        List<String> list = getLines2(TYPEANDRUN_CONFIG);
        List<TARAliasMatchNodeItem> items = new ArrayList<TARAliasMatchNodeItem>();
        for (String tarLine : list) {
            String tarAlias = cut(tarLine, null, "|");
            String tarPath = cut(tarLine, "|", null);
            if (tarPath.contains("|")) {
                tarPath = cutBack(tarPath, null, "|");
                if (!isTextFile(tarPath)) {
                    continue;
                }
            }
            if (tarPath.contains("://"))
                continue;

            tarPath = fixTarPath(tarPath);
            if (containsLeft) {
                String pFirst = cut(p, null, "/");
                addItemIfNecessary(pFirst, items, tarAlias, tarPath);
            } else if (containsRight) {
                String pFirst = cut(p, null, "\\");
                addItemIfNecessary(pFirst, items, tarAlias, tarPath);
            } else {
                addItemIfNecessary(p, items, tarAlias, tarPath);
            }
        }
        if (!items.isEmpty()) {
            Collections.sort(items);
            TARAliasMatchNodeItem matchedItem = items.get(0);
            matchedItem_ = matchedItem;
            if (containsLeft) {
                String sub = cut(p, "/", null);
                sub = sub.replace("/", FILE_SEPARATOR);
                return toTARPath(matchedItem.tarPath, sub);
            } else if (containsRight) {
                String sub = cut(p, "\\", null);
                return toTARPath(matchedItem.tarPath, sub);
            } else {
                return matchedItem.tarPath;
            }
        }
        return p;
    }

    private static void addItemIfNecessary(String p, List<TARAliasMatchNodeItem> items, String tarAlias, String tarPath) {
        if (tarAlias.equals(p)) {
            TARAliasMatchNodeItem item = new TARAliasMatchNodeItem();
            item.i = 1;
            item.tarAlias = tarAlias;
            item.tarPath = tarPath;
            items.add(item);
        }
        if (tarAlias.startsWith(p)) {
            TARAliasMatchNodeItem item = new TARAliasMatchNodeItem();
            item.i = 4;
            item.tarAlias = tarAlias;
            item.tarPath = tarPath;
            items.add(item);
        }
        if (tarAlias.endsWith(p)) {
            TARAliasMatchNodeItem item = new TARAliasMatchNodeItem();
            item.i = 6;
            item.tarAlias = tarAlias;
            item.tarPath = tarPath;
            items.add(item);
        }
        if (tarAlias.contains(p)) {
            TARAliasMatchNodeItem item = new TARAliasMatchNodeItem();
            item.i = 10;
            item.tarAlias = tarAlias;
            item.tarPath = tarPath;
            items.add(item);
        }
    }

    private static String fixTarPath(String tarPath) {
        tarPath = removeLast(tarPath, FILE_SEPARATOR);
        if (tarPath.endsWith(":"))
            tarPath = addLast(tarPath, FILE_SEPARATOR);
        return tarPath;
    }

    public static String toTARPath(String tarPath, String sub) {
        String sp = FILE_SEPARATOR;
        List<String> list = splitToList(sub, sp + sp);
        List<String> list2 = new ArrayList<String>();
        String path = tarPath;
        for (int i = 0; i < list.size(); i++) {
            String node = list.get(i);
            if (i < list.size() - 1) {
                // dir
                node = toTARPathMatchNode(path, node, true);
            } else {
                // dir or file
                node = toTARPathMatchNode(path, node, false);
            }
            list2.add(node);
            path = addLast(path, sp) + node;
        }
        return path;
    }

    private static String toTARPathMatchNode(String path, String node, boolean onlyDir) {
        List<TARPathMatchNodeItem> list = new ArrayList<TARPathMatchNodeItem>();
        File pathFile = new File(path);
        if (pathFile.exists()) {
            File[] files = pathFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (onlyDir) {
                        if (file.isDirectory()) {
                            String n = file.getName();
                            if (containsIgnoreCase(n, node)) {
                                TARPathMatchNodeItem item = new TARPathMatchNodeItem();
                                if (n.equals(node)) {
                                    item.i = 1;
                                } else if (n.contains(node)) {
                                    item.i = 2;
                                } else {
                                    item.i = 3;
                                }
                                item.file = file;
                                item.n = n;
                                list.add(item);
                            }
                        }
                    } else {
                        String n = file.getName();
                        if (containsIgnoreCase(n, node)) {
                            TARPathMatchNodeItem item = new TARPathMatchNodeItem();
                            if (n.equals(node)) {
                                item.i = 1;
                            } else if (file.isDirectory()) {
                                if (n.contains(node))
                                    item.i = 2;
                                else
                                    item.i = 3;
                            } else {
                                item.i = 4;
                            }
                            item.file = file;
                            item.n = n;
                            list.add(item);
                        }
                    }
                }
            }
        }
        if (!list.isEmpty()) {
            Collections.sort(list);
            return list.get(0).n;
        }
        return node;
    }

    public static class TARAliasMatchNodeItem implements Comparable<TARAliasMatchNodeItem> {
        public int i;
        public String tarAlias;
        public String tarPath;

        @Override
        public int compareTo(TARAliasMatchNodeItem o) {
            Integer i1 = i;
            Integer i2 = o.i;
            return i1.compareTo(i2);
        }
    }

    public static class TARPathMatchNodeItem implements Comparable<TARPathMatchNodeItem> {
        public int i;
        public File file;
        public String n;

        @Override
        public int compareTo(TARPathMatchNodeItem o) {
            Integer i1 = i;
            Integer i2 = o.i;
            return i1.compareTo(i2);
        }
    }

    private static String unwrapTARAlias(String p) throws Exception {
        if (p.startsWith("'") && p.endsWith("'"))
            p = cut(p, 1, 1);
        if (p.equals(".") || p.equals("..") || p.startsWith("./") || p.startsWith("../"))
            return toFilePath(p);
        return p;
    }

    private static String getModuleDir(String file) {
        String p = file;
        p = getParent(p);
        p = getParent(p);
        p = getParent(p);
        return p;
    }

    private static void printNode(Node node, boolean last, List<String> lines) throws Exception {
        String line;
        String name = DOMUtil.getAttribute(node, "@name");
        String label = DOMUtil.getAttribute(node, "@label");
        String description = DOMUtil.getAttribute(node, "@description");
        String enums = DOMUtil.getAttribute(node, "@enums");
        String comma = (last ? "" : ",");
        line = format("        \"{0}.label\": \"{1}\",", name, label);
        lines.add(line);
        log(line);
        if (enums != null) {
            String[] items = enums.split(",");
            for (String item : items) {
                String itemKey = item.substring(0, item.indexOf("="));
                String itemLabel = item.substring(item.indexOf("=") + 1, item.length());
                line = format("        \"{0}.{1}.label\": \"{2}\",", name, itemKey, itemLabel);
                lines.add(line);
                log(line);
            }
        }
        line = format("        \"{0}.desc\": \"{1}\"" + comma, name, description);
        lines.add(line);
        log(line);
    }

    private static void printCNNode(Node node, boolean last, List<String> lines) throws Exception {
        String line;
        String name = DOMUtil.getAttribute(node, "@name");
        String label = DOMUtil.getAttribute(node, "@label");
        String description = DOMUtil.getAttribute(node, "@description");
        String enums = DOMUtil.getAttribute(node, "@enums");
        String comma = (last ? "" : ",");
        line = format("          \"{0}.label\": \"{1}麒麟\",", name, label);
        lines.add(line);
        log(line);
        if (enums != null) {
            String[] items = enums.split(",");
            for (String item : items) {
                String itemKey = item.substring(0, item.indexOf("="));
                String itemLabel = item.substring(item.indexOf("=") + 1, item.length());
                line = format("          \"{0}.{1}.label\": \"{2}麒麟\",", name, itemKey, itemLabel);
                lines.add(line);
                log(line);
            }
        }
        line = format("          \"{0}.desc\": \"{1}麒麟\"" + comma, name, description);
        lines.add(line);
        log(line);
    }

    public static List<String> getLinesNoEx(String file) {
        try {
            return getLines(file);
        } catch (Exception e) {
        }
        return new ArrayList<String>();
    }

    public static void cleanOld() throws Exception {
        cleanFolder(desktopDir + "old");
    }

    public static void cleanTester() throws Exception {
        String tester = desktopDir + "Tester";
        File file = new File(tester);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    cleanFolder(file2.getAbsolutePath());
                } else {
                    file2.delete();
                }
            }
        }
    }

    public static void cleanFolder(String file) throws Exception {
        deleteFolder(file, file, null, true);
    }

    public static void deleteFolder(String root, String from, String suffix, boolean keepRoot) throws Exception {
        File fromFile = new File(from);
        if (!fromFile.exists())
            return;
        File[] files = fromFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                deleteFolder(root, file.getAbsolutePath(), suffix, keepRoot);
            } else {
                if (suffix != null) {
                    if (file.getName().endsWith(suffix)) {
                        log("delete: " + file.getAbsolutePath());
                        file.delete();
                    }
                } else {
                    log("delete: " + file.getAbsolutePath());
                    file.delete();
                }
            }
        }
        if (suffix == null) {
            if (keepRoot && root.equals(from)) {
                // keep root
            } else {
                log("delete: " + fromFile.getAbsolutePath());
                fromFile.delete();
            }

        }
    }

    public static void copyFolder(String from, String to) throws Exception {
        copyFolder(from, to, null, null, true);
    }

    public static void copyFolder(String from, String to, Set<String> includeFiles, Set<String> excludeFolders)
            throws Exception {
        copyFolder(from, to, includeFiles, excludeFolders, true);
    }

    public static void copyFolder(String from, String to, Set<String> includeFiles, Set<String> excludeFolders,
            boolean recursive) throws Exception {
        File toFile = new File(to);
        toFile.mkdirs();

        File fromFile = new File(from);
        File[] files = fromFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (!recursive)
                    continue;

                if (excludeFolders != null && excludeFolders.contains(file.getName())) {
                    continue;
                }
                toFile = new File(to + "\\" + file.getName());
                toFile.mkdirs();
                // log("create folder: " + toFile.getAbsolutePath());
                copyFolder(file.getAbsolutePath(), to + "\\" + file.getName(), includeFiles, excludeFolders);
            } else {
                if (includeFiles != null && !includeFiles.contains(file.getName())) {
                    continue;
                } else {
                    copyFile(file.getAbsolutePath(), to + "\\" + file.getName());
                }
            }
        }
    }

    public static String getParent(String s) {
        return s.substring(0, s.lastIndexOf("\\"));
    }

    public static String toLeftSlash(String s) throws Exception {
        return s.replaceAll("\\\\", "/");
    }

    public static void generateCreateUserSql(String user) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(sqlDir + "create_user.sql")));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(desktopDir + "user.sql", true)));
        String line;
        while ((line = in.readLine()) != null) {
            out.println(line.replaceAll("zgf1_migration", user));
        }
        out.println();
        out.close();
        in.close();
        log("Generate sql to create user: " + user);
    }

    public static void removeDup(String filePath) throws Exception {
        List<String> list = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        String line;
        while ((line = in.readLine()) != null) {
            if (!list.contains(line))
                list.add(line);
        }
        in.close();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath, false)));
        for (String string : list) {
            out.println(string);
        }
        out.close();
    }

    public static void sort(String filePath) throws Exception {
        List<String> list = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        String line;
        while ((line = in.readLine()) != null) {
            if (line.length() > 0)
                list.add(line);
        }
        in.close();
        java.util.Collections.sort(list);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath, false)));
        for (String string : list) {
            out.println(string);
        }
        out.close();
    }

    public static List<String> getDesktopLines(String fileName) throws Exception {
        return getLines(desktopDir + fileName);
    }

    public static void setDesktopLines(String fileName, List<String> lines) throws Exception {
        setLines(desktopDir + fileName, lines);
    }

    public static void appendLines(String filePath, List<String> lines) throws Exception {
        mkdirs(getParent(filePath));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath, true)));
        for (String line : lines) {
            out.println(line);
        }
        out.close();
    }

    public static void appendDesktopLines(String fileName, List<String> lines) throws Exception {
        appendLines(desktopDir + fileName, lines);
    }

    public static Properties getProperties(String filePath) throws Exception {
        return getProperties(new FileInputStream(filePath));
    }

    public static Properties getProperties(InputStream is) throws Exception {
        Properties props = new Properties();
        props.load(is);
        return props;
    }

    public static Properties getDesktopProperties(String fileName) throws Exception {
        return getProperties(desktopDir + fileName);
    }

    public static List<File> list(File folder) {
        File[] files = folder.listFiles();
        return Arrays.asList(files);
    }

    public static List<String> getFileLines(String folder) {
        return getFileLines(folder, "{FileName}");
    }

    public static List<String> getFileLines(String folder, String template) {
        List<File> files = listJarFiles(new File(folder));
        List<String> list = new ArrayList<String>();
        for (File file : files) {
            String fileName = file.getName();
            list.add(template.replaceAll("\\{FileName\\}", fileName));
        }
        Collections.sort(list);
        return list;
    }

    public static boolean isFilePath(String path) {
        if (path != null) {
            if (path.matches("[a-zA-Z]:(\\\\\\w+)*(\\\\[\\w-\\.]*\\.\\w+)"))
                return true;
            if (path.matches("\\w+(\\\\\\w+)*(\\\\[\\w- \\.]*\\.\\w+)"))
                return true;
            if (path.matches("(/\\w+)+(/[\\w-\\.]*\\.\\w+)"))
                return true;
            if (path.matches("/([\\w-\\.]*\\.\\w+)"))
                return true;
        }
        return false;
    }

    public static final int BUFFER_SIZE = 8192; // 8Kb

    public static void extractZip(String filePath, String folderPath) {
        try {
            if (!folderPath.endsWith(FILE_SEPARATOR))
                folderPath += FILE_SEPARATOR;
            ZipFile zipFile = new ZipFile(filePath);
            Enumeration emu = zipFile.entries();

            while (emu.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) emu.nextElement();
                if (entry.isDirectory()) {
                    new File(folderPath + entry.getName()).mkdirs();
                    continue;
                }
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                File file = new File(folderPath + entry.getName());
                File parent = file.getParentFile();
                if (parent != null && (!parent.exists())) {
                    parent.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER_SIZE);

                int count;
                byte data[] = new byte[BUFFER_SIZE];
                while ((count = bis.read(data, 0, BUFFER_SIZE)) != -1) {
                    bos.write(data, 0, count);
                }
                bos.flush();
                bos.close();
                bis.close();
            }
            zipFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class PAOperations {

        public static void paPrint(String[] args) throws Exception {
            Params params = Params.toParams("ap", args);
            args = params.args;

            String fromdir = toTARAlias(args[0]);
            args = cutFirstArg(args);

            if (isFile(fromdir)) {
                onlyOneFile(params);
                printFiles(getParent(fromdir), getFileName(fromdir) + "*", "one", params);
            } else {
                String filefrom = "*";
                if (args.length > 0) {
                    filefrom = getFirstArg(args);
                    args = cutFirstArg(args);
                }
                String type = "all";
                if (args.length > 0)
                    type = args[0];
                filefrom = fixFileFrom(filefrom, params);
                // db operations
                if (DBOperations.isDB(fromdir)) {
                    DBOperations.printTables(fromdir, filefrom, params);
                    return;
                }
                // file operations
                printFiles(fromdir, filefrom, type, params);
            }
        }

        public static void paPrintLine(String[] args) throws Exception {
            String m = connectLines(args, " ");
            m = m.replace("'", "\"");
            log(m);
        }

        public static void paPrintFind(String[] args) throws Exception {
            String key = args[0];
            String alias = args[1];
            String resolved = toTARAlias(alias);
            if (!alias.equals(resolved))
                log(format("find \"{0}\" in \"{1}({2})\".", key, alias, resolved));
            else
                log(format("find \"{0}\" in \"{1}\".", key, resolved));
        }

        public static void paPrintReplace(String[] args) throws Exception {
            String from = args[0];
            String to = args[1];
            String alias = args[2];
            String resolved = toTARAlias(alias);
            if (!alias.equals(resolved))
                log(format("replace from \"{0}\" to \"{1}\" in \"{2}({3})\".", from, to, alias, resolved));
            else
                log(format("replace from \"{0}\" to \"{1}\" in \"{1}\".", from, to, resolved));
        }

        public static void paCopy(String[] args) throws Exception {
            Params params = Params.toParams("acp", args);
            args = params.args;

            String from = null, fromfile = null, to = null, tofile = null;
            if (args.length < 2) {
                log(tab(2) + "need specify from and to");
                return;
            } else if (args.length == 2) {
                from = toTARAlias(args[0]);
                if (isFile(from)) {
                    fromfile = getFileName(from);
                    from = getParent(from);
                    onlyOneFile(params);
                } else {
                    fromfile = "*";
                }
                to = toTARAlias(args[1]);
                if (isFile(to)) {
                    tofile = getFileName(to);
                    to = getParent(to);
                } else {
                    tofile = "*";
                }
            } else if (args.length == 3) {
                from = toTARAlias(args[0]);
                fromfile = args[1];
                to = toTARAlias(args[2]);
                if (isFile(to)) {
                    tofile = getFileName(to);
                    to = getParent(to);
                } else {
                    tofile = "*";
                }
            } else if (args.length == 4) {
                from = toTARAlias(args[0]);
                fromfile = args[1];
                to = toTARAlias(args[2]);
                tofile = args[3];
            }

            if (from == null || fromfile == null || to == null || tofile == null) {
                log(tab(2) + "need specify from and to");
                return;
            }

            fromfile = fixFileFrom(fromfile, params);
            copyFiles(from, fromfile, to, tofile, params);
        }

        public static void paRemove(String[] args) throws Exception {
            Params params = Params.toParams("arm", args);
            args = params.args;

            String fromdir = toTARAlias(args[0]);
            String filefrom = "*";
            if (args.length > 1) {
                filefrom = args[1];
            }
            filefrom = fixFileFrom(filefrom, params);
            if (isFile(fromdir)) {
                onlyOneFile(params);
                deleteFiles(getParent(fromdir), getFileName(fromdir) + "*", params);
            } else {
                deleteFiles(fromdir, filefrom, params);
            }
        }

        public static void paList(String[] args) throws Exception {
            Params params = Params.toParams("al", args);
            args = params.args;

            String fromdir = toTARAlias(args[0]);
            String filefrom = "*";
            if (args.length > 1) {
                filefrom = args[1];
            }
            filefrom = fixFileFrom(filefrom, params);
            // db operations
            if (DBOperations.isDB(fromdir)) {
                DBOperations.listTables(fromdir, filefrom, params);
                return;
            }
            // file operations
            if (isFile(fromdir)) {
                onlyOneFile(params);
                listFiles(getParent(fromdir), getFileName(fromdir) + "*", params);
            } else {
                listFiles(fromdir, filefrom, params);
            }
        }

        public static void paRename(String[] args) throws Exception {
            Params params = Params.toParams("arn", args);
            args = params.args;

            String fromdir = toTARAlias(args[0]);
            args = cutFirstArg(args);
            String filefrom = "*";
            if (args.length > 2) {
                filefrom = getFirstArg(args);
                args = cutFirstArg(args);
            }
            String from = args[0];
            String to = args[1];
            filefrom = fixFileFrom(filefrom, params);
            if (isFile(fromdir)) {
                onlyOneFile(params);
                renameFiles(getParent(fromdir), getFileName(fromdir) + "*", from, to, params);
            } else {
                renameFiles(fromdir, filefrom, from, to, params);
            }
        }

        public static void paFind(String[] args) throws Exception {
            Params params = Params.toParams("af", args);
            args = params.args;

            String fromdir = toTARAlias(args[0]);
            String filefrom, from;
            if (args.length == 1) {
                log("need specify the search key");
                return;
            } else if (args.length == 2) {
                filefrom = "*";
                from = args[1];
            } else {
                filefrom = args[1];
                from = args[2];
            }
            filefrom = fixFileFrom(filefrom, params);
            // db operations
            if (DBOperations.isDB(fromdir)) {
                DBOperations.findInTables(fromdir, filefrom, from, params);
                return;
            }
            // file operations
            if (isFile(fromdir)) {
                onlyOneFile(params);
                findInFiles(getParent(fromdir), getFileName(fromdir) + "*", from, params);
            } else {
                findInFiles(fromdir, filefrom, from, params);
            }
        }

        public static void paOpen(String[] args) throws Exception {
            Params params = Params.toParams("ao", args);
            args = params.args;

            String fromdir = toTARAlias(args[0]);
            String filefrom = "*";
            if (args.length > 1) {
                filefrom = args[1];
            }
            filefrom = fixFileFrom(filefrom, params);
            if (isFile(fromdir)) {
                onlyOneFile(params);
                openFiles(getParent(fromdir), getFileName(fromdir) + "*", params);
            } else {
                openFiles(fromdir, filefrom, params);
            }
        }

        public static void paReplace(String[] args) throws Exception {
            Params params = Params.toParams("ar", args);
            args = params.args;

            if (params.newFileName != null) {
                // replace the entire line with new file name patterns
                args = appendArg(args, HANDLE_LINE);
                args = appendArg(args, HANDLE_LINE);
            }

            String fromdir = toTARAlias(args[0]);
            String filefrom = null, from = null, to = null;
            if (args.length < 3) {
                if (params.newFileName == null) {
                    log("need specify replace from and to");
                    return;
                }
            } else if (args.length == 3) {
                filefrom = "*";
                from = args[1];
                to = args[2];
            } else if (args.length == 4) {
                String pattern = args[1];
                if (isFilePattern(pattern)) {
                    filefrom = args[1];
                    from = args[2];
                    to = args[3];
                } else {
                    filefrom = "*";
                    from = args[2] + "/" + args[1];
                    to = args[3];
                }
            } else {
                filefrom = args[1];
                from = args[3] + "/" + args[2];
                to = args[4];
            }
            filefrom = fixFileFrom(filefrom, params);
            if (isFile(fromdir)) {
                onlyOneFile(params);
                replaceFiles(getParent(fromdir), getFileName(fromdir) + "*", from, to, params);
            } else {
                params.recursive = true;
                replaceFiles(fromdir, filefrom, from, to, params);
            }
        }

        protected static void copyOneFile(String from, String to, String filefrom, String fileto) throws Exception {
            copyFile(from + FILE_SEPARATOR + filefrom, to + FILE_SEPARATOR + fileto, false);
            log("copy from: " + formatFrom(from));
            log("     to:   " + to);
            log("           " + filefrom + " -> " + fileto);
        }

        protected static void copyFiles(String from, String fromfile, String to, String tofile, Params params)
                throws Exception {
            if (!params.move)
                log("copy from: " + formatFrom(from));
            else
                log("move from: " + formatFrom(from));
            log("     to:   " + to);
            List<File> files = toCopyFromFiles(getFromFiles(from, fromfile, params));
            if (!files.isEmpty()) {
                List<String> dirs = new ArrayList<String>();
                List<File> tofiles = getToFiles(to, tofile, params);
                if (!tofiles.isEmpty()) {
                    File todirFile = tofiles.get(0);
                    String dir = todirFile.isFile() ? todirFile.getParent() : todirFile.getAbsolutePath();
                    for (File file : files) {
                        if (file.isFile()) {
                            String p = file.getAbsolutePath();
                            String relativePath = toRelativePath(from, p);
                            String topath;
                            // keep dir
                            if (params.keepDir) {
                                topath = dir + FILE_SEPARATOR + relativePath.replace("/", FILE_SEPARATOR);
                            } else {
                                topath = dir + FILE_SEPARATOR + getFileName(p);
                            }
                            // rename file
                            String newFileName = newFileNameInCopy(getFileName(topath), params, true);
                            topath = getParent(topath) + FILE_SEPARATOR + newFileName;
                            if (needOverwrite(p, topath, params)) {
                                long s = System.currentTimeMillis();
                                copyFile(p, topath, false);
                                if (params.move)
                                    deleteFileWithFolders(p);
                                long e = System.currentTimeMillis();
                                long cost = (e - s) / 1000;
                                String costStr = cost > 5 ? " [" + cost + "s]" : "";
                                log("           " + relativePath);
                                log("        -> " + topath + costStr);
                                addWithoutDup(dirs, topath);
                            }
                        }
                    }
                    OpenDirResult.openDirs(params, dirs, from);
                    OutputSummaryResult.outputSummary(params, dirs);
                } else {
                    log(tab(2) + "to files not found: " + tofile);
                }
            } else {
                log(tab(2) + "from files not found: " + fromfile);
            }
        }

        protected static void deleteFiles(String from, String filefrom, Params params) throws Exception {
            log("delete from: " + formatFrom(from));
            FilenameFilter filter = Filters.getFilters(filefrom, params);
            List<File> files = Util.listFiles(new File(from), params.recursive, filter, params);
            if (!files.isEmpty()) {
                List<String> dirs = new ArrayList<String>();
                int filesSize = 0;
                for (File file : files) {
                    if (file.isFile())
                        filesSize++;
                    String p = file.getAbsolutePath();
                    if (params.keepDir)
                        deleteFile(p);
                    else
                        deleteFileWithFolders(p);
                    deleteFolderIfNecessary(params, file);
                    log(tab(2) + toRelativePath(from, p));
                    addWithoutDup(dirs, p);
                }
                OpenDirResult.openDirs(params, dirs, from);
                log(tab(2) + format("dirs: {0}, files: {1}", files.size() - filesSize, filesSize));
            } else {
                log(tab(2) + "no matched files: " + filefrom);
            }
        }

        protected static void printFiles(String from, String filefrom, String type, Params params) throws Exception {
            boolean one = type.equals("one");
            FilenameFilter filter = Filters.getFilters(filefrom, params);
            if (!one)
                log("print from: " + formatFrom(from));
            List<File> files = Util.listFiles(new File(from), params.recursive, filter, params);
            if (!files.isEmpty()) {
                for (File file : files) {
                    List<String> dirs = new ArrayList<String>();
                    if (isTextFile(file)) {
                        String p = file.getAbsolutePath();
                        if (!one)
                            log(tab(2) + toRelativePath(from, p));
                        List<String> lines = getLines(p, params.getEncoding());
                        for (int i = 0; i < lines.size(); i++) {
                            String line = lines.get(i);
                            if (params.expandLines != null) {
                                if (!ExpandLines.matchesLineNumber(params.expandLines, i + 1)) {
                                    continue;
                                }
                            }
                            Line l = new Line(i + 1, line, params);
                            if (!one)
                                l.print(6, 7, params.noLineNumber);
                            else
                                l.print(0, 7, params.noLineNumber);
                        }
                        addWithoutDup(dirs, p);
                    }
                    OpenDirResult.openDirs(params, dirs, from);
                }
            } else {
                log(tab(2) + "no matched files: " + filefrom);
            }
        }

        protected static void listFiles(String from, String filefrom, Params params) throws Exception {
            FilenameFilter filter = Filters.getFilters(filefrom, params);
            if (params.fileTimestamp != null)
                log(format("list from: {0} ({1})", formatFrom(from), params.fileTimestamp.toString2()));
            else
                log("list from: " + formatFrom(from));
            List<File> files = Util.listFiles(new File(from), params.recursive, filter, params);
            List<String> dirs = new ArrayList<String>();
            if (!files.isEmpty()) {
                int filesSize = 0;
                int nameIndent = getNameIndent(from, files);
                for (File file : files) {
                    if (file.isHidden())
                        continue;
                    if (file.isFile())
                        filesSize++;
                    String p = file.getAbsolutePath();
                    String relativePath = toRelativePath(from, p);
                    if (params.useDot) {
                        relativePath = relativePath.replace("/", ".");
                        if (relativePath.endsWith(".java"))
                            relativePath = cutLast(relativePath, 5);
                        if (relativePath.endsWith(".class"))
                            relativePath = cutLast(relativePath, 6);
                    }
                    log(tab(2) + listFileDetail(file, relativePath, nameIndent, params));
                    renameFileInList(file, params, relativePath);
                    addWithoutDup(dirs, p);
                }
                log(tab(2) + format("dirs: {0}, files: {1}", files.size() - filesSize, filesSize));
            } else {
                log(tab(2) + "no matched files: " + filefrom);
            }
            OpenDirResult.openDirs(params, dirs, from);
            ZipOperationsResult.zipOperations(params, dirs);
            GoDirResult.go(params, dirs, from);
            DeleteSameResult.deleteSame(params, dirs);
        }

        protected static void renameFiles(String dir, String filefrom, String from, String to, Params params)
                throws Exception {
            log("rename from: " + formatFrom(dir));
            FilenameFilter filter = Filters.getFilters(filefrom, params);
            List<File> files = Util.listFiles(new File(dir), params.recursive, filter, params);
            if (!files.isEmpty()) {
                List<String> dirs = new ArrayList<String>();
                for (File file : files) {
                    String p = file.getAbsolutePath();
                    String p2 = renameFile(p, from, to);
                    String n1 = toRelativePath(dir, p);
                    String n2 = toRelativePath(dir, p2);
                    log(tab(2) + n1 + " -> " + n2);
                    addWithoutDup(dirs, p2);
                }
                OpenDirResult.openDirs(params, dirs, from);
            } else {
                log(tab(2) + "no matched files: " + filefrom);
            }
        }

        protected static void findInFiles(String dir, String filefrom, String from, Params params) throws Exception {
            log("find from: " + formatFrom(dir));
            FilenameFilter filter = Filters.getFilters(filefrom, params);
            List<File> files = Util.listFiles(new File(dir), params.recursive, filter, params);
            boolean hasResult = false;
            if (!files.isEmpty()) {
                for (File file : files) {
                    List<String> dirs = new ArrayList<String>();
                    if (isTextFile(file)) {
                        String p = file.getAbsolutePath();
                        List<Line> foundLines = findInFile(p, from, params);
                        if (!foundLines.isEmpty()) {
                            String n1 = toRelativePath(dir, p);
                            log(tab(2) + format("found \"{0}\" places in \"{1}\":", foundLines.size(), n1));
                            log();
                            for (Line line : foundLines) {
                                line.print(6, 7, params.noLineNumber);
                            }
                            log();
                            hasResult = true;
                            addWithoutDup(dirs, p);
                            OperateLinesUtil.operateLines(p, foundLines, params);
                        }
                    }
                    OpenDirResult.openDirs(params, dirs, from);
                }
            }
            if (!hasResult) {
                log(tab(6) + "not found");
            }
        }

        protected static void openFiles(String from, String filefrom, Params params) throws Exception {
            FilenameFilter filter = Filters.getFilters(filefrom, params);
            log("open files from: " + formatFrom(from));
            List<File> files = Util.listFiles(new File(from), params.recursive, filter, params);
            List<String> lines = new ArrayList<String>();
            if (!files.isEmpty()) {
                for (File file : files) {
                    List<String> dirs = new ArrayList<String>();
                    if (isTextFile(file)) {
                        String p = file.getAbsolutePath();
                        log(tab(2) + toRelativePath(from, p));
                        lines.add(toEditLine(p));
                        addWithoutDup(dirs, p);
                    }
                    OpenDirResult.openDirs(params, dirs, from);
                }
            } else {
                log(tab(2) + "no matched files: " + filefrom);
            }
            setLines(batDir + "aopentmp.bat", lines);
        }

        protected static void replaceFiles(String dir, String filefrom, String from, String to, Params params)
                throws Exception {
            log(format("replace from \"{0}\" to \"{1}\" in dir: {2}", from, to, formatFrom(dir)));
            Filters filter = Filters.getFilters(filefrom, params);
            List<File> files = Util.listFiles(new File(dir), params.recursive, filter, params);
            boolean replaced = false;
            if (!files.isEmpty()) {
                Filters fromFilter = Filters.getFilters(from, params);
                from = fromFilter.getFirst();
                for (File file : files) {
                    List<String> dirs = new ArrayList<String>();
                    if (isTextFile(file)) {
                        String p = file.getAbsolutePath();
                        ReplaceResult r = replaceFile(p, fromFilter, from, to, params);
                        String p2 = r.filePath;
                        if (r.changed) {
                            String n1 = toRelativePath(dir, p);
                            String n2 = toRelativePath(dir, p2);
                            if (n1.equals(n2))
                                log(tab(2) + n1);
                            else
                                log(tab(2) + format("{0} -> {1}", n1, n2));
                            r.logLines();
                            replaced = true;
                            addWithoutDup(dirs, p);
                        }
                    }
                    OpenDirResult.openDirs(params, dirs, from);
                }
            }
            if (!replaced) {
                log(tab(2) + "no matched files: " + filefrom);
            }
        }

        private static String[] debug(String[] args) {
            args = sortDebugInArgs(args);
            int n;
            do {
                n = args.length;

                // -v
                args = viewDebugLoggings(args);
                // -va
                args = viewDebugAllLoggings(args);
                // -d
                args = cutJBossDebug(args);
                // -lt
                args = cutLogTab(args);

            } while (args.length < n);
            return args;
        }

        private static String[] sortDebugInArgs(String[] args) {
            List<String> list = arrayToList(args);
            Collections.sort(list, new ParamsSorterDebug());
            args = listToArray(list);
            return args;
        }

        private static String[] viewDebugLoggings(String[] args) {
            String last = getLastArg(args);
            if (last.equals("-v")) {
                debug_ = true;
                args = cutLastArg(args);
            }
            return args;
        }

        private static String[] viewDebugAllLoggings(String[] args) {
            String last = getLastArg(args);
            if (last.equals("-va")) {
                debug_ = true;
                debug2_ = true;
                args = cutLastArg(args);
            }
            return args;
        }

        private static String[] cutJBossDebug(String[] args) {
            String last = getLastArg(args);
            if (last.equals("-d")) {
                args = cutLastArg(args);
            }
            return args;
        }

        private static String[] cutLogTab(String[] args) {
            String last = getLastArg(args);
            if (last.matches("-lt\\d*")) {
                last = cutFirst(last, 3);
                if (last.length() == 0)
                    logTab_ = tab(2);
                else
                    logTab_ = tab(toInt(last));
                args = cutLastArg(args);
            }
            return args;
        }

        private static String[] redirectPAOperations(String[] args) {
            List<String> list = new ArrayList<String>();
            list.addAll(Arrays.asList(args));
            String redirectOp = null;
            List<String> list2 = copyList(list);
            for (int i = 0; i < list2.size(); i++) {
                String arg = list2.get(i);
                if (isPAOp(arg)) {
                    redirectOp = arg;
                    list.remove(redirectOp);
                }
            }
            if (redirectOp != null) {
                list.remove(0);
                if (debug_)
                    log("redirect: " + redirectOp + " " + connectLines(list, " "));
                list.add(0, paOpType(redirectOp));
                String[] r = list.toArray(new String[list.size()]);
                return r;
            }
            return args;
        }

        private static void initPA() {
            paOps_.add("ap");
            paOps_.add("acp");
            paOps_.add("arm");
            paOps_.add("al");
            paOps_.add("arn");
            paOps_.add("af");
            paOps_.add("ao");
            paOps_.add("ar");

            paOpTypes_.put("ap", "print");
            paOpTypes_.put("acp", "copy");
            paOpTypes_.put("arm", "remove");
            paOpTypes_.put("al", "list");
            paOpTypes_.put("arn", "rename");
            paOpTypes_.put("af", "find");
            paOpTypes_.put("ao", "open");
            paOpTypes_.put("ar", "replace");
        }

        private static boolean isPAOp(String arg) {
            return paOps_.contains(arg);
        }

        private static String paOpType(String redirectOp) {
            return paOpTypes_.get(redirectOp);
        }

        private static String toRelativePath(String from, String p) {
            if (p.contains("\\\\"))
                p = p.replace("\\\\", "\\");
            from = addLast(from, FILE_SEPARATOR);
            return p.substring(from.length()).replace(FILE_SEPARATOR, "/");
        }

        private static List<File> toCopyFromFiles(List<File> fromFiles) {
            if (!fromFiles.isEmpty()) {
                boolean hasActucalFiles = hasActucalFiles(fromFiles);
                if (!hasActucalFiles) {
                    List<File> list = new ArrayList<File>();
                    for (File dir : fromFiles) {
                        list.addAll(Util.listFiles(dir, true));
                    }
                    return list;
                }
            }
            return fromFiles;
        }

        private static boolean hasActucalFiles(List<File> fromFiles) {
            for (File file : fromFiles) {
                if (file.isFile())
                    return true;
            }
            return false;
        }

        private static int getNameIndent(String from, List<File> files) {
            int i = 0;
            for (File file : files) {
                String p = file.getAbsolutePath();
                String rp = toRelativePath(from, p);
                i = Math.max(i, getUnicodeLength(rp));
            }
            return i;
        }

        private static String listFileDetail(File file, String relativePath, int nameIndent, Params params) {
            if (params.fullPath)
                return file.getAbsolutePath();
            int sizeIndent = 13;
            int dirIndent = 10;
            int timeIndent = 30;
            String n = formatstr(relativePath, nameIndent + 1);
            String size = file.isDirectory() ? formatstr("", sizeIndent) : formatstr(df.format(file.length()),
                    sizeIndent, false);
            String dir = file.isDirectory() ? formatstr("<DIR>", dirIndent) : formatstr("", dirIndent);
            String time = formatstr(sdf4.format(new Date(file.lastModified())), timeIndent);
            if (params.noFileDetail)
                return n;
            return format("{0} {1}     {2} {3}", n, size, dir, time);
        }

        private static List<File> getFromFiles(String from, String filefrom, Params params) throws Exception {
            FilenameFilter filter = Filters.getFilters(filefrom, params);
            List<File> files = Util.listFiles(new File(from), params.recursive, filter, params);
            return files;
        }

        private static List<File> getToFiles(String from, String filefrom, Params params) throws Exception {
            List<File> files = new ArrayList<File>();
            if (filefrom.equals("*")) {
                files.add(new File(from));
                return files;
            } else {
                FilenameFilter filter = Filters.getFilters(filefrom, params);
                files = Util.listFiles(new File(from), params.recursive, filter, params);
                return files;
            }
        }

        private static String fixFileFrom(String filefrom, Params params) {
            if (!filefrom.startsWith("*") && filefrom.endsWith("*")) {
                params.noPath = true;
            }
            if (filefrom.matches("\\{.*\\}")) {
                params.noPath = true;
            }
            if (filefrom.endsWith(";")) {
                params.noPath = true;
            }
            return filefrom;
        }

        private static void onlyOneFile(Params params) {
            params.recursive = false;
            params.recursiveLevel = 0;
            params.noPath = true;
        }

        private static boolean needOverwrite(String frompath, String topath, Params params) {
            if (params.overwrite)
                return true;
            if (exists(topath)) {
                return getFileTimestamp(frompath) > getFileTimestamp(topath) + 10 * SECOND;
            }
            return true;
        }

        private static String newFileNameInCopy(String fileName, Params params, boolean isFile) {
            if (params.newFileName != null) {
                String newFileName = params.newFileName;
                newFileName = newFileName(fileName, newFileName, isFile, false);
                return newFileName;
            }
            return fileName;
        }

        public static String newFileName(String fileName, String newFileName, boolean isFile) {
            return newFileName(fileName, newFileName, isFile, false);
        }

        public static String newFileName(String fileName, String newFileName, boolean isFile, boolean handleLine) {
            String fileSimpleName = fileName;
            if (!handleLine)
                fileSimpleName = getFileSimpleName(fileName);
            if (newFileName.matches("l\\d*")) {
                String n = cutFirst(newFileName, 1);
                newFileName = "{n-" + n + "}";
            }
            if (newFileName.matches("last\\d*")) {
                String n = cutFirst(newFileName, 4);
                newFileName = "{n-" + n + "}";
            }
            if (newFileName.matches("f\\d*")) {
                String n = cutFirst(newFileName, 1);
                newFileName = "{n" + n + "}";
            }
            if (newFileName.matches("first\\d*")) {
                String n = cutFirst(newFileName, 5);
                newFileName = "{n" + n + "}";
            }
            if (newFileName.matches("app.*")) {
                String n = cutFirst(newFileName, 3);
                newFileName = "{n}" + n;
            }
            if (newFileName.matches("pre.*")) {
                String n = cutFirst(newFileName, 3);
                newFileName = n + "{n}";
            }
            if (newFileName.matches("\\d*-?\\d*")) {
                String n = newFileName;
                newFileName = "{n" + n + "}";
            }
            if (newFileName.matches("c\\d*")) { // cut
                String n = cutFirst(newFileName, 1);
                int i = toInt(n);
                int len = fileSimpleName.length();
                newFileName = "{n-" + (len - i) + "}";
            }
            if (newFileName.matches("c-\\d*")) { // cut
                String n = cutFirst(newFileName, 2);
                int i = toInt(n);
                int len = fileSimpleName.length();
                newFileName = "{n" + (len - i) + "}";
            }
            if (newFileName.matches("'.*'")) {
                String n = cut(newFileName, 1, 1);
                newFileName = n;
            }
            if (newFileName.matches(".*\\{\\d*-?\\d*\\}.*")) {
                List<String> list = splitToListWithRegex(newFileName, "\\{\\d*-?\\d*\\}");
                for (String n : list) {
                    newFileName = newFileName.replace(n, "{n" + cutFirst(n, 1));
                }
            }
            // change path
            if (newFileName.endsWith("\\"))
                newFileName = addLast(newFileName, "{n}");
            // auto add ext
            if (isFile && newFileName.endsWith("}"))
                newFileName = addLast(newFileName, ".{e}");
            if (isFile && !newFileName.contains("."))
                newFileName = addLast(newFileName, ".{e}");
            // replace name
            if (newFileName.contains("{n}"))
                newFileName = newFileName.replace("{n}", fileSimpleName);
            // replace sub name
            if (newFileName.matches(".*\\{n\\d*-?\\d*\\}.*")) {
                List<String> list = splitToListWithRegex(newFileName, "\\{n\\d*-?\\d*\\}");
                for (String pattern : list) {
                    String sub = newFileNameSub(fileSimpleName, pattern);
                    newFileName = newFileName.replace(pattern, sub);
                }
            }
            // replace ext
            if (newFileName.contains("{e}"))
                newFileName = newFileName.replace("{e}", getFileExtName(fileName));
            return newFileName;
        }

        private static String newFileNameSub(String fileName, String pattern) {
            pattern = cut(pattern, 1, 1);
            if (pattern.matches("n\\d*-?\\d*")) {
                pattern = cutFirst(pattern, 1);
                String from, to;
                if (pattern.contains("-")) {
                    int i = pattern.indexOf("-");
                    from = pattern.substring(0, i);
                    to = pattern.substring(i + 1, pattern.length());
                } else {
                    from = pattern;
                    to = "";
                }
                int fpos = 0;
                int len = fileName.length();
                int tpos = Integer.MAX_VALUE;
                if (from != null && !from.isEmpty())
                    fpos = toInt(from);
                if (to != null && !to.isEmpty())
                    tpos = toInt(to);
                // last n
                if (fpos == 0) {
                    fpos = len - tpos + 1;
                    tpos = len;
                }
                // first n
                if (tpos == Integer.MAX_VALUE) {
                    tpos = fpos;
                    fpos = 1;
                }
                return sub(fileName, fpos - 1, tpos);
            }
            return "";
        }

        private static void renameFileInList(File file, Params params, String relativePath) {
            if (params.newFileName != null) {
                String fileName = file.getName();
                String newFileName = newFileNameInCopy(fileName, params, file.isFile());
                if (!newFileName.equals(fileName)) {
                    renameFile(file.getAbsolutePath(), fileName, newFileName);
                    relativePath = relativePath.replace(fileName, newFileName);
                    log(tab(2) + "-> " + relativePath);
                }
            }
        }

        private static void deleteFolderIfNecessary(Params params, File file) {
            if (params.recursiveLevel == 0) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                }
            }
        }

        private static String formatFrom(String from) {
            if (matchedItem_ != null) {
                if (matchedItem_.i > 1) {
                    return format("{0} [{1}]", from, matchedItem_.tarAlias);
                }
            }
            return from;
        }
    }

    public static class Filters implements FilenameFilter {

        private static final String FILTERS = "Filters: p={0}, filters={1}, filter={2}, and={3}, dir={4}, name={5}, result={6}";
        private static final String FILTERS2 = "Filters: p={0}, filters={1}, filter={2}, and={3}, line={4}, pos={5}, result={6}";

        private FiltersPattern p;
        private Params params;
        private List<Filters> filters = new ArrayList<Filters>();
        private List<FiltersPattern> subList;
        private Filter filter;
        private boolean and = true;

        public Filters(FiltersPattern p, Params params) {
            this.p = p;
            this.params = params;
            init(p);
        }

        private void init(FiltersPattern p) {
            subList = splitIntoSubList(p);
            if (subList.size() > 1) {
                for (FiltersPattern sub : subList) {
                    filters.add(new Filters(sub, params));
                }
            } else {
                filter = new Filter(subList.get(0), params);
            }
            if (debug2_) {
                log(format("Filters: p={0}, filters={1}, filter={2}", p, filters, filter));
            }
        }

        public static Filters getFilters(String filefrom, Params params2) {
            FiltersPattern fp = new FiltersPattern();
            fp.params = params2;
            fp.p = filefrom;
            fp.init();
            Filters f = new Filters(fp, params2);
            return f;
        }

        public String getFirst() {
            String first = null;
            if (isFilters()) {
                first = filters.get(0).getFirst();
            } else {
                first = filter.getFirst();
            }
            if (debug2_) {
                log(format("Filters: p={0}, filters={1}, filter={2}, first={3}", p, filters, filter, first));
            }
            return first;
        }

        public String getFirstNoIgnore() {
            String first = null;
            if (isFilters()) {
                first = filters.get(0).getFirstNoIgnore();
            } else {
                first = filter.getFirstNoIgnore();
            }
            if (debug2_) {
                log(format("Filters: p={0}, filters={1}, filter={2}, first={3}", p, filters, filter, first));
            }
            return first;
        }

        public boolean isFilters() {
            return filters.size() > 0;
        }

        public boolean isFilter() {
            return filter != null;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (isFilters()) {
                for (Filters f : filters) {
                    if (and) {
                        if (f.p.include)
                            sb.append("/");
                        else
                            sb.append("\\");
                    } else {
                        sb.append("##");
                    }
                    sb.append(f.toString());
                }
            } else {
                sb.append(filter.toString());
            }
            String p = sb.toString();
            String s = fix(p);
            return s;
        }

        private String fix(String p) {
            String p0 = this.p.p;
            FiltersPattern fp = this.p.copy();
            fp.setP(p);
            String s = fp.toString();
            if (!p0.startsWith("/"))
                s = cutFirst(s, 1);
            return s;
        }

        private List<FiltersPattern> splitIntoSubList(FiltersPattern p) {
            String s = p.toString();
            s = appendFirstLeftIfNecessary(s);
            FiltersPattern item;
            List<FiltersPattern> list = null;
            StringBuilder sb = new StringBuilder();
            boolean quote = false;
            boolean regular = false;
            int sub = 0;
            boolean include = false;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '\'') {
                    if (quote == false) {
                        quote = true;
                    } else {
                        quote = false;
                    }
                    sb.append(c);
                } else if (c == '@') {
                    if (regular == false) {
                        regular = true;
                    } else {
                        regular = false;
                    }
                    sb.append(c);
                } else if (c == '(') {
                    sub++;
                    sb.append(c);
                } else if (c == ')') {
                    sub--;
                    sb.append(c);
                } else {
                    if (quote) {
                        sb.append(c);
                        continue;
                    }
                    if (regular) {
                        sb.append(c);
                        continue;
                    }
                    if (sub > 0) {
                        sb.append(c);
                        continue;
                    }
                    if (c == '/' || c == '\\') {
                        if (list != null) {
                            item = new FiltersPattern();
                            item.params = params;
                            item.p = sb.toString();
                            item.include = include;
                            item.init();
                            list.add(item);
                            sb = new StringBuilder();
                            include = (c == '/');
                        } else {
                            list = new ArrayList<FiltersPattern>();
                            include = (c == '/');
                        }
                    } else if (c == '#') {
                        if (i < s.length() - 1) {
                            char next = s.charAt(i + 1);
                            if (next == '#') {
                                and = false;

                                item = new FiltersPattern();
                                item.params = params;
                                item.p = sb.toString();
                                item.include = include;
                                item.init();
                                list.add(item);
                                sb = new StringBuilder();
                                include = true;
                                i++;
                            } else {
                                sb.append(c);
                            }
                        }
                    } else {
                        sb.append(c);
                    }
                }
            }
            item = new FiltersPattern();
            item.params = params;
            item.p = sb.toString();
            item.include = include;
            item.init();
            list.add(item);
            sb = new StringBuilder();
            return list;
        }

        private static String appendFirstLeftIfNecessary(String p) {
            if (!p.startsWith("/") && !p.startsWith("\\"))
                p = "/" + p;
            return p;
        }

        public boolean accept(File dir, String name) {
            if (and) {
                if (isFilters()) {
                    for (Filters filter : filters) {
                        if (!filter.accept(dir, name)) {
                            if (debug2_) {
                                log(format(FILTERS, p, filters, filter, and, dir, name, false));
                            }
                            return false;
                        }
                    }
                    if (debug2_) {
                        log(format(FILTERS, p, filters, filter, and, dir, name, true));
                    }
                    return true;
                } else {
                    boolean result = filter.accept(dir, name);
                    if (debug2_) {
                        log(format(FILTERS, p, filters, filter, and, dir, name, result));
                    }
                    return result;
                }
            } else {
                if (isFilters()) {
                    for (Filters filter : filters) {
                        if (filter.accept(dir, name)) {
                            if (debug2_) {
                                log(format(FILTERS, p, filters, filter, and, dir, name, true));
                            }
                            return true;
                        }
                    }
                    if (debug2_) {
                        log(format(FILTERS, p, filters, filter, and, dir, name, false));
                    }
                    return false;
                } else {
                    boolean result = filter.accept(dir, name);
                    if (debug2_) {
                        log(format(FILTERS, p, filters, filter, and, dir, name, result));
                    }
                    return result;
                }
            }
        }

        public boolean accept(String line, int pos) {
            if (and) {
                if (isFilters()) {
                    for (Filters filter : filters) {
                        if (!filter.accept(line, pos)) {
                            if (debug2_) {
                                log(format(FILTERS2, p, filters, filter, and, line, pos, false));
                            }
                            return false;
                        }
                    }
                    if (debug2_) {
                        log(format(FILTERS2, p, filters, filter, and, line, pos, true));
                    }
                    return true;
                } else {
                    boolean result = filter.accept(line, pos);
                    if (debug2_) {
                        log(format(FILTERS2, p, filters, filter, and, line, pos, result));
                    }
                    return result;
                }
            } else {
                if (isFilters()) {
                    for (Filters filter : filters) {
                        if (filter.accept(line, pos)) {
                            if (debug2_) {
                                log(format(FILTERS2, p, filters, filter, and, line, pos, true));
                            }
                            return true;
                        }
                    }
                    if (debug2_) {
                        log(format(FILTERS2, p, filters, filter, and, line, pos, false));
                    }
                    return false;
                } else {
                    boolean result = filter.accept(line, pos);
                    if (debug2_) {
                        log(format(FILTERS2, p, filters, filter, and, line, pos, result));
                    }
                    return result;
                }
            }
        }
    }

    public static class Filter {

        private static final String FILTER = "Filter: p={0}, dir={1}, name={2}, result={3}";
        private static final String FILTER2 = "Filter: p={0}, line={1}, pos={2}, result={3}";

        private FiltersPattern p;
        private Filters filters;
        private Params params;

        public Filter(FiltersPattern p, Params params) {
            this.p = p;
            this.params = params;
            init();
        }

        public String getFirst() {
            String first = null;
            if (p.isGroup()) {
                first = filters.getFirst();
            } else {
                first = p.getFirst();
            }
            if (debug2_) {
                log(format("Filter: p={0}, filters={1}, first={3}", p, filters, first));
            }
            return first;
        }

        public String getFirstNoIgnore() {
            String first = null;
            if (p.isGroup()) {
                first = filters.getFirstNoIgnore();
            } else {
                first = p.getFirstNoIgnore();
            }
            if (debug2_) {
                log(format("Filter: p={0}, filters={1}, first={3}", p, filters, first));
            }
            return first;
        }

        private void init() {
            if (p.isGroup()) {
                filters = Filters.getFilters(p.p, params);
            }
        }

        public boolean accept(File dir, String name) {
            if (p.isGroup()) {
                boolean b = filters.accept(dir, name);
                if (p.include) {
                    return b;
                } else {
                    return !b;
                }
            } else {
                if (params != null && !params.noPath)
                    name = dir.getAbsolutePath() + FILE_SEPARATOR + name;
                boolean result = p.accept(name);
                if (debug2_) {
                    log(format(FILTER, p, dir, name, result));
                }
                return result;
            }
        }

        public boolean accept(String line, int pos) {
            if (p.isGroup()) {
                boolean b = filters.accept(line, pos);
                if (p.include) {
                    return b;
                } else {
                    return !b;
                }
            } else {
                boolean result = p.accept(line, pos);
                if (debug2_) {
                    log(format(FILTER2, p, line, pos, result));
                }
                return result;
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(p.toString());
            return sb.toString();
        }
    }

    public static class FiltersPattern {
        private Params params;
        public String p;
        public boolean include = true;
        public boolean quote = false;
        public boolean group = false;
        public boolean regular = false;
        public boolean lineNumber = false;
        public boolean emptyLine = false;

        private boolean ignore = false;

        public FiltersPattern() {
        }

        public void init() {
            if (p.startsWith("/")) {
                include = true;
                p = cutFirst(p, 1);
            } else if (p.startsWith("\\")) {
                include = false;
                p = cutFirst(p, 1);
            }
            if (p.startsWith("'") && p.endsWith("'")) {
                quote = true;
                p = cutFirst(p, 1);
                p = cutLast(p, 1);
            } else if (p.startsWith("@") && p.endsWith("@")) {
                regular = true;
                p = cutFirst(p, 1);
                p = cutLast(p, 1);
            } else if (p.startsWith("(") && p.endsWith(")")) {
                group = true;
                p = cutFirst(p, 1);
                p = cutLast(p, 1);
            } else if (p.matches("l\\d*-?\\d*")) {
                lineNumber = true;
            } else if (p.equalsIgnoreCase("EL")) {
                emptyLine = true;
            }
        }

        public String getFirst() {
            ignore = true;
            return p;
        }

        public String getFirstNoIgnore() {
            return p;
        }

        public boolean isGroup() {
            return group;
        }

        public FiltersPattern copy() {
            FiltersPattern fp = new FiltersPattern();
            fp.params = params;
            fp.p = p;
            fp.include = include;
            fp.quote = quote;
            fp.group = group;
            fp.regular = regular;
            fp.lineNumber = lineNumber;
            return fp;
        }

        public void setP(String p2) {
            if (p2.startsWith("/") || p2.startsWith("\\")) {
                p2 = cutFirst(p2, 1);
            }
            if (p2.startsWith("##")) {
                p2 = cutFirst(p2, 2);
            } else {
                if (p2.startsWith("@") && p2.endsWith("@")) {
                    p2 = cutFirst(p2, 1);
                    p2 = cutLast(p2, 1);
                } else if (p2.startsWith("'") && p2.endsWith("'")) {
                    p2 = cutFirst(p2, 1);
                    p2 = cutLast(p2, 1);
                } else if (p2.startsWith("(") && p2.endsWith(")")) {
                    if (isWrapped(p2)) {
                        p2 = cutFirst(p2, 1);
                        p2 = cutLast(p2, 1);
                    }
                }
            }
            p = p2;
        }

        private boolean isWrapped(String p2) {
            int k = 0;
            int pos = 0;
            for (int i = 0; i < p2.length(); i++) {
                char c = p2.charAt(i);
                if (c == '(')
                    k++;
                else if (c == ')')
                    k--;
                if (k == 0) {
                    pos = i;
                    break;
                }
            }
            return pos == p2.length() - 1;
        }

        public boolean accept(String line) {
            boolean b;
            if (regular) {
                if (debug2_) {
                    log(format("Pattern: line={2}, p={0}, regular={1}", p, regular, line));
                }
                b = line.matches(p);
            } else if (quote) {
                if (debug2_) {
                    log(format("Pattern: line={2}, p={0}, quote={1}", p, quote, line));
                }
                b = line.contains(p);
            } else if (emptyLine) {
                if (debug2_) {
                    log(format("Pattern: line={2}, p={0}, emptyLine={1}", p, emptyLine, line));
                }
                b = line.trim().isEmpty();
            } else {
                String fixPattern = fixPattern(p);
                if (debug2_) {
                    log(format("Pattern: line={2}, p={0}, fixPattern={1}", p, fixPattern, line));
                }
                b = matchesIgnoreCase(line, fixPattern, params.caseSensitive);
            }
            if (include) {
                if (debug2_) {
                    log(format("Pattern: line={2}, p={0}, result={1}", p, b, line));
                }
                return b;
            } else {
                if (debug2_) {
                    log(format("Pattern: line={2}, p={0}, result={1}", p, !b, line));
                }
                return !b;
            }
        }

        public boolean accept(String line, int pos) {
            boolean b;
            if (regular) {
                if (debug2_) {
                    log(format("Pattern: line={2}, p={0}, regular={1}", p, regular, line));
                }
                b = line.matches(p);
            } else if (quote) {
                if (debug2_) {
                    log(format("Pattern: line={2}, p={0}, quote={1}", p, quote, line));
                }
                b = line.contains(p);
            } else if (lineNumber) {
                if (debug2_) {
                    log(format("Pattern: line={2}, p={0}, lineNumber={1}", p, lineNumber, line));
                }
                b = ExpandLines.matchesLineNumber(p, pos);
            } else if (emptyLine) {
                if (debug2_) {
                    log(format("Pattern: line={2}, p={0}, emptyLine={1}", p, emptyLine, line));
                }
                b = line.trim().isEmpty();
            } else {
                String fixPattern = fixPattern(p);
                if (debug2_) {
                    log(format("Pattern: line={2}, p={0}, fixPattern={1}", p, fixPattern, line));
                }
                b = matchesIgnoreCase(line, fixPattern, params.caseSensitive);
            }
            if (ignore)
                b = true;
            if (include) {
                if (debug2_) {
                    log(format("Pattern: line={2}, p={0}, result={1}", p, b, line));
                }
                return b;
            } else {
                if (debug2_) {
                    log(format("Pattern: line={2}, p={0}, result={1}", p, !b, line));
                }
                return !b;
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (include)
                sb.append("/");
            else
                sb.append("\\");
            if (quote)
                sb.append("'" + p + "'");
            else if (group)
                sb.append("(" + p + ")");
            else if (regular)
                sb.append("@" + p + "@");
            else
                sb.append(p);
            return sb.toString();
        }

        private static String fixPattern(String filefrom) {
            filefrom = filefrom.replace("`", "*").replace("~", "*");
            if (isContainsPatternNecessary(filefrom)) {
                // wrap * begins and ends like "*abc*". it means contains.
                if (!filefrom.startsWith("*"))
                    filefrom = "*" + filefrom;
                if (!filefrom.endsWith("*"))
                    filefrom = filefrom + "*";
            }
            // cut { and } for exactly match cases
            if (filefrom.matches("\\{.*\\}"))
                filefrom = cut(filefrom, 1, 1);
            if (filefrom.endsWith(";"))
                filefrom = cutLast(filefrom, 1);
            // fix regular expression
            String result = filefrom.replace(".", "\\.").replace("*", ".*");
            return result;
        }

        private static boolean isContainsPatternNecessary(String filefrom) {
            if (filefrom.startsWith("*")) // *abc
                return false;
            if (filefrom.endsWith("*")) // abc*
                return false;
            if (filefrom.matches("l\\d*-?\\d*")) // l100, l100-, l-200, l100-200, not first (first is search key)
                return false;
            if (filefrom.matches("\\{.*\\}")) // {abc}: exactly match abc, not contains
                return false;
            if (filefrom.endsWith(";")) // abc; : exactly match abc, not contains
                return false;
            if (filefrom.contains("##")) // a##b, it means a or b
                return false;
            return true;
        }

    }

    public static class ExpandLinesResult {
        public String[] args;
        public ExpandLines expandLines;

        public static ExpandLinesResult expandLines(String[] args) {
            ExpandLinesResult r = new ExpandLinesResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.expandLines = ExpandLines.parseExpandLines(last);
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "Expand Lines: " + r.expandLines);
            } else {
                r.expandLines = null;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return last.matches("l\\d*-?\\d*");
        }

        public static class ExpandLines {
            public int from = 0;
            public int to = 0;

            public static ExpandLines parseExpandLines(String pattern) {
                if (pattern.matches("l\\d*-?\\d*")) {
                    pattern = cutFirst(pattern, 1);
                    String from, to;
                    if (pattern.contains("-")) {
                        int i = pattern.indexOf("-");
                        from = pattern.substring(0, i);
                        to = pattern.substring(i + 1, pattern.length());
                    } else {
                        from = pattern;
                        to = "";
                    }
                    int fpos = 0;
                    int tpos = Integer.MAX_VALUE;
                    if (from != null && !from.isEmpty())
                        fpos = toInt(from);
                    if (to != null && !to.isEmpty())
                        tpos = toInt(to);
                    ExpandLines el = new ExpandLines();
                    el.from = fpos;
                    el.to = tpos;
                    return el;
                }
                return null;
            }

            public static boolean matchesLineNumber(String pattern, int pos) {
                ExpandLines el = ExpandLines.parseExpandLines(pattern);
                int fpos = el.from;
                int tpos = el.to;
                return pos >= fpos && pos <= tpos;
            }

            public static boolean matchesLineNumber(ExpandLines expandLines, int pos) {
                ExpandLines el = expandLines;
                int fpos = el.from;
                int tpos = el.to;
                return pos >= fpos && pos < tpos;
            }

            @Override
            public String toString() {
                return format("{0}-{1}", from, to);
            }
        }
    }

    public static class RecursiveLevelResult {
        public String[] args;
        public int n;

        public static RecursiveLevelResult recursiveLevel(String[] args) {
            RecursiveLevelResult r = new RecursiveLevelResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.n = toInt(cutFirst(last, 1));
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "Recursive Level: " + r.n);
            } else {
                r.n = -1;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return last.matches("r\\d+");
        }
    }

    public static class NoLineNumberResult {
        public String[] args;
        public boolean noLineNumber;

        public static NoLineNumberResult noLineNumber(String[] args) {
            NoLineNumberResult r = new NoLineNumberResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.noLineNumber = true;
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "No Line Number: " + r.noLineNumber);
            } else {
                r.noLineNumber = false;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return last.equals("nl");
        }
    }

    public static class CaseSensitiveResult {
        public String[] args;
        public boolean caseSensitive;

        public static CaseSensitiveResult caseSensitive(String[] args) {
            CaseSensitiveResult r = new CaseSensitiveResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.caseSensitive = true;
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "Case Sensitive: " + r.caseSensitive);
            } else {
                r.caseSensitive = false;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return last.equals("c");
        }
    }

    public static class OpenDirResult {
        public String[] args;
        public boolean openDir;
        public int openDirsCount = 10;
        public boolean openFile;
        public int openFilesCount = 10;

        public static OpenDirResult openDir(String[] args) {
            OpenDirResult r = new OpenDirResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                if (isParamOpenDir(last)) {
                    r.openDir = true;
                    if (!last.equals("d")) {
                        r.openDirsCount = toInt(cutFirst(last, 1));
                    }
                } else {
                    r.openFile = true;
                    if (!last.equals("f")) {
                        r.openFilesCount = toInt(cutFirst(last, 1));
                    }
                }
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "Open Dir: " + r.openDir);
            } else {
                r.openDir = false;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            boolean isParamOpenDir = isParamOpenDir(last);
            boolean isParamOpenFile = isParamOpenFile(last);
            return isParamOpenDir || isParamOpenFile;
        }

        private static boolean isParamOpenFile(String last) {
            return last.equals("f") || last.matches("f\\d*");
        }

        private static boolean isParamOpenDir(String last) {
            return last.equals("d") || last.matches("d\\d*");
        }

        public static void openDirs(Params params, List<String> dirs, String from) throws Exception {
            if (params.openDir) {
                List<String> list = new ArrayList<String>();
                if (dirs != null && !dirs.isEmpty()) {
                    int i = 0;
                    Set<String> opened = new HashSet<String>();
                    for (String dir : dirs) {
                        if (isFile(dir)) {
                            if (!opened.contains(getParent(dir))) {
                                list.add(toExplorerLineSelect(dir));
                                opened.add(getParent(dir));
                            }
                        } else {
                            if (dir.contains("\\\\"))
                                dir = dir.replace("\\\\", "\\");
                            if (!opened.contains(dir)) {
                                list.add(toExplorerLine(dir));
                                opened.add(dir);
                            }
                        }
                        i++;
                        if (params.openDirsCount > 0 && i >= params.openDirsCount)
                            break;
                    }
                } else {
                    list.add(toExplorerLine(from));
                }
                setLines(batDir + "aopendirtmp.bat", list);
            }
            if (params.openFile) {
                if (dirs != null && !dirs.isEmpty()) {
                    List<String> list = new ArrayList<String>();
                    int i = 0;
                    for (String dir : dirs) {
                        if (isFile(dir)) {
                            if (isTextFile(dir))
                                list.add(toEditLine(dir));
                            else
                                list.add(toExplorerLine(dir));
                        } else {
                            list.add(toExplorerLine(dir));
                        }
                        i++;
                        if (params.openFilesCount > 0 && i >= params.openFilesCount)
                            break;
                    }
                    setLines(batDir + "aopenfiletmp.bat", list);
                }
            }
        }
    }

    public static class GBKEncodingResult {
        public String[] args;
        public boolean gbkEncoding;

        public static GBKEncodingResult gbkEncoding(String[] args) {
            GBKEncodingResult r = new GBKEncodingResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.gbkEncoding = true;
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "GBK Encoding: " + r.gbkEncoding);
            } else {
                r.gbkEncoding = false;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return last.equals("gbk");
        }
    }

    public static class KeepDirResult {
        public String[] args;
        public boolean keepDir;

        public static KeepDirResult keepDir(String[] args) {
            KeepDirResult r = new KeepDirResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.keepDir = true;
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "Keep Dir: " + r.keepDir);
            } else {
                r.keepDir = false;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return last.equals("kd");
        }

    }

    public static class NewFileNameResult {
        public String[] args;
        public String newFileName;

        public static NewFileNameResult newFileName(String[] args) {
            NewFileNameResult r = new NewFileNameResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.newFileName = cutFirst(last, 2);
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "New File Name: " + r.newFileName);
            } else {
                r.newFileName = null;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return last.startsWith("##");
        }

    }

    public static class NoPathResult {
        public String[] args;
        public boolean noPath;

        public static NoPathResult noPath(String[] args) {
            NoPathResult r = new NoPathResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.noPath = true;
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "No Path: " + r.noPath);
            } else {
                r.noPath = false;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return last.equals("np");
        }
    }

    public static class FullPathResult {
        public String[] args;
        public boolean fullPath;

        public static FullPathResult fullPath(String[] args) {
            FullPathResult r = new FullPathResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.fullPath = true;
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "Full Path: " + r.fullPath);
            } else {
                r.fullPath = false;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return last.equals("fp") || last.equals("fn");
        }
    }

    public static class UseDotResult {
        public String[] args;
        public boolean useDot = false;
        public boolean noFileDetail = false;

        public static UseDotResult useDot(String[] args) {
            UseDotResult r = new UseDotResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.useDot = isUseDot(last);
                r.noFileDetail = isNoFileDetail(last);
                r.args = cutLastArg(args);
                if (debug_) {
                    log(tab(2) + "Use Dot: " + r.useDot);
                    log(tab(2) + "No File Detail: " + r.noFileDetail);
                }
            } else {
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return isUseDot(last) || isNoFileDetail(last);
        }

        private static boolean isUseDot(String last) {
            return last.equals("ud");
        }

        private static boolean isNoFileDetail(String last) {
            return last.equals("nd");
        }
    }

    public static class SortTypeResult {
        public String[] args;
        public String sortType;

        public static SortTypeResult sortType(String[] args) {
            SortTypeResult r = new SortTypeResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.sortType = parseSortType(last);
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "Sort Type: " + r.sortType);
            } else {
                r.sortType = null;
                r.args = args;
            }
            return r;
        }

        private static String parseSortType(String last) {
            if (isSortByTime(last))
                return cutFirst(last, 1);
            if (isSortByColumn(last))
                return cut(last, 2, 1);
            return null;
        }

        public static boolean isParam(String last) {
            return isSortByTime(last) || isSortByColumn(last);
        }

        private static boolean isSortByTime(String last) {
            return last.matches("s[t]");
        }

        private static boolean isSortByColumn(String last) {
            return last.matches("s\\{.*\\}");
        }
    }

    public static class MultipleLinesResult {
        public String[] args;
        public boolean multipleLines;

        public static MultipleLinesResult multipleLines(String[] args) {
            MultipleLinesResult r = new MultipleLinesResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.multipleLines = true;
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "Multiple Lines: " + r.multipleLines);
            } else {
                r.multipleLines = false;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return last.equals("ml");
        }
    }

    public static class MoveResult {
        public String[] args;
        public boolean move;

        public static MoveResult move(String[] args) {
            MoveResult r = new MoveResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.move = true;
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "Move: " + r.move);
            } else {
                r.move = false;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return last.equals("mv");
        }
    }

    public static class OperateLinesResult {
        public String[] args;
        public OperateLines operateLines;

        public static OperateLinesResult operateLines(String[] args) {
            OperateLinesResult r = new OperateLinesResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.operateLines = OperateLines.parseOperateLines(last);
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "Operate Lines: " + r.operateLines);
            } else {
                r.operateLines = null;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return OperateLines.isParam(last);
        }

        public static class OperateLines {
            public int op = 0;
            public int to = 0;

            public static OperateLines parseOperateLines(String pattern) {
                if (isParam(pattern)) {
                    OperateLines ol = new OperateLines();
                    ol.op = getOp(pattern);
                    ol.to = getTo(pattern);
                    return ol;
                }
                return null;
            }

            public static boolean isParam(String pattern) {
                return getOp(pattern) > 0;
            }

            public static int getOp(String pattern) {
                // duplicate
                if (pattern.equals("dup"))
                    return 1;
                // move
                if (!pattern.equals("mv") && pattern.matches("mv\\d*"))
                    return 2;
                // delete
                if (pattern.equals("del"))
                    return 3;
                return 0;
            }

            public static int getTo(String pattern) {
                int op = getOp(pattern);
                if (op == 2) {
                    return Integer.valueOf(cutFirst(pattern, 2));
                }
                return 0;
            }

            @Override
            public String toString() {
                if (op == 1)
                    return "dup";
                if (op == 2)
                    return "mv" + to;
                if (op == 3)
                    return "del";
                return "";
            }
        }

        public static class OperateLinesUtil {
            public static void operateLines(String p, List<Line> foundLines, Params params) throws Exception {
                OperateLines ol = params.operateLines;
                if (ol != null) {
                    int op = ol.op;
                    if (op == 1) {
                        dupLines(p, foundLines, params);
                    } else if (op == 2) {
                        mvLines(p, foundLines, params);
                    } else if (op == 3) {
                        delLines(p, foundLines, params);
                    }
                }
            }

            private static void dupLines(String p, List<Line> foundLines, Params params) throws Exception {
                if (foundLines.size() > 0) {
                    List<String> list = new ArrayList<String>();
                    for (Line lineObj : foundLines) {
                        list.addAll(lineObj.toLines());
                    }
                    int n = list.size();
                    int i = foundLines.get(0).i;
                    insertLines(p, list, i + n, params.getEncoding());
                }
            }

            private static void mvLines(String p, List<Line> foundLines, Params params) throws Exception {
                if (foundLines.size() > 0) {
                    List<String> list = new ArrayList<String>();
                    for (Line lineObj : foundLines) {
                        list.addAll(lineObj.toLines());
                    }
                    int i = foundLines.get(0).i;
                    OperateLines ol = params.operateLines;
                    int to = ol.to;
                    if (to > i) {
                        insertLines(p, list, to, params.getEncoding());
                        deleteLines(p, list, i, params.getEncoding());
                    } else {
                        deleteLines(p, list, i, params.getEncoding());
                        insertLines(p, list, to, params.getEncoding());
                    }
                }
            }

            private static void delLines(String p, List<Line> foundLines, Params params) throws Exception {
                if (foundLines.size() > 0) {
                    List<String> list = new ArrayList<String>();
                    for (Line lineObj : foundLines) {
                        list.addAll(lineObj.toLines());
                    }
                    int i = foundLines.get(0).i;
                    deleteLines(p, list, i, params.getEncoding());
                }
            }
        }

    }

    public static class ZipOperationsResult {
        public String[] args;
        public ZipOperations zipOperations;

        public static ZipOperationsResult zipOperations(String[] args) throws Exception {
            ZipOperationsResult r = new ZipOperationsResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.zipOperations = ZipOperations.parseZipOperations(last);
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "Zip Operations: " + r.zipOperations);
            } else {
                r.zipOperations = null;
                r.args = args;
            }
            return r;
        }

        public static void zipOperations(Params params, List<String> files) throws Exception {
            if (params.zipOperations != null) {
                ZipOperations zo = params.zipOperations;
                if (zo.zip) {
                    if (files.size() == 1) {
                        String zipFile = files.get(0);
                        String from = zipFile;
                        String to = getParent(zo.to);
                        String name = getFileName(zo.to);
                        String line = format("call azip \"{0}\" \"{1}\" \"{2}\"", from, to, name);
                        setLines(batDir + "aziptmp.bat", toList(line));
                    } else {
                        if (debug_)
                            log("Ignore zip operations since file size is " + files.size());
                    }
                }
                if (zo.unzip) {
                    if (files.size() == 1) {
                        String zipFile = files.get(0);
                        String from = getParent(zipFile);
                        String to = zo.to;
                        String name = getFileName(zipFile);
                        String line = format("call aunzip \"{0}\" \"{1}\" \"{2}\"", from, to, name);
                        setLines(batDir + "aunziptmp.bat", toList(line));
                    } else {
                        if (debug_)
                            log("Ignore zip operations since file size is " + files.size());
                    }
                }
                if (zo.adf) {
                    if (files.size() == 1) {
                        String from = files.get(0);
                        String fileName = getFileName(from);
                        String to = searchFile(zo.to, fileName);
                        setLines(batDir + "adftmp.bat", CustomOperations.toAdfLines(from, to));
                    } else {
                        if (debug_)
                            log("Ignore adf operations since file size is " + files.size());
                    }
                }
                if (zo.sort) {
                    for (String file : files) {
                        if (isTextFile(file)) {
                            String from = file;
                            boolean asc = true;
                            if (zo.to != null && zo.to.equalsIgnoreCase("desc"))
                                asc = false;
                            sortFile(from, asc);
                            if (asc)
                                log(2, "sort: " + from);
                            else
                                log(2, "sort [desc]: " + from);
                        }
                    }
                }
            }
        }

        private static String searchFile(String to, String fileName) {
            Params params = new Params();
            params.noPath = true;
            FilenameFilter filter = Filters.getFilters("{" + fileName + "}", params);
            List<File> files = Util.listFiles(new File(to), params.recursive, filter, params);
            return files.get(0).getAbsolutePath();
        }

        public static boolean isParam(String last) {
            return ZipOperations.isParam(last);
        }

        public static class ZipOperations {
            public boolean zip = false;
            public boolean unzip = false;
            public boolean adf = false;
            public boolean sql = false;
            public boolean sort = false;
            public String to;

            public boolean isExp() {
                return zip;
            }

            public boolean isImp() {
                return unzip;
            }

            public boolean isZip() {
                return zip;
            }

            public boolean isSql() {
                return sql;
            }

            public boolean isSort() {
                return sort;
            }

            public static ZipOperations parseZipOperations(String pattern) throws Exception {
                if (isParam(pattern)) {
                    ZipOperations zo = new ZipOperations();
                    zo.zip = isZip(pattern);
                    zo.unzip = isUnzip(pattern);
                    zo.adf = isAdf(pattern);
                    zo.sql = isSql(pattern);
                    zo.sort = isSort(pattern);
                    zo.to = getTo(pattern);
                    return zo;
                }
                return null;
            }

            public static boolean isParam(String last) {
                return isZip(last) || isUnzip(last) || isAdf(last) || isSql(last) || isSort(last);
            }

            private static boolean isZip(String pattern) {
                return pattern.startsWith("zip=") || pattern.startsWith("exp=");
            }

            private static boolean isUnzip(String pattern) {
                return pattern.startsWith("unzip=") || pattern.startsWith("imp=");
            }

            private static boolean isAdf(String pattern) {
                return pattern.startsWith("adf=");
            }

            private static boolean isSql(String pattern) {
                return pattern.startsWith("sql=");
            }

            private static boolean isSort(String pattern) {
                return pattern.equals("sort") || pattern.startsWith("sort=");
            }

            private static String getTo(String pattern) throws Exception {
                if (pattern.contains("="))
                    return toTARAlias(cut(pattern, "=", null));
                return null;
            }

            @Override
            public String toString() {
                if (zip)
                    return format("zip={0}", to);
                else
                    return format("unzip={0}", to);
            }
        }
    }

    public static class OverwriteResult {
        public String[] args;
        public boolean overwrite;

        public static OverwriteResult overwrite(String[] args) {
            OverwriteResult r = new OverwriteResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.overwrite = true;
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "Overwrite: " + r.overwrite);
            } else {
                r.overwrite = false;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return last.equals("ov");
        }
    }

    public static class DeleteSameResult {
        public String[] args;
        public boolean deleteSame = false;
        public boolean makeSame = false;
        public boolean diffSame = false;

        public static DeleteSameResult deleteSame(String[] args) {
            DeleteSameResult r = new DeleteSameResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                if (isDeleteSame(last))
                    r.deleteSame = true;
                if (isMakeSame(last))
                    r.makeSame = true;
                if (isDiffSame(last))
                    r.diffSame = true;
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "Delete Same: " + r.deleteSame);
            } else {
                r.deleteSame = false;
                r.makeSame = false;
                r.args = args;
            }
            return r;
        }

        public static void deleteSame(Params params, List<String> dirs) throws Exception {
            if (params.deleteSame) {
                if (dirs != null && !dirs.isEmpty()) {
                    for (String dir : dirs) {
                        if (isFile(dir)) {
                            compareAndDeleteSame(dir);
                        }
                    }
                }
            }
            if (params.makeSame) {
                if (dirs != null && !dirs.isEmpty()) {
                    for (String dir : dirs) {
                        if (isFile(dir)) {
                            compareAndMakeSame(dir);
                        }
                    }
                }
            }
            if (params.diffSame) {
                if (dirs != null && !dirs.isEmpty()) {
                    List<String> list = new ArrayList<String>();
                    for (String dir : dirs) {
                        if (isFile(dir)) {
                            String ds = compareAndDiffSame(dir);
                            if (ds != null)
                                list.add(ds);
                        }
                    }
                    setLines(batDir + "rndiff.bat", list);
                }
            }
        }

        public static boolean isParam(String last) {
            return isDeleteSame(last) || isMakeSame(last) || isDiffSame(last);
        }

        private static boolean isDeleteSame(String last) {
            return last.equals("ds");
        }

        private static boolean isMakeSame(String last) {
            return last.equals("ms");
        }

        private static boolean isDiffSame(String last) {
            return last.equals("diff");
        }
    }

    public static class FileTimestampResult {
        public String[] args;
        public FileTimestamp fileTimestamp;

        public static FileTimestampResult fileTimestamp(String[] args) throws Exception {
            FileTimestampResult r = new FileTimestampResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.fileTimestamp = FileTimestamp.parseFileTimestamp(last);
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "File Timestamp: " + r.fileTimestamp);
            } else {
                r.fileTimestamp = null;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return isFromTo(last) || isNearDays(last);
        }

        private static boolean isFromTo(String last) {
            return last.matches("t\\d*-?\\d*");
        }

        private static boolean isNearDays(String last) {
            return last.matches("t(\\d*[dwmy])*");
        }

        public static class FileTimestamp {
            public long from = 0;
            public long to = Long.MAX_VALUE;

            public boolean matches(File file) {
                return matchesFileTimestamp(this, file.getAbsolutePath());
            }

            public static FileTimestamp parseFileTimestamp(String pattern) throws Exception {
                if (isFromTo(pattern)) {
                    pattern = cutFirst(pattern, 1);
                    String from, to;
                    if (pattern.contains("-")) {
                        int i = pattern.indexOf("-");
                        from = pattern.substring(0, i);
                        to = pattern.substring(i + 1, pattern.length());
                    } else {
                        from = pattern;
                        to = "";
                        if (from.isEmpty())
                            from = "0";
                    }
                    long fpos = 0;
                    long tpos = Long.MAX_VALUE;
                    if (from != null && !from.isEmpty())
                        fpos = toTimestamp(from);
                    if (to != null && !to.isEmpty())
                        tpos = toTimestamp(to);
                    FileTimestamp ft = new FileTimestamp();
                    ft.from = fpos;
                    ft.to = tpos;
                    return ft;
                }
                if (isNearDays(pattern)) {
                    pattern = cutFirst(pattern, 1);
                    String from, to = tomorrow();
                    List<String> list = splitToListWithRegex(pattern, "\\d*[dwmy]");
                    Calendar c = Calendar.getInstance();
                    for (String s : list) {
                        String number = cutLast(s, 1);
                        String unit = subLast(s, 1);
                        switch (unit) {
                        case "d":
                            c.add(Calendar.DAY_OF_YEAR, -1 * toInt(number));
                            break;
                        case "w":
                            c.add(Calendar.WEEK_OF_YEAR, -1 * toInt(number));
                            break;
                        case "m":
                            c.add(Calendar.MONTH, -1 * toInt(number));
                            break;
                        case "y":
                            c.add(Calendar.YEAR, -1 * toInt(number));
                            break;
                        default:
                            break;
                        }
                    }
                    from = toDateStr2(c.getTimeInMillis());
                    long fpos = 0;
                    long tpos = Long.MAX_VALUE;
                    if (from != null && !from.isEmpty())
                        fpos = toTimestamp(from);
                    if (to != null && !to.isEmpty())
                        tpos = toTimestamp(to);
                    FileTimestamp ft = new FileTimestamp();
                    ft.from = fpos;
                    ft.to = tpos;
                    return ft;
                }
                return null;
            }

            public static boolean matchesFileTimestamp(String pattern, String p) throws Exception {
                FileTimestamp el = FileTimestamp.parseFileTimestamp(pattern);
                long fpos = el.from;
                long tpos = el.to;
                long pos = getFileTimestamp(p);
                return pos >= fpos && pos <= tpos;
            }

            public static boolean matchesFileTimestamp(FileTimestamp fileTimestamp, String p) {
                FileTimestamp el = fileTimestamp;
                long fpos = el.from;
                long tpos = el.to;
                long pos = getFileTimestamp(p);
                return pos >= fpos && pos < tpos;
            }

            @Override
            public String toString() {
                return format("{0}-{1}", toDateStr(from), toDateStr(to));
            }

            public String toString2() {
                return format("{0}-{1}", toDateStr2(from), toDateStr2(to));
            }

        }
    }

    public static class MarkOccurrenceResult {
        public String[] args;
        public boolean markOccurrence;

        public static MarkOccurrenceResult markOccurrence(String[] args) {
            MarkOccurrenceResult r = new MarkOccurrenceResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.markOccurrence = true;
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "Mark Occurrence: " + r.markOccurrence);
            } else {
                r.markOccurrence = false;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return last.equals("mk");
        }

        public static String makeMarkLine(String line, String key) {
            String mark = getRepeatingString("^", key.length());
            line = line.replace(key, mark);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c != '^') {
                    sb.append(" ");
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
    }

    public static class ListConditionResult {
        public String[] args;
        public ListCondition listCondition;

        public static ListConditionResult listCondition(String[] args, Params params) throws Exception {
            ListConditionResult r = new ListConditionResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.listCondition = ListCondition.parseListCondition(last, params);
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "List Condition: " + r.listCondition);
            } else {
                r.listCondition = null;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return isInCondition(last);
        }

        private static boolean isInCondition(String last) {
            return last.matches("in\\(.*\\)");
        }

        public static class ListCondition {
            public String dir;
            public String pattern;
            public List<String> fileNames;

            public boolean matches(File file) {
                return matchesListCondition(this, file.getAbsolutePath());
            }

            public static ListCondition parseListCondition(String pattern, Params params) throws Exception {
                if (isInCondition(pattern)) {
                    ListCondition lc = new ListCondition();
                    String dir = cut(pattern, 3, 1);
                    lc.dir = dir;
                    lc.pattern = null;
                    dir = toTARAlias(dir);

                    String from = dir;
                    String filefrom = "*";
                    params = new Params();
                    FilenameFilter filter = Filters.getFilters(filefrom, params);
                    List<File> files = Util.listFiles(new File(from), false, filter, params);

                    List<String> fileNames = new ArrayList<String>();
                    for (File file : files) {
                        fileNames.add(file.getName());
                    }
                    lc.fileNames = fileNames;

                    return lc;
                }
                return null;
            }

            public static boolean matchesListCondition(ListCondition listCondition, String p) {
                ListCondition lc = listCondition;
                String fileName = getFileName(p);
                return lc.fileNames.contains(fileName);
            }

            @Override
            public String toString() {
                return format("in({0})", dir);
            }
        }
    }

    public static class OutputSummaryResult {
        public String[] args;
        public OutputSummary outputSummary;

        public static OutputSummaryResult outputSummary(String[] args) throws Exception {
            OutputSummaryResult r = new OutputSummaryResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.outputSummary = OutputSummary.parseOutputSummary(last);
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "Output Summary: " + r.outputSummary);
            } else {
                r.outputSummary = null;
                r.args = args;
            }
            return r;
        }

        public static void outputSummary(Params params, List<String> dirs) throws Exception {
            if (params.outputSummary != null) {
                params.outputSummary.outputSummary(dirs);
            }
        }

        public static boolean isParam(String last) {
            return isOutputSummary(last);
        }

        private static boolean isOutputSummary(String last) {
            return last.matches("os\\(.*\\)");
        }

        public static class OutputSummary {
            public String dir;

            public void outputSummary(List<String> dirs) throws Exception {
                if (dirs != null && !dirs.isEmpty()) {
                    String osDir = toTARAlias(dir);
                    String osFile = osDir + FILE_SEPARATOR + "summary.txt";
                    appendLines(osFile, dirs);
                }
            }

            public static OutputSummary parseOutputSummary(String pattern) throws Exception {
                if (isOutputSummary(pattern)) {
                    String dir = cut(pattern, 3, 1);
                    OutputSummary os = new OutputSummary();
                    os.dir = dir;
                    return os;
                }
                return null;
            }

            @Override
            public String toString() {
                return format("os({0})", dir);
            }

        }
    }

    public static class GoDirResult {
        public String[] args;
        public GoDir goDir;

        public static GoDirResult goDir(String[] args) {
            GoDirResult r = new GoDirResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.goDir = GoDir.parseGoDir(last);
                r.args = cutLastArg(args);
                if (debug_)
                    log(tab(2) + "Go Dir: " + r.goDir);
            } else {
                r.goDir = null;
                r.args = args;
            }
            return r;
        }

        public static void go(Params params, List<String> dirs, String from) throws Exception {
            GoDir g = params.goDir;
            if (g != null) {
                if (g.go) {
                    if (dirs != null && !dirs.isEmpty()) {
                        List<String> list = new ArrayList<String>();
                        for (String dir : dirs) {
                            if (isFile(dir)) {
                                list.add(toGoLine(getParent(dir)));
                                break;
                            } else {
                                if (dir.contains("\\\\"))
                                    dir = dir.replace("\\\\", "\\");
                                list.add(toGoLine(dir));
                                break;
                            }
                        }
                        setLines(batDir + "agotmp.bat", list);
                    }
                }
                if (g.ago) {
                    List<String> list = new ArrayList<String>();
                    list.add(toExplorerLine(from));
                    setLines(batDir + "agotmp.bat", list);
                }
                if (g.gosub) {
                    if (dirs != null && !dirs.isEmpty()) {
                        List<String> list = new ArrayList<String>();
                        for (String dir : dirs) {
                            dir = subDir(dir, g);
                            if (isFile(dir)) {
                                list.add(toGoLine(getParent(dir)));
                                break;
                            } else {
                                if (dir.contains("\\\\"))
                                    dir = dir.replace("\\\\", "\\");
                                list.add(toGoLine(dir));
                                break;
                            }
                        }
                        setLines(batDir + "agotmp.bat", list);
                    }
                }
                if (g.agosub) {
                    if (dirs != null && !dirs.isEmpty()) {
                        List<String> list = new ArrayList<String>();
                        for (String dir : dirs) {
                            dir = subDir(dir, g);
                            if (isFile(dir)) {
                                list.add(toExplorerLine(getParent(dir)));
                                break;
                            } else {
                                if (dir.contains("\\\\"))
                                    dir = dir.replace("\\\\", "\\");
                                list.add(toExplorerLine(dir));
                                break;
                            }
                        }
                        setLines(batDir + "agotmp.bat", list);
                    }
                }
            }
        }

        private static String subDir(String dir, GoDir g) {
            int times = g.i;
            String odir = dir;
            String p = getParent(dir);
            int i = 0;
            while (p.contains("\\")) {
                String n = getFileName(p);
                if (n.contains(g.sub)) {
                    i++;
                    if (i == times)
                        return p;
                }
                dir = p;
                p = getParent(dir);
            }
            return odir;
        }

        public static boolean isParam(String last) {
            return isGo(last) || isGoSub(last) || isAGo(last) || isAGoSub(last);
        }

        private static boolean isGoSub(String last) {
            return last.startsWith("go") && !isGo(last);
        }

        private static boolean isAGoSub(String last) {
            return last.startsWith("ago") && !isAGo(last);
        }

        private static boolean isGo(String last) {
            return last.equals("go");
        }

        private static boolean isAGo(String last) {
            return last.equals("ago");
        }

        public static class GoDir {
            public boolean go = false;
            public boolean ago = false;
            public boolean gosub = false;
            public boolean agosub = false;
            public String sub;
            public int i = 1;

            public static GoDir parseGoDir(String pattern) {
                if (isGo(pattern)) {
                    GoDir r = new GoDir();
                    r.go = true;
                    return r;
                }
                if (isAGo(pattern)) {
                    GoDir r = new GoDir();
                    r.ago = true;
                    return r;
                }
                if (isGoSub(pattern)) {
                    GoDir r = new GoDir();
                    r.gosub = true;
                    String sub = cut(pattern, "go", null);
                    if (sub.matches(".*\\d")) {
                        r.sub = cutLast(sub, 1);
                        r.i = toInt(subLast(sub, 1));
                    } else {
                        r.sub = sub;
                        r.i = 1;
                    }
                    return r;
                }
                if (isAGoSub(pattern)) {
                    GoDir r = new GoDir();
                    r.agosub = true;
                    String sub = cut(pattern, "ago", null);
                    if (sub.matches(".*\\d")) {
                        r.sub = cutLast(sub, 1);
                        r.i = toInt(subLast(sub, 1));
                    } else {
                        r.sub = sub;
                        r.i = 1;
                    }
                    return r;
                }
                return null;
            }

            @Override
            public String toString() {
                if (go)
                    return "go";
                if (ago)
                    return "ago";
                if (gosub)
                    return "go" + sub + (i > 1 ? i : "");
                if (agosub)
                    return "ago" + sub + (i > 1 ? i : "");
                return null;
            }
        }
    }

    public static class Params {

        public String[] args;
        public boolean recursive = true;
        public ExpandLines expandLines = null;
        public int recursiveLevel = -1;
        public boolean caseSensitive = false;
        public boolean noLineNumber = false;
        public boolean openDir = false;
        public int openDirsCount = 10;
        public boolean openFile = false;
        public int openFilesCount = 10;
        public boolean gbkEncoding = false;
        public boolean keepDir = false;
        public String newFileName = null;
        public boolean noPath = false;
        public boolean fullPath = false;
        public boolean useDot = false;
        public boolean noFileDetail = false;
        public String sortType = null;
        public boolean multipleLines = false;
        public boolean move = false;
        public OperateLines operateLines = null;
        public ZipOperations zipOperations = null;
        public boolean overwrite = false;
        public boolean deleteSame = false;
        public boolean makeSame = false;
        public boolean diffSame = false;
        public FileTimestamp fileTimestamp = null;
        public boolean markOccurrence = false;
        public ListCondition listCondition = null;
        public OutputSummary outputSummary = null;
        public GoDir goDir = null;

        public int getExpandLines() {
            if (expandLines == null) {
                return 0;
            } else {
                if (expandLines.from == 0 && expandLines.to == 1) {
                    return Integer.MAX_VALUE;
                }
                return expandLines.from;
            }
        }

        public boolean listCurrentDir() {
            return recursiveLevel == 0;
        }

        public boolean sortByTime() {
            if (sortType != null) {
                return sortType.equals("t");
            }
            return false;
        }

        public String getEncoding() {
            return gbkEncoding ? "GBK" : "UTF-8";
        }

        public void log() {
            if (debug_) {
                FileUtil.log(tab(4) + "recursive = " + recursive);
                FileUtil.log(tab(4) + "expandLines = " + expandLines);
                FileUtil.log(tab(4) + "recursiveLevel = " + recursiveLevel);
                FileUtil.log(tab(4) + "caseSensitive = " + caseSensitive);
                FileUtil.log(tab(4) + "noLineNumber = " + noLineNumber);
                FileUtil.log(tab(4) + "openDir = " + openDir);
                FileUtil.log(tab(4) + "openFile = " + openFile);
                FileUtil.log(tab(4) + "gbkEncoding = " + gbkEncoding);
            }
        }

        public static Params toParams(String op, String[] args) throws Exception {
            args = sortParamsInArgs(args);
            Params params = new Params();
            int n;
            do {
                n = args.length;
                if (n == 0)
                    break;
                // expand lines
                ExpandLinesResult elr = ExpandLinesResult.expandLines(args);
                if (args.length > elr.args.length) {
                    args = elr.args;
                    if (params.expandLines == null)
                        params.expandLines = elr.expandLines;
                }
                // recursive level
                RecursiveLevelResult rlr = RecursiveLevelResult.recursiveLevel(args);
                if (args.length > rlr.args.length) {
                    args = rlr.args;
                    if (params.recursiveLevel == -1)
                        params.recursiveLevel = rlr.n;
                }
                // no line number
                NoLineNumberResult nlnr = NoLineNumberResult.noLineNumber(args);
                if (args.length > nlnr.args.length) {
                    args = nlnr.args;
                    if (params.noLineNumber == false)
                        params.noLineNumber = nlnr.noLineNumber;
                }
                // case sensitive
                CaseSensitiveResult csr = CaseSensitiveResult.caseSensitive(args);
                if (args.length > csr.args.length) {
                    args = csr.args;
                    if (params.caseSensitive == false)
                        params.caseSensitive = csr.caseSensitive;
                }
                // open dir
                OpenDirResult odr = OpenDirResult.openDir(args);
                if (args.length > odr.args.length) {
                    args = odr.args;
                    if (params.openDir == false) {
                        params.openDir = odr.openDir;
                        params.openDirsCount = odr.openDirsCount;
                    }
                    if (params.openFile == false) {
                        params.openFile = odr.openFile;
                        params.openFilesCount = odr.openFilesCount;
                    }
                }
                // gbk encoding
                GBKEncodingResult ger = GBKEncodingResult.gbkEncoding(args);
                if (args.length > ger.args.length) {
                    args = ger.args;
                    if (params.gbkEncoding == false)
                        params.gbkEncoding = ger.gbkEncoding;
                }
                // keep dir
                KeepDirResult kdr = KeepDirResult.keepDir(args);
                if (args.length > kdr.args.length) {
                    args = kdr.args;
                    if (params.keepDir == false)
                        params.keepDir = kdr.keepDir;
                }
                // new file name
                NewFileNameResult nfnr = NewFileNameResult.newFileName(args);
                if (args.length > nfnr.args.length) {
                    args = nfnr.args;
                    if (params.newFileName == null)
                        params.newFileName = nfnr.newFileName;
                }
                // no path
                NoPathResult npr = NoPathResult.noPath(args);
                if (args.length > npr.args.length) {
                    args = npr.args;
                    if (params.noPath == false)
                        params.noPath = npr.noPath;
                }
                // full path
                FullPathResult fpr = FullPathResult.fullPath(args);
                if (args.length > fpr.args.length) {
                    args = fpr.args;
                    if (params.fullPath == false)
                        params.fullPath = fpr.fullPath;
                }
                // use dot
                UseDotResult udr = UseDotResult.useDot(args);
                if (args.length > udr.args.length) {
                    args = udr.args;
                    if (params.useDot == false)
                        params.useDot = udr.useDot;
                    if (params.noFileDetail == false)
                        params.noFileDetail = udr.noFileDetail;
                }
                // sort type
                SortTypeResult str = SortTypeResult.sortType(args);
                if (args.length > str.args.length) {
                    args = str.args;
                    if (params.sortType == null)
                        params.sortType = str.sortType;
                }
                // multiple lines
                MultipleLinesResult mlr = MultipleLinesResult.multipleLines(args);
                if (args.length > mlr.args.length) {
                    args = mlr.args;
                    if (params.multipleLines == false)
                        params.multipleLines = mlr.multipleLines;
                }
                // move
                MoveResult mvr = MoveResult.move(args);
                if (args.length > mvr.args.length) {
                    args = mvr.args;
                    if (params.move == false)
                        params.move = mvr.move;
                }
                // operate lines
                OperateLinesResult olr = OperateLinesResult.operateLines(args);
                if (args.length > olr.args.length) {
                    args = olr.args;
                    if (params.operateLines == null)
                        params.operateLines = olr.operateLines;
                }
                // zip operations
                ZipOperationsResult zor = ZipOperationsResult.zipOperations(args);
                if (args.length > zor.args.length) {
                    args = zor.args;
                    if (params.zipOperations == null)
                        params.zipOperations = zor.zipOperations;
                }
                // overwrite
                OverwriteResult ovr = OverwriteResult.overwrite(args);
                if (args.length > ovr.args.length) {
                    args = ovr.args;
                    if (params.overwrite == false)
                        params.overwrite = ovr.overwrite;
                }
                // delete same
                DeleteSameResult dsr = DeleteSameResult.deleteSame(args);
                if (args.length > dsr.args.length) {
                    args = dsr.args;
                    if (params.deleteSame == false)
                        params.deleteSame = dsr.deleteSame;
                    if (params.makeSame == false)
                        params.makeSame = dsr.makeSame;
                    if (params.diffSame == false)
                        params.diffSame = dsr.diffSame;
                }
                // file timestamp
                FileTimestampResult ftr = FileTimestampResult.fileTimestamp(args);
                if (args.length > ftr.args.length) {
                    args = ftr.args;
                    if (params.fileTimestamp == null)
                        params.fileTimestamp = ftr.fileTimestamp;
                }
                // mark occurrence
                MarkOccurrenceResult mor = MarkOccurrenceResult.markOccurrence(args);
                if (args.length > mor.args.length) {
                    args = mor.args;
                    if (params.markOccurrence == false)
                        params.markOccurrence = mor.markOccurrence;
                }
                // list condition
                ListConditionResult lcr = ListConditionResult.listCondition(args, params);
                if (args.length > lcr.args.length) {
                    args = lcr.args;
                    if (params.listCondition == null)
                        params.listCondition = lcr.listCondition;
                }
                // output summary
                OutputSummaryResult osr = OutputSummaryResult.outputSummary(args);
                if (args.length > osr.args.length) {
                    args = osr.args;
                    if (params.outputSummary == null)
                        params.outputSummary = osr.outputSummary;
                }
                // go dir
                GoDirResult gdr = GoDirResult.goDir(args);
                if (args.length > gdr.args.length) {
                    args = gdr.args;
                    if (params.goDir == null)
                        params.goDir = gdr.goDir;
                }
            } while (args.length < n);
            params.args = args;
            setDefaultParams(params, op);
            Params.log("after params", args);
            params.log();
            return params;
        }

        public static String[] sortParamsInArgs(String[] args) {
            log("before sort", args);
            List<String> list = arrayToList(args);
            Collections.sort(list, new ParamsSorter());
            args = listToArray(list);
            log("after sort", args);
            return args;
        }

        public static String[] handleFirstParam(String[] args) {
            if (args.length > 0) {
                log("before handle first", args);
                String first = args[0];
                if (isParam(first)) {
                    first = "'" + first + "'";
                    args[0] = first;
                }
                log("after handle first", args);
            }
            return args;
        }

        public static void setDefaultParams(Params params, String op) {
            if (op.equals("ap")) {
                if (params.expandLines == null) {
                    ExpandLines el = new ExpandLines();
                    el.from = 1;
                    el.to = 1000;
                    params.expandLines = el;
                }
            }
        }

        public static void log(String m, String[] args) {
            if (debug_)
                FileUtil.log(format(m + ": ", 30) + connectLines(args, " "));
        }

        public static void log(String m, String line) {
            if (debug_)
                FileUtil.log(format(m + ": ", 30) + line);
        }

        public static boolean isParam(String s) {
            if (ExpandLinesResult.isParam(s))
                return true;
            if (RecursiveLevelResult.isParam(s))
                return true;
            if (NoLineNumberResult.isParam(s))
                return true;
            if (CaseSensitiveResult.isParam(s))
                return true;
            if (OpenDirResult.isParam(s))
                return true;
            if (GBKEncodingResult.isParam(s))
                return true;
            if (KeepDirResult.isParam(s))
                return true;
            if (NewFileNameResult.isParam(s))
                return true;
            if (NoPathResult.isParam(s))
                return true;
            if (UseDotResult.isParam(s))
                return true;
            if (SortTypeResult.isParam(s))
                return true;
            if (MultipleLinesResult.isParam(s))
                return true;
            if (MoveResult.isParam(s))
                return true;
            if (OperateLinesResult.isParam(s))
                return true;
            if (ZipOperationsResult.isParam(s))
                return true;
            if (OverwriteResult.isParam(s))
                return true;
            if (DeleteSameResult.isParam(s))
                return true;
            if (FileTimestampResult.isParam(s))
                return true;
            if (MarkOccurrenceResult.isParam(s))
                return true;
            if (ListConditionResult.isParam(s))
                return true;
            if (OutputSummaryResult.isParam(s))
                return true;
            if (GoDirResult.isParam(s))
                return true;
            return false;
        }

        public boolean isExp() {
            return zipOperations != null && zipOperations.isExp();
        }

        public boolean isImp() {
            return zipOperations != null && zipOperations.isImp();
        }

        public String getExpTo() {
            return zipOperations.to;
        }

        public String getImpFrom() {
            return zipOperations.to;
        }

        public boolean isSql() {
            return zipOperations != null && zipOperations.isSql();
        }
    }

    public static class ParamsSorter implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            Integer i1 = toParamPriority(o1);
            Integer i2 = toParamPriority(o2);
            return i1.compareTo(i2);
        }

        private int toParamPriority(String o1) {
            if (Params.isParam(o1))
                return 1;
            return 0;
        }

    }

    public static class ParamsSorterDebug implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            Integer i1 = toParamPriority(o1);
            Integer i2 = toParamPriority(o2);
            return i1.compareTo(i2);
        }

        private int toParamPriority(String o1) {
            if (o1.equals("-v"))
                return 10;
            if (o1.equals("-va"))
                return 9;
            if (o1.equals("-d"))
                return 8;
            if (o1.matches("-lt\\d*"))
                return 7;
            if (o1.equals("o"))
                return 6;
            if (o1.equals("sl"))
                return 5;
            return 0;
        }

    }

    public static class OutputToFile {
        public static String[] outputToFile(String[] args, String n) throws Exception {
            boolean outputToFile = false;
            boolean slient = false;
            String last = getLastArg(args);
            if (last != null) {
                if (last.equalsIgnoreCase("o")) {
                    outputToFile = true;
                    args = cutLastArg(args);
                } else if (last.equalsIgnoreCase("sl")) {
                    outputToFile = true;
                    slient = true;
                    args = cutLastArg(args);
                }
                if (outputToFile) {
                    String dir = "D:\\alogs\\";
                    mkdirs(dir);
                    String file = dir + n + ".log";
                    if (!slient)
                        log("output to: " + file);
                    outputToFile(file);
                    if (!slient) {
                        String line = toEditLine(file);
                        setLines(batDir + "aoutputtmp.bat", toList(line));
                    }
                }
            }
            return args;
        }

        public static void outputToFile(String n) throws FileNotFoundException {
            outputToFile_ = true;
            System.setOut(new PrintStream(n));
        }
    }

    public static class DBOperations {

        public static final int SIZE_BYTES = 20;
        public static final int SIZE_STRING = 30;

        public static boolean isDB(String fromdir) {
            if (fromdir.startsWith("m:"))
                return true;
            if (fromdir.startsWith("o:"))
                return true;
            return false;
        }

        public static void listTables(String fromdir, String filefrom, Params params) throws Exception {
            Driver driver = parseDriver(fromdir);
            if (params.isSql()) {
                log("execute from: " + driver.url);
                executeSqlInTables(driver, params);
                return;
            }
            log("list from: " + driver.url);
            List<String> tables = listTableNames(driver, filefrom, params);
            for (String tableName : tables) {
                log(tab(2) + tableName);
                exportTable(driver, tableName, params);
                listColumns(driver, tableName, params);
            }
        }

        private static void exportTable(Driver driver, String tableName, Params params) throws Exception {
            if (params.isImp()) {
                // clean table
                log(2, "clean table: " + tableName);
                executeSql(driver, "delete from " + tableName);
                // import table
                log(2, "import table: " + tableName);
                importTable(driver, tableName, params);
            }
            if (params.isExp() || params.isImp()) {
                findInTable(driver, tableName, "`", params);
            }
        }

        private static void importTable(Driver driver, String tableName, Params params) throws Exception {
            Connection conn = null;
            try {
                conn = toConnection(driver);
                List<List<String>> lists = getImportData(params);
                List<String> headers = lists.remove(0);
                List<String> types = lists.remove(0);
                String sql = toImportSql(tableName, headers);
                doImportTable(conn, tableName, sql, lists, headers, types);
            } finally {
                if (conn != null)
                    conn.close();
            }
        }

        private static void doImportTable(Connection conn, String tableName, String sql, List<List<String>> lists,
                List<String> headers, List<String> types) throws Exception {
            for (int k = 0; k < lists.size(); k++) {
                List<String> columnValues = lists.get(k);
                List<Object> columnObjects = toObjects(columnValues, types);

                PreparedStatement stmt = conn.prepareStatement(sql);
                for (int i = 0; i < columnObjects.size(); i++) {
                    int t = toInt(types.get(i));
                    Object v = columnObjects.get(i);
                    setObject(stmt, i + 1, v, t);
                }
                stmt.executeUpdate();
                stmt.close();
                log(4, "insert line: " + (k + 1));
            }
        }

        private static void setObject(PreparedStatement stmt, int i, Object v, int t) throws Exception {
            if (t == 2004) {
                byte[] buf = (byte[]) v;
                InputStream is = new ByteArrayInputStream(buf);
                stmt.setBinaryStream(i, is);
            } else {
                stmt.setObject(i, v, t);
            }
        }

        private static Object toObject(String v, int t) throws Exception {
            if (t == 2) {
                return Long.valueOf(v);
            }
            if (t == 2004) {
                return Hex.decodeHex(v.toCharArray());
            }
            if (v.equals("null"))
                return null;
            return v;
        }

        private static List<Object> toObjects(List<String> columnValues, List<String> types) throws Exception {
            List<Object> list = new ArrayList<Object>();
            for (int i = 0; i < columnValues.size(); i++) {
                int t = toInt(types.get(i));
                String v = columnValues.get(i);
                Object o = toObject(v, t);
                list.add(o);
            }
            return list;
        }

        private static String toImportSql(String tableName, List<String> headers) {
            // parameters
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (int i = 0; i < headers.size(); i++) {
                sb.append("?");
                if (i < headers.size() - 1)
                    sb.append(",");
            }
            sb.append(")");
            String parameters = sb.toString();
            String sql = "insert into " + tableName + " values " + parameters;
            return sql;
        }

        private static List<List<String>> getImportData(Params params) throws Exception {
            String file = params.getImpFrom();
            List<String> lines = getLines(file);
            lines.remove(0); // table name
            List<List<String>> lists = new ArrayList<List<String>>();
            for (String line : lines) {
                lists.add(splitToList(line, " "));
            }
            return lists;
        }

        private static void executeSqlInTables(Driver driver, Params params) throws Exception {
            ZipOperations zo = params.zipOperations;
            if (zo != null) {
                if (zo.sql) {
                    String sql = zo.to;
                    if (sql.startsWith("select")) {
                        Connection conn = null;
                        try {
                            conn = toConnection(driver);
                            List<List<String>> lists = toResults(conn, sql);
                            lists = filterRecords(lists, "*", params);
                            printRecords(getTableNameFromSql(sql), lists, "*", params);
                        } finally {
                            if (conn != null)
                                conn.close();
                        }
                    } else {
                        Connection conn = null;
                        try {
                            conn = toConnection(driver);
                            executeSql(conn, sql);
                            log(2, "execute successfully: " + sql);
                        } finally {
                            if (conn != null)
                                conn.close();
                        }
                    }
                }
            }
        }

        private static String getTableNameFromSql(String s) {
            String n = cut(s, "from", null).trim();
            if (n.contains(" "))
                n = cut(n, null, " ").trim();
            return n;
        }

        public static void printTables(String fromdir, String filefrom, Params params) throws Exception {
            Driver driver = parseDriver(fromdir);
            log("print from: " + driver.url);
            List<String> tables = listTableNames(driver, filefrom, params);
            for (String tableName : tables) {
                printTable(driver, tableName, params);
            }
        }

        public static void findInTables(String fromdir, String filefrom, String from, Params params) throws Exception {
            Driver driver = parseDriver(fromdir);
            log("find from: " + driver.url);
            List<String> tables = listTableNames(driver, filefrom, params);
            for (String tableName : tables) {
                findInTable(driver, tableName, from, params);
            }
        }

        private static List<String> listTableNames(Driver driver, String filefrom, Params params) throws Exception {
            Connection conn = toConnection(driver);
            try {
                List<String> tables = getAllTableNames(conn);
                tables = filterTables(tables, filefrom, params);
                Collections.sort(tables);
                return tables;
            } finally {
                if (conn != null)
                    conn.close();
            }
        }

        private static void printTable(Driver driver, String tableName, Params params) throws Exception {
            findInTable(driver, tableName, "*", params);
        }

        private static void findInTable(Driver driver, String tableName, String from, Params params) throws Exception {
            Connection conn = toConnection(driver);
            try {
                String sql = toSQL(conn, tableName, from, params);
                List<List<String>> lists = toResults(conn, sql);
                lists = filterRecords(lists, from, params);
                printRecords(tableName, lists, from, params);
                expRecords(tableName, lists, from, params);
            } finally {
                if (conn != null)
                    conn.close();
            }
        }

        private static void listColumns(Driver driver, String tableName, Params params) throws Exception {
            // 'c' means print columns
            if (params.caseSensitive) {
                Connection conn = null;
                Statement stmt = null;
                ResultSet rs = null;
                try {
                    conn = toConnection(driver);
                    String sql = "select * from " + tableName;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    ResultSetMetaData rsmd = rs.getMetaData();
                    List<SQLColumn> cols = new ArrayList<SQLColumn>();
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        String colname = rsmd.getColumnName(i);
                        int itype = rsmd.getColumnType(i);
                        int precision = rsmd.getPrecision(i);
                        SQLColumn col = new SQLColumn();
                        SQLType type = JDBCType.valueOf(itype);
                        col.name = colname;
                        col.type = type.getName();
                        col.precision = precision;
                        cols.add(col);
                    }
                    int n = SQLColumn.listSize();
                    // size
                    List<Integer> sizes = new ArrayList<Integer>();
                    for (int i = 0; i < n; i++) {
                        List<Integer> sl = new ArrayList<Integer>();
                        for (SQLColumn col : cols) {
                            List<String> l = col.toList();
                            sl.add(getWordCount(l.get(i)));
                        }
                        Integer size = Collections.max(sl);
                        sizes.add(size);
                    }
                    // print
                    for (SQLColumn col : cols) {
                        List<String> l = col.toList();
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < n; i++) {
                            int size = sizes.get(i);
                            String v = l.get(i);
                            sb.append(formatColumn(v, size + 2, true));
                        }
                        String lineStr = sb.toString();
                        log(4, lineStr);
                    }
                } finally {
                    if (rs != null)
                        rs.close();
                    if (stmt != null)
                        stmt.close();
                    if (conn != null)
                        conn.close();
                }
            }
        }

        private static Driver parseDriver(String fromdir) {
            Driver driver = new Driver();
            List<String> list = splitToList(fromdir, ":");
            // driver
            String type = subFirst(list);
            list = cutFirst(list);
            if (type.equals("m"))
                driver.driver = "com.mysql.jdbc.Driver";
            else
                driver.driver = "oracle.jdbc.OracleDriver";
            if (debug_)
                log("driver = " + driver.driver);
            // url
            String ip = subFirst(list);
            list = cutFirst(list);
            if (ip.equals("l"))
                ip = "localhost";
            String name = subFirst(list);
            list = cutFirst(list);
            driver.url = format("jdbc:mysql://{0}:3306/{1}?autoReconnect=true", ip, name);
            if (debug_)
                log("url = " + driver.url);
            // user
            String user = subFirst(list);
            list = cutFirst(list);
            if (user.equals("r"))
                user = "root";
            driver.user = user;
            if (debug_)
                log("user = " + driver.user);
            // password
            String password = subFirst(list);
            list = cutFirst(list);
            driver.password = password;
            if (debug_)
                log("password = " + driver.password);
            return driver;
        }

        private static String toSQL(Connection conn, String tableName, String from, Params params) throws Exception {
            String sql = format("select * from {0}", tableName);

            // filters

            // order by
            String sortType = params.sortType;
            if (sortType != null) {
                boolean desc = isDesc(sortType);
                if (desc)
                    sortType = cutLast(sortType, 5);
                sql += " order by " + findColumn(conn, tableName, sortType);
                if (desc)
                    sql += " desc";
            }
            if (debug_)
                log("sql: " + sql);
            return sql;
        }

        private static boolean isDesc(String sortType) {
            return sortType.endsWith("_desc");
        }

        private static String findColumn(Connection conn, String tableName, String sortType) throws Exception {
            List<String> headers = getHeaders(conn, tableName);
            return findInList(headers, sortType);
        }

        private static List<String> getHeaders(Connection conn, String tableName) throws Exception {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("select * from " + tableName);
                List<String> headers = toHeader(rs);
                return headers;
            } finally {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            }
        }

        private static List<String> filterTables(List<String> tables, String filefrom, Params params) {
            Filters filters = Filters.getFilters(filefrom, params);
            List<String> list = new ArrayList<String>();
            for (String tableName : tables) {
                if (filters.accept(tableName, 0)) {
                    list.add(tableName);
                }
            }
            return list;
        }

        private static List<List<String>> filterRecords(List<List<String>> lists, String from, Params params) {
            Filters filters = Filters.getFilters(from, params);
            List<List<String>> lists2 = new ArrayList<List<String>>();
            lists2.add(lists.get(0));
            lists2.add(lists.get(1));
            int n = lists.size();
            if (n > 2) {
                for (int i = 2; i < lists.size(); i++) {
                    List<String> list = lists.get(i);
                    if (acceptRecord(list, filters, params)) {
                        lists2.add(list);
                    }
                }
            }
            return lists2;
        }

        private static boolean acceptRecord(List<String> list, Filters filters, Params params) {
            String lineStr = connectLines(list, " ");
            return filters.accept(lineStr, 0);
        }

        private static void printRecords(String tableName, List<List<String>> lists, String from, Params params)
                throws Exception {
            List<String> lines = toPrintRecords(tableName, lists, from, params);
            if (!lines.isEmpty()) {
                String title = subFirst(lines);
                lines = cutFirst(lines);
                log(2, title);
                for (String line : lines) {
                    log(4, line);
                }
            }
        }

        private static void expRecords(String tableName, List<List<String>> lists, String from, Params params)
                throws Exception {
            if (params.isExp()) {
                List<String> lines = toExpRecords(tableName, lists, from, params);
                if (!lines.isEmpty()) {
                    setLines(params.getExpTo(), lines);
                    log(2, "exp to: " + params.getExpTo());
                }
            }
        }

        private static List<String> toPrintRecords(String tableName, List<List<String>> lists, String from,
                Params params) throws Exception {
            lists = toPrintLists(lists);
            int n = lists.get(0).size();
            List<String> lines = new ArrayList<String>();
            // print table name
            int count = lists.size() - 2;
            if (!from.equals("*") && count == 0)
                return lines;
            lines.add(format("{0} [{1}]", tableName, count));
            // size
            List<Integer> sizes = new ArrayList<Integer>();
            for (int i = 0; i < n; i++) {
                List<Integer> sl = new ArrayList<Integer>();
                for (List<String> l : lists) {
                    sl.add(getWordCount(l.get(i)));
                }
                Integer size = Collections.max(sl);
                sizes.add(size);
            }
            // print
            for (int k = 0; k < lists.size(); k++) {
                List<String> l = lists.get(k);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < n; i++) {
                    int size = sizes.get(i);
                    String v = l.get(i);
                    sb.append(formatColumn(v, size + 2, true));
                }
                String lineStr = sb.toString();
                if (k != 1)
                    lines.add(lineStr);
            }
            return lines;
        }

        private static List<String> toExpRecords(String tableName, List<List<String>> lists, String from, Params params)
                throws Exception {
            int n = lists.get(0).size();
            List<String> lines = new ArrayList<String>();
            // print table name
            int count = lists.size() - 2;
            if (!from.equals("*") && count == 0)
                return lines;
            lines.add(format("{0} [{1}]", tableName, count));
            // size
            List<Integer> sizes = new ArrayList<Integer>();
            for (int i = 0; i < n; i++) {
                List<Integer> sl = new ArrayList<Integer>();
                for (List<String> l : lists) {
                    sl.add(getWordCount(l.get(i)));
                }
                Integer size = Collections.max(sl);
                sizes.add(size);
            }
            // print
            for (int k = 0; k < lists.size(); k++) {
                List<String> l = lists.get(k);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < n; i++) {
                    int size = sizes.get(i);
                    String v = l.get(i);
                    sb.append(formatColumn(v, size + 2, true));
                }
                String lineStr = sb.toString();
                lines.add(lineStr);
            }
            return lines;
        }

        private static List<List<String>> toPrintLists(List<List<String>> lists) throws Exception {
            int count = lists.size() - 2;
            if (count > 0) {
                List<String> header = lists.get(0);
                List<String> type = lists.get(1);
                List<List<String>> lists2 = new ArrayList<List<String>>();
                lists2.add(header);
                lists2.add(type);
                for (int i = 2; i < lists.size(); i++) {
                    List<String> lineList = lists.get(i);
                    lists2.add(toPrintList(header, type, lineList));
                }
                return lists2;
            }
            return lists;
        }

        private static List<String> toPrintList(List<String> header, List<String> types, List<String> lineList)
                throws Exception {
            int n = header.size();
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < n; i++) {
                int type = toInt(types.get(i));
                String v = lineList.get(i);
                list.add(toPrintValueString(v, type));
            }
            return list;
        }

        private static String toPrintValueString(String v, int type) throws Exception {
            switch (type) {
            case Types.BLOB:
            case Types.CLOB:
            case Types.LONGVARBINARY:
            case Types.LONGVARCHAR:
            case Types.VARBINARY:
            case Types.BINARY:
                if (v.length() > SIZE_BYTES) {
                    byte[] bytes = Hex.decodeHex(v.toCharArray());
                    v = format("{0} [{1}]", atMost(v, SIZE_BYTES), bytes.length);
                }
                break;
            default:
                if (v.length() > SIZE_STRING && !outputToFile_)
                    v = format("{0} [{1}]", atMost(v, SIZE_STRING), v.length());
                break;
            }
            return v;
        }

        private static List<String> getAllTableNames(Connection conn) throws Exception {
            List<String> tables = new ArrayList<String>();
            DatabaseMetaData dbMetaData = conn.getMetaData();
            String[] types = { "TABLE" };
            ResultSet tabs = dbMetaData.getTables(null, null, null, types);
            while (tabs.next()) {
                tables.add((String) tabs.getObject("TABLE_NAME"));
            }
            return tables;
        }

        private static Connection toConnection(Driver driver) throws Exception {
            Class.forName(driver.driver);
            Connection conn = DriverManager.getConnection(driver.url, driver.user, driver.password);
            return conn;
        }

        private static List<List<String>> toResults(Connection conn, String sql) throws Exception {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                List<List<String>> lists = new ArrayList<List<String>>();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                List<String> headers = toHeader(rs);
                List<String> types = toType(rs);
                lists.add(headers);
                lists.add(types);
                while (rs.next()) {
                    List<String> line = toLine(rs, types);
                    lists.add(line);
                }
                return lists;
            } finally {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
            }
        }

        private static void executeSql(Driver driver, String sql) throws Exception {
            Connection conn = toConnection(driver);
            try {
                executeSql(conn, sql);
            } finally {
                if (conn != null)
                    conn.close();
            }
        }

        private static void executeSql(Connection conn, String sql) throws Exception {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.execute(sql);
            } finally {
                if (stmt != null)
                    stmt.close();
            }
        }

        private static List<String> toHeader(ResultSet rs) throws Exception {
            ResultSetMetaData md = rs.getMetaData();
            int n = md.getColumnCount();
            List<String> list = new ArrayList<String>();
            for (int i = 1; i <= n; i++) {
                String cn = md.getColumnName(i);
                list.add(cn);
            }
            return list;
        }

        private static List<String> toType(ResultSet rs) throws Exception {
            ResultSetMetaData md = rs.getMetaData();
            int n = md.getColumnCount();
            List<String> list = new ArrayList<String>();
            for (int i = 1; i <= n; i++) {
                String cn = "" + md.getColumnType(i);
                list.add(cn);
            }
            return list;
        }

        private static List<String> toLine(ResultSet rs, List<String> types) throws Exception {
            ResultSetMetaData md = rs.getMetaData();
            int n = md.getColumnCount();
            List<String> list = new ArrayList<String>();
            for (int i = 1; i <= n; i++) {
                Object o = rs.getObject(i);
                int t = toInt(types.get(i - 1));
                String v = toValueString(o, t);
                list.add(v);
            }
            return list;
        }

        private static String toValueString(Object o, int t) throws Exception {
            if (o == null)
                return "null";
            if (o instanceof Date) {
                return sdf4.format(o);
            } else if (o instanceof byte[]) {
                byte[] bytes = (byte[]) o;
                String s = new String(Hex.encodeHex(bytes));
                return s;
            } else if (o instanceof Blob) {
                Blob b = (Blob) o;
                byte[] bytes = b.getBytes((long) 1, (int) b.length());
                String s = new String(Hex.encodeHex(bytes));
                return s;
            } else {
                String s = o.toString();
                return s;
            }
        }

        private static String atMost(String s, int n) {
            if (s.length() > n)
                return subFirst(s, n) + "...";
            else
                return s;
        }

        public static class Driver {
            public String driver;
            public String url;
            public String user;
            public String password;
        }

        public static class SQLColumn {
            public String name;
            public String type;
            public int precision;

            public List<String> toList() {
                List<String> list = new ArrayList<String>();
                list.add(name);
                list.add(getType());
                return list;
            }

            private String getType() {
                String type = this.type;
                if (match(type, "longvarchar"))
                    type = "CLOB";
                if (match(type, "longvarbinary"))
                    type = "BLOB";
                if (match(type, "int"))
                    return type;
                if (match(type, "time"))
                    return type;
                if (precision < Integer.MAX_VALUE) {
                    return format("{0}({1})", type, precision);
                }
                return type;
            }

            private boolean match(String type, String s) {
                return containsIgnoreCase(type, s);
            }

            public static int listSize() {
                return 2;
            }
        }
    }
}
