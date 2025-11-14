package com.moa.app.domain.quiz.usecase

import com.moa.app.domain.quiz.model.Quiz
import javax.inject.Inject

class CheckAnswerUseCase @Inject constructor() {
    operator fun invoke(quiz: Quiz, selectedIndex: Int?): Boolean {
        return quiz.isCorrect(selectedIndex)
    }
}
