package com.moa.app.domain.user.repository

import com.moa.app.domain.auth.model.UserProfile
import com.moa.app.domain.user.model.SeniorProfile

interface UserRepository {
    suspend fun getUserProfile(): Result<UserProfile>
    suspend fun withdrawal(): Result<Unit>
    suspend fun validateParentCode(parentCode: String): Result<Long>
    suspend fun fetchSeniorProfile(userId: Long): Result<SeniorProfile>
    suspend fun fetchSeniorProfiles(): Result<List<SeniorProfile>>
    suspend fun connectToSenior(userId: Long): Result<Unit>
    suspend fun deleteSeniorProfile(userId: Long): Result<Unit>
}
