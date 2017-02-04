package com.hqjl.crm.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class HTTPUtil {

    public static String getRequestURLWithParameters(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        Enumeration<String> names = request.getParameterNames();
        List<String> list = new ArrayList<String>();
        while (names.hasMoreElements()) {
            String n = names.nextElement();
            list.add(n + "=" + request.getParameter(n));
        }
        for (int i = 0; i < list.size(); i++) {
            url.append((i == 0 ? "?" : "&"));
            url.append(list.get(i));
        }
        return url.toString();
    }

}
