package com.moa.app.feature.onboarding.role

import androidx.lifecycle.ViewModel
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.NavigationOptions
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class SelectUserRoleViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun navigateToUserConnection() {
        navigator.navigate(
            route = AppRoute.UserConnection,
            options = NavigationOptions(
                launchSingleTop = true
            )
        )
    }
}
