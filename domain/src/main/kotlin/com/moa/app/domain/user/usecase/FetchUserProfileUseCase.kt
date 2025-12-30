package com.moa.app.domain.user.usecase

import com.moa.app.domain.auth.model.UserProfile
import com.moa.app.domain.user.repository.UserRepository
import javax.inject.Inject

class FetchUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Result<UserProfile> {
        return userRepository.getUserProfile()
    }
}
