package gzhou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppFileCopier {

    public static void upgradeClasses() throws Exception {
        for (int i = 0; i < 10; i++) {
            String from = "C:\\workspace\\buildtemp\\bin\\com\\vitria\\pd" + i + "\\analytics\\util";
            String to = "C:\\libs\\apps\\pd" + i + "\\server\\services\\com.vitria.pd.analyticlogger\\com\\vitria\\pd"
                    + i + "\\analytics\\util";
            FileUtil.copyFolder(from, to);
        }
        for (int i = 0; i < 10; i++) {
            String from = "C:\\workspace\\buildtemp\\bin\\com\\vitria\\pd" + i + "\\util";
            String to = "C:\\libs\\apps\\pd" + i + "\\server\\libs\\com.vitria.pd" + i + ".util\\com\\vitria\\pd" + i
                    + "\\util";
            FileUtil.copyFolder(from, to);
        }
        for (int i = 0; i < 10; i++) {
            String from = "C:\\workspace\\buildtemp\\bin\\com\\vitria\\test" + i + "";
            String to = "C:\\libs\\project\\server\\libs\\com.vitria.test" + i + ".lib\\com\\vitria\\test" + i + "";
            Set<String> classes = new HashSet<String>();
            classes.add("ProjectLibListener.class");
            classes.add("TestHelloWorldInLib.class");
            FileUtil.copyFolder(from, to, classes, null);
        }
        for (int i = 0; i < 10; i++) {
            String from = "C:\\workspace\\buildtemp\\bin\\com\\vitria\\test" + i + "";
            String to = "C:\\libs\\project\\server\\services\\com.vitria.test" + i + ".bundle\\com\\vitria\\test" + i
                    + "";
            Set<String> classes = new HashSet<String>();
            classes.add("ProjectBundleInvoker.class");
            classes.add("ProjectBundleListener.class");
            classes.add("TestBundleActivator.class");
            classes.add("TestHelloWorldInBundle.class");

            FileUtil.copyFolder(from, to, classes, null);
        }
        markBigJar(2);
        markBigJar(5);
        markBigJar(10);
    }

    public static void upgradeLibs() throws Exception {
        cleanLibs();
        makeLibs();
        listLibs();
    }

    public static void makeLibs() throws Exception {
        makeLibs(1);
        makeLibs(2);
        makeLibs(5);
        makeLibs(10);
        makeBigLibs(2);
        makeBigLibs(5);
        makeBigLibs(10);
    }

    public static void cleanLibs() throws Exception {
        deleteFolder("C:\\libs_1");
        deleteFolder("C:\\libs_2");
        deleteFolder("C:\\libs_5");
        deleteFolder("C:\\libs_10");
        deleteFolder("C:\\libs_big2");
        deleteFolder("C:\\libs_big5");
        deleteFolder("C:\\libs_big10");
    }

    public static void listLibs() throws Exception {
        listFolder("C:\\libs_1");
        listFolder("C:\\libs_2");
        listFolder("C:\\libs_5");
        listFolder("C:\\libs_10");
        listFolder("C:\\libs_big2");
        listFolder("C:\\libs_big5");
        listFolder("C:\\libs_big10");
    }

    public static void makeLibs(int max) throws Exception {
        String libs = "libs_" + max;
        for (int i = 0; i < max; i++) {
            {
                String from = "C:\\libs\\apps\\pd" + i + "\\server\\services";
                String to = "C:\\" + libs + "\\apps\\pd\\server\\services";
                Set<String> include = new HashSet<String>();
                include.add("com.vitria.pd" + i + ".analyticlogger.jar");
                FileUtil.copyFolder(from, to, include, null, false);
            }

            //            {
            //                String from = "C:\\libs\\apps\\pd"+i+"\\server\\libs";
            //                String to = "C:\\"+libs+"\\apps\\pd\\server\\libs";
            //                Set<String> include = new HashSet<String>();
            //                include.add("com.vitria.pd"+i+".util.jar");
            //                FileUtil.copyFolder(from, to, include, null, false);
            //            }
            //            
            //            {
            //                String from = "C:\\libs\\project\\server\\services";
            //                String to = "C:\\"+libs+"\\project\\server\\services";
            //                Set<String> include = new HashSet<String>();
            //                include.add("com.vitria.test"+i+".bundle.jar");
            //                FileUtil.copyFolder(from, to, include, null, false);
            //            }
            //            
            //            {
            //                String from = "C:\\libs\\project\\server\\libs";
            //                String to = "C:\\"+libs+"\\project\\server\\libs";
            //                Set<String> include = new HashSet<String>();
            //                include.add("com.vitria.test"+i+".lib.jar");
            //                FileUtil.copyFolder(from, to, include, null, false);
            //            }
        }
    }

    public static void makeBigLibs(int max) throws Exception {
        String libs = "libs_big" + max;
        String i = "_big" + max;
        {
            String from = "C:\\libs\\apps\\pd" + i + "\\server\\services";
            String to = "C:\\" + libs + "\\apps\\pd\\server\\services";
            Set<String> include = new HashSet<String>();
            include.add("com.vitria.pd" + i + ".analyticlogger.jar");
            FileUtil.copyFolder(from, to, include, null, false);
        }

        //            {
        //                String from = "C:\\libs\\apps\\pd"+i+"\\server\\libs";
        //                String to = "C:\\"+libs+"\\apps\\pd\\server\\libs";
        //                Set<String> include = new HashSet<String>();
        //                include.add("com.vitria.pd"+i+".util.jar");
        //                FileUtil.copyFolder(from, to, include, null, false);
        //            }
        //            
        //            {
        //                String from = "C:\\libs\\project\\server\\services";
        //                String to = "C:\\"+libs+"\\project\\server\\services";
        //                Set<String> include = new HashSet<String>();
        //                include.add("com.vitria.test"+i+".bundle.jar");
        //                FileUtil.copyFolder(from, to, include, null, false);
        //            }
        //            
        //            {
        //                String from = "C:\\libs\\project\\server\\libs";
        //                String to = "C:\\"+libs+"\\project\\server\\libs";
        //                Set<String> include = new HashSet<String>();
        //                include.add("com.vitria.test"+i+".lib.jar");
        //                FileUtil.copyFolder(from, to, include, null, false);
        //            }
    }

    public static void markBigJar(int max) throws Exception {
        for (int i = 0; i < max; i++) {
            {
                String from = "C:\\libs\\apps\\pd" + i + "\\server\\services\\com.vitria.pd.analyticlogger";
                String to = "C:\\libs\\apps\\pd_big" + max + "\\server\\services\\com.vitria.pd_big" + max
                        + ".analyticlogger";
                FileUtil.copyFolder(from, to);
            }

            //            {
            //                String from = "C:\\libs\\apps\\pd"+i+"\\server\\libs\\com.vitria.pd"+i+".util";
            //                String to = "C:\\libs\\apps\\pd_big"+max+"\\server\\libs\\com.vitria.pd_big"+max+".util";
            //                FileUtil.copyFolder(from, to);
            //            }
            //            
            //            {
            //                String from = "C:\\libs\\project\\server\\libs\\com.vitria.test"+i+".lib";
            //                String to = "C:\\libs\\project\\server\\libs\\com.vitria.test_big"+max+".lib";
            //                FileUtil.copyFolder(from, to);
            //            }
            //            
            //            {
            //                String from = "C:\\libs\\project\\server\\services\\com.vitria.test"+i+".bundle";
            //                String to = "C:\\libs\\project\\server\\services\\com.vitria.test_big"+max+".bundle";
            //                FileUtil.copyFolder(from, to);
            //            }
        }
    }

    public static void replace(String file, String from, String to) throws Exception {
        List<String> list = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        while ((line = in.readLine()) != null) {
            list.add(line.replaceAll(from, to));
        }
        in.close();

        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
        for (String s : list) {
            out.println(s);
        }
        out.close();
        System.out.println("replace \"" + from + "\" with \"" + to + "\" in file: " + file);
    }

    public static void insert(String file, int index, String linestr) throws Exception {
        List<String> list = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        int i = 1;
        while ((line = in.readLine()) != null) {
            if (i == index)
                list.add(linestr);
            list.add(line);
            i++;
        }
        in.close();

        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
        for (String s : list) {
            out.println(s);
        }
        out.close();
        System.out.println("insert \"" + linestr + "\" at line \"" + index + "\" in file: " + file);
    }

    public static void deleteFolder(String from) throws Exception {
        deleteFolder(from, null);
    }

    public static void deleteFolder(String from, String suffix) throws Exception {
        File fromFile = new File(from);
        File[] files = fromFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                deleteFolder(file.getAbsolutePath());
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
            System.out.println("delete: " + fromFile.getAbsolutePath());
            fromFile.delete();
        }
    }

    public static void listFolder(String from) throws Exception {
        listFiles(from);
    }

    public static void listFiles(String from) throws Exception {
        File fromFile = new File(from);
        File[] files = fromFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                listFiles(file.getAbsolutePath());
            } else {
                System.out.println(file.getAbsolutePath());
            }
        }
    }
}
