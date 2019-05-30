package com.BeeFramework.Utils;


import android.util.Base64;

import com.lhqpay.ewallet.keepIntact.Base64Util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @name ${yuansk}
 * @class name：com.BeeFramework.Utils
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/1/23 19:31
 * @change
 * @chang time
 * @class describe
 */
public class EncryptUtil {

    private EncryptUtil() {

    }

    // 生成AES秘钥
    public static String generateAESSecretKey() {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom());
            return Base64.encodeToString(kgen.generateKey().getEncoded(), android.util.Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    // AES加密
    public static String aesEncrypt(String originText, String secret) {
        AesCypher cypher = new AesCypher(secret);
        try {
            return cypher.encrypt(originText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "00000";
    }

    // AES解密
    public static String aesDecrypt(String encryptedText, String secret) throws Exception {
        AesCypher cypher = new AesCypher(secret);
        return cypher.decrypt(encryptedText);
    }


    // 内部aes类
    static class AesCypher {
        private SecretKey key;
        private Cipher cipher;

        public AesCypher(String secret) {
            try {
                byte[] secrets = Base64Util.decode(secret);
                // 转换为AES专用密钥
                this.key = new SecretKeySpec(secrets, "AES");
                // 创建密码器，算法/工作模式/补码方式 提供商
                this.cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
            } catch (Exception e) {
            }

        }

        public synchronized String encrypt(String plainText) throws Exception {
            this.cipher.init(Cipher.ENCRYPT_MODE, this.key);
            byte[] cipherText = this.cipher.doFinal(plainText.getBytes());
            return new String(Base64Util.encode(cipherText));
        }

        public synchronized String decrypt(String codedText) throws Exception {
            byte[] encypted = Base64Util.decode(codedText);
            this.cipher.init(Cipher.DECRYPT_MODE, this.key);
            byte[] decrypted = this.cipher.doFinal(encypted);
            return new String(decrypted, "UTF-8");
        }
    }

    public static void main(String[] args) throws Exception {
        String plainText = "";
        // aes解密 和 解密
        String aesKey = EncryptUtil.generateAESSecretKey();
        System.out.println("AES秘钥: " + aesKey);
        String aesEncrypted = EncryptUtil.aesEncrypt(plainText, aesKey);
        System.out.println("AES密文: " + aesEncrypted);
        System.out.println("AES明文: " + EncryptUtil.aesDecrypt(aesEncrypted, aesKey));
    }
}
