package com.moa.app.domain.quiz.model

data class MemoryQuiz(
    override val id: Long,
    override val questionFormat: String,
    override val questionContent: String,
    val answer: List<String>,
    val imageUrls: List<String>,
) : Quiz {
    override fun isAnswerCorrect(userAnswer: UserAnswer): Boolean {
        return when (userAnswer) {
            is UserAnswer.Text -> isAnswerCorrect(userAnswer.answer)
            is UserAnswer.MultipleText -> isAnswerCorrect(userAnswer.answers)
            else -> false
        }
    }

    private fun isAnswerCorrect(userAnswer: String): Boolean {
        if (userAnswer.isBlank()) return false

        val cleanedInput = userAnswer.replace(SPACE_REGEX, "")
        var lastIndex = -1

        for (target in answer) {
            val currentIndex = cleanedInput.indexOf(target, lastIndex + 1)
            if (currentIndex == -1) return false
            lastIndex = currentIndex
        }

        return true
    }

    private fun isAnswerCorrect(userAnswer: List<String>): Boolean {
        if (userAnswer.isEmpty()) return false
        return userAnswer == answer
    }

    companion object {
        private val SPACE_REGEX = "\\s".toRegex()
    }
}
