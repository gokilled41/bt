package gzhou;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitria.predictive.util.JSONQueryLib;
import com.vitria.predictive.util.JSONUtil;

@SuppressWarnings("all")
public class FundTask extends Task implements Constants {

    private static final int spaces = 3;

    private static final double jr_base = 0;
    private static final double dc_base = 0;
    private static final double jr_invest = 0;
    private static final double dc_invest = 0;
    private static final double jr_quantity = 0;
    private static final double dc_quantity = 0;

    private String id_ = null;
    private int repeat_ = 1;
    private int interval_ = 0;
    private boolean earn_ = false;
    private int earnTotal_ = 0;

    private int investTotal_ = 0;

    public void setId(String id) {
        id_ = id;
    }

    public void setRepeat(int repeat) {
        repeat_ = repeat;
    }

    public void setInterval(int interval) {
        interval_ = interval;
    }

    public void setEarn(boolean earn) {
        earn_ = earn;
    }

    @Override
    public void execute() throws BuildException {
        if (repeat_ == -1)
            repeat_ = Integer.MAX_VALUE;
        for (int i = 0; i < repeat_; i++) {
            runIds();
        }
    }

    private void runIds() {
        String[] ids = id_.split(",");
        for (String id : ids) {
            id_ = id.trim();
            runId();
        }
        printEarnTotal();
    }

    private void runId() {
        if (isFund())
            runFund();
        else
            runStock();
        doSleep();
    }

    private boolean isFund() {
        return !id_.startsWith("sh") && !id_.startsWith("sz");
    }

    private void runFund() {
        try {
            String json = getFundJSONWithRetry(id_, 5);
            FundData data = toFundData(json, id_);
            printFundData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runStock() {
        try {
            String info = getStockInfoWithRetry(id_, 5);
            String detail = getStockDetailJSONWithRetry(id_, 5);
            StockData data = toStockData(info, detail, id_);
            printStockData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doSleep() {
        try {
            Thread.sleep(interval_ * 1000);
        } catch (Exception e) {
        }
    }

    private String getFundJSONWithRetry(String id, int n) throws Exception {
        for (int i = 0; i < n; i++) {
            try {
                return getFundJSON(id);
            } catch (Exception e) {
            }
        }
        throw new Exception("Cannot get json for: " + id);
    }

    private String getFundJSON(String id) throws Exception {
        String link = "http://www.jisilu.cn/jisiludata/StockFenJiDetail.php?qtype=dynamic&display=table&fund_id=" + id;
        URL url = new URL(link);
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(200);
        conn.setReadTimeout(2000);
        conn.connect();
        InputStream is = conn.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        String json = in.readLine();
        in.close();
        is.close();
        return json;
    }

    private FundData toFundData(String json, String id) throws Exception {
        JsonNode jsonNode = JSONUtil.getJsonNode(json);
        FundData d = new FundData();
        d.id_ = id;
        d.fundName_ = (String) JSONQueryLib.getValue(jsonNode, "rows[1].cell.fund_nm");
        d.fundChange_ = (String) JSONQueryLib.getValue(jsonNode, "rows[1].cell.increase_rt");
        String indexChangeStr = (String) JSONQueryLib.getValue(jsonNode, "rows[2].cell.increase_rt");
        d.indexChange_ = indexChangeStr.substring(0, indexChangeStr.indexOf(" "));
        d.fundPremium_ = (String) JSONQueryLib.getValue(jsonNode, "rows[1].cell.discount_rt");
        d.price_ = (String) JSONQueryLib.getValue(jsonNode, "rows[1].cell.price");
        d.time_ = now();
        d.lastPrice_ = getLastPrice(d);
        d.profit_ = getProfit(d);
        d.earn_ = getEarn(d);
        d.earnChange_ = getEarnChange(d);
        return d;
    }

    private String getStockInfoWithRetry(String id, int n) throws Exception {
        for (int i = 0; i < n; i++) {
            try {
                return getStockInfo(id);
            } catch (Exception e) {
            }
        }
        throw new Exception("Cannot get stock info for: " + id);
    }

    /**
        0����������·������Ʊ���֣� 
        1����27.55�壬���տ��̼ۣ� 
        2����27.25�壬�������̼ۣ� 
        3����26.91�壬��ǰ�۸� 
        4����27.55�壬������߼ۣ� 
        5����26.20�壬������ͼۣ� 
        6����26.91�壬����ۣ�������һ�����ۣ� 
        7����26.92�壬�����ۣ�������һ�����ۣ� 
        8����22114263�壬�ɽ��Ĺ�Ʊ�������ڹ�Ʊ������һ�ٹ�Ϊ������λ��������ʹ��ʱ��ͨ���Ѹ�ֵ����һ�٣� 
        9����589824680�壬�ɽ�����λΪ��Ԫ����Ϊ��һĿ��Ȼ��ͨ���ԡ���Ԫ��Ϊ�ɽ����ĵ�λ������ͨ���Ѹ�ֵ����һ�� 
        10����4695�壬����һ������4695�ɣ���47�֣� 
        11����26.91�壬����һ�����ۣ� 
        12����57590�壬������� 
        13����26.90�壬������� 
        14����14700�壬�������� 
        15����26.89�壬�������� 
        16����14300�壬�����ġ� 
        17����26.88�壬�����ġ� 
        18����15100�壬�����塱 
        19����26.87�壬�����塱 
        20����3100�壬����һ���걨3100�ɣ���31�֣� 
        21����26.92�壬����һ������ 
        (22, 23), (24, 25), (26,27), (28, 29)�ֱ�Ϊ���������������ĵ������ 
        30����2008-01-11�壬���ڣ� 
        31����15:05:32�壬ʱ�䣻
    **/
    private String getStockInfo(String id) throws Exception {
        String link = "http://hq.sinajs.cn/list=" + id;
        URL url = new URL(link);
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(200);
        conn.setReadTimeout(2000);
        conn.connect();
        InputStream is = conn.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        String info = in.readLine();
        info = info.substring(info.indexOf("\"") + 1, info.lastIndexOf("\""));
        in.close();
        is.close();
        return info;
    }

    private String getStockDetailJSONWithRetry(String id, int n) throws Exception {
        for (int i = 0; i < n; i++) {
            try {
                return getStockDetailJSON(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new Exception("Cannot get stock detail for: " + id);
    }

    /**
        {
         "lta":"30000", 
         "lastfive":"549.7824", 
         "flag":"1", 
         "totalcapital":"30000", 
         "currcapital":"30000", 
         "curracapital":"0", 
         "currbcapital":"0", 
         "a_code":"sh600750", 
         "b_code":"", 
         "papercode":"sh600750", 
         "exchangerate":"0", 
         "fourQ_mgsy":"0.9160", 
         "lastyear_mgsy":"0.8700", 
         "price_5_ago":"35.700", 
         "price_10_ago":"41.600", 
         "price_20_ago":"47.310", 
         "price_60_ago":"31.860", 
         "price_120_ago":"20.410", 
         "price_250_ago":"15.470", 
         "mgjzc":"7.1368", 
         "stock_state":"1", 
         "trans_flag":"1", 
         "profit":"2.6485", 
         "profit_four":"2.7486", 
         "stockType":"A", 
         "stockname":"����ҩҵ", 
         "corr_hkstock":"", 
         "corr_bdc":"", 
         "corr_bde":"sh122170"
       }
    **/
    private String getStockDetailJSON(String id) throws Exception {
        String link = "http://finance.sina.com.cn/realstock/company/" + id + "/nc.shtml";
        URL url = new URL(link);
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(200);
        conn.setReadTimeout(2000);
        conn.connect();
        InputStream is = conn.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        String line;
        boolean start = false;
        boolean end = false;
        StringBuilder json = new StringBuilder();
        json.append("{");
        while ((line = in.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("var lta"))
                start = true;
            if (line.startsWith("var corr_bde"))
                end = true;
            if (start)
                appendStockDetailJSON(json, line, end);
            if (end)
                break;

        }
        json.append("}");
        in.close();
        is.close();
        return json.toString();
    }

    private void appendStockDetailJSON(StringBuilder json, String line, boolean last) {
        line = line.substring(4, line.indexOf(";"));
        String key = line.substring(0, line.indexOf("=")).trim();
        String value = line.substring(line.indexOf("=") + 1, line.length()).trim();
        if (value.startsWith("'") && value.endsWith("'"))
            value = value.substring(1, value.length() - 1);
        json.append("\"" + key + "\":\"" + value + "\"");
        if (!last)
            json.append(",");
    }

    private StockData toStockData(String info, String detail, String id) throws Exception {
        JsonNode detailJson = JSONUtil.getJsonNode(detail);
        String[] arr = info.split(",");
        StockData d = new StockData();
        d.id_ = id;
        d.stockName_ = arr[0];
        d.lastPrice_ = arr[2];
        d.price_ = arr[3];
        d.stockChange_ = getStockChange(d);
        d.volume_ = getVolume(arr[8]);
        d.amount_ = getAmount(arr[9]);
        d.pe_ = getStockPE(d, detailJson);
        d.pb_ = getStockPB(d, detailJson);
        d.time_ = now();
        return d;
    }

    private String getStockChange(StockData d) {
        double lp = Double.valueOf(d.lastPrice_);
        double p = Double.valueOf(d.price_);
        double sc = (p - lp) / lp;
        return formatDouble(sc * 100, 2) + "%";
    }

    private String getVolume(String vs) {
        double v = Double.valueOf(vs);
        double vws = v / 100 / 10000;
        if (id_.equals("sh000001"))
            vws = vws * 100;
        return formatDouble(vws, 0);
    }

    private String getAmount(String as) {
        double a = Double.valueOf(as);
        double ay = a / 10000 / 10000;
        return formatDouble(ay, 0);
    }

    private String getStockPE(StockData d, JsonNode detailJson) {
        String mgsy = (String) JSONQueryLib.getValue(detailJson, "fourQ_mgsy");
        if (mgsy != null && !mgsy.isEmpty()) {
            double p = Double.valueOf(d.price_);
            double e = Double.valueOf(mgsy);
            double pe = p / e;
            return formatDouble(pe, 1);
        }
        return "";
    }

    private String getStockPB(StockData d, JsonNode detailJson) {
        String value = (String) JSONQueryLib.getValue(detailJson, "mgjzc");
        if (value != null && !value.isEmpty()) {
            double p = Double.valueOf(d.price_);
            double v = Double.valueOf(value);
            double pb = p / v;
            return formatDouble(pb, 1);
        }
        return "";
    }

    private String now() {
        return sdfTime.format(new Date());
    }

    private String getLastPrice(FundData d) {
        double p = Double.valueOf(d.price_);
        double i = Double.valueOf(d.fundChange_.replace("%", "")) / 100;
        double lp = p / (1 + i);
        return formatDouble(Double.valueOf(lp), 3);
    }

    private String getProfit(FundData d) {
        String n = d.fundName_;
        n = fixName(n);
        String ps = d.price_;
        double p = Double.valueOf(ps);
        if (n.equals("jr")) {
            if (jr_base > 0)
                return formatDouble(Double.valueOf((p - jr_base) * 100 / jr_base), 1);
            else
                return "0";
        } else if (n.equals("dc")) {
            if (dc_base > 0)
                return formatDouble(Double.valueOf((p - dc_base) * 100 / dc_base), 1);
            else
                return "0";
        }
        return "";
    }

    private String getEarn(FundData d) {
        String n = d.fundName_;
        n = fixName(n);
        String ps = d.profit_;
        if (!ps.isEmpty()) {
            double p = Double.valueOf(ps) / 100;
            if (n.equals("jr")) {
                int earn = (int) (jr_invest * p);
                earnTotal_ += earn;
                investTotal_ += jr_invest;
                return Integer.valueOf(earn).toString();
            } else if (n.equals("dc")) {
                int earn = (int) (dc_invest * p);
                earnTotal_ += earn;
                investTotal_ += dc_invest;
                return Integer.valueOf(earn).toString();
            }
        }
        return "";
    }

    private String getEarnChange(FundData d) {
        String n = d.fundName_;
        n = fixName(n);
        double lp = Double.valueOf(d.lastPrice_);
        double fc = Double.valueOf(d.fundChange_.replace("%", "")) / 100;
        if (n.equals("jr")) {
            double lv = lp * jr_quantity;
            int ec = (int) (lv * fc);
            String s = Integer.valueOf(ec).toString();
            return ec > 0 ? "+" + s : s;
        } else if (n.equals("dc")) {
            double lv = lp * dc_quantity;
            int ec = (int) (lv * fc);
            String s = Integer.valueOf(ec).toString();
            return ec > 0 ? "+" + s : s;
        }
        return "";
    }

    private void printEarnTotal() {
        if (earn_ && hasInvest()) {
            double d = Double.valueOf(((double) (earnTotal_) * 100) / investTotal_);
            String earnPercentage = formatDouble(d, 1) + "%";
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(out);
            int i = Integer.valueOf(earnTotal_).toString().length();
            printf(ps, "%" + i + "s%6s", earnTotal_, earnPercentage);
            log(out.toString());
        }
    }

    private boolean hasInvest() {
        return investTotal_ > 0;
    }

    private String formatDouble(String d, int scale) {
        return formatDouble(Double.valueOf(d), scale);
    }

    private String formatDouble(double d, int scale) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    private void printFundData(FundData data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        if (interval_ > 0) {
            printf(ps, "%8s%9s%7s%7s%8s", data.time_, data.price_, data.fundChange_, data.indexChange_,
                    data.fundPremium_);
        } else {
            if (earn_)
                printf(ps, "%4s%9s%7s%7s%8s%6s%5s%5s", fixName(data.fundName_), data.price_, data.fundChange_,
                        data.indexChange_, data.fundPremium_, data.profit_ + "%", data.earn_, data.earnChange_);
            else
                printf(ps, "%4s%9s%7s%7s%8s", fixName(data.fundName_), data.price_, data.fundChange_,
                        data.indexChange_, data.fundPremium_);
        }
        log(out.toString());
    }

    private void printStockData(StockData data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);
        if (interval_ > 0) {
            printf(ps, "%8s%9s%7s%7s%8s%5s%4s", data.time_, formatDouble(data.price_, 2), data.stockChange_,
                    data.volume_, data.amount_, data.pe_, data.pb_);
        } else {
            if (earn_)
                printf(ps, "%4s%9s%7s%7s%8s%5s%4s", fixName(data.stockName_), formatDouble(data.price_, 2),
                        data.stockChange_, data.volume_, data.amount_, data.pe_, data.pb_);
            else
                printf(ps, "%4s%9s%7s%7s%8s%5s%4s", fixName(data.stockName_), formatDouble(data.price_, 2),
                        data.stockChange_, data.volume_, data.amount_, data.pe_, data.pb_);
        }
        log(out.toString());
    }

    private void printf(PrintStream ps, String format, Object... objects) {
        String s = format;
        s = s.replace("%", "");
        String[] arr = s.split("s");
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (Integer.valueOf(arr[i]) + spaces - 1) + "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append("%" + arr[i] + "s");
        }
        format = sb.toString();

        ps.printf(format, objects);
    }

    private String fixName(String n) {
        n = n.replace("����", "��h");
        n = n.replace("��", "A");
        n = n.replace(" ", "");
        n = chineseToPingyin(n);
        n = n.toLowerCase();
        return n;
    }

    private String chineseToPingyin(String chinese) {
        String pinyinName = "";
        char[] nameChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);
                } catch (Exception e) {
                }
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }

    private static class FundData {
        private String id_;
        private String fundName_;
        private String lastPrice_;
        private String price_;
        private String fundChange_;
        private String indexChange_;
        private String fundPremium_;
        private String time_;
        private String profit_;
        private String earn_;
        private String earnChange_;
    }

    private static class StockData {
        private String id_;
        private String stockName_;
        private String lastPrice_;
        private String price_;
        private String stockChange_;
        private String volume_;
        private String amount_;
        private String pe_;
        private String pb_;
        private String time_;
        private String profit_;
        private String earn_;
        private String earnChange_;
    }

}
