package txu;

public class Sleep {

    public static void main(String[] args) {
        if (args == null || args.length == 0)
            return;

        long wait = 0;
        int i = 0;
        while (i < args.length) {
            if (args[i].equals("-m")) {
                wait += Long.valueOf(args[++i]) * 60 * 1000;
                i++;
            } else if (args[i].equals("-s")) {
                wait += Long.valueOf(args[++i]) * 1000;
                i++;
            }
        }

        try {
            System.out.println("Sleeping for " + (wait / 1000) + " seconds.");
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
