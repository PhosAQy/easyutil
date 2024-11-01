package com.phosa.encryption;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

/**
 * 使用Java实现AES-256加密和解密功能的工具类。
 */
@Slf4j
public class EncryptionUtil {
    public static String AES = "AES";
    public static String AES_CBC_NoPadding = "AES/CBC/NoPadding";
    public static String AES_CBC_PKCS5Padding = "AES/CBC/PKCS5Padding";
    public static String AES_ECB_NoPadding = "AES/ECB/NoPadding";
    public static String AES_ECB_PKCS5Padding = "AES/ECB/PKCS5Padding";

    public static String DES = "DES";
    public static String DES_CBC_NoPadding = "DES/CBC/NoPadding";
    public static String DES_CBC_PKCS5Padding = "DES/CBC/PKCS5Padding";
    public static String DES_ECB_NoPadding = "DES/ECB/NoPadding";
    public static String DES_ECB_PKCS5Padding = "DES/ECB/PKCS5Padding";

    public static String DESede = "DESede";
    public static String DESede_CBC_NoPadding = "DESede/CBC/NoPadding";
    public static String DESede_CBC_PKCS5Padding = "DESede/CBC/PKCS5Padding";
    public static String DESede_ECB_NoPadding = "DESede/ECB/NoPadding";
    public static String DESede_ECB_PKCS5Padding = "DESede/ECB/PKCS5Padding";
    public static String RSA_ECB_PKCS1Padding = "RSA/ECB/PKCS1Padding";
    public static String RSA_ECB_OAEPWithSHA_1AndMGF1Padding = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
    public static String RSA_ECB_OAEPWithSHA_256AndMGF1Padding = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";


    /**
     * 使用AES-256对明文进行加密。
     * <pre>
     *     {@code
     *     String ciphertext = EncryptionUtil.encrypt(AES_CBC_PKCS5Padding, "Hello, world!", "1234567890123456");}
     * </pre>
     * 加密算法	密匙长度	向量长度
     * AES	    16	    16
     * DES	    8	    8
     * DES3	    24	    8
     * @param plaintext 明文字符串
     * @param key 加密密钥（需要特定位长度的字符串 AES:）
     * @return Base64编码的密文字符串
     */
    public static String baseEncrypt(String algorithm, String transformation, String plaintext, String key) {
        try {
            // 生成一个随机的16字节的初始化向量
            byte[] initVector = new byte[16];
            (new Random()).nextBytes(initVector);
            IvParameterSpec iv = new IvParameterSpec(initVector);

            // 准备密钥
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);

            // 准备AES加密器
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            // 将明文编码为字节数组
            byte[] cipherBytes = cipher.doFinal(plaintext.getBytes());

            // 将初始化向量和密文字节拼接起来 -> Base64编码
            byte[] messageBytes = new byte[initVector.length + cipherBytes.length];
            System.arraycopy(initVector, 0, messageBytes, 0, 16);
            System.arraycopy(cipherBytes, 0, messageBytes, 16, cipherBytes.length);

            // 返回Base64编码的密文字节
            return Base64.encodeBase64String(messageBytes);
        } catch (Exception ex) {
            log.error("加密失败", ex);
        }
        return null;
    }

    /**
     * 使用AES-256对Base64编码的密文进行解密。
     *
     * @param ciphertext Base64编码的密文字符串
     * @param key 解密密钥（需要与加密时使用的密钥相同）
     * @return 解密后的明文字符串
     */
    public static String baseDecrypt(String algorithm, String transformation, String ciphertext, String key) {
        try {
            // 将Base64编码的密文解码为字节数组
            byte[] cipherBytes = Base64.decodeBase64(ciphertext);

            // 获取前16字节作为初始化向量
            byte[] initVector = Arrays.copyOfRange(cipherBytes, 0, 16);

            // 获取剩下的字节作为加密信息
            byte[] messageBytes = Arrays.copyOfRange(cipherBytes, 16, cipherBytes.length);

            // 创建初始化向量和密钥
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);

            // 准备AES解密器
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            // 解密密文字节数组
            byte[] byte_array = cipher.doFinal(messageBytes);

            // 返回解密后的明文字符串
            return new String(byte_array, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            log.error("解密失败", ex);
        }

        return null;
    }

    public static String encrypt(String plaintext, String key) {
        return baseEncrypt("AES", "AES/CBC/PKCS5Padding", plaintext, key);
    }
    public static String decrypt(String plaintext, String key){
        return baseDecrypt("AES", "AES/CBC/PKCS5Padding", plaintext, key);
    }
}
