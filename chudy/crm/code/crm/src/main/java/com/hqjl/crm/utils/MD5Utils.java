package com.hqjl.crm.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * 此类是MD5加密算法的实现， 采用了java内置算法，需要引用java.security.MessageDigest
 *
 * @author airland.congs
 * @version $Revision: 1.1 $
 *
 */
public class MD5Utils {
    // 小写的字符串
    private static char[] DigitLower = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f' };

    // 大写的字符串
    private static char[] DigitUpper = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F' };

    /**
     * 默认构�?�函�?
     *
     */
    public MD5Utils() {
    }

    /**
     * 加密之后的字符串全为小写
     *
     * @param srcStr
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NullPointerException
     */
    protected static String getMD5Lower(String srcStr) throws NoSuchAlgorithmException {
        String sign = "lower";
        return processStr(srcStr, sign);
    }

    /**
     * 加密之后的字符串全为大写
     *
     * @param srcStr
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NullPointerException
     */
    protected static String getMD5Upper(String srcStr) throws NoSuchAlgorithmException {
        String sign = "upper";
        return processStr(srcStr, sign);
    }

    private static String processStr(String srcStr, String sign) throws NoSuchAlgorithmException, NullPointerException {
        MessageDigest digest;
        // 定义调用的方�?
        String algorithm = "MD5";
        // 结果字符�?
        String result = "";
        // 初始化并�?始进行计�?
        digest = MessageDigest.getInstance(algorithm);
        digest.update(srcStr.getBytes());
        byte[] byteRes = digest.digest();

        // 计算byte数组的长�?
        int length = byteRes.length;

        // 将byte数组转换成字符串
        for (int i = 0; i < length; i++) {
            result = result + byteHEX(byteRes[i], sign);
        }

        return result;
    }

    /**
     * 将btye数组转换成字符串
     *
     * @param bt
     * @return
     */
    private static String byteHEX(byte bt, String sign) {

        char[] temp = null;
        if (sign.equalsIgnoreCase("lower")) {
            temp = DigitLower;
        } else if (sign.equalsIgnoreCase("upper")) {
            temp = DigitUpper;
        } else {
            throw new java.lang.RuntimeException("加密缺少必要的条�?");
        }
        char[] ob = new char[2];

        ob[0] = temp[(bt >>> 4) & 0X0F];

        ob[1] = temp[bt & 0X0F];

        return new String(ob);
    }

    public static String getMD5(String content) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(content.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }

    public static void main(String[] args) {
        String content = getMD5("ailuobi");
        System.out.println(content);
    }

}
