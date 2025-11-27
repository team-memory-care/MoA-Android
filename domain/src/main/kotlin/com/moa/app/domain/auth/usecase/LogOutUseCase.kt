package com.moa.app.domain.auth.usecase

import com.moa.app.domain.auth.repository.AuthRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Result<Unit> {
        return authRepository.logOut()
    }
}
