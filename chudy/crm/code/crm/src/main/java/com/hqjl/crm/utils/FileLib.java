package com.hqjl.crm.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * The FileLib class provides useful functions for dealing with files
 * in a multiple operating system  environment.
 */
public abstract class FileLib {
    private static final String DOT = "."; //NOI18N

    /**
     * fixSlashes replaces / or \ with the correct separator
     * and removes leading and trailing separators
     *
     * @param s The string to fix.
     * @return <code>String</code> A string with / or \ replaced with the
     *         correct separator and leading and trailing separators removed.
     */
    public static String fixSlashes(String s) {
        return fixSlashes(s, true);
    }

    /**
     * fixSlashes replaces / or \ with the correct separator
     *
     * @param s The string to fix.
     * @param remove removes leading and trailing separator if true
     * @return <code>String</code> A string with / or \ replaced with the
     *         correct separator,
     *         and if <code>remove</code> is true,
     *         leading and trailing separators removed.
     */
    public static String fixSlashes(String s, boolean remove) {
        if (s == null) {
            return null;
        }

        // change / to \ or \ to / as necessary
        if (File.separatorChar == '/') {
            s = s.replace('\\', File.separatorChar);
        } else if (File.separatorChar == '\\') {
            s = s.replace('/', File.separatorChar);
        } else {
            throw badSeparator();
        }

        if (remove) {
            // remove leading separators
            while ((s.length() > 0) && (s.charAt(0) == File.separatorChar)) {
                s = s.substring(1);
            }
            int len;
            // remove trailing separators
            while (((len = s.length()) > 0) && (s.charAt(len - 1) == File.separatorChar)) {
                s = s.substring(0, len - 1);
            }
        }
        return s;
    }

    /**
     * fixPath replaces ; or : with the correct separator
     * and removes leading and trailing separators
     *
     * @param s The string to fix.
     * @return <code>String</code> InsertOtherCommentsHere
     * @return <code>String</code> A string with ; or : replaced with the
     *         correct separator and leading and trailing separators removed.
     */
    public static String fixPath(String s) {
        return fixPath(s, true);
    }

    /**
     * fixPath replaces ; or : with the correct separator
     *
     * @param s The string to fix.
     * @param remove removes leading and trailing separator if true
     * @return <code>String</code> A string with ; or : replaced with the
     *         correct separator,
     *         and if <code>remove</code> is true,
     *         leading and trailing separators removed.
     */
    public static String fixPath(String s, boolean remove) {
        if (s == null) {
            return null;
        }

        // change ; to : or : to ; as necessary
        if (File.pathSeparatorChar == ':') {
            s = s.replace(';', File.pathSeparatorChar);
        } else if (File.pathSeparatorChar == ';') {
            // make sure not to replace String like c:\abc
            char[] ca = new char[s.length()];
            s.getChars(0, s.length(), ca, 0);
            for (int i = 0; i < ca.length; i++) {
                if (ca[i] == ':') {
                    if (i == ca.length - 1) { // if last char, just replace
                        ca[i] = File.pathSeparatorChar;
                    } else if (ca[i + 1] != '\\') {
                        ca[i] = File.pathSeparatorChar;
                    }
                }
            }
            s = new String(ca);
        } else {
            throw badSeparator();
        }

        if (remove) {
            // remove leading separators
            while ((s.length() > 0) && (s.charAt(0) == File.pathSeparatorChar)) {
                s = s.substring(1);
            }
            int len;
            // remove trailing separators
            while (((len = s.length()) > 0) && (s.charAt(len - 1) == File.pathSeparatorChar)) {
                s = s.substring(0, len - 1);
            }
        }
        return s;
    }

    /**
     * replaces / or \ with the correct separator and
     * replaces ; or : with the correct separator
     *
     * @param s The string to fix.
     * @param remove removes leading and trailing separator if true
     * @return <code>String</code> A string with / \ ; or : replaced with the
     *         correct separator,
     *         and if <code>remove</code> is true,
     *         leading and trailing separators removed.
     */
    public static String fixSlashesAndPath(String s, boolean remove) {
        if (s == null) {
            return null;
        }
        // fix slashes first so c:\ will not turn into c;\
        return fixPath(fixSlashes(s, remove), remove);
    }

    /**
     * Replace ., / or \ with the correct separator
     * and removes leading and trailing separators.
     *
     * @param s The string to fix.
     * @return <code>String</code> A string with / \ or , replaced with the
     *         correct separator,
     *         and leading and trailing separators removed.
     */
    public static String fixDotsAndSlashes(String s) {
        if (s == null) {
            return null;
        }
        s = s.replace('.', File.separatorChar);
        // change / to \ or \ to / as necessary
        if (File.separatorChar == '/') {
            s = s.replace('\\', File.separatorChar);
        } else if (File.separatorChar == '\\') {
            s = s.replace('/', File.separatorChar);
        } else {
            throw badSeparator();
        }

        // remove leading separators
        while (s.length() > 0 && s.charAt(0) == File.separatorChar) {
            s = s.substring(1);
        }
        // remove trailing separators
        int len;
        while ((len = s.length()) > 0 && s.charAt(len - 1) == File.separatorChar) {
            s = s.substring(0, len - 1);
        }
        return s;
    }

    /**
     * slashesToDots replaces '/'s or '\'s with '.'s
     * and removes leading and trailing '.'s
     *
     * @param s The string to fix.
     * @return <code>String</code> InsertOtherCommentsHere
     * @return <code>String</code> A string with '/ 's and '\'s replaced
     *         with '.'s and leading and trailing '.'s removed.
     */
    public static String slashesToDots(String s) {
        if (s == null) {
            return null;
        }

        s = s.replace('\\', '.');
        s = s.replace('/', '.');

        // remove leading '.'
        while (s.length() > 0 && s.charAt(0) == '.') {
            s = s.substring(1);
        }

        // remove trailing separators
        int len;
        while ((len = s.length()) > 0 && s.charAt(len - 1) == '.') {
            s = s.substring(0, len - 1);
        }
        return s;
    }

    /**
     * Strips the specified extension from the specified string.
     * If string does not end with the specified extension then 
     * it returns the passsed string after trimming.
     * 
     * @param str
     * @param ext
     * @return
     */
    public static String stripExt(String str, String ext) {
        String strTrimmed = str.trim();
        String extTrimmed = ext.trim();
        if (!strTrimmed.endsWith(DOT + extTrimmed)) {
            return strTrimmed;
        }
        int idx = str.lastIndexOf(DOT);
        return str.substring(0, idx);
    }

    /**
     * Strip the last component from a string representing a name.
     * E.g., for "a/b/c" return "a/b".  Note if the string has a trailing
     * separator then this operation returns the whole name.  E.g., for "a/b/"
     * we return "a/b".
     *
     * If the name does not have a separator then return null.
     *
     * @param name The name from which to remove the last component.
     * @return <code>String</code> name with the last component removed.
     */
    public static String stripLast(String name) {
        int index = name.lastIndexOf('/');
        if (index == -1) {
            index = name.lastIndexOf('\\');
            if (index == -1) {
                return null;
            }
        }
        return name.substring(0, index);
    }

    /**
     * Get the last component from a string representing a name.
     * E.g., for "a/b/c" return "c".
     *
     * If the name does not have a separator then return the name.
     *
     * @param name The name from which to return the last component
     * @return <code>String</code> The last component of <code>name</code>.
     */
    public static String getLast(String name) {
        int index = name.lastIndexOf('/');
        if (index == -1) {
            index = name.lastIndexOf('\\');
            if (index == -1) {
                return name;
            }
        }
        return name.substring(index + 1);
    }

    private static RuntimeException badSeparator() {
        return new IllegalArgumentException("bad separator");
    }

    // used to break up the lines in a file for matching patterns
    private static Pattern linePattern_ = Pattern.compile(".*\r?\n|.*$");

    // Charset and decoder for UTF-8
    private static Charset charset_ = Charset.forName("UTF-8");//ISO-8859-15
    private static CharsetDecoder charsetDecoder_ = charset_.newDecoder();

    /**
     * Get a list of files present in the specified directory that contain
     * Strings matching the supplied pattern. Optionally a filter can be
     * supplied to further narrow down the list of files to look at.
     *
     * @param dirName Absolute path to the top level directory to look at.
     * @param patternToMatch The regular expression specifying the pattern.
     * @param filter The filter to further narrow the search.
     * @return A list of File objects that contains the matching pattern.
     *         This can be an empty list if no matches are found.
     * @throws IOException if there are errors reading the directory or files
     * @throws PatternSyntaxException If the syntax of the supplied pattern
     *                                is incorrect.
     * @vtexclude
     */
    public static List getMatchingFiles(String dirName, String patternToMatch, FileFilter filter)
            throws IOException, PatternSyntaxException {
        List matchedFiles = new ArrayList();

        if (dirName == null || patternToMatch == null) {
            throw new IllegalArgumentException();
        }

        File rootDir = new File(dirName);
        File[] filesToLookAt = rootDir.listFiles(filter);

        // Pattern used to parse lines
        Pattern suppliedPattern = Pattern.compile(patternToMatch);

        List matchedLinesInFile = null;
        if (filesToLookAt != null) {
            for (int i = 0; i < filesToLookAt.length; i++) {

                // recurse if it is a directory
                if (filesToLookAt[i].isDirectory()) {
                    List allMatchedFilesFromThisDir = getMatchingFiles(filesToLookAt[i].getAbsolutePath(),
                            patternToMatch, filter);
                    matchedFiles.addAll(allMatchedFilesFromThisDir);
                } else {
                    matchedLinesInFile = grep(filesToLookAt[i], suppliedPattern);
                    if (matchedLinesInFile != null) {
                        matchedFiles.add(filesToLookAt[i]);
                    }
                }
            }
        }
        return matchedFiles;
    }

    /**
     * Search a file for a pattern and return a list of lines that match it.
     *
     * @param file
     * @param pattern
     * @return List of lines that match the pattern.
     * @throws IOException
     */
    private static List grep(File file, Pattern pattern) throws IOException {
        List matchedLines = null;
        CharSequence charSeq = null;
        Matcher patternMatcher = null;
        FileInputStream fileInputStr = null;

        try {
            // Open the file and then get a channel from the stream
            fileInputStr = new FileInputStream(file);
            FileChannel fileChannel = fileInputStr.getChannel();

            // Copy the file into a Buffer and decode it
            int sz = (int) fileChannel.size();
            ByteBuffer bf = ByteBuffer.allocate(sz);
            fileChannel.read(bf);
            bf.flip();
            CharBuffer buffer = charsetDecoder_.decode(bf);

            Matcher lineMatcher = linePattern_.matcher(buffer);
            int lines = 0;

            while (lineMatcher.find()) {
                lines++;
                charSeq = lineMatcher.group(); // The current line

                if (patternMatcher == null) {
                    patternMatcher = pattern.matcher(charSeq);
                } else {
                    patternMatcher.reset(charSeq);
                }
                if (patternMatcher.find()) {
                    if (matchedLines == null) {
                        matchedLines = new ArrayList();
                    }
                    matchedLines.add(charSeq.toString());
                    //CommonMessages.logGenericTrace("file: " + file.getAbsolutePath() + " line: "+ lines + ":" + charSeq);
                }
            }
        } catch (java.nio.charset.MalformedInputException ex) {
            // TODO: should log something?
        } finally {
            if (fileInputStr != null)
                fileInputStr.close();
        }
        return matchedLines;
    }

    /**
     * judge if a file exists.
     *
     * @param filename file name.
     * @return true if the file exists, otherwise return false.
     * @vtexclude
     */
    public static boolean ifFileExists(String filename) {
        File f = new File(filename);
        if (f.exists() && f.isFile()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * judge if a directory exists.
     *
     * @param filename file name.
     * @return true if the directory exists, otherwise return false.
     * @vtexclude
     */
    public static boolean ifDirectoryExists(String dir) {
        File f = new File(dir);
        if (f.exists() && f.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Copy a file or a directory to the specified target.
     *
     * @param source The source file. May be a directory.
     * @param target The target file or directory.
     * @param overwrite True for overwriting an existing one.
     * @throws IOException
     * @vtexclude
     */
    public static void copyFile(File source, File target, boolean overwrite) throws IOException {
        if (source == null || target == null) {
            throw new IllegalArgumentException();
        }
        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;
        if (!source.isDirectory()) {
            if (!target.exists() || overwrite) {
                try {
                    sourceChannel = new FileInputStream(source).getChannel();
                    if (!target.getParentFile().exists()) {
                        target.getParentFile().mkdirs();
                    }
                    destinationChannel = new FileOutputStream(target).getChannel();
                    sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
                } finally {
                    if (sourceChannel != null)
                        sourceChannel.close();
                    if (destinationChannel != null)
                        destinationChannel.close();
                }
            }
        } else {
            //recurse
            if (!target.exists()) {
                if (!target.mkdirs()) {
                    throw new IOException("Cannot create folder: " + target.getAbsolutePath());
                }
            }
            File[] kids = source.listFiles();
            for (int i = 0; i < kids.length; i++) {
                copyFile(kids[i], new File(target, kids[i].getName()), true);
            }
        }
        target.setLastModified(source.lastModified());
    }

    /**
     * Copy a file or a directory to the specified target. Overwrite
     * all if targets are already present.
     *
     * @param source The source file. May be a directory.
     * @param target The target file or directory.
     * @throws IOException
     * @vtexclude
     */
    public static void copyFile(File source, File target) throws IOException {
        copyFile(source, target, true); //overwrite = default.
    }

    /**
     * This is replacement API for java.io.File.renameTo(File) API. The renameTo
     * API has issues on Solaris platform. So we resort to this workaround of
     * copying the file and deleting the source.
     * 
     * NOTE: Like FileLib.copyFile(...) API this method does not support
     * folders. It supports moving files only.
     *  
     * @param source
     * @param target
     * @throws IOException
     * @vtexclude
     */
    public static void moveFile(File source, File target) throws IOException {
        if (source.isDirectory()) {
            throw new IllegalArgumentException("source is a folder - " //NOI18N 
                    + source.getAbsolutePath() + ". Moving folders is not supported.");//NOI18N
        }
        if (!source.exists()) {
            throw new IOException("source file does not exist. source=" + source.getAbsolutePath());
        }
        if (target.isDirectory()) {
            throw new IllegalArgumentException("target is a folder - "//NOI18N 
                    + target.getAbsolutePath() + ". Moving folders is not supported.");//NOI18N
        }
        File targetParentFile = target.getParentFile();
        if (!targetParentFile.exists()) {
            boolean success = targetParentFile.mkdirs();
            if (!success) {
                //Some known common cause of this problem is path name is 
                //too long than what the underlying Operating system can 
                //support. On Win XP it is 259 characters.
                throw new IOException("Cannot create folder structure for target file. Folder="//NOI18N
                        + targetParentFile.getAbsolutePath());
            }
        } else if (target.exists()) {
            boolean success = target.delete();
            if (!success) {
                throw new IOException("Cannot delete old file that already exists in the " + //NOI18N
                        "target location. File=" //NOI18N 
                        + target.getAbsolutePath());
            }
        }
        copyFile(source, target, true);
    }

    /**
     * Deletes all files and subdirectories under the supplied directory.
     * Returns true if all deletions were successful.
     * If a deletion fails, the method stops attempting to delete and
     * returns false.
     * @param dir
     * @return true if the directory could be successfully deleted
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
    }

    /**
     * Delete a file
     * @param file
     */
    public static void deleteFile(File file) throws Exception {
        file.delete();
    }

    /**
     * Checks if the given path ends with the specified extension. If not it 
     * adds the extension. If yes then it returns the passed string after
     * trimming.
     * 
     * @param str a java.lang.String instance to ensure.
     * @param ext a java.lang.String instance that represents the extension to
     * be checked for.
     * @return
     */
    public static String ensureEndsWithExt(String str, String ext) {
        String strRet = str.trim();
        String trimmedExt = ext.trim();
        if (strRet.endsWith(trimmedExt)) {
            return strRet;
        }
        if (strRet.endsWith(DOT)) {
            strRet += trimmedExt;
        } else {
            strRet += DOT + trimmedExt;
        }
        return strRet;
    }

    /**
     * Reads the contents of a text file into a string
     * @param textFile
     * @return String containing the contents of the text file.
     */
    public static String readTextFileContent(File textFile) throws FileNotFoundException, IOException {
        StringBuilder fileContent = new StringBuilder(1024);
        BufferedReader reader = new BufferedReader(new FileReader(textFile));
        try {

            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) > -1) {
                fileContent.append(buf, 0, numRead);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return fileContent.toString();
    }
}
