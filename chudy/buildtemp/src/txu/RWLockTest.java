package txu;

import gzhou.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.vitria.fc.concurrent.RWLockFactory;

public class RWLockTest implements Constants {

    public static void main(String[] args) throws Exception {

        ThreadFactory f = new ThreadFactory() {

            public Thread newThread(Runnable r) {
                return new Thread(r);
            }

        };
        ExecutorService executor_ = Executors.newFixedThreadPool(50, f);

        final RWLockFactory factory = RWLockFactory.getRWLockFactory();
        final Random random = new Random();
        final List<String> list = new ArrayList<String>();
        final int print = 13;
        long start = System.currentTimeMillis();

        while (true) {
            Runnable r = new Runnable() {
                public void run() {

                    int i = random.nextInt(25);
                    boolean read = (random.nextDouble() > 0.5);

                    com.vitria.fc.concurrent.RWLock lock;
                    if (read)
                        lock = factory.getReadLock("" + i);
                    else
                        lock = factory.getWriteLock("" + i);

                    lock.lock();
                    try {
                        for (int j = 0; j < 10; j++) {
                            // System.out.println(i + " " + (read ? "r" : "w") + " " + j);
                            if (i == print) {
                                synchronized (list) {
                                    list.add(i + " " + (read ? "r" : "w") + " " + j);
                                }
                            }
                            Thread.sleep(100);
                        }
                    } catch (Exception e) {
                        // ignore
                    } finally {
                        lock.unlockAndRelease();
                    }

                }
            };
            executor_.execute(r);
            Thread.sleep(50);

            long end = System.currentTimeMillis();
            if (end - start > 60 * 1000l)
                break;
        }

        for (String s : list) {
            System.out.println(s);
        }

        System.exit(0);
    }
}
