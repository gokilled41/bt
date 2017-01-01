package gzhou;

import java.io.IOException;
import java.io.InputStream;

public abstract class ResourceLoadUtil {

    static InputStream loadResourceAsStream(String path) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (is == null)
            throw new RuntimeException("resource with path '" + path + "' can't located");
        return is;
    }

    static byte[] loadResourceAsByteArray(String path) throws IOException {
        InputStream is = loadResourceAsStream(path);
        int len = is.available();
        byte[] rtn = new byte[len];
        is.read(rtn);
        is.close();
        return rtn;
    }

    public static String loadResourceAsString(String path) throws IOException {
        byte[] resourceBytes = loadResourceAsByteArray(path);
        String res = new String(resourceBytes);
        return res;
    }
}
