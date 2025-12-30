package com.moa.app.data.auth.service

import com.moa.app.data.auth.model.request.AuthTokenRequest
import com.moa.app.data.auth.model.request.PhoneAuthCodeRequest
import com.moa.app.data.auth.model.request.SignUpUserRequest
import com.moa.app.data.auth.model.response.AuthTokenResponse
import com.moa.app.data.auth.model.response.ParentRoleResponse
import com.moa.app.network.auth.NoAuth
import com.moa.app.network.auth.TokenResponse
import com.moa.app.network.model.NetworkResult
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService {

    @NoAuth
    @POST("/api/v1/users/signup/sms")
    suspend fun requestPhoneAuthCode(@Body request: PhoneAuthCodeRequest): NetworkResult<Unit>

    @NoAuth
    @POST("/api/v1/users/signup")
    suspend fun signUp(@Body request: SignUpUserRequest): NetworkResult<AuthTokenResponse>

    @NoAuth
    @POST("/api/v1/auth/sms/request")
    suspend fun requestSignInAuthCode(
        @Query("phoneNumber") phoneNumber: String
    ): NetworkResult<Unit>

    @NoAuth
    @POST("/api/v1/auth/login")
    suspend fun signIn(
        @Query("phoneNumber") phoneNumber: String,
        @Query("authCode") authCode: String,
    ): NetworkResult<TokenResponse>

    @POST("/api/v1/users/role/parent")
    suspend fun setParentRole(): NetworkResult<ParentRoleResponse>

    @POST("/api/v1/auth/logout")
    suspend fun logOut(): NetworkResult<Unit>

}
