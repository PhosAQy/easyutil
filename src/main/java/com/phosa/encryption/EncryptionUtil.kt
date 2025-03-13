package com.phosa.encryption

import org.apache.commons.codec.binary.Base64
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * 使用Java实现AES-256加密和解密功能的工具类。
 */
object EncryptionUtil {

    val log: Logger = LoggerFactory.getLogger(EncryptionUtil::class.java)

    const val AES: String = "AES"
    const val AES_CBC_NoPadding: String = "AES/CBC/NoPadding"
    const val AES_CBC_PKCS5Padding: String = "AES/CBC/PKCS5Padding"
    const val AES_ECB_NoPadding: String = "AES/ECB/NoPadding"
    const val AES_ECB_PKCS5Padding: String = "AES/ECB/PKCS5Padding"
    const val AES_GCM_NoPadding: String = "AES/GCM/NoPadding"
    const val AES_GCM_PKCS5Padding: String = "AES/GCM/PKCS5Padding"

    const val DES: String = "DES"
    const val DES_CBC_NoPadding: String = "DES/CBC/NoPadding"
    const val DES_CBC_PKCS5Padding: String = "DES/CBC/PKCS5Padding"
    const val DES_ECB_NoPadding: String = "DES/ECB/NoPadding"
    const val DES_ECB_PKCS5Padding: String = "DES/ECB/PKCS5Padding"

    const val DESede: String = "DESede"
    const val DESede_CBC_NoPadding: String = "DESede/CBC/NoPadding"
    const val DESede_CBC_PKCS5Padding: String = "DESede/CBC/PKCS5Padding"
    const val DESede_ECB_NoPadding: String = "DESede/ECB/NoPadding"
    const val DESede_ECB_PKCS5Padding: String = "DESede/ECB/PKCS5Padding"
    const val RSA_ECB_PKCS1Padding: String = "RSA/ECB/PKCS1Padding"
    const val RSA_ECB_OAEPWithSHA_1AndMGF1Padding: String = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding"
    const val RSA_ECB_OAEPWithSHA_256AndMGF1Padding: String = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding"


    /**
     * 使用AES-256对明文进行加密。
     * <pre>
     * `String ciphertext = EncryptionUtil.encrypt(AES_CBC_PKCS5Padding, "Hello, world!", "1234567890123456");`
    </pre> *
     * 加密算法	密匙长度	向量长度
     * AES	    16	    16
     * DES	    8	    8
     * DES3	    24	    8
     *
     * @param algorithm algorithm
     * @param transformation transformation
     * @param plaintext 明文字符串
     * @param key 加密密钥（需要特定位长度的字符串 AES:）
     * @return Base64编码的密文字符串
     */
    fun baseEncrypt(algorithm: String?, transformation: String?, plaintext: String?, key: String?): String? {
        try {
            // 生成一个随机的16字节的初始化向量
            val initVector = ByteArray(16)
            (Random()).nextBytes(initVector)
            val iv = IvParameterSpec(initVector)

            // 准备密钥
            val skeySpec = SecretKeySpec(key?.toByteArray(StandardCharsets.UTF_8), algorithm)

            // 准备AES加密器
            val cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)

            // 将明文编码为字节数组
            val cipherBytes = cipher.doFinal(plaintext?.toByteArray())

            // 将初始化向量和密文字节拼接起来 -> Base64编码
            val messageBytes = ByteArray(initVector.size + cipherBytes.size)
            System.arraycopy(initVector, 0, messageBytes, 0, 16)
            System.arraycopy(cipherBytes, 0, messageBytes, 16, cipherBytes.size)

            // 返回Base64编码的密文字节
            return Base64.encodeBase64String(messageBytes)
        } catch (ex: Exception) {
            log.error("加密失败", ex)
        }
        return null
    }

    /**
     * 使用AES-256对Base64编码的密文进行解密。
     *
     * @param algorithm algorithm
     * @param transformation
     * @param ciphertext Base64编码的密文字符串
     * @param key 解密密钥（需要与加密时使用的密钥相同）
     * @return 解密后的明文字符串
     */
    fun baseDecrypt(algorithm: String?, transformation: String?, ciphertext: String?, key: String?): String? {
        try {
            // 将Base64编码的密文解码为字节数组
            val cipherBytes = Base64.decodeBase64(ciphertext)

            // 获取前16字节作为初始化向量
            val initVector = cipherBytes.copyOfRange(0, 16)

            // 获取剩下的字节作为加密信息
            val messageBytes = cipherBytes.copyOfRange(16, cipherBytes.size)

            // 创建初始化向量和密钥
            val iv = IvParameterSpec(initVector)
            val skeySpec = SecretKeySpec(key?.toByteArray(StandardCharsets.UTF_8), algorithm)

            // 准备AES解密器
            val cipher = Cipher.getInstance(transformation)
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)

            // 解密密文字节数组
            val byteArray = cipher.doFinal(messageBytes)

            // 返回解密后的明文字符串
            return String(byteArray, StandardCharsets.UTF_8)
        } catch (ex: Exception) {
            log.error("解密失败", ex)
        }

        return null
    }

    fun encrypt(plaintext: String, key: String): String? {
        return baseEncrypt(AES, AES_CBC_PKCS5Padding, plaintext, key)
    }

    fun decrypt(plaintext: String?, key: String): String? {
        return baseDecrypt(AES, AES_CBC_PKCS5Padding, plaintext, key)
    }

}
