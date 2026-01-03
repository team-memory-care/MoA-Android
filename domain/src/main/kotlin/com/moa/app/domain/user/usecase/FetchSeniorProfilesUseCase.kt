package com.moa.app.domain.user.usecase

import com.moa.app.domain.user.model.SeniorProfile
import com.moa.app.domain.user.repository.UserRepository
import javax.inject.Inject

class FetchSeniorProfilesUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(): Result<List<SeniorProfile>> {
        return repository.fetchSeniorProfiles()
    }

    suspend operator fun invoke(userId: Long): Result<SeniorProfile> {
        return repository.fetchSeniorProfile(userId)
    }
}
