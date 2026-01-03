package com.moa.app.data.user.model.response

import com.moa.app.domain.auth.model.Gender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeniorProfileResponse(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("birthDate") val birthDate: String,
    @SerialName("gender") val gender: Gender,
    @SerialName("phoneNumber") val phoneNumber: String
)
