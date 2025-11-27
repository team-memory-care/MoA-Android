package com.moa.app.domain.auth.repository

import com.moa.app.domain.auth.model.UserProfile

interface AuthRepository {
    suspend fun requestPhoneAuthCode(phoneNumber: String): Result<Unit>
    suspend fun signUp(userProfile: UserProfile): Result<Unit>
    suspend fun requestSignInAuthCode(phoneNumber: String): Result<Unit>
    suspend fun signIn(phoneNumber: String, authCode: String): Result<String>
    suspend fun setParentRole(): Result<String>
    suspend fun reissueToken(): Result<String>
    suspend fun logOut(): Result<Unit>
}
