package gzhou;

public class PerformanceOperator {

    public static void operate(PerformanceOperation op, int times) throws Exception {
        long s = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            op.operate();
        }
        long e = System.currentTimeMillis();
        System.out.println("eps: " + (1000 * times) / (e - s));
    }

    public interface PerformanceOperation {
        public void operate() throws Exception;
    }

}
