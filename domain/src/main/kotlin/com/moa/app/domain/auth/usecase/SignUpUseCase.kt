package com.moa.app.domain.auth.usecase

import com.moa.app.domain.auth.model.UserProfile
import com.moa.app.domain.auth.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(userProfile: UserProfile): Result<Unit> {
        return authRepository.signUp(userProfile)
    }
}
