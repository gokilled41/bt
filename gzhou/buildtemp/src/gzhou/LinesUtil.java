// Copyright (c) 2014 Vitria Technology, Inc.
// All Rights Reserved.
//
package gzhou;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class LinesUtil {

    public static List<String> getLines(String filePath) throws Exception {
        return getLines(new FileInputStream(filePath));
    }

    public static List<String> getLines(InputStream is) throws Exception {
        List<String> list = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = in.readLine()) != null) {
            list.add(line);
        }
        in.close();
        return list;
    }

    public static void setLines(String filePath, List<String> lines) throws Exception {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath, false)));
        for (String line : lines) {
            out.println(line);
        }
        out.close();
    }

    public static void addLines(List<String> lines, int position, List<String> newLines, boolean after) {
        if (after)
            lines.addAll(position + 1, newLines);
        else
            lines.addAll(position, newLines);
    }

    public static void removeLines(List<String> lines, List<String> newLines) {
        String firstNewLine = newLines.get(0);
        int size = newLines.size();
        int position = indexOf(lines, firstNewLine);
        for (int i = 0; i < size; i++) {
            lines.remove(position);
        }
    }

    public static void removeLines(List<String> lines, int position, int size) {
        for (int i = 0; i < size; i++) {
            lines.remove(position);
        }
    }

    public static int indexOf(List<String> lines, String... conditions) {
        int i = 0;
        for (String line : lines) {
            if (matchLine(line, conditions))
                return i;
            i++;
        }
        return -1;
    }

    public static int lastIndexOf(List<String> lines, String... conditions) {
        for (int i = lines.size() - 1; i >= 0; i--) {
            String line = lines.get(i);
            if (matchLine(line, conditions))
                return i;
        }
        return -1;
    }

    public static int indexOfInFile(String filePath, String... conditions) throws Exception {
        return indexOf(getLines(filePath), conditions);
    }

    public static String insertInLine(String line, int position, String s) {
        if (position < 0)
            position += line.length();
        String a = line.substring(0, position);
        String b = line.substring(position);
        String newLine = Util.format("{0}{1}{2}", a, s, b);
        return newLine;
    }

    private static boolean matchLine(String line, String[] conditions) {
        for (String condition : conditions) {
            boolean exclude = false;
            if (condition.startsWith("\\")) {
                exclude = true;
                condition = condition.substring(1);
            }
            if (exclude && line.contains(condition))
                return false;
            if (!exclude && !line.contains(condition))
                return false;
        }
        return true;
    }

    public static List<String> replaceInLines(List<String> lines, String from, String to) {
        return replaceInLines(lines, from, to, -1, -1);
    }

    public static List<String> replaceInLines(List<String> lines, String from, String to, int fromIndex, int toIndex) {
        List<String> list = new ArrayList<String>();
        if (fromIndex == -1)
            fromIndex = 0;
        if (toIndex == -1)
            toIndex = lines.size();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String outputLine = line;
            if (i >= fromIndex && i < toIndex) {
                outputLine = line.replace(from, to);
            }
            list.add(outputLine);
        }
        return list;
    }

    public static List<String> subLines(List<String> lines, int fromIndex, int toIndex) {
        List<String> list = new ArrayList<String>();
        if (fromIndex == -1)
            fromIndex = 0;
        if (toIndex == -1)
            toIndex = lines.size();
        for (int i = 0; i < lines.size(); i++) {
            if (i >= fromIndex && i < toIndex) {
                list.add(lines.get(i));
            }
        }
        return list;
    }

}
