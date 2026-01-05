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
    data class UserConnection(val userRole: String) : AppRoute

    @Serializable
    data class ConnectionCheck(val userId: Long) : AppRoute

    @Serializable
    data object SeniorHome : AppRoute

    @Serializable
    data object QuizCategory : AppRoute

    @Serializable
    data object PersistenceQuiz : AppRoute

    @Serializable
    data object LinguisticQuiz : AppRoute

    @Serializable
    data object AttentionQuiz : AppRoute

    @Serializable
    data object SpaceTimeQuiz : AppRoute

    @Serializable
    data object MemoryQuiz : AppRoute

    @Serializable
    data object DailyQuiz : AppRoute

    @Serializable
    data object SeniorSetting : AppRoute

    @Serializable
    data class Report(val parentId: Long?) : AppRoute

    @Serializable
    data object GuardianHome : AppRoute

    @Serializable
    data object GuardianSetting : AppRoute
}
