package com.hqjl.crm.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hqjl.crm.Constants;
import com.hqjl.crm.model.Auth;

public class Util implements Constants {

    private static Log logger_ = LogFactory.getLog(Util.class);

    public static String getApplyId(long count) {
        count = count + 1;
        if (count < 10)
            return "AS20160000" + count;
        else if (count < 100)
            return "AS2016000" + count;
        else if (count < 1000)
            return "AS201600" + count;
        else if (count < 10000)
            return "AS20160" + count;
        else
            return "AS2016" + count;
    }

    public static String formatSizeString(long size) {
        if (size < KB)
            return size + "B";
        else if (size < MB)
            return (size / KB) + "KB";
        else if (size < GB)
            return formatDouble(((double) size / MB), 1) + "MB";
        else
            return formatDouble(((double) size / GB), 1) + "GB";
    }

    public static String formatDouble(double d, int scale) {
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return bd.toString();
    }

    public static String getFileType(String ext) {
        if (ConfigUtil.getFileTypes().contains(ext))
            return ext;
        else
            return "unknown";
    }

    public static Object createObject(String className) {
        try {
            ClassLoader cl = Util.class.getClassLoader();
            Class c = cl.loadClass(className);
            return c.newInstance();
        } catch (Exception e) {
            if (getLogger().isDebugEnabled()) {
                getLogger().debug("Cannot load class: " + className);
            }
        }
        return null;
    }

    public static int getMaxLength(List<String> list) {
        int len = 0;
        for (String string : list) {
            if (len < string.length())
                len = string.length();
        }
        return len;
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

    public static String getStartIndent(String line) {
        int len = 0;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c != ' ') {
                len = i;
                break;
            }
        }
        return Util.getSpaces(len);
    }

    public static void mkdirs(String path) {
        File folder = new File(path);
        if (!folder.exists())
            folder.mkdirs();
    }

    public static boolean exists(String path) {
        File folder = new File(path);
        return folder.exists();
    }

    public static void deleteFile(String path) {
        if (path != null && exists(path)) {
            File file = new File(path);
            file.delete();
        }
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

    public static InputStream getResourceAsStream(String resourceName) throws Exception {
        try {
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
                throw new Exception("Cannot get resource as stream: " + resourceName);
            }
            return is;
        } catch (Exception e) {
            rethrowException("Cannot get resource as stream: " + resourceName, e);
        }
        return null;
    }

    public static void rethrowException(String message, Exception e) throws Exception {
        if (e instanceof Exception)
            throw (Exception) e;
        throw new Exception(message, e);
    }

    private static Log getLogger() {
        return logger_;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String toCanonicalPath(String path) {
        try {
            File f = new File(path);
            return f.getCanonicalPath();
        } catch (Exception e) {
            return path;
        }
    }

    public static <T> List<T> toList(T t) {
        List<T> list = new ArrayList<T>();
        if (t != null) {
            list.add(t);
        }
        return list;
    }

    public static <T> List<T> toList(T t, List<T> list2) {
        List<T> list = new ArrayList<T>();
        list.add(t);
        list.addAll(list2);
        return list;
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
            throw new Exception("Cannot load property file: " + propertyFile, e);
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
            throw new Exception("Cannot load property file: " + propertyFile, e);
        }
    }

    public static void saveProperties(Properties props, String propertyFile) throws Exception {
        saveProperties(props, propertyFile, null);
    }

    public static void saveProperties(Properties props, String propertyFile, String comments) throws Exception {
        try {
            FileOutputStream fos = new FileOutputStream(propertyFile);
            props.store(fos, comments);
            fos.close();
        } catch (Exception e) {
            throw new Exception("Cannot save properties to file: " + propertyFile, e);
        }
    }

    public static Properties toProperties(Map<String, Object> map) {
        Properties props = new Properties();
        for (Entry<String, Object> entry : map.entrySet()) {
            props.setProperty(entry.getKey(), entry.getValue().toString());
        }
        return props;
    }

    public static Map<String, Object> toMap(Properties props) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (String key : props.stringPropertyNames()) {
            map.put(key, props.get(key));
        }
        return map;
    }

    public static String format(String message, Object... objects) {
        return MessageFormat.format(message, objects);
    }

    public static String formatConfig(String c, Object... objects) {
        c = format(c, objects);
        c = c.replace("=", "$$=$$");
        c = c.replace(",", "$$,$$");
        return c;
    }

    public static boolean isStringEquals(String s1, String s2) {
        if (s1 == null)
            return s2 == null;
        return s1.equals(s2);
    }

    public static void copyFile(String from, String to) throws Exception {
        FileInputStream fis = new FileInputStream(from);
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        fis.close();
        if (bytes != null && bytes.length > 0) {
            FileOutputStream fos = new FileOutputStream(to, false);
            fos.write(bytes);
            fos.close();
        }
    }

    public static List<String> getLines(String filePath) throws Exception {
        return getLines(new FileInputStream(filePath), "UTF-8");
    }

    public static List<String> getLines(String filePath, String encoding) throws Exception {
        return getLines(new FileInputStream(filePath), encoding, -1);
    }

    public static List<String> getLines(String filePath, int count) throws Exception {
        return getLines(new FileInputStream(filePath), "UTF-8", count);
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
            BufferedReader in = new BufferedReader(new InputStreamReader(is, encoding));
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

    public static void setLines(String filePath, List<String> lines) throws Exception {
        setLines(filePath, lines, "UTF-8");
    }

    public static void setLines(String filePath, List<String> lines, String encoding) throws Exception {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath, false), encoding));
        for (String line : lines) {
            out.println(line);
        }
        out.close();
    }

    public static boolean isTextFile(String fileName) {
        List<String> txtList = new ArrayList<String>();
        txtList.add(".txt");
        txtList.add(".java");
        txtList.add(".xml");
        txtList.add(".ini");
        txtList.add(".properties");
        txtList.add(".project");
        txtList.add(".js");

        String name = fileName;
        for (String suffix : txtList) {
            if (name.endsWith(suffix))
                return true;
        }
        return false;
    }

    public static boolean isZipFile(String path) {
        return path.endsWith(".zip");
    }

    public static String getParent(String path) {
        File file = new File(path);
        return file.getParent();
    }

    public static String getFileName(String path) {
        File file = new File(path);
        return file.getName();
    }

    public static long getFileTimestamp(String path) {
        File file = new File(path);
        return file.lastModified();
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

    public static String listToString(List<String> list) {
        return listToString(list, ", ");
    }

    public static String listToString(List<String> list, String separator) {
        return connectLines(list, separator);
    }

    public static String connectLines(List<String> lines, String separator) {
        StringBuilder sb = new StringBuilder();
        if (lines != null) {
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                line = line.replace(separator.trim(), SEPARATOR_PLACE_HOLDER);
                sb.append(line);
                if (i < lines.size() - 1) {
                    sb.append(separator);
                }
            }
        }
        return sb.toString();
    }

    public static String resolvePath(String location, Object... objects) {
        return resolvePath(format(location, objects));
    }

    public static String resolveFilePath(String location) {
        if (location != null) {
            location = resolvePath(location);
            String fileName = getFileName(location);
            if (fileName.contains("*")) {
                location = findMatchedFile(location);
            }
            return location;
        }
        return null;
    }

    public static String resolveFilePath(String location, Object... objects) {
        return resolveFilePath(format(location, objects));
    }

    private static String findMatchedFile(String location) {
        String fileName = getFileName(location);
        String parent = getParent(location);
        String p = fileName;
        p = p.replace(".", "\\.");
        p = p.replace("*", ".*");
        File parentFolder = new File(parent);
        final String regex = p;
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.matches(regex);
            }
        };
        if (exists(parent)) {
            List<File> files = listFiles(parentFolder, false, filter);
            if (files != null && !files.isEmpty())
                return files.get(0).getAbsolutePath();
        }
        return null;
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

    public static List<File> listFiles(File folder, boolean recursion, FilenameFilter filter) {
        List<File> list = new ArrayList<File>();
        File[] files = folder.listFiles(filter);
        for (File file : files) {
            if (file.isDirectory()) {
                if (recursion)
                    list.addAll(listFiles(file, recursion, filter));
            } else {
                list.add(file);
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

    public static char toCharSequence(int i) {
        char index = (char) (i - 1 + 'a');
        return index;
    }

    public static int middleIndexOf(String message, String s) {
        int len = message.length();
        int half = len / 2;
        String start = message.substring(0, half);
        String end = message.substring(half);
        int i1IndexOf = start.lastIndexOf(s);
        int i1 = i1IndexOf;
        int i2IndexOf = end.indexOf(s);
        int i2 = i2IndexOf >= 0 ? i2IndexOf + half : i2IndexOf;
        if (i1IndexOf < 0) {
            if (i2IndexOf < 0)
                return -1;
            else
                return i2;
        } else {
            if (i2IndexOf < 0)
                return i1;
            else {
                if (half - i1 < i2 - half)
                    return i1;
                else
                    return i2;
            }
        }
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
        }
    }

    public static String getCurrentTimestamp() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH.mm.ss");
        return format.format(new Date());
    }

    public static String getCurrentTime() {
        return sdf.format(new Date());
    }

    public static Object[] toObjectArray(String[] params) {
        if (params != null) {
            Object[] arr = new Object[params.length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = params[i];
            }
            return arr;
        }
        return null;
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

    public static void cleanFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory())
                    cleanFolder(file.getAbsolutePath());
                else
                    file.delete();
            }
        }
        folder.delete();
    }

    public static void extractZip(String filePath, String folderPath) throws Exception {
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
            throw new Exception("Cannot extract zip file: " + filePath + " to folder: " + folderPath, e);
        }
    }

    public static boolean listContains(List<String> list, String item) {
        for (String string : list) {
            if (string.contains(item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmptyString(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean stringEquals(String s1, String s2) {
        if (isEmptyString(s1)) {
            return isEmptyString(s2);
        } else {
            return s1.equals(s2);
        }
    }

    public static boolean booleanEquals(boolean b1, boolean b2) {
        return b1 == b2;
    }

    public static boolean isFolderPath(String path) {
        if (path != null) {
            path = resolvePath(path);
            if (path.matches("[a-zA-Z]:(\\\\[\\w ]+)*"))
                return true;
            if (path.matches("\\w+(\\\\[\\w ]+)*"))
                return true;
            if (path.matches("(/\\w+)+"))
                return true;
            if (path.matches("/"))
                return true;
        }
        return false;
    }

    public static boolean isFilePath(String path) {
        if (path != null) {
            path = resolvePath(path);
            if (path.matches("[a-zA-Z]:(\\\\[\\w ]+)*(\\\\[\\w-\\.]*\\.\\w+)"))
                return true;
            if (path.matches("\\w+(\\\\[\\w ]+)*(\\\\[\\w-\\.]*\\.\\w+)"))
                return true;
            if (path.matches("(/\\w+)+(/[\\w-\\.]*\\.\\w+)"))
                return true;
            if (path.matches("/([\\w-\\.]*\\.\\w+)"))
                return true;
        }
        return false;
    }

    public static List<String> listCompareKept(List<String> oldList, List<String> newList) {
        List<String> list = new ArrayList<String>(oldList);
        list.retainAll(newList);
        return list;
    }

    public static List<String> listCompareRemoved(List<String> oldList, List<String> newList) {
        List<String> list = new ArrayList<String>(oldList);
        list.removeAll(newList);
        return list;
    }

    public static List<String> listCompareAdded(List<String> oldList, List<String> newList) {
        List<String> list = new ArrayList<String>(newList);
        list.removeAll(oldList);
        return list;
    }

    public static boolean listEquals(List<String> oldList, List<String> newList) {
        return oldList.containsAll(newList) && newList.containsAll(oldList);
    }

    public static void replaceInFolder(String folder, String from, String to) throws Exception {
        File folderFile = new File(folder);
        File[] files = folderFile.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                replaceInFile(file.getAbsolutePath(), from, to);
            } else {
                replaceInFolder(file.getAbsolutePath(), from, to);
            }
        }
    }

    public static void replaceInFile(String file, String from, String to) throws Exception {
        replaceInFile(file, from, to, -1, -1);
    }

    public static void replaceInFile(String file, String from, String to, int fromIndex, int toIndex) throws Exception {
        if (exists(file)) {
            file = renameFile(file, from, to);
            String fileName = getFileName(file);
            if (isTextFile(fileName)) {
                List<String> lines = getLines(file);
                List<String> newLines = LinesUtil.replaceInLines(lines, from, to, fromIndex, toIndex);
                if (!listEquals(lines, newLines)) {
                    setLines(file, newLines);
                }
            }
        }
    }

    private static String renameFile(String filePath, String from, String to) {
        String oldName = Util.getFileName(filePath);
        if (oldName.contains(from)) {
            String newName = oldName.replace(from, to);
            File file = new File(filePath);
            File parent = file.getParentFile();
            String parentPath = parent.getAbsolutePath();
            String newFolderPath = parentPath + File.separator + newName;
            File newFolder = new File(newFolderPath);
            file.renameTo(newFolder);
            return newFolderPath;
        }
        return filePath;
    }

    public static String formatTimestamp(Timestamp timestamp) {
        long l = timestamp.getTime();
        Date d = new Date(l);
        return sdf.format(d);
    }

    public static String formatHtml(String str) {
        if (str != null) {
            str = str.replace("\n", "<br>");
            str = str.replace(" ", "&nbsp;");
            return str;
        } else {
            return "";
        }
    }

    public static String formatTemplate(String s, Properties p) {
        for (String k : p.stringPropertyNames()) {
            s = s.replace("$$" + k + "$$", p.getProperty(k));
        }
        return s;
    }

    public static String getLogFolder() {
        return "D:\\huazhi\\projects\\coupon\\log";
    }

    public static String getConfFolder() {
        return "D:\\huazhi\\projects\\coupon\\conf";
    }

    public static String cutFirst(String s, int n) {
        if (s.length() > n)
            return s.substring(n);
        return "";
    }

    public static String cutLast(String s, int n) {
        if (s.length() > n)
            return s.substring(0, s.length() - n);
        return "";
    }

    public static String cut(String s, String from, String to) {
        if (from != null && !from.isEmpty())
            s = s.substring(s.indexOf(from) + from.length());
        if (to != null && !to.isEmpty())
            s = s.substring(0, s.indexOf(to));
        return s;
    }

    public static String toAuthString(Auth r) {
        return toAuthString(r.getApplyId(), r.getAuthRole(), r.getUserName());
    }

    public static String toAuthString(String applyId, String authRole, String userName) {
        StringBuilder sb = new StringBuilder();
        sb.append("Auth");
        sb.append("[");
        sb.append(applyId);
        sb.append("]");
        sb.append("[");
        sb.append(authRole);
        sb.append("]");
        sb.append("[");
        sb.append(userName);
        sb.append("]");
        return sb.toString();
    }

    public static boolean authContains(Map<String, Auth> checkedMap, String applyId, String authRole, String userName) {
        for (Auth auth : checkedMap.values()) {
            if (!auth.getApplyId().equals(applyId))
                continue;
            if (!auth.getUserName().equals(userName))
                continue;
            if (!auth.getAuthRole().contains(authRole))
                continue;
            return true;
        }
        return false;
    }

}
