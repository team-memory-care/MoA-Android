package com.moa.app.data.auth.datasource

import com.moa.app.data.auth.model.request.PhoneAuthCodeRequest
import com.moa.app.data.auth.model.request.SignUpUserRequest
import com.moa.app.data.auth.model.response.AuthTokenResponse
import com.moa.app.data.auth.model.response.ParentRoleResponse
import com.moa.app.network.auth.ReissueTokenRequest
import com.moa.app.network.auth.TokenResponse

interface AuthDataSource {
    suspend fun requestPhoneAuthCode(request: PhoneAuthCodeRequest): Result<Unit>
    suspend fun signUp(request: SignUpUserRequest): Result<AuthTokenResponse>
    suspend fun requestSignInAuthCode(phoneNumber: String): Result<Unit>
    suspend fun signIn(phoneNumber: String, authCode: String): Result<TokenResponse>
    suspend fun setParentRole(): Result<ParentRoleResponse>
    suspend fun reissueToken(request: ReissueTokenRequest): Result<TokenResponse>
    suspend fun logOut(accessToken: String, refreshToken: String): Result<Unit>
}
