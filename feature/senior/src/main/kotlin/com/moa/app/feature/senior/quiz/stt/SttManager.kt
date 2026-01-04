package com.moa.app.feature.senior.quiz.stt

import kotlinx.coroutines.flow.Flow

interface SttManager {
    val sttState: Flow<SttState>
    fun startListening()
    fun stopListening()
    fun destroy()
}
