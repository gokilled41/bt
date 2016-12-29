import gzhou.*;
import gzhou.FileUtil;
import gzhou.Util;
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
//        tests.add("a");
//        tests.add("/a");
//        tests.add("\\a");
//        tests.add("a/b");
//        tests.add("a\\b");
//        tests.add("\\a\\b");
//        tests.add("a/b\\c");
//        tests.add("a\\b/c");
//        tests.add("\\a/b/c\\d/e");
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
        
        tests.add("rn");
        tests.add("rn/a");
        tests.add("rn\\a");
        
        for (String test : tests) {
            
            String s = test;
//            System.out.println(Arrays.toString(test.split("##")));
            // System.out.println(test.matches(".*Notification\\.\\d+\\.\\d+\\.zip.*"));
            System.out.println(test);
//            System.out.println(test.matches("[1][358][0-9]{9}"));
            System.out.println(FileUtil.toTARAlias(test));
            
//            System.out.println(test.matches("\\d*-?\\d*"));
//            if (test.matches("\\d*-\\d*")) {
//                int i = test.indexOf("-");
//                String from = test.substring(0, i);
//                String to = test.substring(i+1, test.length());
//                System.out.println(from);
//                System.out.println(to);
//            }
            
//            if (!s.startsWith("/") && !s.startsWith("\\")) s = "/" + s;
//            
//            List<String> matches = new ArrayList<String>();
//            List<String> excludes = new ArrayList<String>();
//            
//            List<String> list = null;
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < s.length(); i++) {
//                char c = s.charAt(i);
//                if (c == '/') {
//                    if (list != null) {
//                        list.add(sb.toString());
//                        sb = new StringBuilder();
//                    }
//                    list = matches;
//                } else if (c == '\\') {
//                    if (list != null) {
//                        list.add(sb.toString());
//                        sb = new StringBuilder();
//                    }
//                    list = excludes;
//                } else {
//                    sb.append(c);
//                }
//            }
//            list.add(sb.toString());
//            sb = new StringBuilder();
//            
//            System.out.println(format("{0} {1} {2}", s, matches, excludes));
        }
        

        
//        File f = new File(rndir + "\\a");
//        String[] arr = f.list();
//        System.out.println(arr);
//        for (String s : arr) {
//            System.out.println(s);
//        }
        
//        String s = "jdbc:mysql://PEK-WKST67766:3306/60ga";
//        System.out.println(cut(s, "//", null));
        
//        List<String> lines = Util.getLines("k");
//        String start = Util.getLines("kline").get(0);
//        int starti = 0;
//        for (String s : lines) {
//            if (s.startsWith(start)) {
//                break;
//            } else {
//                starti++;
//            }
//        }
//        long iccost = 0;
//        long ircost = 0;
//        long times = 0;
//        for (int i = starti + 1; i < lines.size(); i++) {
//            String line = lines.get(i);
//            if (line.contains("INFO  [SparkInterpreter] - interpret code [")) {
//                String cost = cut(line, "][ ", " ms ]").trim();
//                iccost += (Long.valueOf(cost));
//            }
//            if (line.contains("INFO  [SparkInterpreter] - scala> t2-t1:")) {
//                String cost = cut(line, "scala> t2-t1:", null).trim();
//                ircost += (Long.valueOf(cost));
//            }
//            if (line.contains("INFO  [SparkInterpreter] - interpret code [1]")) {
//                times++;
//            }
//        }
//        System.out.println("times: " + times);
//        if (times > 0) {
//            System.out.println("iccost: " + iccost / times);
//            System.out.println("ircost: " + ircost / times);
//            long compile = iccost - ircost;
//            System.out.println("compile: " + compile / times);
//        } else {
//            System.out.println("No Result");
//        }
        
    }

}
