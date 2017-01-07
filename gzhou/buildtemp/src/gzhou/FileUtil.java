package gzhou;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
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

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.vitria.domainservice.util.DOMUtil;

import gzhou.FileUtil.ExpandLinesResult.ExpandLines;
import gzhou.FileUtil.FileTimestampResult.FileTimestamp;
import gzhou.FileUtil.OperateLinesResult.OperateLines;
import gzhou.FileUtil.OperateLinesResult.OperateLinesUtil;
import gzhou.FileUtil.ZipOperationsResult.ZipOperations;

public class FileUtil extends Util implements Constants {

    private static List<String> paOps_ = new ArrayList<String>();
    private static Map<String, String> paOpTypes_ = new HashMap<String, String>();
    public static boolean debug_ = false;
    public static boolean debug2_ = false;

    static {
        PAOperations.initPA();
    }

    public static void main(String[] args) throws Exception {
        if (args[0].equals("help")) { // fu
            System.out.println("fuc:     clean");
            System.out.println("fucas:   copyAppSrc");
            System.out.println("furas:   removeAppSrc");
            System.out.println("fucmg:   copyMigration");
            System.out.println("fut:     translate");
            System.out.println("fub:     generateAllBatFiles");
            System.out.println("fubc:    generateDevBatFiles");
            System.out.println("fubp:    generatePrivateBranchBatFiles");
            System.out.println("fubnc:   generateAllBatFilesNoCommon");
            System.out.println("fubncf:  generateAllBatFilesNoCommonOnlyFile");
            System.out.println("fubdnc:  generateDevBatFilesNoCommon");
            System.out.println("fubpnc:  generatePrivateBranchBatFilesNoCommon");
            System.out.println("fubk:    generateKstBatFiles");
            System.out.println("fug:     gettersetter");
            System.out.println("fus:     generateCreateUserSql");
            System.out.println("func:    generateNCTemplate");
            System.out.println("fufp:    backUpFeedPublisher");
            System.out.println("fufpb:   overwriteFeedPublisher");
            System.out.println("fusast:  updateSastFile");
            System.out.println("fuwf:    watchFile");
            System.out.println("dfjs:    generateJSFileForDataflowComponent");
            System.out.println("futol:   toOneLine");
            System.out.println("gadd:    addSearchAndReplace");
            System.out.println("newbat:  newBat");
            System.out.println("badd:    addBat");
            System.out.println("tadd:    addTypeAndRunItem");
            System.out.println("tdel:    delTypeAndRunItem");
            System.out.println("flist:   listFiles");
            System.out.println("jlist:   listJavaClasses");
            System.out.println("godir:   goDir");
            System.out.println("sql:     sql");
            System.out.println("taj:     taj"); // ta for sa1,2,3
            System.out.println("tam:     tam"); // ta for yoda_main
            System.out.println("tas:     tas"); // ta for yoda_sjb
            System.out.println("tar:     tar"); // ta rename
            System.out.println("p2st:    p2st"); // patch to dstf
            System.out.println("palias:  palias"); // print tar alias
            System.out.println("git:     git"); // git
            System.out.println("custom:  custom"); // custom
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
                System.out.println("dfjs <Component XML File>");
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
                System.out.println("mkdir: " + file);
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

        System.out.println("increase time " + sec + " seconds in log file: " + file);
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
            System.out.println("add line: " + line);
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
                    System.out.println("remove line: " + line);
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
        System.out.println("translation from " + from + " to " + to + " is done.");
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
            System.out.println("Read folders from " + desktopDir + stFile);
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
            System.out.println("File " + desktopDir + stFile + " does not exist.");
        }

        if (withCommon) {
            System.out.println("Add common folders.");
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
        // System.out.println("Sort folders.");

        List<String> list3 = new ArrayList<String>();
        String[] arr = list2.toArray(new String[list2.size()]);
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].startsWith(arr[i]))
                    list3.add(arr[j]);
            }
        }
        list2.removeAll(list3);
        System.out.println("Combine folders.");

        // remove devtests
        if (dev) {
            List<String> list4 = new ArrayList<String>();
            for (String s : list2) {
                if (s.contains("devtests"))
                    list4.add(s);
            }
            list2.removeAll(list4);
            System.out.println("Remove devtests folders.");
        }

        System.out.println("Folders are: ");
        StringBuilder sb = new StringBuilder();
        for (String s : list2) {
            System.out.println("    " + s);
            sb.append(s);
            sb.append(" ");
        }
        String folders = sb.toString().trim();

        // st
        batFile = dir + prefix + "st.bat";
        System.out.println("Generate: " + batFile);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
        out.println("@echo off");
        out.println("call yodadir");
        out.println("call svn st " + folders);
        out.close();

        // up
        batFile = dir + prefix + "up.bat";
        System.out.println("Generate: " + batFile);
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
        out.println("@echo off");
        out.println("call yodadir");
        out.println("call svn up " + folders);
        out.close();

        // sd
        batFile = dir + prefix + "sd.bat";
        System.out.println("Generate: " + batFile);
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
        out.println("@echo off");
        out.println("call yodadir");
        out.println("call svn diff " + folders);
        out.close();

        // stf
        batFile = dir + prefix + "stf.bat";
        System.out.println("Generate: " + batFile);
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
        out.println("@echo off");
        out.println("call yodadir");
        out.println("call svn st " + folders + " > \"" + desktopDir + "yodast.txt\"");
        out.close();

        // sdf
        batFile = dir + prefix + "sdf.bat";
        System.out.println("Generate: " + batFile);
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
        out.println("@echo off");
        out.println("call yodadir");
        out.println("call svn diff " + folders + " > \"" + desktopDir + "yoda.patch\"");
        out.println("if \"%mdf%\"==\"\" call explorer \"" + desktopDir + "yoda.patch\"");
        out.close();

        // sc
        batFile = dir + prefix + "sc.bat";
        System.out.println("Generate: " + batFile);
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(batFile)));
        out.println("@echo off");
        out.println("call yodadir");
        out.println("call svn commit " + folders + " -m %1 ");
        out.close();

        // sr
        batFile = dir + prefix + "sr.bat";
        System.out.println("Generate: " + batFile);
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
        System.out.println("Generate: " + batFile);
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
        System.out.println("Generate: " + batFile);
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
                System.out.println("Generate: " + batFile);
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
                System.out.println("Generate: " + batFile);
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
            System.out.println("Generate: " + batFile);
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
            System.out.println("Generate: " + batFile);
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
            System.out.println("Generate: " + batFile);
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
            System.out.println("Generate: " + batFile);
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
                System.out.println("Generate: " + batFile);
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
                System.out.println("Generate: " + batFile);
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
        BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(desktopDir + "translate.txt")));
        List<String> list = new ArrayList<String>();
        String line;
        String s = "";
        while ((line = in.readLine()) != null) {
            s = line;
            while (true) {
                if (line.contains("_")) {
                    int i = line.lastIndexOf("_");
                    String next = line.substring(i + 1, i + 2);
                    if (next.equals("(") || next.equals(")") || (next.equals(";") && !line.trim().startsWith("return")
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
        System.out.println("translation for getter and setter is done.");
    }

    public static void generateCreateUserSql() throws Exception {
        File file = new File(desktopDir + "user.sql");
        if (file.exists()) {
            System.out.println("Delete: " + file.getAbsolutePath());
            file.delete();
        }
        generateCreateUserSql("zgf_domainds");
        generateCreateUserSql("zgf_m3ods1");
        generateCreateUserSql("zgf_m3ods2");
        generateCreateUserSql("zgf_migration");
    }

    public static void generateNCTemplate() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(
                "C:\\zhou\\yoda\\unbundled\\apps\\activity_stream\\server\\libs\\src\\engine\\com\\vitria\\as\\ncgenerator.xsl")));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
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

        System.out.println("NCTemplate.java is generated from ncgenerator.xsl.");

    }

    public static void generateViewTemplate() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(
                "C:\\zhou\\yoda\\unbundled\\apps\\activity_stream\\server\\libs\\src\\engine\\com\\vitria\\as\\instance_view.xml")));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
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

        System.out.println("ViewTemplate.java is generated from instance_view.xml.");

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
        copyFile("C:\\Workflow-G\\workflow bug fixing\\2012-12-11 Hadoop\\feed_publisher\\modified\\FeedPublisher.java",
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
            System.out.println("disable sast: " + fromkey);
        else
            System.out.println("enable sast: " + fromkey);
    }

    public static void watchFile(String file) throws Exception {
        int i = 0;
        List<String> lines = getLinesNoEx(file);
        while (true) {
            if (lines.size() > i) {
                for (int j = i; j < lines.size(); j++) {
                    System.out.println(lines.get(j));
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
        System.out.println(" ");
        System.out.println(" ");
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
        System.out.println("generate: " + newfile);
    }

    public static void addSearchAndReplace(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("gadd <suffix> <dir>");
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
        System.out.println("generate: " + filePath);
    }

    public static void newBat(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("newbat <batName> <callBatName>");
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
        System.out.println("generate: " + f);
    }

    public static void addBat(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("badd <batName> <lineStrings>");
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
            System.out.println("tadd <name> <command>");
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
        System.out.println("add typeandrun item: " + line);
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
            System.out.println("tdel <name>");
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
            System.out.println("del typeandrun item: " + line);
        } else {
            System.out.println("typeandrun item not found: " + name);
        }
    }

    public static void listFiles(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("flist <dir> <-t timestamp>");
            System.out.println("  - timestamp: list files after this timestamp. like: 2015-01-01 10:00:00");
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

            System.out.println(file.getAbsolutePath() + "   " + sdf4.format(new Date(file.lastModified())));
            if (viewFileContent) {
                List<String> lines = getLines(file.getAbsolutePath(), viewLines);
                int i = 0;
                for (String line : lines) {
                    System.out.println("    " + (++i) + ": " + line);
                }
            }
        }

    }

    public static void listJavaClasses(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("jlist <dir> <className>");
            return;
        }
        String dir = args[1];
        dir = toTARAlias(dir);
        String className = fixClassName(args[2]);
        List<String> javaClasses = listJavaClasses(dir, className);
        for (String c : javaClasses) {
            System.out.println(c);
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
            System.out.println("godir <alias>");
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
                System.out.println("ERROR: alias not found: " + godir);
            }
        }
    }

    public static void sql(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("sql <type> <sql>");
            return;
        }

        String type = args[1];
        String sql = args[2];

        Properties p = getProperties(sqlDir + "driver.properties");
        String driver = p.getProperty(type + ".driver");
        String url = p.getProperty(type + ".url");
        String user = p.getProperty(type + ".user");
        String password = p.getProperty(type + ".password");
        System.out.println(format("execute \"{0}\" on \"{1}\".", sql, url));

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
            System.out.println(output);
        } else {
            stmt.executeUpdate(sql);
            System.out.println("execute successfully.");
        }
        stmt.close();
        conn.close();
    }

    public static void taj(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("taj <alias>");
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
            System.out.println("tam <alias>");
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
            out.println("call ta " + alias + "m " + path.replace("D:\\jedi\\yoda", "D:\\jedi\\yoda_main"));
            out.close();
        }
    }

    public static void tas(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("tas <alias>");
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
            out.println("call ta " + alias + "s " + path.replace("D:\\jedi\\yoda", "D:\\jedi\\yoda_sjb"));
            out.close();
        }
    }

    public static void tar(String[] args) throws Exception {
        if (args.length < 4) {
            System.out.println("tar <aliasFrom> <aliasTo>");
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
            System.out.println("palias <type> <args>");
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
        if (type.equals("print")) {
            PAOperations.paPrint(args);
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
            System.out.println("git <type> <args>");
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
                    if (line.startsWith("deleted:    ")) {
                        line = cutFirst(line, 12);
                        list.add("call git rm " + line);
                    } else {
                        list.add("call git add " + line);
                    }
                }
            }
            setLines(batDir + "agitaddtmp.bat", list);
        }
    }

    public static void custom(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("custom <type> <args>");
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

    }

    private static String fixPath(String path) {
        if (path.contains("\""))
            path = path.replace("\"", "\"\"");
        return path;
    }

    public static String toTARAlias(String p) throws Exception {
        p = unwrapTARAlias(p);
        List<String> list = getLines2(TYPEANDRUN_CONFIG);
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

            if (p.equals(tarAlias)) {
                tarPath = fixTarPath(tarPath);
                return tarPath;
            } else if (p.startsWith(tarAlias + "/")) {
                tarPath = fixTarPath(tarPath);
                String sub = cutFirst(p, tarAlias.length() + 1);
                sub = sub.replace("/", FILE_SEPARATOR);
                return toTARPath(tarPath, sub);
            } else if (p.startsWith(tarAlias + FILE_SEPARATOR)) {
                tarPath = fixTarPath(tarPath);
                String sub = cutFirst(p, tarAlias.length() + 1);
                return toTARPath(tarPath, sub);
            }
        }
        return p;
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

    private static String unwrapTARAlias(String p) {
        if (p.startsWith("'") && p.endsWith("'"))
            p = cut(p, 1, 1);
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
        System.out.println(line);
        if (enums != null) {
            String[] items = enums.split(",");
            for (String item : items) {
                String itemKey = item.substring(0, item.indexOf("="));
                String itemLabel = item.substring(item.indexOf("=") + 1, item.length());
                line = format("        \"{0}.{1}.label\": \"{2}\",", name, itemKey, itemLabel);
                lines.add(line);
                System.out.println(line);
            }
        }
        line = format("        \"{0}.desc\": \"{1}\"" + comma, name, description);
        lines.add(line);
        System.out.println(line);
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
        System.out.println(line);
        if (enums != null) {
            String[] items = enums.split(",");
            for (String item : items) {
                String itemKey = item.substring(0, item.indexOf("="));
                String itemLabel = item.substring(item.indexOf("=") + 1, item.length());
                line = format("          \"{0}.{1}.label\": \"{2}麒麟\",", name, itemKey, itemLabel);
                lines.add(line);
                System.out.println(line);
            }
        }
        line = format("          \"{0}.desc\": \"{1}麒麟\"" + comma, name, description);
        lines.add(line);
        System.out.println(line);
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
                        System.out.println("delete: " + file.getAbsolutePath());
                        file.delete();
                    }
                } else {
                    System.out.println("delete: " + file.getAbsolutePath());
                    file.delete();
                }
            }
        }
        if (suffix == null) {
            if (keepRoot && root.equals(from)) {
                // keep root
            } else {
                System.out.println("delete: " + fromFile.getAbsolutePath());
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
                // System.out.println("create folder: " + toFile.getAbsolutePath());
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
        System.out.println("Generate sql to create user: " + user);
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
                printFiles(fromdir, filefrom, type, params);
            }
        }

        public static void paPrintFind(String[] args) throws Exception {
            String key = args[0];
            String alias = args[1];
            String resolved = toTARAlias(alias);
            if (!alias.equals(resolved))
                System.out.println(format("find \"{0}\" in \"{1}({2})\".", key, alias, resolved));
            else
                System.out.println(format("find \"{0}\" in \"{1}\".", key, resolved));
        }

        public static void paPrintReplace(String[] args) throws Exception {
            String from = args[0];
            String to = args[1];
            String alias = args[2];
            String resolved = toTARAlias(alias);
            if (!alias.equals(resolved))
                System.out
                        .println(format("replace from \"{0}\" to \"{1}\" in \"{2}({3})\".", from, to, alias, resolved));
            else
                System.out.println(format("replace from \"{0}\" to \"{1}\" in \"{1}\".", from, to, resolved));
        }

        public static void paCopy(String[] args) throws Exception {
            Params params = Params.toParams("acp", args);
            args = params.args;

            String from = null, fromfile = null, to = null, tofile = null;
            if (args.length < 2) {
                System.out.println(tab(2) + "need specify from and to");
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
                System.out.println(tab(2) + "need specify from and to");
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
                System.out.println("need specify the search key");
                return;
            } else if (args.length == 2) {
                filefrom = "*";
                from = args[1];
            } else {
                filefrom = args[1];
                from = args[2];
            }
            filefrom = fixFileFrom(filefrom, params);
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

            String fromdir = toTARAlias(args[0]);
            String filefrom, from, to;
            if (args.length < 3) {
                System.out.println("need specify replace from and to");
                return;
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
            System.out.println("copy from: " + from);
            System.out.println("     to:   " + to);
            System.out.println("           " + filefrom + " -> " + fileto);
        }

        protected static void copyFiles(String from, String fromfile, String to, String tofile, Params params)
                throws Exception {
            if (!params.move)
                System.out.println("copy from: " + from);
            else
                System.out.println("move from: " + from);
            System.out.println("     to:   " + to);
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
                            String newFileName = newFileNameInCopy(getFileName(topath), params);
                            topath = getParent(topath) + FILE_SEPARATOR + newFileName;
                            if (needOverwrite(p, topath, params)) {
                                long s = System.currentTimeMillis();
                                copyFile(p, topath, false);
                                if (params.move)
                                    deleteFileWithFolders(p);
                                long e = System.currentTimeMillis();
                                long cost = (e - s) / 1000;
                                String costStr = cost > 5 ? " [" + cost + "s]" : "";
                                System.out.println("           " + relativePath);
                                System.out.println("        -> " + topath + costStr);
                                addWithoutDup(dirs, topath);
                            }
                        }
                    }
                    OpenDirResult.openDirs(params, dirs);
                } else {
                    System.out.println(tab(2) + "to files not found: " + tofile);
                }
            } else {
                System.out.println(tab(2) + "from files not found: " + fromfile);
            }
        }

        protected static void deleteFiles(String from, String filefrom, Params params) throws Exception {
            System.out.println("delete from: " + from);
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
                    System.out.println(tab(2) + toRelativePath(from, p));
                    addWithoutDup(dirs, p);
                }
                OpenDirResult.openDirs(params, dirs);
                System.out.println(tab(2) + format("dirs: {0}, files: {1}", files.size() - filesSize, filesSize));
            } else {
                System.out.println(tab(2) + "no matched files: " + filefrom);
            }
        }

        protected static void printFiles(String from, String filefrom, String type, Params params) throws Exception {
            boolean one = type.equals("one");
            FilenameFilter filter = Filters.getFilters(filefrom, params);
            if (!one)
                System.out.println("print from: " + from);
            List<File> files = Util.listFiles(new File(from), params.recursive, filter, params);
            if (!files.isEmpty()) {
                for (File file : files) {
                    List<String> dirs = new ArrayList<String>();
                    if (isTextFile(file)) {
                        String p = file.getAbsolutePath();
                        if (!one)
                            System.out.println(tab(2) + toRelativePath(from, p));
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
                    OpenDirResult.openDirs(params, dirs);
                }
            } else {
                System.out.println(tab(2) + "no matched files: " + filefrom);
            }
        }

        protected static void listFiles(String from, String filefrom, Params params) throws Exception {
            FilenameFilter filter = Filters.getFilters(filefrom, params);
            if (params.fileTimestamp != null)
                System.out.println(format("list from: {0} ({1})", from, params.fileTimestamp.toString2()));
            else
                System.out.println("list from: " + from);
            List<File> files = Util.listFiles(new File(from), params.recursive, filter, params);
            if (!files.isEmpty()) {
                List<String> dirs = new ArrayList<String>();
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
                    System.out.println(tab(2) + listFileDetail(file, relativePath, nameIndent));
                    addWithoutDup(dirs, p);
                }
                OpenDirResult.openDirs(params, dirs);
                ZipOperationsResult.zipOperations(params, dirs);
                GoResult.go(params, dirs);
                DeleteSameResult.deleteSame(params, dirs);
                System.out.println(tab(2) + format("dirs: {0}, files: {1}", files.size() - filesSize, filesSize));
            } else {
                System.out.println(tab(2) + "no matched files: " + filefrom);
            }
        }

        protected static void renameFiles(String dir, String filefrom, String from, String to, Params params)
                throws Exception {
            System.out.println("rename from: " + dir);
            FilenameFilter filter = Filters.getFilters(filefrom, params);
            List<File> files = Util.listFiles(new File(dir), params.recursive, filter, params);
            if (!files.isEmpty()) {
                List<String> dirs = new ArrayList<String>();
                for (File file : files) {
                    String p = file.getAbsolutePath();
                    String p2 = renameFile(p, from, to);
                    String n1 = toRelativePath(dir, p);
                    String n2 = toRelativePath(dir, p2);
                    System.out.println(tab(2) + n1 + " -> " + n2);
                    addWithoutDup(dirs, p2);
                }
                OpenDirResult.openDirs(params, dirs);
            } else {
                System.out.println(tab(2) + "no matched files: " + filefrom);
            }
        }

        protected static void findInFiles(String dir, String filefrom, String from, Params params) throws Exception {
            System.out.println("find from: " + dir);
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
                            System.out.println(
                                    tab(2) + format("found \"{0}\" places in \"{1}\":", foundLines.size(), n1));
                            System.out.println();
                            for (Line line : foundLines) {
                                line.print(6, 7);
                            }
                            System.out.println();
                            hasResult = true;
                            addWithoutDup(dirs, p);
                            OperateLinesUtil.operateLines(p, foundLines, params);
                        }
                    }
                    OpenDirResult.openDirs(params, dirs);
                }
            }
            if (!hasResult) {
                System.out.println(tab(6) + "not found");
            }
        }

        protected static void openFiles(String from, String filefrom, Params params) throws Exception {
            FilenameFilter filter = Filters.getFilters(filefrom, params);
            System.out.println("open files from: " + from);
            List<File> files = Util.listFiles(new File(from), params.recursive, filter, params);
            List<String> lines = new ArrayList<String>();
            if (!files.isEmpty()) {
                for (File file : files) {
                    List<String> dirs = new ArrayList<String>();
                    if (isTextFile(file)) {
                        String p = file.getAbsolutePath();
                        System.out.println(tab(2) + toRelativePath(from, p));
                        lines.add(format("call \"{0}\" \"{1}\"", UltraEdit, p));
                        addWithoutDup(dirs, p);
                    }
                    OpenDirResult.openDirs(params, dirs);
                }
            } else {
                System.out.println(tab(2) + "no matched files: " + filefrom);
            }
            setLines(batDir + "aopentmp.bat", lines);
        }

        protected static void replaceFiles(String dir, String filefrom, String from, String to, Params params)
                throws Exception {
            System.out.println(format("replace from \"{0}\" to \"{1}\" in dir: {2}", from, to, dir));
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
                                System.out.println(tab(2) + n1);
                            else
                                System.out.println(tab(2) + format("{0} -> {1}", n1, n2));
                            r.logLines();
                            replaced = true;
                            addWithoutDup(dirs, p);
                        }
                    }
                    OpenDirResult.openDirs(params, dirs);
                }
            }
            if (!replaced) {
                System.out.println(tab(2) + "no matched files: " + filefrom);
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
                    System.out.println("redirect: " + redirectOp + " " + connectLines(list, " "));
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

        private static String listFileDetail(File file, String relativePath, int nameIndent) {
            int sizeIndent = 13;
            int dirIndent = 10;
            int timeIndent = 30;
            String n = formatstr(relativePath, nameIndent + 1);
            String size = file.isDirectory() ? formatstr("", sizeIndent)
                    : formatstr(df.format(file.length()), sizeIndent, false);
            String dir = file.isDirectory() ? formatstr("<DIR>", dirIndent) : formatstr("", dirIndent);
            String time = formatstr(sdf4.format(new Date(file.lastModified())), timeIndent);
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
                return getFileTimestamp(frompath) > getFileTimestamp(topath);
            }
            return true;
        }

        private static String newFileNameInCopy(String fileName, Params params) {
            if (params.newFileName != null) {
                String newFileName = params.newFileName;
                if (newFileName.contains("{n}"))
                    newFileName = newFileName.replace("{n}", getFileSimpleName(fileName));
                if (newFileName.contains("{e}"))
                    newFileName = newFileName.replace("{e}", getFileExtName(fileName));
                return newFileName;
            }
            return fileName;
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
                System.out.println(format("Filters: p={0}, filters={1}, filter={2}", p, filters, filter));
            }
        }

        public static Filters getFilters(String filefrom, Params params2) {
            FiltersPattern fp = new FiltersPattern();
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
                System.out.println(
                        format("Filters: p={0}, filters={1}, filter={2}, first={3}", p, filters, filter, first));
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
                System.out.println(
                        format("Filters: p={0}, filters={1}, filter={2}, first={3}", p, filters, filter, first));
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
                                System.out.println(format(FILTERS, p, filters, filter, and, dir, name, false));
                            }
                            return false;
                        }
                    }
                    if (debug2_) {
                        System.out.println(format(FILTERS, p, filters, filter, and, dir, name, true));
                    }
                    return true;
                } else {
                    boolean result = filter.accept(dir, name);
                    if (debug2_) {
                        System.out.println(format(FILTERS, p, filters, filter, and, dir, name, result));
                    }
                    return result;
                }
            } else {
                if (isFilters()) {
                    for (Filters filter : filters) {
                        if (filter.accept(dir, name)) {
                            if (debug2_) {
                                System.out.println(format(FILTERS, p, filters, filter, and, dir, name, true));
                            }
                            return true;
                        }
                    }
                    if (debug2_) {
                        System.out.println(format(FILTERS, p, filters, filter, and, dir, name, false));
                    }
                    return false;
                } else {
                    boolean result = filter.accept(dir, name);
                    if (debug2_) {
                        System.out.println(format(FILTERS, p, filters, filter, and, dir, name, result));
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
                                System.out.println(format(FILTERS2, p, filters, filter, and, line, pos, false));
                            }
                            return false;
                        }
                    }
                    if (debug2_) {
                        System.out.println(format(FILTERS2, p, filters, filter, and, line, pos, true));
                    }
                    return true;
                } else {
                    boolean result = filter.accept(line, pos);
                    if (debug2_) {
                        System.out.println(format(FILTERS2, p, filters, filter, and, line, pos, result));
                    }
                    return result;
                }
            } else {
                if (isFilters()) {
                    for (Filters filter : filters) {
                        if (filter.accept(line, pos)) {
                            if (debug2_) {
                                System.out.println(format(FILTERS2, p, filters, filter, and, line, pos, true));
                            }
                            return true;
                        }
                    }
                    if (debug2_) {
                        System.out.println(format(FILTERS2, p, filters, filter, and, line, pos, false));
                    }
                    return false;
                } else {
                    boolean result = filter.accept(line, pos);
                    if (debug2_) {
                        System.out.println(format(FILTERS2, p, filters, filter, and, line, pos, result));
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
                System.out.println(format("Filter: p={0}, filters={1}, first={3}", p, filters, first));
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
                System.out.println(format("Filter: p={0}, filters={1}, first={3}", p, filters, first));
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
                    System.out.println(format(FILTER, p, dir, name, result));
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
                    System.out.println(format(FILTER2, p, line, pos, result));
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
        public String p;
        public boolean include = true;
        public boolean quote = false;
        public boolean group = false;
        public boolean regular = false;
        public boolean lineNumber = false;

        private boolean ignore = false;

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
                    System.out.println(format("Pattern: line={2}, p={0}, regular={1}", p, regular, line));
                }
                b = line.matches(p);
            } else if (quote) {
                if (debug2_) {
                    System.out.println(format("Pattern: line={2}, p={0}, quote={1}", p, quote, line));
                }
                b = line.contains(p);
            } else {
                String fixPattern = fixPattern(p);
                if (debug2_) {
                    System.out.println(format("Pattern: line={2}, p={0}, fixPattern={1}", p, fixPattern, line));
                }
                b = line.matches(fixPattern);
            }
            if (include) {
                if (debug2_) {
                    System.out.println(format("Pattern: line={2}, p={0}, result={1}", p, b, line));
                }
                return b;
            } else {
                if (debug2_) {
                    System.out.println(format("Pattern: line={2}, p={0}, result={1}", p, !b, line));
                }
                return !b;
            }
        }

        public boolean accept(String line, int pos) {
            boolean b;
            if (regular) {
                if (debug2_) {
                    System.out.println(format("Pattern: line={2}, p={0}, regular={1}", p, regular, line));
                }
                b = line.matches(p);
            } else if (quote) {
                if (debug2_) {
                    System.out.println(format("Pattern: line={2}, p={0}, quote={1}", p, quote, line));
                }
                b = line.contains(p);
            } else if (lineNumber) {
                if (debug2_) {
                    System.out.println(format("Pattern: line={2}, p={0}, lineNumber={1}", p, lineNumber, line));
                }
                b = ExpandLines.matchesLineNumber(p, pos);
            } else {
                String fixPattern = fixPattern(p);
                if (debug2_) {
                    System.out.println(format("Pattern: line={2}, p={0}, fixPattern={1}", p, fixPattern, line));
                }
                b = line.matches(fixPattern);
            }
            if (ignore)
                b = true;
            if (include) {
                if (debug2_) {
                    System.out.println(format("Pattern: line={2}, p={0}, result={1}", p, b, line));
                }
                return b;
            } else {
                if (debug2_) {
                    System.out.println(format("Pattern: line={2}, p={0}, result={1}", p, !b, line));
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
                    System.out.println(tab(2) + "Expand Lines: " + r.expandLines);
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
                    System.out.println(tab(2) + "Recursive Level: " + r.n);
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
                    System.out.println(tab(2) + "No Line Number: " + r.noLineNumber);
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
                    System.out.println(tab(2) + "Case Sensitive: " + r.caseSensitive);
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
                    System.out.println(tab(2) + "Open Dir: " + r.openDir);
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

        public static void openDirs(Params params, List<String> dirs) throws Exception {
            if (params.openDir) {
                if (dirs != null && !dirs.isEmpty()) {
                    List<String> list = new ArrayList<String>();
                    int i = 0;
                    Set<String> opened = new HashSet<String>();
                    for (String dir : dirs) {
                        if (isFile(dir)) {
                            if (!opened.contains(getParent(dir))) {
                                list.add("call explorer /e,/select," + dir);
                                opened.add(getParent(dir));
                            }
                        } else {
                            if (dir.contains("\\\\"))
                                dir = dir.replace("\\\\", "\\");
                            if (!opened.contains(dir)) {
                                list.add("call explorer " + dir);
                                opened.add(dir);
                            }
                        }
                        i++;
                        if (params.openDirsCount > 0 && i >= params.openDirsCount)
                            break;
                    }
                    setLines(batDir + "aopendirtmp.bat", list);
                }
            }
            if (params.openFile) {
                if (dirs != null && !dirs.isEmpty()) {
                    List<String> list = new ArrayList<String>();
                    int i = 0;
                    for (String dir : dirs) {
                        if (isFile(dir)) {
                            if (isTextFile(dir))
                                list.add("call e " + dir);
                            else
                                list.add("call explorer " + dir);
                        } else {
                            list.add("call explorer " + dir);
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
                    System.out.println(tab(2) + "GBK Encoding: " + r.gbkEncoding);
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
                    System.out.println(tab(2) + "Keep Dir: " + r.keepDir);
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
                    System.out.println(tab(2) + "New File Name: " + r.newFileName);
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
                    System.out.println(tab(2) + "No Path: " + r.noPath);
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

    public static class UseDotResult {
        public String[] args;
        public boolean useDot;

        public static UseDotResult useDot(String[] args) {
            UseDotResult r = new UseDotResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.useDot = true;
                r.args = cutLastArg(args);
                if (debug_)
                    System.out.println(tab(2) + "Use Dot: " + r.useDot);
            } else {
                r.useDot = false;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return last.equals("ud");
        }
    }

    public static class SortTypeResult {
        public String[] args;
        public String sortType;

        public static SortTypeResult sortType(String[] args) {
            SortTypeResult r = new SortTypeResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.sortType = cutFirst(last, 1);
                r.args = cutLastArg(args);
                if (debug_)
                    System.out.println(tab(2) + "Sort Type: " + r.sortType);
            } else {
                r.sortType = null;
                r.args = args;
            }
            return r;
        }

        public static boolean isParam(String last) {
            return last.matches("s[t]");
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
                    System.out.println(tab(2) + "Multiple Lines: " + r.multipleLines);
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
                    System.out.println(tab(2) + "Move: " + r.move);
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
                    System.out.println(tab(2) + "Operate Lines: " + r.operateLines);
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
                    System.out.println(tab(2) + "Zip Operations: " + r.zipOperations);
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
                            System.out.println("Ignore zip operations since file size is " + files.size());
                    }
                } else {
                    if (files.size() == 1) {
                        String zipFile = files.get(0);
                        String from = getParent(zipFile);
                        String to = zo.to;
                        String name = getFileName(zipFile);
                        String line = format("call aunzip \"{0}\" \"{1}\" \"{2}\"", from, to, name);
                        setLines(batDir + "aunziptmp.bat", toList(line));
                    } else {
                        if (debug_)
                            System.out.println("Ignore zip operations since file size is " + files.size());
                    }
                }
            }
        }

        public static boolean isParam(String last) {
            return ZipOperations.isParam(last);
        }

        public static class ZipOperations {
            public boolean zip = true;
            public String to;

            public static ZipOperations parseZipOperations(String pattern) throws Exception {
                if (isParam(pattern)) {
                    ZipOperations zo = new ZipOperations();
                    zo.zip = isZip(pattern);
                    zo.to = getTo(pattern);
                    return zo;
                }
                return null;
            }

            public static boolean isParam(String last) {
                return last.startsWith("unzip=") || last.startsWith("zip=");
            }

            private static boolean isZip(String pattern) {
                return pattern.startsWith("zip=");
            }

            private static String getTo(String pattern) throws Exception {
                String tmp;
                if (isZip(pattern)) {
                    tmp = cutFirst(pattern, "zip=".length());
                } else {
                    tmp = cutFirst(pattern, "unzip=".length());
                }
                return toTARAlias(tmp);
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
                    System.out.println(tab(2) + "Overwrite: " + r.overwrite);
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

    public static class GoResult {
        public String[] args;
        public boolean go;

        public static GoResult go(String[] args) {
            GoResult r = new GoResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.go = true;
                r.args = cutLastArg(args);
                if (debug_)
                    System.out.println(tab(2) + "Go: " + r.go);
            } else {
                r.go = false;
                r.args = args;
            }
            return r;
        }

        public static void go(Params params, List<String> dirs) throws Exception {
            if (params.go) {
                if (dirs != null && !dirs.isEmpty()) {
                    List<String> list = new ArrayList<String>();
                    for (String dir : dirs) {
                        if (isFile(dir)) {
                            list.add("call go " + getParent(dir));
                            break;
                        } else {
                            if (dir.contains("\\\\"))
                                dir = dir.replace("\\\\", "\\");
                            list.add("call go " + dir);
                            break;
                        }
                    }
                    setLines(batDir + "agotmp.bat", list);
                }
            }
        }

        public static boolean isParam(String last) {
            return last.equals("go");
        }
    }

    public static class DeleteSameResult {
        public String[] args;
        public boolean deleteSame;

        public static DeleteSameResult deleteSame(String[] args) {
            DeleteSameResult r = new DeleteSameResult();
            String last = getLastArg(args);
            if (isParam(last)) {
                r.deleteSame = true;
                r.args = cutLastArg(args);
                if (debug_)
                    System.out.println(tab(2) + "Delete Same: " + r.deleteSame);
            } else {
                r.deleteSame = false;
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
        }

        public static boolean isParam(String last) {
            return last.equals("ds");
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
                    System.out.println(tab(2) + "File Timestamp: " + r.fileTimestamp);
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
                    System.out.println(tab(2) + "Mark Occurrence: " + r.markOccurrence);
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
        public boolean useDot = false;
        public String sortType = null;
        public boolean multipleLines = false;
        public boolean move = false;
        public OperateLines operateLines = null;
        public ZipOperations zipOperations = null;
        public boolean overwrite = false;
        public boolean go = false;
        public boolean deleteSame = false;
        public FileTimestamp fileTimestamp = null;
        public boolean markOccurrence = false;

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
                System.out.println(tab(4) + "recursive = " + recursive);
                System.out.println(tab(4) + "expandLines = " + expandLines);
                System.out.println(tab(4) + "recursiveLevel = " + recursiveLevel);
                System.out.println(tab(4) + "caseSensitive = " + caseSensitive);
                System.out.println(tab(4) + "noLineNumber = " + noLineNumber);
                System.out.println(tab(4) + "openDir = " + openDir);
                System.out.println(tab(4) + "openFile = " + openFile);
                System.out.println(tab(4) + "gbkEncoding = " + gbkEncoding);
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
                // use dot
                UseDotResult udr = UseDotResult.useDot(args);
                if (args.length > udr.args.length) {
                    args = udr.args;
                    if (params.useDot == false)
                        params.useDot = udr.useDot;
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
                // go
                GoResult gor = GoResult.go(args);
                if (args.length > gor.args.length) {
                    args = gor.args;
                    if (params.go == false)
                        params.go = gor.go;
                }
                // delete same
                DeleteSameResult dsr = DeleteSameResult.deleteSame(args);
                if (args.length > dsr.args.length) {
                    args = dsr.args;
                    if (params.deleteSame == false)
                        params.deleteSame = dsr.deleteSame;
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
                System.out.println(format(m + ": ", 30) + connectLines(args, " "));
        }

        public static void log(String m, String line) {
            if (debug_)
                System.out.println(format(m + ": ", 30) + line);
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
            if (MultipleLinesResult.isParam(s))
                return true;
            if (MoveResult.isParam(s))
                return true;
            if (OperateLinesResult.isParam(s))
                return true;
            if (OverwriteResult.isParam(s))
                return true;
            if (GoResult.isParam(s))
                return true;
            if (DeleteSameResult.isParam(s))
                return true;
            if (FileTimestampResult.isParam(s))
                return true;
            return false;
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
                        System.out.println("output to: " + file);
                    outputToFile(file);
                    if (!slient) {
                        String line = "call e " + file;
                        setLines(batDir + "aoutputtmp.bat", toList(line));
                    }
                }
            }
            return args;
        }

        public static void outputToFile(String n) throws FileNotFoundException {
            System.setOut(new PrintStream(n));
        }
    }

}
