package com.moa.app.feature.senior.quiz.tts

import androidx.lifecycle.ViewModel

abstract class TtsAwareViewModel(
    protected val ttsManager: TtsManager,
) : ViewModel() {
    init {
        ttsManager.resetSession()
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.stop()
    }
}
