package com.moa.app.data.user.service

import com.moa.app.data.user.model.response.UserProfileResponse
import com.moa.app.network.model.NetworkResult
import retrofit2.http.DELETE
import retrofit2.http.GET

interface UserService {

    @GET("/api/v1/users/me")
    suspend fun fetchUserProfile(): NetworkResult<UserProfileResponse>

    @DELETE("/api/v1/users/withdraw")
    suspend fun withdrawal(): NetworkResult<Unit>

    @GET("/api/v1/users/parent-code/{parentCode}/verify")
    suspend fun validateParentCode(
        @Path("parentCode") parentCode: String
    ): NetworkResult<SeniorProfileResponse>
}
