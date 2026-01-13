package com.moa.app.domain.user.model

import com.moa.app.domain.auth.model.Gender

data class SeniorProfile(
    val id: Long,
    val name: String,
    val birthDate: String,
    val gender: Gender,
    val phoneNumber: String,
)
