package com.moa.app.data.user.repositoryImpl

import com.moa.app.data.user.datasource.UserDataSource
import com.moa.app.domain.auth.model.Gender
import com.moa.app.domain.auth.model.UserProfile
import com.moa.app.domain.user.model.SeniorProfile
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

    override suspend fun validateParentCode(parentCode: String): Result<Long> {
        return userDataSource.validateParentCode(parentCode)
            .mapCatching { it.id }
    }

    override suspend fun connectToSenior(userId: Long): Result<Unit> {
//        return userDataSource.connectToSenior(userId)
        val response = userDataSource.connectToSenior(userId)

        return response.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { Result.failure(it) }
        )
    }

    override suspend fun fetchSeniorProfile(userId: Long): Result<SeniorProfile> {
        return userDataSource.fetchSeniorProfile(userId)
            .map {
                SeniorProfile(
                    id = it.id,
                    name = it.name,
                    birthDate = it.birthDate,
                    gender = it.gender,
                    phoneNumber = it.phoneNumber
                )
            }
    }

    override suspend fun fetchSeniorProfiles(): Result<List<SeniorProfile>> {
        return userDataSource.fetchSeniorProfiles()
            .map { response ->
                response.map {
                    SeniorProfile(
                        id = it.id,
                        name = it.name,
                        birthDate = it.birthDate,
                        gender = it.gender,
                        phoneNumber = it.phoneNumber
                    )
                }
            }
    }

}
