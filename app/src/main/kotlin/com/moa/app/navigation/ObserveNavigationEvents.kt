package com.moa.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.moa.app.main.MainViewModel

@Composable
internal fun ObserveNavigationEvents(
    viewModel: MainViewModel,
    navController: NavController,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.navigationEvents, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.navigationEvents.collect { event ->
                when (event) {
                    is NavigationEvent.NavigateBack -> navController.popBackStack()
                    is NavigationEvent.OpenUrl -> openUrlInBrowser(context, event.url)
                    is NavigationEvent.Navigate -> {
                        navController.navigate(event.route) {
                            if (event.options.clearBackStack) {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            } else {
                                event.options.popUpTo?.let { popUpToRoute ->
                                    popUpTo(popUpToRoute) {
                                        inclusive = event.options.inclusive
                                        saveState = event.options.saveState
                                    }
                                }
                            }
                            launchSingleTop = event.options.launchSingleTop
                            restoreState = event.options.restoreState
                        }
                    }
                }
            }
        }
    }
}
