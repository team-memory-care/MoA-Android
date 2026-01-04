package com.moa.app.feature.senior.quiz.stt

sealed interface SttState {
    object Idle : SttState
    object Speaking : SttState
    data class Success(val result: String) : SttState
    data class Error(val message: String) : SttState
}
