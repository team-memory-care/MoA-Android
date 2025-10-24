package com.moa.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute {
    @Serializable
    data object Splash : AppRoute

    @Serializable
    data object AuthLanding : AppRoute

    @Serializable
    data object SignIn : AppRoute

    @Serializable
    data object SignUpProfile : AppRoute

    @Serializable
    data object SignUpPhoneAuth : AppRoute
}
