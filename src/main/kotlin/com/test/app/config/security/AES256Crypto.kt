package com.test.app.config.security

import org.apache.logging.log4j.util.Strings
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Created by haeyup.yang on 2022-04-28.
 *
 * emart-app review 서비스에서 aes256 으로 custId 값을 암호화 해서 사용하고 있다.
 * 시스템간 연속성 차원에서 동일하게 push 시스템에서 사용 하도록 한다.
 *
 * 참고. https://dev.azure.com/emartdev/EmartApp/_git/renew-emartapp.emart.com?path=/src/main/java/emart/store/com/util/service/AES256CryptionUtil.java&version=GBmaster&_a=contents
 */
object AES256Crypto {
    private const val alg = "AES/CBC/NoPadding"
    private const val key = "emrtemrtemrtemrt"
    private const val iv = "emrtemrtemrtemrt"

    private val keyspec = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "AES")
    private val ivspec = IvParameterSpec(iv.toByteArray(Charsets.UTF_8))

    fun encrypt(text: String?): String {
        if (text.isNullOrBlank()) {
            return Strings.EMPTY
        }

        val cipher = Cipher.getInstance(alg)

        cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec)

        val blockSize = cipher.blockSize
        val dataBytes = text.toByteArray(Charsets.UTF_8)

        // find fillChar & pad
        val fillChar = blockSize - (dataBytes.size % blockSize)
        val plaintext = ByteArray(dataBytes.size + fillChar)

        Arrays.fill(plaintext, fillChar.toByte())

        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.size)

        val cipherBytes = cipher.doFinal(plaintext)

        return Base64.getEncoder().encodeToString(cipherBytes)
    }

    fun decrypt(cipherText: String?): String {
        if (cipherText.isNullOrBlank()) {
            return Strings.EMPTY
        }

        val cipher = Cipher.getInstance(alg)

        cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec)

        val base64decoded = Base64.getDecoder().decode(cipherText.toByteArray(Charsets.UTF_8))
        val aesdecode = cipher.doFinal(base64decoded)

        // unpad
        val origin = ByteArray(aesdecode.size - aesdecode[aesdecode.size - 1])
        System.arraycopy(aesdecode, 0, origin, 0, origin.size)

        return String(origin, Charsets.UTF_8)
    }
}
