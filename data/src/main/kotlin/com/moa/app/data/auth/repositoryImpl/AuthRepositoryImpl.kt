package com.moa.app.data.auth.repositoryImpl

import com.moa.app.data.auth.datasource.AuthDataSource
import com.moa.app.data.auth.model.request.PhoneAuthCodeRequest
import com.moa.app.data.auth.model.request.toDto
import com.moa.app.domain.auth.model.UserProfile
import com.moa.app.domain.auth.repository.AuthRepository
import com.moa.app.network.auth.TokenManager
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val tokenManager: TokenManager
) : AuthRepository {
    override suspend fun requestPhoneAuthCode(phoneNumber: String): Result<Unit> {
        val request = PhoneAuthCodeRequest(phoneNumber = phoneNumber)
        return authDataSource.requestPhoneAuthCode(request)
    }

    override suspend fun signUp(userProfile: UserProfile): Result<Unit> {
        return authDataSource.signUp(userProfile.toDto())
            .mapCatching { authTokenResponse ->
                tokenManager.saveTokens(
                    accessToken = authTokenResponse.accessToken,
                    refreshToken = authTokenResponse.refreshToken
                )
            }
    }
}
