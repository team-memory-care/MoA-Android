package com.moa.app.domain.auth.repository

interface AuthRepository {
    suspend fun requestPhoneAuthCode(phoneNumber: String): Result<Unit>
}
