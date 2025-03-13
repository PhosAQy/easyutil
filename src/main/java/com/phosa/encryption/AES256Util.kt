package com.phosa.encryption

import com.phosa.encryption.EncryptionUtil.AES
import com.phosa.encryption.EncryptionUtil.AES_CBC_PKCS5Padding
import com.phosa.encryption.EncryptionUtil.baseDecrypt
import com.phosa.encryption.EncryptionUtil.baseEncrypt

/**
 * AES256 加密工具
 */
object AES256Util {
    /**
     * 使用AES-256对明文进行加密。
     *
     * @param plaintext 明文字符串
     * @param key       加密密钥
     * @return Base64编码的密文字符串
     */
    fun encrypt(plaintext: String?, key: String?): String? {
        return baseEncrypt(AES, AES_CBC_PKCS5Padding, plaintext, key)
    }


    /**
     * 使用AES-256对Base64编码的密文进行解密。
     *
     * @param ciphertext Base64编码的密文字符串
     * @param key        解密密钥（需要与加密时使用的密钥相同）
     * @return 解密后的明文字符串
     */
    fun decrypt(ciphertext: String?, key: String?): String? {
        return baseDecrypt(AES, AES_CBC_PKCS5Padding, ciphertext, key)
    }
}
