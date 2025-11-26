package com.moa.app.domain.auth.usecase

import com.moa.app.domain.auth.model.UserRole
import com.moa.app.domain.auth.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(phoneNumber: String, authCode: String): Result<UserRole> {
        return authRepository.signIn(phoneNumber, authCode)
            .mapCatching { userRole ->
                UserRole.fromString(userRole)
            }
    }
}
