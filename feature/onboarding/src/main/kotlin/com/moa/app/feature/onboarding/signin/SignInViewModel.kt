package com.moa.app.feature.onboarding.signin

import androidx.lifecycle.ViewModel
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val navigator: Navigator,
) : ViewModel() {

    fun navigateToSignUp() {

    }
}
