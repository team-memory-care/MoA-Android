package com.moa.app.domain.quiz.model

import kotlinx.serialization.Serializable

/**
 * MMSE 기반 퀴즈 카테고리
 *
 * PERSISTENCE: 지남력
 * LINGUISTIC: 언어능력
 * MEMORY: 기억력
 * ATTENTION: 주의력
 * SPACETIME: 시공간
 */
@Serializable
enum class QuizCategory {
    ALL,
    PERSISTENCE,
    LINGUISTIC,
    MEMORY,
    ATTENTION,
    SPACETIME,

    ;
}
