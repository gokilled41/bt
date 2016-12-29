import gzhou.*;
import gzhou.FileUtil;
import gzhou.Util;
import gzhou.FileUtil.Params;
import gzhou.PerformanceOperator.*;

import java.io.*;
import java.lang.reflect.*;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.math.*;
import java.net.*;
import java.nio.*;
import java.security.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

import javax.management.*;
import javax.naming.*;
import javax.script.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.servlet.http.*;
import javax.sql.*;
import javax.xml.bind.*;
import javax.xml.transform.*;

import org.apache.commons.exec.*;
import org.apache.commons.lang.*;
import org.apache.commons.logging.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.FileSystem;
import org.apache.parquet.Log;
import org.apache.parquet.schema.GroupType;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;
import org.apache.parquet.schema.Type;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.hive.HiveContext;
import org.apache.spark.sql.hive.thriftserver.HiveThriftServer2;
import org.apache.xmlbeans.XmlDateTime;
import org.dmg.pmml.*;
import org.joda.time.*;
import org.joda.time.format.*;
import org.jpmml.model.*;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.w3c.dom.*;
import org.w3c.dom.Node;
import org.xml.sax.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.vitria.cluster.*;
import com.vitria.component.api.*;
import com.vitria.component.server.*;
import com.vitria.component.server.ServerManager;
import com.vitria.component.server.beans.*;
import com.vitria.dataflow.*;
import com.vitria.domainservice.*;
import com.vitria.domainservice.ResourceInfo;
import com.vitria.domainservice.beans.*;
import com.vitria.domainservice.persister.Registry;
import com.vitria.domainservice.persister.RegistryFactory;
import com.vitria.domainservice.util.*;
import com.vitria.dataflow.*;
//import com.vitria.dataflow.framework.util.FrameworkUtil;
//import com.vitria.dataflow.framework.util.JSONUtil;
//import com.vitria.ejb3.util.*;
import com.vitria.engine.client.*;
import com.vitria.fc.data.*;
import com.vitria.fc.utils.*;
import com.vitria.modeling.repository.sapi.*;
import com.vitria.naming.*;
import com.vitria.xquery.*;

@SuppressWarnings("all")
public class Test extends gzhou.Util {

    public static void main(String[] args) throws Exception {

        List<String> tests = new ArrayList<String>();
        tests.add("a");
        tests.add("/a");
        tests.add("\\a");
        tests.add("a/b");
        tests.add("a\\b");
        tests.add("\\a\\b");
        tests.add("a/b\\c");
        tests.add("a\\b/c");
        tests.add("\\a/b/c\\d/e");
        tests.add("Ser/Man/l100");
        tests.add("'Ser/Man'/l100");
        tests.add("'Ser/Man'/'[]()+'");
        tests.add("@abc.*@/l100");
        //        tests.add("\\*a/*b./c*.jar\\*d*.zip/e");
        //        tests.add("l100");
        //        tests.add("l-100");
        //        tests.add("l100-");
        //        tests.add("l100-200");
        //        tests.add("l-100a");
        //        tests.add("a100-");
        //        tests.add("a100-200a");
        //        tests.add("r0");
        //        tests.add("r1");
        //        tests.add("r10");
        //        tests.add("r20");
        //        tests.add("abc");
        //        tests.add("r");
        //        tests.add("a");
        //        tests.add("b");
        //        tests.add("a##b");
        //        tests.add("/a/b");
        //        tests.add("/a/b##\\b\\a");
        //        tests.add("/a/b");
        //        tests.add("Notification.3.0.zip");
        //        tests.add("13693680644");
        //        tests.add("a13693680644");

        for (String test : tests) {

            String s = test;

            FiltersPattern fp = new FiltersPattern();
            fp.p = test;

            Filters f = new Filters(fp, null);

            if (!Filters.appendFirstLeftIfNecessary(test).equals(f.print())) {
                System.out.println("fail: " + test);
            } else {
                System.out.println("succ: " + test);
            }

        }

    }

    public static class Filters {

        private List<Filters> filters = new ArrayList<Filters>();
        private List<FiltersPattern> subList;

        public Filters(FiltersPattern p, Params params) {
            init(p);
        }

        private void init(FiltersPattern p) {
            p.p = appendFirstLeftIfNecessary(p.p);
            subList = splitIntoSubList(p);
        }

        private String print() {
            StringBuilder sb = new StringBuilder();
            for (FiltersPattern p : subList) {
                sb.append(p.toString());
            }
            return sb.toString();
        }

        private List<FiltersPattern> splitIntoSubList(FiltersPattern p) {
            String s = p.p;
            boolean first = true;
            FiltersPattern item;
            List<FiltersPattern> list = null;
            StringBuilder sb = new StringBuilder();
            boolean quote = false;
            boolean regular = false;
            boolean sub = false;
            boolean include = false;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '\'') {
                    if (quote == false) {
                        quote = true;
                    } else {
                        quote = false;
                    }
                    sb.append(c);
                } else if (c == '@') {
                    if (regular == false) {
                        regular = true;
                    } else {
                        regular = false;
                    }
                    sb.append(c);
                } else if (c == '$') {
                    if (sub == false) {
                        sub = true;
                    } else {
                        sub = false;
                    }
                    sb.append(c);
                } else {
                    if (quote) {
                        sb.append(c);
                        continue;
                    }
                    if (regular) {
                        sb.append(c);
                        continue;
                    }
                    if (c == '/' || c == '\\') {
                        if (list != null) {
                            item = new FiltersPattern();
                            item.p = sb.toString();
                            item.include = include;
                            item.init();
                            list.add(item);
                            sb = new StringBuilder();
                            include = (c == '/');
                        } else {
                            list = new ArrayList<FiltersPattern>();
                            include = (c == '/');
                        }
                    } else {
                        sb.append(c);
                    }
                }
            }
            item = new FiltersPattern();
            item.p = sb.toString();
            item.include = include;
            item.init();
            list.add(item);
            sb = new StringBuilder();
            return list;
        }

        private static String appendFirstLeftIfNecessary(String p) {
            if (!p.startsWith("/") && !p.startsWith("\\"))
                p = "/" + p;
            return p;
        }

        public boolean accept(File dir, String name) {
            for (Filters filter : filters) {
                if (filter.accept(dir, name))
                    return true;
            }
            return false;
        }

        public boolean accept(String line, int pos) {
            for (Filters filter : filters) {
                if (filter.accept(line, pos))
                    return true;
            }
            return false;
        }

    }

    public static class FiltersPattern {
        public String p;
        public boolean include = true;
        public boolean quote = false;
        public boolean group = false;
        public boolean regular = false;

        public String toString() {

            StringBuilder sb = new StringBuilder();
            if (include)
                sb.append("/");
            else
                sb.append("\\");
            if (quote)
                sb.append("'" + p + "'");
            else if (group)
                sb.append("(" + p + ")");
            else if (regular)
                sb.append("@" + p + "@");
            else
                sb.append(p);
            return sb.toString();
        }

        public void init() {
            if (p.startsWith("'") && p.endsWith("'")) {
                quote = true;
                p = cutFirst(p, 1);
                p = cutLast(p, 1);
            } else if (p.startsWith("@") && p.endsWith("@")) {
                regular = true;
                p = cutFirst(p, 1);
                p = cutLast(p, 1);
            } else if (p.startsWith("(") && p.endsWith(")")) {
                group = true;
                p = cutFirst(p, 1);
                p = cutLast(p, 1);
            }
        }

    }

    public static class Filter {

        private List<String> matches = new ArrayList<String>();
        private List<String> excludes = new ArrayList<String>();
        private boolean ignoreFirst = false;
        private String firstKey;
        private Params params;

        // *a*/*b*\*c*
        public Filter(String ps) {
            this(ps, false);
        }

        public Filter(String ps, Params params) {
            this.params = params;
            init(ps);
        }

        public Filter(String ps, boolean ignoreFirst) {
            this.ignoreFirst = ignoreFirst;
            init(ps);
        }

        private void init(String s) {
            if (!s.startsWith("/") && !s.startsWith("\\"))
                s = "/" + s;

            boolean first = true;
            String item;
            List<String> list = null;
            StringBuilder sb0 = new StringBuilder();
            StringBuilder sb = new StringBuilder();
            boolean quote = false;
            boolean regular = false;
            boolean sub = false;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '\'') {
                    if (quote == false) {
                        quote = true;
                    } else {
                        quote = false;
                    }
                } else if (c == '@') {
                    if (regular == false) {
                        regular = true;
                    } else {
                        regular = false;
                    }
                } else if (c == '$') {
                    if (sub == false) {
                        sub = true;
                    } else {
                        sub = false;
                    }
                } else {
                    if (quote) {
                        sb0.append(c);
                        if (c == '\\') {
                            sb.append("\\\\");
                        } else if (c == '*') {
                            sb.append("\\#star#");
                        } else if (c == '+') {
                            sb.append("\\+");
                        } else if (c == '?') {
                            sb.append("\\?");
                        } else if (c == '$') {
                            sb.append("\\$");
                        } else if (c == '(') {
                            sb.append("\\(");
                        } else if (c == ')') {
                            sb.append("\\)");
                        } else if (c == '|') {
                            sb.append("\\|");
                        } else {
                            sb.append(c);
                        }
                        continue;
                    }
                    if (regular) {
                        sb0.append(c);
                        sb.append(c);
                        continue;
                    }
                    if (sub) {
                        sb0.append(c);
                        sb.append(c);
                        // TODO
                        continue;
                    }
                    if (c == '/') {
                        if (list != null) {
                            item = sb.toString();
                            if (first) {
                                firstKey = sb0.toString();
                                if (!ignoreFirst) {
                                    item = fixPattern(item, first);
                                }
                                first = false;
                            } else {
                                item = fixPattern(item, first);
                            }
                            list.add(item);
                            sb0 = new StringBuilder();
                            sb = new StringBuilder();
                        }
                        list = matches;
                    } else if (c == '\\') {
                        if (list != null) {
                            item = sb.toString();
                            if (first) {
                                firstKey = sb0.toString();
                                if (!ignoreFirst) {
                                    item = fixPattern(item, first);
                                }
                                first = false;
                            } else {
                                item = fixPattern(item, first);
                            }
                            list.add(item);
                            sb0 = new StringBuilder();
                            sb = new StringBuilder();
                        }
                        list = excludes;
                    } else {
                        sb0.append(c);
                        sb.append(c);
                    }
                }
            }
            item = sb.toString();
            if (first) {
                firstKey = sb0.toString();
                if (!ignoreFirst) {
                    item = fixPattern(item, first);
                }
            } else {
                item = fixPattern(item, first);
            }
            first = false;
            list.add(item);
            sb0 = new StringBuilder();
            sb = new StringBuilder();

            Params.log("Filter", format("{0} {1} {2}", s, matches, excludes));
        }

        public boolean accept(File dir, String name) {
            if (!params.noPath)
                name = dir.getAbsolutePath() + FILE_SEPARATOR + name;
            for (String m : matches) {
                if (!name.matches(m)) {
                    return false;
                }
            }
            for (String m : excludes) {
                if (name.matches(m)) {
                    return false;
                }
            }
            return true;
        }

        public boolean accept(String line, int pos) {
            for (String m : matches) {
                if (!matches(line, m, pos, params)) {
                    return false;
                }
            }
            for (String m : excludes) {
                if (matches(line, m, pos, params)) {
                    return false;
                }
            }
            return true;
        }

        private static String fixPattern(String filefrom, boolean first) {
            filefrom = filefrom.replace("`", "*").replace("~", "*");
            if (isContainsPatternNecessary(filefrom, first)) {
                // wrap * begins and ends like "*abc*". it means contains.
                if (!filefrom.startsWith("*"))
                    filefrom = "*" + filefrom;
                if (!filefrom.endsWith("*"))
                    filefrom = filefrom + "*";
            }
            // fix regular expression
            String result = filefrom.replace(".", "\\.").replace("*", ".*").replace("#star#", "*");
            return result;
        }

        private static boolean isContainsPatternNecessary(String filefrom, boolean first) {
            if (filefrom.startsWith("*")) // *abc
                return false;
            if (filefrom.endsWith("*")) // abc*
                return false;
            if (filefrom.matches("l\\d*-?\\d*")) // l100, l100-, l-200, l100-200, not first (first is search key)
                return false;
            if (filefrom.contains("##")) // a##b, it means a or b
                return false;
            return true;
        }
    }
}
