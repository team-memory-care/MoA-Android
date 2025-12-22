package com.moa.app.domain.quiz.model

data class MemoryQuiz(
    override val id: Long,
    override val type: QuizCategory,
    override val questionFormat: String,
    override val questionContent: String,
    val answer: List<String>,
    val imageUrls: List<String>,
) : Quiz
