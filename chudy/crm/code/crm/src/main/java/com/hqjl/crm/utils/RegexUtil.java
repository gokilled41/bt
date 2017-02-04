package com.hqjl.crm.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    private RegexUtil() {
        super();
    }

    public static String getValue(String str, String regex, int group) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while (m.find()) {
            return m.group(group);
        }

        return null;
    }

    public static List<String> getAllValue(String input, String regex) {
        return getAllValue(input, regex, 0);
    }

    public static List<String> getAllValue(String input, String regex, int group) {
        if (StringUtil.isEmpty(input) || StringUtil.isEmpty(regex)) {
            return null;
        }

        List<String> result = new ArrayList<String>();
        Matcher matcher = Pattern.compile(regex).matcher(input);
        while (matcher.find()) {
            String str = matcher.group(group);
            result.add(str);
        }

        return result;
    }

    /**
     * 获得可匹配对�?
     * 
     * @param input
     * @param regex
     * @return
     */
    public static Matcher getMatcher(String input, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m;
    }

}
