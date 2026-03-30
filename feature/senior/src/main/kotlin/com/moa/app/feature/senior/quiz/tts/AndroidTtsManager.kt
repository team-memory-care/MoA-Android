package com.moa.app.feature.senior.quiz.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.util.Locale
import java.util.UUID

class AndroidTtsManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : TtsManager, DefaultLifecycleObserver {

    private enum class EngineState { UNINITIALIZED, INITIALIZING, READY, ERROR }

    private var tts: TextToSpeech? = null

    private val engineState = MutableStateFlow(EngineState.UNINITIALIZED)

    private val _playbackState = MutableStateFlow(PlaybackState.IDLE)
    override val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this@AndroidTtsManager)
    }

    override fun speak(text: String, isFlush: Boolean) {
        scope.launch {
            checkAndInitializeEngine()

            val currentState = withTimeoutOrNull(10_000L) {
                engineState.first { it == EngineState.READY || it == EngineState.ERROR }
            } ?: run {
                _playbackState.value = PlaybackState.ERROR
                return@launch
            }

            if (currentState == EngineState.ERROR) {
                _playbackState.value = PlaybackState.ERROR
                return@launch
            }

            val queueMode = if (isFlush) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
            val utteranceId = UUID.randomUUID().toString()

            try {
                tts?.speak(text, queueMode, null, utteranceId)
            } catch (e: Exception) {
                _playbackState.value = PlaybackState.ERROR
            }
        }
    }

    private fun checkAndInitializeEngine() {
        val canInitialize = engineState.compareAndSet(
            expect = EngineState.UNINITIALIZED,
            update = EngineState.INITIALIZING,
        ) || engineState.compareAndSet(
            expect = EngineState.ERROR,
            update = EngineState.INITIALIZING,
        )

        if (canInitialize) initEngine()
    }

    private fun initEngine() {
        tts?.shutdown()
        tts = null
        tts = TextToSpeech(context) { status ->
            if (status != TextToSpeech.SUCCESS) {
                engineState.update { EngineState.ERROR }
                return@TextToSpeech
            }

            tts?.setSpeechRate(0.85f)
            val result = tts?.setLanguage(Locale.KOREAN)

            when (result) {
                TextToSpeech.LANG_MISSING_DATA, TextToSpeech.LANG_NOT_SUPPORTED, null -> {
                    engineState.update { EngineState.ERROR }
                }

                else -> {
                    setupProgressListener()
                    engineState.update { EngineState.READY }
                }
            }
        }
    }

    private fun setupProgressListener() {
        tts?.setOnUtteranceProgressListener(
            object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    scope.launch { _playbackState.value = PlaybackState.SPEAKING }
                }

                override fun onDone(utteranceId: String?) {
                    scope.launch { _playbackState.value = PlaybackState.IDLE }
                }

                override fun onError(utteranceId: String?, errorCode: Int) {
                    scope.launch { _playbackState.value = PlaybackState.ERROR }
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    scope.launch { _playbackState.value = PlaybackState.ERROR }
                }
            },
        )
    }

    override fun stop() {
        tts?.stop()
        if (_playbackState.value == PlaybackState.SPEAKING) {
            _playbackState.value = PlaybackState.IDLE
        }
    }

    override fun resetSession() {
        tts?.stop()
        _playbackState.value = PlaybackState.IDLE
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        stop()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        tts?.shutdown()
        tts = null
        engineState.value = EngineState.UNINITIALIZED
    }

}
