package txu;

import gzhou.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.vitria.fc.concurrent.RWLockFactory;

@SuppressWarnings("all")
public class SemaphoreTest implements Constants {

    private static final Map<String, Semaphore> semaphores = new HashMap<String, Semaphore>();

    public static void main(String[] args) throws Exception {

        java.util.concurrent.ThreadFactory f = new java.util.concurrent.ThreadFactory() {
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };
        ExecutorService executor_ = Executors.newFixedThreadPool(50, f);

        final RWLockFactory oLockfactory = RWLockFactory.getRWLockFactory();
        final RWLockFactory ALockfactory = RWLockFactory.getRWLockFactory();
        final List<String> list = new ArrayList<String>();
        final int print = 13;
        long start = System.currentTimeMillis();

        for (int i = 0; i < 25; i++) {
            A a = new A();
            a.i = i;
            //            com.vitria.fc.concurrent.RWLock alock;
            //            alock = ALockfactory.getWriteLock("" + i);
            //            alock.lock();
            Semaphore s = getSemaphore("" + i);
            s.acquire();

            executor_.execute(a);
            Thread.sleep(50);

            B b = new B();
            b.i = i;
            executor_.execute(b);
        }

    }

    private static Semaphore getSemaphore(String key) {
        synchronized (semaphores) {
            Semaphore s = semaphores.get(key);
            if (s == null) {
                s = new Semaphore(1);
                semaphores.put(key, s);
            }
            return s;
        }
    }

    private static String now() {
        return sdf.format(new Date());
    }

    private static Random random = new Random();
    private static RWLockFactory oLockfactory = RWLockFactory.getRWLockFactory();
    private static RWLockFactory ALockfactory = RWLockFactory.getRWLockFactory();

    private static class A implements Runnable {
        protected int i;

        public void run() {

            com.vitria.fc.concurrent.RWLock lock;
            lock = oLockfactory.getWriteLock("" + i);

            Semaphore aSemaphore = getSemaphore("" + i);

            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            lock.lock();
            try {
                for (int j = 0; j < 1; j++) {
                    System.out.println(now() + " " + i + " " + "A" + " " + j);
                    //                    Thread.sleep(100);
                }
            } catch (Exception e) {
                // ignore
            } finally {
                lock.unlockAndRelease();
                aSemaphore.release();
            }

        }
    }

    private static class B implements Runnable {
        protected int i;

        public void run() {

            com.vitria.fc.concurrent.RWLock lock;
            lock = oLockfactory.getWriteLock("" + i);

            Semaphore aSemaphore = getSemaphore("" + i);

            //            try {
            //                Thread.sleep(random.nextInt(1000));
            //            } catch (InterruptedException e1) {
            //                e1.printStackTrace();
            //            }

            try {
                aSemaphore.acquire();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            lock.lock();
            try {
                for (int j = 0; j < 1; j++) {
                    System.out.println(now() + " " + i + " " + "B" + " " + j);
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                // ignore
            } finally {
                lock.unlockAndRelease();
                aSemaphore.release();
            }

        }
    }
}
