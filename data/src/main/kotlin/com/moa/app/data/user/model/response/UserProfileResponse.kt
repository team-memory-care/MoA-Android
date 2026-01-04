package com.moa.app.data.user.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("phoneNumber") val phoneNumber: String,
    @SerialName("birthDate") val birthDate: String,
    @SerialName("role") val role: String,
    @SerialName("gender") val gender: String,
    @SerialName("status") val status: String,
    @SerialName("parentCode") val parentCode: String?,
    @SerialName("parentUserIds") val parentUserId: List<Long>?
)
