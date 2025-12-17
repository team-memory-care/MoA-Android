package com.moa.app.feature.senior.quiz.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.feature.senior.quiz.category.model.QuizCategorySideEffect
import com.moa.app.feature.senior.quiz.category.model.QuizCategoryUiState
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

    private val _uiState = MutableStateFlow(QuizCategoryUiState.INIT)
    val uiState: StateFlow<QuizCategoryUiState> = _uiState.asStateFlow()

    private val _sideEffect: MutableSharedFlow<QuizCategorySideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<QuizCategorySideEffect> = _sideEffect.asSharedFlow()

    init {
        _uiState.update {
            it.copy(
                enabledCategories = setOf(
                    QuizCategory.PERSISTENCE,
                    QuizCategory.LINGUISTIC,
                    QuizCategory.ATTENTION,
                    QuizCategory.SPACETIME
                ),
            )
        }
    }


    fun onCategoryClicked(quizCategory: QuizCategory) {
        if (!_uiState.value.enabledCategories.contains(quizCategory)) {
            viewModelScope.launch {
                _sideEffect.emit(QuizCategorySideEffect.ShowToast("${quizCategory.name} 퀴즈는 아직 준비중이에요"))
            }
            return
        }

        viewModelScope.launch {
            when (quizCategory) {
                QuizCategory.PERSISTENCE -> navigateToQuiz(AppRoute.PersistenceQuiz)
                QuizCategory.LINGUISTIC -> navigateToQuiz(AppRoute.LinguisticQuiz)
                QuizCategory.ATTENTION -> navigateToQuiz(AppRoute.AttentionQuiz)
                else -> {
                    _sideEffect.emit(QuizCategorySideEffect.ShowToast("${quizCategory.name} 퀴즈는 아직 준비중이에요"))
                }
            }
        }
    }

    fun onBackClick() = navigator.navigateBack()

    private fun navigateToQuiz(route: AppRoute) {
        navigator.navigate(
            route = route,
            options = NavigationOptions(
                launchSingleTop = true,
            ),
        )
    }
}
