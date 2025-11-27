package com.moa.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute {
    @Serializable
    data object Splash : AppRoute

    @Serializable
    data object AuthLanding : AppRoute

    @Serializable
    data object SignUp : AppRoute

    @Serializable
    data object SignUpProfile : AppRoute

    @Serializable
    data object SignUpPhoneAuth : AppRoute

    @Serializable
    data object SignUpComplete : AppRoute

    @Serializable
    data object SignIn : AppRoute

    @Serializable
    data object SelectUserRole : AppRoute

    @Serializable
    data object SeniorHome : AppRoute

    @Serializable
    data object QuizCategory : AppRoute

    @Serializable
    data object OrientationQuiz : AppRoute

    @Serializable
    data class UserConnection(val userRole: String) : AppRoute

    @Serializable
    data object SeniorSetting
}
