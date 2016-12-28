package gzhou;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@SuppressWarnings("all")
public class JavaSearcher {

    private String classname;
    private String filetype;
    private String pathlist[];

    public JavaSearcher() {
        classname = null;
        filetype = "class";
        pathlist = new String[0];
    }

    public String search(String classname, String path) throws IOException {
        URL aurl[];
        String s;
        String args[] = new String[4];
        args[0] = "-n";
        args[1] = classname;
        args[2] = "-p";
        args[3] = path;
        buildParam(args);
        aurl = buildClasspath();
        s = classname.replace('\\', '/');
        s = (new StringBuilder(String.valueOf(s.replace('.', '/')))).append(filetype).toString();
        URL url;
        URLClassLoader urlclassloader = new URLClassLoader(aurl);
        url = urlclassloader.getResource(s);
        urlclassloader.close();
        if (url != null)
            return url.toString();
        return "null";
    }

    public List<String> searchAll(String classname, String path) throws IOException {
        URL aurl[];
        String s;
        String args[] = new String[4];
        args[0] = "-n";
        args[1] = classname;
        args[2] = "-p";
        args[3] = path;
        buildParam(args);
        aurl = buildClasspath();
        s = classname.replace('\\', '/');
        s = (new StringBuilder(String.valueOf(s.replace('.', '/')))).append(filetype).toString();
        URLClassLoader urlclassloader = new URLClassLoader(aurl);
        Enumeration<URL> urls = urlclassloader.getResources(s);
        List<String> list = new ArrayList<String>();
        while (urls.hasMoreElements()) {
            URL url;
            url = urls.nextElement();
            if (url != null)
                list.add(url.toString());
        }
        urlclassloader.close();
        return list;
    }

    private void buildParam(String as[]) {
        int i = as.length;
        for (int j = 0; j < i; j++)
            if (as[j].equals("-n"))
                classname = as[++j];
            else if (as[j].equals("-p"))
                pathlist = as[++j].split(";");
            else if (as[j].equals("-t")) {
                filetype = as[++j];
                if (filetype.equals("dir"))
                    filetype = "";
            }

        filetype = (new StringBuilder(".")).append(filetype).toString();
    }

    private URL[] buildClasspath() {
        ArrayList arraylist = new ArrayList();
        int i = pathlist.length;
        for (int j = 0; j < i; j++)
            if (!pathlist[j].endsWith("//") && !pathlist[j].endsWith("\\\\"))
                buildClasspath(((List) (arraylist)), new File(pathlist[j]));

        URL aurl[] = new URL[arraylist.size()];
        for (int k = 0; k < aurl.length; k++)
            try {
                File file = (File) arraylist.get(k);
                aurl[k] = file.toURL();
                String s = file.getCanonicalPath();
                if (s.endsWith(".jar") || s.endsWith(".zip"))
                    aurl[k] = new URL("jar", "", (new StringBuilder()).append(aurl[k]).append("!/").toString());
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            }

        return aurl;
    }

    private void buildClasspath(List list, File file) {
        list.add(file);
        if (file.isDirectory()) {
            File afile[] = file.listFiles();
            for (int i = 0; i < afile.length; i++) {
                File file1 = afile[i];
                String s = file1.getName();
                if (file1.isFile()) {
                    if (s.endsWith(".jar") || s.endsWith(".zip"))
                        list.add(file1);
                } else {
                    buildClasspath(list, file1);
                }
            }

        }
    }

}
