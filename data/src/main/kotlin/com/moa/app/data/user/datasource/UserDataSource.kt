package com.moa.app.data.user.datasource

import com.moa.app.data.user.model.response.UserProfileResponse

interface UserDataSource {
    suspend fun getUserProfile(): Result<UserProfileResponse>
    suspend fun withdrawal(): Result<Unit>
}
