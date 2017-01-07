package gzhou;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {

    public static String format(String s) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(s.getBytes("UTF-8"));
        s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        return s;
    }

    public static String formatOneLine(String s) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(s.getBytes("UTF-8"));
        s = mapper.writeValueAsString(json);
        return s;
    }

}
