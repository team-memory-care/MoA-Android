package com.moa.app.feature.onboarding.landing

import androidx.lifecycle.ViewModel
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthLandingViewModel @Inject constructor(
    private val navigator: Navigator,
) : ViewModel() {

    fun onSignUpClicked() = navigator.navigate(route = AppRoute.SignUp)

    fun onSignInClicked() = navigator.navigate(route = AppRoute.SignIn)
}
