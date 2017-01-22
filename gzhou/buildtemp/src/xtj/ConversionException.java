package xtj;

public class ConversionException extends Exception {

    private static final long serialVersionUID = -4410941665491888853L;

    public ConversionException(String msg) {
        super(msg);
    }

    public ConversionException(String msg, Throwable t) {
        super(msg, t);
    }

    public ConversionException(Throwable t) {
        super(t);
    }

}
