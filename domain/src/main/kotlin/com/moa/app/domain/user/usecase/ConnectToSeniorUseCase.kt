package com.moa.app.domain.user.usecase

import com.moa.app.domain.user.repository.UserRepository
import javax.inject.Inject

class ConnectToSeniorUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(userId: Long): Result<Unit> {
        return userRepository.connectToSenior(userId)
    }
}
