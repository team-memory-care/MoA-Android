package com.moa.app.domain.quiz.usecase

import com.moa.app.domain.quiz.model.Quiz
import com.moa.app.domain.quiz.model.Quizzes
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

class FetchOrientationQuizUseCase @Inject constructor() {
    operator fun invoke(): Quizzes {
        val quizzes = persistentListOf(
            Quiz("오늘은 몇 년도인가요?", persistentListOf("2025년", "2022년", "2020년"), 0),
            Quiz("지금은 무슨 계절인가요?", persistentListOf("여름", "가을", "겨울"), 0),
            Quiz("오늘은 무슨 요일인가요?", persistentListOf("월요일", "수요일", "금요일"), 2),
            Quiz("지금 우리가 있는 나라는 어디입니까?", persistentListOf("대한민국", "일본", "중국"), 0),
            Quiz("지금은 몇 월입니까?", persistentListOf("1월", "8월", "12월"), 2),
        )

        return Quizzes.from(quizzes)
    }
}
