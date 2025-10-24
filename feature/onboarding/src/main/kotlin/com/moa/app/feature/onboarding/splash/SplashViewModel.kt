package com.moa.app.feature.onboarding.splash

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
class SplashViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    init {
        navigateToAuthLanding()
    }

    private fun navigateToAuthLanding() {
        viewModelScope.launch {
            delay(1000L)
            navigator.navigate(
                route = AppRoute.AuthLanding,
                options = NavigationOptions(
                    popUpTo = AppRoute.Splash,
                    inclusive = true,
                ),
            )
        }
    }
}
