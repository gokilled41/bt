import gzhou.*;
import gzhou.CountingHelper.Line;
import gzhou.PerformanceOperator.*;

import java.io.*;
import java.lang.reflect.*;
import java.lang.reflect.Field;
import java.math.*;
import java.net.*;
import java.net.Proxy;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

import javax.management.*;
import javax.naming.*;
import javax.script.*;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.servlet.http.*;
import javax.sql.*;
import javax.xml.bind.*;
import javax.xml.transform.*;

import org.apache.commons.lang.*;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.FileSystem;
import org.apache.poi.hssf.record.GutsRecord;
import org.dmg.pmml.*;
import org.joda.time.*;
import org.joda.time.format.*;
import org.jpmml.model.*;
import org.w3c.dom.*;
import org.w3c.dom.Node;
import org.xml.sax.*;

import com.fasterxml.jackson.databind.*;
import com.vitria.cluster.*;
import com.vitria.component.server.*;
import com.vitria.component.server.ServerManager;
import com.vitria.component.server.beans.Administration;
import com.vitria.dataflow.*;
import com.vitria.domainservice.*;
import com.vitria.domainservice.ResourceInfo;
import com.vitria.domainservice.util.*;
import com.vitria.dataflow.*;
import com.vitria.dataflow.dmsdk.framework.util.Util;
import com.vitria.dataflow.exception.*;
import com.vitria.dataflow.helper.JSONEventHelper;
import com.vitria.dataflow.jmx.*;
import com.vitria.dataflow.util.*;
import com.vitria.ejb3.example.Calculator;
import com.vitria.engine.client.ApplicationLib;
import com.vitria.fc.data.*;
import com.vitria.fc.utils.*;
import com.vitria.locale.Logger;
import com.vitria.naming.*;
import com.vitria.predictive.util.*;
import com.vitria.security.auth.login.impl.StandardCallbackHandler;
import com.vitria.xquery.*;

@SuppressWarnings("all")
public class Test extends gzhou.Util {

    public static void main(String[] args) throws Exception {

        List<String> a = Util.getLines(desktopDir + "a.txt");
        a.remove(0);
        List<String> bs = Util.getLines(desktopDir + "b.txt");
        for (String b : bs) {
            boolean conn = b.startsWith("connect:");
            if (conn)
                b = b.substring("connect:".length());
            StringBuilder sb = new StringBuilder();
            for (String l : a) {
                l = l.substring(l.lastIndexOf(" ") + 1, l.length() - 1);
                String o = b.replace("item", l);
                if (conn)
                    sb.append(o).append(", ");
                else
                    System.out.println(o);
            }
            if (conn) {
                String o = sb.toString();
                o = o.substring(0, o.length() - 2);
                System.out.println(o);
            }
            System.out.println(" ");
        }

        String message = "123";
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(message.getBytes());
        byte[] b = md.digest();
        org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();  
        b=base64.encode(b);  
        System.out.println(new String(b));

    }

}
