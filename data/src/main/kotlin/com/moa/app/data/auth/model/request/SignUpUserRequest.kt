package com.moa.app.data.auth.model.request

import com.moa.app.domain.auth.model.UserProfile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpUserRequest(
    @SerialName("name") val name: String,
    @SerialName("birthDate") val birthDate: String,
    @SerialName("phoneNumber") val phoneNumber: String,
    @SerialName("gender") val gender: String,
    @SerialName("authCode") val authCode: String,
)

fun UserProfile.toDto(): SignUpUserRequest {
    return SignUpUserRequest(
        name = this.name,
        birthDate = this.birthDate,
        phoneNumber = this.phoneNumber,
        gender = this.gender.toString(),
        authCode = this.authCode
    )
}
