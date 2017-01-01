package wfunit;

import java.text.NumberFormat;

public class Result {

    private static final NumberFormat percentageFormat_ = NumberFormat.getPercentInstance();

    static {
        percentageFormat_.setMaximumFractionDigits(2);
        percentageFormat_.setMinimumFractionDigits(2);
    }

    public static NumberFormat getPercentageFormat() {
        return percentageFormat_;
    }

    private int errors_;
    private int failures_;
    private float successRate_;
    private int tests_;

    private float time_;

    public String getClassType() {
        if (errors_ > 0)
            return "Error";
        else if (failures_ > 0)
            return "Failure";
        else
            return "Pass";
    }

    public int getErrors() {
        return errors_;
    }

    public int getFailures() {
        return failures_;
    }

    public String getFormattedSuccessRate() {
        return percentageFormat_.format(successRate_);
    }

    public float getSuccessRate() {
        return successRate_;
    }

    public int getTests() {
        return tests_;
    }

    public float getTime() {
        return time_;
    }

    public void setErrors(int errors) {
        errors_ = errors;
    }

    public void setFailures(int failures) {
        failures_ = failures;
    }

    public void setSuccessRate(float successRate) {
        successRate_ = successRate;
    }

    public void setTests(int tests) {
        tests_ = tests;
    }

    public void setTime(float time) {
        time_ = time;
    }

    public void calculateSuccessRate() {
        successRate_ = ((float) (tests_ - failures_ - errors_)) / (float) tests_;
    }
}
