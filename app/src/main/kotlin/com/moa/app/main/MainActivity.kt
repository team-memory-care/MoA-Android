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
import androidx.navigation.compose.rememberNavController
import com.moa.app.designsystem.theme.MoATheme
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.feature.guardian.guardianGraph
import com.moa.app.feature.onboarding.onboardingGraph
import com.moa.app.feature.report.reportGraph
import com.moa.app.feature.senior.seniorGraph
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
                        onboardingGraph(navController)
                        seniorGraph()
                        guardianGraph()
                        reportGraph()
                    }
                }
            }
        }
    }
}
