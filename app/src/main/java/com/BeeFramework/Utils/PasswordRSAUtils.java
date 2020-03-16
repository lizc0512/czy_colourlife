package com.BeeFramework.Utils;

import android.util.Base64;

import com.BeeFramework.model.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @name ${yuansk}
 * @class name：com.BeeFramework.Utils
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/2/27 10:28
 * @change https://github.com/songxiaoliang/EncryptionLib/blob/master/app/src/main/java/com/android/song/encryptionlib/RSAUtils.java
 * @chang time
 * @class describe  4.1.0 和密码有关的RSA加密
 */


public final class PasswordRSAUtils {

    public static final String KEY_ALGORITHM = "RSA";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * 貌似默认是RSA/NONE/PKCS1Padding，未验证
     */
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String privateKeyString = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANkOqdamH0vG0PTO\n" +
            "C0YBhNN/aNh5X6eLkXWjTDcVqDg44PVgcJFQJciwXL6Ilia49/m8yRmuSiImYoF+\n" +
            "RojM2WslnMfOr4uw9A0IXzqnWcuy4duRY2/KM4Q+f8JSvI/YrGZ7hL54Sosh43Cn\n" +
            "vRU5oy4+ZGgRnEwMCC9zpnQNaxV1AgMBAAECgYA6M5Fc6hx4MC51hg5FzkB0ibRu\n" +
            "X1VD0svFNG1KU/dQZAgGI77raAdJoeisemlwpqZvg6yUw0RDlfFR8a8uHWeqsbEJ\n" +
            "LUMqaS5LbEBCheg4h/YbZ3MWtQwVxsSXAL1UZz7A1fJ68jJCap58ssBxR+PZtJ7v\n" +
            "whJRWE99sb9IGTJqfQJBAPvRFLAsHMDcgBWFtM3WdTIAYvHIo7MF+vu5h3wGlZuM\n" +
            "CduWZ/SGXQxM9jE3/d+SRdU2uGRvWZG0gKi6KWOlGiMCQQDcqcI1BAN3VEZjiUlk\n" +
            "DnuxQEmmFIYEdhYeewi/83sNsP1igjApkvYHeRcqVAbBFm9YdbfvsOksp1w0To3B\n" +
            "aM+HAkEAy2od2CIKKBD2tmwHna77hKSjSMTW16qhD+7S7vEysS/yVpus5e4UA/e3\n" +
            "eBLO1WcHWjCVyyvXose4lpOrE38vGQJBAMBFxyxM1/xVWZQHnwRpPkxfeQ0W2wPu\n" +
            "JS3gBOrE7Jcsfk0kpXTEUk6Gq7G9T7lVpCXPRTu8yiCXn9cQD6LUgecCQQCffITI\n" +
            "EnwAutE2pmzUaGIC3AyGzlx3/JHB/kKkPWilGt9TFN46fZBuy//E3f6WA8daPc/o\n" +
            "E3x4b6IIyvCbED1P";
    public static final int DEFAULT_KEY_SIZE = 2048; //秘钥默认长度
    public static final byte[] DEFAULT_SPLIT = "#PART#".getBytes();    // 当要加密的内容超过bufferSize，则采用partSplit进行分块加密
    public static final int DEFAULT_BUFFERSIZE = (DEFAULT_KEY_SIZE / 8) - 11; // 当前秘钥支持加密的最大字节数


    /*
     *  用户公钥加密content
     *
     * */
    public static String encryptByPublicKey(String content) {
        byte[] keyBytes = Base64Utils.decode(Constants.publicKeyString);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicK);
            byte[] data = content.getBytes("UTF-8");
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return Base64.encodeToString(encryptedData, android.util.Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
        return content;
    }

    /*支付时公钥加密*/
    public static String encryptByPayKey(String content) {
        byte[] keyBytes = Base64Utils.decode(Constants.PAYKEYSTRING);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicK);
            byte[] data = content.getBytes("UTF-8");
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return Base64.encodeToString(encryptedData, android.util.Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
        return content;
    }

    /**
     * 私钥加密
     *
     * @param data       待加密数据
     * @param privateKey 密钥
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] privateKey) throws Exception {

        // 得到私钥
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey keyPrivate = kf.generatePrivate(keySpec);
        // 数据加密
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keyPrivate);
        return cipher.doFinal(data);
    }


    /**
     * 公钥解密
     *
     * @param data      待解密数据
     * @param publicKey 密钥
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {

        // 得到公钥
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey keyPublic = kf.generatePublic(keySpec);
        // 数据解密
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keyPublic);
        return cipher.doFinal(data);
    }
}
