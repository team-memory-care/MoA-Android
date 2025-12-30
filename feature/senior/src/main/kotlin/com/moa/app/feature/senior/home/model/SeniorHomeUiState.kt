package com.moa.app.feature.senior.home.model

data class SeniorHomeUiState(
    val userName: String,
) {
    companion object {
        val INIT = SeniorHomeUiState(
            userName = "",
        )
    }
}
