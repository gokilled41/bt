package gzhou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vitria.component.util.DOMUtil;

import gzhou.FileUtil.FileTimestampResult.FileTimestamp;
import gzhou.FileUtil.Filters;
import gzhou.FileUtil.ListConditionResult.ListCondition;
import gzhou.FileUtil.MarkOccurrenceResult;
import gzhou.FileUtil.Params;

public class Util implements Constants {

    private static final int ABATCH = 10000;
    private static List<String> txtList_ = new ArrayList<String>();

    static {
        initTxt();
    }

    public static Date toDate(long l) {
        Date d = new Date(l);
        return d;
    }

    // yyyyMMddHHmmss
    public static String toDateStr(long l) {
        if (l == Long.MAX_VALUE)
            return "MAX";
        return sdf2.format(toDate(l));
    }

    // yyyyMMdd
    public static String toDateStr2(long l) {
        if (l == Long.MAX_VALUE)
            return "MAX";
        return sdfDay2.format(toDate(l));
    }

    public static long toTimestamp(String s) throws Exception {
        return sdf2.parse(toTimestampFormat(s)).getTime();
    }

    public static String toTimestampFormat(String s) {
        if (s.length() == 1) {
            if (s.equals("0"))
                return format("{0}{1}{2}000000", thisYear(), thisMonth(), thisDay());
            else
                return format("{0}{1}0{2}000000", thisYear(), thisMonth(), s);
        } else if (s.length() == 2) {
            return format("{0}{1}{2}000000", thisYear(), thisMonth(), s);
        } else if (s.length() == 3) {
            return format("{0}0{1}000000", thisYear(), s);
        } else if (s.length() == 4) {
            return format("{0}{1}000000", thisYear(), s);
        } else if (s.length() == 6) {
            return format("{0}{1}0000", thisYear(), s);
        } else if (s.length() == 8) {
            String first2 = subFirst(s, 2);
            int i = toInt(first2);
            if (i > 12)
                return format("{0}000000", s);
            else
                return format("{0}{1}00", thisYear(), s);
        } else if (s.length() == 10) {
            String first2 = subFirst(s, 2);
            int i = toInt(first2);
            if (i > 12)
                return format("{0}0000", s);
            else
                return format("{0}{1}", thisYear(), s);
        } else if (s.length() == 12) {
            String first2 = subFirst(s, 2);
            int i = toInt(first2);
            if (i > 12)
                return format("{0}00", s);
        } else if (s.length() == 14) {
            String first2 = subFirst(s, 2);
            int i = toInt(first2);
            if (i > 12)
                return format("{0}", s);
        }
        throw new UnsupportedOperationException("Unsupported time format: " + s);
    }

    public static String thisYear() {
        Date today = new Date();
        String month = sdfMonth.format(today);
        return subFirst(month, 4);
    }

    public static String thisMonth() {
        Date today = new Date();
        String month = sdfMonth.format(today);
        return subLast(month, 2);
    }

    public static String thisDay() {
        Date today = new Date();
        String day = sdfDay.format(today);
        return subLast(day, 2);
    }

    public static String now() {
        return sdf2.format(toDate(System.currentTimeMillis()));
    }

    public static String today() {
        return sdfDay2.format(toDate(System.currentTimeMillis()));
    }

    public static String tomorrow() {
        return sdfDay2.format(toDate(System.currentTimeMillis() + 24 * 3600 * 1000));
    }

    public static boolean isToday(Date d) {
        Date today = new Date();
        String todayStr = sdfDay.format(today);
        String dStr = sdfDay.format(d);
        return dStr.equals(todayStr);
    }

    public static List<String> getFileList(String dir) {
        List<String> list = new ArrayList<String>();
        File folder = new File(dir);
        File[] files = folder.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isFile())
                    list.add(file.getName());
            }
        }
        return list;
    }

    public static String getFileContentFromThread(String file) {
        InputStream in = null;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            return new String(bytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static String getFileContentFromFile(String file) {
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            return new String(bytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static String formatXML(String xml) {
        return DOMUtil.getXML(DOMUtil.getDocument(xml));
    }

    public static List<String> readLines(String file) throws Exception {
        List<String> list = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        while ((line = in.readLine()) != null) {
            list.add(line);
        }
        in.close();
        return list;
    }

    public static void printLines(List<String> lines) {
        for (String line : lines) {
            System.out.println(line);
        }
    }

    public static String wrapWithQuota(String s) {
        if (s.contains(" ")) {
            return "\"" + s + "\"";
        }
        return s;
    }

    public static String format(String message, Object... objects) {
        message = beforeFormat(message);
        message = MessageFormat.format(message, objects);
        message = afterFormat(message);
        return message;
    }

    private static String beforeFormat(String s) {
        s = s.replace("\\{", "##leftquota##");
        s = s.replace("\\}", "##rightquota##");
        return s;
    }

    private static String afterFormat(String s) {
        s = s.replace("##leftquota##", "{");
        s = s.replace("##rightquota##", "}");
        return s;
    }

    public static String formatColumn(String s, int n, boolean left) {
        int len = getWordCount(s);
        if (len < n) {
            for (int i = 0; i < n - len; i++) {
                if (left)
                    s = s + " ";
                else
                    s = " " + s;
            }
        }
        return s;
    }

    public static String formatColumnObject(Object o) {
        if (o == null)
            return "null";
        if (o instanceof Date) {
            return sdf4.format(o);
        } else {
            return o.toString();
        }
    }

    public static int getWordCount(String s) {
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = Character.codePointAt(s, i);
            if (ascii >= 0 && ascii <= 255)
                length++;
            else
                length += 2;

        }
        return length;

    }

    public static String cutFirst(String s, int n) {
        return s.substring(n);
    }

    public static String cutLast(String s, int n) {
        return s.substring(0, s.length() - n);
    }

    public static String cut(String s, String from, String to) {
        if (from != null && !from.isEmpty())
            s = s.substring(s.indexOf(from) + from.length());
        if (to != null && !to.isEmpty())
            s = s.substring(0, s.indexOf(to));
        return s;
    }

    public static String cut(String s, int first, int last) {
        if (first > 0)
            s = cutFirst(s, first);
        if (last > 0)
            s = cutLast(s, last);
        return s;
    }

    public static String sub(String s, int begin, int end) {
        return s.substring(begin, end);
    }

    public static String subFirst(String s, int n) {
        return sub(s, 0, n);
    }

    public static String subLast(String s, int n) {
        return sub(s, s.length() - n, s.length());
    }

    public static String addFirst(String s, String n) {
        if (!s.startsWith(n))
            s = n + s;
        return s;
    }

    public static String addLast(String s, String n) {
        if (!s.endsWith(n))
            s = s + n;
        return s;
    }

    public static String removeFirst(String s, String n) {
        if (s.startsWith(n))
            s = cutFirst(s, n.length());
        return s;
    }

    public static String removeLast(String s, String n) {
        if (s.endsWith(n))
            s = cutLast(s, n.length());
        return s;
    }

    public static String cutBack(String s, String from, String to) {
        if (from != null && !from.isEmpty())
            s = s.substring(0, s.lastIndexOf(from));
        if (to != null && !to.isEmpty())
            s = s.substring(s.lastIndexOf(to) + to.length(), s.length());
        return s;
    }

    public static List<String> getLines(String filePath) throws Exception {
        filePath = FileUtil.toTARAlias(filePath);
        return getLines(new FileInputStream(filePath), "UTF-8");
    }

    public static List<String> getLines2(String filePath) throws Exception {
        return getLines(new FileInputStream(filePath), "UTF-8");
    }

    public static List<String> getLines(String filePath, String encoding) throws Exception {
        return getLines(new FileInputStream(filePath), encoding, -1);
    }

    public static List<String> getLines(String filePath, int count) throws Exception {
        return getLines(new FileInputStream(filePath), "UTF-8", count);
    }

    public static List<String> getLines(String filePath, String encoding, int count) throws Exception {
        return getLines(new FileInputStream(filePath), encoding, count);
    }

    public static List<String> getLines(InputStream is) throws Exception {
        return getLines(is, "UTF-8", -1);
    }

    public static List<String> getLines(InputStream is, String encoding) throws Exception {
        return getLines(is, encoding, -1);
    }

    public static List<String> getLines(InputStream is, String encoding, int count) throws Exception {
        List<String> list = new ArrayList<String>();
        if (count != 0) {
            BufferedReader in = new BufferedReader(new UnicodeReader(is, encoding));
            String line;
            int i = 0;
            while ((line = in.readLine()) != null) {
                i++;
                list.add(line);
                if (i == count) {
                    break;
                }
            }
            in.close();
        }
        return list;
    }

    public static LinesResult getLinesFromStream(String filePath, String encoding, int count, LinesResult lr)
            throws Exception {
        LinesResult r = new LinesResult();
        List<String> list = new ArrayList<String>();
        if (count != 0) {
            BufferedReader in;
            int i = 0;
            if (lr != null && lr.in != null) {
                in = lr.in;
                i = lr.i;
            } else {
                in = new BufferedReader(new UnicodeReader(new FileInputStream(filePath), encoding));
                r.in = in;
            }
            String line;
            boolean hasMore = false;
            while ((line = in.readLine()) != null) {
                i++;
                list.add(line);
                if (i == count) {
                    hasMore = true;
                    break;
                }
            }
            r.hasMore = hasMore;
            r.i = i;
            r.li = lr.i;
            r.lines = list;
            r.in = in;
            if (!r.hasMore)
                in.close();
        }
        return r;
    }

    public static void setLines(String filePath, List<String> lines) throws Exception {
        setLines(filePath, lines, determineEncoding(lines));
    }

    public static String determineEncoding(List<String> lines) {
        if (lines != null && !lines.isEmpty()) {
            String line = lines.get(0);
            if (isChinese(line)) {
                return "GBK";
            }
        }
        return "UTF-8";
    }

    public static void setLines(String filePath, List<String> lines, String encoding) throws Exception {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath, false), encoding));
        for (String line : lines) {
            out.println(line);
        }
        out.close();
    }

    public static void insertLines(String p, List<String> list, int i, String encoding) throws Exception {
        List<String> lines = getLines(p, encoding);
        lines.addAll(i - 1, list);
        setLines(p, lines, encoding);
    }

    public static void deleteLines(String p, List<String> list, int i, String encoding) throws Exception {
        List<String> lines = getLines(p, encoding);
        for (int j = 0; j < list.size(); j++) {
            lines.remove(i - 1);
        }
        setLines(p, lines, encoding);
    }

    public static void moveFiles(String from, String to) {
        if (exists(from)) {
            mkdirs(to);
            File fromFile = new File(from);
            File[] files = fromFile.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    file.renameTo(new File(to + FILE_SEPARATOR + file.getName()));
                } else {
                    moveFiles(file.getAbsolutePath(), to + FILE_SEPARATOR + file.getName());
                }
            }
            deleteFolderIfEmpty(from);
        }
    }

    public static void deleteFolderIfEmpty(String path) {
        if (exists(path)) {
            File file = new File(path);
            String[] list = file.list();
            while (list.length == 0) {
                file.delete();
                file = file.getParentFile();
                list = file.list();
            }
        }
    }

    public static boolean exists(String path) {
        File folder = new File(path);
        return folder.exists();
    }

    public static void mkdirs(String path) {
        File folder = new File(path);
        if (!folder.exists())
            folder.mkdirs();
    }

    public static String getParent(String path) {
        File file = new File(path);
        return file.getParent();
    }

    public static void distinctFile(String filePath) throws Exception {
        List<String> lines = getLines(filePath);
        List<String> list = new ArrayList<String>();
        for (String line : lines) {
            if (!list.contains(line))
                list.add(line);
        }
        setLines(filePath, list);
    }

    public static String getSpaces(int len) {
        return getRepeatingString(" ", len);
    }

    public static String getRepeatingString(String s, int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static List<String> toList(String string) {
        List<String> list = new ArrayList<String>();
        if (string != null) {
            list.add(string);
        }
        return list;
    }

    public static List<String> toList(String string, List<String> stringList) {
        List<String> list = new ArrayList<String>();
        list.add(string);
        list.addAll(stringList);
        return list;
    }

    public static void addBat(String n, String line) throws Exception {
        addBat(n, toList(line));
    }

    public static void addBat(String n, List<String> lines) throws Exception {
        String batFile = batDir + n + ".bat";
        setLines(batFile, lines);
        System.out.println("add bat file: " + batFile);
        for (String line : lines) {
            System.out.println("  " + line);
        }
    }

    public static void appendLine(String file, String line) throws Exception {
        appendLines(file, toList(line));
    }

    public static void appendLines(String file, List<String> lines) throws Exception {
        List<String> list = getLines(file);
        list.addAll(lines);
        setLines(file, list);
    }

    public static List<File> listFiles(File folder, boolean recursion, FilenameFilter filter) {
        if (!folder.exists())
            System.out.println(tab(2) + "folder does not exist: " + folder.getAbsolutePath());
        List<File> list = new ArrayList<File>();
        File[] files = folder.listFiles((FilenameFilter) null);
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (recursion)
                        list.addAll(listFiles(file, recursion, filter));
                }
                if (filter != null) {
                    if (filter.accept(folder, file.getName())) {
                        list.add(file);
                    }
                } else {
                    list.add(file);
                }
            }
        }
        return list;
    }

    public static List<File> listFiles(File folder, boolean recursion, FilenameFilter filter, Params params) {
        List<File> list = new ArrayList<File>();
        if (params.recursiveLevel < 0) {
            list = listFiles(folder, recursion, filter);
        } else {
            list = listFiles(folder, recursion, filter, params.recursiveLevel, 0);
        }
        list = filterFiles(list, params);
        sortFiles(list, params);
        return list;
    }

    private static List<File> filterFiles(List<File> list, Params params) {
        List<File> filtered = new ArrayList<File>();
        FileTimestamp ft = params.fileTimestamp;
        ListCondition lc = params.listCondition;
        for (File file : list) {
            if (ft != null && !ft.matches(file)) {
                continue;
            }
            if (lc != null && !lc.matches(file)) {
                continue;
            }
            filtered.add(file);
        }
        return filtered;
    }

    private static void sortFiles(List<File> list, Params params) {
        if (params.sortType != null) {
            if (params.sortType.equals("t")) { // time
                Collections.sort(list, new FileTimestampSorter());
                return;
            }
        }
        Collections.sort(list);
    }

    private static List<File> listFiles(File folder, boolean recursion, FilenameFilter filter, int recursiveLevel,
            int depth) {
        if (!folder.exists())
            System.out.println(tab(2) + "folder does not exist: " + folder.getAbsolutePath());
        List<File> list = new ArrayList<File>();
        File[] files = folder.listFiles((FilenameFilter) null);
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (recursion) {
                        if (depth + 1 <= recursiveLevel) {
                            list.addAll(listFiles(file, recursion, filter, recursiveLevel, depth + 1));
                        }
                    }
                }
                if (filter != null) {
                    if (filter.accept(folder, file.getName())) {
                        list.add(file);
                    }
                } else {
                    list.add(file);
                }
            }
        }
        return list;
    }

    public static List<File> listFiles(File folder, boolean recursion) {
        return listFiles(folder, recursion, null);
    }

    public static List<File> listFiles(File folder) {
        return listFiles(folder, false);
    }

    public static List<File> listFiles(File folder, final String ext) {
        return listFiles(folder, ext, false);
    }

    public static List<File> listFiles(File folder, final String ext, boolean recursion) {
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(ext);
            }
        };
        return listFiles(folder, recursion, filter);
    }

    public static List<File> listJarFiles(File folder) {
        return listFiles(folder, ".jar");
    }

    public static List<File> listPropertiesFiles(File folder) {
        return listFiles(folder, ".properties");
    }

    public static List<File> listXmlFiles(File folder) {
        return listFiles(folder, ".xml");
    }

    public static List<File> listTxtFiles(File folder) {
        return listFiles(folder, ".txt");
    }

    public static List<File> listJavaFiles(File folder) {
        return listFiles(folder, ".java");
    }

    public static List<File> listClassFiles(File folder) {
        return listFiles(folder, ".class");
    }

    public static List<File> listFolders(File folder) {
        List<File> list = new ArrayList<File>();
        File[] files = folder.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files)
                if (file.isDirectory())
                    list.add(file);
        }
        return list;
    }

    public static List<String> listJavaClasses(String dir, String className) throws Exception {
        JavaSearcher js = new JavaSearcher();
        return js.searchAll(className, dir);
    }

    public static void sortRN() throws Exception {
        List<String> list = getLines(rn);
        Collections.sort(list);
        for (String s : list) {
            System.out.println(s);
        }
    }

    public static Properties getProperties(String propertyFile) throws Exception {
        return getProperties(new File(propertyFile));
    }

    public static Properties getProperties(File propertyFile) throws Exception {
        try {
            FileInputStream fis = new FileInputStream(propertyFile);
            Properties props = new Properties();
            props.load(fis);
            fis.close();
            return props;
        } catch (Exception e) {
            throw new Exception("Cannot load properties: " + propertyFile, e);
        }
    }

    public static Properties getPropertiesFromThread(String propertyFile) throws Exception {
        try {
            InputStream fis = getResourceAsStream(propertyFile);
            Properties props = new Properties();
            props.load(fis);
            fis.close();
            return props;
        } catch (Exception e) {
            throw new Exception("Cannot load properties: " + propertyFile, e);
        }
    }

    public static InputStream getResourceAsStream(String resourceName) throws Exception {
        InputStream is = null;
        ClassLoader cl = null;
        // first try to load the resource with context class loader
        if (is == null) {
            cl = Thread.currentThread().getContextClassLoader();
            is = cl.getResourceAsStream(resourceName);
        }
        // second try to load the resource with foundation class loader
        if (is == null) {
            cl = Util.class.getClassLoader();
            is = cl.getResourceAsStream(resourceName);
        }
        // throw out exception if not found
        if (is == null) {
            throw new Exception("Cannot load resource: " + resourceName);
        }
        return is;
    }

    public static <T> void addWithoutDup(List<T> list1, List<T> list2) {
        for (T t : list2) {
            addWithoutDup(list1, t);
        }
    }

    public static <T> void addWithoutDup(List<T> list1, T t) {
        if (!list1.contains(t)) {
            list1.add(t);
        }
    }

    public static <T> List<T> copyList(List<T> list) {
        List<T> list2 = new ArrayList<T>();
        list2.addAll(list);
        return list2;
    }

    public static <T> List<T> arrayToList(T[] arr) {
        List<T> list = new ArrayList<T>();
        list.addAll(Arrays.asList(arr));
        return list;
    }

    public static String[] listToArray(List<String> list) {
        return list.toArray(new String[list.size()]);
    }

    public static <K, V> void addWithoutDup(Map<K, V> map1, Map<K, V> map2) {
        for (Entry<K, V> entry : map2.entrySet()) {
            addWithoutDup(map1, entry);
        }
    }

    public static <K, V> void addWithoutDup(Map<K, V> map1, Map.Entry<K, V> entry) {
        if (!map1.containsKey(entry.getKey())) {
            map1.put(entry.getKey(), entry.getValue());
        }
    }

    public static <K, V> Map<K, V> copyMap(Map<K, V> map) {
        Map<K, V> map2 = new HashMap<K, V>();
        map2.putAll(map);
        return map2;
    }

    public static void copyFile(String from, String to) throws Exception {
        copyFile(from, to, true);
    }

    public static void copyFile(String from, String to, boolean log) throws Exception {
        int size = 64 * 1024; // 64 KB
        FileInputStream fis = new FileInputStream(from);
        mkdirs(getParent(to));
        FileOutputStream fos = new FileOutputStream(to, false);
        byte[] buf;
        while (fis.available() > 0) {
            buf = new byte[Math.min(fis.available(), size)];
            fis.read(buf);
            fos.write(buf);
        }
        fos.close();
        fis.close();
        setFileTimestamp(to, getFileTimestamp(from));
        if (log)
            System.out.println("copy: " + from + " to " + to);
    }

    public static void deleteFile(String path) {
        if (path != null && exists(path)) {
            File file = new File(path);
            file.delete();
        }
    }

    public static void deleteFolder(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFolder(files[i]);
                }
            }
            file.delete();
        }
    }

    public static void deleteFileWithFolders(String path) {
        deleteFile(path);
        deleteFolderIfEmpty(getParent(path));
    }

    public static String renameFile(String filePath, String from, String to) {
        String oldName = getFileName(filePath);
        if (oldName.contains(from)) {
            if (oldName.equals("rename.txt"))
                return filePath;
            String newName = oldName.replace(from, to);
            File file = new File(filePath);
            File parent = file.getParentFile();
            String parentPath = parent.getAbsolutePath();
            String newFilePath = parentPath + File.separator + newName;
            File newFile = new File(newFilePath);
            mkdirs(getParent(newFilePath));
            file.renameTo(newFile);
            deleteFolderIfEmpty(parentPath);
            return newFilePath;
        }
        return filePath;
    }

    public static ReplaceResult replaceFile(String filePath, Filters fromFilter, String from, String to, Params params)
            throws Exception {
        List<String> lines = getLines(filePath, params.getEncoding());
        List<String> list = new ArrayList<String>();
        List<Line> affected = new ArrayList<Line>();
        boolean changed = false;
        List<Line> foundLines = findInFile(filePath, fromFilter, params);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int pos = i + 1;
            if (containsInLine(line, fromFilter, from, params.caseSensitive, pos, params, foundLines)) {
                changed = true;
                String replaced = replaceInLine(line, from, to, params.caseSensitive);
                list.add(replaced);
                affected.add(new Line(pos, line, replaced, params));
            } else {
                list.add(line);
            }
        }
        if (changed) {
            setLines(filePath, list, params.getEncoding());
        }
        filePath = renameFile(filePath, from, to);
        ReplaceResult r = new ReplaceResult();
        r.changed = changed;
        r.filePath = filePath;
        r.affected = affected;
        return r;
    }

    private static boolean containsInLine(String line, Filters fromFilter, String from, boolean caseSensitive, int pos,
            Params params, List<Line> foundLines) {
        if (caseSensitive)
            return lineConstains(line, fromFilter, from, pos, params, foundLines);
        else
            return lineConstains(line, fromFilter, toLowerCase(from), pos, params, foundLines)
                    || lineConstains(line, fromFilter, toUpperCase(from), pos, params, foundLines)
                    || lineConstains(line, fromFilter, toCamelCase(from), pos, params, foundLines);
    }

    private static boolean lineConstains(String line, Filters fromFilter, String from, int pos, Params params,
            List<Line> foundLines) {
        if (!line.contains(from))
            return false;
        return fromFilter.accept(line, pos) || matchFoundLines(foundLines, line, pos);
    }

    private static boolean matchFoundLines(List<Line> foundLines, String line, int pos) {
        for (Line foundLine : foundLines) {
            if (foundLine.match(pos))
                return true;
        }
        return false;
    }

    private static String replaceInLine(String line, String from, String to, boolean caseSensitive) {
        String output = line;
        if (caseSensitive) {
            output = output.replace(from, to);
        } else { // camel
            output = output.replace(toLowerCase(from), toLowerCase(to));
            if (needUpper(from, to))
                output = output.replace(toUpperCase(from), toUpperCase(to));
            if (needCamel(from, to))
                output = output.replace(toCamelCase(from), toCamelCase(to));
        }
        return output;
    }

    private static boolean needUpper(String from, String to) {
        return !from.equals(toUpperCase(from)) || !to.equals(toUpperCase(to));
    }

    private static boolean needCamel(String from, String to) {
        return !from.equals(toCamelCase(from)) || !to.equals(toCamelCase(to));
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

    public static String getFileName(String path) {
        File file = new File(path);
        return file.getName();
    }

    public static String getFileSimpleName(String path) {
        String n = getFileName(path);
        if (n.contains("."))
            return cutBack(n, ".", null);
        return n;
    }

    public static String getFileExtName(String path) {
        String n = getFileName(path);
        return cutBack(n, null, ".");
    }

    public static long getFileTimestamp(String path) {
        File file = new File(path);
        return file.lastModified();
    }

    public static void setFileTimestamp(String path, long t) {
        File file = new File(path);
        file.setLastModified(t);
    }

    public static List<String> splitToList(String listString) {
        return splitToList(listString, ",");
    }

    public static List<String> splitToList(String listString, String separator) {
        List<String> list = new ArrayList<String>();
        if (listString != null) {
            String[] strings = listString.split(separator);
            for (String string : strings) {
                String item = string.trim();
                if (item != null && item.length() > 0) {
                    item = item.replace(SEPARATOR_PLACE_HOLDER, separator.trim());
                    list.add(item);
                }
            }
        }
        return list;
    }

    /**
     * find matches in given string with regex.
     */
    public static List<String> splitToListWithRegex(String listString, String regex) {
        List<String> list = new ArrayList<String>();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(listString);
        while (m.find()) {
            String g = m.group();
            list.add(g);
        }
        return list;
    }

    public static List<Line> findInFile(String p, String from, Params params) throws Exception {
        Filters f = Filters.getFilters(from, params);
        return findInFile(p, f, params);
    }

    public static List<Line> findInFile(String p, Filters f, Params params) throws Exception {
        String first = f.getFirstNoIgnore();
        List<Line> list = new ArrayList<Line>();
        LinesResult lr = new LinesResult();
        while (lr.hasMore) {
            lr = getLinesFromStream(p, params.getEncoding(), ABATCH, lr);
            List<String> lines = lr.lines;
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                int pos = lr.li + i + 1;
                if (f.accept(line, pos)) {
                    Line item = new Line(pos, line, params);
                    item.searchKey = first;
                    item.fillPrevNext(params, lines);
                    list.add(item);
                }
            }
        }
        return list;
    }

    public static class Line {

        public int i;
        public String line;
        public String replaced;
        public List<String> prev;
        public List<String> next;
        public Params params;
        public String searchKey;

        public Line(int i, String line, Params params) {
            this.i = i;
            this.line = line;
            this.params = params;
        }

        public Line(int i, String line, String replaced, Params params) {
            this.i = i;
            this.line = line;
            this.replaced = replaced;
            this.params = params;
        }

        public boolean match(int pos) {
            int from = i - getPrevLines();
            int to = i + getNextLines();
            return from <= pos && pos <= to;
        }

        private int getPrevLines() {
            if (prev != null)
                return prev.size();
            return 0;
        }

        private int getNextLines() {
            if (next != null)
                return next.size();
            return 0;
        }

        public void fillPrevNext(Params params, List<String> lines) {
            int eln = params.getExpandLines();
            if (eln > 0) {
                prev = new ArrayList<String>();
                next = new ArrayList<String>();
                int current = this.i % ABATCH - 1;
                for (int k = Math.max(current - eln, 0); k < current; k++) {
                    prev.add(lines.get(k));
                }
                for (int k = current + 1; k <= Math.min(current + eln, lines.size() - 1); k++) {
                    next.add(lines.get(k));
                }
            } else {
                boolean multipleLines = params.multipleLines;
                if (multipleLines) {
                    next = new ArrayList<String>();
                    int current = this.i % ABATCH - 1;
                    for (int k = current + 1; k <= lines.size() - 1; k++) {
                        String nextLine = lines.get(k);
                        MultipleLineResult r = stopMultipleLine(nextLine);
                        if (r.stop) {
                            if (r.next) {
                                next.add(nextLine);
                            }
                            break;
                        }
                        next.add(nextLine);
                    }
                }
            }
        }

        private MultipleLineResult stopMultipleLine(String nextLine) {
            MultipleLineResult r = new MultipleLineResult();
            r.stop = true;
            // log file
            if (isLogLine(line)) {
                r.stop = isLogLine(nextLine);
                r.next = false;
            }
            // ant target
            if (isAntTargetLine(line)) {
                r.stop = isAntTargetLine(nextLine);
                r.next = true;
            }
            // java method
            if (isJavaMethodLineStart(line)) {
                r.stop = isJavaMethodLineEnd(nextLine);
                r.next = true;
            }
            return r;
        }

        private boolean isLogLine(String s) {
            return s.length() > 10 && isDate(cutLast(s, s.length() - 10));
        }

        private boolean isAntTargetLine(String s) {
            s = s.trim();
            return s.startsWith("<target") || s.startsWith("</target>");
        }

        private boolean isJavaMethodLineStart(String s) {
            s = s.trim();
            return s.startsWith("public") || s.startsWith("private") || s.startsWith("protected");
        }

        private boolean isJavaMethodLineEnd(String s) {
            return s.trim().equals("}") && getIndentSize(s) == getIndentSize(line);
        }

        public void print(int tab, int indent) {
            print(tab, indent, false);
        }

        public void print(int tab, int indent, boolean noLineNumber) {
            print(tab + indent, prev, true);
            if (!noLineNumber) {
                System.out.println(tab(tab) + formatstr(i + ": ", indent) + line);
                if (params.markOccurrence) {
                    System.out.println(tab(tab + indent) + MarkOccurrenceResult.makeMarkLine(line, searchKey));
                }
                if (replaced != null)
                    System.out.println(tab(tab) + formatstr("  ->", indent) + replaced);
            } else {
                System.out.println(tab(tab) + line);
            }
            print(tab + indent, next, false);
        }

        private void print(int tab, List<String> l, boolean p) {
            if (l != null && !l.isEmpty()) {
                if (p)
                    System.out.println();
                for (String s : l) {
                    System.out.println(tab(tab) + s);
                }
                if (!p)
                    System.out.println();
            }
        }

        public List<String> toLines() {
            List<String> list = new ArrayList<String>();
            if (prev != null)
                list.addAll(prev);
            list.add(line);
            if (next != null)
                list.addAll(next);
            return list;
        }

        private class MultipleLineResult {
            public boolean stop;
            public boolean next;
        }
    }

    public static class LinesResult {
        public int li = 0;
        public int i = 0;
        public List<String> lines = new ArrayList<String>();
        public BufferedReader in = null;
        public boolean hasMore = true;
    }

    public static class ReplaceResult {
        public boolean changed;
        public String filePath;
        public List<Line> affected;

        public void logLines() {
            if (affected != null) {
                for (Line line : affected) {
                    line.print(6, 7);
                }
            }
        }
    }

    public static class FileTimestampSorter implements Comparator<File> {
        public int compare(File o1, File o2) {
            Long l1 = o1.lastModified();
            Long l2 = o2.lastModified();
            return -l1.compareTo(l2);
        }
    }

    public static String getIndent(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public static int getIndentSize(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ' ')
                return i;
        }
        return 0;
    }

    public static boolean isDate(String s) {
        try {
            sdfDay.parse(s);
            return true;
        } catch (ParseException e) {
        }
        return false;
    }

    public static String formatstr(String s, int n) {
        return formatstr(s, n, true);
    }

    public static String formatstr(String s, int n, boolean right) {
        int len = getUnicodeLength(s);
        if (n > len) {
            if (right)
                return s + getIndent(n - len);
            else
                return getIndent(n - len) + s;
        }
        return s;
    }

    public static boolean isTextFile(String filePath) {
        return isTextFile(new File(filePath));
    }

    public static boolean isTextFile(File file) {
        String name = file.getName();
        for (String suffix : txtList_) {
            if (name.endsWith(suffix))
                return true;
        }
        return false;
    }

    private static void initTxt() {
        addWithoutDup(txtList_, ".txt");
        addWithoutDup(txtList_, ".java");
        addWithoutDup(txtList_, ".xml");
        addWithoutDup(txtList_, ".xsd");
        addWithoutDup(txtList_, ".html");
        addWithoutDup(txtList_, ".ini");
        addWithoutDup(txtList_, ".properties");
        addWithoutDup(txtList_, ".bat");
        addWithoutDup(txtList_, ".project");
        addWithoutDup(txtList_, ".js");
        addWithoutDup(txtList_, ".jsp");
        addWithoutDup(txtList_, ".module");
        addWithoutDup(txtList_, ".component");
        addWithoutDup(txtList_, ".cmd");
        addWithoutDup(txtList_, ".sql");
        addWithoutDup(txtList_, ".lst");
        addWithoutDup(txtList_, ".log");
        addWithoutDup(txtList_, ".conf");
        addWithoutDup(txtList_, ".scala");
    }

    public static String getFirstArg(String[] args) {
        if (args.length > 0)
            return args[0];
        return null;
    }

    public static String getLastArg(String[] args) {
        if (args.length > 0)
            return args[args.length - 1];
        return null;
    }

    public static String[] cutFirstArg(String[] args) {
        int n = args.length;
        String[] args2 = new String[n - 1];
        for (int i = 0; i < args2.length; i++) {
            args2[i] = args[i + 1];
        }
        return args2;
    }

    public static String[] cutLastArg(String[] args) {
        int n = args.length;
        String[] args2 = new String[n - 1];
        for (int i = 0; i < args2.length; i++) {
            args2[i] = args[i];
        }
        return args2;
    }

    public static String getFirst(List<String> list) {
        return list.get(0);
    }

    public static List<String> cutFirst(List<String> list) {
        list.remove(0);
        return list;
    }

    public static boolean isFile(String p) {
        File f = new File(p);
        if (f.exists()) {
            return f.isFile();
        } else {
            String n = getFileName(p);
            return n.contains(".");
        }
    }

    public static boolean isInt(String s) {
        try {
            Integer.valueOf(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int toInt(String s) {
        return Integer.valueOf(s);
    }

    public static String tab(int n) {
        return getIndent(n);
    }

    public static String listToString(List<String> list) {
        return listToString(list, ", ");
    }

    public static String listToString(List<String> list, String separator) {
        return connectLines(list, separator);
    }

    public static String connectLines(String[] arr, String separator) {
        List<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList(arr));
        return connectLines(list, separator);
    }

    public static String connectLines(List<String> lines, String separator) {
        StringBuilder sb = new StringBuilder();
        if (lines != null) {
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (!separator.equals(" ")) {
                    line = line.replace(separator.trim(), SEPARATOR_PLACE_HOLDER);
                } else {
                    if (line.contains(" ")) {
                        line = "\"" + line + "\"";
                    }
                }
                sb.append(line);
                if (i < lines.size() - 1) {
                    sb.append(separator);
                }
            }
        }
        return sb.toString();
    }

    public static boolean isFilePattern(String pattern) {
        for (String ext : txtList_) {
            if (pattern.contains(ext))
                return true;
        }
        return false;
    }

    public static void compareAndDeleteSame(String p) throws Exception {
        String n = getFileName(p);
        if (n.contains("-2")) {
            String n2 = n.replace("-2", "");
            String dir = getParent(p);
            compareAndDeleteSame(dir, n, n2);
            deleteFolderIfEmpty(dir);
        }
    }

    public static void compareAndDeleteSame(String dir, String n, String n2) throws Exception {
        String p1 = dir + FILE_SEPARATOR + n;
        String p2 = dir + FILE_SEPARATOR + n2;
        if (isSameTextFile(p1, p2)) {
            System.out.println(format("delete same: {0}={1}", n, n2));
            deleteFile(p1);
            deleteFile(p2);
        }
    }

    public static boolean isSameTextFile(String p1, String p2) throws Exception {
        if (exists(p1) && exists(p2)) {
            List<String> l1 = Util.getLines(p1);
            List<String> l2 = Util.getLines(p2);
            return l1.containsAll(l2) && l2.containsAll(l1);
        }
        return false;
    }

    public static boolean isChinese(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static int getUnicodeLength(String s) {
        int len = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isChinese(c))
                len += 2;
            else
                len++;
        }
        return len;
    }

    public static boolean containsIgnoreCase(String s, String n) {
        return s.toLowerCase().contains(n.toLowerCase());
    }

}
