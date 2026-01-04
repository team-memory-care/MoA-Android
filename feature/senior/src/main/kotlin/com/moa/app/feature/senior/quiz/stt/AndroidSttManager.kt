package com.moa.app.feature.senior.quiz.stt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

class AndroidSttManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : SttManager {
    private var speechRecognizer: SpeechRecognizer? = null

    private val _sttState = MutableStateFlow<SttState>(SttState.Idle)
    override val sttState: Flow<SttState> = _sttState.asStateFlow()

    private val recognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {
            _sttState.value = SttState.Speaking
        }

        override fun onResults(results: Bundle?) {
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            _sttState.value = SttState.Success(matches?.firstOrNull() ?: "")
        }

        override fun onError(error: Int) {
            when (error) {
                SpeechRecognizer.ERROR_NO_MATCH,
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT,
                    -> {
                    Timber.d("STT: 음성 인식 결과가 없습니다.")
                    _sttState.value = SttState.Success("")
                }

                else -> {
                    val errorMessage = mapErrorCodeToMessage(error)
                    _sttState.value = SttState.Error(errorMessage)
                }
            }
        }

        private fun mapErrorCodeToMessage(error: Int): String =
            when (error) {
                SpeechRecognizer.ERROR_AUDIO -> "오디오 녹음 오류"
                SpeechRecognizer.ERROR_CLIENT -> "클라이언트 오류"
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "권한 부족"
                SpeechRecognizer.ERROR_NETWORK -> "네트워크 오류"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "서비스 바쁨"
                SpeechRecognizer.ERROR_SERVER -> "서버 오류"
                else -> "알 수 없는 오류 ($error)"
            }

        override fun onRmsChanged(p0: Float) {}
        override fun onBeginningOfSpeech() {}
        override fun onBufferReceived(p0: ByteArray?) {}
        override fun onEndOfSpeech() {}
        override fun onEvent(p0: Int, p1: Bundle?) {}
        override fun onPartialResults(p0: Bundle?) {}
    }

    override fun startListening() {
        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
                setRecognitionListener(recognitionListener)
            }
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM,
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
        }
        speechRecognizer?.startListening(intent)
    }

    override fun stopListening() {
        speechRecognizer?.stopListening()
    }

    override fun destroy() {
        speechRecognizer?.destroy()
        speechRecognizer = null
    }
}
