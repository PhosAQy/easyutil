package com.phosa.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 使用Java实现AES-GCM加密和解密的工具类。
 * <p>该工具类提供了基于AES-GCM模式的加密和解密方法，AES-GCM具有更好的安全性，支持认证。
 */
public class AESGCMUtil extends EncryptionUtil {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12; // GCM推荐的IV长度为12字节
    private static final int GCM_TAG_LENGTH = 128; // GCM认证标签长度，单位为位

    private AESGCMUtil() {

    }

    /**
     * 使用AES-GCM加密明文。
     *
     * @param plaintext 明文字符串
     * @param key       加密密钥（长度应为128、192或256位）
     * @return Base64编码的密文字符串
     */
    public static String encrypt(String plaintext, String key) {
        try {
            // 生成随机的12字节初始化向量（IV）
            byte[] iv = new byte[GCM_IV_LENGTH];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);

            // 准备密钥
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);

            // 初始化AES加密器
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);

            // 加密明文
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

            // 拼接IV和密文并进行Base64编码
            byte[] encryptedMessage = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, encryptedMessage, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, encryptedMessage, iv.length, encryptedBytes.length);

            return Base64.getEncoder().encodeToString(encryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用AES-GCM解密密文。
     *
     * @param ciphertext Base64编码的密文字符串
     * @param key        解密密钥（长度应为128、192或256位）
     * @return 解密后的明文字符串
     */
    public static String decrypt(String ciphertext, String key) {
        try {
            // 解码Base64编码的密文
            byte[] encryptedMessage = Base64.getDecoder().decode(ciphertext);

            // 提取IV和密文
            byte[] iv = new byte[GCM_IV_LENGTH];
            byte[] encryptedBytes = new byte[encryptedMessage.length - GCM_IV_LENGTH];
            System.arraycopy(encryptedMessage, 0, iv, 0, GCM_IV_LENGTH);
            System.arraycopy(encryptedMessage, GCM_IV_LENGTH, encryptedBytes, 0, encryptedBytes.length);

            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);

            // 初始化AES解密器
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);

            // 解密密文
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成随机AES密钥。
     *
     * @param keySize 密钥长度（128、192或256）
     * @return Base64编码的密钥字符串
     */
    public static String generateKey(int keySize) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(keySize);
            SecretKey secretKey = keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
