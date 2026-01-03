package com.moa.app.data.user.datasource

import com.moa.app.data.user.model.response.ProfileResponse
import com.moa.app.data.user.model.response.SeniorProfileResponse
import com.moa.app.data.user.model.response.UserProfileResponse

interface UserDataSource {
    suspend fun getUserProfile(): Result<UserProfileResponse>
    suspend fun withdrawal(): Result<Unit>
    suspend fun validateParentCode(parentCode: String): Result<SeniorProfileResponse>
    suspend fun connectToSenior(userId: Long): Result<ProfileResponse>
    suspend fun fetchSeniorProfile(userId: Long): Result<SeniorProfileResponse>
    suspend fun fetchSeniorProfiles(): Result<List<SeniorProfileResponse>>
}
