package com.moa.app.feature.report.extension

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.moa.app.designsystem.R
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.domain.quiz.model.QuizCategory

val QuizCategory.scoreTitle: String
    get() = when (this) {
        QuizCategory.ALL -> "전체 평균"
        QuizCategory.PERSISTENCE -> "지남력"
        QuizCategory.LINGUISTIC -> "언어능력"
        QuizCategory.MEMORY -> "기억력"
        QuizCategory.ATTENTION -> "주의력/계산"
        QuizCategory.SPACETIME -> "시공간 구조"
    }

val QuizCategory.scoreBackgroundColor: Color
    @Composable
    get() = when (this) {
        QuizCategory.PERSISTENCE -> MoaTheme.colors.blue400
        QuizCategory.LINGUISTIC -> MoaTheme.colors.orange500
        QuizCategory.MEMORY -> MoaTheme.colors.red400
        QuizCategory.ATTENTION -> MoaTheme.colors.purple600
        QuizCategory.SPACETIME -> MoaTheme.colors.green500
        QuizCategory.ALL -> MoaTheme.colors.blue400
    }

val QuizCategory.scoreIconRes: Int
    @DrawableRes
    get() = when (this) {
        QuizCategory.PERSISTENCE -> R.drawable.img_persistence_quiz
        QuizCategory.LINGUISTIC -> R.drawable.img_linguistic_quiz
        QuizCategory.MEMORY -> R.drawable.img_memory_quiz
        QuizCategory.ATTENTION -> R.drawable.img_attention_quiz
        QuizCategory.SPACETIME -> R.drawable.img_space_quiz
        QuizCategory.ALL -> R.drawable.img_persistence_quiz
    }
