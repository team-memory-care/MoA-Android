package com.moa.app.feature.senior.quiz.tts

object QuizTextNormalizer {
    fun normalizeExpression(expression: String): String {
        return expression
            .replace("-", " 빼기 ")
            .replace("+", " 더하기 ")
            .replace("*", " 곱하기 ")
            .replace("/", " 나누기 ")
            .replace("=", " 은(는) ")
            .trim()
    }
}
