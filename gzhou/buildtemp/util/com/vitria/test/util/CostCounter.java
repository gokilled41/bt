package com.vitria.test.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CostCounter {

    private static final Log logger_ = LogFactory.getLog(CostCounter.class);
    private static final long INTERVAL_IN_SECOND = 5;
    private static final long SECOND_IN_NANO = 1000000000l;
    // private static final long SECOND_IN_MILLI = 1000l;
    private static final long MILLI_IN_NANO = 1000000l;
    private static Map<String, CostCounter> counters_ = new HashMap<String, CostCounter>();

    private String name_;
    private ThreadLocal<Long> begin_ = new ThreadLocal<Long>();
    private ThreadLocal<Long> end_ = new ThreadLocal<Long>();
    private long count_;
    private long cost_;
    private long start_;

    public static synchronized void begin(String name) {
        CostCounter counter = counters_.get(name);
        if (counter == null) {
            counter = new CostCounter(name);
            counters_.put(name, counter);
        }
        counter.begin();
    }

    public static synchronized void end(String name) {
        CostCounter counter = counters_.get(name);
        if (counter == null) {
            counter = new CostCounter(name);
            counters_.put(name, counter);
        }
        counter.end();
    }

    private CostCounter(String name) {
        name_ = name;
    }

    private void begin() {
        begin_.set(System.nanoTime());
        if (start_ == 0)
            start_ = System.nanoTime();
    }

    private void end() {
        end_.set(System.nanoTime());
        count_++;
        cost_ += (end_.get() - begin_.get());
        if (isLog())
            log();
        if (isReset())
            resetAll();
    }

    private boolean isLog() {
        return (end_.get() - start_) > INTERVAL_IN_SECOND * SECOND_IN_NANO;
    }

    private boolean isReset() {
        return (end_.get() - start_) > 24 * INTERVAL_IN_SECOND * SECOND_IN_NANO;
    }

    private void log() {
        if (logger_.isWarnEnabled()) {
            logger_.warn(name_ + ": " + (cost_ / (count_ * MILLI_IN_NANO)) + " ms");
        }
        reset();
    }

    private void reset() {
        start_ = 0;
    }

    private void resetAll() {
        reset();
        cost_ = 0;
        count_ = 0;
    }
}
