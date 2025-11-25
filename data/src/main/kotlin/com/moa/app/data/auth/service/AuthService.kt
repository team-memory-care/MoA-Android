package com.moa.app.data.auth.service

import com.moa.app.data.auth.model.request.PhoneAuthCodeRequest
import com.moa.app.network.model.NetworkResult
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/api/v1/users/signup/sms")
    suspend fun requestPhoneAuthCode(
        @Body request: PhoneAuthCodeRequest,
    ): NetworkResult<Unit>

}
