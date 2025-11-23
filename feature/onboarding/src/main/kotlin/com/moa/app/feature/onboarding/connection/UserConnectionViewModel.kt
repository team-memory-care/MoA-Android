package com.moa.app.feature.onboarding.connection

import androidx.lifecycle.ViewModel
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.NavigationOptions
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class UserConnectionViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun navigateToSeniorHome() {
        navigator.navigate(
            route = AppRoute.SeniorHome,
            options = NavigationOptions(
                popUpTo = AppRoute.UserConnection,
                inclusive = true,
            )
        )
    }
}
