package com.moa.app.domain.user.repository

import com.moa.app.domain.auth.model.UserProfile

interface UserRepository {
    suspend fun getUserProfile(): Result<UserProfile>
    suspend fun withdrawal(): Result<Unit>
}
