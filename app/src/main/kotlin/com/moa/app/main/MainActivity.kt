package com.moa.app.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moa.app.designsystem.theme.MoATheme
import com.moa.app.feature.onboarding.signin.SignInScreen
import com.moa.app.feature.onboarding.signup.auth.SignUpPhoneAuthScreen
import com.moa.app.feature.onboarding.signup.profile.SignUpProfileScreen
import com.moa.app.feature.onboarding.splash.SplashScreen
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
                Scaffold { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AppRoute.Splash,
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        composable<AppRoute.Splash> {
                            SplashScreen()
                        }

                        composable<AppRoute.SignIn> {
                            SignInScreen()
                        }

                        composable<AppRoute.SignUpProfile> {
                            SignUpProfileScreen()
                        }

                        composable<AppRoute.SignUpPhoneAuth> {
                            SignUpPhoneAuthScreen()
                        }
                    }
                }
            }
        }
    }
}
