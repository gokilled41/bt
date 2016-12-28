package gzhou;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class BatUtil implements Constants {

    public static void createBatFiles() throws Exception {
        for (int i = 0; i < msBatArray.length; i++) {
            String s = msBatArray[i];
            String loc = msBatLocArray[i];
            String file = batDir + s + "sc.bat";
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
            out.println("call yodadir");
            out.print("call svn commit " + loc + " -m %1");
            out.close();
            System.out.println("write file: " + file);

            //            String dffile = batDir + s + "df.bat";
            //            String sdffile = batDir + s + "sdf.bat";
            //            File f = new File(dffile);
            //            f.renameTo(new File(sdffile));

            //            AppFileCopier.replace(sdffile, "d.patch", ".patch");
        }
    }

    public static void st(String prefix) throws Exception {
        svnbat(prefix, "st");
    }

    public static void sd(String prefix) throws Exception {
        svnbat(prefix, "sd");
    }

    public static void sup(String prefix) throws Exception {
        svnbat(prefix, "sup");
    }

    public static void sr(String prefix) throws Exception {
        svnbat(prefix, "sr");
    }

    private static void svnbat(String prefix, String cmd) throws Exception {
        String file = batDir + prefix + cmd + ".bat";
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
        out.println("call " + prefix + "dir");
        out.print("call " + cmd);
        out.close();
        System.out.println("write file: " + file);
    }
}
