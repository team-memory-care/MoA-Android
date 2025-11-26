package com.moa.app.data.auth.datasourceImpl

import com.moa.app.data.auth.datasource.AuthDataSource
import com.moa.app.data.auth.model.request.PhoneAuthCodeRequest
import com.moa.app.data.auth.model.request.SignUpUserRequest
import com.moa.app.data.auth.model.response.AuthTokenResponse
import com.moa.app.data.auth.model.response.ParentRoleResponse
import com.moa.app.data.auth.service.AuthService
import com.moa.app.network.auth.TokenService
import com.moa.app.network.auth.ReissueTokenRequest
import com.moa.app.network.auth.TokenResponse
import com.moa.app.network.extension.toResult
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService,
    private val tokenService: TokenService
) : AuthDataSource {
    override suspend fun requestPhoneAuthCode(request: PhoneAuthCodeRequest): Result<Unit> {
        return authService.requestPhoneAuthCode(request).toResult()
    }

    override suspend fun signUp(request: SignUpUserRequest): Result<AuthTokenResponse> {
        return authService.signUp(request).toResult { it }
    }

    override suspend fun setParentRole(): Result<ParentRoleResponse> {
        return authService.setParentRole().toResult { it }
    }

    override suspend fun reissueToken(request: ReissueTokenRequest): Result<TokenResponse> {
        return tokenService.reissueToken(request).toResult { it }
    }
}
