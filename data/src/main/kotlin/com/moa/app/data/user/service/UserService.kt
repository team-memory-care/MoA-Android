package com.moa.app.data.user.service

import com.moa.app.data.user.model.response.ProfileResponse
import com.moa.app.data.user.model.response.SeniorProfileResponse
import com.moa.app.data.user.model.response.UserProfileResponse
import com.moa.app.network.model.NetworkResult
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("/api/v1/users/me")
    suspend fun fetchUserProfile(): NetworkResult<UserProfileResponse>

    @DELETE("/api/v1/users/withdraw")
    suspend fun withdrawal(): NetworkResult<Unit>

    @GET("/api/v1/users/parent-code/{parentCode}/verify")
    suspend fun validateParentCode(
        @Path("parentCode") parentCode: String
    ): NetworkResult<SeniorProfileResponse>

    @POST("/api/v1/users/link-parent")
    suspend fun connectToSenior(@Query("parentId") userId: Long): NetworkResult<ProfileResponse>

    @GET("/api/v1/users/parent/{parentId}")
    suspend fun fetchSeniorProfile(@Path("parentId") userId: Long): NetworkResult<SeniorProfileResponse>

    @GET("/api/v1/users/my-parents")
    suspend fun fetchSeniorProfiles(): NetworkResult<List<SeniorProfileResponse>>

    @DELETE("/api/v1/users/my-parents/{parentId}")
    suspend fun deleteSeniorProfile(@Path("parentId") userId: Long): NetworkResult<Unit>
}
