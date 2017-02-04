package com.hqjl.crm.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigUtil {

    private static final Map<String, String> map = new HashMap<String, String>();;

    private static List<String> fileTypes;
    private static List<String> businessTypes;

    public static String get(String k) {
        return map.get(k);
    }

    public static void set(String k, String v) {
        map.put(k, v);
    }

    public static String getHQJLHome() {
        String v = get("HQJL_HOME");
        if (v == null)
            v = System.getenv("HQJL_HOME");
        if (v == null)
            v = "c:/temp";
        return v;
    }

    public static List<String> getFileTypes() {
        if (fileTypes == null) {
            fileTypes = Util.splitToList(get("fileTypes"));
        }
        return fileTypes;
    }

    public static List<String> getBusinessTypes() {
        if (businessTypes == null) {
            businessTypes = Util.splitToList(get("businessTypes"));
        }
        return businessTypes;
    }

    public static String getSWFToolsDir() {
        return get("SWFToolsDir");
    }

    public static String getOpenOfficeDir() {
        return get("OpenOfficeDir");
    }

    public static String getCrmUrl() {
        return get("crmUrl");
    }
}
