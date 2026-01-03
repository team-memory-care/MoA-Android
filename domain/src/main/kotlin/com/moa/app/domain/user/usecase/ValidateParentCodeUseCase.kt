package com.moa.app.domain.user.usecase

import com.moa.app.domain.user.repository.UserRepository
import javax.inject.Inject

class ValidateParentCodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(parentCode: String): Result<Long> {
        return userRepository.validateParentCode(parentCode)
    }
}
