package gzhou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class VerifyProxyTask extends Task {

    private File src_ = null;
    private boolean icbc_ = true;

    public void setSrc(File src) {
        src_ = src;
    }

    public void setIcbc(boolean icbc) {
        icbc_ = icbc;
    }

    @Override
    public void execute() throws BuildException {
        try {
            print();
            List<ProxyItem> proxies = parse();
            verifyProxies(proxies);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void print() {
        if (icbc_)
            log("icbc: ");
        else
            log("bjgjj: ");
    }

    private List<ProxyItem> parse() throws IOException {
        List<ProxyItem> proxies = new ArrayList<ProxyItem>();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(src_)));
        String line;
        while ((line = in.readLine()) != null) {
            // if (line.contains("±±¾©"))
                parseLine(proxies, line);
        }
        in.close();
        return proxies;
    }

    private void parseLine(List<ProxyItem> proxies, String line) {
        try {
            String s = line;
            int i = s.indexOf("@");
            s = s.substring(0, i);
            i = s.indexOf(":");
            String host = s.substring(0, i);
            String port = s.substring(i + 1);
            proxies.add(new ProxyItem(host, Integer.valueOf(port)));
        } catch (Exception e) {
        }
    }

    private void verifyProxies(List<ProxyItem> proxies) throws Exception {
        // thread pool
        ThreadPoolExecutor tpe = getExecutor();
        List<FutureTask> tasks = new ArrayList<FutureTask>();
        for (ProxyItem p : proxies) {
            String host = p.getHost();
            int port = p.getPort();
            tasks.add(submitTask(tpe, host, port));
        }
        // wait output
        for (FutureTask t : tasks) {
            t.get();
        }
        // shut down
        tpe.shutdown();
        tpe.awaitTermination(1, TimeUnit.MINUTES);
    }

    private FutureTask<String> submitTask(ThreadPoolExecutor tpe, final String host, final int port) {
        Callable<String> c = new Callable<String>() {
            @Override
            public String call() throws Exception {
                boolean ok = verifyProxy(host, port);
                if (ok) {
                    log("success ==> " + host + ":" + port);
                }
                return "DONE";
            }
        };
        FutureTask<String> task = new FutureTask<String>(c);
        tpe.execute(task);
        return task;
    }

    private ThreadPoolExecutor getExecutor() {
        int c = 20;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(c * 10000);
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(c, c, 5, TimeUnit.MINUTES, workQueue);
        return tpe;
    }

    private boolean verifyProxy(String host, int port) {
        // log("verify ==> " + host + ":" + port);
        boolean ok;
        ok = verifyProxy(host, port, "http://www.baidu.com/");
        if (icbc_) {
            if (!ok)
                return false;
            ok = verifyProxy(host, port, "https://mybank.icbc.com.cn/icbc/perbank/index.jsp");
            if (!ok)
                return false;
            ok = verifyProxy(host, port, "https://vip.icbc.com.cn/icbc/perbank/index.jsp");
            if (!ok)
                return false;
            ok = verifyProxy(host, port, "https://vip.icbc.com.cn/icbc/perbank/index.jsp");
        } else {
            if (!ok)
                return false;
            ok = verifyProxy(host, port, "http://www.bjgjj.gov.cn/wsyw/wscx/gjjcx-login.jsp");
            if (!ok)
                return false;
            ok = verifyProxy(host, port, "http://www.bjgjj.gov.cn/wsyw/wscx/gdcx-login.jsp");
            if (!ok)
                return false;
            ok = verifyProxy(host, port, "http://www.bjhjyd.gov.cn/");
        }
        if (!ok)
            return false;
        return true;
    }

    private boolean verifyProxy(String host, int port, String urlString) {
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection(proxy);
            conn.setConnectTimeout(200);
            conn.setReadTimeout(1000);
            conn.connect();
            conn.getInputStream();
            // log("        " + host + ":" + port + " -> " + urlString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static class ProxyItem {

        private String host_;
        private int port_;

        public ProxyItem(String host, int port) {
            host_ = host;
            port_ = port;
        }

        public String getHost() {
            return host_;
        }

        public int getPort() {
            return port_;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ProxyItem))
                return false;
            ProxyItem target = (ProxyItem) obj;
            return host_.equals(target.host_) && port_ == target.port_;
        }

        @Override
        public int hashCode() {
            int i = 0;
            i = i + 37 * host_.hashCode();
            i = i + 37 * port_;
            return i;
        }
    }
}
