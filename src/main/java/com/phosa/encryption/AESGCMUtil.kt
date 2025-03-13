package com.phosa.encryption

import com.phosa.encryption.EncryptionUtil.AES
import com.phosa.encryption.EncryptionUtil.AES_GCM_NoPadding
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * 使用Java实现AES-GCM加密和解密的工具类。
 *
 * 该工具类提供了基于AES-GCM模式的加密和解密方法，AES-GCM具有更好的安全性，支持认证。
 */
object AESGCMUtil {
    private const val GCM_IV_LENGTH = 12 // GCM推荐的IV长度为12字节
    private const val GCM_TAG_LENGTH = 128 // GCM认证标签长度，单位为位

    /**
     * 使用AES-GCM加密明文。
     *
     * @param plaintext 明文字符串
     * @param key       加密密钥（长度应为128、192或256位）
     * @return Base64编码的密文字符串
     */
    fun encrypt(plaintext: String, key: String): String? {
        try {
            // 生成随机的12字节初始化向量（IV）
            val iv = ByteArray(GCM_IV_LENGTH)
            val random = SecureRandom()
            random.nextBytes(iv)
            val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH, iv)

            // 准备密钥
            val keySpec = SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), AES)

            // 初始化AES加密器
            val cipher = Cipher.getInstance(AES_GCM_NoPadding)
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec)

            // 加密明文
            val encryptedBytes = cipher.doFinal(plaintext.toByteArray(StandardCharsets.UTF_8))

            // 拼接IV和密文并进行Base64编码
            val encryptedMessage = ByteArray(iv.size + encryptedBytes.size)
            System.arraycopy(iv, 0, encryptedMessage, 0, iv.size)
            System.arraycopy(encryptedBytes, 0, encryptedMessage, iv.size, encryptedBytes.size)

            return Base64.getEncoder().encodeToString(encryptedMessage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 使用AES-GCM解密密文。
     *
     * @param ciphertext Base64编码的密文字符串
     * @param key        解密密钥（长度应为128、192或256位）
     * @return 解密后的明文字符串
     */
    fun decrypt(ciphertext: String?, key: String): String? {
        try {
            // 解码Base64编码的密文
            val encryptedMessage = Base64.getDecoder().decode(ciphertext)

            // 提取IV和密文
            val iv = ByteArray(GCM_IV_LENGTH)
            val encryptedBytes = ByteArray(encryptedMessage.size - GCM_IV_LENGTH)
            System.arraycopy(encryptedMessage, 0, iv, 0, GCM_IV_LENGTH)
            System.arraycopy(encryptedMessage, GCM_IV_LENGTH, encryptedBytes, 0, encryptedBytes.size)

            val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
            val keySpec = SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), AES)

            // 初始化AES解密器
            val cipher = Cipher.getInstance(AES_GCM_NoPadding)
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec)

            // 解密密文
            val decryptedBytes = cipher.doFinal(encryptedBytes)

            return String(decryptedBytes, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 生成随机AES密钥。
     *
     * @param keySize 密钥长度（128、192或256）
     * @return Base64编码的密钥字符串
     */
    fun generateKey(keySize: Int): String? {
        try {
            val keyGenerator = KeyGenerator.getInstance(AES)
            keyGenerator.init(keySize)
            val secretKey = keyGenerator.generateKey()
            return Base64.getEncoder().encodeToString(secretKey.getEncoded())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
