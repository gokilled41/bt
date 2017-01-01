package gzhou;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchSrcPath {

    private List<String> list = new ArrayList<String>();
    private Map<String, String> mapcp = new HashMap<String, String>();
    private List<String> listcp = new ArrayList<String>();
    private static Set<String> set;
    private static Set<String> setCode;
    private static Set<String> fileSuffix;
    private static Set<String> exset;
    private static Map<String, String> replace;

    private static boolean onlyJavaFile = true;

    static {
        init();
    }

    public static void main(String[] args) throws Exception {
        String path = args[0];
        search(path);
    }

    public static void search(String path) {
        SearchSrcPath ssp = new SearchSrcPath();
        ssp.dosearch(path);
        System.out.println();
        ssp.calClassPath();
        ssp.sortClassPath();
        ssp.printClassPath();
        System.out.println();
        ssp.printLink();
        ssp.printOnebyOne();
    }

    public void dosearch(String loc) {
        File f = new File(loc);
        search(f);
    }

    public void calClassPath() {
        for (String p : list) {
            String cp = getClassPath(p);
            mapcp.put(cp, p);
            listcp.add(cp);
        }
    }

    public void sortClassPath() {
        String[] cp = listcp.toArray(new String[listcp.size()]);
        Arrays.sort(cp);
        listcp.clear();
        listcp.addAll(Arrays.asList(cp));
    }

    public void printClassPath() {
        for (String cp : listcp) {
            System.out.println("\t<classpathentry kind=\"src\" path=\"" + cp + "\" />");
        }
    }

    public void printLink() {
        for (String cp : listcp) {
            String p = mapcp.get(cp);
            System.out.println("\t\t<link>");
            System.out.println("\t\t\t<name>" + cp + "</name>");
            System.out.println("\t\t\t<type>2</type>");
            System.out.println("\t\t\t<location>" + p + "</location>");
            System.out.println("\t\t</link>");
        }
    }

    public void printOnebyOne() {
        for (String cp : listcp) {
            String p = mapcp.get(cp);
            System.out.println();
            System.out.println("\t" + cp);
            System.out.println("\t" + p.replaceAll("/", "\\\\"));
        }
    }

    private void search(File f) {
        if (!f.isDirectory())
            return;

        if (isModule(f)) {
            printFile(f);
            return;
        }

        File[] list = f.listFiles();
        for (int i = 0; i < list.length; i++) {
            File file = list[i];
            search(file);
        }
    }

    private boolean isModule(File f) {
        if (!f.isDirectory())
            return false;
        File[] list = f.listFiles();
        for (int i = 0; i < list.length; i++) {
            if (setCode.contains(list[i].getName())) {
                if (!isModule(list[i]))
                    return true;
            }
        }
        return false;
    }

    private void printFile(File f) {
        String ap = f.getAbsolutePath();
        ap = ap.replaceAll("\\\\", "/");

        if (isEx(ap))
            return;

        if (!isIncludeFile(ap))
            return;

        System.out.println(ap);
        list.add(ap);
    }

    private static String getClassPath(String p) {
        String[] s = p.split("/");

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < s.length; i++) {
            if (!set.contains(s[i])) {
                String node = replace.get(s[i]) != null ? replace.get(s[i]) : s[i];
                sb.append(node);
                sb.append("_");
            }
        }

        String classpath = sb.toString();
        if (classpath.endsWith("_"))
            classpath = classpath.substring(0, classpath.length() - 1);

        return classpath;
    }

    private boolean isEx(String s) {
        for (String ex : exset) {
            if (s.indexOf(ex) > -1)
                return true;
        }
        return false;
    }

    private boolean isIncludeFile(String s) {
        if (!onlyJavaFile)
            return true;

        File f = new File(s);
        File[] files = f.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (isIncludeFile(file.getAbsolutePath())) {
                    return true;
                }
            } else {
                if (file.getName().endsWith(".java")) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void init() {
        set = new HashSet<String>();

        set.add("C:");
        set.add("jedi");
        set.add("yoda");
        set.add("src");
        set.add("main");
        set.add("ejbModule");
        set.add("C:");
        set.add("devtests");
        set.add("auto");
        set.add("jboss-as");
        set.add("java");

        setCode = new HashSet<String>();
        setCode.add("com");
        setCode.add("org");
        setCode.add("java");
        setCode.add("javax");

        fileSuffix = new HashSet<String>();
        fileSuffix.add(".java");

        exset = new HashSet<String>();
        exset.add("seam");
        exset.add("docs");
        // exset.add("tests");
        exset.add("resources");
        exset.add("examples");
        exset.add("etc");
        exset.add("jdk15");
        exset.add("vtTaskManager");
        exset.add("vtWorkflowCommon");
        exset.add("thirdparty");

        replace = new HashMap<String, String>();
        replace.put("jboss-eap-4.3-src", "jboss");
        replace.put("workflow", "wf");
    }
}
