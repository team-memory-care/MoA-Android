package com.moa.app.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.moa.app.designsystem.theme.MoATheme
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.feature.onboarding.connection.UserConnectionScreen
import com.moa.app.feature.onboarding.landing.AuthLandingScreen
import com.moa.app.feature.onboarding.role.SelectUserRoleScreen
import com.moa.app.feature.onboarding.signin.SignInScreen
import com.moa.app.feature.onboarding.signup.SignUpCompleteScreen
import com.moa.app.feature.onboarding.signup.SignUpPhoneAuthScreen
import com.moa.app.feature.onboarding.signup.SignUpProfileScreen
import com.moa.app.feature.onboarding.signup.SignUpSharedViewModel
import com.moa.app.feature.onboarding.splash.SplashScreen
import com.moa.app.feature.senior.home.SeniorHomeScreen
import com.moa.app.feature.senior.quiz.persistence.PersistenceQuizScreen
import com.moa.app.feature.senior.quiz.category.QuizCategoryScreen
import com.moa.app.feature.senior.quiz.linguistic.LinguisticQuizScreen
import com.moa.app.feature.senior.setting.SeniorSettingScreen
import com.moa.app.navigation.AppRoute
import com.moa.app.navigation.ObserveNavigationEvents
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val mainViewModel: MainViewModel = hiltViewModel()

            ObserveNavigationEvents(mainViewModel, navController)

            MoATheme {
                Scaffold(
                    containerColor = MoaTheme.colors.white,
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AppRoute.Splash,
                        modifier = Modifier.padding(innerPadding),
                    ) {
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
                        composable<AppRoute.SeniorHome> { SeniorHomeScreen() }
                        composable<AppRoute.QuizCategory> { QuizCategoryScreen() }
                        composable<AppRoute.PersistenceQuiz> { PersistenceQuizScreen() }
                        composable<AppRoute.LinguisticQuiz> { LinguisticQuizScreen() }
                        composable<AppRoute.UserConnection> { UserConnectionScreen() }
                        composable<AppRoute.SeniorSetting> { SeniorSettingScreen() }
                    }
                }
            }
        }
    }
}

@Composable
internal inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) { navController.getBackStackEntry(navGraphRoute) }
    return hiltViewModel(parentEntry)
}
