package com.moa.app.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {

    private val _events = Channel<NavigationEvent>(
        capacity = Channel.CONFLATED,
    )
    override val events: Flow<NavigationEvent> = _events.receiveAsFlow()

    override fun navigate(route: AppRoute, options: NavigationOptions) {
        _events.trySend(
            NavigationEvent.Navigate(route = route, options = options),
        ).onFailure {
            Timber.tag("Navigator").e(it, "Failed to send navigation event")
        }
    }

    override fun navigateBack() {
        _events.trySend(NavigationEvent.NavigateBack)
            .onFailure {
                Timber.tag("Navigator").e(it, "Failed to send navigation event")
            }
    }
}
