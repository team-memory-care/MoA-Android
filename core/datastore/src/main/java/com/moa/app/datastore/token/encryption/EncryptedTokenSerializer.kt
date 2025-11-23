package com.moa.app.datastore.token.encryption

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.moa.app.datastore.token.model.TokenData
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class EncryptedTokenSerializer(
    private val cryptoManager: CryptoManager,
) : Serializer<TokenData> {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    override val defaultValue: TokenData = TokenData.INIT

    override suspend fun readFrom(input: InputStream): TokenData {
        return try {
            val encryptedData = input.readBytes()

            if (encryptedData.isEmpty()) return defaultValue

            val decryptedBytes = cryptoManager.decrypt(encryptedData)
            val jsonString = decryptedBytes.decodeToString()
            json.decodeFromString<TokenData>(jsonString)
        } catch (e: Exception) {
            throw CorruptionException("Failed to decrypt token data: ${e.message}", e)
        }
    }

    override suspend fun writeTo(t: TokenData, output: OutputStream) {
        try {
            val jsonString = json.encodeToString(TokenData.serializer(), t)
            val plaintext = jsonString.encodeToByteArray()
            val encryptedData = cryptoManager.encrypt(plaintext)
            output.write(encryptedData)
        } catch (e: Exception) {
            throw CorruptionException("Failed to encrypt token data: ${e.message}", e)
        }
    }
}
