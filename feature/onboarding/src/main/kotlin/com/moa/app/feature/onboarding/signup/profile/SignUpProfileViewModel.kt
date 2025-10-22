package com.moa.app.feature.onboarding.signup.profile

import androidx.lifecycle.ViewModel
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpProfileViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {


    fun navigateToNext() {
        navigator.navigate(AppRoute.SignUpPhoneAuth)
    }

    fun navigateToBack() {
        navigator.navigateBack()
    }
}
