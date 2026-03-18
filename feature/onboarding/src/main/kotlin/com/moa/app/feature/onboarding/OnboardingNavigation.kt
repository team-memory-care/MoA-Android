package com.moa.app.feature.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.moa.app.feature.onboarding.connection.ConnectionCheckScreen
import com.moa.app.feature.onboarding.connection.UserConnectionScreen
import com.moa.app.feature.onboarding.landing.AuthLandingScreen
import com.moa.app.feature.onboarding.role.SelectUserRoleScreen
import com.moa.app.feature.onboarding.signin.SignInScreen
import com.moa.app.feature.onboarding.signup.SignUpCompleteScreen
import com.moa.app.feature.onboarding.signup.SignUpPhoneAuthScreen
import com.moa.app.feature.onboarding.signup.SignUpProfileScreen
import com.moa.app.feature.onboarding.signup.SignUpSharedViewModel
import com.moa.app.feature.onboarding.splash.SplashScreen
import com.moa.app.navigation.AppRoute
import com.moa.app.ui.extension.sharedViewModel

fun NavGraphBuilder.onboardingGraph(navController: NavController) {
    composable<AppRoute.Splash> { SplashScreen() }
    composable<AppRoute.AuthLanding> { AuthLandingScreen() }
    composable<AppRoute.SignIn> { SignInScreen() }
    navigation<AppRoute.SignUp>(
        startDestination = AppRoute.SignUpProfile
    ) {
        composable<AppRoute.SignUpProfile> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<SignUpSharedViewModel>(navController)
            SignUpProfileScreen(viewModel)
        }
        composable<AppRoute.SignUpPhoneAuth> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<SignUpSharedViewModel>(navController)
            SignUpPhoneAuthScreen(viewModel)
        }
        composable<AppRoute.SignUpComplete> { SignUpCompleteScreen() }
    }
    composable<AppRoute.SelectUserRole> { SelectUserRoleScreen() }
    composable<AppRoute.UserConnection> { UserConnectionScreen() }
    composable<AppRoute.ConnectionCheck> { ConnectionCheckScreen() }
}
