package com.hqjl.crm.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EncryptUtil {

    private static final Log logger_ = LogFactory.getLog(EncryptUtil.class);

    private static MessageDigest md;
    private static Base64 encoder;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
            encoder = new Base64();
        } catch (NoSuchAlgorithmException e) {
            if (logger_.isErrorEnabled()) {
                logger_.error(e);
            }
        }
    }

    public static String md5(String s) {
        md.update(s.getBytes());
        byte[] b = md.digest();
        b = encoder.encode(b);
        return new String(b);
    }

}
