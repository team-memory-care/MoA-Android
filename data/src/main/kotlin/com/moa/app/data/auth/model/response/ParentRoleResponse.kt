package com.moa.app.data.auth.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParentRoleResponse(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("phoneNumber") val phoneNumber: String,
    @SerialName("birthdate") val birthdate: String,
    @SerialName("role") val role: String,
    @SerialName("gender") val gender: String,
    @SerialName("status") val status: String,
    @SerialName("parentCode") val parentCode: String,
)
