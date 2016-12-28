package hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.vitria.workflow.transaction.SessionOperation;
import com.vitria.workflow.transaction.SessionOperator;

public class HibernateTest {

    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        System.out.println("before build");
        SessionFactory sf = cfg.configure().buildSessionFactory();
        System.out.println("after build");
        System.out.println("before close");
        sf.close();
        System.out.println("after close");
        SessionOperator op = new SessionOperator(sf);
        SessionOperation operation = new SessionOperation() {

            public Object operate(Session session) {

                query(session);

                return null;
            }

        };
        op.operate(operation);
    }

    protected static void query(Session session) {

        Query query = session.createQuery("from hibernate.Student");
        query.setTimeout(300);
        List list = query.list();
        System.out.println(list);

    }

    protected static void insert(Session session) {
        Student s = new Student();
        s.setId("1");
        s.setName("name1");
        session.save(s);
    }

}
