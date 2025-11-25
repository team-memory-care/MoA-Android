package com.moa.app.data.auth.repositoryImpl

import com.moa.app.data.auth.datasource.AuthDataSource
import com.moa.app.data.auth.model.request.PhoneAuthCodeRequest
import com.moa.app.domain.auth.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun requestPhoneAuthCode(phoneNumber: String): Result<Unit> {
        val request = PhoneAuthCodeRequest(phoneNumber = phoneNumber)
        return authDataSource.requestPhoneAuthCode(request)
    }

}
