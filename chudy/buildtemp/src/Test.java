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
import java.util.regex.*;

import javax.management.*;
import javax.naming.*;
import javax.script.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.servlet.http.*;
import javax.sql.*;
import javax.xml.bind.*;
import javax.xml.transform.*;

//import org.apache.commons.exec.*;
//import org.apache.commons.lang.*;
//import org.apache.commons.logging.*;
//import org.apache.hadoop.conf.*;
//import org.apache.hadoop.fs.*;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.parquet.Log;
//import org.apache.parquet.schema.GroupType;
//import org.apache.parquet.schema.MessageType;
//import org.apache.parquet.schema.MessageTypeParser;
//import org.apache.parquet.schema.Type;
//import org.apache.spark.SparkConf;
//import org.apache.spark.SparkContext;
//import org.apache.spark.sql.hive.HiveContext;
//import org.apache.spark.sql.hive.thriftserver.HiveThriftServer2;
//import org.apache.xmlbeans.XmlDateTime;
//import org.dmg.pmml.*;
//import org.joda.time.*;
//import org.joda.time.format.*;
//import org.jpmml.model.*;
//import org.slf4j.bridge.SLF4JBridgeHandler;
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
        
        List<String> lines = getLines(rn);
        List<String> lines2 = getLines(rn);
        List<String> list = new ArrayList<String>();
        Map<String, Integer> map = new HashMap<String, Integer>();
        Map<String, Integer> costs = new HashMap<String, Integer>();
        for (String s : lines) {
            if (s.contains("took")) {
//                String id = cut(s, "on path ", null);
//                s = cut(s, ": ", null);
//                s = cut(s, ": ", null);
//                s = cut(s, null, ",");
//                System.out.println(s);
                try {
                    String cost = cut(s, "took ", "ms");
                    s = cut(s, "to run ", null);
                    addWithoutDup(list, s);
                    {
                        Integer i = map.get(s);
                        if (i == null) {
                            i = 0;
                        }
                        i++;
                        map.put(s, i);
                    }
                    {
                        Integer i = costs.get(s);
                        if (i == null) {
                            i = 0;
                        }
                        i += toInt(cost);
                        costs.put(s, i);
                    }
                    
                    
                } catch (Exception e) {
                }
            }
        }
        for (String s : list) {
            int cost = costs.get(s);
            int times = map.get(s);
            System.out.println(format("[times:{0} avgcost:{2}] {1}", formatstr(times + "", 3), s, cost/times));
        }
        
    }

}
