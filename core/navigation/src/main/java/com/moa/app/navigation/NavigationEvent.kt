package com.moa.app.navigation

sealed interface NavigationEvent {
    data class Navigate(
        val route: AppRoute,
        val options: NavigationOptions = NavigationOptions.Default,
    ) : NavigationEvent

    data object NavigateBack : NavigationEvent
}
