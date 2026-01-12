package com.moa.app.feature.onboarding.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.auth.model.UserRole
import com.moa.app.domain.auth.usecase.ReissueTokenUseCase
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.NavigationOptions
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val navigator: Navigator,
    private val reissueTokenUseCase: ReissueTokenUseCase
) : ViewModel() {

    init {
        autoLogin()
    }

    private fun autoLogin() {
        viewModelScope.launch {
            val result = coroutineScope {
                val apiDeferred = async { reissueTokenUseCase() }
                val timerJob = launch { delay(1000L) }
                timerJob.join()
                apiDeferred.await()
            }

            result.fold(
                onSuccess = { userRole ->
                    handleNavigationForRole(userRole)
                },
                onFailure = {
                    navigateToRoute(AppRoute.AuthLanding)
                },
            )
        }
    }

    private fun handleNavigationForRole(userRole: UserRole) {
        when (userRole) {
            UserRole.PARENT -> navigateToRoute(AppRoute.SeniorHome)
            UserRole.CHILD -> navigateToRoute(AppRoute.GuardianHome)
            UserRole.PENDING -> navigateToRoute(AppRoute.SelectUserRole)
            else -> navigateToRoute(AppRoute.AuthLanding)
        }
    }

    private fun navigateToRoute(route: AppRoute) {
        navigator.navigate(
            route = route,
            options = NavigationOptions(
                popUpTo = AppRoute.Splash,
                inclusive = true,
                clearBackStack = true
            )
        )
    }
}
