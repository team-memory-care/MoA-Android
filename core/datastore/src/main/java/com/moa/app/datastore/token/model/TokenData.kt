package com.moa.app.datastore.token.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenData(
    val accessToken: String,
    val refreshToken: String,
) {
    companion object {
        val INIT = TokenData(
            accessToken = "",
            refreshToken = "",
        )
    }
}
