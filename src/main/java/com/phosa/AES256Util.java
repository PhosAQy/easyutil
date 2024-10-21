package com.phosa;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

/**
 * java实现AES256加密解密
 * 依赖说明：
 * bcprov-jdk15-133.jar：PKCS7Padding
 * javabase64-1.3.1.jar：base64
 * local_policy.jar 和 US_export_policy.jar需添加到%JAVE_HOME%\jre\lib\security中（lib中版本适合jdk1.7）
 */

public class AES256Util {
    // String plaintext -> Base64-encoded String ciphertext
    public static String encrypt(String plaintext, String key) {
        try {
            // Generate a random 16-byte initialization vector
            byte initVector[] = new byte[16];
            (new Random()).nextBytes(initVector);
            IvParameterSpec iv = new IvParameterSpec(initVector);

            // prep the key
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            // prep the AES Cipher
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            // Encode the plaintext as array of Bytes
            byte[] cipherbytes = cipher.doFinal(plaintext.getBytes());

            // Build the output message initVector + cipherbytes -> base64
            byte[] messagebytes = new byte[initVector.length + cipherbytes.length];

            System.arraycopy(initVector, 0, messagebytes, 0, 16);
            System.arraycopy(cipherbytes, 0, messagebytes, 16, cipherbytes.length);

            // Return the cipherbytes as a Base64-encoded string
            return Base64.encodeBase64String(messagebytes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Base64-encoded String ciphertext -> String plaintext
    public static String decrypt(String ciphertext, String key) {
        try {
            byte[] cipherbytes = Base64.decodeBase64(ciphertext);

            byte[] initVector = Arrays.copyOfRange(cipherbytes,0,16);

            byte[] messagebytes = Arrays.copyOfRange(cipherbytes,16,cipherbytes.length);

            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            // Convert the ciphertext Base64-encoded String back to bytes, and
            // then decrypt
            byte[] byte_array = cipher.doFinal(messagebytes);

            // Return plaintext as String
            return new String(byte_array, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
