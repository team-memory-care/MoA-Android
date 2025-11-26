package com.moa.app.feature.senior.quiz.category

import androidx.lifecycle.ViewModel
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.NavigationOptions
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizCategoryViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun onCategoryClicked(quizCategory: QuizCategory) {
        when (quizCategory) {
            QuizCategory.ORIENTATION -> navigateToPersistence()
            else -> {}
        }

    }

    private fun navigateToPersistence() {
        navigator.navigate(
            route = AppRoute.PersistenceQuiz,
            options = NavigationOptions(
                launchSingleTop = true
            )
        )
    }
}
