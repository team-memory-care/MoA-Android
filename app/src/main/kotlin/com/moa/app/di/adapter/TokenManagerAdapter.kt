package com.moa.app.di.adapter

import com.moa.app.datastore.token.TokenStorage
import com.moa.app.network.auth.TokenManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManagerAdapter @Inject constructor(
    private val tokenStorage: TokenStorage
) : TokenManager {

    override suspend fun getAccessToken(): String? = tokenStorage.getAccessToken()

    override suspend fun getRefreshToken(): String? = tokenStorage.getRefreshToken()

    override suspend fun saveTokens(accessToken: String, refreshToken: String) =
        tokenStorage.saveTokens(accessToken, refreshToken)

    override suspend fun clearTokens() = tokenStorage.clearTokens()
}
