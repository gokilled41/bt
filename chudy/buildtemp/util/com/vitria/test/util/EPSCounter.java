package com.vitria.test.util;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EPSCounter {

    private static final Log logger_ = LogFactory.getLog(EPSCounter.class);

    private static final String dir_ = "C:\\testfolder\\predictive\\performance\\";

    private static final long INTERVAL_IN_SECOND = 5;
    // private static final long SECOND_IN_NANO = 1000000000l;
    private static final long SECOND_IN_MILLI = 1000l;
    private static Map<String, EPSCounter> counters_ = new HashMap<String, EPSCounter>();

    private String name_;
    private long start_;
    private long end_;
    private long count_;
    private String outputFile_;
    private PrintWriter out_;

    public static synchronized void count(String name) {
        count(name, 1);
    }

    public static synchronized void count(String name, int n) {
        EPSCounter counter = counters_.get(name);
        if (counter == null) {
            counter = new EPSCounter(name);
            counters_.put(name, counter);
        }
        counter.count(n);
    }

    public static synchronized void close(String name) {
        EPSCounter counter = counters_.get(name);
        if (counter != null) {
            counter.close();
            counters_.remove(name);
        }
    }

    private EPSCounter(String name) {
        name_ = name;
        outputFile_ = dir_ + name_ + ".txt";
        try {
            out_ = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFile_, false)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void count(int n) {
        if (start_ == 0)
            start_ = System.currentTimeMillis();
        count_ += n;
        end_ = System.currentTimeMillis();
        if (isLog())
            log();
    }

    private boolean isLog() {
        return (end_ - start_) > INTERVAL_IN_SECOND * SECOND_IN_MILLI;
    }

    private void log() {
        long eps = count_ * SECOND_IN_MILLI / (end_ - start_);
        if (logger_.isWarnEnabled()) {
            logger_.warn(name_ + ": " + eps + " eps");
        }
        out_.println(eps);
        out_.flush();
        reset();
    }

    private void close() {
        if (out_ != null) {
            out_.close();
        }
    }

    private void reset() {
        start_ = 0;
        end_ = 0;
        count_ = 0;
    }
}
