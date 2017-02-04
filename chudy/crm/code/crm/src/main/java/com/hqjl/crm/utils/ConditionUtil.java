package com.hqjl.crm.utils;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConditionUtil {

    private static final Log logger_ = LogFactory.getLog(ConditionUtil.class);

    private static final String AND = "and";
    private static final String OR = "or";

    public static String toCondition(String s) {
        return toCondition(null, s);
    }

    public static String toCondition(String n, String s) {
        String r = doToCondition(n, s);
        if (logger_.isDebugEnabled()) {
            logger_.debug("condition util: " + s + " -> " + r);
        }
        return r;
    }

    private static String doToCondition(String n, String s) {
        n = (n == null ? "" : n);
        if (s.startsWith("^")) {
            s = s.substring(1);
            if (isWrapped(s)) {
                s = unwrap(s);
                if (s.contains(AND))
                    return n + "not " + toInCondition(s);
                else
                    return toOrCondition(n, s, false);
            } else {
                return n + "!= '" + s + "'";
            }
        } else if (s.contains(AND)) {
            return n + toInCondition(s);
        } else if (s.contains(OR)) {
            return toOrCondition(n, s, true);
        } else {
            return n + "= '" + s + "'";
        }
    }

    private static String toOrCondition(String n, String s, boolean b) {
        List<String> list = Util.splitToList(s, OR);
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < list.size(); i++) {
            sb.append(n + (b ? " = " : " != ") + "'" + list.get(i) + "'");
            if (i < list.size() - 1) {
                sb.append(" or ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private static boolean isWrapped(String s) {
        return s.startsWith("(") && s.endsWith(")");
    }

    private static String unwrap(String s) {
        return s.substring(1, s.length() - 1);
    }

    private static String toInCondition(String s) {
        List<String> list = Util.splitToList(s, AND);
        StringBuilder sb = new StringBuilder();
        sb.append("in (");
        for (int i = 0; i < list.size(); i++) {
            sb.append("'" + list.get(i) + "'");
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

}
