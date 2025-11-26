package com.moa.app.data.auth.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhoneAuthCodeRequest(
    @SerialName("phoneNumber") val phoneNumber: String,
)
