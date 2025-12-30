package com.moa.app.feature.onboarding.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.NavigationOptions
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpCompleteViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    init {
        navigateToSelectUserRole()
    }

    private fun navigateToSelectUserRole() {
        viewModelScope.launch {
            delay(1000L)
            navigator.navigate(
                route = AppRoute.SelectUserRole,
                options = NavigationOptions(
                    popUpTo = AppRoute.SignUpComplete,
                    inclusive = true
                )
            )
        }
    }
}
