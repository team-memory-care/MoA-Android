package com.moa.app.domain.auth.usecase

import com.moa.app.domain.auth.repository.AuthRepository
import javax.inject.Inject

class SetParentRoleUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Result<String> {
        return authRepository.setParentRole()
    }
}
