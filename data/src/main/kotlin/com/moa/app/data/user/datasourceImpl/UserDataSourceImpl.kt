package com.moa.app.data.user.datasourceImpl

import com.moa.app.data.user.datasource.UserDataSource
import com.moa.app.data.user.model.response.ProfileResponse
import com.moa.app.data.user.model.response.SeniorProfileResponse
import com.moa.app.data.user.model.response.UserProfileResponse
import com.moa.app.data.user.service.UserService
import com.moa.app.network.extension.toResult
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserDataSource {

    override suspend fun getUserProfile(): Result<UserProfileResponse> {
        return userService.fetchUserProfile().toResult { it }
    }

    override suspend fun withdrawal(): Result<Unit> {
        return userService.withdrawal().toResult()
    }

    override suspend fun validateParentCode(parentCode: String): Result<SeniorProfileResponse> {
        return userService.validateParentCode(parentCode).toResult { it }
    }

    override suspend fun connectToSenior(userId: Long): Result<ProfileResponse> {
//        return userService.connectToSenior(userId).toResult()
        return userService.connectToSenior(userId).toResult { it }
    }

    override suspend fun fetchSeniorProfile(userId: Long): Result<SeniorProfileResponse> {
        return userService.fetchSeniorProfile(userId).toResult { it }
    }

    override suspend fun fetchSeniorProfiles(): Result<List<SeniorProfileResponse>> {
        return userService.fetchSeniorProfiles().toResult { it }
    }

    override suspend fun deleteSeniorProfile(userId: Long): Result<Unit> {
        return userService.deleteSeniorProfile(userId).toResult()
    }
}
