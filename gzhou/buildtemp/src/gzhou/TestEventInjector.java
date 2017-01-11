package gzhou;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang.StringEscapeUtils;

public class TestEventInjector {

    private static final String providerUrl = "jnp://10.101.3.73:1099";
    private static final String projectName = "/home/vtbaadmin/TestProject$Deployment";
    private static final String eventType = "event1";
    private static final String feedName = "feed1";

    protected static int count_ = 0;

    public static void main(String[] args) throws Exception {
        String queueName = QUEUE;

        // int count = 1;
        // int count = 50000;
        int count = 50000;

        int interval = 1;

        List<DocPath> docs_in = new ArrayList<DocPath>();

        // ---------------Customize-----------------
        // docs_in.add(new DocPath("ArrayBV=order_unqualified.xml"));
        // docs_in.add(new DocPath("OAGOrder=OAGOrder1.xml"));
        // docs_in.add(new DocPath("primitiveTypes=primitiveTypes.xml"));
        // docs_in.add(new DocPath("PrimitiveTypes=primitiveTypes.xml"));
        // docs_in.add(new DocPath("primitiveTypes=primitiveTypes_qualified.xml"));
        // docs_in.add(new DocPath("order=lib.xml"));
        // docs_in.add(new DocPath("order=lib_no_item.xml"));
        // docs_in.add(new DocPath("order1=lib3.xml"));
        // docs_in.add(new DocPath("school=School.xml"));
        // docs_in.add(new DocPath("purchaseOrder=ipo1.xml"));
        // docs_in.add(new DocPath("taskReference=taskReference.xml"));
        // docs_in.add(new DocPath("elementB=NamespaceTest_elementB.xml"));
        // docs_in.add(new DocPath("elementC=NamespaceTest_elementC.xml"));
        // docs_in.add(new DocPath("ComplexTypeB=NamespaceTest_ComplexTypeB.xml"));
        // docs_in.add(new DocPath("ComplexTypeC=NamespaceTest_ComplexTypeC.xml"));
        // docs_in.add(new DocPath("parameterized=parameterized.xml"));
        // docs_in.add(new DocPath("bv=bv.xml"));
        // docs_in.add(new DocPath("bookOrder1=complexBookOrder1.xml"));
        // docs_in.add(new DocPath("bookOrder2=complexBookOrder1.xml"));
        // docs_in.add(new DocPath("bookOrder=complexBookOrder1.xml"));
        // ---------------End-----------------

        List<DocPath> docs = new ArrayList<DocPath>();

        Hashtable<String, String> environment = new Hashtable<String, String>();
        environment.put("java.naming.provider.url", providerUrl);
        // environment.put("java.naming.provider.url", "jnp://10.101.7.54:1099");
        environment.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");

        //1) set the event header information
        String evtType = eventType;
        String evtSpace = ".*";
        String evtCode = "code";
        String externalID = "1234";
        String feed = feedName;

        // String evtType = "Warning";
        // String evtSpace = ".*";
        // String evtCode = "code";
        // String externalID = "1234";
        // String feed = "Feed/feed1";

        // String evtType = "Info";
        // String evtSpace = ".*";
        // String evtCode = "code";
        // String externalID = "1234";
        // String feed = "Feed/feed2";

        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-c")) {
                i++;
                evtCode = args[i];
            } else if (args[i].equalsIgnoreCase("-t")) {
                i++;
                evtType = args[i];
            } else if (args[i].equalsIgnoreCase("-n")) {
                i++;
                count = Integer.valueOf(args[i]);
            } else if (args[i].equalsIgnoreCase("-s")) {
                i++;
                interval = Integer.valueOf(args[i]);
            } else if (args[i].equalsIgnoreCase("-d")) {
                i++;
                DocPath docPath = new DocPath(args[i]);
                docs.add(docPath);
            } else if (args[i].equalsIgnoreCase("-q")) {
                i++;
                queueName = args[i];
            } else if (args[i].equalsIgnoreCase("-provider")) {
                i++;
                environment.put("java.naming.provider.url", args[i]);
            } else if (args[i].equalsIgnoreCase("-factory")) {
                i++;
                environment.put("java.naming.factory.initial", args[i]);
            } else if (args[i].equalsIgnoreCase("-feed")) {
                i++;
                if (args[i].equals("none"))
                    feed = null;
                else
                    feed = args[i];
            }
        }

        // ---------------Customize-----------------
        if (docs.size() == 0) {
            docs.addAll(docs_in);
        }
        // ---------------End-----------------

        InitialContext context;
        if (environment.size() == 2)
            context = new InitialContext(environment);
        else
            context = new InitialContext();

        TestEventInjector client = new TestEventInjector();
        client.init(context, queueName);

        //2) set payload with correct variable name, which is what you defined in Event schema
        for (int i = 0; i < count; i++) {
            Map<String, String> documents = getDocuments(docs, i);
            // documents.put("workgroupId", "workgroup" + (i % 20 + 1));
            logmsg(count, i, documents.keySet());
            client.send(evtType, evtSpace, evtCode, externalID, feed, documents);
            Thread.sleep(interval);
        }
        client.close();
    }

    // Defines the JMS connection factory for the queue.
    public final static String QUEUE_CONNECTION_FACTORY = "jms/queue-factory";

    // Defines the queue.
    public final static String QUEUE = "/vitria/vtEmfService";
    public final static String QUEUE_A = "/queue/A";
    public final static String QUEUE_B = "/queue/B";

    private QueueConnectionFactory qconFactory;
    private QueueConnection qcon;
    private QueueSession qsession;
    private QueueSender qsender;
    private javax.jms.Queue queue;
    private MapMessage msg;

    /**
     * Creates all the necessary objects for sending messages to a JMS queue.
     */
    public void init(Context ctx, String queueName) throws NamingException, JMSException {
        qconFactory = (QueueConnectionFactory) ctx.lookup(QUEUE_CONNECTION_FACTORY);
        qcon = qconFactory.createQueueConnection();
        qsession = qcon.createQueueSession(true, Session.AUTO_ACKNOWLEDGE); // transacted session to improve performance
        queue = (javax.jms.Queue) ctx.lookup(queueName);
        qsender = qsession.createSender(queue);
        qcon.start();
    }

    /**
     * Sends a message to a JMS queue.
     */
    public void send(String eventType, String eventSpace, String eventCode, String externalID, String feed,
            Map<String, String> documents) throws JMSException {
        msg = qsession.createMapMessage();

        msg.setStringProperty("type", "Map");

        // msg.setString("_type", eventType);
        // msg.setString("_space", eventSpace);
        // msg.setString("_code", eventCode);
        // msg.setString("_timestamp", formatXmlSchemaDateTime(new Date()));
        // msg.setString("_externalID", externalID);
        // documents.put("Header", getHeader(eventType, eventSpace, eventCode, externalID, null));
        documents.put("string", (count_++) + ": string");
        // documents.put("name", "name" + (count_++));
        // documents.put("time", formatXmlSchemaDateTime(new Date()));
//        documents.put("orders", "<or:bookOrder xmlns:or=\"http://schema.vitria.com/dmstest/bo1\"><orderId>oid</orderId><custId>cid</custId><code>222-DD</code><notes>notes</notes><purchaser><firstName>Guifang</firstName><lastName>Zhou</lastName><age>26</age><address><city>Beijing</city><street>East Chang An street</street><houseNumber>1</houseNumber></address></purchaser><item><productName>product1</productName><quantity>100</quantity><price>5.5</price><notes>This is a book.</notes></item></or:bookOrder>");

        msg.setStringProperty("_projectName", projectName);
        if (feed != null)
            msg.setStringProperty("_destinationFeed", feed);

        for (String name : documents.keySet()) {
            msg.setString(name, documents.get(name));
        }

        qsender.send(msg);
        
//        TextMessage m = qsession.createTextMessage("<or:bookOrder xmlns:or=\"http://schema.vitria.com/dmstest/bo1\"><orderId>oid</orderId><custId>cid</custId><code>222-DD</code><notes>notes</notes><purchaser><firstName>Guifang</firstName><lastName>Zhou</lastName><age>26</age><address><city>Beijing</city><street>East Chang An street</street><houseNumber>1</houseNumber></address></purchaser><item><productName>product1</productName><quantity>100</quantity><price>5.5</price><notes>This is a book.</notes></item></or:bookOrder>");
//        qsender.send(m);
    }

    public void close() throws JMSException {
        qsession.commit();
        qsender.close();
        qsession.close();
        qcon.close();
    }

    public static String get(String name, int i) throws Exception {
        String content;
        if (name.indexOf(".") < 0)
            return name;
        else if ((content = specialContent(name, i)) != null)
            return content;
        else
            return ResourceLoadUtil.loadResourceAsString(name);
    }

    public static String getStringTypeDoc(int var, int count) {
        StringBuffer sb = new StringBuffer();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n");
        sb.append("<dms:stringTypes" + var + " xmlns:dms=\"http://schema.vitria.com/dmstest\">" + "\n");
        sb.append("    <stringType" + var + "1>stringType" + var + "_1_" + count + "</stringType" + var + "1>" + "\n");
        sb.append("    <stringType" + var + "2>stringType" + var + "_2_" + count + "</stringType" + var + "2>" + "\n");
        sb.append("    <stringType" + var + "3>stringType" + var + "_3_" + count + "</stringType" + var + "3>" + "\n");
        sb.append("    <stringType" + var + "4>stringType" + var + "_4_" + count + "</stringType" + var + "4>" + "\n");
        sb.append("    <stringType" + var + "5>stringType" + var + "_5_" + count + "</stringType" + var + "5>" + "\n");
        sb.append("</dms:stringTypes" + var + ">" + "\n");

        return sb.toString();
    }

    public static String getBookOrderDoc(int count) {
        StringBuffer sb = new StringBuffer();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n");

        sb.append("<or:bookOrder xmlns:or=\"http://schema.vitria.com/dmstest\">" + "\n");
        sb.append("<orderId>oid_" + count + "</orderId>" + "\n");
        sb.append("<custId>cid_" + count + "</custId>" + "\n");
        sb.append("<code>222-DD</code>" + "\n");
        sb.append("<notes>notes_" + count + "</notes>" + "\n");
        sb.append("<item>" + "\n");
        sb.append("    <productName>product_" + count + "</productName>" + "\n");
        sb.append("    <quantity>100</quantity>" + "\n");
        sb.append("    <price>5.5</price>" + "\n");
        sb.append("    <notes>This is book_" + count + ".</notes>" + "\n");
        sb.append("</item>" + "\n");
        sb.append("</or:bookOrder>" + "\n");

        return sb.toString();
    }

    private static class DocPath {
        String name;
        String file;

        DocPath(String path) {
            String[] s = path.split("=");
            name = s[0];
            file = s[1];
        }
    }

    private static void logmsg(int count, int i, Set<String> variables) {
        if (count <= 1000) {
            System.out.println("Sent one message #" + i + " with variables " + variables);
        } else {
            if (i % 100 == 0) {
                System.out.println("Sent one hundred messages #" + (i / 100) + " with variables " + variables);
            }
        }
    }

    // Get documents
    private static Map<String, String> getDocuments(List<DocPath> docs, int i) throws Exception {
        Map<String, String> documents = new HashMap<String, String>();
        for (DocPath docPath : docs) {
            documents.put(docPath.name, get(docPath.file, i));
        }
        return documents;
    }

    private static String specialContent(String name, int i) {
        if (name.startsWith("stringTypes") && name.endsWith(".xml")) {
            String str = name.substring("stringTypes".length(), name.length() - ".xml".length());
            if (str == null || str.length() == 0)
                return null;
            int var = Integer.parseInt(str);
            return getStringTypeDoc(var, i);
        } else if (name.equals("bookOrderForPerformance.xml")) {
            return getBookOrderDoc(i);
        }
        return null;
    }

    private static String formatXmlSchemaDateTime(Date dt) {
        DateFormat iso8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        TimeZone timeZone = TimeZone.getDefault();
        iso8601Local.setTimeZone(timeZone);
        int offset = timeZone.getOffset(dt.getTime());
        String sign = "+";
        if (offset < 0) {
            offset = -offset;
            sign = "-";
        }
        int hours = offset / 3600000;
        int minutes = (offset - hours * 3600000) / 60000;
        if (offset != hours * 3600000 + minutes * 60000) {
            throw new RuntimeException("TimeZone offset (" + sign + offset + " ms) is not an exact number of minutes");
        }
        DecimalFormat twoDigits = new DecimalFormat("00");
        return iso8601Local.format(dt) + sign + twoDigits.format(hours) + ":" + twoDigits.format(minutes);
    }

    protected static String getHeader(String eventType, String eventSpace, String eventCode, String externalID,
            String timestamp) {
        StringBuffer buf = new StringBuffer();
        buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        buf.append("<event:EMFEventHeader xmlns:event=\"http://schema.vitria.com/model\">\n");
        buf.append("  <EventSpace>" + ((eventSpace == null) ? "" : StringEscapeUtils.escapeXml(eventSpace))
                + "</EventSpace>\n");
        buf.append("  <EventCode>" + ((eventCode == null) ? "" : StringEscapeUtils.escapeXml(eventCode))
                + "</EventCode>\n");

        buf.append("  <ExternalID>" + ((externalID == null) ? "" : StringEscapeUtils.escapeXml(externalID))
                + "</ExternalID>\n");
        buf.append("  <Timestamp>" + ((null == timestamp) ? formatXmlSchemaDateTime(new Date()) : timestamp)
                + "</Timestamp>\n");
        buf.append("</event:EMFEventHeader>");

        return buf.toString();
    }
}
