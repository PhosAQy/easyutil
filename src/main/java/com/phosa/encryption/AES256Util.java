package com.phosa.encryption;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AES256Util extends EncryptionUtil {

    /**
     * 使用AES-256对明文进行加密。
     *
     * @param plaintext 明文字符串
     * @param key       加密密钥
     * @return Base64编码的密文字符串
     */
    public static String encrypt(String plaintext, String key) {
        return baseEncrypt(AES, AES_CBC_PKCS5Padding, plaintext, key);
    }


    /**
     * 使用AES-256对Base64编码的密文进行解密。
     *
     * @param ciphertext Base64编码的密文字符串
     * @param key        解密密钥（需要与加密时使用的密钥相同）
     * @return 解密后的明文字符串
     */
    public static String decrypt(String ciphertext, String key) {
        return baseDecrypt(AES, AES_CBC_PKCS5Padding, ciphertext, key);
    }


}
