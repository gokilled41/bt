package gzhou;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TopicReceiver implements MessageListener {

    public static final String JAVA_NAMING_PROVIDER_URL = "java.naming.provider.url";
    public static final String JAVA_NAMING_FACTORY_INITIAL = "java.naming.factory.initial";

    public final static String TOPIC_CONNECTION_FACTORY = "jms/topic-factory";
    public final static String TOPIC = "/vitria/vtCacheManager";

    private String providerUrl;
    private String factoryInitial;

    private InitialContext context;
    private TopicConnectionFactory tconFactory;
    private TopicConnection tcon;
    private TopicSession tsession;
    private TopicSubscriber tsubscriber;
    private Topic topic;

    private TopicReceiver() {
    }

    public static TopicReceiver geTopicReceiver(String providerUrl) {
        TopicReceiver receiver = new TopicReceiver();
        receiver.providerUrl = providerUrl;
        receiver.factoryInitial = "org.jnp.interfaces.NamingContextFactory";
        receiver.init();
        return receiver;
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
        tsubscriber = tsession.createSubscriber(topic);
        tsubscriber.setMessageListener(this);
        tcon.start();
    }

    public void close() throws JMSException {
        tsubscriber.close();
        tsession.close();
        tcon.close();
    }

    public void onMessage(Message message) {
        try {
            if (message instanceof MapMessage) {
                MapMessage msg = (MapMessage) message;
                System.out.println("Topic {" + TOPIC + ", " + providerUrl + "} receive message: " + "_message = "
                        + msg.getString("_message"));
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TopicReceiver.geTopicReceiver(args[0]);
    }
}
