package gzhou;

import java.util.ArrayList;
import java.util.List;

public class ShareCD implements Constants {

    public static void main(String[] args) throws Exception {

        ShareCDCalculater cal = new ShareCDCalculater();
        cal.cal();

        for (ShareCDCalculater.Result result : cal.results) {
            System.out.println(result.count);
        }

    }

}

class ShareCDCalculater {

    List<Result> results = new ArrayList<Result>();

    double time = 180;

    String[] skills = new String[] { "正义之锤", "奉献", "神圣之盾", "审判" };
    int[] cds = new int[] { 6, 10, 8, 8 };
    double[] lasts_ = new double[] { -1, -1, -1, -1 };

    public void cal() {

        double now = 0;

        List<String> list = new ArrayList<String>();

        calNext(0, now, list, lasts_);

    }

    public boolean calNext(int i, double now, List<String> list, double[] lasts) {

        if (now < time) {
            List<String> tempList = copy(list);
            double[] tempLasts = copy(lasts);

            if (lasts[i] == -1 || lasts[i] + cds[i] <= now) {

                tempList.add(skills[i]);
                //System.out.println(now);
                //System.out.println(tempList);
                tempLasts[i] = now;
                now += 1.5;

                boolean b = false;

                while (now < time && b == false) {

                    for (int j = 0; j < skills.length; j++) {
                        if (calNext(j, now, tempList, tempLasts)) {
                            b = true;
                        }

                    }

                    if (b == false) {
                        now += 0.5;
                    }
                }

                return b;

            } else {
                // in the cool down
                return false;
            }

        } else {
            Result result = new Result();
            result.count = list.size();
            result.list = copy(list);
            System.out.println(result.count);
            System.out.println(result.list);
            results.add(result);
            return true;
        }

    }

    protected List<String> copy(List<String> list) {
        List<String> list2 = new ArrayList<String>();
        list2.addAll(list);
        return list2;
    }

    protected double[] copy(double[] lasts) {
        double[] lasts2 = new double[lasts.length];
        for (int i = 0; i < lasts2.length; i++) {
            lasts2[i] = lasts[i];
        }
        return lasts2;
    }

    public static class Result {
        int count;
        List<String> list;
    }
}