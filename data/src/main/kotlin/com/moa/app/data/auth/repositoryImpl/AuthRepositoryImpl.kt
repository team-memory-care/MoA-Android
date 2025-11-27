package com.moa.app.data.auth.repositoryImpl

import com.moa.app.data.auth.datasource.AuthDataSource
import com.moa.app.data.auth.model.request.PhoneAuthCodeRequest
import com.moa.app.data.auth.model.request.toDto
import com.moa.app.domain.auth.model.UserProfile
import com.moa.app.domain.auth.repository.AuthRepository
import com.moa.app.network.auth.ReissueTokenRequest
import com.moa.app.network.auth.TokenManager
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val tokenManager: TokenManager,
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
                    refreshToken = authTokenResponse.refreshToken,
                )
            }
    }

    override suspend fun requestSignInAuthCode(phoneNumber: String): Result<Unit> {
        return authDataSource.requestSignInAuthCode(phoneNumber)
    }

    override suspend fun signIn(phoneNumber: String, authCode: String): Result<String> {
        return authDataSource.signIn(phoneNumber, authCode)
            .mapCatching { tokenResponse ->
                tokenManager.saveTokens(
                    accessToken = tokenResponse.accessToken,
                    refreshToken = tokenResponse.refreshToken,
                )
                tokenResponse.role
            }
    }

    override suspend fun setParentRole(): Result<String> {
        return authDataSource.setParentRole()
            .mapCatching { parentRoleResponse ->
                parentRoleResponse.parentCode
            }
    }

    override suspend fun reissueToken(): Result<String> {
        val refreshToken = tokenManager.getRefreshToken()
            ?: return Result.failure(Exception("Refresh token not found"))
        val request = ReissueTokenRequest(refreshToken)

        return authDataSource.reissueToken(request)
            .mapCatching { tokenResponse ->
                tokenManager.saveTokens(
                    accessToken = tokenResponse.accessToken,
                    refreshToken = tokenResponse.refreshToken,
                )
                tokenResponse.role
            }
    }

    override suspend fun logOut(): Result<Unit> {
        val accessToken = tokenManager.getAccessToken()
            ?: return Result.failure(Exception("Access token not found"))
        val refreshToken = tokenManager.getRefreshToken()
            ?: return Result.failure(Exception("Refresh token not found"))

        return authDataSource.logOut(accessToken, refreshToken)
    }
}
