package com.moa.app.domain.auth.usecase

import com.moa.app.domain.auth.repository.AuthRepository
import javax.inject.Inject

class PhoneAuthCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(phoneNumber: String): Result<Unit> {
        return authRepository.requestPhoneAuthCode(phoneNumber)
    }
}
