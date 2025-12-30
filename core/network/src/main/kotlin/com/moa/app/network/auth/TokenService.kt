package com.moa.app.network.auth

import com.moa.app.network.model.NetworkResult
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {
    @NoAuth
    @POST("/api/v1/auth/token/reissue")
    suspend fun reissueToken(@Body request: ReissueTokenRequest): NetworkResult<TokenResponse>
}
