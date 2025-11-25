package com.moa.app.data.auth.datasource

import com.moa.app.data.auth.model.request.PhoneAuthCodeRequest
import com.moa.app.data.auth.model.request.SignUpUserRequest
import com.moa.app.data.auth.model.response.AuthTokenResponse

interface AuthDataSource {
    suspend fun requestPhoneAuthCode(request: PhoneAuthCodeRequest): Result<Unit>
    suspend fun signUp(request: SignUpUserRequest): Result<AuthTokenResponse>
}
