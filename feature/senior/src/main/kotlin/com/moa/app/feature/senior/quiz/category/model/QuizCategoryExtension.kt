package com.moa.app.feature.senior.quiz.category.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.moa.app.designsystem.R
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.domain.quiz.model.QuizCategory

val QuizCategory.title: String
    get() = when (this) {
        QuizCategory.PERSISTENCE -> "지남력"
        QuizCategory.LINGUISTIC -> "언어능력"
        QuizCategory.MEMORY -> "기억력"
        QuizCategory.ATTENTION -> "주의력/계산"
        QuizCategory.SPACETIME -> "시공간 구조"
    }

val QuizCategory.description: String
    get() = when (this) {
        QuizCategory.PERSISTENCE -> "현재 시간과 장소를\n맞게 인지해요"
        QuizCategory.LINGUISTIC -> "말하기 따라하기로\n언어를 살펴봐요"
        QuizCategory.MEMORY -> "단어를 기억하고\n다시 말해요"
        QuizCategory.ATTENTION -> "집중해 계산하고\n차례대로 풀어봐요"
        QuizCategory.SPACETIME -> "도형을 보고\n공간을 이해해요"
    }

val QuizCategory.backgroundColor: Color
    @Composable
    get() = when (this) {
        QuizCategory.PERSISTENCE -> MoaTheme.colors.blue400
        QuizCategory.LINGUISTIC -> MoaTheme.colors.orange500
        QuizCategory.MEMORY -> MoaTheme.colors.red400
        QuizCategory.ATTENTION -> MoaTheme.colors.purple600
        QuizCategory.SPACETIME -> MoaTheme.colors.green500
    }

val QuizCategory.iconRes: Int
    @DrawableRes
    get() = when (this) {
        QuizCategory.PERSISTENCE -> R.drawable.img_quiz_list_1
        QuizCategory.LINGUISTIC -> R.drawable.img_quiz_list_2
        QuizCategory.MEMORY -> R.drawable.img_quiz_list_3
        QuizCategory.ATTENTION -> R.drawable.img_quiz_list_4
        QuizCategory.SPACETIME -> R.drawable.img_quiz_list_5
    }

val QuizCategory.loadDescription: String
    get() = when (this) {
        QuizCategory.PERSISTENCE -> "지남력은\n시간과 장소를 확인해요"
        QuizCategory.LINGUISTIC -> "언어능력은\n말하기와 이해를 살펴봐요"
        QuizCategory.MEMORY -> "기억력은\n단어와 그림을 기억해요"
        QuizCategory.ATTENTION -> "주의력/계산은\n집중과 계산을 해봐요"
        QuizCategory.SPACETIME -> "시공간 구조는\n도형과 공간을 이해해요"
    }

val QuizCategory.loadBackgroundColor: Color
    @Composable
    get() = when (this) {
        QuizCategory.PERSISTENCE -> MoaTheme.colors.blue100
        QuizCategory.LINGUISTIC -> MoaTheme.colors.orange300
        QuizCategory.MEMORY -> MoaTheme.colors.red200
        QuizCategory.ATTENTION -> MoaTheme.colors.purple100
        QuizCategory.SPACETIME -> MoaTheme.colors.neutralGray95
    }

val QuizCategory.loadTitleColor: Color
    @Composable
    get() = when (this) {
        QuizCategory.PERSISTENCE -> MoaTheme.colors.blue700
        QuizCategory.LINGUISTIC -> MoaTheme.colors.orange700
        QuizCategory.MEMORY -> MoaTheme.colors.red700
        QuizCategory.ATTENTION -> MoaTheme.colors.purple700
        QuizCategory.SPACETIME -> MoaTheme.colors.coolGray10
    }

val QuizCategory.loadReadyColor: Color
    @Composable
    get() = when (this) {
        QuizCategory.PERSISTENCE -> MoaTheme.colors.blue200
        QuizCategory.LINGUISTIC -> MoaTheme.colors.orange300
        QuizCategory.MEMORY -> MoaTheme.colors.red300
        QuizCategory.ATTENTION -> MoaTheme.colors.purple300
        QuizCategory.SPACETIME -> MoaTheme.colors.coolGray10
    }

