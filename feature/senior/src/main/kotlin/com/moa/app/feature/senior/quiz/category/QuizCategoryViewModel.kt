package com.moa.app.feature.senior.quiz.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.feature.senior.quiz.category.model.QuizCategorySideEffect
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.NavigationOptions
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizCategoryViewModel @Inject constructor(
    private val navigator: Navigator,
) : ViewModel() {

    fun onCategoryClicked(quizCategory: QuizCategory) {
        viewModelScope.launch {
            when (quizCategory) {
                QuizCategory.PERSISTENCE -> navigateToQuiz(AppRoute.PersistenceQuiz)
                QuizCategory.LINGUISTIC -> navigateToQuiz(AppRoute.LinguisticQuiz)
                QuizCategory.ATTENTION -> navigateToQuiz(AppRoute.AttentionQuiz)
                QuizCategory.SPACETIME -> navigateToQuiz(AppRoute.SpaceTimeQuiz)
                QuizCategory.MEMORY -> navigateToQuiz(AppRoute.MemoryQuiz)
                QuizCategory.ALL -> {}
            }
        }
    }

    private fun navigateToQuiz(route: AppRoute) {
        navigator.navigate(
            route = route,
            options = NavigationOptions(launchSingleTop = true),
        )
    }

    fun onBackClick() = navigator.navigateBack()
}
