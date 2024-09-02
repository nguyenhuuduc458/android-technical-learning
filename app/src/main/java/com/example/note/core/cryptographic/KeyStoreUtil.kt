package com.example.note.core.cryptographic

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyPairGenerator
import java.security.KeyStore
import javax.crypto.Cipher

object KeyStoreUtil {
    const val KEY_ALIAS = "KEY_ALIAS"

    /**
     * To generate a key pair, use the KeyPairGenerator class to create a new RSA key pair or
     * KeyGenerator for AES keys if you prefer symmetric encryption. The keys are stored in the Android Keystore
     */
    fun generateKeyPair(alias: String) {
        val keyPairGenerator =
            KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_RSA,
                "AndroidKeyStore",
            )

        val keyGenParameterSpec =
            KeyGenParameterSpec
                .Builder(
                    alias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
                ).setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .build()

        keyPairGenerator.initialize(keyGenParameterSpec)
        keyPairGenerator.generateKeyPair()
    }

    /**
     * Save the encrypted token in a secure location, such as SharedPreferences or a secure file.
     */
    fun encryptData(
        data: String,
        alias: String,
    ): String {
        val keyStore =
            KeyStore.getInstance("AndroidKeyStore").apply {
                load(null)
            }
        val publicKey = keyStore.getCertificate(alias).publicKey

        val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)

        val encryptedData = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encryptedData, Base64.DEFAULT)
    }

    fun decryptData(
        encryptedData: String,
        alias: String,
    ): String {
        val keyStore =
            KeyStore.getInstance("AndroidKeyStore").apply {
                load(null)
            }
        val privateKey = keyStore.getKey(alias, null)

        val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
        cipher.init(Cipher.DECRYPT_MODE, privateKey)

        val decodedData = Base64.decode(encryptedData, Base64.DEFAULT)
        val decryptedData = cipher.doFinal(decodedData)
        return String(decryptedData, Charsets.UTF_8)
    }
}
