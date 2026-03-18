package com.moa.app.feature.senior

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moa.app.feature.senior.home.SeniorHomeScreen
import com.moa.app.feature.senior.quiz.attention.AttentionQuizScreen
import com.moa.app.feature.senior.quiz.category.QuizCategoryScreen
import com.moa.app.feature.senior.quiz.daily.DailyQuizScreen
import com.moa.app.feature.senior.quiz.linguistic.LinguisticQuizScreen
import com.moa.app.feature.senior.quiz.memory.MemoryQuizScreen
import com.moa.app.feature.senior.quiz.persistence.PersistenceQuizScreen
import com.moa.app.feature.senior.quiz.spacetime.SpaceTimeQuizScreen
import com.moa.app.feature.senior.setting.SeniorSettingScreen
import com.moa.app.navigation.AppRoute

fun NavGraphBuilder.seniorGraph() {
    composable<AppRoute.SeniorHome> { SeniorHomeScreen() }
    composable<AppRoute.QuizCategory> { QuizCategoryScreen() }
    composable<AppRoute.PersistenceQuiz> { PersistenceQuizScreen() }
    composable<AppRoute.LinguisticQuiz> { LinguisticQuizScreen() }
    composable<AppRoute.AttentionQuiz> { AttentionQuizScreen() }
    composable<AppRoute.SpaceTimeQuiz> { SpaceTimeQuizScreen() }
    composable<AppRoute.MemoryQuiz> { MemoryQuizScreen() }
    composable<AppRoute.DailyQuiz> { DailyQuizScreen() }
    composable<AppRoute.SeniorSetting> { SeniorSettingScreen() }
}
