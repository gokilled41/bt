package gzhou;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class JMSTask extends Task {

    private String type_;
    private String dest_;
    private File event_;
    private int count_ = 20000;
    // private String props_; // TODO
    private String connFactory_ = "ConnectionFactory";

    private List<String> eventLines_;
    private int eventIndex_ = 0;
    private int sent_ = 0;

    public void setType(String type) {
        type_ = type;
    }

    public void setDest(String dest) {
        dest_ = dest;
    }

    public void setEvent(File event) {
        event_ = event;
    }

    public void setCount(int count) {
        count_ = count;
    }

    //    public void setProps(String props) {
    //        props_ = props;
    //    }

    @Override
    public void execute() throws BuildException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            if (type_.equals("pub")) {
                pub();
            } else {
                sub();
            }
        } catch (Exception e) {
            throw new BuildException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
    }

    protected void pub() throws Exception {
        initPub();
        Connection con = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            Properties p = getProps();
            Context ctx = new InitialContext(p);
            Destination d = (Destination) ctx.lookup(dest_);
            ConnectionFactory cf = (ConnectionFactory) ctx.lookup(connFactory_);
            con = cf.createConnection();
            session = con.createSession(true, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(d);

            int batchSize = (count_ / 100);
            for (int i = 0; i < count_; i++) {
                pubEvent(session, producer, getEvent());
                if (i % batchSize == 0) {
                    session.commit();
                }
            }
            session.commit();
        } finally {
            if (producer != null)
                producer.close();
            if (session != null)
                session.close();
            if (con != null)
                con.close();
        }
    }

    private void initPub() throws Exception {
        eventLines_ = LinesUtil.getLines(event_.getAbsolutePath());
    }

    private String getEvent() throws Exception {
        eventIndex_++;
        if (eventIndex_ >= eventLines_.size())
            eventIndex_ = 0;
        return eventLines_.get(eventIndex_);
    }

    private void pubEvent(Session session, MessageProducer producer, String event) throws JMSException {
        TextMessage msg = session.createTextMessage(event);
        producer.send(msg);
        doPubLog(++sent_);
    }

    private void doPubLog(int i) {
        if (count_ < 1000) {
            System.out.println("Sent " + i + " events.");
        } else {
            if (count_ > 1000 && i % (count_ / 100) == 0)
                System.out.println("Sent " + i + " events.");
        }
    }

    protected void sub() throws Exception {
        Connection con = null;
        Session session = null;
        MessageConsumer consumer = null;
        try {
            ConnectionFactory myConnFactory;
            Queue myQueue;

            // Create the initial context.
            Properties p = getProps();
            Context ctx = new InitialContext(p);

            myConnFactory = (javax.jms.ConnectionFactory) ctx.lookup(connFactory_);
            myQueue = (javax.jms.Queue) ctx.lookup(dest_);

            con = myConnFactory.createConnection();
            session = con.createSession(true, Session.AUTO_ACKNOWLEDGE);

            consumer = session.createConsumer(myQueue);

            con.start();

            Listener listener = new SysoutListener();
            Notifier notifier = new Notifier(session, consumer, listener);
            notifier.start();

            Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook(notifier), "jms-sub-shutdown-hook"));

            notifier.join();
        } finally {
            if (consumer != null)
                consumer.close();
            if (session != null)
                session.close();
            if (con != null)
                con.close();
        }
    }

    private void doSubLog(int i) {
        if (count_ < 1000) {
            System.out.println("Received " + i + " events.");
        } else {
            if (count_ > 1000 && i % (count_ / 100) == 0)
                System.out.println("Received " + i + " events.");
        }
    }

    // shared apis
    private Properties getProps() {
        Properties p = new Properties();
        p.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        p.setProperty("java.naming.provider.url", "jnp://localhost:1099");
        p.setProperty("java.naming.security.principal", "vtbaadmin");
        p.setProperty("java.naming.security.credentials", "vitria");
        return p;
    }

    private interface Listener {
        public void onEvent(String event);

        public boolean isOneBatch();
    }

    private class SysoutListener implements Listener {

        private int received_ = 0;

        @Override
        public void onEvent(String event) {
            received_++;
            doSubLog(received_);
        }

        public boolean isOneBatch() {
            return received_ % (count_ / 100) == 0;
        }
    }

    private class Notifier extends Thread {

        private boolean isRunning_;
        private MessageConsumer consumer_;
        private Listener listener_;
        private Session session_;

        public Notifier(Session session, MessageConsumer consumer, Listener listener) {
            session_ = session;
            consumer_ = consumer;
            listener_ = listener;
        }

        public void stopIt() {
            isRunning_ = false;
        }

        @Override
        public void run() {
            isRunning_ = true;
            while (isRunning_) {
                //Step 10:
                //Receive a message from the queue.
                try {
                    Message msg = consumer_.receive(1000);

                    if (msg != null) {
                        //Step 11:
                        //Retreive the contents of the message.
                        if (msg instanceof TextMessage) {
                            TextMessage txtMsg = (TextMessage) msg;
                            listener_.onEvent(txtMsg.getText());
                        } else if (msg instanceof BytesMessage) {
                            BytesMessage bmsg = (BytesMessage) msg;
                            listener_.onEvent(new String(bmsg.readUTF()));
                        }

                        if (listener_.isOneBatch()) {
                            session_.commit();
                        }
                    } else {
                        session_.commit();
                    }
                } catch (JMSException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private class ShutdownHook implements Runnable {

        private Notifier notifier_;

        private ShutdownHook(Notifier notifier) {
            notifier_ = notifier;
        }

        public void run() {
            try {
                notifier_.stopIt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
