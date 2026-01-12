package com.moa.app.feature.senior.quiz.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import java.util.Locale

class AndroidTtsManager @Inject constructor(
    @ApplicationContext private val context: Context
) : TtsManager, TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isReady = false
    private var pendingTask: (() -> Unit)? = null

    override val isSpeaking: Boolean
        get() = tts?.isSpeaking ?: false

    private fun initEngine() {
        if (tts != null) return
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.KOREAN)
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                isReady = true
                pendingTask?.invoke()
                pendingTask = null
            }
        } else {
            tts = null
        }
    }

    override fun speak(text: String, isFlush: Boolean) {
        if (tts == null) {
            initEngine()
        }

        if (!isReady) {
            pendingTask = { speak(text, isFlush) }
            return
        }

        val queueMode = if (isFlush) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
        tts?.speak(text, queueMode, null, text.hashCode().toString())
    }

    override fun stop() {
        tts?.stop()
        pendingTask = null
    }

    override fun destroy() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        isReady = false
        pendingTask = null
    }
}
