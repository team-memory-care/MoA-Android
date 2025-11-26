package com.moa.app.domain.auth.model

data class UserProfile(
    val name: String,
    val birthDate: String,
    val phoneNumber: String,
    val gender: Gender,
    val authCode: String,
)
