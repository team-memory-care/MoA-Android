package com.moa.app.feature.senior.quiz.tts

import kotlinx.coroutines.flow.StateFlow

interface TtsManager {
    val isSpeaking: Boolean
        get() = playbackState.value == PlaybackState.SPEAKING

    val playbackState: StateFlow<PlaybackState>

    fun speak(text: String, isFlush: Boolean = true)

    fun stop()

    fun resetSession()
}
