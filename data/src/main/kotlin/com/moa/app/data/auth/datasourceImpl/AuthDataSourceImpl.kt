package com.moa.app.data.auth.datasourceImpl

import com.moa.app.data.auth.datasource.AuthDataSource
import com.moa.app.data.auth.model.request.PhoneAuthCodeRequest
import com.moa.app.data.auth.model.request.SignUpUserRequest
import com.moa.app.data.auth.model.response.AuthTokenResponse
import com.moa.app.data.auth.service.AuthService
import com.moa.app.network.extension.toResult
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthDataSource {
    override suspend fun requestPhoneAuthCode(request: PhoneAuthCodeRequest): Result<Unit> {
        return authService.requestPhoneAuthCode(request).toResult()
    }

    override suspend fun signUp(request: SignUpUserRequest): Result<AuthTokenResponse> {
        return authService.signUp(request).toResult { it }
    }
}
