package com.moa.app.data.auth.datasourceImpl

import com.moa.app.data.auth.datasource.AuthDataSource
import com.moa.app.data.auth.model.request.PhoneAuthCodeRequest
import com.moa.app.data.auth.service.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthDataSource {
    override suspend fun requestPhoneAuthCode(request: PhoneAuthCodeRequest): Result<Unit> {
        return authService.requestPhoneAuthCode(request).toResult()
    }

}
