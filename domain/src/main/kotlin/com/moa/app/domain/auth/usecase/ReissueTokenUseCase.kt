package com.moa.app.domain.auth.usecase

import com.moa.app.domain.auth.model.UserRole
import com.moa.app.domain.auth.repository.AuthRepository
import javax.inject.Inject

class ReissueTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Result<UserRole> {
        return authRepository.reissueToken()
            .mapCatching { role ->
                UserRole.fromString(role)
            }
    }
}
