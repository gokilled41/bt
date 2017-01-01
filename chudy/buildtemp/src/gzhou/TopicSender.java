package gzhou;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TopicSender {

    public static final String JAVA_NAMING_PROVIDER_URL = "java.naming.provider.url";
    public static final String JAVA_NAMING_FACTORY_INITIAL = "java.naming.factory.initial";
    public static final String PROVIDER_URL = "jnp://localhost:1100";
    public static final String FACTORY_INITIAL = "org.jnp.interfaces.NamingContextFactory";

    public final static String TOPIC_CONNECTION_FACTORY = "jms/topic-factory";
    public final static String TOPIC = "/vitria/vtCacheManager";

    private String providerUrl;
    private String factoryInitial;
    private InitialContext context;
    private TopicConnectionFactory tconFactory;
    private TopicConnection tcon;
    private TopicSession tsession;
    private TopicPublisher tpublisher;
    private Topic topic;
    private MapMessage msg;

    private TopicSender() {
    }

    public static TopicSender geTopicSender(String providerUrl) {
        TopicSender sender = new TopicSender();
        sender.providerUrl = providerUrl;
        sender.factoryInitial = FACTORY_INITIAL;
        sender.init();
        return sender;
    }

    private void init() {
        try {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(JAVA_NAMING_PROVIDER_URL, providerUrl);
            env.put(JAVA_NAMING_FACTORY_INITIAL, factoryInitial);
            context = new InitialContext(env);

            init(context, TOPIC);
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void init(Context ctx, String topicName) throws NamingException, JMSException {
        tconFactory = (TopicConnectionFactory) ctx.lookup(TOPIC_CONNECTION_FACTORY);
        tcon = tconFactory.createTopicConnection();
        tsession = tcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        topic = (Topic) ctx.lookup(topicName);
        tpublisher = tsession.createPublisher(topic);
        tcon.start();
    }

    public void close() throws JMSException {
        tpublisher.close();
        tsession.close();
        tcon.close();
    }

    public void send(String message) throws JMSException {
        msg = tsession.createMapMessage();
        msg.setString("_message", message);

        tpublisher.send(msg);
    }

    public static void main(String[] args) throws Exception {
        TopicSender sender = TopicSender.geTopicSender(args[0]);
        String msg = "HelloWorld!";
        sender.send(msg);
        System.out.println("Sent to " + PROVIDER_URL + " a message: " + msg);
        sender.close();
    }
}
