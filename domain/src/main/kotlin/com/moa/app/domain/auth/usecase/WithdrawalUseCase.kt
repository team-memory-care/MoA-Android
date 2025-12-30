package com.moa.app.domain.auth.usecase

import com.moa.app.domain.user.repository.UserRepository
import javax.inject.Inject

class WithdrawalUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Result<Unit> {
        return userRepository.withdrawal()
    }
}
