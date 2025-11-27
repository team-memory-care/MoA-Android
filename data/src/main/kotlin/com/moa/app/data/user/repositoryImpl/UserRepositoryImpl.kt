package com.moa.app.data.user.repositoryImpl

import com.moa.app.data.user.datasource.UserDataSource
import com.moa.app.domain.auth.model.Gender
import com.moa.app.domain.auth.model.UserProfile
import com.moa.app.domain.user.repository.UserRepository
import com.moa.app.network.auth.TokenManager
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val tokenManager: TokenManager,
) : UserRepository {

    override suspend fun getUserProfile(): Result<UserProfile> {
        return userDataSource.getUserProfile()
            .mapCatching { response ->
                UserProfile(
                    name = response.name,
                    birthDate = response.birthDate,
                    phoneNumber = response.phoneNumber,
                    gender = Gender.fromString(response.gender),
                    authCode = response.parentCode
                )
            }
    }

    override suspend fun withdrawal(): Result<Unit> {
        return userDataSource.withdrawal()
            .onSuccess { tokenManager.clearTokens() }
    }
}
