package com.moa.app.feature.senior.quiz

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.moa.app.designsystem.R
import com.moa.app.designsystem.foundation.defaultMoaColors

enum class QuizCategory(
    @DrawableRes val imageRes: Int,
    val backgroundColor: Color,
    val title: String,
    val description: String
) {
    ORIENTATION(
        imageRes = R.drawable.img_quiz_list_1,
        backgroundColor = defaultMoaColors.blue400,
        title = "지남력",
        description = "현재 시간과 장소를\n맞게 인지해요",
    ),
    LANGUAGE(
        imageRes = R.drawable.img_quiz_list_2,
        backgroundColor = defaultMoaColors.orange500,
        title = "언어능력",
        description = "말하기 따라하기로\n언어를 살펴봐요",
    ),
    MEMORY(
        imageRes = R.drawable.img_quiz_list_3,
        backgroundColor = defaultMoaColors.red400,
        title = "기억력",
        description = "단어를 기억하고\n다시 말해요",
    ),
    ATTENTION_CALCULATION(
        imageRes = R.drawable.img_quiz_list_4,
        backgroundColor = defaultMoaColors.purple600,
        title = "주의력/계산",
        description = "집중해 계산하고\n차례대로 풀어봐요",
    ),
    VISUOSPATIAL(
        imageRes = R.drawable.img_quiz_list_5,
        backgroundColor = defaultMoaColors.green500,
        title = "시공간 구조",
        description = "도형을 보고\n공간을 이해해요",
    );
}
