package com.moa.app.navigation

import kotlinx.coroutines.flow.Flow

interface Navigator {
    val events: Flow<NavigationEvent>

    fun navigate(
        route: AppRoute,
        options: NavigationOptions = NavigationOptions.Default,
    )

    fun navigateBack()
    fun openUrl(url: String)
}
