package com.moa.app.feature.senior.home

import androidx.lifecycle.ViewModel
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeniorHomeViewModel @Inject constructor(
    private val navigator: Navigator,
) : ViewModel() {


    fun navigateToQuizCategory() {
        navigator.navigate(AppRoute.QuizCategory)
    }

    fun navigateToDailyQuiz() {
        navigator.navigate(AppRoute.PersistenceQuiz)
    }

    fun navigateToSetting() {
        navigator.navigate(AppRoute.SeniorSetting)
    }
}
