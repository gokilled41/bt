package gzhou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class DiffFileCopier implements Constants {

    public static void main(String[] args) throws Exception {
        if (args != null && args.length > 0) {
            String command = args[0];
            if (command.equals("removeCompileClass")) {
                removeCompileClass();
            } else if (command.equals("copyDiffFiles")) {
                run();
            } else if (command.equals("copyBackFiles")) {
                copyBackFiles();
            }
        } else {
            run();
        }
    }

    public static void run() throws Exception {
        String prefix = YODA_DIR;
        String desktop = desktopDir;

        String file = desktopDir + "\\yodast.txt";

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        String line;
        while ((line = in.readLine()) != null) {
            if (line == null || line.length() == 0)
                continue;
            line = line.substring(1).trim();
            if (line == null || line.length() == 0)
                continue;

            String src = prefix + "\\" + line;
            String dest = desktop + line;
            try {
                copy(src, dest);
            } catch (FileNotFoundException e) {
                // ignore
            }
        }
        in.close();
    }

    public static void copyBackFiles() throws Exception {
        String prefix = YODA_DIR;
        String desktop = desktopDir;

        String file = desktopDir + "\\yodast.txt";

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        String line;
        while ((line = in.readLine()) != null) {
            if (line == null || line.length() == 0)
                continue;
            line = line.substring(1).trim();
            if (line == null || line.length() == 0)
                continue;

            String src = prefix + "\\" + line;
            String dest = desktop + line;
            try {
                copy(dest, src);
            } catch (FileNotFoundException e) {
                // ignore
            }
        }
        in.close();
    }

    public static void removeCompileClass() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(DIFF_TXT)));
        String line;
        while ((line = in.readLine()) != null) {
            if (line.startsWith("D    ")) {
                line = line.substring("D    ".length());
                if (line.startsWith("m3o\\server\\src\\")) {
                    line = line.substring("m3o\\server\\src\\".length());
                    if (line.endsWith(".java")) {
                        line = line.substring(0, line.length() - ".java".length()) + ".class";
                        String classFilePath = YODA_DIR + "\\export\\m3o\\classes\\" + line;
                        File classFile = new File(classFilePath);
                        if (classFile.exists()) {
                            classFile.delete();
                            System.out.println("remove class file: " + classFilePath);
                        }
                    }
                }
            }
        }
        in.close();
    }

    private static void copy(String src, String dest) throws Exception {
        check(dest);
        FileInputStream in = new FileInputStream(src);
        byte[] bytes = new byte[in.available()];
        in.read(bytes);
        FileOutputStream out = new FileOutputStream(dest);
        out.write(bytes);
        out.close();
        in.close();
        System.out.println("copy: " + src + " -->");
        System.out.println("      " + dest);
    }

    private static void check(String path) throws Exception {
        String[] nodes = path.split("\\\\");
        //System.out.println(Arrays.asList(nodes));

        String tmp = "";
        String[] pathes = new String[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            if (i == 0)
                tmp = nodes[i];
            else
                tmp = tmp + "\\\\" + nodes[i];
            pathes[i] = tmp;
        }

        //System.out.println(Arrays.asList(pathes));

        for (int i = 0; i < pathes.length; i++) {
            File f = new File(pathes[i]);
            if (!f.exists()) {
                if (pathes[i].indexOf(".") > -1 && !isFiltered(pathes[i]))
                    f.createNewFile();
                else
                    f.mkdir();
                //System.out.println("Create " + pathes[i]);
            }
        }
    }

    private static boolean isFiltered(String name) {
        name = (new File(name)).getName();
        if (name.equals("activemq_5.9_libmodule"))
            return true;
        return false;
    }
}
