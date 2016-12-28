package gzhou;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class XmlPicker {

    public static String pick(String file) {
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = r.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
