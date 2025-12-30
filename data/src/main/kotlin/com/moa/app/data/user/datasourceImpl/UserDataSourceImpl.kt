package com.moa.app.data.user.datasourceImpl

import com.moa.app.data.user.datasource.UserDataSource
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

}
