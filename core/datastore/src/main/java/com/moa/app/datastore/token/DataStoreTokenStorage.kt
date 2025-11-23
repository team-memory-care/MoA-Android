package com.moa.app.datastore.token

import androidx.datastore.core.DataStore
import com.moa.app.datastore.token.model.TokenData
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreTokenStorage @Inject constructor(
    private val dataStore: DataStore<TokenData>,
) : TokenStorage {

    override suspend fun getAccessToken(): String? {
        return dataStore.data
            .firstOrNull()
            ?.accessToken
            ?.ifEmpty { null }
    }

    override suspend fun getRefreshToken(): String? {
        return dataStore.data
            .firstOrNull()
            ?.refreshToken
            ?.ifEmpty { null }
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        dataStore.updateData { current ->
            current.copy(
                accessToken = accessToken,
                refreshToken = refreshToken,
            )
        }
    }

    override suspend fun clearTokens() {
        dataStore.updateData { TokenData.INIT }
    }
}
