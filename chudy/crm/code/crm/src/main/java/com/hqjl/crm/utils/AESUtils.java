package com.hqjl.crm.utils;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class AESUtils {

    private static final Logger LOG = Logger.getLogger(AESUtils.class);

    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 加密（解密）算法/工作模式/填充方式 java 6 支持PKCS5Padding填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * JDK中支持的AES的密钥长度是128位，�?个英文字�?8位，也就�?16个英文字�?
     */
    private static final String KEY = "asdfghjklqwertyu";

    /**
     * 生成密钥
     * 
     * @return：二进制密钥
     * @throws Exception
     */
    public static byte[] initKey() throws Exception {
        // 实例�?
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);

        // AES要求密钥长度�?128位�??192位�??256位，设置�?192或�??256会报异常
        kg.init(128);

        // 生成秘密密钥
        SecretKey secretKey = kg.generateKey();

        // 获得密钥的二进制编码形式
        return secretKey.getEncoded();
    }

    /**
     * 转换密钥
     * 
     * @param key
     *            ：二进制密钥
     * @return：密�?
     */
    private static Key toKey(byte[] key) {
        SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * 加密
     * 
     * @param data
     *            ：待加密数据
     * @return：加密之后的数据
     */
    public static String encrypt(String data) {
        try {
            return encrypt(data, KEY);
        } catch (Exception e) {
            LOG.error("加密异常", e);
        }

        return data;
    }

    /**
     * 加密
     * 
     * @param data
     *            ：待加密数据
     * @param key
     *            ：密�?
     * @return：加密之后的数据
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private static String encrypt(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        byte[] binaryData = encrypt(data.getBytes(), key.getBytes());

        return Base64.encodeBase64String(binaryData);
    }

    /**
     * 加密
     * 
     * @param data
     *            ：待加密数据
     * @param key
     *            ：密�?
     * @return：加密之后的数据
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // 设置为加密模�?
        return cipher(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     * 
     * @param data
     *            ：待解密数据
     * @return：解密之后的数据
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String decrypt(String data) {
        try {
            return decrypt(data, KEY);
        } catch (Exception e) {
            LOG.error("解密异常", e);
        }

        return data;
    }

    /**
     * 解密
     * 
     * @param data
     *            ：待解密数据
     * @param key
     *            ：密�?
     * @return：解密之后的数据
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private static String decrypt(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        byte[] dataByteArray = Base64.decodeBase64(data);

        byte[] binaryData = decrypt(dataByteArray, key.getBytes());
        return new String(binaryData);
    }

    /**
     * 解密
     * 
     * @param data
     *            ：待解密数据
     * @param key
     *            ：密�?
     * @return：解密之后的数据
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // 设置为解密模�?
        return cipher(data, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 加密或�?�解�?
     * 
     * @param data
     * @param key
     * @param opmode
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private static byte[] cipher(byte[] data, byte[] key, int opmode) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // 还原密钥
        Key k = toKey(key);

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        // 根据加密或�?�解密的模式进行初始�?
        cipher.init(opmode, k);

        // 执行加密或�?�解密操�?
        return cipher.doFinal(data);
    }
}
