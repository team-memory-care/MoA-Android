package com.moa.app.feature.senior.quiz.tts

interface TtsManager {
    val isSpeaking: Boolean
    fun speak(text: String, isFlush: Boolean = true)
    fun stop()
    fun destroy()
}
